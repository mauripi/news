package br.com.mauricio.news.dao.contabil;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.joda.time.LocalDate;

import br.com.mauricio.news.model.contabil.Patrimonio;
/**
*
* @author MAURICIO CRUZ
*/
public class BemDao {
	private EntityManagerFactory factory;
	private EntityManager manager;

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
	
	public List<Patrimonio> getPatrimonios(String codigo, Date data) throws IOException{
		abrirConexao();
		LocalDate dt = new LocalDate(data);
		int mes=dt.getMonthOfYear()-1;
		int ano =dt.getYear();
		
		String sql1 ="SELECT b.codbem,b.desbem,b.dataqi,b.vlrbas,nomfor,numdoc,l.numpla "
				+ "FROM e670bem b inner join e670loc l on (l.codbem=b.codbem) "
				+ "WHERE b.sitpat<>'I' and SUBSTRING(l.numpla, 1, 6)= :codigo "
				+ "group by b.codbem,b.desbem,b.dataqi,b.vlrbas,nomfor,numdoc,l.numpla "
				+ "order by l.numpla";

		String sql2 ="select "
				+ "SUM(c.vlrant-c.dpracu) as residual "
				+ "FROM e670cal c "
				+ "WHERE c.codbem= :codbem and YEAR(c.datcal)= :ano and MONTH(c.datcal)= :mes  ";
		
		List<Patrimonio> bens = new ArrayList<Patrimonio>();
		@SuppressWarnings("unchecked")
		List<Object[]> list = manager.createNativeQuery(sql1).setParameter("codigo", formataCodigoBem(codigo))
				.getResultList();
				
		for(Object[] o:list){
			Patrimonio p = new Patrimonio();
			p.setCodbem(o[0].toString());
			p.setDesbem(o[1].toString());
			p.setDataqi((Date) o[2]);
			p.setVlrbas(new Double(o[3].toString()));
			p.setNomfor(o[4].toString());
			p.setNumdoc(o[5].toString());
			p.setNumpla(o[6].toString());			

			BigDecimal vlrres = (BigDecimal) manager.createNativeQuery(sql2).setParameter("ano", ano).setParameter("mes", mes).setParameter("codbem", p.getCodbem()).getSingleResult();		
			if(vlrres==null){
				do{
					dt = dt.minusMonths(1);
					mes=dt.getMonthOfYear();
					ano =dt.getYear();
					vlrres = (BigDecimal) manager.createNativeQuery(sql2).setParameter("ano", ano).setParameter("mes", mes).setParameter("codbem", p.getCodbem()).getSingleResult();		
				} while( vlrres==null );
			}
			p.setVlrres(vlrres.doubleValue());

			bens.add(p);
		}
		
		fechaConexao();		
		System.out.println("========== BemDao.obterBens gerado com "+ bens.size()+" registros ===============");
		return bens;
	}


	public List<Patrimonio> obterPatrimonios(String codigo) throws IOException{
		abrirConexao();
		
		String sql1 ="select "
				+ "b.codbem,b.desbem,b.dataqi,b.vlrbas,nomfor,numdoc,l.numpla "
				+ "FROM e670bem b inner join e670loc l on (l.codbem=b.codbem) "
				+ "WHERE b.sitpat<>'I' and SUBSTRING(l.numpla, 1, 6)= :codigo "
				+ "group by b.codbem,b.desbem,b.dataqi,b.vlrbas,nomfor,numdoc,l.numpla "
				+ "order by l.numpla";
		
		List<Patrimonio> bens = new ArrayList<Patrimonio>();
		@SuppressWarnings("unchecked")
		List<Object[]> list = manager.createNativeQuery(sql1).setParameter("codigo", formataCodigoBem(codigo))
				.getResultList();
				
		for(Object[] o:list){
			Patrimonio p = new Patrimonio();
			p.setCodbem(o[0].toString());
			p.setDesbem(o[1].toString());
			p.setDataqi((Date) o[2]);
			p.setVlrbas(new Double(o[3].toString()));
			p.setNomfor(o[4].toString());
			p.setNumdoc(o[5].toString());
			p.setNumpla(o[6].toString());
			bens.add(p);
		}
		
		fechaConexao();		
		System.out.println("========== BemDao.obterBens gerado com "+ bens.size()+" registros ===============");
		return bens;
	}
	
	
	
	private  String formataCodigoBem(String c){
		String s=c;
		if(c.length()<6)
			for(int i=0;i<6-c.length();i++)
				s="0"+s;
		return s;		
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
