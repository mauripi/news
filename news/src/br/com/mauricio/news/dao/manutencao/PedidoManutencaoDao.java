package br.com.mauricio.news.dao.manutencao;

import java.util.List;

import javax.persistence.EntityManager;

import br.com.mauricio.news.cnn.Conexao;
import br.com.mauricio.news.model.Login;
import br.com.mauricio.news.model.manutencao.PedidoManutencao;
/**
*
* @author MAURICIO CRUZ and CAROLINE MARQUES
*/
public class PedidoManutencaoDao {
	private EntityManager manager;
	
	public PedidoManutencaoDao(EntityManager manager){
		this.manager = manager;
	}
	public PedidoManutencaoDao(){
		Conexao c = new Conexao();
		this.manager = c.getEntityManager();
	}
	
	@SuppressWarnings("unchecked")
	public List<PedidoManutencao> listarAbertos(Boolean filtro){	
		String sql=" SELECT distinct p FROM pedidomanutencao p left join fetch p.interacoes WHERE p.concluida = :concluida ";	
		return this.manager.createQuery(sql).setParameter("concluida", filtro).getResultList();
	}	
	
	@SuppressWarnings("unchecked")
	public List<PedidoManutencao> listByUsuario(Login usuario){	
		String sql=" SELECT distinct p FROM pedidomanutencao p left join fetch p.interacoes WHERE"
				+ " p.solicitante= :usuario or p.favorecido= :usuario order by p.id desc" ;	
		return this.manager.createQuery(sql).setParameter("usuario", usuario).getResultList();
	}		
}
