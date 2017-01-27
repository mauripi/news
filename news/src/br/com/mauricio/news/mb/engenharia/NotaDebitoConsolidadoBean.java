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
	private List<ViewNotaDebito> debitos1 = new ArrayList<ViewNotaDebito>();
	private List<ViewNotaDebito> debitos2 = new ArrayList<ViewNotaDebito>();
	private List<ViewNotaDebito> all = new ArrayList<ViewNotaDebito>();
	 private Map<Integer, Integer> anos;
	
	@PostConstruct
	public void init(){
		ano = (new DateTime()).getYear();
		listYear();
		listar();
	}
	
	public void listar(){
		ln = new NotaDebitoEnergiaLN();
		all = ln.getNotaDebitoConsolidados(ano);
		for(ViewNotaDebito v:all)
			if(v.getTiploc()==1)
				debitos1.add(v);
			else
				debitos2.add(v);
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

	public List<ViewNotaDebito> getDebitos1() {
		return debitos1;
	}

	public void setDebitos1(List<ViewNotaDebito> debitos1) {
		this.debitos1 = debitos1;
	}

	public List<ViewNotaDebito> getDebitos2() {
		return debitos2;
	}

	public void setDebitos2(List<ViewNotaDebito> debitos2) {
		this.debitos2 = debitos2;
	}

	public List<ViewNotaDebito> getAll() {
		return all;
	}

	public void setAll(List<ViewNotaDebito> all) {
		this.all = all;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
