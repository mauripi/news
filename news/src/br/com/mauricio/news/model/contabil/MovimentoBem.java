package br.com.mauricio.news.model.contabil;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PersistenceUnit;
import javax.persistence.Temporal;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import br.com.mauricio.news.model.Login;
/**
*
* @author MAURICIO CRUZ
*/
@Entity(name="movimentobem")
@PersistenceUnit(unitName="news")
public class MovimentoBem implements Serializable{

	private static final long serialVersionUID = 1L;
	@GeneratedValue
	@Id
	private Integer id;	
	@OneToOne
	@JoinColumn(name="login_id")
	private Login solicitante;
	
	@Column(length=500,nullable=false)
	private String localOrigem;
	
	@Column(length=500,nullable=false)
	private String localDestino;	
	
	@Column(length=100)
	private String nomeProprietarioDestino;
	
	@Column(length=20)
	private String cnpjProprietarioDestino;

	@Column(length=100)
	private String nomeTransportadora;
	
	@Column(length=20)
	private String cnpjTransportadora;

	@Column(length=100)
	private String nomeResponsavelRecepcao;
	
	@Column(length=20)
	private String cpfResponsavelRecepcao;		
	
	@Column(length=20)
	private String rgResponsavelRecepcao;
	
	@Temporal(javax.persistence.TemporalType.DATE)
	private Date dataemissao;

	@Temporal(javax.persistence.TemporalType.DATE)
	private Date datasaida;	

	@Column(columnDefinition="bit")
	private int espacoLocado;
	
	@Column(columnDefinition="bit")
	private int comFrete;
	
	@Column(length=1500,nullable=false)
	private String motivo;
	
	@OneToMany(mappedBy = "movimentobem", targetEntity = ItemMovimento.class,cascade=CascadeType.MERGE)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<ItemMovimento> itens;
	
