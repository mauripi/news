package br.com.mauricio.news.test;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import br.com.mauricio.news.dao.financeiro.FluxoCaixaDao;
import br.com.mauricio.news.model.financeiro.ClassificacaoFluxoCaixa;
import br.com.mauricio.news.model.financeiro.FluxoCaixa;

public class TestaFluxodeCaixa {

	public static void main(String[] args) {
		FluxoCaixaDao dao = new FluxoCaixaDao();
		dao.adicionar(adicionaTeste());
		/*dao.alterar(AlterarTeste());*/
		
		/*dao.remover(8212);*/
		List<FluxoCaixa> movimentos = dao.listar();
		movimentos.forEach(m -> System.out.println(m.getId() + " - " + m.getData() +" - " + m.getValor()));

		/*
		List<ClassificacaoFluxoCaixa> classif = dao.listarClassificacao();
		classif.forEach(m -> System.out.println(m.getCtared() + " - " + m.getDescta()));
		*/
	}

	private static FluxoCaixa adicionaTeste(){
		FluxoCaixa f = new FluxoCaixa();
		f.setClifor("Mauricio de Oliveira da Cruz");
		f.setCodccu("30001");
		f.setCodtns("");
		f.setCtafin(0);
		f.setCtared(160);
		f.setData(new Date(System.currentTimeMillis()));
		f.setDesccu("MATRIZ");
		f.setDesctafin("");
		f.setDesctared("");
		f.setNumtit("MUMU_19022019");
		f.setObservacao("Teste de Gravação de Movimento através da interface");
		f.setOrilct("M");
		f.setProrea("PROJETADO");
		f.setValor(new BigDecimal("100.99"));
		
		return f;		
	}
	
	private static FluxoCaixa AlterarTeste(){
		FluxoCaixa f = new FluxoCaixa();
		f.setId(8212);
		f.setClifor("Mauricio de Oliveira da Cruz");
		f.setCodccu("30002");
		f.setCodtns("");
		f.setCtafin(0);
		f.setCtared(160);
		f.setData(new Date(System.currentTimeMillis()));
		f.setDesccu("MATRIZ");
		f.setDesctafin("");
		f.setDesctared("");
		f.setNumtit("MUMU_190220155");
		f.setObservacao("Teste de Gravação de Movimento através da interface");
		f.setOrilct("M");
		f.setProrea("REALIZADO");
		f.setValor(new BigDecimal("100.99"));
		
		return f;		
	}
}
