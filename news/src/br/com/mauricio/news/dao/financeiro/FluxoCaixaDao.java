package br.com.mauricio.news.dao.financeiro;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import br.com.mauricio.news.model.financeiro.ClassificacaoFluxoCaixa;
import br.com.mauricio.news.model.financeiro.FluxoCaixa;

public class FluxoCaixaDao {
	private EntityManagerFactory factory;
	private EntityManager manager;

	
	private EntityManager abrirConexao(){
		this.factory = Persistence.createEntityManagerFactory("sapiens_homo");
		this.manager = this.factory.createEntityManager();
		this.manager.getTransaction().begin();
		return this.manager;
	}
	
	private void fechaConexao(){
		this.manager.close();
		this.factory.close();		
	}	

	public List<FluxoCaixa>  listar(){
		abrirConexao();
		String sql ="select id,usu_ctafin,usu_data,usu_valor,usu_codtns, "
				+ "usu_codccu,usu_observacao,usu_ctared,usu_prorea,usu_numtit, "
				+ "usu_clifor,usu_desctared,usu_desccu,usu_desctafin,usu_orilct from usu_tfluxo where usu_orilct='M' ";
		
		@SuppressWarnings("unchecked")
		List<Object[]> list = manager.createNativeQuery(sql).getResultList();
		List<FluxoCaixa> movimentos = new ArrayList<FluxoCaixa>();
		for(Object[] o : list) {
			FluxoCaixa f = new FluxoCaixa();
			f.setId((int) o[0]);
			f.setCtafin((int) o[1]);
			f.setData((Date) o[2]);
			f.setValor(new BigDecimal(o[3].toString()));
			f.setCodtns(o[4].toString());
			f.setCodccu(o[5].toString());
			f.setObservacao(o[6].toString());
			f.setCtared((int) o[7]);
			f.setProrea(o[8].toString());
			f.setNumtit(o[9].toString());
			f.setClifor(o[10].toString());
			f.setDesctared(o[11].toString());
			f.setDesccu(o[12].toString());
			f.setDesctafin(o[13].toString());
			f.setOrilct(o[14].toString());
			
			movimentos.add(f);
		}
		fechaConexao();
		return movimentos;
	}	
	
	public void adicionar(FluxoCaixa f){
		try{
		abrirConexao();
		String query = "insert into usu_tfluxo "
				+ "(usu_ctafin,usu_data,usu_valor,usu_codtns,usu_codccu,usu_observacao,usu_ctared,usu_prorea,usu_numtit,"
				+ "usu_clifor,usu_desctared,usu_desccu,usu_desctafin,usu_orilct) "
				+ "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

		manager.createNativeQuery(query)
		   .setParameter(1, f.getCtafin()).setParameter(2, f.getData()).setParameter(3, f.getValor()).setParameter(4, f.getCodtns()).setParameter(5, f.getCodccu())
		   .setParameter(6, f.getObservacao()).setParameter(7, f.getCtared()).setParameter(8, f.getProrea()).setParameter(9, f.getNumtit()).setParameter(10, f.getClifor())
		   .setParameter(11, f.getDesctared()).setParameter(12, f.getDesccu()).setParameter(13, f.getDesctafin()).setParameter(14, f.getOrilct())
		   .executeUpdate();
		manager.getTransaction().commit();
		fechaConexao();
		}catch(Exception e){
			System.out.println(e);
		}
	}
	
	public void alterar(FluxoCaixa f){
		abrirConexao();
		String query = " UPDATE usu_tfluxo "
				+ "SET usu_ctafin=?, usu_data=?, usu_valor=?,usu_codtns=?,usu_codccu=?,usu_observacao=?,usu_ctared=?,usu_prorea=?,"
				+"		usu_numtit=?,usu_clifor=?,usu_desctared=?,usu_desccu=?, usu_desctafin=?,usu_orilct=? "
				+"		WHERE id = ?; ";
		manager.createNativeQuery(query)
		   .setParameter(1, f.getCtafin()).setParameter(2, f.getData()).setParameter(3, f.getValor()).setParameter(4, f.getCodtns()).setParameter(5, f.getCodccu())
		   .setParameter(6, f.getObservacao()).setParameter(7, f.getCtared()).setParameter(8, f.getProrea()).setParameter(9, f.getNumtit()).setParameter(10, f.getClifor())
		   .setParameter(11, f.getDesctared()).setParameter(12, f.getDesccu()).setParameter(13, f.getDesctafin()).setParameter(14, f.getOrilct()).setParameter(15, f.getId())
		   .executeUpdate();
		manager.getTransaction().commit();
		fechaConexao();
	}
	
	public void remover(Integer id){
		abrirConexao();
		String query = " delete from usu_tfluxo WHERE id = ?; ";
		manager.createNativeQuery(query).setParameter(1, id).executeUpdate();
		manager.getTransaction().commit();
		fechaConexao();
	}

	public List<ClassificacaoFluxoCaixa>  listarClassificacao(){
		abrirConexao();
		String sql ="select ctared,descta from e043pcm where codmpc=2001 and nivcta=2";
		
		@SuppressWarnings("unchecked")
		List<Object[]> list = manager.createNativeQuery(sql).getResultList();
		List<ClassificacaoFluxoCaixa> classificacoes = new ArrayList<ClassificacaoFluxoCaixa>();
		ClassificacaoFluxoCaixa cfc = new ClassificacaoFluxoCaixa();
		cfc.setCtared(0);
		cfc.setDescta("");
		classificacoes.add(cfc);		
		for(Object[] o : list) {
			ClassificacaoFluxoCaixa c = new ClassificacaoFluxoCaixa();
			c.setCtared((int) o[0]);
			c.setDescta(o[1].toString());
			classificacoes.add(c);
		}
		fechaConexao();
		return classificacoes;
	}	
	
}
