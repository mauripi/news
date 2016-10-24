package br.com.mauricio.news.filter;

import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpSession;

import br.com.mauricio.news.model.Login;

public class ControlaRecurso implements PhaseListener{

	private static final long serialVersionUID = -9034698044029884227L;
	public void afterPhase(PhaseEvent event) {

		FacesContext facesContext = event.getFacesContext();		
		String currentPage = facesContext.getViewRoot().getViewId();

		boolean isLoginPage = (currentPage.lastIndexOf("login.jsf") > -1);
		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
		Login currentUser = (Login) session.getAttribute("login");


		if (!isLoginPage && currentUser == null) {
		NavigationHandler nh = facesContext.getApplication().getNavigationHandler();
		nh.handleNavigation(facesContext, null, "loginPage");
		}
	}

	@Override
	public void beforePhase(PhaseEvent event) {	}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.RESTORE_VIEW;
	}

}
