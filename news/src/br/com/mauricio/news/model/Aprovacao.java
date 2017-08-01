package br.com.mauricio.news.model;
/**
*
* @author MAURICIO CRUZ and CAROLINE MARQUES
*/

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PersistenceUnit;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@PersistenceUnit(unitName="news")
@Entity(name="aprovacao")
public class Aprovacao implements Serializable {

	private static final long serialVersionUID = 1L;
	@GeneratedValue
	@Id
	private Integer id;
	@Column(columnDefinition="DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataaprov;
	@Temporal(TemporalType.DATE)
	private Date dataenvio;
	@OneToOne
	private UserProject responsavel;
	@Enumerated(EnumType.ORDINAL)
	private StatusAprovacao status;
	private String justificativa;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Date getDataaprov() {
		return dataaprov;
	}
	public void setDataaprov(Date dataaprov) {
		this.dataaprov = dataaprov;
	}
	public Date getDataenvio() {
		return dataenvio;
	}
	public void setDataenvio(Date dataenvio) {
		this.dataenvio = dataenvio;
	}
	public UserProject getResponsavel() {
		return responsavel;
	}
	public void setResponsavel(UserProject responsavel) {
		this.responsavel = responsavel;
	}
	public StatusAprovacao getStatus() {
		return status;
	}
	public void setStatus(StatusAprovacao status) {
		this.status = status;
	}
	public String getJustificativa() {
		return justificativa;
	}
	public void setJustificativa(String justificativa) {
		this.justificativa = justificativa;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dataaprov == null) ? 0 : dataaprov.hashCode());
		result = prime * result + ((dataenvio == null) ? 0 : dataenvio.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((justificativa == null) ? 0 : justificativa.hashCode());
		result = prime * result + ((responsavel == null) ? 0 : responsavel.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
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
		Aprovacao other = (Aprovacao) obj;
		if (dataaprov == null) {
			if (other.dataaprov != null)
				return false;
		} else if (!dataaprov.equals(other.dataaprov))
			return false;
		if (dataenvio == null) {
			if (other.dataenvio != null)
				return false;
		} else if (!dataenvio.equals(other.dataenvio))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (justificativa == null) {
			if (other.justificativa != null)
				return false;
		} else if (!justificativa.equals(other.justificativa))
			return false;
		if (responsavel == null) {
			if (other.responsavel != null)
				return false;
		} else if (!responsavel.equals(other.responsavel))
			return false;
		if (status != other.status)
			return false;
		return true;
	}

	

	
}
