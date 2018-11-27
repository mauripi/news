package br.com.mauricio.news.dao.ti;

import java.io.Serializable;

import javax.persistence.EntityManager;
import br.com.mauricio.news.cnn.Conexao;
import br.com.mauricio.news.model.Ramal;

public class RamalDao implements Serializable {

	private static final long serialVersionUID = 1L;
	private EntityManager manager;
	
	public RamalDao(EntityManager manager){
		this.manager = manager;
	}
	public RamalDao(){
		Conexao c = new Conexao();
		this.manager = c.getEntityManager();
	}
	
	public void excluir(Ramal ramal) {
		manager.createNativeQuery("delete from ramal where id= :id").setParameter("id", ramal.getId()).executeUpdate();
	}
	
}
