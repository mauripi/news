package br.com.mauricio.news.mb.contabil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.joda.time.DateTime;

import br.com.mauricio.news.ln.contabil.GestaoNotaFiscalLN;
import br.com.mauricio.news.model.contabil.GestaoNotaFiscal;

/**
*
* @author MAURICIO CRUZ
*/
@ManagedBean(name="gestaonotafiscaMB")
@ViewScoped
public class GestaoNotaFiscaBean implements Serializable{
	private static final long serialVersionUID = 1L;
	private GestaoNotaFiscalLN ln;
	private List<GestaoNotaFiscal> notas;
	private List<GestaoNotaFiscal> filtradas;	
	private Integer mes;
	private Integer ano;
	private Integer opcao=1;
	
	@PostConstruct
	public void init(){
		carregaMesAno();
		listarNotas();
		todasNotas();
	}

	public void listarNotas() {
		ln = new GestaoNotaFiscalLN();
		notas = ln.buscarPorCompetencia(mes, ano);
		listar();
	}

	private void carregaMesAno() {
		DateTime hoje = new DateTime();
		mes = hoje.getMonthOfYear();
		ano = hoje.getYear();
	}

	public void listar(){

		switch (opcao) {
			case 1:
				todasNotas();
				break;
			case 2:
				somenteLancadas();
				break;
			case 3:
				somenteNaoLancadas();
				break;
			default:
				todasNotas();
				break;
		}
	}
	
	private void somenteLancadas(){
		filtradas = new ArrayList<GestaoNotaFiscal>();
		for (GestaoNotaFiscal n : notas)
			if(n.getStatus().equals("FATURADAS") && n.getNumnfc()!=null)
				filtradas.add(n);
	}
	
	private void somenteNaoLancadas(){
		filtradas = new ArrayList<GestaoNotaFiscal>();
		for (GestaoNotaFiscal n : notas)
			if(n.getStatus().equals("NAO_FATURADAS"))
				filtradas.add(n);
	}

	private void todasNotas(){
		filtradas = new ArrayList<GestaoNotaFiscal>();
		filtradas = notas;
	}

	public List<GestaoNotaFiscal> getFiltradas() {
		return filtradas;
	}

	public void setFiltradas(List<GestaoNotaFiscal> filtradas) {
		this.filtradas = filtradas;
	}

	public Integer getMes() {
		return mes;
	}

	public void setMes(Integer mes) {
		this.mes = mes;
	}

	public Integer getAno() {
		return ano;
	}

	public void setAno(Integer ano) {
		this.ano = ano;
	}

	public Integer getOpcao() {
		return opcao;
	}

	public void setOpcao(Integer opcao) {
		this.opcao = opcao;
	}
	
	
}
