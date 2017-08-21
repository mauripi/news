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
import java.util.Set;
import java.util.stream.Collectors;

import org.joda.time.LocalDate;
import org.joda.time.Months;

import br.com.mauricio.news.dao.contabil.LancamentoContabilDao;
import br.com.mauricio.news.model.contabil.ContaValorTotal;
import br.com.mauricio.news.model.contabil.ContaValorTotalFilial;
import br.com.mauricio.news.model.contabil.LancamentoContabil;
import br.com.mauricio.news.model.contabil.RelacionamentoSig;
import br.com.mauricio.news.model.contabil.ValorSig;

public class LancaContTest {
	private static List<LancamentoContabil> todosLancamentos;
	private static int totalDeMeses = 0;
	private static Set<ContaValorTotal> consolidado = new HashSet<ContaValorTotal>();
	private static Set<ContaValorTotalFilial> consolidadoPorFilial = new HashSet<ContaValorTotalFilial>();
	private static Map<Integer,BigDecimal> medias = new HashMap<Integer,BigDecimal>();
	private static Map<Integer,Map<Integer,BigDecimal>> mediasFiliais = new HashMap<Integer,Map<Integer,BigDecimal>>();
	private static List<RelacionamentoSig> relacionamento = new ArrayList<RelacionamentoSig>();
	private static List<ValorSig> orcamento = new ArrayList<ValorSig>();
	private static BigDecimal divisor = BigDecimal.ZERO;
	
	
	
	public static void main(String[] args) {
System.out.println("Inicio:    " + new Date());			
		LancamentoContabilDao dao = new LancamentoContabilDao();
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		Date dataIni=null;
		Date dataFim=null;
		try {
			dataIni = df.parse("01/01/2017");
			dataFim = df.parse("30/06/2017");
			
			Months m = Months.monthsBetween(new LocalDate(dataIni),new LocalDate(dataFim));
			totalDeMeses = m.getMonths()+1;
			divisor = new BigDecimal(totalDeMeses);
			todosLancamentos = dao.obterLancamentos(dataIni, dataFim);
			relacionamento = dao.relacionamentoSig();
			agruparContas(todosLancamentos);
			totalizarContas();
			totalizarContasPorFilial();
			calculaMediaTotais();
			calculaMediaTotaisFiliais();
			
			

			ValorSig pai = calculaValoresDoSistema(6001);
			
			System.out.println("Conta sig " + pai.getChave());
			System.out.println("Total da conta " + pai.getVlrsis());
			System.out.println("");
			pai.getParentes().forEach(f -> {
				System.out.println("Conta contabil " + f.getChave());
				System.out.println("Total da conta contabil :  " + f.getVlrsis());		
				System.out.println("Percentual da conta contabil  :  " + f.getPersis());
				System.out.println("");
				f.getParentes().forEach(n -> {
					System.out.println("Filial " + n.getChave());
					System.out.println("Total da filial :  " + n.getVlrsis());		
					System.out.println("Percentual da filial  :  " + n.getPersis());	
					System.out.println("");
				});
				System.out.println("");
				System.out.println("");
			});
			
			
			
			
			//mediaTotalDoGrupoSig(6001);
			/* System.out.println(mediasFiliais);
			System.out.println(mediasFiliais.get(10434));
			System.out.println(mediasFiliais.get(11767));
		
			/*System.out.println(medias);
			*/
			//BigDecimal percentualFilial1 = mediasFiliais.get(11100).get(1).divide(medias.get(11100), 2, RoundingMode.HALF_EVEN);
			//System.out.println(percentualFilial1);
		} catch (Exception e) {
			e.printStackTrace();
		}
System.out.println("Fim:    " + new Date());
	}
	
	private static BigDecimal calculaConta(BigDecimal atual, LancamentoContabil lanca){
		BigDecimal temp = new BigDecimal(-1);
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
		BigDecimal negativo = new BigDecimal(-1);

		for(ContaValorTotal v : consolidado){			
			BigDecimal total = BigDecimal.ZERO;
			for(int i=0; i<totalDeMeses;i++)
				total = total.add(v.getTotais()[i]);			
			if(total.compareTo(BigDecimal.ZERO) <= 0)
				total = total.multiply(negativo);			
			BigDecimal media = total.divide(divisor, 2, RoundingMode.HALF_EVEN);
			medias.put(v.getCtared(), media);	
		}
	}
	
	private static void calculaMediaTotaisFiliais(){
		BigDecimal negativo = new BigDecimal(-1);
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
			media = total.divide(divisor, 2, RoundingMode.HALF_EVEN);
			mediaFil.put(v.getFilrat(), media);
			mediasFiliais.put(v.getCtared(), mediaFil);							
		}
	}
	
	private static List<Integer> contasRelacionadas(int cantaSig){
		return relacionamento.stream()
			.filter(c -> c.getCtasig().equals(cantaSig))
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
			filho.setPersis(pai.getVlrsis().divide(filho.getVlrsis(), 2, RoundingMode.HALF_EVEN));

			consolidadoPorFilial.forEach(cf -> {
				ValorSig neto = new ValorSig();
				neto.setChave(cf.getFilrat());
				neto.setVlrsis((Arrays.stream(cf.getTotais()).reduce(BigDecimal::add).get()).divide(divisor, 2, RoundingMode.HALF_EVEN));
				neto.setPersis(filho.getVlrsis().divide(neto.getVlrsis(), 2, RoundingMode.HALF_EVEN));
				filho.getParentes().add(neto);
			});
			
			pai.getParentes().add(filho);
		});
		
		return pai;	
	}
}
