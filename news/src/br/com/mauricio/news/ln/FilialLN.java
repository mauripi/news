package br.com.mauricio.news.ln;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.mauricio.news.cnn.Conexao;
import br.com.mauricio.news.dao.FilialDao;
import br.com.mauricio.news.model.Filial;
/**
*
* @author MAURICIO CRUZ
*/
public class FilialLN implements Serializable {

	private static final long serialVersionUID = 1L;
	private EntityManager manager;
	
	public FilialLN(){
		Conexao c = new Conexao();
		this.manager = c.getEntityManager();
	}
		
	public List<Filial> list(){
		FilialDao dao = new FilialDao(manager);
		return dao.list();	
	}

	public EntityManager getManager() {
		return manager;
	}

	public void setManager(EntityManager manager) {
		this.manager = manager;
	}

}
