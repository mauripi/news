package br.com.mauricio.news.dao.rh;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.mauricio.news.cnn.Conexao;
import br.com.mauricio.news.model.Login;
import br.com.mauricio.news.model.rh.Vencimento;


/**
*
* @author MAURICIO CRUZ
*/
public class VencimentoDao{
	private EntityManager manager;
	
	public VencimentoDao(){
		Conexao c = new Conexao();
		this.manager = c.getEntityManager();
	}
	
	@SuppressWarnings("unchecked")
	public List<Vencimento> getVencimentos(Login l, int mes, int ano, int periodo){
	    Query query = this.manager.createQuery("From vencimento v WHERE v.chapa in ( select chapa from login where cpf= :cpf ) and v.mes= :mes and v.ano= :ano and v.periodo= :periodo order by v.codigo");
	    query.setParameter("cpf",l.getCpf());
	    query.setParameter("mes",mes);
	    query.setParameter("ano",ano);
	    query.setParameter("periodo",periodo);
		return query.getResultList();		
	}

	@SuppressWarnings("unchecked")
	public Double getFGTS13(Login l, int mes, int ano, int periodo){
		Double valorFGTSEv200=0.0;
		List<Vencimento> list = new ArrayList<Vencimento>();
		Query query = this.manager.createQuery("From vencimento v WHERE v.chapa in ( select chapa from login where cpf= :cpf ) and v.mes= :mes and v.ano= :ano and v.periodo= :periodo and v.codigo= :codigo ");
		query.setParameter("cpf",l.getCpf());
	    query.setParameter("mes",mes);
	    query.setParameter("ano",ano);
	    query.setParameter("periodo",periodo);
	    query.setParameter("codigo",200);
	    list = query.getResultList();
	    for(Vencimento v:list){
	    	valorFGTSEv200 = valorFGTSEv200 + v.getValor();	
	    }
	    return valorFGTSEv200;		
	}

	@SuppressWarnings("unchecked")
	public List<Vencimento> listaPorMesAnoPeriodo(int mes, int ano, int periodo){
	    Query query = this.manager.createQuery("From vencimento v WHERE v.mes= :mes and v.ano= :ano and v.periodo= :periodo order by v.ano,v.mes desc");
	    query.setParameter("mes",mes).setParameter("ano",ano).setParameter("periodo",periodo);;
		return query.getResultList();		
	}
	
	public void deletaPorMesAnoPeriodo(int mes, int ano, int periodo){
	    Query query = this.manager.createNativeQuery(" Delete From vencimento WHERE mes= :mes and ano= :ano and periodo= :periodo");
	    query.setParameter("mes",mes).setParameter("ano",ano).setParameter("periodo",periodo).executeUpdate();		
	}
}
