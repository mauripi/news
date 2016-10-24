package br.com.mauricio.news.ln.ti;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.mauricio.news.cnn.Conexao;
import br.com.mauricio.news.dao.GenericDao;
import br.com.mauricio.news.dao.ti.HistoricoSolicitacaoDao;
import br.com.mauricio.news.model.ti.Atendente;
import br.com.mauricio.news.model.ti.HistoricoSolicitacao;
import br.com.mauricio.news.model.ti.Solicitacao;
import br.com.mauricio.news.model.ti.StatusSolicitacao;


public class HistoricoSolicitacaoLN implements Serializable {

	private static final long serialVersionUID = 1L;
	private GenericDao<HistoricoSolicitacao> dao;
	private EntityManager manager;

	public HistoricoSolicitacaoLN(EntityManager manager){
		this.manager = manager;
	}
	public HistoricoSolicitacaoLN(){
		Conexao c = new Conexao();
		manager = c.getEntityManager();
	}	

	public HistoricoSolicitacao getById(int id) {
		dao = new GenericDao<HistoricoSolicitacao>();
		return (HistoricoSolicitacao) dao.findById(HistoricoSolicitacao.class,id);
	}

	public List<HistoricoSolicitacao> getList() {
		dao = new GenericDao<HistoricoSolicitacao>();
		return dao.list("solicitacao_hist", "id");
	}
	
	public List<HistoricoSolicitacao> findyBySolicitacao(Solicitacao solicitacao){
		HistoricoSolicitacaoDao dao = new HistoricoSolicitacaoDao();
		return dao.findyBySolicitacao(solicitacao);
	}
	
	private Atendente getAtendente(HistoricoSolicitacao hist){
		GenericDao<Atendente> daoa = new GenericDao<Atendente>();
		return daoa.findById((new Atendente()).getClass(), hist.getAtendente().getId());
	}
	
	public String create(HistoricoSolicitacao hist) {
		dao = new GenericDao<HistoricoSolicitacao>();
		if(hist.getStatus().equals(StatusSolicitacao.ABERTA))
		hist.setAtendente(getAtendente(hist));
		dao.save(hist);
		return "Castrado com sucesso.";
	}

	public String create(HistoricoSolicitacao hist,EntityManager manager2) {
		dao = new GenericDao<HistoricoSolicitacao>(manager2);
		if(hist.getStatus().equals(StatusSolicitacao.ABERTA))
		hist.setAtendente(getAtendente(hist));
		dao.save(hist);
		return "Castrado com sucesso.";
	}
	
	public String update(HistoricoSolicitacao hist) {
		dao = new GenericDao<HistoricoSolicitacao>();
		dao.update(hist);		
		return "Atualizado com sucesso.";
	}

	public String delete(HistoricoSolicitacao hist) {
		dao = new GenericDao<HistoricoSolicitacao>();
		try{
			dao.delete((HistoricoSolicitacao)dao.findById(hist.getClass(), hist.getId()));
		}catch(Exception e){
			System.out.println(e.getLocalizedMessage() + "--> em: HistoricoSolicitacaoLN.delete(HistoricoSolicitacao hist) + solicitaçao id="+hist.getSolicitacao().getId());
		}
		return "Removido com sucesso.";
	}

	public String delete(List<HistoricoSolicitacao> hs) {
		dao = new GenericDao<HistoricoSolicitacao>();
		dao.deleteList(hs);
		return "Removido com sucesso.";
	}
	
	public HistoricoSolicitacao lastInteraction(List<HistoricoSolicitacao> hs){
    	if(hs!=null)
	    	Collections.sort(hs);		
		HistoricoSolicitacao h=new HistoricoSolicitacao();
		if(hs!=null){
			if(hs.size() > 1){
				h = hs.get(0);
				for(int i=0;i<hs.size();i++){
					if(hs.get(i).getId()>h.getId()){
						h=hs.get(i);
					}
				}
			}else{
				h=hs.get(0);
			}
		}
		return h;	
	}	
	
	public Boolean verificaSeJaEntrouEmAtendimento(List<HistoricoSolicitacao> hs){
		Boolean emAtendimento=false;
		for(HistoricoSolicitacao h:hs)
			if(h.getStatus().equals(StatusSolicitacao.EM_ATENDIMENTO))
				emAtendimento=true;
		return emAtendimento;		
	}
	
    public List<HistoricoSolicitacao> ordenarHistorico(Solicitacao solicitacao){
    	List<HistoricoSolicitacao> hs = solicitacao.getHistoricos();
    	if(hs!=null)
	    	Collections.sort(hs);
		return hs;
    }			
	
	public GenericDao<HistoricoSolicitacao> getDao() {
		return dao;
	}

	public void setDao(GenericDao<HistoricoSolicitacao> dao) {
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
