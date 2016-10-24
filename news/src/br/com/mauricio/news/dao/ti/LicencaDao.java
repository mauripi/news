package br.com.mauricio.news.dao.ti;

import java.util.List;

import javax.persistence.EntityManager;

import br.com.mauricio.news.cnn.Conexao;
import br.com.mauricio.news.model.ti.Licenca;
/**
*
* @author MAURICIO CRUZ
*/
public class LicencaDao {
	private EntityManager manager;
	
	public LicencaDao(EntityManager manager){
		this.manager = manager;
	}
	public LicencaDao(){
		Conexao c = new Conexao();
		this.manager = c.getEntityManager();
	}
	@SuppressWarnings("unchecked")
	public List<Licenca> licencasDisponiveis(){		
		int q = 0;
		return this.manager.createQuery("From licenca l where l.qtdDisponivel > :q order by id")
				.setParameter("q", q)
				.getResultList();		
	}

}
