package br.com.mauricio.news.ln.rh;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import br.com.mauricio.news.dao.rh.VencimentoDao;
import br.com.mauricio.news.model.rh.Base;
import br.com.mauricio.news.model.rh.Holerite;
import br.com.mauricio.news.model.Login;
import br.com.mauricio.news.model.rh.Vencimento;
/**
 * @author Mauricio Cruz
 */
public class HoleriteLN implements Serializable {

	private static final long serialVersionUID = -745593021993066877L;
	
	public List<Holerite> getHolerites(Login l,int mes,int ano,int periodo){
		VencimentoDao vDao = new VencimentoDao();
		List<Vencimento> vencs = vDao.getVencimentos(l, mes, ano,periodo);
		List<Holerite> hls = new ArrayList<Holerite>();		
		vencs.stream().filter(v -> !v.getTipo().equals("B")).forEach(x -> hls.add(getHolerite(x)));
		return hls;
	}
	
	private Holerite getHolerite(Vencimento v){
		Holerite h = new Holerite();
		h.setCodigo(v.getCodigo());
		h.setDescricao(v.getDescricao());
		h.setReferencia(v.getReferencia());
		h.setPeriodo(v.getPeriodo());
		if(v.getTipo().equals("P")){
			h.setProvento(v.getValor());
		}
		if(v.getTipo().equals("D")){
			h.setDesconto(v.getValor());
		}			
		return h;			
	}
	
	public Double getTotalProvento(List<Holerite> hls){
		return hls.stream().filter(h -> h.getProvento()!=null)
				.mapToDouble(h -> h.getProvento()).sum();
	}
	
	public Double getTotalDesconto(List<Holerite> hls){
		return hls.stream().filter(h -> h.getDesconto()!=null)
				.mapToDouble(h -> h.getDesconto()).sum();
	}
	
	public Double getTotalLiquido(Double provento, Double desconto){
		Double total = 0.0;
		total = provento-desconto;
		return total;
	}

	public Double calculaFGTS(List<Holerite> eventos, Base baseSelecionada,Login l) {
		VencimentoDao vDao = new VencimentoDao();
		Double valorEvento0200 = vDao.getFGTS13(l, baseSelecionada.getMes(), baseSelecionada.getAno(), 3);	
		if(baseSelecionada.getCbo().equals("412205"))
			return ((baseSelecionada.getBaseFGTS()+ valorEvento0200)*0.02);
		else
			return ((baseSelecionada.getBaseFGTS()+ valorEvento0200)*0.08);
	}
}
