package br.com.mauricio.news.dao.contabil;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.joda.time.DateTime;

import br.com.mauricio.news.model.contabil.MCTB001;
import br.com.mauricio.news.model.contabil.MCTB002;
/**
*
* @author MAURICIO CRUZ
*/
public class MCTB001Dao {
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
	
	public List<MCTB001> obterLancamentos(int ano, int mes) throws IOException{
		String sql ="";

		abrirConexao();
		sql ="select * from USU_VCUSTO c WHERE  year(c.datent)= :ano AND MONTH(c.datent)= :mes ";

	
		List<MCTB001> lancamentos = new ArrayList<MCTB001>();
		@SuppressWarnings("unchecked")
		List<Object[]> list = manager.createNativeQuery(sql).setParameter("ano", ano).setParameter("mes", mes)
				.getResultList();
		fechaConexao();
		
		for(Object[] o:list){
			MCTB001 l = new MCTB001();
			l.setCodemp(1);
			l.setCodfil(Integer.parseInt(o[1].toString()));
			l.setCodfor((int) o[2]);
			l.setDatent(new DateTime((Date) o[0]));
			l.setCodsnf(o[3].toString());
			l.setCodtns(o[4].toString());
			l.setNumnfc((int) o[5]);
			l.setCodpro(o[6].toString());
			l.setCplipc(o[7].toString());
			l.setVlrrat(new BigDecimal(o[8].toString()));
			l.setTipdes(o[9].toString());
			l.setCtared((int) o[10]);
			l.setCodccu(o[11].toString());
			l.setCodfam(o[12].toString());
			if(o[13]!=null)
				l.setClassificacao(o[13].toString());
			else
				l.setClassificacao("X");
			lancamentos.add(l);
		}
		System.out.println("========== Lancamentos gerados com "+ lancamentos.size()+" registros ===============");
		return lancamentos;
	}

	public List<MCTB002> obterCcu() throws IOException{
		String sql ="";

		abrirConexao();
		sql ="select codemp,codccu,desccu,ccupai,claccu from e044ccu c ";

	
		List<MCTB002> lancamentos = new ArrayList<MCTB002>();
		@SuppressWarnings("unchecked")
		List<Object[]> list = manager.createNativeQuery(sql).getResultList();
		fechaConexao();
		
		for(Object[] o:list){
			MCTB002 c = new MCTB002();
			c.setCodemp(1);
			c.setCodccu(o[1].toString());
			c.setDesccu(o[2].toString());
			c.setPaiccu(o[3].toString());
			c.setClaccu(o[4].toString());;
			
			lancamentos.add(c);
		}
		System.out.println("========== Lancamentos gerados com "+ lancamentos.size()+" registros ===============");
		return lancamentos;
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
