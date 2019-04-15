package br.com.mauricio.news.ln.ti;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import br.com.mauricio.news.cnn.Conexao;
import br.com.mauricio.news.dao.GenericDao;
import br.com.mauricio.news.dao.ti.ConsumoEmbratelDao;
import br.com.mauricio.news.model.ti.ConsumoEmbratel;
import br.com.mauricio.news.model.ti.ConsumoEmbratelDesconto;
import br.com.mauricio.news.model.ti.RateioFinalEmbratel;

public class ConsumoEmbratelLN implements Serializable {

	private static final long serialVersionUID = 1L;
	private ConsumoEmbratelDao consumoDao;
	private GenericDao<ConsumoEmbratelDesconto> daoD;
	private GenericDao<ConsumoEmbratel> dao;
	private EntityManager manager; 
 
	public ConsumoEmbratelLN (){
		Conexao c = new Conexao();
		manager = c.getEntityManager();		
	}
	
	public ConsumoEmbratelLN(EntityManager manager) {
		this.manager = manager;
	}

	public String carregaDescontoArquivo(String arquivo,String fatura){
		try{
			consumoDao = new ConsumoEmbratelDao();
			List<ConsumoEmbratelDesconto> descontos = consumoDao.carregaDescontoArquivo(arquivo, fatura);
			daoD = new GenericDao<ConsumoEmbratelDesconto>(manager);
			daoD.saveList(descontos);		
			return "Arquivo importado com sucesso";
		}catch(Exception e){
			System.out.println(e);
			return "Erro ao importar arquivo. Verifique o log.";
		}
	}
	
	public String carregaArquivo(String arquivo,String fatura){
		try{
			consumoDao = new ConsumoEmbratelDao();
			List<ConsumoEmbratel> consumos = consumoDao.carregaArquivo(arquivo, fatura);
			dao = new GenericDao<ConsumoEmbratel>(manager);
			dao.saveList(consumos);		
			return "Arquivo importado com sucesso";
		}catch(Exception e){
			System.out.println(e);
			return "Erro ao importar arquivo. Verifique o log.";
		}
	}
	
	public Map<String,BigDecimal> rateio(String fatura){
		consumoDao = new ConsumoEmbratelDao(manager);		
		return consumoDao.rateio(fatura);		
	}

	public List<RateioFinalEmbratel> rateioFinal(String fatura){
		consumoDao = new ConsumoEmbratelDao(manager);		
		return calculaProporcao(consumoDao.rateioFinal(fatura));		
	}
	
	private List<RateioFinalEmbratel> calculaProporcao(List<RateioFinalEmbratel> rateioFinal) {
		List<RateioFinalEmbratel> ratsF1 = rateioFinal.stream().filter(x -> (x.getFilial().equals(1) && x.getServico().contains("VPE") && !x.getTipo().contains("Desconto"))).collect(Collectors.toList());
		List<RateioFinalEmbratel> ratsF2 = rateioFinal.stream().filter(x -> (x.getFilial().equals(2) && x.getServico().contains("VPE") && !x.getTipo().contains("Desconto"))).collect(Collectors.toList());
		List<RateioFinalEmbratel> ratsF3 = rateioFinal.stream().filter(x -> (x.getServico().contains("ZFT") && !x.getTipo().contains("Desconto"))).collect(Collectors.toList());
		BigDecimal DescTotalFilial1 = rateioFinal.stream().filter(x -> (x.getFilial().equals(1) && x.getTipo().contains("Desconto"))).map((x) -> x.getValor()).reduce(BigDecimal.ZERO, BigDecimal::add);
		BigDecimal DescTotalFilial2 = rateioFinal.stream().filter(x -> (x.getFilial().equals(2) && x.getTipo().contains("Desconto"))).map((x) -> x.getValor()).reduce(BigDecimal.ZERO, BigDecimal::add);
		BigDecimal totalFilial1 = ratsF1.stream().map((x) -> x.getValor()).reduce(BigDecimal.ZERO, BigDecimal::add);
		BigDecimal totalFilial2 = ratsF2.stream().map((x) -> x.getValor()).reduce(BigDecimal.ZERO, BigDecimal::add);

		ratsF1.forEach(r -> r.setValor(r.getValor().add((r.getValor().multiply(new BigDecimal("100.0")).divide(totalFilial1, 3, RoundingMode.HALF_UP)).multiply(DescTotalFilial1).divide(new BigDecimal("100.0"), 3, RoundingMode.HALF_UP))));
		ratsF2.forEach(r -> r.setValor(r.getValor().add((r.getValor().multiply(new BigDecimal("100.0")).divide(totalFilial2, 3, RoundingMode.HALF_UP)).multiply(DescTotalFilial2).divide(new BigDecimal("100.0"), 3, RoundingMode.HALF_UP))));
		
		List<RateioFinalEmbratel> list = new ArrayList<RateioFinalEmbratel>();
		list.addAll(ratsF1);
		list.addAll(ratsF2);
		list.addAll(ratsF3);
		return list;
	}

	public boolean faturaJaExiste(String fatura) {
		consumoDao = new ConsumoEmbratelDao(manager);
		return consumoDao.faturaJaExiste(fatura);
	}
}
