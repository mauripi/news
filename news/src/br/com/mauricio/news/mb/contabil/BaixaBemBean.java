package br.com.mauricio.news.mb.contabil;

import java.io.IOException;
import java.io.InputStream;
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

import org.primefaces.event.FileUploadEvent;

import net.sf.jasperreports.engine.JRException;
import br.com.mauricio.news.ln.FilialLN;
import br.com.mauricio.news.ln.GenericLN;
import br.com.mauricio.news.ln.contabil.BaixaBemLN;
import br.com.mauricio.news.ln.contabil.RelBaixaBemLN;
import br.com.mauricio.news.model.CCusto;
import br.com.mauricio.news.model.Filial;
import br.com.mauricio.news.model.Login;
import br.com.mauricio.news.model.contabil.BaixaBem;
import br.com.mauricio.news.model.contabil.DocBaixaBem;
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
	private int controlaCadastro = 0;
	private String msg;
	private Login usuario = new Login();
	private List<CCusto> centroCustos;
	private List<CCusto> custosFilial;
	private List<Filial> filiais = new ArrayList<Filial>();
    private TipoBaixa tipoBaixa;
    private List<Patrimonio> patrimonios = new ArrayList<Patrimonio>();
    private String codigo;
    private Date data = new Date();
    private DocBaixaBem documento = new DocBaixaBem();
    private List<DocBaixaBem> documentos = new ArrayList<DocBaixaBem>();
	private String CAMINHO_DO_ARQUIVO = "C:\\ARQUIVOS_INTRANET\\CONTABILIDADE\\BAIXABEM\\";
	private String nomeArquivo;
	private InputStream is;
	private String descricao="";
	
	
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
		if(patrimonios.size()>0){
			for(Patrimonio p: patrimonios){
				if(p.getCodbem().contains("-00")){
					baixabem.setDataaquisicao(p.getDataqi());
					baixabem.setDescricaoBem(p.getDesbem());
					baixabem.setVlraquisicao(p.getVlrbas());
					baixabem.setVlrresidual(p.getVlrres());
					baixabem.setPatrimonio(p.getCodbem());
				}
			}
		}else{
			msg = "Nenhum registro encontrado para este número de patrimônio. Preencha os campos com as informações.";
			baixabem.setDataaquisicao(null);
			baixabem.setDescricaoBem("");
			baixabem.setVlraquisicao(null);
			baixabem.setVlrresidual(null);
			mensagens();	
		}
	}
	
	public void grava(){	
		if(validaCampos()){
			gln = new GenericLN<BaixaBem>();
			baixabem.setSolicitante(usuario);
			baixabem.setDatavenda(data);
			baixabem.setDataemissao(new Date());
			baixabem.setTipoBaixa(tipoBaixa);
			baixabem.setJustificativa(acresentaJustificativa());
			if(patrimonios.size()<1){
				baixabem.setPatrimonio(codigo);	
			}else{
				populaAgregados();
			}

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

	private void populaAgregados() {
		baixabem.setAgregados(null);
		List<BaixaBem> agregados = new ArrayList<BaixaBem>();
		for(Patrimonio p: patrimonios){
			if(!p.getCodbem().contains("-00")){
				BaixaBem b = new BaixaBem();
				b.setSolicitante(baixabem.getSolicitante());
				b.setCcusto(baixabem.getCcusto());
				b.setFilial(baixabem.getFilial());
				b.setDatavenda(baixabem.getDatavenda());
				b.setVlrvenda(0.0);
				b.setDataemissao(baixabem.getDataemissao());
				b.setJustificativa(baixabem.getJustificativa());
				b.setTipoBaixa(baixabem.getTipoBaixa());
				
				b.setDataaquisicao(p.getDataqi());
				b.setDescricaoBem(p.getDesbem());
				b.setPatrimonio(p.getCodbem());
				b.setVlraquisicao(p.getVlrbas());
				b.setVlrresidual(p.getVlrres());
				agregados.add(b);
			}
		}
		baixabem.setAgregados(agregados);
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
	
	private boolean validaCampos(){
		if(usuario==null)
			return false;
		if(tipoBaixa==null)
			return false;
		if(baixabem.getJustificativa()==null)
			return false;		
		if(baixabem.getVlrvenda()==null)
			return false;
		if(data==null)
			return false;
		
		return true;		
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
	}

	public void selecao(){
		baixabemSel = baixabem;
		usuario = baixabem.getSolicitante();
		data = baixabem.getDatavenda();
		tipoBaixa = baixabem.getTipoBaixa();
		String scod[] = baixabem.getPatrimonio().split("-");
		codigo=scod[0];
		String sjus[] = baixabem.getJustificativa().replace("*", ";").split("   ;");
		baixabem.setJustificativa(sjus[0]);
		encontrarBem();
		controlaCadastro=0;
		exibeArquivo();
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

  	
	private void exibeArquivo(){
		BaixaBemLN ln = new BaixaBemLN();
		ln.carregaArquivo(baixabem,caminho());		
	}
	
	private String caminho(){
		return CAMINHO_DO_ARQUIVO +baixabem.getId()+"\\";
	}

	public void handleFileUpload(FileUploadEvent event) throws IOException {
		is=event.getFile().getInputstream();
    	if(is!=null){
    		nomeArquivo =  event.getFile().getFileName();
    		BaixaBemLN ln = new BaixaBemLN();    
			msg = ln.recebeArquivoUpload(is,nomeArquivo,caminho());				
			if(msg!=""){
				msg = "Arquivo não importado. Ocorreu erro no recebimento do arquivo.";
				mensagens();
			}
		} else{
			System.out.println("is é null em BaixaBemBean.handleFileUpload(FileUploadEvent event)");
		}
    }

	public void salvarArquivo(){
		if(descricao.length()>1){		
			BaixaBemLN ln = new BaixaBemLN();    
			documento = new DocBaixaBem();
			documento.setArquivo(nomeArquivo);
			documento.setBaixabem(baixabem);
			documento.setDescricao(descricao);
	    	msg = ln.salvarDocumeto(documento);	
	    	documentos.add(documento);
	    	baixabem.getDocumentos().add(documento);
	    	mensagens();   	
	    	descricao="";
		}else{
			msg="Informe a descrição do arquivo.";
			mensagens();
		}
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

	public String getNomeArquivo() {
		return nomeArquivo;
	}

	public void setNomeArquivo(String nomeArquivo) {
		this.nomeArquivo = nomeArquivo;
	}

	public InputStream getIs() {
		return is;
	}

	public void setIs(InputStream is) {
		this.is = is;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public DocBaixaBem getDocumento() {
		return documento;
	}

	public void setDocumento(DocBaixaBem documento) {
		this.documento = documento;
	}

	public List<DocBaixaBem> getDocumentos() {
		return documentos;
	}

	public void setDocumentos(List<DocBaixaBem> documentos) {
		this.documentos = documentos;
	}

}
