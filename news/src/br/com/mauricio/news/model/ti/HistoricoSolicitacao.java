package br.com.mauricio.news.model.ti;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PersistenceUnit;
import javax.persistence.Temporal;

import br.com.mauricio.news.model.Login;

/**
*
* @author MAURICIO CRUZ
*/
@Entity(name="solicitacao_hist")
@PersistenceUnit(unitName="news")
public class HistoricoSolicitacao implements Serializable,Comparable<HistoricoSolicitacao>{
	
	private static final long serialVersionUID = 1L;
	@GeneratedValue
	@Id
	private Integer id;
	
	@Temporal(javax.persistence.TemporalType.DATE)
	private Date data;
	
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	private Date hora;
	
	@Column(length=1000,nullable=false)
	private String descricao;
	
	@OneToOne
	@JoinColumn(name="atendente_id",nullable = true)
	private Atendente atendente;
	
	@Enumerated(EnumType.ORDINAL)
	private StatusSolicitacao status;
	
	@ManyToOne
	@JoinColumn(name = "solicitacao_id")
	private Solicitacao solicitacao;
	
	@OneToOne
	@JoinColumn(name="user_interacao_id")
	private Login user_interacao;

	@Column(length=100)
	private String arquivo;

	
    public int compareTo(HistoricoSolicitacao historico) {
        if(this.getId() > historico.getId())
            return -1;        
        else if(this.getId() < historico.getId())
            return 1;       
        return 0;
    }	

	
	
	
	
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

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Atendente getAtendente() {
		return atendente;
	}

	public void setAtendente(Atendente atendente) {
		this.atendente = atendente;
	}

	public StatusSolicitacao getStatus() {
		return status;
	}

	public void setStatus(StatusSolicitacao status) {
		this.status = status;
	}

	public Solicitacao getSolicitacao() {
		return solicitacao;
	}

	public void setSolicitacao(Solicitacao solicitacao) {
		this.solicitacao = solicitacao;
	}

	public Login getUser_interacao() {
		return user_interacao;
	}

	public void setUser_interacao(Login user_interacao) {
		this.user_interacao = user_interacao;
	}

	public String getArquivo() {
		return arquivo;
	}

	public void setArquivo(String arquivo) {
		this.arquivo = arquivo;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((arquivo == null) ? 0 : arquivo.hashCode());
		result = prime * result
				+ ((atendente == null) ? 0 : atendente.hashCode());
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result
				+ ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result + ((hora == null) ? 0 : hora.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((solicitacao == null) ? 0 : solicitacao.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result
				+ ((user_interacao == null) ? 0 : user_interacao.hashCode());
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
		HistoricoSolicitacao other = (HistoricoSolicitacao) obj;
		if (arquivo == null) {
			if (other.arquivo != null)
				return false;
		} else if (!arquivo.equals(other.arquivo))
			return false;
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
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
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
		if (status != other.status)
			return false;
		if (user_interacao == null) {
			if (other.user_interacao != null)
				return false;
		} else if (!user_interacao.equals(other.user_interacao))
			return false;
		return true;
	}	
	
}
