package br.com.mauricio.news.mb;

import java.io.IOException;

import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@ManagedBean
public class SairBean {
	private HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest(); 
	private HttpSession session = request.getSession(true);
	
	
	public void sair() throws IOException{
		//invalida a sessao
		 session.invalidate();
		 FacesContext.getCurrentInstance().getExternalContext().redirect(request.getContextPath()+"/login.jsf");
	}

}
