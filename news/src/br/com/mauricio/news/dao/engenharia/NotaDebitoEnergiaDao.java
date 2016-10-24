package br.com.mauricio.news.dao.engenharia;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.mauricio.news.cnn.Conexao;
import br.com.mauricio.news.model.engenharia.NotaDebitoEnergia;
import br.com.mauricio.news.model.engenharia.ViewNotaDebito;

public class NotaDebitoEnergiaDao implements Serializable {

	private static final long serialVersionUID = 1L;
	private EntityManager manager;
	
	public NotaDebitoEnergiaDao(){
		Conexao c = new Conexao();
		this.manager = c.getEntityManager();
	}

	public NotaDebitoEnergiaDao(EntityManager manager){
		this.manager = manager;
	}
	
	public boolean existeNaBase(NotaDebitoEnergia debito){		
		boolean t=false;
		String sql = " From notadebitopraca where mes= :mes and ano= :ano and praca= :praca";
		@SuppressWarnings("unchecked")
		List<NotaDebitoEnergia> list = this.manager.createQuery(sql).setParameter("mes", debito.getMes())
				.setParameter("ano", debito.getAno()).setParameter("praca", debito.getPraca()).getResultList();
		if(list.size()>0)
			t=true;
		return t;
	}
	
	public boolean existeNaBaseMaisDeUm(NotaDebitoEnergia debito) {
		boolean t=false;
		String sql = " From notadebitopraca where mes= :mes and ano= :ano and praca= :praca";
		@SuppressWarnings("unchecked")
		List<NotaDebitoEnergia> list = this.manager.createQuery(sql).setParameter("mes", debito.getMes())
				.setParameter("ano", debito.getAno()).setParameter("praca", debito.getPraca()).getResultList();
		if(list.size()>1)
			t=true;
		return t;
	}

	public List<ViewNotaDebito> getNotaDebitoConsolidados(int ano){
		String sql ="SELECT * FROM (SELECT p.nome, "
				+ "CASE (mes) WHEN 1 THEN 'JANEIRO' WHEN 2 THEN 'FEVEREIRO'  WHEN 3 THEN 'MARÇO' WHEN 4 THEN 'ABRIL'  WHEN 5 THEN 'MAIO'  WHEN 6 THEN 'JUNHO' "
				+ "WHEN 7 THEN 'JULHO' WHEN 8 THEN 'AGOSTO' WHEN 9 THEN 'SETEMBRO' WHEN 10 THEN 'OUTUBRO'  WHEN 11 THEN 'NOVEMBRO' WHEN 12 THEN 'DEZEMBRO' "
				+ "END [mes] ,valor FROM notadebitopraca d, praca p WHERE (d.praca_id=p.id) and [ano]= :ano) "
				+ "TableDate PIVOT ( SUM(valor) FOR [mes] IN ( [JANEIRO],[FEVEREIRO],[MARÇO],[ABRIL],[MAIO],[JUNHO],[JULHO],[AGOSTO],[SETEMBRO],[OUTUBRO],[NOVEMBRO],[DEZEMBRO] )) PivotTable ";

		List<ViewNotaDebito> lancamentos = new ArrayList<ViewNotaDebito>();
		
		@SuppressWarnings("unchecked")
		List<Object[]> list = manager.createNativeQuery(sql).setParameter("ano", ano).getResultList();
		
		for(Object[] o:list){
			ViewNotaDebito v = new ViewNotaDebito();
			v.setPraca(o[0].toString());
			
			if(o[1]==null)
				v.setJaneiro(new Double("0.0"));
			else
				v.setJaneiro(new Double(o[1].toString()));				
			
			if(o[2]==null)
				v.setFevereiro(new Double("0.0"));
			else
				v.setFevereiro(new Double(o[2].toString()));
							
			if(o[3]==null)
				v.setMarco(new Double("0.0"));
			else
				v.setMarco(new Double(o[3].toString()));			
			
			if(o[4]==null)
				v.setAbril(new Double("0.0"));
			else
				v.setAbril(new Double(o[4].toString()));			
			
			if(o[5]==null)
				v.setMaio(new Double("0.0"));
			else
				v.setMaio(new Double(o[5].toString()));			
			
			if(o[6]==null)
				v.setJunho(new Double("0.0"));
			else
				v.setJunho(new Double(o[6].toString()));			

			if(o[7]==null)
				v.setJulho(new Double("0.0"));
			else
				v.setJulho(new Double(o[7].toString()));			
			
			if(o[8]==null)
				v.setAgosto(new Double("0.0"));
			else
				v.setAgosto(new Double(o[8].toString()));										
			
			if(o[9]==null)
				v.setSetembro(new Double("0.0"));
			else
				v.setSetembro(new Double(o[9].toString()));			
			
			if(o[10]==null)
				v.setOutubro(new Double("0.0"));
			else
				v.setOutubro(new Double(o[10].toString()));			
			
			if(o[11]==null)
				v.setNovembro(new Double("0.0"));
			else
				v.setNovembro(new Double(o[11].toString()));			
			
			if(o[12]==null)
				v.setDezembro(new Double("0.0"));
			else
				v.setDezembro(new Double(o[12].toString()));			
			
			lancamentos.add(v);
		}		
		return lancamentos;		
	}
	
}
