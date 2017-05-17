package br.com.mauricio.news.dao.contabil;

import java.util.List;

import javax.persistence.EntityManager;

import br.com.mauricio.news.cnn.Conexao;
import br.com.mauricio.news.model.contabil.DocMovimentoBem;
import br.com.mauricio.news.model.contabil.MovimentoBem;

public class DocMovimentoBemDao {
	private EntityManager manager;
	
	public DocMovimentoBemDao(){
		Conexao c = new Conexao();
		this.manager = c.getEntityManager();
	}

	@SuppressWarnings("unchecked")
	public List<DocMovimentoBem> listarAnexos(MovimentoBem movimentobem) {
		return this.manager.createQuery(" from docmovimentobem d where d.movimentobem= :mb ").setParameter("mb", movimentobem).getResultList();		
	}
}
