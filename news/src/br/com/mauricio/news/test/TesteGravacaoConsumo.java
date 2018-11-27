package br.com.mauricio.news.test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import br.com.mauricio.news.ln.ti.ConsumoEmbratelLN;

public class TesteGravacaoConsumo {

	public static void main(String[] args) {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("news");
		EntityManager manager = factory.createEntityManager();
		String arquivo = "C:\\FAT_181121000124.txt";
		String fatura = "181121000124";
		
		manager.getTransaction().begin();
		
		ConsumoEmbratelLN ln = new ConsumoEmbratelLN(manager);
		//ln.carregaArquivo(arquivo, fatura);
		//ln.rateio(fatura).forEach((x,y)->System.out.println(x + " - " + y));
		System.out.println(ln.faturaJaExiste(fatura));
		
		manager.getTransaction().commit();
		
        manager.close();
        factory.close();

	}

}
