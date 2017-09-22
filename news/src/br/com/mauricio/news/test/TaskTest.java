package br.com.mauricio.news.test;

import java.sql.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import br.com.mauricio.news.model.Task;

public class TaskTest {
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		
		System.out.println("");
		System.out.println("::::: popular tabela -- inicio ::::");
		System.out.println("");
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("news");
		EntityManager manager = factory.createEntityManager();
		
		manager.getTransaction().begin();
				
		Task t1 = new Task();		
		t1.setTitulo("Projeto dos Projetos");
		t1.setInicio(new Date(2017,1,1));
		t1.setFim(new Date(2017,12,31));
		t1.setPretask(null);
		

		manager.persist(t1);
		
		Task t2 = new Task();		
		t2.setTitulo("Gestão do Projeto");
		t2.setInicio(new Date(2017,1,1));
		t2.setFim(new Date(2017,12,31));
		t2.setPretask(t1);
		
		
		manager.persist(t2);

		Task t3 = new Task();		
		t3.setTitulo("Desenvolvimento");
		t3.setInicio(new Date(2017,1,1));
		t3.setFim(new Date(2017,12,31));
		t3.setPretask(t1);
		
		manager.persist(t3);
		
		Task t4 = new Task();		
		t4.setTitulo("Gestão de Escopo");
		t4.setInicio(new Date(2017,1,1));
		t4.setFim(new Date(2017,12,31));
		t4.setPretask(t2);
		
		manager.persist(t4);
		
		Task t5 = new Task();		
		t5.setTitulo("Gestão de Pessoas");
		t5.setInicio(new Date(2017,1,1));
		t5.setFim(new Date(2017,12,31));
		t5.setPretask(t2);	
		
		manager.persist(t5);

		Task t6 = new Task();		
		t6.setTitulo("Levantamento de Requisitos");
		t6.setInicio(new Date(2017,1,1));
		t6.setFim(new Date(2017,12,31));
		t6.setPretask(t3);
		
		manager.persist(t6);
		
		Task t7 = new Task();		
		t7.setTitulo("Testes");
		t7.setInicio(new Date(2017,1,1));
		t7.setFim(new Date(2017,12,31));
		t7.setPretask(t3);
		
		manager.persist(t7);
		
		Task t8 = new Task();		
		t8.setTitulo("Produção");
		t8.setInicio(new Date(2017,1,1));
		t8.setFim(new Date(2017,12,31));
		t8.setPretask(t3);

		manager.persist(t8);

		
		montaListaProjetoDosProjetosCaroline(t1);
		manager.getTransaction().commit();
		
        manager.close();
        factory.close();
        
	}
	
	private static void montaListaProjetoDosProjetosCaroline(Task t){
		System.out.printf("%s %s \n",t.getId(), t.getTitulo());
			
		for(Task x : t.getTasks())
			montaListaProjetoDosProjetosCaroline(x);
			
	}
}
