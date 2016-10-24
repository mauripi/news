package br.com.mauricio.news.filter;

import java.io.IOException;

import javax.faces.application.ViewExpiredException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.hibernate.SessionException;


public class JPAFilter implements Filter {
	
	private EntityManagerFactory factory;
	private EntityManager manager;
	
	public void init(FilterConfig filterConfig) throws ServletException {
		this.factory = Persistence.createEntityManagerFactory("news");
	}
	
	@Override
	public void destroy() {
		this.factory.close();
		manager.close();
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {
		manager = this.factory.createEntityManager();
		request.setAttribute("EntityManager", manager);
		manager.getTransaction().begin();
		try {
			chain.doFilter(request, response);
			manager.getTransaction().commit();

		} catch (IOException e) {
			//System.out.println(e.getLocalizedMessage());
		} catch (ServletException e) {
			//System.out.println(e.getLocalizedMessage());
		} catch (IllegalStateException e){
			//System.out.println(e.getLocalizedMessage());
		} catch (PersistenceException e){
			//System.out.println(e.getLocalizedMessage() + e.getCause());
		} catch (SessionException e){
			//System.out.println(e.getLocalizedMessage() + e.getCause());
		} catch (ViewExpiredException e){
			//System.out.println(e.getLocalizedMessage() + e.getCause());
		}		
	}
}
