package br.com.mauricio.news.dao.contabil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import br.com.mauricio.news.model.contabil.LancamentoConsolidado;
/**
*
* @author MAURICIO CRUZ
*/
public class LancamentoConsolidadoDao {
	private EntityManagerFactory factory;
	private EntityManager manager;

	private EntityManager abrirConexao(){
		this.factory = Persistence.createEntityManagerFactory("sapiens");
		this.manager = this.factory.createEntityManager();
		this.manager.getTransaction().begin();
		return this.manager;
	}

	private EntityManager abrirConexao2(){
		this.factory = Persistence.createEntityManagerFactory("sapiens_prod");
		this.manager = this.factory.createEntityManager();
		this.manager.getTransaction().begin();
		return this.manager;
	}
	
	private void fechaConexao(){
		this.manager.close();
		this.factory.close();		
	}	
	
	public List<LancamentoConsolidado> obterLancamentos(int ano, int mes) throws IOException{
		String sql ="";
		if(mes<3 && ano<=2016){
			abrirConexao();
			sql ="select "
					+ "l.ctadeb,l.ctacre,l.vlrlct,p.clacta,p.descta,l.datlct,h.deshpd,l.cpllct, "
					+ "	CASE "
					+ "WHEN (p.ctared=l.ctadeb and p.clacta like '4%') THEN 'D'"
					+ "WHEN (p.ctared=l.ctacre and p.clacta like '4%') THEN 'R'"
					+ "WHEN (p.ctared=l.ctadeb and p.clacta like '3%') THEN 'T'"
					+ "WHEN (p.ctared=l.ctacre and p.clacta like '3%') THEN 'C'"
					+ "END as tipo "
					+ "FROM user_sapiens_rnews.e640lct l "
					+ "full join user_sapiens_rnews.e045pla p on (p.ctared=l.ctadeb or p.ctared=l.ctacre) "
					+ "inner join user_sapiens_rnews.e046hpd h on (l.codhpd=h.codhpd) "
					+ "WHERE  year(l.datlct)= :ano AND MONTH(l.datlct)= :mes AND "
					+ "l.tiplct=1 and l.sitlct=2 and (p.nivcta = 4 or p.nivcta = 5) and (p.clacta like '3%' or p.clacta like '4%') "
					+ "order by p.clacta";
		}else{
			abrirConexao2();
			sql ="select "
					+" l.ctadeb,l.ctacre,l.vlrlct,p.clacta,p.descta,l.datlct,h.deshpd,l.cpllct, "
					+" 	CASE "
					+" WHEN (p.ctared=l.ctadeb and p.clacta like '4%') THEN 'D' "
					+" WHEN (p.ctared=l.ctacre and p.clacta like '4%') THEN 'R' "
					+" WHEN (p.ctared=l.ctadeb and p.clacta like '3%') THEN 'T' "
					+" WHEN (p.ctared=l.ctacre and p.clacta like '3%') THEN 'C' "
					+" END as tipo "
					+" FROM e640lct l "
					+" full join e045pla p on (p.ctared=l.ctadeb or p.ctared=l.ctacre) "
					+" inner join e046hpd h on (l.codhpd=h.codhpd) "
					+" WHERE  year(l.datlct)= :ano AND MONTH(l.datlct)= :mes AND "
					+" l.tiplct=1 and l.sitlct=2 and (p.nivcta = 4 or p.nivcta = 5) and (p.clacta like '3%' or p.clacta like '4%') "
					+" order by p.clacta ";
		}
	
		List<LancamentoConsolidado> lancamentos = new ArrayList<LancamentoConsolidado>();
		@SuppressWarnings("unchecked")
		List<Object[]> list = manager.createNativeQuery(sql).setParameter("ano", ano).setParameter("mes", mes)
				.getResultList();
		fechaConexao();
		
		for(Object[] o:list){
			LancamentoConsolidado l = new LancamentoConsolidado();
			l.setCtadeb((int) o[0]);
			l.setCtacre((int) o[1]);
			l.setVlrlct(new Double(o[2].toString()));
			l.setClacta(Integer.parseInt(o[3].toString()));
			l.setDescta(o[4].toString());
			l.setDatlct((Date) o[5]);
			l.setHistorico(montaHistorico(o[6].toString() ,o[7].toString().replace("\"", " ")));
			l.setTipo(o[8].toString());
			lancamentos.add(l);
		}
		System.out.println("========== LancamentoConsolidadoDao.obterLancamentos(ano="+ano+", mes="+mes+") gerado com "+ lancamentos.size()+" registros ===============");
		return lancamentos;
	}

	private String montaHistorico(String a, String b){

		String cpl[] =  b.split(" , ");

		if((a.split("\\*ALF").length)>0)
			a = a.replaceAll("\\*ALF", ";");
		if((a.split("\\*MA").length)>0)
			a = a.replaceAll("\\*MA", ";");
		if((a.split("\\*NUM").length)>0)
			a = a.replaceAll("\\*NUM", ";");		
		if((a.split("\\*DMA").length)>0)
			a = a.replaceAll("\\*DMA", ";");		

		String s[]=a.split(";");
		String texto="";
		if(s.length==cpl.length){
			for(int i=0;i<s.length;i++){			
				texto = texto + s[i] + cpl[i];
			}
		}
		StringBuilder sb = new StringBuilder();
		if(s.length>cpl.length){
			for(int i=0;i<s.length;i++){
				sb.append(s[i]);
				for(int j=i;j<cpl.length;){
					sb.append(cpl[j]);
					break;
				}
			}
			texto=sb.toString();
		}
		if(s.length<cpl.length){
			for(int i=0;i<s.length;i++){			
				texto = texto + s[i] + cpl[i];
			}
		}
		return texto;
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
