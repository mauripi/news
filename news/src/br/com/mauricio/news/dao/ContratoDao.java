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
	
	
	/*
	 	StringBuilder sb = new StringBuilder();
		sb.append("SELECT ");
		sb.append(" c.nome,r.datareuniao,");
		sb.append("count(m.idmembro),");
		sb.append(" SUM(case when rm.estevepresente=1 Then 1 Else 0 End),");
		sb.append(" SUM(case when rm.estevepresente=0 Then 1 Else 0 End)");
		sb.append(" FROM reuniao_membro rm");
		sb.append(" join rm.reuniao r");
		sb.append(" join rm.membro m");
		sb.append(" join m.celula c");
		sb.append(" WHERE");
		sb.append(" r.datareuniao between :inicio and :fim");
		sb.append(" group by c.idcelula,r.idreuniao");

	 */
	public void delete(Contrato c){	
		//this.manager.createNativeQuery(" delete from doccontrato where contrato_id= :id").setParameter("id", c.getId()).executeUpdate();		
		//this.manager.createNativeQuery(" delete from contrato where id= :id").setParameter("id", c.getId()).executeUpdate();		
	}
}
