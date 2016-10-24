package br.com.mauricio.news.model.financeiro;

import java.io.Serializable;

public class Custo implements Serializable{

	private static final long serialVersionUID = 1L;
	private Integer ano;
	private Integer mes;
	private Double fixo;
	private Double variavel;
	private Double investimento;
	public Integer getAno() {
		return ano;
	}
	public void setAno(Integer ano) {
		this.ano = ano;
	}
	public Integer getMes() {
		return mes;
	}
	public void setMes(Integer mes) {
		this.mes = mes;
	}
	public Double getFixo() {
		return fixo;
	}
	public void setFixo(Double fixo) {
		this.fixo = fixo;
	}
	public Double getVariavel() {
		return variavel;
	}
	public void setVariavel(Double variavel) {
		this.variavel = variavel;
	}
	public Double getInvestimento() {
		return investimento;
	}
	public void setInvestimento(Double investimento) {
		this.investimento = investimento;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ano == null) ? 0 : ano.hashCode());
		result = prime * result + ((fixo == null) ? 0 : fixo.hashCode());
		result = prime * result
				+ ((investimento == null) ? 0 : investimento.hashCode());
		result = prime * result + ((mes == null) ? 0 : mes.hashCode());
		result = prime * result
				+ ((variavel == null) ? 0 : variavel.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Custo other = (Custo) obj;
		if (ano == null) {
			if (other.ano != null)
				return false;
		} else if (!ano.equals(other.ano))
			return false;
		if (fixo == null) {
			if (other.fixo != null)
				return false;
		} else if (!fixo.equals(other.fixo))
			return false;
		if (investimento == null) {
			if (other.investimento != null)
				return false;
		} else if (!investimento.equals(other.investimento))
			return false;
		if (mes == null) {
			if (other.mes != null)
				return false;
		} else if (!mes.equals(other.mes))
			return false;
		if (variavel == null) {
			if (other.variavel != null)
				return false;
		} else if (!variavel.equals(other.variavel))
			return false;
		return true;
	}
	
}
