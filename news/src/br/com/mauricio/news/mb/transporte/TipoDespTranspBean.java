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
import br.com.mauricio.news.model.transporte.TipoDespTransp;

/**
*
* @author MAURICIO CRUZ
*/
@ManagedBean(name="tipodesptranspMB")
@ViewScoped
public class TipoDespTranspBean  implements Serializable {

	private static final long serialVersionUID = 1L;
	private TipoDespTransp despesa;
	private TipoDespTransp despesaSel;
	private List<TipoDespTransp> despesas = new ArrayList<TipoDespTransp>();
	private GenericLN<TipoDespTransp> gln;
	private int controlaCadastro = 0;
	private String msg;
	
	@PostConstruct
	public void init(){
		despesa = new TipoDespTransp();
		listar();		
	}

	public void listar(){
		gln = new GenericLN<TipoDespTransp>();
		despesas = gln.listWithoutRemoved("tipodesptransp", "despesa");
	}

	public void novo(){
		despesa = new TipoDespTransp();
		controlaCadastro=1;
	}

	public void edita(){
		controlaCadastro=2;
	}

	public void exclui(){
		if(despesa.getId()==null){
			msg = "Nenhum registro selecionado para exclusão.";
			mensagens();			
		}else{
			gln = new GenericLN<TipoDespTransp>();
			msg = gln.update(despesa);
			mensagens();
			limpaCadastro();
			listar();
		}		
	}
	
	public void grava(){		
		if(validaCampos()){
			gln = new GenericLN<TipoDespTransp>();
			if(controlaCadastro==2)	
				msg = gln.update(despesa);
			if(controlaCadastro==1)
				msg = gln.add(despesa);		
			mensagens();
			listar();
			limpaCadastro();	
		}else{
			msg = "Favor preencha todos campos com *, pois são obrigatórios.";
			mensagens();
		}
	}
	
	private boolean validaCampos(){
		if(despesa.getDespesa()=="")
			return false;
		return true;		
	}
	
	public void limpaCadastro(){
		despesa = despesaSel = new TipoDespTransp();
		controlaCadastro=0;
	}

	public void selecao(){
		despesa = despesaSel;
		controlaCadastro=0;
		edita();
	}
	    
	public void mensagens(){
        FacesContext context = FacesContext.getCurrentInstance();  	          
        context.addMessage(null, new FacesMessage("",msg));  		
	}

	
	
/*==================/GETTERS E SETTERS/===========================*/
	public TipoDespTransp getDespesa() {
		return despesa;
	}

	public void setDespesa(TipoDespTransp despesa) {
		this.despesa = despesa;
	}

	public TipoDespTransp getDespesaSel() {
		return despesaSel;
	}

	public void setDespesaSel(TipoDespTransp despesaSel) {
		this.despesaSel = despesaSel;
	}

	public List<TipoDespTransp> getDespesas() {
		return despesas;
	}

	public void setDespesas(List<TipoDespTransp> despesas) {
		this.despesas = despesas;
	}

	public GenericLN<TipoDespTransp> getGln() {
		return gln;
	}

	public void setGln(GenericLN<TipoDespTransp> gln) {
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
	
}
