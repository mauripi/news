package br.com.mauricio.news.dao;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

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

	public Map<Boolean, Login> recuperaSenha(String cpf){
		Map<Boolean, Login> result = new HashMap<Boolean, Login>();
		Login lg = null;
		int removido = 0;
		boolean ativo = true;
		String sql = " From login l WHERE l.cpf = :cpf AND l.removido =:removido AND l.ativo = :ativo";
		Query query = this.manager.createQuery(sql );   
		query.setParameter("removido", removido); 
		query.setParameter("ativo", ativo); 
		query.setParameter("cpf", cpf);				

		try{
			lg = (Login) query.getResultList().get(0); 
			result.put(true, lg);
	    }catch(NoResultException e){
		    System.out.println("Usuário : " +cpf +" tentando efetuar login" + e.getLocalizedMessage());
		    lg = null;
		    result.put(false, lg);
	    } catch (Exception e) {
		    System.out.println("Usuário : " +cpf +" tentando efetuar login" + e.getLocalizedMessage());
		    lg = null;
		    result.put(false, lg);
		} 
		  
		return result;		
	}
}
