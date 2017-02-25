package br.com.mauricio.news.mb.rh;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import br.com.mauricio.news.ln.rh.InformeLN;
import br.com.mauricio.news.model.Login;
import br.com.mauricio.news.model.rh.Informe;

/*
* @author MAURICIO CRUZ
*/

@ManagedBean(name="informeBean")
@ViewScoped
public class InformeBean implements Serializable{

	private static final long serialVersionUID = -1720029126855499058L;
	private String msg;
	private Login usuarioLogado = new Login();
	private Informe irrf;
	private List<Informe> informes;

	
	public InformeBean(){
		FacesContext cx = FacesContext.getCurrentInstance();
        HttpSession sessao = (HttpSession) cx.getExternalContext().getSession(false);		
		this.usuarioLogado = (Login) sessao.getAttribute("login");
	}
	

	public void listarInformes(){
		InformeLN ln = new InformeLN();
		informes = new ArrayList<Informe>();
		informes.addAll(ln.getLista(usuarioLogado));
	}
	
	public void mensagens(){
        FacesContext context = FacesContext.getCurrentInstance();  	          
        context.addMessage(null, new FacesMessage("",msg));  		
	}
 	/*=============GETTERS E SETTERS====================*/

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Login getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(Login usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

	public Informe getIrrf() {
		return irrf;
	}

	public void setIrrf(Informe irrf) {
		this.irrf = irrf;
	}

	public List<Informe> getInformes() {
		return informes;
	}

	public void setInformes(List<Informe> informes) {
		this.informes = informes;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
