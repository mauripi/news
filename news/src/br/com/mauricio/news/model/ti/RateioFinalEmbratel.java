package br.com.mauricio.news.model.ti;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

public class RateioFinalEmbratel implements Serializable{

	private static final long serialVersionUID = 1L;
	private Locale localeBR = new Locale( "pt", "BR" );
	private String ccusto;
	private String tipo;
	private String servico;
	private Integer filial; 
	private BigDecimal valor;

	public String valorFormatado(){
		NumberFormat dinheiroBR = NumberFormat.getCurrencyInstance(localeBR);
		return dinheiroBR.format(this.valor);		
	}
	
	public String getCcusto() {
		return ccusto;
	}
	public void setCcusto(String ccusto) {
		this.ccusto = ccusto;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getServico() {
		return servico;
	}
	public void setServico(String servico) {
		this.servico = servico;
	}
	public Integer getFilial() {
		return filial;
	}
	public void setFilial(Integer filial) {
		this.filial = filial;
	}
	public BigDecimal getValor() {
		return valor;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ccusto == null) ? 0 : ccusto.hashCode());
		result = prime * result + ((filial == null) ? 0 : filial.hashCode());
		result = prime * result + ((servico == null) ? 0 : servico.hashCode());
		result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
		result = prime * result + ((valor == null) ? 0 : valor.hashCode());
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
		RateioFinalEmbratel other = (RateioFinalEmbratel) obj;
		if (ccusto == null) {
			if (other.ccusto != null)
				return false;
		} else if (!ccusto.equals(other.ccusto))
			return false;
		if (filial == null) {
			if (other.filial != null)
				return false;
		} else if (!filial.equals(other.filial))
			return false;
		if (servico == null) {
			if (other.servico != null)
				return false;
		} else if (!servico.equals(other.servico))
			return false;
		if (tipo == null) {
			if (other.tipo != null)
				return false;
		} else if (!tipo.equals(other.tipo))
			return false;
		if (valor == null) {
			if (other.valor != null)
				return false;
		} else if (!valor.equals(other.valor))
			return false;
		return true;
	}

}
