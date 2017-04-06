package br.com.mauricio.news.dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import br.com.mauricio.news.model.MCLIFOR;

public class MCLIFORERPDao {
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


	public List<MCLIFOR> getCliFor(String dado, String filtro) throws IOException{
		String sql="";
		if(filtro=="1"){
			sql = "SELECT * FROM (select codcli,nomcli,apecli,tipcli,'C' as tipo,cgccpf,endcli,nencli,cplend,baicli,cidcli,cepcli,sigufs,foncli,codfor from e085cli "
					+ "		UNION ALL select codfor,nomfor,apefor,tipfor,'F' as tipo,cgccpf,endfor,nenfor,cplend,baifor,cidfor,cepfor,sigufs,fonfor,codcli from e095for) AS CONS   "
					+ " where cgccpf= :dado ";
		}
		if(filtro=="2"){
			sql = "SELECT  * FROM (select codcli,nomcli,apecli,tipcli,'C' as tipo,cgccpf,endcli,nencli,cplend,baicli,cidcli,cepcli,sigufs,foncli,codfor from e085cli "
					+ "		UNION ALL select codfor,nomfor,apefor,tipfor,'F' as tipo,cgccpf,endfor,nenfor,cplend,baifor,cidfor,cepfor,sigufs,fonfor,codcli from e095for) AS CONS   "
					+ " where apecli like :dado ";
		}		
		abrirConexao();	

		@SuppressWarnings("unchecked")
		List<Object[]> list = manager.createNativeQuery(sql).setParameter("dado", "%"+dado+"%").getResultList();

		fechaConexao();
		return getResult(list);
	}
	

	private List<MCLIFOR> getResult(List<Object[]> list){
		List<MCLIFOR> results = new ArrayList<MCLIFOR>();
		if(list.size()>0){
			for(Object[] o:list){				
				MCLIFOR reg = new MCLIFOR();
				if(o[4].toString().equals("C")){
					reg.setCodcli(Integer.parseInt(o[0].toString()));
					reg.setCodfor(Integer.parseInt(o[14].toString()));
					reg.setClifor(1);
				}
				if(o[4].toString().equals("F")){
					reg.setCodfor(Integer.parseInt(o[0].toString()));
					reg.setCodcli(Integer.parseInt(o[14].toString()));
					reg.setClifor(2);
				}
				
				reg.setNomraz(o[1].toString());
				reg.setNomfan(o[2].toString());
				
				if(o[3].toString()=="F")
					reg.setTippes(2);
				else
					reg.setTippes(1);
				
				reg.setCgccpf(o[5].toString());					
				reg.setEndrua(o[6].toString());
				reg.setEndnum(o[7].toString());
				reg.setEndcpl(o[8].toString());
				reg.setEndbai(o[9].toString());
				reg.setEndcid(o[10].toString());
				String cep=o[11].toString();
				if(cep.length()>0)
					reg.setEndcep(o[11].toString());
				reg.setEndest(o[12].toString());
				reg.setFoncon(o[13].toString());
				results.add(reg);
			}
		}
		return results;		
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
