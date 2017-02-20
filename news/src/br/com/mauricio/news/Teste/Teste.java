package br.com.mauricio.news.Teste;

import java.io.IOException;
import java.util.Date;

import br.com.mauricio.news.dao.contabil.SigDao;
import br.com.mauricio.news.util.DateUtil;

public class Teste {


	public static void main(String[] args) throws IOException {

		SigDao dao = new SigDao();
		dao.gerar(2017, 1);
		
	}

}
