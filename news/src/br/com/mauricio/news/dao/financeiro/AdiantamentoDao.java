package br.com.mauricio.news.dao.financeiro;

import java.util.List;

import javax.persistence.EntityManager;

import br.com.mauricio.news.cnn.Conexao;
import br.com.mauricio.news.model.Login;
import br.com.mauricio.news.model.financeiro.Adiantamento;

public class AdiantamentoDao {
	private EntityManager manager;
	
	public AdiantamentoDao(EntityManager manager){
		this.manager = manager;
	}
	public AdiantamentoDao(){
		Conexao c = new Conexao();
		this.manager = c.getEntityManager();
	}

	@SuppressWarnings("unchecked")
	public List<Adiantamento> listByUsuario(Login usuario){		
		return this.manager.createQuery(" FROM adiantamento WHERE solicitante= :usuario or favorecido= :usuario order by id desc").setParameter("usuario", usuario).getResultList();
	}
}
