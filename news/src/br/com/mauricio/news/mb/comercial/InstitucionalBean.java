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
import br.com.mauricio.news.model.comercial.Institucional;

/**
*
* @author MAURICIO CRUZ and CAROLINE MARQUES
*/

@ManagedBean(name="institucionalMB")
@ViewScoped
public class InstitucionalBean implements Serializable{

	private static final long serialVersionUID = 1L;
	private Institucional institucional;
	private List<Institucional> listAll;
	private String url = "";
	
	
	@PostConstruct
	public void inicio(){
		listar();
	}
	
	public void novo(){
		institucional = new Institucional();		
	}
	
	public void listar(){
		GenericLN<Institucional> gln = new GenericLN<Institucional>();
		listAll = gln.listWithoutRemoved("institucional", "id desc");
	}
	
	public void grava(){
		GenericLN<Institucional> gln = new GenericLN<Institucional>();
		for (Institucional i : listAll){
			i.setStatus(false);
			gln.update(i);
		}
		
		String msg;
		msg = gln.add(institucional);
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("",msg));
		listar();
	}
	
	public void selecao(SelectEvent event){
		institucional=(Institucional) event.getObject();
		buscaUrl();
	}
	
	public void limpaCadastro(){
		institucional = new Institucional();
		url ="";
	}

	public void buscaUrl(){
		String oldUrl = institucional.getUrl();
		String [] partes = oldUrl.split("=");
		String id = partes[partes.length-1];
		url = "https://www.youtube.com/embed/"+id;
	}
	
	
	
	
	public Institucional getInstitucional() {
		return institucional;
	}

	public void setInstitucional(Institucional institucional) {
		this.institucional = institucional;
	}

	public List<Institucional> getListAll() {
		return listAll;
	}

	public void setListAll(List<Institucional> listAll) {
		this.listAll = listAll;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
}
