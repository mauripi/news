package br.com.mauricio.news.dao.marketing;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.mauricio.news.cnn.Conexao;
import br.com.mauricio.news.model.marketing.PosVenda;

public class PosVendaDao {
	private EntityManager manager;
	
	public PosVendaDao(EntityManager manager){
		this.manager = manager;
	}
	
	public PosVendaDao(){
		Conexao c = new Conexao();
		this.manager = c.getEntityManager();
	}
	
	@SuppressWarnings("unchecked")
	public List<PosVenda> listaPosVenda(Date datai, Date dataf){
		String sql="select "
				+ "emm.data as data,emm.horario as hora, MONTH(emm.data) as mes, YEAR(emm.data) as ano, "
				+ "emm.duracao,emm.programa,emm.cliente,emm.titulo,emm.audiencia, "
				+ "lib.dratper as dratper,lib.dratabs as dratabd, lib.dcovperc as dcovper,lib.dcovabs as dcovabs, "
				+ "lib.iratper as iratper,lib.iratabs as iratabd, lib.icovperc as icovper,lib.icovabs as icovabs "
				+ "from programarelacionamento pr "
				+ "inner join programaibope pib on (pr.programaibope_id=pib.id) "
				+ "inner join programamidiamais pmm on (pr.programamidiamais_id=pmm.id) "
				+ "inner join liveibope lib on (pib.id=lib.programaibope_id) "
				+ "inner join exibicaomidiamais emm on (emm.programamidiamais_id=pmm.id) "
				+ "where emm.data between :datai and :dataf order by cliente";
		
		List<PosVenda> pvs = new ArrayList<PosVenda>();
		List<Object[]> list = manager.createNativeQuery(sql).setParameter("datai", datai).setParameter("dataf", dataf).getResultList();
		
		for(Object[] o:list){
			PosVenda pv = new PosVenda();
			pv.setData((Date) o[0]);
			pv.setHora(o[1].toString());
			pv.setMes((int) o[2]);
			pv.setAno((int) o[3]);
			pv.setDuracao(o[4].toString());
			pv.setProgramamidiamais(o[5].toString());
			pv.setCliente(o[6].toString());
			pv.setTitulo(o[7].toString());
			pv.setAudiencia(new Double(o[8].toString()));
			pv.setDratper(new Double(o[9].toString()));
			pv.setDratabs(new Double(o[10].toString()));
			pv.setDcovper(new Double(o[11].toString()));
			pv.setDcovabs(new Double(o[12].toString()));
			pv.setIratper(new Double(o[13].toString()));
			pv.setIratabs(new Double(o[14].toString()));
			pv.setIcovper(new Double(o[15].toString()));
			pv.setIcovabs(new Double(o[16].toString()));
			pvs.add(pv);
		}
		return pvs;
	}
}
