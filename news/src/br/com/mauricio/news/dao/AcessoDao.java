package br.com.mauricio.news.dao;

import javax.persistence.EntityManager;

import br.com.mauricio.news.cnn.Conexao;
import br.com.mauricio.news.model.Login;
/**
*
* @author MAURICIO CRUZ
*/
public class AcessoDao {
	private EntityManager manager;
	
	public AcessoDao(EntityManager manager){
		this.manager = manager;
	}
	public AcessoDao(){
		Conexao c = new Conexao();
		this.manager = c.getEntityManager();
	}

	public void deletarAcessos(Login l){		
		this.manager.createNativeQuery(" delete from acesso where login_id= :id").setParameter("id", l.getId()).executeUpdate();		
	}
	public void deletarPermissaoPrestacao(Login l) {
		this.manager.createNativeQuery(" delete from permissaoprestacao where permissao_de= :id").setParameter("id", l.getId()).executeUpdate();		
	}

}
