package br.com.mauricio.news.model.manutencao;

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
import javax.persistence.TemporalType;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Type;

import br.com.mauricio.news.model.Login;

/**
*
* @author MAURICIO CRUZ and CAROLINE MARQUES
*/
@PersistenceUnit(unitName="news")
@Entity(name="pedidomanutencao")
public class PedidoManutencao implements Serializable{

	private static final long serialVersionUID = 1L;
	@GeneratedValue
	@Id
	private Integer id;
	@Temporal(TemporalType.DATE)
	private Date dataabertura;	
	@Temporal(TemporalType.TIME) 
	private Date horaabertura;
	@Temporal(TemporalType.DATE)
	private Date datafechamento;	
	@Temporal(TemporalType.TIME) 
	private Date horafechamento;		
	@Column(length=1000,nullable=false)
	private String descricao;
	@OneToOne
	@JoinColumn(name="solicitante_id")
	private Login solicitante;	
	@OneToOne
	@JoinColumn(name="favorecido_id")
	private Login favorecido;	
	@OneToMany(mappedBy = "pedidomanutencao", cascade=CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)
    private List<PedidoManutencaoInteracao> interacoes;
	@Column(nullable = false,columnDefinition="bit default 1")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private Boolean concluida=false;

	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Date getDataabertura() {
		return dataabertura;
	}
	public void setDataabertura(Date dataabertura) {
		this.dataabertura = dataabertura;
	}
	public Date getHoraabertura() {
		return horaabertura;
	}
	public void setHoraabertura(Date horaabertura) {
		this.horaabertura = horaabertura;
	}
	public Date getDatafechamento() {
		return datafechamento;
	}
	public void setDatafechamento(Date datafechamento) {
		this.datafechamento = datafechamento;
	}
	public Date getHorafechamento() {
		return horafechamento;
	}
	public void setHorafechamento(Date horafechamento) {
		this.horafechamento = horafechamento;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public Login getSolicitante() {
		return solicitante;
	}
	public void setSolicitante(Login solicitante) {
		this.solicitante = solicitante;
	}
	public Login getFavorecido() {
		return favorecido;
	}
	public void setFavorecido(Login favorecido) {
		this.favorecido = favorecido;
	}
	public List<PedidoManutencaoInteracao> getInteracoes() {
		return interacoes;
	}
	public void setInteracoes(List<PedidoManutencaoInteracao> interacoes) {
		this.interacoes = interacoes;
	}
	public Boolean getConcluida() {
		return concluida;
	}
	public void setConcluida(Boolean concluida) {
		this.concluida = concluida;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((concluida == null) ? 0 : concluida.hashCode());
		result = prime * result + ((dataabertura == null) ? 0 : dataabertura.hashCode());
		result = prime * result + ((datafechamento == null) ? 0 : datafechamento.hashCode());
		result = prime * result + ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result + ((favorecido == null) ? 0 : favorecido.hashCode());
		result = prime * result + ((horaabertura == null) ? 0 : horaabertura.hashCode());
		result = prime * result + ((horafechamento == null) ? 0 : horafechamento.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((interacoes == null) ? 0 : interacoes.hashCode());
		result = prime * result + ((solicitante == null) ? 0 : solicitante.hashCode());
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
		PedidoManutencao other = (PedidoManutencao) obj;
		if (concluida == null) {
			if (other.concluida != null)
				return false;
		} else if (!concluida.equals(other.concluida))
			return false;
		if (dataabertura == null) {
			if (other.dataabertura != null)
				return false;
		} else if (!dataabertura.equals(other.dataabertura))
			return false;
		if (datafechamento == null) {
			if (other.datafechamento != null)
				return false;
		} else if (!datafechamento.equals(other.datafechamento))
			return false;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
			return false;
		if (favorecido == null) {
			if (other.favorecido != null)
				return false;
		} else if (!favorecido.equals(other.favorecido))
			return false;
		if (horaabertura == null) {
			if (other.horaabertura != null)
				return false;
		} else if (!horaabertura.equals(other.horaabertura))
			return false;
		if (horafechamento == null) {
			if (other.horafechamento != null)
				return false;
		} else if (!horafechamento.equals(other.horafechamento))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (interacoes == null) {
			if (other.interacoes != null)
				return false;
		} else if (!interacoes.equals(other.interacoes))
			return false;
		if (solicitante == null) {
			if (other.solicitante != null)
				return false;
		} else if (!solicitante.equals(other.solicitante))
			return false;
		return true;
	}
}
