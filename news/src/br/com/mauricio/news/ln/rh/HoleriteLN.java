package br.com.mauricio.news.ln.rh;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.com.mauricio.news.dao.rh.VencimentoDao;
import br.com.mauricio.news.model.rh.Base;
import br.com.mauricio.news.model.rh.Holerite;
import br.com.mauricio.news.model.Login;
import br.com.mauricio.news.model.rh.Vencimento;

public class HoleriteLN implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -745593021993066877L;

	
	public List<Holerite> listaHolerite(Login l,int mes,int ano,int periodo){
		VencimentoDao vDao = new VencimentoDao();
		List<Vencimento> vencs = new ArrayList<Vencimento>();
		vencs = vDao.getVencimentos(l, mes, ano,periodo);
		List<Holerite> hls = new ArrayList<Holerite>();
		
		for(int i=0;i<vencs.size();i++){
			if(!vencs.get(i).getTipo().equals("B")){
				Holerite h = new Holerite();
				h.setCodigo(vencs.get(i).getCodigo());
				h.setDescricao(vencs.get(i).getDescricao());
				h.setReferencia(vencs.get(i).getReferencia());
				h.setPeriodo(vencs.get(i).getPeriodo());
				if(vencs.get(i).getTipo().equals("P")){
					h.setProvento(vencs.get(i).getValor());
				}
				if(vencs.get(i).getTipo().equals("D")){
					h.setDesconto(vencs.get(i).getValor());
				}			
				hls.add(h);
			}
		}
		return hls;
	}
	
	public Double getTotalProvento(List<Holerite> hls){
		Double total = 0.0;
		for(int i=0;i<hls.size();i++){
			if(hls.get(i).getProvento()!=null){
				total+=hls.get(i).getProvento();				
			}			
		}		
		return total;
	}
	
	public Double getTotalDesconto(List<Holerite> hls){
		Double total = 0.0;
		for(int i=0;i<hls.size();i++){
			if(hls.get(i).getDesconto()!=null){
				total+=hls.get(i).getDesconto();				
			}
		}
		return total;
	}
	public Double getTotalLiquido(Double provento, Double desconto){
		Double total = 0.0;
		total = provento-desconto;
		return total;
	}

	public Double calculaFGTS(List<Holerite> eventos, Base baseSelecionada,Login l) {
		VencimentoDao vDao = new VencimentoDao();
		Double valorEvento0200 = vDao.getFGTS13(l, baseSelecionada.getMes(), baseSelecionada.getAno(), 3);
		Double valorFGTS = 0.0;		
		if(baseSelecionada.getCbo().equals("412205"))
			valorFGTS = ((baseSelecionada.getBaseFGTS()+ valorEvento0200)*0.02);
		else
			valorFGTS = ((baseSelecionada.getBaseFGTS()+ valorEvento0200)*0.08);
		return valorFGTS;
	}
}
