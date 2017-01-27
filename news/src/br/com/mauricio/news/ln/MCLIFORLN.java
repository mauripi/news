package br.com.mauricio.news.ln;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.mauricio.news.cnn.Conexao;
import br.com.mauricio.news.dao.MCLIFORDao;
import br.com.mauricio.news.dao.MCLIFORERPDao;
import br.com.mauricio.news.model.MCLIFOR;


public class MCLIFORLN implements Serializable {

	private static final long serialVersionUID = 1L;
	private EntityManager manager;
	
	public MCLIFORLN() {
		Conexao c = new Conexao();
		this.manager = c.getEntityManager();
	}
	
	public List<MCLIFOR> find(String dado,String filtro){
		MCLIFORDao cdao = new MCLIFORDao(manager);
		List<MCLIFOR> results;
		try {
			results = cdao.getCliFor(dado, filtro);
			if (results.size()<1){
				MCLIFORERPDao edao = new MCLIFORERPDao();
				results = edao.getCliFor(dado, filtro);
			}
		} catch (IOException e) {
			System.out.println("Ocorreu erro ao buscar clifor "+e.getLocalizedMessage());
			results = new ArrayList<MCLIFOR>();
		}		
		return results;		
	}
	
	
}
