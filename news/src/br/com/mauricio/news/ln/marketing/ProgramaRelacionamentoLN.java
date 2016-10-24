package br.com.mauricio.news.ln.marketing;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import br.com.mauricio.news.cnn.Conexao;
import br.com.mauricio.news.dao.marketing.ProgramaRelacionamentoDao;
import br.com.mauricio.news.model.marketing.ProgramaRelacionamento;


public class ProgramaRelacionamentoLN implements Serializable {

	private static final long serialVersionUID = 1L;
	private EntityManager manager;

	public ProgramaRelacionamentoLN(){
		Conexao c = new Conexao();
		manager = c.getEntityManager();
	}
	
	public ProgramaRelacionamentoLN(EntityManager manager){
		this.manager = manager;
	}


	public List<ProgramaRelacionamento> listNaoRelacionados() {
		ProgramaRelacionamentoDao dao = new ProgramaRelacionamentoDao(manager);
		return dao.listNaoRelacionados();
	}
	
	public ProgramaRelacionamento buscaProgramaPorNome(String nome) { 
		ProgramaRelacionamento p = null;
	    try {   
	      Query query = this.manager.createQuery(" FROM programamidiamais p WHERE p.nome = :nome");   		     
	      query.setParameter("nome", nome);     		  
	      p = (ProgramaRelacionamento) query.getSingleResult();       
	   } catch (NoResultException e) {   
		   p = null;  
	   } catch (RuntimeException e) {
		   p = null;
	   }   		     
	   return p;   
	} 
	public EntityManager getManager() {
		return manager;
	}

	public void setManager(EntityManager manager) {
		this.manager = manager;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
    
}
