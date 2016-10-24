package br.com.mauricio.news.model.marketing;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PersistenceUnit;
import javax.persistence.Temporal;

/**
*
* @author MAURICIO CRUZ
*/
@Entity(name="liveibope")
@PersistenceUnit(unitName="news")
public class LiveIbope  implements Serializable{

	private static final long serialVersionUID = 1L;
	@GeneratedValue
	@Id
	private Integer id;
	private Double dratper;
	private Double dratabs;
	private Double dcovperc;
	private Double dcovabs;
	private Double iratper;
	private Double iratabs;
	private Double icovperc;
	private Double icovabs;
	@Column(length=15)
	private String inicio;
	@Temporal(javax.persistence.TemporalType.DATE)
	private Date data;
	@OneToOne
	@JoinColumn(name="programaibope_id")
	private ProgramaIbope programaibope;

	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	public Double getDcovperc() {
		return dcovperc;
	}
	public void setDcovperc(Double dcovperc) {
		this.dcovperc = dcovperc;
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
	public Double getIcovperc() {
		return icovperc;
	}
	public void setIcovperc(Double icovperc) {
		this.icovperc = icovperc;
	}
	public Double getIcovabs() {
		return icovabs;
	}
	public void setIcovabs(Double icovabs) {
		this.icovabs = icovabs;
	}
	public String getInicio() {
		return inicio;
	}
	public void setInicio(String inicio) {
		this.inicio = inicio;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public ProgramaIbope getProgramaibope() {
		return programaibope;
	}
	public void setProgramaibope(ProgramaIbope programaibope) {
		this.programaibope = programaibope;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + ((dcovabs == null) ? 0 : dcovabs.hashCode());
		result = prime * result
				+ ((dcovperc == null) ? 0 : dcovperc.hashCode());
		result = prime * result + ((dratabs == null) ? 0 : dratabs.hashCode());
		result = prime * result + ((dratper == null) ? 0 : dratper.hashCode());
		result = prime * result + ((icovabs == null) ? 0 : icovabs.hashCode());
		result = prime * result
				+ ((icovperc == null) ? 0 : icovperc.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((inicio == null) ? 0 : inicio.hashCode());
		result = prime * result + ((iratabs == null) ? 0 : iratabs.hashCode());
		result = prime * result + ((iratper == null) ? 0 : iratper.hashCode());
		result = prime * result
				+ ((programaibope == null) ? 0 : programaibope.hashCode());
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
		LiveIbope other = (LiveIbope) obj;
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
		if (dcovperc == null) {
			if (other.dcovperc != null)
				return false;
		} else if (!dcovperc.equals(other.dcovperc))
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
		if (icovabs == null) {
			if (other.icovabs != null)
				return false;
		} else if (!icovabs.equals(other.icovabs))
			return false;
		if (icovperc == null) {
			if (other.icovperc != null)
				return false;
		} else if (!icovperc.equals(other.icovperc))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (inicio == null) {
			if (other.inicio != null)
				return false;
		} else if (!inicio.equals(other.inicio))
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
		if (programaibope == null) {
			if (other.programaibope != null)
				return false;
		} else if (!programaibope.equals(other.programaibope))
			return false;
		return true;
	}
}
