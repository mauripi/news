package br.com.mauricio.news.dao;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import br.com.mauricio.news.model.MCLIFOR;

public class MCLIFORDao {
	private EntityManager manager;

	public MCLIFORDao(EntityManager manager) {
		this.manager = manager;
	}

	@SuppressWarnings("unchecked")
	public List<MCLIFOR> getCliFor(String dado, String filtro) throws IOException{
		String sql="";
		if(filtro=="1")
			sql = " FROM mclifor where cgccpf= :dado ";		
		if(filtro=="2")
			sql = " FROM mclifor where apecli like :dado ";
		return manager.createNativeQuery(sql).setParameter("dado", "%"+dado+"%").getResultList();
	}
	
	public EntityManager getManager() {
		return manager;
	}

	public void setManager(EntityManager manager) {
		this.manager = manager;
	}

}
