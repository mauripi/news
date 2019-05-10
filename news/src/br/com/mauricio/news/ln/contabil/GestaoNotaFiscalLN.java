package br.com.mauricio.news.ln.contabil;

import java.io.Serializable;
import java.util.List;

import br.com.mauricio.news.dao.contabil.GestaoNotaFiscalDao;
import br.com.mauricio.news.model.contabil.GestaoNotaFiscal;

public class GestaoNotaFiscalLN implements Serializable{

	private static final long serialVersionUID = 1L;
	private GestaoNotaFiscalDao dao;
	
	
	
	public List<GestaoNotaFiscal> buscarPorCompetencia(Integer mes, Integer ano){
		dao = new GestaoNotaFiscalDao();
		return dao.buscarPorCompetencia(mes, ano);
	}

}
