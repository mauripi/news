package br.com.mauricio.news.dao.financeiro;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import br.com.mauricio.news.model.financeiro.Movimento;
import br.com.mauricio.news.model.financeiro.Titulo;
/**
*
* @author MAURICIO CRUZ
*/
public class FluxoClassificacaoDao {
	private EntityManagerFactory factory;
	private EntityManager manager;
	private List<Titulo> titulos = new ArrayList<Titulo>();
	private List<Movimento> movimentos = new ArrayList<Movimento>();
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
	
	public List<Titulo> buscarTitulos(Date d1,Date d2){
		abrirConexao();
		getContasReceber(d1,d2);
		getContasPagar(d1,d2);
		
		fechaConexao();		
		/*System.out.println("========== TítuloDao.buscarTitulos gerado com "+ titulos.size()+" registros ===============");*/
		return titulos;
	}

	public void atualizaTitulo(Titulo titulo){
		abrirConexao();
		String sql = "";

		if(titulo.getOritit().equals("CP"))
			sql = "UPDATE E501TCP SET vctpro = :vctpro, datppt = :datppt, obstcp = :obstit, usu_clsflx = :clsflx, codbar = :codbar, codban = :codban, codage = :codage, ccbfor = :ccbfor "
		    		+ " WHERE codemp = :codemp AND codfil = :codfil AND numtit = :numtit AND codtpt = :codtpt AND codfor = :codclifor";
		else{
			sql = "UPDATE E301TCR SET vctpro = :vctpro, datppt = :datppt, obstcr = :obstit, usu_clsflx = :clsflx, codbar = :codbar"
		    		+ " WHERE codemp = :codemp AND codfil = :codfil AND numtit = :numtit AND codtpt = :codtpt AND codcli = :codclifor";			
		}
		if(titulo.getOritit().equals("CR")){
		    manager.createNativeQuery(sql)
		    .setParameter("vctpro", titulo.getVencAtual())
		    .setParameter("datppt", titulo.getProvPagto())
		    .setParameter("obstit", titulo.getObstit())
		    .setParameter("clsflx", titulo.getClsflx())
		    .setParameter("codemp", 1)
		    .setParameter("codfil", titulo.getCodfil())
		    .setParameter("numtit", titulo.getNumtit())
		    .setParameter("codtpt", titulo.getCodtpt())
		    .setParameter("codclifor", titulo.getCodclifor())
		    .setParameter("codbar", titulo.getCodbar().replace(" ", ""))
		    .executeUpdate();
		}else{
			 manager.createNativeQuery(sql)
		    .setParameter("vctpro", titulo.getVencAtual())
		    .setParameter("datppt", titulo.getProvPagto())
		    .setParameter("obstit", titulo.getObstit())
		    .setParameter("clsflx", titulo.getClsflx())
		    .setParameter("codemp", 1)
		    .setParameter("codfil", titulo.getCodfil())
		    .setParameter("numtit", titulo.getNumtit())
		    .setParameter("codtpt", titulo.getCodtpt())
		    .setParameter("codclifor", titulo.getCodclifor())
		    .setParameter("codbar", titulo.getCodbar().replace(" ", ""))
		    .setParameter("codban", titulo.getCodban())
		    .setParameter("codage", titulo.getCodage())
		    .setParameter("ccbfor", titulo.getCcbfor())
		    .executeUpdate();
		}
	    
	    manager.getTransaction().commit();

	    fechaConexao();
	}

