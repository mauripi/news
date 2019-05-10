package br.com.mauricio.news.dao.contabil;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import br.com.mauricio.news.model.contabil.GestaoNotaFiscal;

public class GestaoNotaFiscalDao implements Serializable{

	private static final long serialVersionUID = 1L;
	private EntityManagerFactory factory;
	private EntityManager manager;

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
	
	public List<GestaoNotaFiscal> buscarPorCompetencia(Integer mes, Integer ano){
		abrirConexao();
		
		String sql ="select codfil,numctr,qtdpar,propar,codfor,nomfor,objctr,diabas,ultgoc,'FATURADAS' as status "
				+ "from usu_vContratos where numctr in (select numctr from usu_vNotasFiscais where YEAR(datent) = :ano and MONTH(datent) = :mes and numctr>0) "
				+ "union all "
				+ "select codfil,numctr,qtdpar,propar,codfor,nomfor,objctr,diabas,ultgoc,'NAO_FATURADAS' as status "
				+ "from usu_vContratos where numctr not in (select numctr from usu_vNotasFiscais where YEAR(datent) = :ano and MONTH(datent) = :mes and numctr>0)";
		
		List<GestaoNotaFiscal> notas = new ArrayList<GestaoNotaFiscal>();
		@SuppressWarnings("unchecked")
		List<Object[]> list = manager.createNativeQuery(sql).setParameter("mes", mes).setParameter("ano", ano).getResultList();
				
		for(Object[] o:list){
			GestaoNotaFiscal c = new GestaoNotaFiscal();
			c.setCodfil(Integer.parseInt(o[0].toString()));
			c.setNumctr(Integer.parseInt(o[1].toString()));
			c.setQtdpar(Integer.parseInt(o[2].toString()));
			c.setPropar(Integer.parseInt(o[3].toString()));
			c.setCodfor(Integer.parseInt(o[4].toString()));
			c.setNomfor(o[5].toString());
			c.setObjctr(o[6].toString());
			c.setDiabas(Integer.parseInt(o[7].toString()));
			c.setUltgoc((Date) o[8]);
			c.setStatus(o[9].toString());

			notas.add(c);
		}
		
		carregaDadosDaNota(mes,ano,notas);
		fechaConexao();		
		return notas;
	}

	private void carregaDadosDaNota(Integer mes, Integer ano, List<GestaoNotaFiscal> notas) {
		List<Integer> contratos = notas.stream().map(n -> n.getNumctr()).collect(Collectors.toList());
		String sql ="select codfil,numctr,codsnf,vlrbru,datent,numnfc,obsnfc from usu_vNotasFiscais where YEAR(datent) = :ano and MONTH(datent) = :mes and numctr in ( :contratos)";

		@SuppressWarnings("unchecked")
		List<Object[]> list = manager.createNativeQuery(sql).setParameter("mes", mes).setParameter("ano", ano).setParameter("contratos", contratos).getResultList();				
		for(Object[] o:list){
			Integer codfil = Integer.parseInt(o[0].toString());
			Integer numctr = Integer.parseInt(o[1].toString());
			for (GestaoNotaFiscal nota : notas){
				if(nota.getCodfil()==codfil && nota.getNumctr()==numctr){
					nota.setCodsnf(o[2].toString());
					nota.setVlrbru(new BigDecimal(o[3].toString()));
					nota.setDatent((Date) o[4]);	
					nota.setNumnfc(Integer.parseInt(o[5].toString()));
					nota.setObjctr(o[6].toString());
				}
			}
		}

	}

	
}
