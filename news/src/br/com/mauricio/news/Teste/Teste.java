package br.com.mauricio.news.Teste;

import java.io.IOException;
import java.util.List;

import br.com.mauricio.news.dao.contabil.MCTB001Dao;
import br.com.mauricio.news.model.contabil.MCTB001;
import br.com.mauricio.news.model.contabil.MCTB002;

public class Teste {

	public static void main(String[] args) {
		MCTB001Dao custodao = new MCTB001Dao();
		try {
			List<MCTB001> lancamentos = custodao.obterLancamentos(2016, 11);
			List<MCTB002> custos = custodao.obterCcu();
			for(MCTB002 c:custos)
				System.out.println(c.getPaiccu());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
