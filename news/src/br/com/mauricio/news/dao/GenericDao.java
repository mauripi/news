package br.com.mauricio.news.dao;

import java.util.List;
import java.io.Serializable;
import java.lang.reflect.Field;

import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import br.com.mauricio.news.cnn.Conexao;


/**
*
* @author MAURICIO CRUZ
*/
public class GenericDao<T> implements Serializable{

	private static final long serialVersionUID = 1L;
	private EntityManager manager;
	
	public GenericDao(){
		Conexao c = new Conexao();
		this.manager = c.getEntityManager();
	}

	public GenericDao(EntityManager manager){
		this.manager = manager;
	}

	public void save(T t){
		manager.persist(t);
		manager.flush();
	}

	public void saveList(List<T> list){
	   for (T t : list) 
		  manager.persist(t);	
	}
	
	public void update(T t){
		manager.merge(t);
		manager.flush();
	}

	public void deleteList(List<T> list){
	   for (T t : list) 
		   manager.remove(t);		   
	}

	public void delete(T t){
		this.manager.remove(t);
		manager.flush();
	}
    
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public T findById(Class classe, Object id){
		try{
			Field[] fields = classe.getDeclaredFields();
			String chave = "";
			for(Field field: fields){
				if(field.getAnnotation(Id.class)!= null){
					chave = field.getName();
					break;
				}			
			}
			Query query = this.manager.createQuery(" From "+classe.getName() + " Where "+ chave + " = :id");
			query.setParameter("id", id);
			query.setMaxResults(1);
			return (T) query.getSingleResult();		
		}catch(PersistenceException e){
			System.out.println("Erro: "+e.getLocalizedMessage()+"  em: GenericDao<T>.findById(Class classe, Object id) "+classe.getName() );
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<T> list(String classe,Object orderBy){
		StringBuilder stringBuilder = new StringBuilder();
	    stringBuilder.append(" From "+ classe +" x ");
	    int removido = 0;
	    stringBuilder.append("WHERE x.removido= :remove");
     	if(orderBy!=null){
    		stringBuilder.append(" ORDER BY x."+orderBy); 	   	   		
    	}
     	Query query = this.manager.createQuery(stringBuilder.toString());
     	query.setParameter("remove",removido);
		return query.getResultList();		
	}

	@SuppressWarnings("unchecked")
	public List<T> listWithoutRemoved(String classe,Object orderBy){
		StringBuilder stringBuilder = new StringBuilder();
	    stringBuilder.append(" From "+ classe +" x ");
     	if(orderBy!=null){
    		stringBuilder.append(" ORDER BY x."+orderBy); 	   	   		
    	}
     	Query query = this.manager.createQuery(stringBuilder.toString());
		return query.getResultList();		
	}
}
