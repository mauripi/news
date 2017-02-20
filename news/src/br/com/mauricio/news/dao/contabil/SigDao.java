package br.com.mauricio.news.dao.contabil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;



/**
*
* @author MAURICIO CRUZ
*/
public class SigDao{
	private EntityManagerFactory factory;
	private EntityManager manager;
	private int ano=0;
	private int mes=0;
	private static final String SOURCE_FOLDER = "C:\\Windows\\Temp\\SIG\\";

	public void gerar(int ano, int mes) throws IOException{
		this.ano =ano;
		this.mes=mes;
		criarPastas();
		abrirConexao();
		gerarArquivoDespesa();
		gerarArquivoReceita();
		gerarArquivoFornecedor();
		gerarArquivoCliente();
		fechaConexao();
	}
	
	private void criarPastas() {
		File file2 = new File("C:\\Windows\\Temp\\SIGZIP\\","SIG.zip");
		File file = new File(SOURCE_FOLDER);
		File file3 = new File("C:\\Windows\\Temp\\SIGZIP\\");
		if(file2.exists())
			file2.delete();
		if (!file.exists())
			file.mkdir();
		if (!file3.exists())
			file3.mkdir();
	}

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
	
	public void gerarArquivoDespesa() throws IOException{
		File file = new File(SOURCE_FOLDER,"despesas10.txt");
		
		if(file.exists())
			file.delete();
		OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(file, true), "windows-1252");
		String sql ="select "
				+ "l.numlct AS DOCUMENTO,"
				+ "'10' AS EMPRESA,"
				+ "CONVERT(varchar(5), (CASE WHEN l.orilct='CPR' AND (SELECT lnf.codfor FROM E644LNF lnf WHERE lnf.codemp=l.codemp AND lnf.numlct=l.numlct AND lnf.codfil=l.codfil GROUP BY lnf.numlct,lnf.codfor) <>0  "                
				+ " THEN (SELECT lnf.codfor FROM E644LNF lnf WHERE lnf.codemp=l.codemp AND lnf.numlct=l.numlct AND lnf.codfil=l.codfil GROUP BY lnf.numlct,lnf.codfor)  "
                + " WHEN l.orilct='PAG' and (SELECT lti.codfor FROM E644LTI lti WHERE lti.codemp=l.codemp AND lti.numlct=l.numlct AND lti.codfil=l.codfil GROUP BY lti.numlct,lti.codfor) is null "
                + " THEN (SELECT MAX(f.codfor) FROM e095FOR f  WHERE f.cgccpf=l.cgccpf) WHEN l.orilct='TES' THEN 1322 WHEN l.orilct='PAT' THEN 1322 WHEN l.orilct='VRB' THEN 1322 WHEN l.orilct='MAN' THEN 1322 ELSE CASE WHEN (SELECT MAX(f.codfor) FROM e095FOR f WHERE f.cgccpf=l.cgccpf) IS NULL THEN 1322 ELSE (SELECT MAX(f.codfor) FROM e095FOR f WHERE f.cgccpf=l.cgccpf) END END)) AS CODFOR, "				
				+ " '1' AS UN_NG,"
				+ "'0' AS CCUSTO,"
				+ "CONVERT(varchar(5), (CASE SUBSTRING(p.clacta, 1, 1) WHEN 1 THEN CONVERT(INT,SUBSTRING(p.clacta, 2, 6)) ELSE CONVERT(INT,SUBSTRING(p.clacta, 1, 6)) END)) AS SIG, "
				+ "CONVERT(varchar(10), replace(convert(NVARCHAR, l.datlct, 111), ' ', '/')) AS DATA, "
				+ "l.vlrlct,"
				+ "CONVERT(varchar(254), CASE WHEN l.cpllct='' THEN '.' WHEN L.cpllct=' ' THEN '.' ELSE  SUBSTRING(REPLACE(l.cpllct COLLATE Latin1_General_BIN,'\"', ''),1,160) END) AS COMPLEMENTO "
				+ "FROM e640lct l INNER JOIN e043rmp r ON (l.ctadeb=r.ctaant) INNER JOIN e043pcm p ON (p.codmpc=1005 AND p.anasin='A' AND p.ctared=r.ctaatu) "
				+ " WHERE l.sitlct=2 AND l.tiplct=1 AND year(l.datlct)= :ano AND MONTH(l.datlct)= :mes AND r.codmpa=1005 "
				+ " and l.ctadeb in (select x.ctared from e043pcm x where x.codmpc=1002 and x.clacta like '4%')  "

