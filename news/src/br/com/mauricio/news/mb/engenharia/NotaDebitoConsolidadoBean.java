package br.com.mauricio.news.mb.engenharia;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.joda.time.DateTime;

import br.com.mauricio.news.ln.engenharia.NotaDebitoEnergiaLN;
import br.com.mauricio.news.model.engenharia.ViewNotaDebito;

@ManagedBean(name="notadebitoconsolidadoMB")
@SessionScoped
public class NotaDebitoConsolidadoBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private NotaDebitoEnergiaLN ln;
	private Integer ano;
	private List<ViewNotaDebito> debitos = new ArrayList<ViewNotaDebito>();
	 private Map<Integer, Integer> anos;
	
	@PostConstruct
	public void init(){
		ano = (new DateTime()).getYear();
		listYear();
		listar();
	}
	
	public void listar(){
		ln = new NotaDebitoEnergiaLN();
		debitos = ln.getNotaDebitoConsolidados(ano);
	}

	
	private void listYear(){
		anos = new LinkedHashMap<Integer, Integer>();
		int year = ano;
		while (year >= 2015){
			anos.put(year, year);
			year--;
		}
	}
	
	
	
	/*==========================================================*/
	public NotaDebitoEnergiaLN getLn() {
		return ln;
	}

	public void setLn(NotaDebitoEnergiaLN ln) {
		this.ln = ln;
	}

	public Integer getAno() {
		return ano;
	}

	public void setAno(Integer ano) {
		this.ano = ano;
	}

	public Map<Integer, Integer> getAnos() {
		return anos;
	}

	public void setAnos(Map<Integer, Integer> anos) {
		this.anos = anos;
	}

	public List<ViewNotaDebito> getDebitos() {
		return debitos;
	}

	public void setDebitos(List<ViewNotaDebito> debitos) {
		this.debitos = debitos;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
