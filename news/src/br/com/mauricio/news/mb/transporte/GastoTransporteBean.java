package br.com.mauricio.news.mb.transporte;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.com.mauricio.news.ln.GenericLN;
import br.com.mauricio.news.ln.transporte.GastoTransporteLN;
import br.com.mauricio.news.model.transporte.GastoTransporte;
import br.com.mauricio.news.model.transporte.TipoDespTransp;

/**
*
* @author MAURICIO CRUZ
*/
@ManagedBean(name="gastotransporteMB")
@ViewScoped
public class GastoTransporteBean  implements Serializable {

	private static final long serialVersionUID = 1L;
	private GastoTransporte gasto;
	private GastoTransporte gastoSel;
	private List<GastoTransporte> gastos = new ArrayList<GastoTransporte>();
	private GenericLN<GastoTransporte> gln;
	private TipoDespTransp despesa;
	private List<TipoDespTransp> despesas = new ArrayList<TipoDespTransp>();
	private int controlaCadastro = 0;
	private String msg;
	private List<String> msgs;
	
	@PostConstruct
	public void init(){
		gasto = new GastoTransporte();
		listarTipoDespesa();
		listar();		
	}

	public void listar(){
		gln = new GenericLN<GastoTransporte>();
		gastos = gln.listWithoutRemoved("gastotransporte", "id,mes,tipodespesa desc");
	}

	public void listarTipoDespesa(){
		GenericLN<TipoDespTransp> glnd = new GenericLN<TipoDespTransp>();
		despesas = glnd.listWithoutRemoved("tipodesptransp", "despesa");
	}
	
	public void novo(){
		limpaCadastro();
		controlaCadastro=1;
	}

	public void edita(){
		controlaCadastro=2;
	}

	public void exclui(){
		if(gasto.getId()==null){
			msg = "Nenhum registro selecionado para exclusão.";
			mensagens();			
		}else{
			gln = new GenericLN<GastoTransporte>();
			msg = gln.update(gasto);
			mensagens();
			limpaCadastro();
			listar();
		}		
	}
	
	public void grava(){		
		if(validaCampos()){
			
			gln = new GenericLN<GastoTransporte>();
			if(controlaCadastro==2)	
				msg = gln.update(gasto);
			if(controlaCadastro==1)
				msg = gln.add(gasto);
			
			mensagens();
			listar();
			limpaCadastro();	
		}else{
			mensagens(msgs);
		}
	}
	
	private boolean existeNaBase(){
		GastoTransporteLN ln = new GastoTransporteLN();
		return ln.exiteNaBase(gasto);
	}

	private boolean existeNaBaseMaisDeUm(){
		GastoTransporteLN ln = new GastoTransporteLN();
		return ln.existeNaBaseMaisDeUm(gasto);
	}
	
	private boolean validaCampos(){
		msgs = new ArrayList<String>();
		boolean v=true;
		gasto.setTipodespesa(despesa);
		if(gasto.getMes()<=0||gasto.getMes()>12){
			msgs.add("Informe o mês entre 1 e 12.");
			v = false;
		}
		if(gasto.getAno()<2014){
			msgs.add("Informe o ano acima de 2014.");
			v = false;
		}		
		if(gasto.getValor()==null){
			msgs.add("Informe o valor.");
			v=false;		
		}	
		if(controlaCadastro==1){
			if(existeNaBase()){
				msgs.add("Já existe registro para este tipo neste período!");
				v = false;				
			}
		}
		if(controlaCadastro==2){
			if(existeNaBaseMaisDeUm()){
				msgs.add("Já existe registro para este tipo neste período!");
				v = false;				
			}
		}
		return v;		
	}
	
	public void limpaCadastro(){
		gasto = gastoSel = new GastoTransporte();
		controlaCadastro=0;
		despesa=new TipoDespTransp();
	}

	public void selecao(){
		gasto = gastoSel;
		despesa = gasto.getTipodespesa();
		controlaCadastro=0;
		edita();
	}
	
    public List<TipoDespTransp> completeDespesa(String query) {
        List<TipoDespTransp> filtered = new ArrayList<TipoDespTransp>(); 
        for (int i = 0; i < despesas.size(); i++) 
            if(despesas.get(i).getDespesa().toLowerCase().contains(query.toLowerCase()))
                filtered.add(despesas.get(i));              
        return filtered;
    }
    
	public void mensagens(){
        FacesContext context = FacesContext.getCurrentInstance();  	          
        context.addMessage(null, new FacesMessage("",msg));  		
	}
	
	private void mensagens(List<String> ms) {
        FacesContext context = FacesContext.getCurrentInstance(); 
        for(String m:ms)
        	context.addMessage(null, new FacesMessage("",m));  		
	}	

/*==================/GETTERS E SETTERS/===========================*/
	public GastoTransporte getGasto() {
		return gasto;
	}

	public void setGasto(GastoTransporte gasto) {
		this.gasto = gasto;
	}

	public GastoTransporte getGastoSel() {
		return gastoSel;
	}

	public void setGastoSel(GastoTransporte gastoSel) {
		this.gastoSel = gastoSel;
	}

	public List<GastoTransporte> getGastos() {
		return gastos;
	}

	public void setGastos(List<GastoTransporte> gastos) {
		this.gastos = gastos;
	}

	public GenericLN<GastoTransporte> getGln() {
		return gln;
	}

	public void setGln(GenericLN<GastoTransporte> gln) {
		this.gln = gln;
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

	public TipoDespTransp getDespesa() {
		return despesa;
	}

	public void setDespesa(TipoDespTransp despesa) {
		this.despesa = despesa;
	}

	public List<TipoDespTransp> getDespesas() {
		return despesas;
	}

	public void setDespesas(List<TipoDespTransp> despesas) {
		this.despesas = despesas;
	}

	public List<String> getMsgs() {
		return msgs;
	}

	public void setMsgs(List<String> msgs) {
		this.msgs = msgs;
	}


}