				+ " UNION "
				+ "select l.numlct AS DOCUMENTO, '10' AS EMPRESA, "
				+ " '999'+ CONVERT(varchar(5),( CASE WHEN  l.orilct='PAG' THEN 444 "
				+ "		WHEN l.orilct='REC' THEN (select codcli from e301tcr rc inner join E644LTi tt on (tt.codemp=rc.codemp and tt.codfil=rc.codfil and tt.numtit=rc.numtit and tt.codtpt=rc.codtpt) where tt.numlct=l.numlct) "
				+ "		WHEN  l.orilct='TES' THEN 444 "
				+ "		WHEN  l.orilct='PAT' THEN 444 "				
				+ "		WHEN  l.orilct='MAN' THEN 444 ELSE CASE WHEN (SELECT MAX(c.codcli) FROM e085cli c WHERE c.cgccpf=l.cgccre) is null THEN 444 ELSE (SELECT MAX(c.codcli) FROM e085cli c WHERE c.cgccpf=l.cgccre) end end )) AS CODCLI, "
				+ " '1' AS UN_NG,'0' AS CCUSTO, "
				+ " CONVERT(varchar(5), (CASE SUBSTRING(p.clacta, 1, 1) WHEN 1 THEN CONVERT(INT,SUBSTRING(p.clacta, 2, 6)) ELSE CONVERT(INT,SUBSTRING(p.clacta, 1, 6)) END)) AS SIG, "
				+ " CONVERT(varchar(10), replace(convert(NVARCHAR, l.datlct, 111), ' ', '/')) AS DATA, l.vlrlct, "
				+ "	CONVERT(varchar(254), CASE WHEN l.cpllct='' THEN '.' WHEN L.cpllct=' ' THEN '.' ELSE  SUBSTRING(REPLACE(l.cpllct COLLATE Latin1_General_BIN,'\', ''),1,160) END) AS COMPLEMENTO "
				+ " FROM e640lct l inner join e043rmp r on (l.ctacre=r.ctaant) "
				+ " inner join e043pcm p on (p.codmpc=1005 AND p.anasin='A' AND p.ctared=r.ctaatu) "
				+ " WHERE l.sitlct=2 AND l.tiplct=1 AND year(l.datlct)= :ano AND MONTH(l.datlct)= :mes AND r.codmpa=1005 and "
				+ "	l.ctacre in (select x.ctared from e043pcm x where x.codmpc=1002 and x.clacta like '3%') "
				+ " and p.clacta in (select pcm2.clacta from e043pcm pcm2 where pcm2.codmpc=1005 and (pcm2.clacta between 115000 and 115999 or pcm2.clacta = 114004))";		
		@SuppressWarnings("unchecked")
		List<Object[]> list = manager.createNativeQuery(sql).setParameter("ano", this.ano).setParameter("mes", this.mes)
				.getResultList();
			
		for(Object[] o:list)
			writer.append("W"+o[0].toString()+";"+o[1]+";"+o[2]+";"+o[3]+";"+o[4]+";"+o[5]+";"+o[6]+";"+o[7]+";"+o[8].toString().replace("\n", "").replace("\r", "")+ "\r\n");

		writer.close();

