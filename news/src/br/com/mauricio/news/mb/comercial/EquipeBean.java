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
import br.com.mauricio.news.model.comercial.Equipe;

/**
*
* @author MAURICIO CRUZ and CAROLINE MARQUES
*/

@ManagedBean(name="equipeMB")
@ViewScoped
public class EquipeBean implements Serializable{

	private static final long serialVersionUID = 1L;
	private Equipe equipe;
	private List<Equipe> listAll;
	
	
	@PostConstruct
	public void inicio(){
		listar();
	}
	
	public void novo(){
		equipe = new Equipe();		
	}
	
	public void listar(){
		GenericLN<Equipe> gln = new GenericLN<Equipe>();
		listAll = gln.listWithoutRemoved("equipe", "id desc");
	}
	
	public void grava(){
		GenericLN<Equipe> gln = new GenericLN<Equipe>();
		String msg;
		if(equipe.getId() != null){
			msg = gln.update(equipe);
		}else{	
			msg = gln.add(equipe);
		}
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("",msg));
		limpaCadastro();
		listar();
	}
	
	public void selecao(SelectEvent event){
		equipe=(Equipe) event.getObject();
	}
	
	public void limpaCadastro(){
		equipe = new Equipe();
	}

	public Equipe getEquipe() {
		return equipe;
	}

	public void setEquipe(Equipe equipe) {
		this.equipe = equipe;
	}

	public List<Equipe> getListAll() {
		return listAll;
	}

	public void setListAll(List<Equipe> listAll) {
		this.listAll = listAll;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
