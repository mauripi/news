package br.com.mauricio.news.test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.apache.tomcat.jni.OS;
import org.joda.time.LocalDate;
import org.joda.time.Months;

import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;
import com.lowagie.text.ZapfDingbatsList;

import br.com.mauricio.news.dao.contabil.LancamentoContabilDao;
import br.com.mauricio.news.model.contabil.ContaValorTotal;
import br.com.mauricio.news.model.contabil.ContaValorTotalFilial;
import br.com.mauricio.news.model.contabil.LancamentoContabil;
import br.com.mauricio.news.model.contabil.OrcamentoSig;
import br.com.mauricio.news.model.contabil.RelacionamentoSig;
import br.com.mauricio.news.model.contabil.ValorSig;



public class LancaContTest {
	private static List<LancamentoContabil> todosLancamentos;
	private static Integer totalDeMeses = 0;
	private static Set<ContaValorTotal> consolidado = new HashSet<ContaValorTotal>();
	private static Set<ContaValorTotalFilial> consolidadoPorFilial = new HashSet<ContaValorTotalFilial>();
	private static Map<Integer,BigDecimal> medias = new HashMap<Integer,BigDecimal>();
	private static Map<Integer,Map<Integer,BigDecimal>> mediasFiliais = new HashMap<Integer,Map<Integer,BigDecimal>>();
	private static List<RelacionamentoSig> relacionamento = new ArrayList<RelacionamentoSig>();
	private static List<ValorSig> orcamento = new ArrayList<ValorSig>();
	private static BigDecimal divisor = BigDecimal.ZERO;
	private static Set<OrcamentoSig> orcamentoSig = new HashSet<OrcamentoSig>();
	private static BigDecimal negativo = new BigDecimal("-1");
	private static Set<ContaValorTotalFilial> orcadoMensalPorFilial = new HashSet<ContaValorTotalFilial>();
	
	public static void main(String[] args) {
System.out.println("Inicio:    " + new Date());			
		LancamentoContabilDao dao = new LancamentoContabilDao();
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		Date dataIni=null;
		Date dataFim=null;
		orcamento = new ArrayList<ValorSig>();
		
		try {
			dataIni = df.parse("01/01/2017");
			dataFim = df.parse("30/06/2017");
			
			Months m = Months.monthsBetween(new LocalDate(dataIni),new LocalDate(dataFim));
			totalDeMeses = m.getMonths()+1;
			divisor = new BigDecimal(totalDeMeses.toString());
			todosLancamentos = dao.obterLancamentos(dataIni, dataFim);
			relacionamento = dao.relacionamentoSig();
			agruparContas(todosLancamentos);
			totalizarContas();
			totalizarContasPorFilial();
			calculaMediaTotais();
			calculaMediaTotaisFiliais();
			
					
			carregaArquivoOrcamento("C:\\sap\\sig2.xlsx");
			Set<OrcamentoSig> orcamentoSigRemover = new HashSet<OrcamentoSig>();
			orcamentoSig.forEach(x -> {
				try{ 
					orcamento.add(calculaValoresDoSistema(x.getConta()));
				}catch(NoSuchElementException e){
					orcamentoSigRemover.add(x);
				}
			});
			orcamentoSigRemover.forEach(v -> System.out.println(v.getConta()));
			//orcamentoSig.removeAll(orcamentoSigRemover);
			//calculaValoresParaOrcamento();
			/*
			orcadoMensalPorFilial.stream().filter(o -> o.getCtared().equals(11055)).forEach(c -> {
				System.out.println("Conta: "+c.getCtared() + " - Filial: "+c.getFilrat());				
				Arrays.asList(c.getTotais()).forEach(System.out::println);
			});
			*/
		} catch (Exception e) {
			e.printStackTrace();
		}
System.out.println("Fim:    " + new Date());
	}
	
	private static BigDecimal calculaConta(BigDecimal atual, LancamentoContabil lanca){
		BigDecimal temp = new BigDecimal("-1");
		BigDecimal resultado = BigDecimal.ZERO;
		if(lanca.getDebcre().equals("D")) 
			resultado = lanca.getVlrrat().multiply(temp);
		else
			resultado = lanca.getVlrrat();
		return atual.add(resultado);		
	}

