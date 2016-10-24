package br.com.mauricio.news.ln.rh;

import java.io.Serializable;
import java.util.List;

import br.com.mauricio.news.dao.rh.InformeDao;
import br.com.mauricio.news.model.Login;
import br.com.mauricio.news.model.rh.Informe;

public class InformeLN implements Serializable {

	private static final long serialVersionUID = 1L;

	public List<Informe> getLista(Login usuarioLogado) {
		InformeDao dao = new InformeDao();
		return dao.listaPorUsuario(usuarioLogado);
	}

}
