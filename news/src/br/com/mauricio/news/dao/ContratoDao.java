package br.com.mauricio.news.dao;

import java.util.List;

import javax.persistence.EntityManager;

import br.com.mauricio.news.model.Contrato;

public class ContratoDao {
	private EntityManager manager;

	public ContratoDao(EntityManager manager){
		this.manager = manager;
	}
		
	@SuppressWarnings("unchecked")
	public List<Contrato> listaContratosAtivos() {
		Boolean ativo=true;
		return this.manager.createQuery(" from contrato where ativo= :ativo order by id desc").setParameter("ativo", ativo).getResultList();	
	}

	@SuppressWarnings("unchecked")
	public List<Contrato> listaContratosByCCusto(List<String> custos) {
		Boolean ativo=true;
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT c.* ");
		sb.append(" FROM contrato c");
		sb.append(" join c. r");
		sb.append(" join c.responsavel r");
		sb.append(" join r.custo cc");
		sb.append(" WHERE");
		sb.append(" c.ativo= :ativo ");
		sb.append(" cc.codigo in ( :custos ) ");
		return this.manager.createQuery(" from contrato where ativo= :ativo order by id desc").setParameter("ativo", ativo).getResultList();	
	}
	
	
	public void delete(Contrato c){	
		//this.manager.createNativeQuery(" delete from doccontrato where contrato_id= :id").setParameter("id", c.getId()).executeUpdate();		
		//this.manager.createNativeQuery(" delete from contrato where id= :id").setParameter("id", c.getId()).executeUpdate();		
	}
	
	
}
