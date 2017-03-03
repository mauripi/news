package br.com.mauricio.news.mb.contabil;


import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;

import org.primefaces.event.FlowEvent;

import net.sf.jasperreports.engine.JRException;
import br.com.mauricio.news.ln.FilialLN;
import br.com.mauricio.news.ln.GenericLN;
import br.com.mauricio.news.ln.contabil.BaixaBemLN;
import br.com.mauricio.news.ln.contabil.RelBaixaBemLN;
import br.com.mauricio.news.model.CCusto;
import br.com.mauricio.news.model.Filial;
import br.com.mauricio.news.model.Login;
import br.com.mauricio.news.model.contabil.BaixaBem;
import br.com.mauricio.news.model.contabil.ItemBaixaBem;
import br.com.mauricio.news.model.contabil.Patrimonio;
import br.com.mauricio.news.model.contabil.TipoBaixa;

@ManagedBean(name="baixabemMB")
@ViewScoped
public class BaixaBemBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private BaixaBem baixabem = new BaixaBem();
	private BaixaBem baixabemSel = new BaixaBem();
	private GenericLN<BaixaBem> gln = new GenericLN<BaixaBem>();
	private List<BaixaBem> baixabens = new ArrayList<BaixaBem>();
	private List<ItemBaixaBem> itens = new ArrayList<ItemBaixaBem>();
	private List<ItemBaixaBem> itensRemovido = new ArrayList<ItemBaixaBem>();
	private int controlaCadastro = 0;
	private String msg;
	private List<String> msgs;
	private Login usuario = new Login();
	private List<CCusto> centroCustos;
	private List<CCusto> custosFilial;
	private List<Filial> filiais = new ArrayList<Filial>();
    private TipoBaixa tipoBaixa;
    private List<Patrimonio> patrimonios = new ArrayList<Patrimonio>();
    private String codigo;
    private Date data = new Date();


	private String descricao="";
	private boolean skip;
	private Double totalCompra;
	private Double totalResidual;
	private Boolean isVenda=false;
	
	
	@PostConstruct
	public void init(){
		FacesContext cx = FacesContext.getCurrentInstance();
        HttpSession sessao = (HttpSession) cx.getExternalContext().getSession(false);		
		this.usuario = (Login) sessao.getAttribute("login");
		baixabem = new BaixaBem();
		listarFiliais();
		baixabem.setFilial(filiais.get(0));
		carregaCentroCustosDaFilial();
		listar();		
	}
	
	private void listarCentrosDeCustos(){
		GenericLN<CCusto> cgln= new GenericLN<CCusto>();
		centroCustos = cgln.listWithoutRemoved("ccusto", "filial,nome");
	}
	
	private void listarFiliais(){
		FilialLN ln = new FilialLN();
		this.filiais = ln.list();
	}
	
	public void carregaCentroCustosDaFilial(){
		listarCentrosDeCustos();
		custosFilial = new ArrayList<CCusto>();
		for(CCusto c:centroCustos)
			if(baixabem.getFilial().getId()==c.getFilial().getId())
				custosFilial.add(c);
		baixabem.setCcusto(custosFilial.get(0));
	}
	
	public void listar(){
		if(usuario.getChapa().equals("000746")||usuario.getChapa().equals("000772")||usuario.getChapa().equals("000755")||usuario.getChapa().equals("000763")){
			gln = new GenericLN<BaixaBem>();
			baixabens = gln.listWithoutRemoved("baixabem", "id desc");
		}else{
			BaixaBemLN bln = new BaixaBemLN();
			baixabens = bln.listaPorUsuario(usuario);			
		}
	}

	public void novo(){
		limpaCadastro();
		controlaCadastro=1;
	}

	public void edita(){
		controlaCadastro=2;
	}

	public void exclui(){
		if(baixabemSel.getId()==null){
			msg = "Nenhum registro selecionado para exclusão.";
			mensagens();			
		}else{
			gln = new GenericLN<BaixaBem>();
			enviaEmailBemExcluido(baixabemSel);
			msg = gln.remove(baixabemSel);
			mensagens();
			limpaCadastro();
			listar();
		}		
	}

	public void encontrarBem(){
		BaixaBemLN ln = new BaixaBemLN();
		patrimonios = ln.obterPatrimonios(codigo, data);
		if(patrimonios.size()<1){
			msg = "Nenhum registro encontrado para este número de patrimônio. ";
			baixabem.setDataaquisicao(null);
			baixabem.setDescricaoBem("");
			baixabem.setVlraquisicao(null);
			baixabem.setVlrresidual(null);
			mensagens();	
		}else{
			for(Patrimonio p: patrimonios){
				if(p.getNumpla().contains("-00")){
					baixabem.setDataaquisicao(p.getDataqi());
					baixabem.setDescricaoBem(p.getDesbem());
					baixabem.setVlraquisicao(p.getVlrbas());
					baixabem.setVlrresidual(p.getVlrres());
					baixabem.setPatrimonio(p.getCodbem());
				}
			}
			totalCompra = new Double("0.0");
			totalResidual = new Double("0.0");
			for(Patrimonio p: patrimonios){
				ItemBaixaBem item = new ItemBaixaBem();
				item.setDescricao(p.getDesbem());
				item.setNotafiscal(p.getNumdoc());
				item.setPatrimonio(p.getCodbem());
				item.setNomeFornecedor(p.getNomfor());
				item.setNumpla(p.getNumpla());
				item.setVlraquisicao(p.getVlrbas());
				item.setVlrresidual(p.getVlrres());
				item.setQuantidade(new Double("0"));
				item.setDataaquisicao(p.getDataqi());
				item.setDatavenda(data);
				item.setBaixabem(baixabem);
				totalCompra = totalCompra+p.getVlrbas();
				totalResidual = totalResidual+p.getVlrres();
				itens.add(item);
			}
			patrimonios = new ArrayList<Patrimonio>();
		}
	}
	
	public void addItensRemovido(ItemBaixaBem i){
		totalCompra = totalCompra-i.getVlraquisicao();
		totalResidual = totalResidual-i.getVlrresidual();		
		itensRemovido.add(i);
	}	

	public void grava(){	
		if(validaCampos()){
			gln = new GenericLN<BaixaBem>();
			baixabem.setSolicitante(usuario);
			baixabem.setDatavenda(data);
			baixabem.setDataemissao(new Date());
			baixabem.setTipoBaixa(tipoBaixa);
			baixabem.setJustificativa(acresentaJustificativa());
			baixabem.setItens(itens);

			if(controlaCadastro==1)					
				msg = gln.add(baixabem);

			if(controlaCadastro==2)					
				msg = gln.update(baixabem);
			
			gerarRelatorio();
			enviaEmail(baixabem);
			mensagens();
			listar();
			limpaCadastro();	
		}else{
			msg = "Favor preencha todos campos, pois são obrigatórios.";
			mensagens();
		}
	}
	
	public void imprimir() {
		RelBaixaBemLN rel = new RelBaixaBemLN();
		try {
			rel.imprimir(baixabem);
		} catch (IOException | JRException e) {
			msg = msg + " Mas não foi possivel imprimir relatório.";
			System.out.println(e.getLocalizedMessage() + " BaixaBemBean.imprimir() IOException");
			mensagens();
		}
	}	
	
	private void enviaEmail(BaixaBem b) {
		BaixaBemLN bln = new BaixaBemLN();
		bln.enviarEmail(b);
	}

	private void enviaEmailBemExcluido(BaixaBem b) {
		BaixaBemLN bln = new BaixaBemLN();
		bln.enviarEmailExclusao(b);
	}	

	public void gerarRelatorio() {
		RelBaixaBemLN rel = new RelBaixaBemLN();
		try {
			rel.geraRelatorioBaixa(baixabem);
		} catch (IOException e) {
			msg = msg + " Mas não foi possivel gerar relatório.";
			System.out.println(e.getLocalizedMessage() + " BaixaBemBean.gerarRelatorio() IOException");
			mensagens();
		}
	}

	private String acresentaJustificativa(){
		StringBuilder sb = new StringBuilder();
		sb.append(baixabem.getJustificativa());		
		for(Patrimonio p: patrimonios){
			if(p.getCodbem().contains("-00")){
				sb.append("   ***  ");
				sb.append("Fornecedor: " + p.getNomfor());
				sb.append(" Núm.Nota: "+ p.getNumdoc());
			}
		}
		return sb.toString();		
	}

	public void limpaCadastro(){
		baixabem = baixabemSel = new BaixaBem();
		patrimonios=new ArrayList<Patrimonio>();
		data = new Date();
		codigo="";
		controlaCadastro=0;
		tipoBaixa = null;
		baixabem.setFilial(filiais.get(0));
		carregaCentroCustosDaFilial();
		totalCompra = new Double("0.0");
		totalResidual = new Double("0.0");
		itens = new ArrayList<ItemBaixaBem>();
		itensRemovido = new ArrayList<ItemBaixaBem>();		
	}

	public void selecao(){
		baixabemSel = baixabem;
		usuario = baixabem.getSolicitante();
		data = baixabem.getDatavenda();
		tipoBaixa = baixabem.getTipoBaixa();
		itens = baixabem.getItens();
		totalCompra = new Double("0.0");
		totalResidual = new Double("0.0");
		
		for(ItemBaixaBem i:itens){
			if(i.getNumpla().contains("-00")){
				String scod[] = i.getNumpla().split("-");
				codigo=scod[0];
			}
			totalCompra = totalCompra+i.getVlraquisicao();
			totalResidual = totalResidual+i.getVlrresidual();
		}
		
		
		//String sjus[] = baixabem.getJustificativa().replace("*", ";").split("   ;");
		//baixabem.setJustificativa(sjus[0]);
		//encontrarBem();
		controlaCadastro=0;
		///exibeArquivo();
		
		edita();
	}

  	public SelectItem[] getTiposBaixa() {
  		SelectItem[] items=null;
  		int i = 0;
		items = new SelectItem[TipoBaixa.values().length];
		for(TipoBaixa t: TipoBaixa.values()) 
			items[i++] = new SelectItem(t, t.name());
	 	return items;
 	}

	private boolean validaCampos(){
		if(usuario==null)
			return false;

		if(baixabem.getJustificativa()==null)
			return false;
		if(baixabem.getTipoBaixa()==TipoBaixa.VENDA)
			if(baixabem.getVlrvenda()==null)
				return false;
		if(data==null)
			return false;
		
		return true;		
	}
	
    public String onFlowProcess(FlowEvent event) {
	   	if(controlaCadastro!=0){
	       if(skip) {
	            skip = false;   //reset in case user goes back
	            return "confirm";
	        }else {
	        	msgs = new ArrayList<String>();
	        	if(event.getOldStep().equals("gerais")){
	        		if(tipoBaixa==null)
	        			msgs.add("Informe o tipo de baixa.");
	        		else
	        			if(tipoBaixa==TipoBaixa.VENDA)
	        				isVenda=true;
	        			else
	        				isVenda=false;
	        		if(msgs.size()>0){
	        			mensagens(msgs);
	        			return event.getOldStep();
	        		}
	        	}
	        	if(event.getOldStep().equals("patrimonios")){
	        		if(itens.size()<1)
	        			msgs.add("Adicione os patrimônios");
	        		if(msgs.size()>0){
	        			mensagens(msgs);
	        			return event.getOldStep();
	        		}       		
	        	} 
	        	if(event.getOldStep().equals("just")){
	        		if(baixabem.getJustificativa()==null)
	        			msgs.add("Adicione a justificativa");
	        		if(msgs.size()>0){
	        			mensagens(msgs);
	        			return event.getOldStep();
	        		}       		
	        	} 	        	
	        	
	            return event.getNewStep();
	        }
	   	}
		return "gerais";
   }	
	
	private void mensagens(List<String> ms) {
        FacesContext context = FacesContext.getCurrentInstance(); 
        for(String m:ms)
        	context.addMessage(null, new FacesMessage(m,m));  			
	}
	

	public void mensagens(){
        FacesContext context = FacesContext.getCurrentInstance();  	          
        context.addMessage(null, new FacesMessage("",msg));  		
	}
	//--------------------------GETTERS E SETTERS ----------------------------------

	public BaixaBem getBaixaBem() {
		return baixabem;
	}

	public void setBaixaBem(BaixaBem baixabem) {
		this.baixabem = baixabem;
	}

	public BaixaBem getBaixaBemSel() {
		return baixabemSel;
	}

	public void setBaixaBemSel(BaixaBem baixabemSel) {
		this.baixabemSel = baixabemSel;
	}

	public GenericLN<BaixaBem> getGln() {
		return gln;
	}

	public void setGln(GenericLN<BaixaBem> gln) {
		this.gln = gln;
	}

	public List<BaixaBem> getBaixaBens() {
		return baixabens;
	}

	public void setBaixaBens(List<BaixaBem> baixabens) {
		this.baixabens = baixabens;
	}

	public int getControlaCadastro() {
		return controlaCadastro;
	}

	public void setControlaCadastro(int controlaCadastro) {
		this.controlaCadastro = controlaCadastro;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Login getUsuario() {
		return usuario;
	}

	public void setUsuario(Login usuario) {
		this.usuario = usuario;
	}

	public BaixaBem getBaixabem() {
		return baixabem;
	}

	public void setBaixabem(BaixaBem baixabem) {
		this.baixabem = baixabem;
	}

	public BaixaBem getBaixabemSel() {
		return baixabemSel;
	}

	public void setBaixabemSel(BaixaBem baixabemSel) {
		this.baixabemSel = baixabemSel;
	}

	public List<BaixaBem> getBaixabens() {
		return baixabens;
	}

	public void setBaixabens(List<BaixaBem> baixabens) {
		this.baixabens = baixabens;
	}

	public List<CCusto> getCentroCustos() {
		return centroCustos;
	}

	public void setCentroCustos(List<CCusto> centroCustos) {
		this.centroCustos = centroCustos;
	}

	public List<Filial> getFiliais() {
		return filiais;
	}

	public void setFiliais(List<Filial> filiais) {
		this.filiais = filiais;
	}

	public List<CCusto> getCustosFilial() {
		return custosFilial;
	}

	public void setCustosFilial(List<CCusto> custosFilial) {
		this.custosFilial = custosFilial;
	}

	public TipoBaixa getTipoBaixa() {
		return tipoBaixa;
	}

	public void setTipoBaixa(TipoBaixa tipoBaixa) {
		this.tipoBaixa = tipoBaixa;
	}

	public List<Patrimonio> getPatrimonios() {
		return patrimonios;
	}

	public void setPatrimonios(List<Patrimonio> patrimonios) {
		this.patrimonios = patrimonios;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public boolean isSkip() {
		return skip;
	}

	public void setSkip(boolean skip) {
		this.skip = skip;
	}

	public List<ItemBaixaBem> getItens() {
		return itens;
	}

	public void setItens(List<ItemBaixaBem> itens) {
		this.itens = itens;
	}

	public List<ItemBaixaBem> getItensRemovido() {
		return itensRemovido;
	}

	public void setItensRemovido(List<ItemBaixaBem> itensRemovido) {
		this.itensRemovido = itensRemovido;
	}

	public Double getTotalCompra() {
		return totalCompra;
	}

	public void setTotalCompra(Double totalCompra) {
		this.totalCompra = totalCompra;
	}

	public Double getTotalResidual() {
		return totalResidual;
	}

	public void setTotalResidual(Double totalResidual) {
		this.totalResidual = totalResidual;
	}

	public Boolean getIsVenda() {
		return isVenda;
	}

	public void setIsVenda(Boolean isVenda) {
		this.isVenda = isVenda;
	}

}
