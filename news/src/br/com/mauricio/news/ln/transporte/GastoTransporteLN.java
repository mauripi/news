package br.com.mauricio.news.ln.transporte;

import java.io.Serializable;
import java.util.List;

import br.com.mauricio.news.dao.transporte.GastoTransporteDao;
import br.com.mauricio.news.model.transporte.GastoTransporte;
import br.com.mauricio.news.model.transporte.ViewGastoTrans;
/**
*
* @author MAURICIO CRUZ
*/
public class GastoTransporteLN  implements Serializable {

	private static final long serialVersionUID = 1L;
	private GastoTransporteDao dao;

	public boolean exiteNaBase(GastoTransporte gasto) {
		dao = new GastoTransporteDao();
		return dao.existeNaBase(gasto);
	}

	public boolean existeNaBaseMaisDeUm(GastoTransporte gasto) {
		dao = new GastoTransporteDao();
		return dao.existeNaBaseMaisDeUm(gasto);
	}
	
	public List<ViewGastoTrans> getGastosConsolidados(int ano){
		dao = new GastoTransporteDao();
		return dao.getGastosConsolidados(ano);
	}
	
}
