package br.com.mauricio.news.dao.contabil;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import br.com.mauricio.news.model.contabil.CustoContabil;

public class CustoContabilDao implements Serializable{

	private static final long serialVersionUID = 1L;
	private EntityManagerFactory factory;
	private EntityManager manager;

	private EntityManager abrirConexao(){
		System.out.println("Abrindo conexão ......  CustoContabil");
		this.factory = Persistence.createEntityManagerFactory("sapiens_prod");
		this.manager = this.factory.createEntityManager();
		this.manager.getTransaction().begin();
		return this.manager;
	}
	
	private void fechaConexao(){
		this.manager.close();
		this.factory.close();	
		System.out.println("Fechando conexão ......  CustoContabil");
	}	
	
	public List<CustoContabil> buscarERP(Integer ano){
		abrirConexao();
		String sql ="exec rateio_contabil_ERP_SIG :ano ";
		
		List<CustoContabil> custos = new ArrayList<CustoContabil>();
		@SuppressWarnings("unchecked")
		List<Object[]> list = manager.createNativeQuery(sql).setParameter("ano", ano).getResultList();
		fechaConexao();
		
		for(Object[] o:list){
			CustoContabil c = new CustoContabil();
			c.setClacta(Integer.parseInt(o[0].toString()));
			c.setCtared(Integer.parseInt(o[1].toString()));
			c.setCtasig(Integer.parseInt(o[2].toString()));
			c.setDescta(o[3].toString());
			c.setCodccu(Integer.parseInt(o[4].toString()));
			c.setDesccu(o[5].toString());
			c.setDebcre(o[6].toString());
			c.setCcusig(o[7].toString());
			if(o[8]!=null)
				c.setJan(new BigDecimal(o[8].toString()));
			if(o[9]!=null)
				c.setFev(new BigDecimal(o[9].toString()));
			if(o[10]!=null)
				c.setMar(new BigDecimal(o[10].toString()));
			if(o[11]!=null)
				c.setAbr(new BigDecimal(o[11].toString()));
			if(o[12]!=null)
				c.setMai(new BigDecimal(o[12].toString()));
			if(o[13]!=null)
				c.setJun(new BigDecimal(o[13].toString()));
			if(o[14]!=null)
				c.setJul(new BigDecimal(o[14].toString()));
			if(o[15]!=null)
				c.setAgo(new BigDecimal(o[15].toString()));
			if(o[16]!=null)
				c.setSet(new BigDecimal(o[16].toString()));
			if(o[17]!=null)
				c.setOut(new BigDecimal(o[17].toString()));
			if(o[18]!=null)
				c.setNov(new BigDecimal(o[18].toString()));
			if(o[19]!=null)
				c.setDec(new BigDecimal(o[19].toString()));
			
			custos.add(c);
		}
		return custos;
	}
	
}
