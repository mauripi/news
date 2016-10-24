package br.com.mauricio.news.mb.engenharia;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.com.mauricio.news.ln.GenericLN;
import br.com.mauricio.news.model.engenharia.Praca;
/**
*
* @author MAURICIO CRUZ
*/
@ManagedBean(name="pracaMB")
@ViewScoped
public class PracaBean  implements Serializable {

	private static final long serialVersionUID = 1L;
	private Praca praca;
	private Praca pracaSel;
	private List<Praca> pracas = new ArrayList<Praca>();
	private GenericLN<Praca> gln;
	private int controlaCadastro = 0;
	private String msg;
	
	@PostConstruct
	public void init(){
		praca = new Praca();
		listar();		
	}

	public void listar(){
		gln = new GenericLN<Praca>();
		pracas = gln.listWithoutRemoved("praca", "nome");
	}

	public void novo(){
		praca = new Praca();
		controlaCadastro=1;
	}

	public void edita(){
		controlaCadastro=2;
	}

	public void exclui(){
		if(praca.getId()==null){
			msg = "Nenhum registro selecionado para exclusão.";
			mensagens();			
		}else{
			gln = new GenericLN<Praca>();
			msg = gln.update(praca);
			mensagens();
			limpaCadastro();
			listar();
		}		
	}
	
	public void grava(){		
		if(validaCampos()){
			gln = new GenericLN<Praca>();
			if(controlaCadastro==2)	
				msg = gln.update(praca);
			if(controlaCadastro==1)
				msg = gln.add(praca);		
			mensagens();
			listar();
			limpaCadastro();	
		}else{
			msg = "Favor preencha todos campos com *, pois são obrigatórios.";
			mensagens();
		}
	}
	
	private boolean validaCampos(){
		if(praca.getNome()=="")
			return false;
		return true;		
	}
	
	public void limpaCadastro(){
		praca = pracaSel = new Praca();
		controlaCadastro=0;
	}

	public void selecao(){
		praca = pracaSel;
		controlaCadastro=0;
		edita();
	}
	    
	public void mensagens(){
        FacesContext context = FacesContext.getCurrentInstance();  	          
        context.addMessage(null, new FacesMessage("",msg));  		
	}

	
	
/*==================/GETTERS E SETTERS/===========================*/

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

	public Praca getPraca() {
		return praca;
	}

	public void setPraca(Praca praca) {
		this.praca = praca;
	}

	public Praca getPracaSel() {
		return pracaSel;
	}

	public void setPracaSel(Praca pracaSel) {
		this.pracaSel = pracaSel;
	}

	public List<Praca> getPracas() {
		return pracas;
	}

	public void setPracas(List<Praca> pracas) {
		this.pracas = pracas;
	}

	public GenericLN<Praca> getGln() {
		return gln;
	}

	public void setGln(GenericLN<Praca> gln) {
		this.gln = gln;
	}
	
}
