package br.com.mauricio.news.model.marketing;

import java.io.Serializable;
import java.util.Date;

/**
*
* @author MAURICIO CRUZ
*/
public class PosVenda implements Serializable{

	private static final long serialVersionUID = 1L;
	private Date data;
	private String hora;
	private int mes;
	private int ano;
	private String formato;
	private String duracao;
	private String programamidiamais;
	private String negociacao;
	private String cliente;
	private String projeto;
	private String tipo;
	private String agencia;
	private Double valorTabela;
	private Double desconto;
	private Double valorBruto;
	private Double valorLiquido;
	private String titulo;
	private Double audiencia;
	private Double dratper;
	private Double dratabs;
	private Double dcovper;
	private Double dcovabs;
	private Double iratper;
	private Double iratabs;
	private Double icovper;
	private Double icovabs;

	
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public String getHora() {
		return hora;
	}
	public void setHora(String hora) {
		this.hora = hora;
	}
	public int getMes() {
		return mes;
	}
	public void setMes(int mes) {
		this.mes = mes;
	}
	public int getAno() {
		return ano;
	}
	public void setAno(int ano) {
		this.ano = ano;
	}
	public String getFormato() {
		return formato;
	}
	public void setFormato(String formato) {
		this.formato = formato;
	}
	public String getDuracao() {
		return duracao;
	}
	public void setDuracao(String duracao) {
		this.duracao = duracao;
	}
	public String getProgramamidiamais() {
		return programamidiamais;
	}
	public void setProgramamidiamais(String programamidiamais) {
		this.programamidiamais = programamidiamais;
	}
	public String getNegociacao() {
		return negociacao;
	}
	public void setNegociacao(String negociacao) {
		this.negociacao = negociacao;
	}
	public String getCliente() {
		return cliente;
	}
	public void setCliente(String cliente) {
		this.cliente = cliente;
	}
	public String getProjeto() {
		return projeto;
	}
	public void setProjeto(String projeto) {
		this.projeto = projeto;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getAgencia() {
		return agencia;
	}
	public void setAgencia(String agencia) {
		this.agencia = agencia;
	}
	public Double getValorTabela() {
		return valorTabela;
	}
	public void setValorTabela(Double valorTabela) {
		this.valorTabela = valorTabela;
	}
	public Double getDesconto() {
		return desconto;
	}
	public void setDesconto(Double desconto) {
		this.desconto = desconto;
	}
	public Double getValorBruto() {
		return valorBruto;
	}
	public void setValorBruto(Double valorBruto) {
		this.valorBruto = valorBruto;
	}
	public Double getValorLiquido() {
		return valorLiquido;
	}
	public void setValorLiquido(Double valorLiquido) {
		this.valorLiquido = valorLiquido;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public Double getAudiencia() {
		return audiencia;
	}
	public void setAudiencia(Double audiencia) {
		this.audiencia = audiencia;
	}
	public Double getDratper() {
		return dratper;
	}
	public void setDratper(Double dratper) {
		this.dratper = dratper;
	}
	public Double getDratabs() {
		return dratabs;
	}
	public void setDratabs(Double dratabs) {
		this.dratabs = dratabs;
	}
	public Double getDcovper() {
		return dcovper;
	}
	public void setDcovper(Double dcovper) {
		this.dcovper = dcovper;
	}
	public Double getDcovabs() {
		return dcovabs;
	}
	public void setDcovabs(Double dcovabs) {
		this.dcovabs = dcovabs;
	}
	public Double getIratper() {
		return iratper;
	}
	public void setIratper(Double iratper) {
		this.iratper = iratper;
	}
	public Double getIratabs() {
		return iratabs;
	}
	public void setIratabs(Double iratabs) {
		this.iratabs = iratabs;
	}
	public Double getIcovper() {
		return icovper;
	}
	public void setIcovper(Double icovper) {
		this.icovper = icovper;
	}
	public Double getIcovabs() {
		return icovabs;
	}
	public void setIcovabs(Double icovabs) {
		this.icovabs = icovabs;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((agencia == null) ? 0 : agencia.hashCode());
		result = prime * result + ano;
		result = prime * result
				+ ((audiencia == null) ? 0 : audiencia.hashCode());
		result = prime * result + ((cliente == null) ? 0 : cliente.hashCode());
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + ((dcovabs == null) ? 0 : dcovabs.hashCode());
		result = prime * result + ((dcovper == null) ? 0 : dcovper.hashCode());
		result = prime * result
				+ ((desconto == null) ? 0 : desconto.hashCode());
		result = prime * result + ((dratabs == null) ? 0 : dratabs.hashCode());
		result = prime * result + ((dratper == null) ? 0 : dratper.hashCode());
		result = prime * result + ((duracao == null) ? 0 : duracao.hashCode());
		result = prime * result + ((formato == null) ? 0 : formato.hashCode());
		result = prime * result + ((hora == null) ? 0 : hora.hashCode());
		result = prime * result + ((icovabs == null) ? 0 : icovabs.hashCode());
		result = prime * result + ((icovper == null) ? 0 : icovper.hashCode());
		result = prime * result + ((iratabs == null) ? 0 : iratabs.hashCode());
		result = prime * result + ((iratper == null) ? 0 : iratper.hashCode());
		result = prime * result + mes;
		result = prime * result
				+ ((negociacao == null) ? 0 : negociacao.hashCode());
		result = prime
				* result
				+ ((programamidiamais == null) ? 0 : programamidiamais
						.hashCode());
		result = prime * result + ((projeto == null) ? 0 : projeto.hashCode());
		result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
		result = prime * result + ((titulo == null) ? 0 : titulo.hashCode());
		result = prime * result
				+ ((valorBruto == null) ? 0 : valorBruto.hashCode());
		result = prime * result
				+ ((valorLiquido == null) ? 0 : valorLiquido.hashCode());
		result = prime * result
				+ ((valorTabela == null) ? 0 : valorTabela.hashCode());
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
		PosVenda other = (PosVenda) obj;
		if (agencia == null) {
			if (other.agencia != null)
				return false;
		} else if (!agencia.equals(other.agencia))
			return false;
		if (ano != other.ano)
			return false;
		if (audiencia == null) {
			if (other.audiencia != null)
				return false;
		} else if (!audiencia.equals(other.audiencia))
			return false;
		if (cliente == null) {
			if (other.cliente != null)
				return false;
		} else if (!cliente.equals(other.cliente))
			return false;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		if (dcovabs == null) {
			if (other.dcovabs != null)
				return false;
		} else if (!dcovabs.equals(other.dcovabs))
			return false;
		if (dcovper == null) {
			if (other.dcovper != null)
				return false;
		} else if (!dcovper.equals(other.dcovper))
			return false;
		if (desconto == null) {
			if (other.desconto != null)
				return false;
		} else if (!desconto.equals(other.desconto))
			return false;
		if (dratabs == null) {
			if (other.dratabs != null)
				return false;
		} else if (!dratabs.equals(other.dratabs))
			return false;
		if (dratper == null) {
			if (other.dratper != null)
				return false;
		} else if (!dratper.equals(other.dratper))
			return false;
		if (duracao == null) {
			if (other.duracao != null)
				return false;
		} else if (!duracao.equals(other.duracao))
			return false;
		if (formato == null) {
			if (other.formato != null)
				return false;
		} else if (!formato.equals(other.formato))
			return false;
		if (hora == null) {
			if (other.hora != null)
				return false;
		} else if (!hora.equals(other.hora))
			return false;
		if (icovabs == null) {
			if (other.icovabs != null)
				return false;
		} else if (!icovabs.equals(other.icovabs))
			return false;
		if (icovper == null) {
			if (other.icovper != null)
				return false;
		} else if (!icovper.equals(other.icovper))
			return false;
		if (iratabs == null) {
			if (other.iratabs != null)
				return false;
		} else if (!iratabs.equals(other.iratabs))
			return false;
		if (iratper == null) {
			if (other.iratper != null)
				return false;
		} else if (!iratper.equals(other.iratper))
			return false;
		if (mes != other.mes)
			return false;
		if (negociacao == null) {
			if (other.negociacao != null)
				return false;
		} else if (!negociacao.equals(other.negociacao))
			return false;
		if (programamidiamais == null) {
			if (other.programamidiamais != null)
				return false;
		} else if (!programamidiamais.equals(other.programamidiamais))
			return false;
		if (projeto == null) {
			if (other.projeto != null)
				return false;
		} else if (!projeto.equals(other.projeto))
			return false;
		if (tipo == null) {
			if (other.tipo != null)
				return false;
		} else if (!tipo.equals(other.tipo))
			return false;
		if (titulo == null) {
			if (other.titulo != null)
				return false;
		} else if (!titulo.equals(other.titulo))
			return false;
		if (valorBruto == null) {
			if (other.valorBruto != null)
				return false;
		} else if (!valorBruto.equals(other.valorBruto))
			return false;
		if (valorLiquido == null) {
			if (other.valorLiquido != null)
				return false;
		} else if (!valorLiquido.equals(other.valorLiquido))
			return false;
		if (valorTabela == null) {
			if (other.valorTabela != null)
				return false;
		} else if (!valorTabela.equals(other.valorTabela))
			return false;
		return true;
	}

}
