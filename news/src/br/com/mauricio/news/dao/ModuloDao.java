package br.com.mauricio.news.dao;

import java.util.List;

import javax.persistence.EntityManager;
import br.com.mauricio.news.cnn.Conexao;
import br.com.mauricio.news.model.Modulo;
/**
*
* @author MAURICIO CRUZ
*/
public class ModuloDao {
	private EntityManager manager;
	
	public ModuloDao(EntityManager manager){
		this.manager = manager;
	}
	public ModuloDao(){
		Conexao c = new Conexao();
		this.manager = c.getEntityManager();
	}
	@SuppressWarnings("unchecked")
	public List<Modulo> listaModulo(){		
		return this.manager.createQuery("From modulo order by id").getResultList();		
	}

}
