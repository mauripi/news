package br.com.mauricio.news.ln.manutencao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.mauricio.news.cnn.Conexao;
import br.com.mauricio.news.dao.manutencao.PedidoManutencaoDao;
import br.com.mauricio.news.model.Login;
import br.com.mauricio.news.model.manutencao.PedidoManutencao;

public class PedidoManutencaoLN implements Serializable {

	private static final long serialVersionUID = 1L;
	private PedidoManutencaoDao dao;
	private EntityManager manager;

	public PedidoManutencaoLN(){
		Conexao c = new Conexao();
		manager = c.getEntityManager();
	}
	
	public PedidoManutencaoLN(EntityManager manager){
		this.manager = manager;
	}
	
	public List<PedidoManutencao> listarAbertos(Boolean filtro){		
		dao = new PedidoManutencaoDao(manager);
		return dao.listarAbertos(filtro);
	}
	
	public List<PedidoManutencao> listByUsuario(Login usuario){		
		dao = new PedidoManutencaoDao(manager);
		return dao.listByUsuario(usuario);
	}	
}
