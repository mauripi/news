package br.com.mauricio.news.ln.ti;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.mauricio.news.cnn.Conexao;
import br.com.mauricio.news.dao.GenericDao;
import br.com.mauricio.news.dao.ti.LicencaDao;
import br.com.mauricio.news.model.ti.Licenca;
import br.com.mauricio.news.util.UsuarioLogado;


public class LicencaLN implements Serializable {

	private static final long serialVersionUID = 1L;
	private GenericDao<Licenca> dao;
	private EntityManager manager;

	public LicencaLN(){
		Conexao c = new Conexao();
		manager = c.getEntityManager();
	}
	
	public Licenca getById(int id) {
		dao = new GenericDao<Licenca>();
		return (Licenca) dao.findById(Licenca.class,id);
	}

	public List<Licenca> getList() {
		dao = new GenericDao<Licenca>();
		return dao.list("Licenca", "id");
	}
	
	public void atualizaLicencasEquipamento(Licenca l,int operacao){
		GenericDao<Licenca> daol = new GenericDao<Licenca>();
		
		Licenca x=(Licenca) daol.findById(Licenca.class, l.getId());;
		if(operacao==1)//add
			x.setQtdDisponivel(l.getQtdDisponivel()+1);		
		if(operacao==2)//del
			x.setQtdDisponivel(l.getQtdDisponivel()-1);	
		x.setDataAlteracao(new Date(System.currentTimeMillis()));
		x.setUsuarioAlteracao(UsuarioLogado.getUser());		
		daol.update(x);
	}
	
	public List<Licenca> getLicencasDisponiveis() {
		LicencaDao daol = new LicencaDao();
		return daol.licencasDisponiveis();
	}

	
	
	
	public GenericDao<Licenca> getDao() {
		return dao;
	}

	public void setDao(GenericDao<Licenca> dao) {
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
