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
		EntityManager manager;
		EntityManagerFactory factory;
		FacesContext fc = FacesContext.getCurrentInstance();
		ExternalContext ec = fc.getExternalContext();
		HttpServletRequest request = (HttpServletRequest) ec.getRequest();
		if(request!=null){
			manager = (EntityManager) request.getAttribute("EntityManager");
		}else{
			factory = Persistence.createEntityManagerFactory("news");
			manager = factory.createEntityManager();
		}
		
		if(!manager.isOpen()){
			factory = Persistence.createEntityManagerFactory("news");
			manager = factory.createEntityManager();
		}			
		return manager;		
	}
}
