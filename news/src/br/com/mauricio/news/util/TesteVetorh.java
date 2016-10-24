package br.com.mauricio.news.util;

import br.com.mauricio.news.dao.rh.VetorhDao;

public class TesteVetorh {

	public static void main(String[] args) {
		VetorhDao dao = new VetorhDao();
		dao.buscaEventosFolha(2015, 8, 11);

	}

}
