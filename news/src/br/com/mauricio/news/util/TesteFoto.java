package br.com.mauricio.news.util;

import br.com.mauricio.news.dao.LoginFotoDao;
import br.com.mauricio.news.model.Login;

public class TesteFoto {

	public static void main(String[] args) {
		LoginFotoDao dao = new LoginFotoDao();
		dao.userFoto(new Login());
	}

}
