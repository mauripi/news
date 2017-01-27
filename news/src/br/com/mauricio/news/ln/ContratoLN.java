package br.com.mauricio.news.ln;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.mauricio.news.cnn.Conexao;
import br.com.mauricio.news.dao.ContratoDao;
import br.com.mauricio.news.dao.GenericDao;
import br.com.mauricio.news.model.Contrato;
import br.com.mauricio.news.model.Login;


public class ContratoLN implements Serializable {

	private static final long serialVersionUID = 1L;
	private GenericDao<Contrato> dao;
	private EntityManager manager;
	
	public ContratoLN() {
		Conexao c = new Conexao();
		this.manager = c.getEntityManager();
	}

	public String create(){
		return null;
	}
	public String update(){
		return null;
	}
	public String delete(){
		return null;
	}

	public List<Contrato> list(Login userLogado) {
		ContratoDao cdao = new ContratoDao(manager);
		String codigo = userLogado.getCusto().getCodigo();
		List<Contrato> list = new ArrayList<Contrato>();
		if(codigo.equals("30006")||codigo.equals("30018")||userLogado.getChapa().equals("000755")){
			list = cdao.listaContratosAtivos();
		}else{
			List<String> custos = Arrays.asList("30034", "30071","30107");
			list = cdao.listaContratosByCCusto(custos);
		}
		return list;
	}
}
