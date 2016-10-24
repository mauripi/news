package br.com.mauricio.news.dao.rh;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.mauricio.news.cnn.Conexao;
import br.com.mauricio.news.model.rh.Informe;
import br.com.mauricio.news.model.Login;


/**
*
* @author MAURICIO CRUZ
*/
public class InformeDao{
	private EntityManager manager;
	
	public InformeDao(){
		Conexao c = new Conexao();
		this.manager = c.getEntityManager();
	}

	@SuppressWarnings("unchecked")
	public List<Informe> listaPorUsuario(Login l){
	    Query query = this.manager.createQuery("From informe b WHERE b.chapa= :chapa order by b.ano asc");
	    query.setParameter("chapa",l.getChapa());
		return query.getResultList();		
	}

}
