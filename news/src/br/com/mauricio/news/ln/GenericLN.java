package br.com.mauricio.news.ln;

import java.io.Serializable;
import java.util.List;

import br.com.mauricio.news.dao.GenericDao;


public class GenericLN<T> implements Serializable {

	private static final long serialVersionUID = -745593021993066877L;

	private String msg;
	
	public String add(T t){
		try {
			GenericDao<T> dao = new GenericDao<T>();
			dao.save(t);		
			msg = "Cadastrado com sucesso.";
		} catch (Exception e) {
			System.out.println(e.getMessage() + " -- Erro na Adição");
			msg = "Erro ao adicionar. Verifique se os campos foram preenchidos, caso persista, envie email para maucruz@recordnews.com.br";
		}					
		return msg;
	}
	public String update(T t){
		try {
			GenericDao<T> dao = new GenericDao<T>();
			dao.update(t);			
			msg = "Atualizado com sucesso.";
		} catch (Exception e) {
			System.out.println(e.getMessage() + " -- Erro na Atualização");
			msg = "Erro ao atualizar. Verifique se os campos foram preenchidos, caso persista,  envie email para maucruz@recordnews.com.br";
		}
		return msg;
	}
	
	public String remove(T t){
		try {
			GenericDao<T> dao = new GenericDao<T>();
			dao.delete(t);
			msg = "Excuido com sucesso.";
		} catch (Exception e) {
			System.out.println(e.getMessage() + " -- Erro na exclusao do remove(T t)");
			msg = "O registro não pode ser excluido. Talvez ele esteja associado a outros registros no sistema.";
		}
		return msg;		
	}

	public String remove(List<T> list){
		try {
			GenericDao<T> dao = new GenericDao<T>();
			dao.deleteList(list);
			msg = "Excuido com sucesso.";
		} catch (Exception e) {
			System.out.println(e.getMessage() + " -- Erro na exclusao do remove(List<T> list)");
			msg = "O registro não pode ser excluido. Talvez ele esteja associado a outros registros no sistema.";
		}
		return msg;		
	}
	
	public T find(T t, int id){
		GenericDao<T> dao = new GenericDao<T>();
		return (T) dao.findById(t.getClass(), id);		
	}
	
	public List<T> listAll(T t,String orderBy){
		GenericDao<T> dao = new GenericDao<T>();
		return dao.list(t.getClass().getName(), orderBy);		
	}

	public List<T> listWithoutRemoved(String classe,String orderBy){
		GenericDao<T> dao = new GenericDao<T>();
		return dao.listWithoutRemoved(classe, orderBy);		
	}
}
