package br.com.mauricio.news.ln.financeiro;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.mauricio.news.cnn.Conexao;
import br.com.mauricio.news.dao.financeiro.T�tuloDao;
import br.com.mauricio.news.model.financeiro.Titulo;

public class TituloLN implements Serializable{

	private static final long serialVersionUID = 1L;
	private T�tuloDao dao;
	private EntityManager manager;
	
	public TituloLN(){
		Conexao c = new Conexao();
		manager = c.getEntityManager();
		dao = new T�tuloDao();
	}
	
	public TituloLN(EntityManager manager){
		this.manager = manager;
	}
	
	public List<Titulo> buscarTitulos(Date d1, Date d2){
		return dao.buscarTitulos(d1, d2);		
	}

	public void atualizaTitulo(Titulo titulo){
		dao.atualizaTitulo(titulo);
	}
	
	
	
	public EntityManager getManager() {
		return manager;
	}

	public void setManager(EntityManager manager) {
		this.manager = manager;
	}
	
}
