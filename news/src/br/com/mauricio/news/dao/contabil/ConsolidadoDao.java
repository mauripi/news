package br.com.mauricio.news.dao.contabil;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import br.com.mauricio.news.cnn.Conexao;
import br.com.mauricio.news.model.contabil.Consolidado;
import br.com.mauricio.news.model.contabil.ViewConsolidado;


/**
*
* @author MAURICIO CRUZ
*/
public class ConsolidadoDao{
	private EntityManager manager;
	
	public ConsolidadoDao(){
		Conexao c = new Conexao();
		this.manager = c.getEntityManager();
	}
	public ConsolidadoDao(EntityManager manager){
		this.manager = manager;
	}
		
	@SuppressWarnings("unchecked")
	public List<ViewConsolidado> getConsolidados(int ano){
		String sql ="SELECT * FROM ( SELECT clacta, CASE (mes) "
				+ "WHEN 1 THEN 'JANEIRO' WHEN 2 THEN 'FEVEREIRO'  WHEN 3 THEN 'MARÇO' WHEN 4 THEN 'ABRIL'  WHEN 5 THEN 'MAIO'  WHEN 6 THEN 'JUNHO' "
				+ "WHEN 7 THEN 'JULHO' WHEN 8 THEN 'AGOSTO' WHEN 9 THEN 'SETEMBRO' WHEN 10 THEN 'OUTUBRO'  WHEN 11 THEN 'NOVEMBRO' WHEN 12 THEN 'DEZEMBRO' "
				+ "END [mes] ,valor FROM consolidado where [ano]= :ano) TableDate "
				+ "PIVOT ( SUM(valor)  FOR [mes] IN ( [JANEIRO],[FEVEREIRO],[MARÇO],[ABRIL],[MAIO],[JUNHO],[JULHO],[AGOSTO],[SETEMBRO],[OUTUBRO],[NOVEMBRO],[DEZEMBRO] )) PivotTable ";

		List<ViewConsolidado> lancamentos = new ArrayList<ViewConsolidado>();
		
		List<Object[]> list = manager.createNativeQuery(sql).setParameter("ano", ano).getResultList();
		
		for(Object[] o:list){
			ViewConsolidado v = new ViewConsolidado();
			v.setClacta((int) o[0]);
			
			if(o[1]==null){
				v.setJaneiro(new Double("0.0"));
			}else{
				v.setJaneiro(new Double(o[1].toString()));
			}	
			
			if(o[2]==null){
				v.setFevereiro(new Double("0.0"));
			}else{
				v.setFevereiro(new Double(o[2].toString()));
			}	
			
			if(o[3]==null){
				v.setMarco(new Double("0.0"));
			}else{
				v.setMarco(new Double(o[3].toString()));
			}
			
			if(o[4]==null){
				v.setAbril(new Double("0.0"));
			}else{
				v.setAbril(new Double(o[4].toString()));
			}
			
			if(o[5]==null){
				v.setMaio(new Double("0.0"));
			}else{
				v.setMaio(new Double(o[5].toString()));
			}
			
			if(o[6]==null){
				v.setJunho(new Double("0.0"));
			}else{
				v.setJunho(new Double(o[6].toString()));
			}

			if(o[7]==null){
				v.setJulho(new Double("0.0"));
			}else{
				v.setJulho(new Double(o[7].toString()));
			}
			
			if(o[8]==null){
				v.setAgosto(new Double("0.0"));
			}else{
				v.setAgosto(new Double(o[8].toString()));
			}							
			
			if(o[9]==null){
				v.setSetembro(new Double("0.0"));
			}else{
				v.setSetembro(new Double(o[9].toString()));
			}
			
			if(o[10]==null){
				v.setOutubro(new Double("0.0"));
			}else{
				v.setOutubro(new Double(o[10].toString()));
			}
			
			if(o[11]==null){
				v.setNovembro(new Double("0.0"));
			}else{
				v.setNovembro(new Double(o[11].toString()));
			}
			
			if(o[12]==null){
				v.setDezembro(new Double("0.0"));
			}else{
				v.setDezembro(new Double(o[12].toString()));
			}
			
			lancamentos.add(v);
		}
		return organizaLancamentos(lancamentos);		
	}

	private List<ViewConsolidado> organizaLancamentos(List<ViewConsolidado> list){
		List<ViewConsolidado> sintetico = new ArrayList<ViewConsolidado>();
		for(ViewConsolidado v:list)
			if(String.valueOf(v.getClacta()).length()==5)
				sintetico.add(v);		
		for(int i=0;i<sintetico.size();i++){
			sintetico.get(i).setAnaliticos(new ArrayList<ViewConsolidado>());	
			for(ViewConsolidado v:list)
				if(String.valueOf(v.getClacta()).length()>5)
					if(v.getClacta().toString().contains(sintetico.get(i).getClacta().toString()))
						sintetico.get(i).getAnaliticos().add(v);
		}					
		return sintetico;	
	}

	@SuppressWarnings("unchecked")
	public List<Consolidado> listaPorMesAno(int mes, int ano){		
		return this.manager.createQuery(" from consolidado where mes = :mes and ano = :ano").setParameter("mes", mes).setParameter("ano", ano).getResultList();		
	}

	public void deletaPorMesAno(int mes, int ano){		
		this.manager.createNativeQuery(" delete from consolidado where mes = :mes and ano = :ano").setParameter("mes", mes).setParameter("ano", ano).executeUpdate();		
	}

	public void deletaPlano(){		
		this.manager.createNativeQuery(" delete from planoconsolidado").executeUpdate();		
	}
	
	@SuppressWarnings("unchecked")
	public List<Consolidado> findByConta(Integer numeroConta, int ano) {
		String observacao="''";
		return this.manager.createQuery(" from consolidado where clacta = :numeroConta and observacao <> :observacao and ano = :ano order by ano,mes")
				.setParameter("numeroConta", numeroConta).setParameter("ano", ano).setParameter("observacao", observacao).getResultList();		
	}
	
	@SuppressWarnings("unchecked")
	public Consolidado findByContaMesAno(int conta, Integer mes, Integer ano) {
		List<Consolidado> list=new ArrayList<Consolidado>();
		list =  this.manager.createQuery(" from consolidado where clacta = :conta and mes = :mes and ano = :ano order by ano,mes")
					.setParameter("conta", conta).setParameter("mes", mes).setParameter("ano", ano).getResultList();		
		if(list == null || list.isEmpty())
			return new Consolidado();
		return list.get(0);
	}
}
