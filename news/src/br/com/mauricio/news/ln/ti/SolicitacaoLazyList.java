package br.com.mauricio.news.ln.ti;

import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import br.com.mauricio.news.dao.ti.SolicitacaoDao;
import br.com.mauricio.news.model.ti.Solicitacao;

public class SolicitacaoLazyList extends LazyDataModel<Solicitacao> {

	private static final long serialVersionUID = 1L;
	private List<Solicitacao> solicitacoes;
	private SolicitacaoDao dao;
	
	@Override
	public List<Solicitacao> load(int startingAt, int maxPerPage, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        try {
            dao =  new SolicitacaoDao();
            solicitacoes = dao.listAbertas(startingAt, maxPerPage);
        } catch (Exception e) {  e.printStackTrace(); }
        if(getRowCount() <= 0)           
        	setRowCount(dao.countSolicitacaoTotal());
        
        setPageSize(maxPerPage);		
		return solicitacoes;		
	}
	
    @Override
    public Object getRowKey(Solicitacao s) {
        return s.getId();
    }
 
    @Override
    public Solicitacao getRowData(String sol_id) {
        Integer id = Integer.valueOf(sol_id);
 
        for (Solicitacao s : solicitacoes) {
            if(id.equals(s.getId())){
                return s;
            }
        }
        return null;
    }	
	
	
}
