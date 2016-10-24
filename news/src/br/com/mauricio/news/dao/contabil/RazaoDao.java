package br.com.mauricio.news.dao.contabil;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import org.joda.time.DateTime;

import br.com.mauricio.news.cnn.Conexao;
import br.com.mauricio.news.model.contabil.Razao;


/**
*
* @author MAURICIO CRUZ
*/
public class RazaoDao{
	private EntityManager manager;
	
	public RazaoDao(){
		Conexao c = new Conexao();
		this.manager = c.getEntityManager();
	}
	public RazaoDao(EntityManager manager){
		this.manager = manager;
	}

	
	@SuppressWarnings("unchecked")
	public List<Razao> findByContaMesAno(int conta, Integer mes, Integer ano) {
		DateTime d = new DateTime(ano,mes,8,0,0);
		Date d1=d.dayOfMonth().withMinimumValue().toDate();
		Date d2=d.dayOfMonth().withMaximumValue().toDate();
		return this.manager.createQuery(" from razao where data BETWEEN :startDate AND :endDate and clacta = :conta order by data")
					.setParameter("conta", conta).setParameter("startDate", d1).setParameter("endDate", d2).getResultList();		
	}
	
	public void deletePeriodo(Integer mes, Integer ano){
		manager.createNativeQuery("delete from razao where YEAR(data)= :ano and MONTH(data)= :mes ")
		.setParameter("ano", ano).setParameter("mes", mes).executeUpdate();
	}
}
