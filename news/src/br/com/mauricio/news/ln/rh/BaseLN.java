package br.com.mauricio.news.ln.rh;

import java.io.Serializable;
import java.util.List;

import br.com.mauricio.news.dao.rh.BaseDao;
import br.com.mauricio.news.model.rh.Base;
import br.com.mauricio.news.model.Login;

/**
 * @author Mauricio Cruz
 */
public class BaseLN implements Serializable {

	private static final long serialVersionUID = -745593021993066877L;


	public List<Base> listaPeriodosDisponiveis(Login l,int ano){
		BaseDao dao = new BaseDao();
		return dao.listaPorUsuario(l,ano);		
	}
	
	public Base getUltimaBasePorUsuario(Login l){
		BaseDao dao = new BaseDao();
		return dao.getUltimaBasePorUsuario(l);			
	}
}
