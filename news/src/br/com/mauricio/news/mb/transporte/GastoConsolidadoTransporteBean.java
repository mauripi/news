package br.com.mauricio.news.mb.transporte;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.joda.time.DateTime;

import br.com.mauricio.news.ln.transporte.GastoTransporteLN;
import br.com.mauricio.news.model.transporte.ViewGastoTrans;

@ManagedBean(name="consolidadotransporteMB")
@SessionScoped
public class GastoConsolidadoTransporteBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private GastoTransporteLN ln;
	private Integer ano;
	private List<ViewGastoTrans> gastos = new ArrayList<ViewGastoTrans>();
	private List<ViewGastoTrans> totais = new ArrayList<ViewGastoTrans>();
	private ViewGastoTrans total;
	 private Map<Integer, Integer> anos;
	
	@PostConstruct
	public void init(){
		ano = (new DateTime()).getYear();
		listYear();
		listar();
	}
	
	public void listar(){
		ln = new GastoTransporteLN();
		gastos = ln.getGastosConsolidados(ano);
		somaTotais();
	}

	
	private void listYear(){
		anos = new LinkedHashMap<Integer, Integer>();
		int year = ano;
		while (year >= 2015){
			anos.put(year, year);
			year--;
		}
	}
	
	private void somaTotais(){
		totais = new ArrayList<ViewGastoTrans>();
		total = new ViewGastoTrans();
		total.setDespesa("TOTAIS");
		for(ViewGastoTrans v:gastos){
			total.setJaneiro(v.getJaneiro()+total.getJaneiro());
			total.setFevereiro(v.getFevereiro()+total.getFevereiro());
			total.setMarco(v.getMarco()+total.getMarco());
			total.setAbril(v.getAbril()+total.getAbril());
			total.setMaio(v.getMaio()+total.getMaio());
			total.setJunho(v.getJunho()+total.getJunho());
			total.setJulho(v.getJulho()+total.getJulho());
			total.setAgosto(v.getAgosto()+total.getAgosto());
			total.setSetembro(v.getSetembro()+total.getSetembro());
			total.setOutubro(v.getOutubro()+total.getOutubro());
			total.setNovembro(v.getNovembro()+total.getNovembro());
			total.setDezembro(v.getDezembro()+total.getDezembro());
			total.setTotal(total.getJaneiro()+total.getFevereiro()+total.getMarco()+
					total.getAbril()+total.getMaio()+total.getJunho()+total.getJulho()+
					total.getAgosto()+total.getSetembro()+total.getOutubro()+total.getNovembro()+total.getDezembro() );

		}
		totais.add(total);
	}
	
	/*==========================================================*/
	public GastoTransporteLN getLn() {
		return ln;
	}

	public void setLn(GastoTransporteLN ln) {
		this.ln = ln;
	}

	public Integer getAno() {
		return ano;
	}

	public void setAno(Integer ano) {
		this.ano = ano;
	}

	public List<ViewGastoTrans> getGastos() {
		return gastos;
	}

	public void setGastos(List<ViewGastoTrans> gastos) {
		this.gastos = gastos;
	}

	public Map<Integer, Integer> getAnos() {
		return anos;
	}

	public void setAnos(Map<Integer, Integer> anos) {
		this.anos = anos;
	}

	public List<ViewGastoTrans> getTotais() {
		return totais;
	}

	public void setTotais(List<ViewGastoTrans> totais) {
		this.totais = totais;
	}

	public ViewGastoTrans getTotal() {
		return total;
	}

	public void setTotal(ViewGastoTrans total) {
		this.total = total;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
