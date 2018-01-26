package br.com.mauricio.news.dao.financeiro;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import br.com.mauricio.news.model.financeiro.Titulo;
/**
*
* @author MAURICIO CRUZ
*/
public class TítuloDao {
	private EntityManagerFactory factory;
	private EntityManager manager;
	List<Titulo> titulos = new ArrayList<Titulo>();
	
	private EntityManager abrirConexao(){
		this.factory = Persistence.createEntityManagerFactory("sapiens_homo");
		this.manager = this.factory.createEntityManager();
		this.manager.getTransaction().begin();
		return this.manager;
	}
	
	private void fechaConexao(){
		this.manager.close();
		this.factory.close();		
	}	
	
	public List<Titulo> buscarTitulos(Date d1,Date d2){
		abrirConexao();
		getContasReceber(d1,d2);
		getContasPagar(d1,d2);
		
		fechaConexao();		
		System.out.println("========== TítuloDao.buscarTitulos gerado com "+ titulos.size()+" registros ===============");
		return titulos;
	}

	public void atualizaTitulo(Titulo titulo){
		abrirConexao();
		String sql = "";

		if(titulo.getOritit().equals("CP"))
			sql = "UPDATE E501TCP SET vctpro = :vctpro, datppt = :datppt, obstcp = :obstit, usu_clsflx = :clsflx "
		    		+ " WHERE codemp = :codemp AND codfil = :codfil AND numtit = :numtit AND codtpt = :codtpt AND codfor = :codclifor";
		else{
			sql = "UPDATE E301TCR SET vctpro = :vctpro, datppt = :datppt, obstcr = :obstit, usu_clsflx = :clsflx "
		    		+ " WHERE codemp = :codemp AND codfil = :codfil AND numtit = :numtit AND codtpt = :codtpt AND codcli = :codclifor";			
		}
		System.out.println(sql);

	    int ret = manager.createNativeQuery(sql)
	    .setParameter("vctpro", titulo.getVencAtual())
	    .setParameter("datppt", titulo.getProvPagto())
	    .setParameter("obstit", titulo.getObstit())
	    .setParameter("clsflx", titulo.getClsflx())
	    .setParameter("codemp", 1)
	    .setParameter("codfil", titulo.getCodfil())
	    .setParameter("numtit", titulo.getNumtit())
	    .setParameter("codtpt", titulo.getCodtpt())
	    .setParameter("codclifor", titulo.getCodclifor())
	    .executeUpdate();
	    manager.getTransaction().commit();
	    System.out.println(ret);
	    fechaConexao();
	}

	private void getContasReceber(Date d1, Date d2) {
		String sql = "SELECT numtit,codfil,codtpt,E085CLI.codcli,nomcli,vlrori,vlrabe,obstcr,vctori as venc_original,vctpro as venc_atual,datppt as prov_pagto,usu_clsflx,codtns"
				+ " FROM E301TCR, E085CLI WHERE E301TCR.CODCLI=E085CLI.CODCLI and E301TCR.datppt BETWEEN :d1 and :d2 and "
				+ " E301TCR.sittit <> 'LQ' and E301TCR.codtns <> '90330' order by E301TCR.datppt";
		
		@SuppressWarnings("unchecked")
		List<Object[]> list = manager.createNativeQuery(sql).setParameter("d1", d1).setParameter("d2", d2).getResultList();

		for(Object[] o:list){
			Titulo tit = new Titulo();
			tit.setNumtit(o[0].toString());
			tit.setCodfil(Integer.parseInt(o[1].toString()));
			tit.setCodtpt(o[2].toString());
			tit.setCodclifor(Integer.parseInt(o[3].toString()));
			tit.setNomclifor(o[4].toString());
			tit.setVlrori(new BigDecimal(o[5].toString()));
			tit.setVlrabe(new BigDecimal(o[6].toString()));			
			tit.setObstit(o[7].toString());
			tit.setVencOriginal((Date) o[8]);
			tit.setVencAtual((Date) o[9]);			
			tit.setProvPagto((Date) o[10]);
			if(o[11]!=null)
				tit.setClsflx(Integer.parseInt(o[11].toString()));
			else
				tit.setClsflx(0);
			tit.setCodtns(o[12].toString());
			tit.setOritit("CR");
			titulos.add(tit);
		}		
	}

	private void getContasPagar(Date d1, Date d2) {
		String sql = "SELECT numtit,codfil,codtpt,E095FOR.codfor,nomfor,vlrori,vlrabe,obstcp,vctori as venc_original,vctpro as venc_atual,datppt as prov_pagto,usu_clsflx,codtns"
				+ " FROM E501TCP, E095FOR WHERE E501TCP.CODFOR=E095FOR.CODFOR and E501TCP.datppt BETWEEN :d1 and :d2 and "
				+ " E501TCP.sittit <> 'CA' order by E501TCP.datppt";
		
		@SuppressWarnings("unchecked")
		List<Object[]> list = manager.createNativeQuery(sql).setParameter("d1", d1).setParameter("d2", d2).getResultList();

		for(Object[] o:list){
			Titulo tit = new Titulo();
			tit.setNumtit(o[0].toString());
			tit.setCodfil(Integer.parseInt(o[1].toString()));
			tit.setCodtpt(o[2].toString());
			tit.setCodclifor(Integer.parseInt(o[3].toString()));
			tit.setNomclifor(o[4].toString());
			tit.setVlrori(new BigDecimal(o[5].toString()));
			tit.setVlrabe(new BigDecimal(o[6].toString()));			
			tit.setObstit(o[7].toString());
			tit.setVencOriginal((Date) o[8]);
			tit.setVencAtual((Date) o[9]);			
			tit.setProvPagto((Date) o[10]);
			if(o[11]!=null)
				tit.setClsflx(Integer.parseInt(o[11].toString()));
			else
				tit.setClsflx(0);
			tit.setCodtns(o[12].toString());
			tit.setOritit("CP");
			titulos.add(tit);
		}	
	}
	
	
	
	public EntityManagerFactory getFactory() {
		return factory;
	}

	public void setFactory(EntityManagerFactory factory) {
		this.factory = factory;
	}

	public EntityManager getManager() {
		return manager;
	}

	public void setManager(EntityManager manager) {
		this.manager = manager;
	}


}
