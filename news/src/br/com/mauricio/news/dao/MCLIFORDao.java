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
			sql = " FROM mclifor where nomfan like :dado ";
		return manager.createQuery(sql).setParameter("dado", "%"+dado+"%").getResultList();
	}
	
	public MCLIFOR findByNomeCnpj(String razao, Long cgccpf){
		MCLIFOR cf;
		String sql = " FROM mclifor where cgccpf= :cgccpf and nomfan like :razao  ";	
		cf = (MCLIFOR) manager.createQuery(sql).setParameter("razao", "%"+razao+"%").setParameter("cgccpf", cgccpf).getSingleResult();
		return cf;		
	}
	
	public EntityManager getManager() {
		return manager;
	}

	public void setManager(EntityManager manager) {
		this.manager = manager;
	}

}
