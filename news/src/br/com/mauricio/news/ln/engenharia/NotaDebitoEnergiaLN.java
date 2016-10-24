package br.com.mauricio.news.ln.engenharia;

import java.io.Serializable;
import java.util.List;

import br.com.mauricio.news.dao.engenharia.NotaDebitoEnergiaDao;
import br.com.mauricio.news.model.engenharia.NotaDebitoEnergia;
import br.com.mauricio.news.model.engenharia.ViewNotaDebito;
/**
*
* @author MAURICIO CRUZ
*/
public class NotaDebitoEnergiaLN  implements Serializable {

	private static final long serialVersionUID = 1L;
	private NotaDebitoEnergiaDao dao;

	public boolean exiteNaBase(NotaDebitoEnergia debito) {
		dao = new NotaDebitoEnergiaDao();
		return dao.existeNaBase(debito);
	}

	public boolean existeNaBaseMaisDeUm(NotaDebitoEnergia debito) {
		dao = new NotaDebitoEnergiaDao();
		return dao.existeNaBaseMaisDeUm(debito);
	}
	
	public List<ViewNotaDebito> getNotaDebitoConsolidados(int ano){
		dao = new NotaDebitoEnergiaDao();
		return dao.getNotaDebitoConsolidados(ano);
	}
	
}
