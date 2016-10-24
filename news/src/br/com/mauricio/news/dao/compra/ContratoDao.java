package br.com.mauricio.news.dao.compra;

import java.util.List;

import javax.persistence.EntityManager;

import br.com.mauricio.news.cnn.Conexao;
import br.com.mauricio.news.model.compra.Contrato;

public class ContratoDao {
	private EntityManager manager;
	
	public ContratoDao(){
		Conexao c = new Conexao();
		this.manager = c.getEntityManager();
	}
	public ContratoDao(EntityManager manager){
		this.manager = manager;
	}
		
	@SuppressWarnings("unchecked")
	public List<Contrato> listaContratosAtivos() {
		Boolean ativo=true;
		return this.manager.createQuery(" from contrato where ativo= :ativo order by id desc").setParameter("ativo", ativo).getResultList();	
	}
	
	public void delete(Contrato c){	
		this.manager.createNativeQuery(" delete from doccontrato where contrato_id= :id").setParameter("id", c.getId()).executeUpdate();		
		this.manager.createNativeQuery(" delete from contrato where id= :id").setParameter("id", c.getId()).executeUpdate();		
	}
}
