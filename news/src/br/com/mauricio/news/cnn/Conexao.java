package br.com.mauricio.news.cnn;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.http.HttpServletRequest;
/**
*
* @author MAURICIO CRUZ
*/
public class Conexao {

	public EntityManager getEntityManager(){
		FacesContext fc = FacesContext.getCurrentInstance();
		ExternalContext ec = fc.getExternalContext();
		HttpServletRequest request = (HttpServletRequest) ec.getRequest();
		EntityManager manager = (EntityManager) request.getAttribute("EntityManager");
		if(!manager.isOpen()){
			EntityManagerFactory factory = Persistence.createEntityManagerFactory("news");
			manager = factory.createEntityManager();
		}			
		return manager;		
	}
}
