package br.com.mauricio.news.ln.financeiro;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.mauricio.news.cnn.Conexao;
import br.com.mauricio.news.dao.financeiro.FluxoClassificacaoDao;
import br.com.mauricio.news.model.financeiro.Movimento;
import br.com.mauricio.news.model.financeiro.Titulo;

public class FluxoClassificacaoLN implements Serializable{

	private static final long serialVersionUID = 1L;
	private EntityManager manager;
	private FluxoClassificacaoDao dao;
	
	public FluxoClassificacaoLN(){
		Conexao c = new Conexao();
		manager = c.getEntityManager();
		dao = new FluxoClassificacaoDao();
	}
	
	public FluxoClassificacaoLN(EntityManager manager){
		this.manager = manager;
	}
	
	public List<Titulo> buscarTitulos(Date d1, Date d2){
		return dao.buscarTitulos(d1, d2);		
	}
	
	public void atualizaTitulo(Titulo titulo){
		dao.atualizaTitulo(titulo);
	}

	public List<Movimento> buscarMovimentos(Date d1, Date d2) {
		return dao.buscarMovimentos(d1, d2);
	}
		
	public void atualizaMovimento(Movimento movimento) {
		dao.atualizaMovimento(movimento);
	}
	
	public EntityManager getManager() {
		return manager;
	}

	public void setManager(EntityManager manager) {
		this.manager = manager;
	}


	
}
