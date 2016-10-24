package br.com.mauricio.news.dao;

import java.util.List;

import javax.persistence.EntityManager;

import br.com.mauricio.news.model.Filial;
/**
*
* @author MAURICIO CRUZ
*/
public class FilialDao {
	private EntityManager manager;
	
	public FilialDao(EntityManager manager){
		this.manager = manager;
	}
	
	@SuppressWarnings("unchecked")
	public List<Filial> list(){		
		return this.manager.createQuery(" From filial")
				.getResultList();
	}

}
