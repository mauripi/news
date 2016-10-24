package br.com.mauricio.news.dao.contabil;

import java.util.List;

import javax.persistence.EntityManager;

import br.com.mauricio.news.cnn.Conexao;
import br.com.mauricio.news.model.Login;
import br.com.mauricio.news.model.contabil.ItemMovimento;
import br.com.mauricio.news.model.contabil.MovimentoBem;


/**
*
* @author MAURICIO CRUZ
*/
public class MovimentoBemDao{
	private EntityManager manager;
	
	public MovimentoBemDao(){
		Conexao c = new Conexao();
		this.manager = c.getEntityManager();
	}
	public MovimentoBemDao(EntityManager manager){
		this.manager = manager;
	}
		

	@SuppressWarnings("unchecked")
	public List<MovimentoBem> listaPorUsuario(Login usuario) {
		return this.manager.createQuery(" from movimentobem where solicitante = :usuario order by id desc")
				.setParameter("usuario", usuario).getResultList();		
	}
	
	public void deleteItens(List<ItemMovimento> list){
		for(ItemMovimento i:list)
			this.manager.remove(manager.getReference(ItemMovimento.class, i.getId()));
		this.manager.flush();
	}
    
}
