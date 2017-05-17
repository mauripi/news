package br.com.mauricio.news.dao.contabil;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.com.mauricio.news.cnn.Conexao;
import br.com.mauricio.news.model.Login;
import br.com.mauricio.news.model.contabil.BaixaBem;


/**
*
* @author MAURICIO CRUZ
*/
public class BaixaBemDao{
	private EntityManager manager;
	private CriteriaBuilder cb;
	
	public BaixaBemDao(){
		Conexao c = new Conexao();
		this.manager = c.getEntityManager();
		cb = this.manager.getCriteriaBuilder();
	}
	
	public BaixaBemDao(EntityManager manager){
		this.manager = manager;
		cb = this.manager.getCriteriaBuilder();
	}
		
	public List<BaixaBem> getByUser(Login usuario) {
        CriteriaQuery<BaixaBem> query = cb.createQuery(BaixaBem.class);
        Root<BaixaBem> root = query.from(BaixaBem.class);        
        Path<Login> path = root.get("solicitante");        
        Predicate predicate = cb.equal(path, usuario);       
        query.where(predicate);
        TypedQuery<BaixaBem> typedQuery = manager.createQuery(query);        
        return typedQuery.getResultList();
	}
	

}
