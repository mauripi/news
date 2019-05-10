package br.com.mauricio.news.test;

import java.util.List;

import br.com.mauricio.news.dao.contabil.GestaoNotaFiscalDao;
import br.com.mauricio.news.model.contabil.GestaoNotaFiscal;

public class GestaoNFTeste {

	public static void main(String[] args) {
		GestaoNotaFiscalDao dao = new GestaoNotaFiscalDao();
		List<GestaoNotaFiscal> notas = dao.buscarPorCompetencia(4, 2019);
		notas.forEach(n->System.out.println(n.getVlrbru() + " => " + n.getStatus() + " => " + n.getNumnfc() + " => " + n.getCodfil() + " => " + n.getNumctr() ));
	}

}
