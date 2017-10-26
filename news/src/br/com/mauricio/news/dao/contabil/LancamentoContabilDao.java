package br.com.mauricio.news.dao.contabil;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import br.com.mauricio.news.model.contabil.ContaValorTotalFilial;
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
					+ "from e640rat where datlct >= :dataIni and datlct <= :dataFim and sitrat=2 "
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

	
	
	public void atualizaOrcamentoBaseSenior(Set<ContaValorTotalFilial> orcadoMensalPorFilial, Map<Integer, Integer> contaClassificacao){
		System.out.println(" =========     INÍCIO DA ATUALIZAÇÃO/INSERÇÃO DO ORÇAMENTO ============== ");
		System.out.println("");
		String sqlExiste = "SELECT count(ctared) FROM e650sal  where codemp = :codemp and mesano = :mesano and codfil = :codfil and ctared = :ctared";
		abrirConexao();
		DateTimeZone zoneSaoPaulo = DateTimeZone.forID( "America/Sao_Paulo" );
		//orcadoMensalPorFilial.stream().filter(o -> o.getCtared().equals(11055)).forEach(c -> {	
		
		orcadoMensalPorFilial.stream().forEach(c -> {	

			for(int i=0; i<c.getTotais().length;i++){
				double vlrorc =c.getTotais()[i].doubleValue();
				Integer codfil = c.getFilrat();
				Integer ctared = c.getCtared();
				String clacta = contaClassificacao.get(ctared).toString();
				DateTime data = new DateTime(2017,i+1,1,0,0,0,zoneSaoPaulo);
				Date dataBanco = data.toDate();
				Integer count = (Integer) manager.createNativeQuery(sqlExiste)
						.setParameter("codemp", 1).setParameter("codfil", codfil)
						.setParameter("mesano", dataBanco).setParameter("ctared", ctared)
						.getSingleResult();
				String mesano = montaData(i+1);
				
				if(count.equals( 0 )) iserirOrcamento(codfil, mesano, ctared, clacta, vlrorc);
				else update(codfil, mesano, ctared, vlrorc);

			}
		});
		fechaConexao();
	}

	private void iserirOrcamento(Integer codfil, String mesano, Integer ctared, String clacta,Double vlrorc){
		Integer codemp = 1;
		String anasin = "A";
		Integer qtdpos = 4;
		double debcal = new Double("0.0");
		double crecal = new Double("0.0");
		double debmes = new Double("0.0");
		double cremes = new Double("0.0");
		double salmes = new Double("0.0");

		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO e650pma (codemp,codfil,mesano,ultlot,ultlct,ultcnv,temlct,temlrt,temlca,temlra,temrat,indest) VALUES( ");
		sb.append(codemp).append(",").append(codfil).append(",'").append(mesano).append("',0,0,0,'N','N','N','N','S','S'").append(")");
		escrever(sb.toString());
		
		
		sb = new StringBuilder();
		sb.append("INSERT INTO e650sal (codemp,codfil,mesano,ctared,clacta,anasin,qtdpos,debcal,crecal,debmes,cremes,salmes,vlrorc) VALUES( ");
		sb.append(codemp).append(",").append(codfil).append(",'").append(mesano).append("',");
		sb.append(ctared).append(",").append(clacta).append(",'").append(anasin).append("',");
		sb.append(qtdpos).append(",").append(debcal).append(",").append(crecal).append(",");
		sb.append(debmes).append(",").append(cremes).append(",").append(salmes).append(",");
		sb.append(vlrorc).append(")");
		escrever(sb.toString());
		
		sb = new StringBuilder();
		sb.append("insert into e650rto (codemp,codfil,mesano,ctared,codccu,ctafin,salmes,vlrrat,perrat,datger,seqobs) values (1,");
		sb.append(codfil).append(",'").append(mesano).append("',");
		sb.append(ctared).append(",'30006','22712',0.00,");
		sb.append(vlrorc).append(",100.0000,'1900-12-31 00:00:00.000',0);");
		escrever(sb.toString());
	}

	private void update(Integer codfil, String mesano, Integer ctared, Double vlrorc){
		Integer codemp = 1;
		StringBuilder sb = new StringBuilder();
		sb.append("update e650sal set vlrorc = ");
		sb.append(vlrorc).append(" ");
		sb.append("where codemp = ");
		sb.append(codemp).append(" and codfil = ");
		sb.append(codfil).append(" and mesano = '");
		sb.append(mesano).append("' and ctared = ");
		sb.append(ctared).append(";");
		escrever(sb.toString());	
		
		sb = new StringBuilder();
		sb.append("insert into e650rto (codemp,codfil,mesano,ctared,codccu,ctafin,salmes,vlrrat,perrat,datger,seqobs) values (1,");
		sb.append(codfil).append(",'").append(mesano).append("',");
		sb.append(ctared).append(",'30006','22712',0.00,");
		sb.append(vlrorc).append(",100.0000,'1900-12-31 00:00:00.000',0);");
		escrever(sb.toString());
		
	}
	
	private String montaData(Integer i){
		String data="2017-01-01 00:00:00.000";
    	if(i.toString().length()==1)
    		data="2017-0"+i.toString()+"-01 00:00:00.000";
    	else
    		data="2017-"+i.toString()+"-01 00:00:00.000";		
		return data;
	}
	
    private void escrever(String comando) {
        File dir = new File("C:\\sap\\");
        File arq = new File(dir, "sql.txt");

        try {
            arq.createNewFile();

            FileWriter fileWriter = new FileWriter(arq, true);

            PrintWriter printWriter = new PrintWriter(fileWriter);
 		    printWriter.println(comando);

            printWriter.flush();
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
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
