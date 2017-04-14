package br.com.mauricio.news.dao.rh;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import br.com.mauricio.news.model.rh.Vencimento;


/**
*
* @author MAURICIO CRUZ
*/
public class VetorhDao{
	private EntityManagerFactory factory_vetorh;
	private EntityManager manager_vetorh;
	

	private EntityManager abrirConexao(){
		this.factory_vetorh = Persistence.createEntityManagerFactory("vetorh");
		this.manager_vetorh = this.factory_vetorh.createEntityManager();
		this.manager_vetorh.getTransaction().begin();
		return this.manager_vetorh;
	}
	
	private void fechaConexao(){
		this.manager_vetorh.close();
		this.factory_vetorh.close();		
	}	
	
	public List<Vencimento> buscaEventosFolha(int ano, int mes, int periodo){
		this.manager_vetorh = abrirConexao();
		
		String sql ="select year(c.perref) as ano, f.numcad, f.codeve, e.deseve,MONTH(c.perref) as mes, "
				+ "CASE WHEN c.tipcal=11 THEN 2  WHEN c.tipcal=31 THEN 3 WHEN c.tipcal=32 THEN 3 WHEN c.tipcal=14 THEN 14 WHEN c.tipcal=92 THEN 92 END as periodo,"
				+ "f.refeve,CASE WHEN e.tipeve=1 THEN 'P' WHEN e.tipeve=2 THEN 'P' WHEN e.tipeve=3 THEN 'D' END as PD, "
				+ "f.valeve from r046ver f inner join r044cal c on (f.codcal=c.codcal) "
				+ "inner join r008evc e on (f.codeve=e.codeve) "
				+ "where "
				+ "e.codtab=1 and c.tipcal in (:periodo) AND e.tipeve < 4 and "
				+ "year(c.perref)=:ano and MONTH(c.perref)=:mes "
				+ "order by f.numcad,f.codeve"; 
		@SuppressWarnings("unchecked")
		List<Object[]> list = manager_vetorh.createNativeQuery(sql)
				.setParameter("periodo", periodo)
				.setParameter("ano", ano)
				.setParameter("mes", mes)
				.getResultList();
		List<Vencimento> vencs = new ArrayList<Vencimento>();
		for(Object[] o:list){
			Vencimento v = new Vencimento();
			v.setAno((int) o[0]);
			
			if(o[1].toString().length()==3)
				v.setChapa("000"+o[1]);
			else
				v.setChapa(o[1].toString());			
			v.setCodigo(Integer.parseInt(o[2].toString()) );			
			v.setDescricao(o[3].toString());			
			v.setMes((int) o[4]);			
			v.setPeriodo((int) o[5]);
			v.setReferencia(new Double(o[6].toString()));			
			v.setTipo(o[7].toString());
			v.setValor(new Double(o[8].toString()));
			vencs.add(v);
		}
		fechaConexao();
		return vencs;
	}
}
