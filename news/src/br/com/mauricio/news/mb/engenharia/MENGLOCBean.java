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
import br.com.mauricio.news.model.engenharia.MENGLOC;
/**
*
* @author MAURICIO CRUZ
*/
@ManagedBean(name="menglocMB")
@ViewScoped
public class MENGLOCBean  implements Serializable {

	private static final long serialVersionUID = 1L;
	private MENGLOC mengloc;
	private MENGLOC menglocSel;
	private List<MENGLOC> menglocs = new ArrayList<MENGLOC>();
	private GenericLN<MENGLOC> gln;
	private int controlaCadastro = 0;
	private String msg;
	
	@PostConstruct
	public void init(){
		mengloc = new MENGLOC();
		listar();		
	}

	public void listar(){
		gln = new GenericLN<MENGLOC>();
		menglocs = gln.listWithoutRemoved("mengloc", "nome");
	}

	public void novo(){
		mengloc = new MENGLOC();
		controlaCadastro=1;
	}

	public void edita(){
		controlaCadastro=2;
	}

	public void exclui(){
		if(mengloc.getId()==null){
			msg = "Nenhum registro selecionado para exclusão.";
			mensagens();			
		}else{
			gln = new GenericLN<MENGLOC>();
			msg = gln.update(mengloc);
			mensagens();
			limpaCadastro();
			listar();
		}		
	}
	
	public void grava(){		
		if(validaCampos()){
			gln = new GenericLN<MENGLOC>();
			if(controlaCadastro==2)	
				msg = gln.update(mengloc);
			if(controlaCadastro==1)
				msg = gln.add(mengloc);		
			mensagens();
			listar();
			limpaCadastro();	
		}else{
			msg = "Favor preencha todos campos com *, pois são obrigatórios.";
			mensagens();
		}
	}
	
	private boolean validaCampos(){
		if(mengloc.getNome()=="")
			return false;
		return true;		
	}
	
	public void limpaCadastro(){
		mengloc = menglocSel = new MENGLOC();
		controlaCadastro=0;
	}

	public void selecao(){
		mengloc = menglocSel;
		controlaCadastro=0;
		edita();
	}
	    
	public void mensagens(){
        FacesContext context = FacesContext.getCurrentInstance();  	          
        context.addMessage(null, new FacesMessage("",msg));  		
	}
/*==================/GETTERS E SETTERS/===========================*/

	public MENGLOC getMengloc() {
		return mengloc;
	}

	public void setMengloc(MENGLOC mengloc) {
		this.mengloc = mengloc;
	}

	public MENGLOC getMenglocSel() {
		return menglocSel;
	}

	public void setMenglocSel(MENGLOC menglocSel) {
		this.menglocSel = menglocSel;
	}

	public List<MENGLOC> getMenglocs() {
		return menglocs;
	}

	public void setMenglocs(List<MENGLOC> menglocs) {
		this.menglocs = menglocs;
	}

	public GenericLN<MENGLOC> getGln() {
		return gln;
	}

	public void setGln(GenericLN<MENGLOC> gln) {
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
