package br.com.mauricio.news.dao.financeiro;

import java.util.List;

import javax.persistence.EntityManager;
import br.com.mauricio.news.model.Login;
import br.com.mauricio.news.model.financeiro.PrestacaoConta;
/**
*
* @author MAURICIO CRUZ
*/
public class PrestacaoContaDao {
	private EntityManager manager;
	
	public PrestacaoContaDao(EntityManager manager){
		this.manager = manager;
	}

	@SuppressWarnings("unchecked")
	public List<PrestacaoConta> list(Login funcionario) {
		int remove=0;
		return manager.createQuery(" FROM prestacaoconta p where p.colaborador= :c and p.removido= :remove order by p.id desc")
				.setParameter("c", funcionario)
				.setParameter("remove", remove)
				.getResultList();
	}

}
