package br.com.mauricio.news.dao.marketing;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import br.com.mauricio.news.cnn.Conexao;
import br.com.mauricio.news.model.marketing.LiveIbope;
import br.com.mauricio.news.model.marketing.ProgramaIbope;

public class IbopeDao {
	private EntityManager manager;
	
	public IbopeDao(EntityManager manager){
		this.manager = manager;
	}
	
	public IbopeDao(){
		Conexao c = new Conexao();
		this.manager = c.getEntityManager();
	}
		
	public ProgramaIbope buscaProgramaPorCodigo(Integer codigo) { 
		ProgramaIbope p = null;
	    try {   
	      Query query = this.manager.createQuery(" FROM programaibope p WHERE p.codigoibope = :codigo");   		     
	      query.setParameter("codigo", codigo);     		  
	      p = (ProgramaIbope) query.getSingleResult();       
	   } catch (NoResultException e) {   
		   p = null;  
	   } catch (RuntimeException e) {
		   p = null;
	   }   		     
	   return p;   
	} 

	public LiveIbope buscaLivePorProgramaDataHora(ProgramaIbope p, Date data, String inicio){ 
		LiveIbope live =null;
		try {   
	      Query query = this.manager.createQuery(" FROM liveibope l WHERE l.data = :data and l.programaibope= :p and l.inicio= :inicio");   		     
		  query.setParameter("data", data).setParameter("p", p).setParameter("inicio", inicio);
		  live = (LiveIbope) query.getSingleResult();       
	   } catch (NoResultException e) {  
		   live = null; 
		   e.getLocalizedMessage();
	   } catch (RuntimeException e) {
		   e.getLocalizedMessage();
		   live = null;
	   }   		     
	   return live;   
	} 
}
