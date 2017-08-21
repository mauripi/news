package br.com.mauricio.news.dao.contabil;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import br.com.mauricio.news.model.contabil.LancamentoContabil;
import br.com.mauricio.news.model.contabil.RelacionamentoSig;
/**
*
* @author MAURICIO CRUZ
*/
public class LancamentoContabilDao {
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
	
	@SuppressWarnings("unchecked")
	public List<LancamentoContabil> obterLancamentos(Date dataIni, Date dataFim) throws IOException{
		String sql ="";
		List<LancamentoContabil> lancamentos = new ArrayList<LancamentoContabil>();
		abrirConexao();

		sql ="select ctared,codccu,filrat,MONTH(datlct) as MES,YEAR(datlct) as ANO,datlct,debcre,vlrrat "
					+ "from e640rat where datlct >= :dataIni and datlct <= :dataFim and sitrat=2 and ctared in (10434,11767)"
					+ "order by ctared,datlct,debcre";
		
		List<Object[]> list = manager.createNativeQuery(sql).setParameter("dataIni", dataIni).setParameter("dataFim", dataFim).getResultList();
		fechaConexao();
		
		for(Object[] o:list){
			LancamentoContabil l = new LancamentoContabil();
			l.setCtared((int) o[0]);
			l.setCodccu(Integer.parseInt(o[1].toString()));
			l.setFilrat(Integer.parseInt(o[2].toString()));
			l.setMes((int) o[3]);
			l.setAno((int) o[4]);
			l.setDatlct((Date) o[5]);
			l.setDebcre(o[6].toString());
			l.setVlrrat(new BigDecimal(o[7].toString()));

			lancamentos.add(l);
		}
		System.out.println("========== LancamentoContabilDao.obterLancamentos(dataIni= "+dataIni+", dataFim= "+dataFim+") "
				+ "gerado com "+ lancamentos.size()+" registros ===============");
		return lancamentos;
	}

	@SuppressWarnings("unchecked")
	public List<RelacionamentoSig> relacionamentoSig() throws IOException{
		String sql ="";
		List<RelacionamentoSig> relacionamento = new ArrayList<RelacionamentoSig>();
		abrirConexao();

		sql ="select rmp.ctaant,pcm.clacta, (select pcmx.clacta from e043pcm pcmx where pcmx.ctared=rmp.ctaant and pcmx.codmpc = 1002) as classificacao,  "
				+" CASE when SUBSTRING (pcm.clacta, 1,2) = 10 then SUBSTRING (pcm.clacta, 3,LEN(pcm.clacta))"
				+ "when SUBSTRING (pcm.clacta, 1,2) > 10 then SUBSTRING (pcm.clacta, 2,LEN(pcm.clacta)) end as SIG, pcm.descta "
				+ "from e043rmp rmp inner join e043pcm pcm on (rmp.codmpa = pcm.codmpc and rmp.ctaatu = pcm.ctared)"
				+ "where  rmp.codmpc = 1002 and rmp.codmpa = 1005 group by rmp.ctaant,pcm.clacta, pcm.descta order by pcm.clacta";
		
		List<Object[]> list = manager.createNativeQuery(sql).getResultList();
		fechaConexao();
		for(Object[] o:list){
			RelacionamentoSig r = new RelacionamentoSig();
			r.setCtared((int) o[0]);
			r.setClasig(Integer.parseInt(o[1].toString()));
			r.setClacta(Integer.parseInt(o[2].toString()));
			r.setCtasig(Integer.parseInt(o[3].toString()));
			r.setDessig(o[4].toString());
			relacionamento.add(r);
		}
		System.out.println("========== LancamentoContabilDao.relacionamentoSig() "
				+ "gerado com "+ relacionamento.size()+" registros ===============");
		return relacionamento;
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