	private void getContasReceber(Date d1, Date d2) {
		String sql = "SELECT numtit,codfil,codtpt,E085CLI.codcli,nomcli,vlrori,vlrabe,obstcr,vctori as venc_original,"
				+ " vctpro as venc_atual,datppt as prov_pagto,usu_clsflx,codtns,datemi,codbar "
				+ " FROM E301TCR, E085CLI WHERE E301TCR.CODCLI=E085CLI.CODCLI and E301TCR.datppt BETWEEN :d1 and :d2 and vlrabe>0 and "
				+ " E301TCR.sittit  NOT IN ('CA','LS') order by E301TCR.datppt";
		
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
			tit.setDatemi((Date) o[13]);
			tit.setCodbar(o[14].toString());
			tit.setOritit("CR");
			titulos.add(tit);
		}		
	}

	private void getContasPagar(Date d1, Date d2) {

		String sql = " SELECT numtit,E501TCP.codfil,codtpt,E095FOR.codfor,nomfor,E501TCP.vlrori,vlrabe,obstcp,vctori as venc_original,vctpro as venc_atual,datppt as prov_pagto, "
		+ " E501TCP.usu_clsflx,E501TCP.codtns,E501TCP.datemi,E501TCP.codbar,E501TCP.codban,E501TCP.codage,E501TCP.ccbfor,r910usu.nomcom as nomapr,e614usu.datapr as datapr FROM E501TCP " 
		+ " Left join E095FOR ON (E501TCP.CODFOR=E095FOR.CODFOR) "
		+ " LEFT JOIN e440ipc ON (E501TCP.filnfc = E440IPC.codfil AND E501TCP.fornfc = E440IPC.codfor AND E501TCP.numnfc = E440IPC.numnfc AND E501TCP.snfnfc = E440IPC.codsnf AND E440IPC.seqipc=1) "
		+ " LEFT JOIN e440isc ON (E501TCP.filnfc = E440ISC.codfil AND E501TCP.fornfc = E440ISC.codfor AND E501TCP.numnfc = E440ISC.numnfc AND E501TCP.snfnfc = E440ISC.codsnf AND E440ISC.seqisc=1) "
		+ " LEFT JOIN e420ocp ON ((e440ipc.numocp = e420ocp.numocp AND e440ipc.filocp = e420ocp.codfil ) OR (e440isc.numocp = e420ocp.numocp AND e440isc.filocp = e420ocp.codfil)) "
		+ " lEFT JOIN e614usu ON (e420ocp.numapr = e614usu.numapr) LEFT JOIN r910usu ON (e614usu.usuapr = r910usu.codent) "
		+ " WHERE E501TCP.datppt BETWEEN :d1 and :d2 and E501TCP.sittit NOT IN ('CA','LS') and vlrabe>0 order by E501TCP.datppt ";	

		
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
			tit.setDatemi((Date) o[13]);
			tit.setCodbar(o[14].toString());
			tit.setCodban(o[15].toString());
			tit.setCodage(o[16].toString());
			tit.setCcbfor(o[17].toString());
			if (o[18]!=null)
				tit.setNomapr(primeiroNome(o[18].toString()));
			if (o[19]!=null)
				tit.setDatapr((Date) o[19]);
			tit.setOritit("CP");
			titulos.add(tit);
		}	
	}

	public List<Movimento> buscarMovimentos(Date d1,Date d2){
		abrirConexao();
		getMovimentos(d1,d2);		
		fechaConexao();		
		/*System.out.println("========== MovimentoDao.buscarMovimentos gerado com "+ movimentos.size()+" registros ===============");*/
		return movimentos;
	}

	public void atualizaMovimento(Movimento movimento){
		abrirConexao();
		String sql = "UPDATE e600mcc SET usu_clsflx = :clsflx, hismov = :hismov WHERE codemp = :codemp AND numcco = :numcco AND datmov = :datmov AND seqmov = :seqmov ";

	    manager.createNativeQuery(sql)
	    .setParameter("clsflx", movimento.getClsflx())
	    .setParameter("hismov", movimento.getHismov())
	    .setParameter("codemp", 1)
	    .setParameter("numcco", movimento.getNumcco())
	    .setParameter("datmov", movimento.getDatmov())
	    .setParameter("seqmov", movimento.getSeqmov())
	    .executeUpdate();
	    manager.getTransaction().commit();

	    fechaConexao();
	}

	private void getMovimentos(Date d1, Date d2) {
		String sql = "SELECT numcco,datmov,seqmov,codtns,docmov,hismov,vlrmov,debcre,usu_clsflx,codfil "
				+ " FROM E600MCC WHERE sitmcc='A' AND datmov BETWEEN :d1 AND :d2 ";
		
		@SuppressWarnings("unchecked")
		List<Object[]> list = manager.createNativeQuery(sql).setParameter("d1", d1).setParameter("d2", d2).getResultList();

		for(Object[] o:list){
			Movimento m = new Movimento();
			m.setNumcco(o[0].toString());
			m.setDatmov((Date) o[1]);
			m.setSeqmov(Integer.parseInt(o[2].toString()));
			m.setCodtns(o[3].toString());
			m.setDocmov(o[4].toString());
			m.setHismov(o[5].toString());
			m.setVlrmov(new BigDecimal(o[6].toString()));
			m.setDebcre(o[7].toString());			
			if(o[8]!=null)
				m.setClsflx(Integer.parseInt(o[8].toString()));
			else
				m.setClsflx(0);
			m.setCodfil(Integer.parseInt(o[9].toString()));			
			movimentos.add(m);
		}		
	}

	private String primeiroNome(String nome){
		String primeiroNome = "";
		for (int i=0;i<nome.length();i++){
			if ((i==0) && (nome.substring(i, i+1).equalsIgnoreCase(" "))){
				System.out.println("Erro: Nome digitado iniciado com tecla ESPAÇO.");
				break;
			}
			else if (!nome.substring(i, i+1).equalsIgnoreCase(" ")){
				primeiroNome += nome.substring(i, i+1);
			}
			else
				break;
		}
		return primeiroNome;
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
