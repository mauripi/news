package br.com.mauricio.news.dao.contabil;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.mauricio.news.cnn.Conexao;
import br.com.mauricio.news.model.contabil.HistoricoConsolidado;

public class HistoricoConsolidadoDao implements Serializable{
	private static final long serialVersionUID = 1L;
	private EntityManager manager;
	
	public HistoricoConsolidadoDao(){
		Conexao c = new Conexao();
		this.manager = c.getEntityManager();
	}
	public HistoricoConsolidadoDao(EntityManager manager){
		this.manager = manager;
	}
	
	@SuppressWarnings("unchecked")
	public List<HistoricoConsolidado> listaPorMesAno(int mes, int ano){
		return this.manager.createQuery(" FROM historicoconsolidado where mes= :mes and ano= :ano order by clacta")
				.setParameter("mes", mes).setParameter("ano", ano).getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<HistoricoConsolidado> listaPorContaAno(int contaSelecionada, int ano){
		return this.manager.createQuery(" FROM historicoconsolidado where clacta= :clacta and ano= :ano order by mes")
				.setParameter("clacta", contaSelecionada).setParameter("ano", ano).getResultList();
	}
	
	public HistoricoConsolidado findByHistoricoContaMesAno(int contaSelecionada, Integer mes, Integer ano) {
		HistoricoConsolidado h=null;
		Query query = this.manager.createQuery(" FROM historicoconsolidado where mes= :mes and ano= :ano and clacta= :clacta order by clacta")
				.setParameter("mes", mes).setParameter("ano", ano).setParameter("clacta", contaSelecionada);
		int t = query.getResultList().size();
		if(t>0)
			h = (HistoricoConsolidado) query.getResultList().get(0);
		else
			h= null;
		return h;
	}
}
