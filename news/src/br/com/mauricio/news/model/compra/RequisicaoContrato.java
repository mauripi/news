package br.com.mauricio.news.model.compra;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PersistenceUnit;

import br.com.mauricio.news.model.Login;
/**
*
* @author MAURICIO CRUZ
*/
@PersistenceUnit(unitName="news")
@Entity(name="requisicaocontrato")
public class RequisicaoContrato implements Serializable{
	private static final long serialVersionUID = 1L;
	@GeneratedValue
	@Id
	private Integer id;
	@Column(length=30)
	private String solicitante;
	@Column(length=30)
	private String departamento;
	private Date datarequisicao;
	@Column(length=30)
	private String tipo;
	@Column(length=150)
	private String razaosocial;
	@Column(length=20)
	private String cnpj;
	@Column(length=30)
	private String telefone;
	@Column(length=100)
	private String contato;
	@Column(length=50)
	private String email;
	@Column(length=1500)
	private String observacao;
	@Column(length=30)
	private String vigencia;
	private Double valor;
	private Integer internoexterno;//1-interno 2-externo
	@OneToOne
	@JoinColumn(name="usuario_id")
	private Login usuario;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getSolicitante() {
		return solicitante;
	}
	public void setSolicitante(String solicitante) {
		this.solicitante = solicitante;
	}
	public String getDepartamento() {
		return departamento;
	}
	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}
	public Date getDatarequisicao() {
		return datarequisicao;
	}
	public void setDatarequisicao(Date datarequisicao) {
		this.datarequisicao = datarequisicao;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getRazaosocial() {
		return razaosocial;
	}
	public void setRazaosocial(String razaosocial) {
		this.razaosocial = razaosocial;
	}
	public String getCnpj() {
		return cnpj;
	}
	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	public String getContato() {
		return contato;
	}
	public void setContato(String contato) {
		this.contato = contato;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getObservacao() {
		return observacao;
	}
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	public String getVigencia() {
		return vigencia;
	}
	public void setVigencia(String vigencia) {
		this.vigencia = vigencia;
	}
	public Double getValor() {
		return valor;
	}
	public void setValor(Double valor) {
		this.valor = valor;
	}
	public Integer getInternoexterno() {
		return internoexterno;
	}
	public void setInternoexterno(Integer internoexterno) {
		this.internoexterno = internoexterno;
	}
	public Login getUsuario() {
		return usuario;
	}
	public void setUsuario(Login usuario) {
		this.usuario = usuario;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cnpj == null) ? 0 : cnpj.hashCode());
		result = prime * result + ((contato == null) ? 0 : contato.hashCode());
		result = prime * result
				+ ((datarequisicao == null) ? 0 : datarequisicao.hashCode());
		result = prime * result
				+ ((departamento == null) ? 0 : departamento.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((internoexterno == null) ? 0 : internoexterno.hashCode());
		result = prime * result
				+ ((observacao == null) ? 0 : observacao.hashCode());
		result = prime * result
				+ ((razaosocial == null) ? 0 : razaosocial.hashCode());
		result = prime * result
				+ ((solicitante == null) ? 0 : solicitante.hashCode());
		result = prime * result
				+ ((telefone == null) ? 0 : telefone.hashCode());
		result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
		result = prime * result + ((usuario == null) ? 0 : usuario.hashCode());
		result = prime * result + ((valor == null) ? 0 : valor.hashCode());
		result = prime * result
				+ ((vigencia == null) ? 0 : vigencia.hashCode());
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
		RequisicaoContrato other = (RequisicaoContrato) obj;
		if (cnpj == null) {
			if (other.cnpj != null)
				return false;
		} else if (!cnpj.equals(other.cnpj))
			return false;
		if (contato == null) {
			if (other.contato != null)
				return false;
		} else if (!contato.equals(other.contato))
			return false;
		if (datarequisicao == null) {
			if (other.datarequisicao != null)
				return false;
		} else if (!datarequisicao.equals(other.datarequisicao))
			return false;
		if (departamento == null) {
			if (other.departamento != null)
				return false;
		} else if (!departamento.equals(other.departamento))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (internoexterno == null) {
			if (other.internoexterno != null)
				return false;
		} else if (!internoexterno.equals(other.internoexterno))
			return false;
		if (observacao == null) {
			if (other.observacao != null)
				return false;
		} else if (!observacao.equals(other.observacao))
			return false;
		if (razaosocial == null) {
			if (other.razaosocial != null)
				return false;
		} else if (!razaosocial.equals(other.razaosocial))
			return false;
		if (solicitante == null) {
			if (other.solicitante != null)
				return false;
		} else if (!solicitante.equals(other.solicitante))
			return false;
		if (telefone == null) {
			if (other.telefone != null)
				return false;
		} else if (!telefone.equals(other.telefone))
			return false;
		if (tipo == null) {
			if (other.tipo != null)
				return false;
		} else if (!tipo.equals(other.tipo))
			return false;
		if (usuario == null) {
			if (other.usuario != null)
				return false;
		} else if (!usuario.equals(other.usuario))
			return false;
		if (valor == null) {
			if (other.valor != null)
				return false;
		} else if (!valor.equals(other.valor))
			return false;
		if (vigencia == null) {
			if (other.vigencia != null)
				return false;
		} else if (!vigencia.equals(other.vigencia))
			return false;
		return true;
	}
	
}
