package br.com.mauricio.news.ln.ti;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.mauricio.news.cnn.Conexao;
import br.com.mauricio.news.dao.GenericDao;
import br.com.mauricio.news.model.ti.AreaSolicitacao;


public class AreaSolicitacaoLN implements Serializable {

	private static final long serialVersionUID = 1L;
	private GenericDao<AreaSolicitacao> dao;
	private EntityManager manager;

	public AreaSolicitacaoLN(){
		Conexao c = new Conexao();
		manager = c.getEntityManager();
	}
	
	public AreaSolicitacao getById(int id) {
		dao = new GenericDao<AreaSolicitacao>();
		return (AreaSolicitacao) dao.findById(AreaSolicitacao.class,id);
	}

	public List<AreaSolicitacao> getList() {
		dao = new GenericDao<AreaSolicitacao>();
		return dao.listWithoutRemoved("solicitacao_area", "categoria");
	}
	
	public String create(AreaSolicitacao e) {
		dao = new GenericDao<AreaSolicitacao>();
		dao.save(e);
		return "Castrado com sucesso.";
	}

	public String update(AreaSolicitacao e) {
		dao = new GenericDao<AreaSolicitacao>();
		dao.update(e);		
		return "Atualizado com sucesso.";
	}

	public String delete(AreaSolicitacao e) {
		dao = new GenericDao<AreaSolicitacao>();
		dao.delete((AreaSolicitacao)dao.findById(e.getClass(), e.getId()));
		return "Removido com sucesso.";
	}

	
	public GenericDao<AreaSolicitacao> getDao() {
		return dao;
	}

	public void setDao(GenericDao<AreaSolicitacao> dao) {
		this.dao = dao;
	}

	public EntityManager getManager() {
		return manager;
	}

	public void setManager(EntityManager manager) {
		this.manager = manager;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

     
}
