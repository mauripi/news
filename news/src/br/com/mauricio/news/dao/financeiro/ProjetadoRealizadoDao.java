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
		String sql = "select retour,vlrour,qtdpar,numtit,vctpro,nomfor,obstcp,vlrabe,usu_clsflx"
				+ " FROM USU_VPREVISTO WHERE vctpro BETWEEN :d1 and :d2 and usu_clsflx > 0 ORDER BY vctpro";				
		
		@SuppressWarnings("unchecked")
		List<Object[]> list = manager.createNativeQuery(sql).setParameter("d1", d1).setParameter("d2", d2).getResultList();

		for(Object[] o : list){
			Previsto pre = new Previsto();
			pre.setRetour(o[0].toString());
			if(o[1] != null) pre.setVlrour(new BigDecimal(o[1].toString()));				
			else pre.setVlrour(BigDecimal.ZERO);
			if(o[2] != null) pre.setQtdpar(Integer.parseInt(o[2].toString()));			
			else pre.setQtdpar(0);								
			pre.setNumtit(o[3].toString());
			pre.setVctpro((Date) o[4]);
			pre.setNomclifor(o[5].toString());
			pre.setObstit(o[6].toString());
			pre.setVlrabe(new BigDecimal(o[7].toString()));
			if(pre.getRetour().equals("S") && pre.getVlrour().compareTo(BigDecimal.ZERO)==1 && pre.getQtdpar()>0)
				pre.setVlrabe(pre.getVlrabe().subtract(pre.getVlrour().divide(new BigDecimal(pre.getQtdpar()))));			
			if(o[8] != null) pre.setClsflx(Integer.parseInt(o[8].toString()));		
			else pre.setClsflx(0);				
			previstos.add(pre);
		}				
		fechaConexao();		
		System.out.println("========== ProjetadoRealizadoDao.buscarPrevisto gerado com "+ previstos.size()+" registros ===============");
		return previstos;
	}
	
	public List<Realizado> buscarRealizado(Date d1,Date d2){
		abrirConexao();
		String sql = "select numtit,vctpro,datmov,nomcli,obsmcr,vlrliq,usu_clsflx FROM USU_VRealizado WHERE datmov BETWEEN :d1 and :d2 and usu_clsflx > 0 ORDER BY datmov";						
		@SuppressWarnings("unchecked")
		List<Object[]> list = manager.createNativeQuery(sql).setParameter("d1", d1).setParameter("d2", d2).getResultList();

		for(Object[] o : list){
			Realizado r = new Realizado();
			r.setNumtit(o[0].toString());
			r.setVctpro((Date) o[1]);
			r.setDatmov((Date) o[2]);
			r.setNomclifor(o[3].toString());
			r.setObstit(o[4].toString());
			r.setVlrliq(new BigDecimal(o[5].toString()));
			if(o[6] != null) r.setClsflx(Integer.parseInt(o[6].toString()));		
			else r.setClsflx(0);				
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
		List<Object[]> list = manager.createNativeQuery("select usu_codcls as id,usu_descls as nome from usu_tclsflx order by id").getResultList();
		fechaConexao();
		list.forEach(c -> cls.put(Integer.parseInt(c[0].toString()), c[1].toString()));
		return cls;		
	}
	
}
