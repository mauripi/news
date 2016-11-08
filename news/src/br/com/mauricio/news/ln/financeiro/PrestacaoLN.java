package br.com.mauricio.news.ln.financeiro;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.mauricio.news.cnn.Conexao;
import br.com.mauricio.news.dao.GenericDao;
import br.com.mauricio.news.dao.financeiro.PrestacaoContaDao;
import br.com.mauricio.news.model.Login;
import br.com.mauricio.news.model.financeiro.Despesa;
import br.com.mauricio.news.model.financeiro.PrestacaoConta;
/**
*
* @author MAURICIO CRUZ
*/
public class PrestacaoLN implements Serializable {

	private static final long serialVersionUID = 1L;
	private EntityManager manager;
	private GenericDao<PrestacaoConta> dao;
	
	public PrestacaoLN(){
		Conexao c = new Conexao();
		this.manager = c.getEntityManager();
	}

	public PrestacaoConta getById(int id) {
		dao = new GenericDao<PrestacaoConta>();
		return (PrestacaoConta) dao.findById(PrestacaoConta.class,id);
	}

	public List<PrestacaoConta> getList() {
		dao = new GenericDao<PrestacaoConta>();
		return dao.list("contrato", "id");
	}
	
	public String create(PrestacaoConta p) {
		dao = new GenericDao<PrestacaoConta>();
		GenericDao<Login> daol = new GenericDao<Login>();
		GenericDao<Despesa> daod = new GenericDao<Despesa>();
		p.setColaborador((Login) daol.findById(Login.class, p.getColaborador().getId()));
		p.setUsuarioCriacao((Login) daol.findById(Login.class, p.getUsuarioCriacao().getId()));
		dao.save(p);
		for(int i=0;i<p.getDespesas().size();i++){
			p.getDespesas().get(i).setUsuarioCriacao(p.getUsuarioCriacao());
			p.getDespesas().get(i).setDataCriacao(p.getDataCriacao());
			p.getDespesas().get(i).setPrestacaoconta(p);
			p.getDespesas().get(i).setRemovido(0);
			daod.save(p.getDespesas().get(i));
		}
		return "Castrado com sucesso.";
	}

	public String update(PrestacaoConta p, List<Despesa> despesaParaRemover) {
		dao = new GenericDao<PrestacaoConta>();
		GenericDao<Despesa> daod = new GenericDao<Despesa>();
		GenericDao<Login> daol = new GenericDao<Login>();
		p.setColaborador((Login) daol.findById(Login.class, p.getColaborador().getId()));
		p.setUsuarioAlteracao((Login) daol.findById(Login.class, p.getUsuarioAlteracao().getId()));
		dao.update(p);	

		if(despesaParaRemover.size()>0)
			for(Despesa d:despesaParaRemover)
				daod.delete(daod.findById(Despesa.class, d.getId()));
				
		return "Atualizado com sucesso.";
	}

	public String delete(PrestacaoConta p) {
		dao = new GenericDao<PrestacaoConta>();
		dao.update(p);
		return "Removido com sucesso.";
	}
	
	public List<PrestacaoConta> listaPorFuncionario(Login funcionario){
		PrestacaoContaDao dao = new PrestacaoContaDao(manager);
		return dao.list(funcionario);
	}

}
