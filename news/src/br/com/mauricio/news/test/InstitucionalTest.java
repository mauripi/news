package br.com.mauricio.news.test;

import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import br.com.mauricio.news.model.comercial.LinkProgramacao;
import br.com.mauricio.news.model.comercial.ProgramacaoComercial;

public class InstitucionalTest {
	public static void main(String[] args) {
		
		System.out.println("");
		System.out.println("::::: popular tabela -- inicio ::::");
		System.out.println("");
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("news");
		EntityManager manager = factory.createEntityManager();
		
		manager.getTransaction().begin();
				
		ProgramacaoComercial p = new ProgramacaoComercial();
		p.setDescricao("Esportes");
		p.setLinks(new ArrayList<LinkProgramacao>());
		

		LinkProgramacao l1 = new LinkProgramacao();		
		l1.setProgramacao(p);
		l1.setTitulo("5º Tempo");
		l1.setUrl("http://esportes.r7.com/michael-phelps-perde-corrida-para-tubarao-branco-24072017");
		l1.setPathimg("https://img.r7.com/images/2014/09/30/9jt7v5sv9p_6zszsuhurz_file?dimensions=460x305");		
		manager.persist(l1);		

		LinkProgramacao l2 = new LinkProgramacao();		
		l2.setProgramacao(p);
		l2.setTitulo("5º Tempo");
		l2.setUrl("http://esportes.r7.com/waldir-peres-morre-aos-66-anos-23072017");
		l2.setPathimg("https://img.r7.com/images/2013/05/15/14_36_08_728_file?dimensions=460x305");		
		manager.persist(l2);
		
		
		p.getLinks().add(l1);
		p.getLinks().add(l2);
		manager.persist(p);
		manager.getTransaction().commit();
		
        manager.close();
        factory.close();
        
	}
	
}
