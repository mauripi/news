package br.com.mauricio.news.dao;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import br.com.mauricio.news.cnn.Conexao;
import br.com.mauricio.news.model.Login;
import br.com.mauricio.news.util.Cripto;
/**
*
* @author MAURICIO CRUZ
*/
public class LoginDao {
	
	private EntityManager manager;
	
	public LoginDao(){
		Conexao c = new Conexao();
		this.manager = c.getEntityManager();
	}

	public Boolean validaLogin(String cpf, String senha) { 
		  Login lg = null;
		  int removido = 0;
		  boolean ativo = true;
	      Query query = this.manager.createQuery("From login l WHERE l.cpf = :cpf AND l.senha = :senha AND l.removido = :removido AND l.ativo = :ativo order by l.chapa desc" );   
	      query.setParameter("removido", removido); 
	      query.setParameter("ativo", ativo); 
	      query.setParameter("cpf", cpf);   
	      try {
			query.setParameter("senha", Cripto.criptografa(senha));
	      } catch (NoSuchAlgorithmException e) {			
	    	  System.out.println("Usuário : " +cpf +" tentando efetuar login" + e.getLocalizedMessage());
			lg = null;
	      }    
	  
	      try{
	    	  lg = (Login) query.getResultList().get(0); 
	      }catch(NoResultException e){
	    	  System.out.println("Usuário : " +cpf +" tentando efetuar login" + e.getLocalizedMessage());
	    	  lg = null;
	      } catch (Exception e) {
	    	  System.out.println("Usuário : " +cpf +" tentando efetuar login" + e.getLocalizedMessage());
	    	  lg = null;
		}     
		   return (lg != null);   
	}

	public Boolean exiteLogin(String chapa) { 
		  Login lg = null;
	      Query query = this.manager.createQuery("From login l WHERE l.chapa = :chapa");   	     
	      query.setParameter("chapa",chapa); 
	      try{
	    	  lg = (Login) query.getSingleResult(); 
	      }catch(NoResultException e){
	    	  System.out.println("Usuário : " +chapa +" tentando efetuar login" + e.getLocalizedMessage());
	    	  lg = null;
	      }	            
		   return (lg != null);   
	}	

	public Login retornaUsuario(String chapa) { 
		Login lg = null;
		   try {   
		      Query query = this.manager.createQuery(" FROM login l WHERE l.chapa = :chapa");   		     
		      query.setParameter("chapa", chapa);     		  
		      lg = (Login) query.getSingleResult();       
		   } catch (NoResultException e) {   
			   System.out.println("Usuário : " +chapa +" tentando efetuar login" + e.getLocalizedMessage());
			   lg = null;   
		   } catch (RuntimeException e) {
			   System.out.println("Usuário : " +chapa +" tentando efetuar login" + e.getLocalizedMessage());
			   lg = null;
		      e.printStackTrace();   
		   }   		     
		   return lg;   
		}  

	public Login retornaUsuario(String cpf, String senha) { 
		  Login lg = null;
		  int removido = 0;
		  boolean ativo = true;
		   try {   
		      Query query = this.manager.createQuery(" From login l WHERE l.cpf = :cpf AND l.senha = :senha AND l.removido = :removido AND l.ativo = :ativo order by l.chapa desc");   
		      query.setParameter("removido", removido); 
		      query.setParameter("ativo", ativo); 		     
		      query.setParameter("cpf", cpf);   
		      try {
					query.setParameter("senha", Cripto.criptografa(senha));
		      } catch (NoSuchAlgorithmException e) {			
		    	  System.out.println("Usuário : " +cpf +" tentando efetuar login" + e.getLocalizedMessage());
				lg = null;
		      }  
		      try{
		    	  lg = (Login) query.getResultList().get(0); 
		      }catch(NoResultException e){
		    	  System.out.println("Usuário : " +cpf +" tentando efetuar login" + e.getLocalizedMessage());
		    	  lg = null;
		      }     
		   } catch (NoResultException e) {  
		    	  System.out.println("Usuário : " +cpf +" tentando efetuar login" + e.getLocalizedMessage());
			   lg = null;   
		   } catch (RuntimeException e) { 
		    	  System.out.println("Usuário : " +cpf +" tentando efetuar login" + e.getLocalizedMessage());
		      e.printStackTrace();   
		   }   		     
		   return lg;   
		}  
	
	@SuppressWarnings("unchecked")
	public List<Login> findUserByQuery(String query){		
		return this.manager.createQuery(" From login where nome like :query").setParameter("query", "'%"+query+"%'").getResultList();	
	}
}
