package br.com.mauricio.news.ln.marketing;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.mauricio.news.cnn.Conexao;
import br.com.mauricio.news.dao.marketing.PosVendaDao;
import br.com.mauricio.news.model.marketing.PosVenda;


public class PosVendaLN implements Serializable {

	private static final long serialVersionUID = 1L;
	private EntityManager manager;

	public PosVendaLN(){
		Conexao c = new Conexao();
		manager = c.getEntityManager();
	}
	
	public PosVendaLN(EntityManager manager){
		this.manager = manager;
	}


	public List<PosVenda> listaPosVenda(Date datei, Date datef) {
		PosVendaDao dao = new PosVendaDao(manager);
		return dao.listaPosVenda(datei,datef);
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
