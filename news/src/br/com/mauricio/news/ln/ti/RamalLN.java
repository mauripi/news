package br.com.mauricio.news.ln.ti;

import java.io.Serializable;
import br.com.mauricio.news.dao.ti.RamalDao;
import br.com.mauricio.news.model.Ramal;


public class RamalLN implements Serializable {

	private static final long serialVersionUID = 1L;
	private RamalDao dao;

	public String excluir(Ramal ramal) {
		dao = new RamalDao();
		try {
			dao.excluir(ramal);
			return "Excluído com sucesso!";
		}
		catch(Exception e){
			System.out.println(e);
		}
		return " ";
	}
		

}
