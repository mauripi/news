package br.com.mauricio.news.ln;

import java.io.Serializable;
import java.util.List;

import br.com.mauricio.news.dao.TaskDao;
import br.com.mauricio.news.model.Task;

public class TaskLN implements Serializable{

	private static final long serialVersionUID = 1L;
	private TaskDao dao;
	
	public List<Task> listarProjetos() {
		dao = new TaskDao();
		return dao.listarProjetos();
	}

	public void remove(Task t) {
		dao = new TaskDao();
		dao.remove(t);
	}

}
