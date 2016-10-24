package br.com.mauricio.news.Teste;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import br.com.mauricio.news.ln.contabil.ConsolidadoLN;

public class Teste {
	
	private static EntityManagerFactory factory;
	private static EntityManager manager;
	
	public static void main(String[] args) {
		factory = Persistence.createEntityManagerFactory("news");
		manager = factory.createEntityManager();
		manager.getTransaction().begin();

		ConsolidadoLN ln = new ConsolidadoLN(manager);
		ln.atualizaPlano();
		
		manager.getTransaction().commit();
		factory.close();
		manager.close();
	}

}
