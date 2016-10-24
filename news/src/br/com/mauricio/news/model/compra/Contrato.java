package br.com.mauricio.news.model.compra;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PersistenceUnit;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Type;

import br.com.mauricio.news.model.Auditoria;

/**
*
* @author MAURICIO CRUZ
*/
@PersistenceUnit(unitName="news")
@Entity(name="contrato")
public class Contrato extends Auditoria implements Serializable{

	private static final long serialVersionUID = 1L;
	@GeneratedValue
	@Id
	private Integer id;
	@Column(length=200)
	private String objeto;
	@Column(length=1)
	private int especie=0; //compra=1 venda=2
	@ManyToOne(targetEntity=Fornecedor.class)
	@JoinColumn(name="fornecedor_id")
	private Fornecedor fornecedor;
	@ManyToOne(targetEntity=Cliente.class)
	@JoinColumn(name="cliente_id")		
	private Cliente cliente;
	@Column(length=80)
	private String advogado;
	@Column(length=600)
	private String observacao;
	@OneToOne
	@JoinColumn(name="tipo_id")	
	private TipoContrato tipo;
	private Date inicio;
	private Date fim;
	@Column(nullable = false,columnDefinition="double precision default '0.0'")	
	private Double valor=0.0;
	@Column(nullable = false,columnDefinition="bit default 1")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private Boolean enviarEmail=true;
	@Column(nullable = false,columnDefinition="bit default 1")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private Boolean ativo=true;
	private Integer diasParaInicioEmail;
	@OneToMany(mappedBy = "contrato", cascade=CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<DocContrato> documentos;

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
	public int getEspecie() {
		return especie;
	}
	public void setEspecie(int especie) {
		this.especie = especie;
	}
	public Fornecedor getFornecedor() {
		return fornecedor;
	}
	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	public String getAdvogado() {
		return advogado;
	}
	public void setAdvogado(String advogado) {
		this.advogado = advogado;
	}
	public String getObservacao() {
		return observacao;
	}
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	public TipoContrato getTipo() {
		return tipo;
	}
	public void setTipo(TipoContrato tipo) {
		this.tipo = tipo;
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
	public Double getValor() {
		return valor;
	}
	public void setValor(Double valor) {
		this.valor = valor;
	}
	public Boolean getEnviarEmail() {
		return enviarEmail;
	}
	public void setEnviarEmail(Boolean enviarEmail) {
		this.enviarEmail = enviarEmail;
	}
	public Boolean getAtivo() {
		return ativo;
	}
	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}
	public Integer getDiasParaInicioEmail() {
		return diasParaInicioEmail;
	}
	public void setDiasParaInicioEmail(Integer diasParaInicioEmail) {
		this.diasParaInicioEmail = diasParaInicioEmail;
	}
	public List<DocContrato> getDocumentos() {
		return documentos;
	}
	public void setDocumentos(List<DocContrato> documentos) {
		this.documentos = documentos;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((advogado == null) ? 0 : advogado.hashCode());
		result = prime * result + ((ativo == null) ? 0 : ativo.hashCode());
		result = prime * result + ((cliente == null) ? 0 : cliente.hashCode());
		result = prime
				* result
				+ ((diasParaInicioEmail == null) ? 0 : diasParaInicioEmail
						.hashCode());
		result = prime * result
				+ ((documentos == null) ? 0 : documentos.hashCode());
		result = prime * result
				+ ((enviarEmail == null) ? 0 : enviarEmail.hashCode());
		result = prime * result + especie;
		result = prime * result + ((fim == null) ? 0 : fim.hashCode());
		result = prime * result
				+ ((fornecedor == null) ? 0 : fornecedor.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((inicio == null) ? 0 : inicio.hashCode());
		result = prime * result + ((objeto == null) ? 0 : objeto.hashCode());
		result = prime * result
				+ ((observacao == null) ? 0 : observacao.hashCode());
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
		Contrato other = (Contrato) obj;
		if (advogado == null) {
			if (other.advogado != null)
				return false;
		} else if (!advogado.equals(other.advogado))
			return false;
		if (ativo == null) {
			if (other.ativo != null)
				return false;
		} else if (!ativo.equals(other.ativo))
			return false;
		if (cliente == null) {
			if (other.cliente != null)
				return false;
		} else if (!cliente.equals(other.cliente))
			return false;
		if (diasParaInicioEmail == null) {
			if (other.diasParaInicioEmail != null)
				return false;
		} else if (!diasParaInicioEmail.equals(other.diasParaInicioEmail))
			return false;
		if (documentos == null) {
			if (other.documentos != null)
				return false;
		} else if (!documentos.equals(other.documentos))
			return false;
		if (enviarEmail == null) {
			if (other.enviarEmail != null)
				return false;
		} else if (!enviarEmail.equals(other.enviarEmail))
			return false;
		if (especie != other.especie)
			return false;
		if (fim == null) {
			if (other.fim != null)
				return false;
		} else if (!fim.equals(other.fim))
			return false;
		if (fornecedor == null) {
			if (other.fornecedor != null)
				return false;
		} else if (!fornecedor.equals(other.fornecedor))
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
		if (objeto == null) {
			if (other.objeto != null)
				return false;
		} else if (!objeto.equals(other.objeto))
			return false;
		if (observacao == null) {
			if (other.observacao != null)
				return false;
		} else if (!observacao.equals(other.observacao))
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
