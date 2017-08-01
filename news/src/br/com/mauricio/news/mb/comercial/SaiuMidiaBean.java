package br.com.mauricio.news.mb.comercial;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.SelectEvent;

import br.com.mauricio.news.ln.GenericLN;
import br.com.mauricio.news.model.comercial.SaiuMidia;

/**
*
* @author MAURICIO CRUZ and CAROLINE MARQUES
*/

@ManagedBean(name="saiumidiaMB")
@ViewScoped
public class SaiuMidiaBean implements Serializable{

	private static final long serialVersionUID = 1L;
	private SaiuMidia saiumidia;
	private List<SaiuMidia> listAll;
	
	
	@PostConstruct
	public void inicio(){
		listar();
	}
	
	public void novo(){
		saiumidia = new SaiuMidia();		
	}
	
	public void listar(){
		GenericLN<SaiuMidia> gln = new GenericLN<SaiuMidia>();
		listAll = gln.listWithoutRemoved("saiumidia", "data asc");
	}
	
	public void grava(){
		GenericLN<SaiuMidia> gln = new GenericLN<SaiuMidia>();
		String msg;
		if(saiumidia.getId() != null){
			msg = gln.update(saiumidia);
		}else{	
			msg = gln.add(saiumidia);
		}
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("",msg));
		limpaCadastro();
		listar();
	}
	
	public void selecao(SelectEvent event){
		saiumidia=(SaiuMidia) event.getObject();
	}
	
	public void limpaCadastro(){
		saiumidia = new SaiuMidia();
	}

	public SaiuMidia getSaiumidia() {
		return saiumidia;
	}

	public void setSaiumidia(SaiuMidia saiumidia) {
		this.saiumidia = saiumidia;
	}

	public List<SaiuMidia> getListAll() {
		return listAll;
	}

	public void setListAll(List<SaiuMidia> listAll) {
		this.listAll = listAll;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
