package br.com.mauricio.news.dao;

import java.util.List;

import javax.persistence.EntityManager;

import br.com.mauricio.news.cnn.Conexao;
import br.com.mauricio.news.model.CCusto;
/**
*
* @author MAURICIO CRUZ
*/
public class CCustoDao {
	private EntityManager manager;
	
	public CCustoDao(EntityManager manager){
		this.manager = manager;
	}
	public CCustoDao(){
		Conexao c = new Conexao();
		this.manager = c.getEntityManager();
	}
	@SuppressWarnings("unchecked")
	public List<CCusto> list(){		
		return this.manager.createQuery("From ccusto order by id").getResultList();		
	}

}
