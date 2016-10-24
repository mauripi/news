package br.com.mauricio.news.model.contabil;

import java.util.Date;
import java.util.List;

public class Agregado {
	private String patrimonio;
	private String descricaoBem;
	private Date dataaquisicao;	
	private Double vlraquisicao;
	private Double vlrresidual;
	private List<Agregado> agregados;
	
	public Agregado(List<Agregado> agregados){
		this.agregados=agregados;
	}
	
	public Agregado(String patrimonio,String descricaoBem,Date dataaquisicao,Double vlraquisicao,Double vlrresidual){
		this.patrimonio = patrimonio;
		this.descricaoBem=descricaoBem;
		this.dataaquisicao=dataaquisicao;
		this.vlraquisicao=vlraquisicao;
		this.vlrresidual=vlrresidual;				
	}

	public List<Agregado> getAgregados() {
		return this.agregados;
	}

	public String getPatrimonio() {
		return patrimonio;
	}

	public void setPatrimonio(String patrimonio) {
		this.patrimonio = patrimonio;
	}

	public String getDescricaoBem() {
		return descricaoBem;
	}

	public void setDescricaoBem(String descricaoBem) {
		this.descricaoBem = descricaoBem;
	}

	public Date getDataaquisicao() {
		return dataaquisicao;
	}

	public void setDataaquisicao(Date dataaquisicao) {
		this.dataaquisicao = dataaquisicao;
	}

	public Double getVlraquisicao() {
		return vlraquisicao;
	}

	public void setVlraquisicao(Double vlraquisicao) {
		this.vlraquisicao = vlraquisicao;
	}

	public Double getVlrresidual() {
		return vlrresidual;
	}

	public void setVlrresidual(Double vlrresidual) {
		this.vlrresidual = vlrresidual;
	}

	public void setAgregados(List<Agregado> agregados) {
		this.agregados = agregados;
	}

}
