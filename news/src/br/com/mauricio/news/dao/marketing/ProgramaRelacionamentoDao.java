package br.com.mauricio.news.dao.marketing;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.mauricio.news.cnn.Conexao;
import br.com.mauricio.news.model.marketing.ProgramaRelacionamento;

public class ProgramaRelacionamentoDao implements Serializable{

	private static final long serialVersionUID = 1L;
	private EntityManager manager;
	
	public ProgramaRelacionamentoDao(EntityManager manager){
		this.manager = manager;
	}
	
	public ProgramaRelacionamentoDao(){
		Conexao c = new Conexao();
		this.manager = c.getEntityManager();
	}
		
	@SuppressWarnings("unchecked")
	public List<ProgramaRelacionamento> listNaoRelacionados() { 
	      Query query = this.manager.createQuery(" FROM programarelacionamento where programaibope is null");   		       		  
	      return query.getResultList();       
	} 

	
}
