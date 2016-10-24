package br.com.mauricio.news.dao.marketing;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import br.com.mauricio.news.cnn.Conexao;
import br.com.mauricio.news.model.marketing.ExibicaoMidiaMais;
import br.com.mauricio.news.model.marketing.ProgramaMidiaMais;

public class MidiaMaisDao {
	private EntityManager manager;
	
	public MidiaMaisDao(){
		Conexao c = new Conexao();
		this.manager = c.getEntityManager();
	}
	
	public MidiaMaisDao(EntityManager manager){
		this.manager = manager;
	}
	
	public ProgramaMidiaMais buscaProgramaPorNome(String nome) { 
		ProgramaMidiaMais p = null;
	    try {   
	      Query query = this.manager.createQuery(" FROM programamidiamais p WHERE p.nome = :nome");   		     
	      query.setParameter("nome", nome);     		  
	      p = (ProgramaMidiaMais) query.getSingleResult();       
	   } catch (NoResultException e) {   
		   p = null;  
	   } catch (RuntimeException e) {
		   p = null;
	   }   		     
	   return p;   
	} 

	public ExibicaoMidiaMais buscaExibicaoPorProgramaData(ProgramaMidiaMais p, Date data, String material) { 
		ExibicaoMidiaMais exb =null;
		try {   
	      Query query = this.manager.createQuery(" FROM exibicaomidiamais e WHERE e.data = :data and e.programamidiamais= :p and e.material= :material");   		     
		  query.setParameter("data", data).setParameter("p", p).setParameter("material", material);
		  exb = (ExibicaoMidiaMais) query.getSingleResult();       
	   } catch (NoResultException e) {  
		   exb = null;   
	   } catch (RuntimeException e) {
		   exb = null;
	   }   		     
	   return exb;   
	} 

	public boolean existeDadosNoPeriodo(int mes, int ano) { 
		boolean b = false;
		try {   
	      Query query = this.manager.createQuery(" FROM exibicaomidiamais e WHERE year(e.data)= :ano AND MONTH(e.data)= :mes");   		     
		  query.setParameter("ano", ano).setParameter("mes", mes);
		  if(query.getResultList().size()>0)
			  b=true;
	   } catch (NoResultException e) {  
		   b = false;   
		   e.printStackTrace();
	   } catch (RuntimeException e) {
		   b = false;
		   e.printStackTrace();
	   }   		    
	   return b;   
	}

	public void apagaExibicoesDoBanco(List<ExibicaoMidiaMais> exibicoes) {

		for(ExibicaoMidiaMais e:exibicoes) {
			Query query = this.manager.createNativeQuery
					(" DELETE FROM exibicaomidiamais WHERE cliente= :cliente AND titulo= :titulo AND data= :data AND horario= :horario");
	        query.setParameter("cliente", e.getCliente()).setParameter("titulo", e.getTitulo())
	        	.setParameter("data", e.getData()).setParameter("horario", e.getHorario());		
			query.executeUpdate();	
		}
	}

	public void apagaPeriodoDoBanco(int mes, int ano) {
		Query query = this.manager.createNativeQuery(" DELETE FROM exibicaomidiamais WHERE year(data)= :ano AND MONTH(data)= :mes");
        query.setParameter("ano", ano).setParameter("mes", mes);		
		query.executeUpdate();
	} 
}
