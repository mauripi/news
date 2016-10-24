package br.com.mauricio.news.model.ti;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PersistenceUnit;
import javax.persistence.Temporal;

/**
*
* @author MAURICIO CRUZ
*/
@Entity(name="solicitacao_resolvida")
@PersistenceUnit(unitName="news")
public class SolicitacaoResolvida implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@GeneratedValue
	@Id
	private Integer id;
	
	@Temporal(javax.persistence.TemporalType.DATE)
	private Date data;
	
	@Temporal(javax.persistence.TemporalType.TIME)
	private Date hora;
		
	@OneToOne
	@JoinColumn(name="atendente_id")
	private Atendente atendente;
		
	@ManyToOne
	@JoinColumn(name = "solicitacao_id")
	private Solicitacao solicitacao;

	
	
	
	
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

	public Date getHora() {
		return hora;
	}

	public void setHora(Date hora) {
		this.hora = hora;
	}

	public Atendente getAtendente() {
		return atendente;
	}

	public void setAtendente(Atendente atendente) {
		this.atendente = atendente;
	}

	public Solicitacao getSolicitacao() {
		return solicitacao;
	}

	public void setSolicitacao(Solicitacao solicitacao) {
		this.solicitacao = solicitacao;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((atendente == null) ? 0 : atendente.hashCode());
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + ((hora == null) ? 0 : hora.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((solicitacao == null) ? 0 : solicitacao.hashCode());
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
		SolicitacaoResolvida other = (SolicitacaoResolvida) obj;
		if (atendente == null) {
			if (other.atendente != null)
				return false;
		} else if (!atendente.equals(other.atendente))
			return false;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		if (hora == null) {
			if (other.hora != null)
				return false;
		} else if (!hora.equals(other.hora))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (solicitacao == null) {
			if (other.solicitacao != null)
				return false;
		} else if (!solicitacao.equals(other.solicitacao))
			return false;
		return true;
	}

}
