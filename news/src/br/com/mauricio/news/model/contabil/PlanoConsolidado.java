package br.com.mauricio.news.model.contabil;

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
@Entity(name="planoconsolidado")
@PersistenceUnit(unitName="news")
public class PlanoConsolidado implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@GeneratedValue
	@Id
	private Integer id;
	private Integer ctared;
	private Integer clacta;
	private String descta;
	@Column(length=1) 
	private String natcta;	
	private Integer nivcta;	
	private Integer gructa;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getCtared() {
		return ctared;
	}
	public void setCtared(Integer ctared) {
		this.ctared = ctared;
	}
	public Integer getClacta() {
		return clacta;
	}
	public void setClacta(Integer clacta) {
		this.clacta = clacta;
	}
	public String getDescta() {
		return descta;
	}
	public void setDescta(String descta) {
		this.descta = descta;
	}
	public String getNatcta() {
		return natcta;
	}
	public void setNatcta(String natcta) {
		this.natcta = natcta;
	}
	public Integer getNivcta() {
		return nivcta;
	}
	public void setNivcta(Integer nivcta) {
		this.nivcta = nivcta;
	}
	public Integer getGructa() {
		return gructa;
	}
	public void setGructa(Integer gructa) {
		this.gructa = gructa;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((clacta == null) ? 0 : clacta.hashCode());
		result = prime * result + ((ctared == null) ? 0 : ctared.hashCode());
		result = prime * result + ((descta == null) ? 0 : descta.hashCode());
		result = prime * result + ((gructa == null) ? 0 : gructa.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((natcta == null) ? 0 : natcta.hashCode());
		result = prime * result + ((nivcta == null) ? 0 : nivcta.hashCode());
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
		PlanoConsolidado other = (PlanoConsolidado) obj;
		if (clacta == null) {
			if (other.clacta != null)
				return false;
		} else if (!clacta.equals(other.clacta))
			return false;
		if (ctared == null) {
			if (other.ctared != null)
				return false;
		} else if (!ctared.equals(other.ctared))
			return false;
		if (descta == null) {
			if (other.descta != null)
				return false;
		} else if (!descta.equals(other.descta))
			return false;
		if (gructa == null) {
			if (other.gructa != null)
				return false;
		} else if (!gructa.equals(other.gructa))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (natcta == null) {
			if (other.natcta != null)
				return false;
		} else if (!natcta.equals(other.natcta))
			return false;
		if (nivcta == null) {
			if (other.nivcta != null)
				return false;
		} else if (!nivcta.equals(other.nivcta))
			return false;
		return true;
	}
}
