package br.com.mauricio.news.dao.ti;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import br.com.mauricio.news.cnn.Conexao;
import br.com.mauricio.news.model.ti.Solicitacao;
import br.com.mauricio.news.model.ti.SolicitacaoResolvida;
/**
*
* @author MAURICIO CRUZ
*/
public class SolicitacaoResolvidaDao {
	private EntityManager manager;
	
	public SolicitacaoResolvidaDao(EntityManager manager){
		this.manager = manager;
	}
	
	public SolicitacaoResolvidaDao(){
		Conexao c = new Conexao();
		this.manager = c.getEntityManager();
	}


	public SolicitacaoResolvida findBySolicitacao(Solicitacao solicitacao){		
		SolicitacaoResolvida sr=null;
		try{
			sr =(SolicitacaoResolvida) this.manager.createQuery(" FROM solicitacao_resolvida WHERE solicitacao= :solicitacao").setParameter("solicitacao", solicitacao).getSingleResult();
		}catch(PersistenceException e){
			sr=null;
		}
		return sr ;
	}

	@SuppressWarnings("unchecked")
	public List<SolicitacaoResolvida> findResolvidas(){				 
		return this.manager.createQuery(" FROM solicitacao_resolvida").getResultList();
	}
	
	public void excluiSolicitacaoResolvida(SolicitacaoResolvida sr){
		this.manager.remove(sr);
	}
}
