package br.com.mauricio.news.ln.ti;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.mauricio.news.cnn.Conexao;
import br.com.mauricio.news.dao.GenericDao;
import br.com.mauricio.news.model.ti.Equipamento;
import br.com.mauricio.news.model.ti.Licenca;
import br.com.mauricio.news.model.ti.TipoEquipamento;


public class EquipamentoLN implements Serializable {

	private static final long serialVersionUID = 1L;
	private GenericDao<Equipamento> dao;
	private EntityManager manager;

	public EquipamentoLN(){
		Conexao c = new Conexao();
		manager = c.getEntityManager();
	}
	
	public Equipamento getById(int id) {
		dao = new GenericDao<Equipamento>();
		return (Equipamento) dao.findById(Equipamento.class,id);
	}

	public List<Equipamento> getList() {
		dao = new GenericDao<Equipamento>();
		return dao.list("equipamento", "id");
	}
	
	public String create(Equipamento e) {
		dao = new GenericDao<Equipamento>();
		GenericDao<TipoEquipamento> daot = new GenericDao<TipoEquipamento>();
		e.setTipo((TipoEquipamento) daot.findById(TipoEquipamento.class, e.getTipo().getId()));
		dao.save(e);
		return "Castrado com sucesso.";
	}

	public String update(Equipamento e) {
		dao = new GenericDao<Equipamento>();
		dao.update(e);		
		return "Atualizado com sucesso.";
	}

	public String delete(Equipamento e) {
		dao = new GenericDao<Equipamento>();
		dao.delete((Equipamento)dao.findById(e.getClass(), e.getId()));
		return "Removido com sucesso.";
	}

	public void liberaLicencasEquipamentoExcluido(Equipamento e){
		GenericDao<Licenca> daol = new GenericDao<Licenca>();
		Licenca x;
		for(Licenca l:e.getLicencas()){
			x = (Licenca) daol.findById(Licenca.class, l.getId());
			x.setQtdDisponivel(l.getQtdDisponivel()+1);
			x.setUsuarioAlteracao(e.getUsuarioAlteracao());
			x.setDataAlteracao(e.getDataAlteracao());
			daol.update(x);
		}
	}
	

	public List<Licenca> getLicencas() {
		GenericDao<Licenca> daol = new GenericDao<Licenca>();
		return daol.list("licenca", "id");
	}
	
	public GenericDao<Equipamento> getDao() {
		return dao;
	}

	public void setDao(GenericDao<Equipamento> dao) {
		this.dao = dao;
	}

	public EntityManager getManager() {
		return manager;
	}

	public void setManager(EntityManager manager) {
		this.manager = manager;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

     
}
