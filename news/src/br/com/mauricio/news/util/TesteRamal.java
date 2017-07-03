package br.com.mauricio.news.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import br.com.mauricio.news.model.DeptoRamal;
import br.com.mauricio.news.model.Ramal;

public class TesteRamal {
	public static void main(String[] args) {
	
		DeptoRamal depto1 = new DeptoRamal();
		depto1.setId(1);
		Ramal ramal1 = new Ramal();
		String nome ="Segundo o Professor Raphael Torres, do Cursinho QG do Enem, a definição de um tema passa por perspectivas políticas, sociais, científicas e culturais. Nos últimos anos, as propostas abarcavam questões atuais e presentes no cotidiano dos candidatos. É importante ressaltar que um fato pontual não se caracteriza como proposta comum da banca, mas alguma repercussão de acontecimentos. ";
		ramal1.setDepto(depto1);
		ramal1.setNome(nome);
		
		System.out.println("");
		System.out.println("::::: INICIO ::::");
		System.out.println("");
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("news");
		EntityManager manager = factory.createEntityManager();
		manager.getTransaction().begin();
		
		manager.persist(ramal1);
		
		manager.getTransaction().commit();
	    manager.close();
	    factory.close();	
	}
}