		System.out.println("========== Arquivo despesas10.txt gerado com "+ list.size()+" registros ===============");
	}
	
	public void gerarArquivoReceita() throws IOException{

		File file = new File(SOURCE_FOLDER,"receitas10.txt");
		if(file.exists())
			file.delete();
		OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(file, true), "windows-1252");

		
		String sql ="select "
				+ "l.numlct AS DOCUMENTO, "
				+ "'10' AS EMPRESA, "
				+ "CONVERT(varchar(5),( CASE WHEN  l.orilct='PAG' THEN 444 WHEN  l.orilct='REC' THEN (select codcli from e301tcr rc inner join E644LTi tt on (tt.codemp=rc.codemp and tt.codfil=rc.codfil and tt.numtit=rc.numtit and tt.codtpt=rc.codtpt) where tt.numlct=l.numlct) WHEN  l.orilct='TES' THEN 444 WHEN  l.orilct='MAN' THEN 444 ELSE CASE WHEN (SELECT MAX(c.codcli) FROM e085cli c WHERE c.cgccpf=l.cgccre) is null THEN 444 ELSE (SELECT MAX(c.codcli) FROM e085cli c WHERE c.cgccpf=l.cgccre) end end )) AS CODCLI, "
				+ "'1' AS UN_NG, "
				+ "'0' AS CCUSTO, "
				+ "CONVERT(varchar(5), (CASE SUBSTRING(p.clacta, 1, 1) WHEN 1 THEN CONVERT(INT,SUBSTRING(p.clacta, 2, 6)) ELSE CONVERT(INT,SUBSTRING(p.clacta, 1, 6)) END)) AS SIG, "
				+ "CONVERT(varchar(10), replace(convert(NVARCHAR, l.datlct, 111), ' ', '/')) AS DATA, "
				+ "l.vlrlct, "
				+ "CONVERT(varchar(254), CASE WHEN l.cpllct='' THEN '.' WHEN L.cpllct=' ' THEN '.' ELSE  SUBSTRING(REPLACE(l.cpllct COLLATE Latin1_General_BIN,'\"', ''),1,160) END) AS COMPLEMENTO "
				+ "FROM e640lct l "
				+ "inner join e043rmp r on (l.ctacre=r.ctaant) "
				+ "inner join e043pcm p on (p.codmpc=1005 AND p.anasin='A' AND p.ctared=r.ctaatu) "
				+ "WHERE "
				+ "l.sitlct=2 AND l.tiplct=1 AND "
				+ " year(l.datlct)= :ano AND MONTH(l.datlct)= :mes AND "
				+ "r.codmpa=1005 and "
				+ "l.ctacre in (select x.ctared from e043pcm x where x.codmpc=1002 and x.clacta like '3%') "
				+ " and p.clacta not in (select pcm3.clacta from e043pcm pcm3 where pcm3.codmpc=1005 and (pcm3.clacta between 115000 and 115999 or pcm3.clacta = 114004)) "
				+ "ORDER BY l.datlct";

		
		@SuppressWarnings("unchecked")
		List<Object[]> list = manager.createNativeQuery(sql).setParameter("ano", this.ano).setParameter("mes", this.mes).getResultList();
			
		for(Object[] o:list)
			writer.append("T"+o[0].toString()+";"+o[1]+";"+o[2]+";"+o[3]+";"+o[4]+";"+o[5]+";"+o[6]+";"+o[7]+";"+o[8].toString().replace("\n", "").replace("\r", "")+ "\r\n");

		writer.close();

		System.out.println("========== Arquivo de receitas10.txt gerado com "+ list.size()+" registros ===============");
	}

	public void gerarArquivoFornecedor() throws IOException{
		List<Integer> codigos= new ArrayList<Integer>();
		codigos.addAll(codigosFornecedores());
		
		File file = new File(SOURCE_FOLDER,"fornecedores10.txt");
		if(file.exists())
			file.delete();
		OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(file, true), "windows-1252");

		String sql ="select "
				+ "f.codfor,'10',f.tipfor, "
				+ "(CASE WHEN f.cgccpf=0 THEN f.codfor ELSE f.cgccpf end) as cnpjcpf,"
				+ "f.nomfor,f.apefor "
				+ "from e095for f where f.codfor in (:codigos) "
				+ "union all "
				+ "select "
				+ " '999'+ CONVERT(varchar(5), c.codcli) as fornecedor,'10',c.tipcli,"
				+ "(CASE WHEN c.cgccpf=0 THEN c.codcli WHEN c.cgccpf in (select f.cgccpf from e095for f) THEN c.codcli ELSE c.cgccpf end) as cnpjcpf"
				+ ",c.nomcli,c.apecli "
				+ "from e085cli c";
		
		
		@SuppressWarnings("unchecked")
		List<Object[]> list = manager.createNativeQuery(sql).setParameter("codigos", codigos).getResultList();

		for(Object[] o:list){
			if(o[3].toString().equals("61550141000172") || o[3].toString().equals("40432544083506") || 
									o[3].toString().equals("1404158000190") || o[3].toString().equals("1404158001838") || 
									o[3].toString().equals("191") || o[3].toString().equals("40432544082607")){
				writer.append(o[0]+";"+o[1]+";"+o[2]+";"+o[0]+";"+o[4]+";"+o[5].toString().replace("\n", "").replace("\r", "")+ "\r\n");
			}else{
				writer.append(o[0]+";"+o[1]+";"+o[2]+";"+o[3]+";"+o[4]+";"+o[5].toString().replace("\n", "").replace("\r", "")+ "\r\n");
			}
		}
	
		writer.close();

		System.out.println("========== Arquivo fornecedores10.txt gerado com "+ (list.size()+list.size())+" registros ===============");
	}

	
	public void gerarArquivoCliente() throws IOException{

		File file = new File(SOURCE_FOLDER,"clientes10.txt");
		if(file.exists())
			file.delete();
		OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(file, true), "windows-1252");
		
		String sql ="select "
				+ "c.codcli,'10',c.tipcli,(CASE WHEN c.cgccpf=0 THEN c.codcli "
				+ "WHEN (select count(cx.cgccpf) from e085cli cx where cx.cgccpf=c.cgccpf group by cx.cgccpf having count(cx.cgccpf)>1)>1 THEN c.codcli "
				+ "ELSE c.cgccpf end) as cnpjcpf,c.nomcli,c.apecli "
				+ "from e085cli c ";
		
		@SuppressWarnings("unchecked")
		List<Object[]> list = manager.createNativeQuery(sql).getResultList();
			
		for(Object[] o:list)
			writer.append(o[0]+";"+o[1]+";"+o[2]+";"+o[3]+";"+o[4]+";"+o[5].toString().replace("\n", "").replace("\r", "")+ "\r\n");

		writer.close();

		System.out.println("========== Arquivo clientes10.txt gerado com "+ list.size()+" registros ===============");
	}

	
	private Set<Integer> codigosFornecedores(){

		String sql ="select "
				+ "CONVERT(varchar(5), (CASE WHEN l.orilct='CPR' AND (SELECT lnf.codfor FROM E644LNF lnf WHERE lnf.codemp=l.codemp AND lnf.numlct=l.numlct AND lnf.codfil=l.codfil GROUP BY lnf.numlct,lnf.codfor) <>0  "
 				+ " THEN (SELECT lnf.codfor FROM E644LNF lnf WHERE lnf.codemp=l.codemp AND lnf.numlct=l.numlct AND lnf.codfil=l.codfil GROUP BY lnf.numlct,lnf.codfor)  "
                + " WHEN l.orilct='PAG' and (SELECT lti.codfor FROM E644LTI lti WHERE lti.codemp=l.codemp AND lti.numlct=l.numlct AND lti.codfil=l.codfil GROUP BY lti.numlct,lti.codfor) is null "
                + " THEN (SELECT MAX(f.codfor) FROM e095FOR f  WHERE f.cgccpf=l.cgccpf) WHEN l.orilct='TES' THEN 1322 WHEN l.orilct='PAT' THEN 1322 WHEN l.orilct='VRB' THEN 1322 WHEN l.orilct='MAN' THEN 1322 ELSE "
                + "CASE WHEN (SELECT MAX(f.codfor) FROM e095FOR f WHERE f.cgccpf=l.cgccpf) IS NULL THEN 1322 ELSE (SELECT MAX(f.codfor) FROM e095FOR f WHERE f.cgccpf=l.cgccpf) END END)) AS CODFOR "				
				+ "FROM e640lct l INNER JOIN e043rmp r ON (l.ctadeb=r.ctaant) INNER JOIN e043pcm p ON (p.codmpc=1005 AND p.anasin='A' AND p.ctared=r.ctaatu) "
				+ " WHERE l.sitlct=2 AND l.tiplct=1 AND year(l.datlct)= :ano AND MONTH(l.datlct)= :mes AND r.codmpa=1005 "
				+ " and l.ctadeb in (select x.ctared from e043pcm x where x.codmpc=1002 and x.clacta like '4%')  "

				+ " UNION "
				+ "select  "
				+ " '999'+ CONVERT(varchar(5),( CASE WHEN  l.orilct='PAG' THEN 444 WHEN  l.orilct='REC' THEN (select codcli from e301tcr rc inner join E644LTi tt on (tt.codemp=rc.codemp and tt.codfil=rc.codfil and tt.numtit=rc.numtit and tt.codtpt=rc.codtpt) where tt.numlct=l.numlct) WHEN  l.orilct='TES' THEN 444 WHEN  l.orilct='MAN' THEN 444 ELSE CASE WHEN (SELECT MAX(c.codcli) FROM e085cli c WHERE c.cgccpf=l.cgccre) is null THEN 444 ELSE (SELECT MAX(c.codcli) FROM e085cli c WHERE c.cgccpf=l.cgccre) end end )) AS CODCLI "
				+ " FROM e640lct l inner join e043rmp r on (l.ctacre=r.ctaant) "
				+ " inner join e043pcm p on (p.codmpc=1005 AND p.anasin='A' AND p.ctared=r.ctaatu) "
				+ " WHERE l.sitlct=2 AND l.tiplct=1 AND year(l.datlct)= :ano AND MONTH(l.datlct)= :mes AND r.codmpa=1005 and "
				+ "	l.ctacre in (select x.ctared from e043pcm x where x.codmpc=1002 and x.clacta like '3%') "
				+ " and p.clacta in (select pcm2.clacta from e043pcm pcm2 where pcm2.codmpc=1005 and (pcm2.clacta between 115000 and 115999 or pcm2.clacta = 114004))";		
		
		@SuppressWarnings("unchecked")
		List<Object[]> list = manager.createNativeQuery(sql).setParameter("ano", this.ano).setParameter("mes", this.mes)
				.getResultList();
		
		Set<Integer> codigos = new HashSet<Integer>();
		for(Object o:list)
			codigos.add(Integer.parseInt(o.toString()));
		return codigos;		
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

	public int getAno() {
		return ano;
	}

	public void setAno(int ano) {
		this.ano = ano;
	}

	public int getMes() {
		return mes;
	}

	public void setMes(int mes) {
		this.mes = mes;
	}


}
