package br.com.mauricio.news.dao.contabil;

import java.util.List;

import javax.persistence.EntityManager;

import br.com.mauricio.news.cnn.Conexao;
import br.com.mauricio.news.model.Login;
import br.com.mauricio.news.model.contabil.BaixaBem;


/**
*
* @author MAURICIO CRUZ
*/
public class BaixaBemDao{
	private EntityManager manager;
	
	public BaixaBemDao(){
		Conexao c = new Conexao();
		this.manager = c.getEntityManager();
	}
	public BaixaBemDao(EntityManager manager){
		this.manager = manager;
	}
		

	@SuppressWarnings("unchecked")
	public List<BaixaBem> listaPorUsuario(Login usuario) {
		String s = "%-00";
		return this.manager.createQuery(" from baixabem where solicitante = :usuario and patrimonio like :patrimonio order by id desc")
				.setParameter("usuario", usuario).setParameter("patrimonio", s).getResultList();		
	}
	

}
