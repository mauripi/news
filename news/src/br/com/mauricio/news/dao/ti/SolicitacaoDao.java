package br.com.mauricio.news.dao.ti;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;

import br.com.mauricio.news.cnn.Conexao;
import br.com.mauricio.news.model.Login;
import br.com.mauricio.news.model.ti.Solicitacao;
import br.com.mauricio.news.model.ti.StatusSolicitacao;
/**
*
* @author MAURICIO CRUZ
*/
public class SolicitacaoDao {
	private EntityManager manager;
	
	public SolicitacaoDao(EntityManager manager){
		this.manager = manager;
	}
	public SolicitacaoDao(){
		Conexao c = new Conexao();
		this.manager = c.getEntityManager();
	}

	@SuppressWarnings("unchecked")
	public List<Solicitacao> listByUsuario(Login usuario){		
		return this.manager.createQuery(" FROM solicitacao WHERE solicitante= :usuario or favorecido= :usuario order by id desc").setParameter("usuario", usuario).getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Solicitacao> listAbertas(){	
		List<StatusSolicitacao> status = new ArrayList<StatusSolicitacao>() ;
		status.add(StatusSolicitacao.CANCELADA);
		status.add(StatusSolicitacao.FINALIZADA);
		status.add(StatusSolicitacao.RESOLVIDA);
		
		String sql=" SELECT s FROM solicitacao s WHERE "
				+ "s not in (SELECT s FROM solicitacao s JOIN s.historicos h WHERE h.status in (:status)) ";
		
		return this.manager.createQuery(sql)
				.setParameter("status", status).getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Solicitacao> listFechadas(){	
		List<StatusSolicitacao> status = new ArrayList<StatusSolicitacao>() ;
		status.add(StatusSolicitacao.CANCELADA);
		status.add(StatusSolicitacao.FINALIZADA);
		status.add(StatusSolicitacao.RESOLVIDA);
		
		String sql="SELECT s FROM solicitacao s "
				+ "JOIN s.historicos h WHERE h.status in (:status) ";
		
		return this.manager.createQuery(sql)
				.setParameter("status", status).getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Solicitacao> listAbertasEResolvidas() {
		List<StatusSolicitacao> status = new ArrayList<StatusSolicitacao>() ;
		status.add(StatusSolicitacao.CANCELADA);
		status.add(StatusSolicitacao.FINALIZADA);
		
		String sql=" SELECT s FROM solicitacao s WHERE "
				+ "s not in (SELECT s FROM solicitacao s JOIN s.historicos h WHERE h.status in (:status)) ";
		
		return this.manager.createQuery(sql)
				.setParameter("status", status).getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Solicitacao> listResolvidas() {
		List<StatusSolicitacao> status = new ArrayList<StatusSolicitacao>();
		status.add(StatusSolicitacao.RESOLVIDA);
		String sql="SELECT s FROM solicitacao s JOIN s.historicos h WHERE h.status in (:status) ";		
		return this.manager.createQuery(sql).setParameter("status", status).getResultList();
	}
}
