package br.com.mauricio.news.dao.financeiro;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import br.com.mauricio.news.model.financeiro.Previsto;
import br.com.mauricio.news.model.financeiro.Realizado;


public class ProjetadoRealizadoDao {
	private EntityManagerFactory factory;
	private EntityManager manager;//
	private List<Previsto> previstos = new ArrayList<Previsto>();
	private List<Realizado> realizados = new ArrayList<Realizado>();
	//sapiens_homo
	//sapiens_prod
	private EntityManager abrirConexao(){
		this.factory = Persistence.createEntityManagerFactory("sapiens_prod");
		this.manager = this.factory.createEntityManager();
		this.manager.getTransaction().begin();
		return this.manager;
	}
	
	private void fechaConexao(){
		this.manager.close();
		this.factory.close();		
	}	
	
	public List<Previsto> buscarPrevisto(Date d1,Date d2){
		abrirConexao();
		String sql = "select numtit,data,sum(valor) as valor,apecli,obstcr,ctared,ctafin,codtns from USU_VNEWFLUXO where status='PROJETADO' "
				+ "	and data BETWEEN :d1 and :d2 group by numtit,data,apecli,obstcr,ctared,ctafin,codtns ORDER BY data";				
		
		@SuppressWarnings("unchecked")
		List<Object[]> list = manager.createNativeQuery(sql).setParameter("d1", d1).setParameter("d2", d2).getResultList();

		for(Object[] o : list){
			Previsto pre = new Previsto();
								
			pre.setNumtit(o[0].toString());
			pre.setVctpro((Date) o[1]);
			pre.setVlrabe(new BigDecimal(o[2].toString()));
			pre.setNomclifor(o[3].toString());
			pre.setObstit(o[4].toString());
			if(o[5] != null) pre.setCtared(Integer.parseInt(o[5].toString()));					
			previstos.add(pre);
		}				
		fechaConexao();		
		System.out.println("========== ProjetadoRealizadoDao.buscarPrevisto gerado com "+ previstos.size()+" registros ===============");
		return previstos;
	}
	
	public List<Realizado> buscarRealizado(Date d1,Date d2){
		abrirConexao();
		String sql = "select numtit,data,sum(valor) as valor,apecli,obstcr,ctared,ctafin,codtns from USU_VNEWFLUXO where status='REALIZADO' "
				+ " and data BETWEEN :d1 and :d2 group by numtit,data,apecli,obstcr,ctared,ctafin,codtns ORDER BY data";						
		@SuppressWarnings("unchecked")
		List<Object[]> list = manager.createNativeQuery(sql).setParameter("d1", d1).setParameter("d2", d2).getResultList();

		for(Object[] o : list){
			Realizado r = new Realizado();
			r.setNumtit(o[0].toString());
			r.setDatmov((Date) o[1]);
			r.setVlrliq(new BigDecimal(o[2].toString()));
			r.setNomclifor(o[3].toString());
			r.setObstit(o[4].toString());
			if(o[5] != null) r.setCtared(Integer.parseInt(o[5].toString()));		
				
			realizados.add(r);
		}			
		fechaConexao();		
		System.out.println("========== ProjetadoRealizadoDao.buscarRealizado gerado com "+ realizados.size()+" registros ===============");
		return realizados;
	}

	public Map<Integer,String> buscarClassificacao(){
		abrirConexao();
		Map<Integer,String> cls = new HashMap<Integer,String>();
		@SuppressWarnings("unchecked")
		List<Object[]> list = manager.createNativeQuery("select ctared,descta from e043pcm where codmpc=2001 and clacta>100 order by ctared").getResultList();
		fechaConexao();
		list.forEach(c -> cls.put(Integer.parseInt(c[0].toString()), c[1].toString()));
		return cls;		
	}
	
}
