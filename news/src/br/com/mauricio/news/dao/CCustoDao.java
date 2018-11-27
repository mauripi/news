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
		return this.manager.createQuery("From ccusto where codigo not in ('30008','30009','30015','30016','30045','30046','30052','30053','30089','30090','30082','30083') order by filial,nome").getResultList();		
	}

}
