package br.com.mauricio.news.dao;

import java.util.List;

import javax.persistence.EntityManager;

import br.com.mauricio.news.cnn.Conexao;
import br.com.mauricio.news.model.Task;

public class TaskDao {
	private EntityManager manager;
	
	public TaskDao(EntityManager manager){
		this.manager = manager;
	}
	public TaskDao(){
		Conexao c = new Conexao();
		this.manager = c.getEntityManager();
	}
	
	@SuppressWarnings("unchecked")
	public List<Task> listarProjetos() {
		String jpql = "FROM task where pretask is null ";
		return manager.createQuery(jpql).getResultList();
	}
	
	public void remove(Task t) {
		Task tarefaParaRemover= manager.find(t.getClass(), t.getId());
		if(tarefaParaRemover.getPretask()!=null)
			tarefaParaRemover.getPretask().getTasks().remove(tarefaParaRemover);
		manager.remove(tarefaParaRemover);
	}

}
