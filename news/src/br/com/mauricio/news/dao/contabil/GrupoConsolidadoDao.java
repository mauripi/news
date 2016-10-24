package br.com.mauricio.news.dao.contabil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import br.com.mauricio.news.model.contabil.GrupoConsolidado;
/**
*
* @author MAURICIO CRUZ
*/
public class GrupoConsolidadoDao {
	private EntityManagerFactory factory;
	private EntityManager manager;

	private EntityManager abrirConexao(){
		this.factory = Persistence.createEntityManagerFactory("sapiens_prod");
		this.manager = this.factory.createEntityManager();
		this.manager.getTransaction().begin();
		return this.manager;
	}
	
	private void fechaConexao(){
		this.manager.close();
		this.factory.close();		
	}	
	
	public List<GrupoConsolidado> obterGrupos() throws IOException{
		abrirConexao();
		List<GrupoConsolidado> grupos = new ArrayList<GrupoConsolidado>();
		
		String sql =" select "
				+ "ctared,clacta,descta,nivcta,gructa,natcta FROM e045pla p  "
				+ "WHERE (p.nivcta = 4 or p.nivcta = 5) and (p.clacta like '3%' or p.clacta like '4%') order by clacta  ";

		@SuppressWarnings("unchecked")
		List<Object[]> list = manager.createNativeQuery(sql).getResultList();
		
		for(Object[] o:list){
			GrupoConsolidado c = new GrupoConsolidado();
			c.setCtared((int) o[0]);
			c.setClacta(Integer.parseInt(o[1].toString()));
			c.setDescta(o[2].toString());
			c.setNivcta(Integer.parseInt(o[3].toString()));
			c.setGructa(Integer.parseInt(o[4].toString()));
			c.setNatcta(o[5].toString());
			grupos.add(c);
		}
		fechaConexao();
		System.out.println("========== GrupoConsolidadoDao.obterGrupos() gerado com "+ grupos.size()+" registros ===============");
		return grupos;
	}



	public EntityManagerFactory getFactory() {
		return factory;
	}

	public void setFactory(EntityManagerFactory factory) {
		this.factory = factory;
	}

	public EntityManager getManager() {
		return manager;
	}

	public void setManager(EntityManager manager) {
		this.manager = manager;
	}


}
