package br.com.mauricio.news.dao.ti;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import br.com.mauricio.news.cnn.Conexao;
import br.com.mauricio.news.model.Login;
import br.com.mauricio.news.model.ti.Atendente;
/**
*
* @author MAURICIO CRUZ
*/
public class AtendenteDao {
	private EntityManager manager;
	
	public AtendenteDao(EntityManager manager){
		this.manager = manager;
	}
	public AtendenteDao(){
		Conexao c = new Conexao();
		this.manager = c.getEntityManager();
	}

	public Atendente findyByLogin(Login l){		
		try{
		return (Atendente) this.manager.createQuery(" FROM atendente WHERE login= :l").setParameter("l", l).getSingleResult();
		}catch(PersistenceException e){
			return new Atendente();
		}
	}


}
