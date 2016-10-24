package br.com.mauricio.news.model.rh;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PersistenceUnit;

/**
*
* @author MAURICIO CRUZ
*/
@PersistenceUnit(unitName="news")
@Entity(name="base")
public class Base implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@GeneratedValue
	@Id
	private Long id;
	@Column(length=6)
	private String chapa;
	private int ano;
	private int mes;
	private Double salarioBase;
	private Double baseINSS;
	private Double baseFGTS;
	private Double baseIRRF;
	@Column(length=12,nullable=false)
	private String cbo;	
	@Column(length=100,nullable=false)
	private String funcao;
	private int periodo;//2 salario 3-decimo terceiro

	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getChapa() {
		return chapa;
	}
	public void setChapa(String chapa) {
		this.chapa = chapa;
	}
	public int getAno() {
		return ano;
	}
	public void setAno(int ano) {
		this.ano = ano;
	}
	public int getMes() {
		return mes;
	}
	public void setMes(int mes) {
		this.mes = mes;
	}
	public Double getSalarioBase() {
		return salarioBase;
	}
	public void setSalarioBase(Double salarioBase) {
		this.salarioBase = salarioBase;
	}
	public Double getBaseINSS() {
		return baseINSS;
	}
	public void setBaseINSS(Double baseINSS) {
		this.baseINSS = baseINSS;
	}
	public Double getBaseFGTS() {
		return baseFGTS;
	}
	public void setBaseFGTS(Double baseFGTS) {
		this.baseFGTS = baseFGTS;
	}
	public Double getBaseIRRF() {
		return baseIRRF;
	}
	public void setBaseIRRF(Double baseIRRF) {
		this.baseIRRF = baseIRRF;
	}
	public String getCbo() {
		return cbo;
	}
	public void setCbo(String cbo) {
		this.cbo = cbo;
	}
	public String getFuncao() {
		return funcao;
	}
	public void setFuncao(String funcao) {
		this.funcao = funcao;
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
		result = prime * result + ano;
		result = prime * result
				+ ((baseFGTS == null) ? 0 : baseFGTS.hashCode());
		result = prime * result
				+ ((baseINSS == null) ? 0 : baseINSS.hashCode());
		result = prime * result
				+ ((baseIRRF == null) ? 0 : baseIRRF.hashCode());
		result = prime * result + ((cbo == null) ? 0 : cbo.hashCode());
		result = prime * result + ((chapa == null) ? 0 : chapa.hashCode());
		result = prime * result + ((funcao == null) ? 0 : funcao.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + mes;
		result = prime * result + periodo;
		result = prime * result
				+ ((salarioBase == null) ? 0 : salarioBase.hashCode());
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
		Base other = (Base) obj;
		if (ano != other.ano)
			return false;
		if (baseFGTS == null) {
			if (other.baseFGTS != null)
				return false;
		} else if (!baseFGTS.equals(other.baseFGTS))
			return false;
		if (baseINSS == null) {
			if (other.baseINSS != null)
				return false;
		} else if (!baseINSS.equals(other.baseINSS))
			return false;
		if (baseIRRF == null) {
			if (other.baseIRRF != null)
				return false;
		} else if (!baseIRRF.equals(other.baseIRRF))
			return false;
		if (cbo == null) {
			if (other.cbo != null)
				return false;
		} else if (!cbo.equals(other.cbo))
			return false;
		if (chapa == null) {
			if (other.chapa != null)
				return false;
		} else if (!chapa.equals(other.chapa))
			return false;
		if (funcao == null) {
			if (other.funcao != null)
				return false;
		} else if (!funcao.equals(other.funcao))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (mes != other.mes)
			return false;
		if (periodo != other.periodo)
			return false;
		if (salarioBase == null) {
			if (other.salarioBase != null)
				return false;
		} else if (!salarioBase.equals(other.salarioBase))
			return false;
		return true;
	}
}
