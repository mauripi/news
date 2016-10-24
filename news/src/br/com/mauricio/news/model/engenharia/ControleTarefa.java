package br.com.mauricio.news.model.engenharia;

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

import org.hibernate.annotations.Type;

import br.com.mauricio.news.model.Login;

/**
*
* @author MAURICIO CRUZ
*/

@Entity(name="controletarefa")
@PersistenceUnit(unitName="news")
public class ControleTarefa implements Serializable{

	private static final long serialVersionUID = 1L;
	@GeneratedValue
	@Id
	private Integer id;	
	@Temporal(javax.persistence.TemporalType.DATE)
	private Date datainicio;
	@Temporal(javax.persistence.TemporalType.DATE)
	private Date datafim;	
	private Double valor;
	private Integer moeda=1;
	@Column(length=100)
	private String atividade;
	@Column(length=100)
	private String responsavel;
	@Column(length=2000)
	private String observacao;
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private Boolean aprovado=false;
	@OneToOne
	@JoinColumn(name="usuarioaprovacao_id")
	private Login usuarioaprovacao;
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	private Date dataaprovacao;
	@Column(length=10)
	private String patrimonio;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Date getDatainicio() {
		return datainicio;
	}
	public void setDatainicio(Date datainicio) {
		this.datainicio = datainicio;
	}
	public Date getDatafim() {
		return datafim;
	}
	public void setDatafim(Date datafim) {
		this.datafim = datafim;
	}
	public Double getValor() {
		return valor;
	}
	public void setValor(Double valor) {
		this.valor = valor;
	}
	public Integer getMoeda() {
		return moeda;
	}
	public void setMoeda(Integer moeda) {
		this.moeda = moeda;
	}
	public String getAtividade() {
		return atividade;
	}
	public void setAtividade(String atividade) {
		this.atividade = atividade;
	}
	public String getResponsavel() {
		return responsavel;
	}
	public void setResponsavel(String responsavel) {
		this.responsavel = responsavel;
	}
	public String getObservacao() {
		return observacao;
	}
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	public Boolean getAprovado() {
		return aprovado;
	}
	public void setAprovado(Boolean aprovado) {
		this.aprovado = aprovado;
	}
	public Login getUsuarioaprovacao() {
		return usuarioaprovacao;
	}
	public void setUsuarioaprovacao(Login usuarioaprovacao) {
		this.usuarioaprovacao = usuarioaprovacao;
	}
	public Date getDataaprovacao() {
		return dataaprovacao;
	}
	public void setDataaprovacao(Date dataaprovacao) {
		this.dataaprovacao = dataaprovacao;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getPatrimonio() {
		return patrimonio;
	}
	public void setPatrimonio(String patrimonio) {
		this.patrimonio = patrimonio;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((aprovado == null) ? 0 : aprovado.hashCode());
		result = prime * result
				+ ((atividade == null) ? 0 : atividade.hashCode());
		result = prime * result
				+ ((dataaprovacao == null) ? 0 : dataaprovacao.hashCode());
		result = prime * result + ((datafim == null) ? 0 : datafim.hashCode());
		result = prime * result
				+ ((datainicio == null) ? 0 : datainicio.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((moeda == null) ? 0 : moeda.hashCode());
		result = prime * result
				+ ((observacao == null) ? 0 : observacao.hashCode());
		result = prime * result
				+ ((patrimonio == null) ? 0 : patrimonio.hashCode());
		result = prime * result
				+ ((responsavel == null) ? 0 : responsavel.hashCode());
		result = prime
				* result
				+ ((usuarioaprovacao == null) ? 0 : usuarioaprovacao.hashCode());
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
		ControleTarefa other = (ControleTarefa) obj;
		if (aprovado == null) {
			if (other.aprovado != null)
				return false;
		} else if (!aprovado.equals(other.aprovado))
			return false;
		if (atividade == null) {
			if (other.atividade != null)
				return false;
		} else if (!atividade.equals(other.atividade))
			return false;
		if (dataaprovacao == null) {
			if (other.dataaprovacao != null)
				return false;
		} else if (!dataaprovacao.equals(other.dataaprovacao))
			return false;
		if (datafim == null) {
			if (other.datafim != null)
				return false;
		} else if (!datafim.equals(other.datafim))
			return false;
		if (datainicio == null) {
			if (other.datainicio != null)
				return false;
		} else if (!datainicio.equals(other.datainicio))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (moeda == null) {
			if (other.moeda != null)
				return false;
		} else if (!moeda.equals(other.moeda))
			return false;
		if (observacao == null) {
			if (other.observacao != null)
				return false;
		} else if (!observacao.equals(other.observacao))
			return false;
		if (patrimonio == null) {
			if (other.patrimonio != null)
				return false;
		} else if (!patrimonio.equals(other.patrimonio))
			return false;
		if (responsavel == null) {
			if (other.responsavel != null)
				return false;
		} else if (!responsavel.equals(other.responsavel))
			return false;
		if (usuarioaprovacao == null) {
			if (other.usuarioaprovacao != null)
				return false;
		} else if (!usuarioaprovacao.equals(other.usuarioaprovacao))
			return false;
		if (valor == null) {
			if (other.valor != null)
				return false;
		} else if (!valor.equals(other.valor))
			return false;
		return true;
	}

}
