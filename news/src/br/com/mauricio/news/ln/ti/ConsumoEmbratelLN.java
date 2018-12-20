package br.com.mauricio.news.ln.ti;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import br.com.mauricio.news.dao.GenericDao;
import br.com.mauricio.news.dao.ti.ConsumoEmbratelDao;
import br.com.mauricio.news.model.ti.ConsumoEmbratel;

public class ConsumoEmbratelLN implements Serializable {

	private static final long serialVersionUID = 1L;
	private ConsumoEmbratelDao consumoDao;
	private GenericDao<ConsumoEmbratel> dao;
	private EntityManager manager;

	public ConsumoEmbratelLN (){
		
	}
	
	public ConsumoEmbratelLN(EntityManager manager) {
		this.manager = manager;
	}

	public String carregaArquivo(String arquivo,String fatura){
		try{
			consumoDao = new ConsumoEmbratelDao();
			List<ConsumoEmbratel> consumos = consumoDao.carregaArquivo(arquivo, fatura);
			dao = new GenericDao<ConsumoEmbratel>(manager);
			dao.saveList(consumos);		
			return "Arquivo importado com sucesso";
		}catch(Exception e){
			System.out.println(e);
			return "Erro ao importar arquivo. Verifique o log.";
		}
	}
	
	public Map<String,BigDecimal> rateio(String fatura){
		consumoDao = new ConsumoEmbratelDao(manager);		
		return consumoDao.rateio(fatura);		
	}

	public boolean faturaJaExiste(String fatura) {
		consumoDao = new ConsumoEmbratelDao(manager);
		return consumoDao.faturaJaExiste(fatura);
	}
}
