package br.com.mauricio.news.dao.rh;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.joda.time.DateTime;

import br.com.mauricio.news.cnn.Conexao;
import br.com.mauricio.news.model.rh.Base;
import br.com.mauricio.news.model.Login;


/**
*
* @author MAURICIO CRUZ
*/
public class BaseDao{
	private EntityManager manager;
	
	public BaseDao(){
		Conexao c = new Conexao();
		this.manager = c.getEntityManager();
	}
	
	@SuppressWarnings("unchecked")
	public List<Base> listaPorUsuario(Login l,int ano){
	    Query query = this.manager.createQuery("From base b WHERE b.chapa in ( select chapa from login where cpf= :cpf ) and b.ano= :ano order by b.ano,b.mes desc");
	    query.setParameter("cpf",l.getCpf());
	    query.setParameter("ano",ano);
		return query.getResultList();		
	}
	
	@SuppressWarnings({ "static-access", "rawtypes" })
	public Base getUltimaBasePorUsuario(Login l){
		DateTime d = new DateTime().now();
	    Query query = this.manager.createQuery("From base b WHERE b.cpf= :cpf and b.ano= :ano order by b.ano,b.mes desc");
	    query.setParameter("cpf",l.getCpf());
	    query.setParameter("ano",d.getYear());
	    List results = query.getResultList();
        if (results.isEmpty())
        	return new Base();
        else
        	return (Base) results.get(0);		
	}
	
	public String getUltimaFuncao(Login l){
		Object funcao;
		String sql="select funcao from base where chapa= :chapa and "
				+ " mes=(select max(m.mes) from base m where m.ano=(select max(c.ano) from base c)) "
				+ "and ano=(select max(a.ano) from base a) ";
		try{
			funcao = this.manager.createNativeQuery(sql).setParameter("chapa",l.getChapa()).getResultList().get(0);		
		}catch(NoResultException e){
			 sql="select funcao from base where chapa= :chapa and "
					+ " mes=(select max(m.mes-1) from base m where m.ano=(select max(c.ano) from base c)) "
					+ "and ano=(select max(a.ano) from base a) ";	
			funcao = this.manager.createNativeQuery(sql).setParameter("chapa",l.getChapa()).getResultList().get(0);
		}
		return (String) funcao;
	}
	
	@SuppressWarnings("unchecked")
	public List<Base> listaPorMesAnoPeriodo(int mes, int ano, int periodo){
	    Query query = this.manager.createQuery("From base b WHERE b.mes= :mes and b.ano= :ano and b.periodo= :periodo order by b.ano,b.mes desc");
	    query.setParameter("mes",mes).setParameter("ano",ano).setParameter("periodo",periodo);;
		return query.getResultList();		
	}

	public void deletaPorMesAnoPeriodo(int mes, int ano, int periodo){
	    Query query = this.manager.createNativeQuery(" Delete From base WHERE mes= :mes and ano= :ano and periodo= :periodo");
	    query.setParameter("mes",mes).setParameter("ano",ano).setParameter("periodo",periodo).executeUpdate();		
	}
}