	private static void agruparContas (List<LancamentoContabil> lancamentos){
		for (LancamentoContabil lanca : lancamentos) {
			ContaValorTotal c = new ContaValorTotal();
			ContaValorTotalFilial cf = new ContaValorTotalFilial();
			c.setCtared(lanca.getCtared());
			cf.setCtared(lanca.getCtared());
			cf.setFilrat(lanca.getFilrat());				
			consolidado.add(c);
			consolidadoPorFilial.add(cf);
		}	
	}
	
	private static void totalizarContas(){
		for(ContaValorTotal v : consolidado){
			BigDecimal totais[] = new BigDecimal[12];
			Arrays.fill(totais, BigDecimal.ZERO);
			for (LancamentoContabil lanca : todosLancamentos) 
				if(lanca.getCtared().equals(v.getCtared()))
					totais[lanca.getMes()-1] = totais[lanca.getMes()-1].add(calculaConta(v.getTotais()[lanca.getMes()-1],lanca));	
			v.setTotais(totais);
		}
	}

	private static void totalizarContasPorFilial(){
		for(ContaValorTotalFilial v : consolidadoPorFilial){
			BigDecimal totais[] = new BigDecimal[12];
			Arrays.fill(totais, BigDecimal.ZERO);
			switch (v.getFilrat()) {
				case 1:
					for (LancamentoContabil lanca : todosLancamentos) 
						if(lanca.getCtared().equals(v.getCtared()) && lanca.getFilrat().equals(1))
							totais[lanca.getMes()-1] = totais[lanca.getMes()-1].add(calculaConta(v.getTotais()[lanca.getMes()-1],lanca));	
						
					v.setTotais(totais);
					break;
				case 2:
					for (LancamentoContabil lanca : todosLancamentos) 
						if(lanca.getCtared().equals(v.getCtared()) && lanca.getFilrat().equals(2))
							totais[lanca.getMes()-1] = totais[lanca.getMes()-1].add(calculaConta(v.getTotais()[lanca.getMes()-1],lanca));
						
					v.setTotais(totais);				
					break;
				case 3:
					for (LancamentoContabil lanca : todosLancamentos) 
						if(lanca.getCtared().equals(v.getCtared()) && lanca.getFilrat().equals(3))
							totais[lanca.getMes()-1] = totais[lanca.getMes()-1].add(calculaConta(v.getTotais()[lanca.getMes()-1],lanca));							
					v.setTotais(totais);				
					break;
			}
		}
	}
	
	private static void calculaMediaTotais(){
		for(ContaValorTotal v : consolidado){	
			BigDecimal total = BigDecimal.ZERO;
			for(int i=0; i<totalDeMeses;i++)
				total = total.add(v.getTotais()[i]);		
			if(total.compareTo(BigDecimal.ZERO) <= 0)
				total = total.multiply(negativo);			
			BigDecimal media = total.divide(divisor, 2, RoundingMode.HALF_UP);
			medias.put(v.getCtared(), media);	
		}
	}
	
	private static void calculaMediaTotaisFiliais(){
		BigDecimal total = BigDecimal.ZERO;
		BigDecimal media = BigDecimal.ZERO;
		Map<Integer,BigDecimal> mediaFil = new HashMap<Integer,BigDecimal>();
		
		for(ContaValorTotalFilial v : consolidadoPorFilial){			
			total = BigDecimal.ZERO;
			media = BigDecimal.ZERO;
			for(int i=0; i<totalDeMeses;i++)
				total = total.add(v.getTotais()[i]);					
			if(total.compareTo(BigDecimal.ZERO) <= 0)
				total = total.multiply(negativo);			
			media = total.divide(divisor, 2, RoundingMode.HALF_UP);
			mediaFil.put(v.getFilrat(), media);
			mediasFiliais.put(v.getCtared(), mediaFil);							
		}
	}
	
	private static List<Integer> contasRelacionadas(int cantaSig){
		return relacionamento.stream()
			.filter(c -> c.getCtasig().equals(cantaSig) && medias.containsKey(c.getCtared()))
			.map(map->map.getCtared())
			.collect(Collectors.toList());		 		
	}

