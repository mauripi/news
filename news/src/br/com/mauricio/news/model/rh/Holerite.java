package br.com.mauricio.news.model.rh;

import java.io.Serializable;

/**
*
* @author MAURICIO CRUZ
*/
public class Holerite implements Serializable{

	private static final long serialVersionUID = 1L;
	private int codigo;
	private String descricao;
	private Double referencia;
	private Double provento;
	private Double desconto;
	private int periodo;

	
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public Double getReferencia() {
		return referencia;
	}
	public void setReferencia(Double referencia) {
		this.referencia = referencia;
	}
	public Double getProvento() {
		return provento;
	}
	public void setProvento(Double provento) {
		this.provento = provento;
	}
	public Double getDesconto() {
		return desconto;
	}
	public void setDesconto(Double desconto) {
		this.desconto = desconto;
	}
	public int getPeriodo() {
		return periodo;
	}
	public void setPeriodo(int periodo) {
		this.periodo = periodo;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + codigo;
		result = prime * result
				+ ((desconto == null) ? 0 : desconto.hashCode());
		result = prime * result
				+ ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result + periodo;
		result = prime * result
				+ ((provento == null) ? 0 : provento.hashCode());
		result = prime * result
				+ ((referencia == null) ? 0 : referencia.hashCode());
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
		Holerite other = (Holerite) obj;
		if (codigo != other.codigo)
			return false;
		if (desconto == null) {
			if (other.desconto != null)
				return false;
		} else if (!desconto.equals(other.desconto))
			return false;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
			return false;
		if (periodo != other.periodo)
			return false;
		if (provento == null) {
			if (other.provento != null)
				return false;
		} else if (!provento.equals(other.provento))
			return false;
		if (referencia == null) {
			if (other.referencia != null)
				return false;
		} else if (!referencia.equals(other.referencia))
			return false;
		return true;
	}
	
}
