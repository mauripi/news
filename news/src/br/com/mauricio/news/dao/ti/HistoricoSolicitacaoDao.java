package br.com.mauricio.news.dao.ti;

import java.util.List;

import javax.persistence.EntityManager;
import br.com.mauricio.news.cnn.Conexao;
import br.com.mauricio.news.model.ti.HistoricoSolicitacao;
import br.com.mauricio.news.model.ti.Solicitacao;
/**
*
* @author MAURICIO CRUZ
*/
public class HistoricoSolicitacaoDao {
	private EntityManager manager;
	
	public HistoricoSolicitacaoDao(EntityManager manager){
		this.manager = manager;
	}
	public HistoricoSolicitacaoDao(){
		Conexao c = new Conexao();
		this.manager = c.getEntityManager();
	}

	@SuppressWarnings("unchecked")
	public List<HistoricoSolicitacao> findyBySolicitacao(Solicitacao solicitacao){		
		return this.manager.createQuery(" FROM solicitacao_hist WHERE solicitacao= :solicitacao").setParameter("solicitacao", solicitacao).getResultList();
	}

}