	@OneToMany(mappedBy = "movimentobem", cascade=CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<DocMovimentoBem> documentos;

	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Login getSolicitante() {
		return solicitante;
	}

	public void setSolicitante(Login solicitante) {
		this.solicitante = solicitante;
	}

	public String getLocalOrigem() {
		return localOrigem;
	}

	public void setLocalOrigem(String localOrigem) {
		this.localOrigem = localOrigem;
	}

	public String getLocalDestino() {
		return localDestino;
	}

	public void setLocalDestino(String localDestino) {
		this.localDestino = localDestino;
	}

	public String getNomeProprietarioDestino() {
		return nomeProprietarioDestino;
	}

	public void setNomeProprietarioDestino(String nomeProprietarioDestino) {
		this.nomeProprietarioDestino = nomeProprietarioDestino;
	}

	public String getCnpjProprietarioDestino() {
		return cnpjProprietarioDestino;
	}

	public void setCnpjProprietarioDestino(String cnpjProprietarioDestino) {
		this.cnpjProprietarioDestino = cnpjProprietarioDestino;
	}

	public String getNomeTransportadora() {
		return nomeTransportadora;
	}

	public void setNomeTransportadora(String nomeTransportadora) {
		this.nomeTransportadora = nomeTransportadora;
	}

	public String getCnpjTransportadora() {
		return cnpjTransportadora;
	}

	public void setCnpjTransportadora(String cnpjTransportadora) {
		this.cnpjTransportadora = cnpjTransportadora;
	}

	public String getNomeResponsavelRecepcao() {
		return nomeResponsavelRecepcao;
	}

	public void setNomeResponsavelRecepcao(String nomeResponsavelRecepcao) {
		this.nomeResponsavelRecepcao = nomeResponsavelRecepcao;
	}

	public String getCpfResponsavelRecepcao() {
		return cpfResponsavelRecepcao;
	}

	public void setCpfResponsavelRecepcao(String cpfResponsavelRecepcao) {
		this.cpfResponsavelRecepcao = cpfResponsavelRecepcao;
	}

	public String getRgResponsavelRecepcao() {
		return rgResponsavelRecepcao;
	}

	public void setRgResponsavelRecepcao(String rgResponsavelRecepcao) {
		this.rgResponsavelRecepcao = rgResponsavelRecepcao;
	}

	public Date getDataemissao() {
		return dataemissao;
	}

	public void setDataemissao(Date dataemissao) {
		this.dataemissao = dataemissao;
	}

	public Date getDatasaida() {
		return datasaida;
	}

	public void setDatasaida(Date datasaida) {
		this.datasaida = datasaida;
	}

	public int getEspacoLocado() {
		return espacoLocado;
	}

	public void setEspacoLocado(int espacoLocado) {
		this.espacoLocado = espacoLocado;
	}

	public int getComFrete() {
		return comFrete;
	}

	public void setComFrete(int comFrete) {
		this.comFrete = comFrete;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public List<ItemMovimento> getItens() {
		return itens;
	}

	public void setItens(List<ItemMovimento> itens) {
		this.itens = itens;
	}

	public List<DocMovimentoBem> getDocumentos() {
		return documentos;
	}

	public void setDocumentos(List<DocMovimentoBem> documentos) {
		this.documentos = documentos;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((cnpjProprietarioDestino == null) ? 0
						: cnpjProprietarioDestino.hashCode());
		result = prime
				* result
				+ ((cnpjTransportadora == null) ? 0 : cnpjTransportadora
						.hashCode());
		result = prime * result + comFrete;
		result = prime
				* result
				+ ((cpfResponsavelRecepcao == null) ? 0
						: cpfResponsavelRecepcao.hashCode());
		result = prime * result
				+ ((dataemissao == null) ? 0 : dataemissao.hashCode());
		result = prime * result
				+ ((datasaida == null) ? 0 : datasaida.hashCode());
		result = prime * result
				+ ((documentos == null) ? 0 : documentos.hashCode());
		result = prime * result + espacoLocado;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((itens == null) ? 0 : itens.hashCode());
		result = prime * result
				+ ((localDestino == null) ? 0 : localDestino.hashCode());
		result = prime * result
				+ ((localOrigem == null) ? 0 : localOrigem.hashCode());
		result = prime * result + ((motivo == null) ? 0 : motivo.hashCode());
		result = prime
				* result
				+ ((nomeProprietarioDestino == null) ? 0
						: nomeProprietarioDestino.hashCode());
		result = prime
				* result
				+ ((nomeResponsavelRecepcao == null) ? 0
						: nomeResponsavelRecepcao.hashCode());
		result = prime
				* result
				+ ((nomeTransportadora == null) ? 0 : nomeTransportadora
						.hashCode());
		result = prime
				* result
				+ ((rgResponsavelRecepcao == null) ? 0 : rgResponsavelRecepcao
						.hashCode());
		result = prime * result
				+ ((solicitante == null) ? 0 : solicitante.hashCode());
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
		MovimentoBem other = (MovimentoBem) obj;
		if (cnpjProprietarioDestino == null) {
			if (other.cnpjProprietarioDestino != null)
				return false;
		} else if (!cnpjProprietarioDestino
				.equals(other.cnpjProprietarioDestino))
			return false;
		if (cnpjTransportadora == null) {
			if (other.cnpjTransportadora != null)
				return false;
		} else if (!cnpjTransportadora.equals(other.cnpjTransportadora))
			return false;
		if (comFrete != other.comFrete)
			return false;
		if (cpfResponsavelRecepcao == null) {
			if (other.cpfResponsavelRecepcao != null)
				return false;
		} else if (!cpfResponsavelRecepcao.equals(other.cpfResponsavelRecepcao))
			return false;
		if (dataemissao == null) {
			if (other.dataemissao != null)
				return false;
		} else if (!dataemissao.equals(other.dataemissao))
			return false;
		if (datasaida == null) {
			if (other.datasaida != null)
				return false;
		} else if (!datasaida.equals(other.datasaida))
			return false;
		if (documentos == null) {
			if (other.documentos != null)
				return false;
		} else if (!documentos.equals(other.documentos))
			return false;
		if (espacoLocado != other.espacoLocado)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (itens == null) {
			if (other.itens != null)
				return false;
		} else if (!itens.equals(other.itens))
			return false;
		if (localDestino == null) {
			if (other.localDestino != null)
				return false;
		} else if (!localDestino.equals(other.localDestino))
			return false;
		if (localOrigem == null) {
			if (other.localOrigem != null)
				return false;
		} else if (!localOrigem.equals(other.localOrigem))
			return false;
		if (motivo == null) {
			if (other.motivo != null)
				return false;
		} else if (!motivo.equals(other.motivo))
			return false;
		if (nomeProprietarioDestino == null) {
			if (other.nomeProprietarioDestino != null)
				return false;
		} else if (!nomeProprietarioDestino
				.equals(other.nomeProprietarioDestino))
			return false;
		if (nomeResponsavelRecepcao == null) {
			if (other.nomeResponsavelRecepcao != null)
				return false;
		} else if (!nomeResponsavelRecepcao
				.equals(other.nomeResponsavelRecepcao))
			return false;
		if (nomeTransportadora == null) {
			if (other.nomeTransportadora != null)
				return false;
		} else if (!nomeTransportadora.equals(other.nomeTransportadora))
			return false;
		if (rgResponsavelRecepcao == null) {
			if (other.rgResponsavelRecepcao != null)
				return false;
		} else if (!rgResponsavelRecepcao.equals(other.rgResponsavelRecepcao))
			return false;
		if (solicitante == null) {
			if (other.solicitante != null)
				return false;
		} else if (!solicitante.equals(other.solicitante))
			return false;
		return true;
	}	
	

}
