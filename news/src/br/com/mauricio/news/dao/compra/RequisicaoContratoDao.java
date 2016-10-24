package br.com.mauricio.news.dao.compra;

import java.util.List;

import javax.persistence.EntityManager;

import br.com.mauricio.news.cnn.Conexao;
import br.com.mauricio.news.model.Login;
import br.com.mauricio.news.model.compra.RequisicaoContrato;

public class RequisicaoContratoDao {
	private EntityManager manager;
	
	public RequisicaoContratoDao(){
		Conexao c = new Conexao();
		this.manager = c.getEntityManager();
	}
	public RequisicaoContratoDao(EntityManager manager){
		this.manager = manager;
	}
		
	@SuppressWarnings("unchecked")
	public List<RequisicaoContrato> listaPorUsuario(Login usuario) {
		if(usuario.getCpf().equals("30321080807")||usuario.getCpf().equals("38873640826")||usuario.getCpf().equals("79453627291"))
			return this.manager.createQuery(" from requisicaocontrato order by id desc").getResultList();	
		else
			return this.manager.createQuery(" from requisicaocontrato where usuario = :usuario order by id desc").setParameter("usuario", usuario).getResultList();	
	}
	

}