	private static ValorSig calculaValoresDoSistema(int cantaSig){
		ValorSig pai = new ValorSig();
		pai.setParentes(new ArrayList<ValorSig>());
		pai.setChave(cantaSig);
		pai.setPersis(new BigDecimal("100.0"));
		
		List<Integer> contas = contasRelacionadas(cantaSig);

		BigDecimal mediaTotal = contas.stream().map(c -> medias.get(c)).reduce(BigDecimal::add).get();
		pai.setVlrsis(mediaTotal);
		
		contas.forEach(c -> {
			ValorSig filho = new ValorSig();
			filho.setParentes(new ArrayList<ValorSig>());
			filho.setChave(c);
			filho.setVlrsis(medias.get(c));
			filho.setPersis(filho.getVlrsis().divide(pai.getVlrsis(), 4, RoundingMode.HALF_UP));

			consolidadoPorFilial.stream().filter(cf -> cf.getCtared().equals(filho.getChave()))
				.forEach(cf -> {				
					ValorSig neto = new ValorSig();
					neto.setChave(cf.getFilrat());
					neto.setVlrsis((Arrays.stream(cf.getTotais()).reduce(BigDecimal::add).get()).divide(divisor, 2, RoundingMode.HALF_UP).multiply(negativo));
					neto.setPersis(neto.getVlrsis().divide(filho.getVlrsis(), 4, RoundingMode.HALF_UP));
					filho.getParentes().add(neto);
				});
			
			pai.getParentes().add(filho);
		});		
		return pai;	
	}
	
	private static void calculaValoresParaOrcamento(){
		//orcamento.stream().filter(x -> x.getChave().equals(6020)).forEach(pai -> {		
		orcamento.stream().forEach(pai -> {	
			orcamentoSig.stream().filter(y -> y.getConta().equals(pai.getChave())).forEach(os -> {
				OrcamentoSig orcSig = os;
	//System.out.println("Conta SIG : "+pai.getChave());			
	//System.out.println("SIG Janeiro: "+orcSig.getTotais()[0]);
				pai.getParentes().forEach(f -> {
					ValorSig conta = f;
	//System.out.println("Conta Contabil: "+f.getChave());
//	System.out.println("Conta Contabil Percentual: "+f.getPersis());
					f.getParentes().forEach(n -> {
	//System.out.println("Filial: "+ n.getChave() +  "    Percentual = "+ n.getPersis()+"    Valor = "+ orcSig.getTotais()[0].multiply(conta.getPersis()).multiply(n.getPersis()).setScale(2, RoundingMode.HALF_UP));
						ValorSig fil = n;
						ContaValorTotalFilial filial = new ContaValorTotalFilial();
						BigDecimal totais[] = new BigDecimal[12];
						filial.setCtared(conta.getChave());
						filial.setFilrat(fil.getChave()); 
						for (int i=0;i < orcSig.getTotais().length;i++){
							totais[i] = orcSig.getTotais()[i].multiply(conta.getPersis()).multiply(fil.getPersis()).setScale(2, RoundingMode.HALF_UP);
						}
						filial.setTotais(totais);
						orcadoMensalPorFilial.add(filial);							
					});	
	//System.out.println("_____________________________");
//	System.out.println("");
				});
			});
		});
	}
		
		

	private static void carregaArquivoOrcamento(String caminho){
		System.setProperty("ROW", "1");
		Fillo fillo=new Fillo();
		Connection connection;
		try {
			connection = fillo.getConnection(caminho);
			String strQuery="Select * from plan5 ";
			Recordset recordset=connection.executeQuery(strQuery);
					
			while(recordset.next()){
				OrcamentoSig o = new OrcamentoSig();
				BigDecimal totais[] = new BigDecimal[12];
				o.setConta(Integer.parseInt(recordset.getField("conta")));
				totais[0] = new BigDecimal(recordset.getField("Janeiro").toString());
				totais[1] = new BigDecimal(recordset.getField("Fevereiro").toString());
				totais[2] = new BigDecimal(recordset.getField("Março").toString());
				totais[3] = new BigDecimal(recordset.getField("Abril").toString());
				totais[4] = new BigDecimal(recordset.getField("Maio").toString());
				totais[5] = new BigDecimal(recordset.getField("Junho").toString());
				totais[6] = new BigDecimal(recordset.getField("Julho").toString());
				totais[7] = new BigDecimal(recordset.getField("Agosto").toString());
				totais[8] = new BigDecimal(recordset.getField("Setembro").toString());
				totais[9] = new BigDecimal(recordset.getField("Outubro").toString());
				totais[10] = new BigDecimal(recordset.getField("Novembro").toString());
				totais[11] = new BigDecimal(recordset.getField("Dezembro").toString());
				o.setTotais(totais);
				orcamentoSig.add(o);
			}		
			recordset.close();
			connection.close();		
		
		} catch (FilloException e) {
			e.printStackTrace();
		}


	}
}
