
package br.com.mauricio.news.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PersistenceUnit;

import org.hibernate.annotations.Type;
/**
*
* @author MAURICIO CRUZ
*/
@PersistenceUnit(unitName="news")
@Entity(name="contrato")
public class Contrato implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@GeneratedValue
	@Id
	private Integer id;
	@Column(length=200)
	private String objeto;
	@Column(length=1)
	private String origem="C"; //C V
	@OneToOne
	@JoinColumn(name="tipocontrato_id")	
	private TipoContrato tipocontrato;
	private Date inicio;
	private Date fim;
	private Integer tipoValor=0;
	@Column(nullable = false,columnDefinition="double precision default '0.0'")	
	private Double valorTotal=0.0;
	@Column(nullable = false,columnDefinition="double precision default '0.0'")	
	private Double valorMensal=0.0;	
	@Column(nullable = false,columnDefinition="bit default 1")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private Boolean ativo=true;
	@OneToOne
	@JoinColumn(name="responsavel_id")
	private Login responsavel;
	@OneToOne
	@JoinColumn(name="mclifor_id")
	private MCLIFOR mclifor;
	private Integer diasAviso;
	private Integer repetirAviso;
	private String assuntoEmail;
	private String mensagemEmail;
	private String emailsAviso;

	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getObjeto() {
		return objeto;
	}
	public void setObjeto(String objeto) {
		this.objeto = objeto;
	}
	public String getOrigem() {
		return origem;
	}
	public void setOrigem(String origem) {
		this.origem = origem;
	}
	public TipoContrato getTipocontrato() {
		return tipocontrato;
	}
	public void setTipocontrato(TipoContrato tipocontrato) {
		this.tipocontrato = tipocontrato;
	}
	public Date getInicio() {
		return inicio;
	}
	public void setInicio(Date inicio) {
		this.inicio = inicio;
	}
	public Date getFim() {
		return fim;
	}
	public void setFim(Date fim) {
		this.fim = fim;
	}
	public Integer getTipoValor() {
		return tipoValor;
	}
	public void setTipoValor(Integer tipoValor) {
		this.tipoValor = tipoValor;
	}
	public Double getValorTotal() {
		return valorTotal;
	}
	public void setValorTotal(Double valorTotal) {
		this.valorTotal = valorTotal;
	}
	public Double getValorMensal() {
		return valorMensal;
	}
	public void setValorMensal(Double valorMensal) {
		this.valorMensal = valorMensal;
	}
	public Boolean getAtivo() {
		return ativo;
	}
	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}
	public Login getResponsavel() {
		return responsavel;
	}
	public void setResponsavel(Login responsavel) {
		this.responsavel = responsavel;
	}
	public MCLIFOR getMclifor() {
		return mclifor;
	}
	public void setMclifor(MCLIFOR mclifor) {
		this.mclifor = mclifor;
	}
	public Integer getDiasAviso() {
		return diasAviso;
	}
	public void setDiasAviso(Integer diasAviso) {
		this.diasAviso = diasAviso;
	}
	public Integer getRepetirAviso() {
		return repetirAviso;
	}
	public void setRepetirAviso(Integer repetirAviso) {
		this.repetirAviso = repetirAviso;
	}
	public String getAssuntoEmail() {
		return assuntoEmail;
	}
	public void setAssuntoEmail(String assuntoEmail) {
		this.assuntoEmail = assuntoEmail;
	}
	public String getMensagemEmail() {
		return mensagemEmail;
	}
	public void setMensagemEmail(String mensagemEmail) {
		this.mensagemEmail = mensagemEmail;
	}
	public String getEmailsAviso() {
		return emailsAviso;
	}
	public void setEmailsAviso(String emailsAviso) {
		this.emailsAviso = emailsAviso;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((assuntoEmail == null) ? 0 : assuntoEmail.hashCode());
		result = prime * result + ((ativo == null) ? 0 : ativo.hashCode());
		result = prime * result
				+ ((diasAviso == null) ? 0 : diasAviso.hashCode());
		result = prime * result
				+ ((emailsAviso == null) ? 0 : emailsAviso.hashCode());
		result = prime * result + ((fim == null) ? 0 : fim.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((inicio == null) ? 0 : inicio.hashCode());
		result = prime * result + ((mclifor == null) ? 0 : mclifor.hashCode());
		result = prime * result
				+ ((mensagemEmail == null) ? 0 : mensagemEmail.hashCode());
		result = prime * result + ((objeto == null) ? 0 : objeto.hashCode());
		result = prime * result + ((origem == null) ? 0 : origem.hashCode());
		result = prime * result
				+ ((repetirAviso == null) ? 0 : repetirAviso.hashCode());
		result = prime * result
				+ ((responsavel == null) ? 0 : responsavel.hashCode());
		result = prime * result
				+ ((tipoValor == null) ? 0 : tipoValor.hashCode());
		result = prime * result
				+ ((tipocontrato == null) ? 0 : tipocontrato.hashCode());
		result = prime * result
				+ ((valorMensal == null) ? 0 : valorMensal.hashCode());
		result = prime * result
				+ ((valorTotal == null) ? 0 : valorTotal.hashCode());
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
		Contrato other = (Contrato) obj;
		if (assuntoEmail == null) {
			if (other.assuntoEmail != null)
				return false;
		} else if (!assuntoEmail.equals(other.assuntoEmail))
			return false;
		if (ativo == null) {
			if (other.ativo != null)
				return false;
		} else if (!ativo.equals(other.ativo))
			return false;
		if (diasAviso == null) {
			if (other.diasAviso != null)
				return false;
		} else if (!diasAviso.equals(other.diasAviso))
			return false;
		if (emailsAviso == null) {
			if (other.emailsAviso != null)
				return false;
		} else if (!emailsAviso.equals(other.emailsAviso))
			return false;
		if (fim == null) {
			if (other.fim != null)
				return false;
		} else if (!fim.equals(other.fim))
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
		if (mclifor == null) {
			if (other.mclifor != null)
				return false;
		} else if (!mclifor.equals(other.mclifor))
			return false;
		if (mensagemEmail == null) {
			if (other.mensagemEmail != null)
				return false;
		} else if (!mensagemEmail.equals(other.mensagemEmail))
			return false;
		if (objeto == null) {
			if (other.objeto != null)
				return false;
		} else if (!objeto.equals(other.objeto))
			return false;
		if (origem == null) {
			if (other.origem != null)
				return false;
		} else if (!origem.equals(other.origem))
			return false;
		if (repetirAviso == null) {
			if (other.repetirAviso != null)
				return false;
		} else if (!repetirAviso.equals(other.repetirAviso))
			return false;
		if (responsavel == null) {
			if (other.responsavel != null)
				return false;
		} else if (!responsavel.equals(other.responsavel))
			return false;
		if (tipoValor == null) {
			if (other.tipoValor != null)
				return false;
		} else if (!tipoValor.equals(other.tipoValor))
			return false;
		if (tipocontrato == null) {
			if (other.tipocontrato != null)
				return false;
		} else if (!tipocontrato.equals(other.tipocontrato))
			return false;
		if (valorMensal == null) {
			if (other.valorMensal != null)
				return false;
		} else if (!valorMensal.equals(other.valorMensal))
			return false;
		if (valorTotal == null) {
			if (other.valorTotal != null)
				return false;
		} else if (!valorTotal.equals(other.valorTotal))
			return false;
		return true;
	}
}
