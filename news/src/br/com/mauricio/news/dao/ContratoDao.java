package br.com.mauricio.news.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;

import br.com.mauricio.news.model.Contrato;
import br.com.mauricio.news.util.DateUtil;

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
	
	public List<String> emailsCadastrados(){
		String sql = "SELECT emailsAviso FROM contrato";
		@SuppressWarnings("unchecked")
		List<String> list = this.manager.createNativeQuery(sql).getResultList();
		Set<String> emails = new HashSet<String>();
		for(String o:list){
			if(o!=null){
				String e[] = o.split(",");
				for(String x:e)
					if(x!=",") emails.add(x);				
			}
		}
		List<String> all = new ArrayList<String>(emails);
		return all;	
	}

	@SuppressWarnings("unchecked")
	public List<Contrato> listPrimeiroAviso() {
		Boolean ativo=true;
		String sql=" from contrato where DATEADD(DAY, -1*(diasAviso), fim) = :hoje and ativo= :ativo";
		Date hoje = DateUtil.hoje();
		return this.manager.createQuery(sql).setParameter("ativo", ativo).setParameter("hoje", hoje).getResultList();	
	}
}
