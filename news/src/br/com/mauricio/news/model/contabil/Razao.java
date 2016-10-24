package br.com.mauricio.news.model.contabil;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PersistenceUnit;

/**
*
* @author MAURICIO CRUZ
*/
@Entity(name="razao")
@PersistenceUnit(unitName="news")
public class Razao implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@GeneratedValue
	@Id
	private Integer id;
	private Date data;
	private Double valor=new Double("0.0");
	private Integer clacta;
	private Integer ctadeb;
	private Integer ctacre;
	private String descta;
	@Column(length=400)
	private String historico;
	private String tipo;

	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public Double getValor() {
		return valor;
	}
	public void setValor(Double valor) {
		this.valor = valor;
	}
	public Integer getClacta() {
		return clacta;
	}
	public void setClacta(Integer clacta) {
		this.clacta = clacta;
	}
	public Integer getCtadeb() {
		return ctadeb;
	}
	public void setCtadeb(Integer ctadeb) {
		this.ctadeb = ctadeb;
	}
	public Integer getCtacre() {
		return ctacre;
	}
	public void setCtacre(Integer ctacre) {
		this.ctacre = ctacre;
	}
	public String getDescta() {
		return descta;
	}
	public void setDescta(String descta) {
		this.descta = descta;
	}
	public String getHistorico() {
		return historico;
	}
	public void setHistorico(String historico) {
		this.historico = historico;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((clacta == null) ? 0 : clacta.hashCode());
		result = prime * result + ((ctacre == null) ? 0 : ctacre.hashCode());
		result = prime * result + ((ctadeb == null) ? 0 : ctadeb.hashCode());
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + ((descta == null) ? 0 : descta.hashCode());
		result = prime * result
				+ ((historico == null) ? 0 : historico.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Razao other = (Razao) obj;
		if (clacta == null) {
			if (other.clacta != null)
				return false;
		} else if (!clacta.equals(other.clacta))
			return false;
		if (ctacre == null) {
			if (other.ctacre != null)
				return false;
		} else if (!ctacre.equals(other.ctacre))
			return false;
		if (ctadeb == null) {
			if (other.ctadeb != null)
				return false;
		} else if (!ctadeb.equals(other.ctadeb))
			return false;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		if (descta == null) {
			if (other.descta != null)
				return false;
		} else if (!descta.equals(other.descta))
			return false;
		if (historico == null) {
			if (other.historico != null)
				return false;
		} else if (!historico.equals(other.historico))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
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
