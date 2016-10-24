package br.com.mauricio.news.ln;

import java.io.Serializable;
import java.util.List;

import br.com.mauricio.news.dao.ModuloDao;
import br.com.mauricio.news.model.Modulo;
/**
*
* @author MAURICIO CRUZ
*/
public class ModuloLN implements Serializable {

	private static final long serialVersionUID = 3695916168631238801L;

		
	public List<Modulo> listaModulos(){
		ModuloDao dao = new ModuloDao();
		return dao.listaModulo();
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
