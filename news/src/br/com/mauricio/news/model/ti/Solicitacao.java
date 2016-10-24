package br.com.mauricio.news.model.ti;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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

import br.com.mauricio.news.model.Login;

/**
*
* @author MAURICIO CRUZ
*/
@Entity(name="solicitacao")
@PersistenceUnit(unitName="news")
public class Solicitacao implements Serializable{
	
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
	@JoinColumn(name="area_id")
	private AreaSolicitacao area;
	
	@OneToOne
	@JoinColumn(name="solicitante_id")
	private Login solicitante;
	
	@OneToOne
	@JoinColumn(name="favorecido_id")
	private Login favorecido;
	
	@Enumerated(EnumType.ORDINAL)
	CategoriaSolicitacao categoria;
	
	@OneToMany(mappedBy = "solicitacao", cascade=CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)
    private List<HistoricoSolicitacao> historicos;

	@Column(length=250,nullable=true)
	private String emailsadicionais;

	
	
	
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

	public AreaSolicitacao getArea() {
		return area;
	}

	public void setArea(AreaSolicitacao area) {
		this.area = area;
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

	public CategoriaSolicitacao getCategoria() {
		return categoria;
	}

	public void setCategoria(CategoriaSolicitacao categoria) {
		this.categoria = categoria;
	}

	public List<HistoricoSolicitacao> getHistoricos() {
		return historicos;
	}

	public void setHistoricos(List<HistoricoSolicitacao> historicos) {
		this.historicos = historicos;
	}

	public String getEmailsadicionais() {
		return emailsadicionais;
	}

	public void setEmailsadicionais(String emailsadicionais) {
		this.emailsadicionais = emailsadicionais;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((area == null) ? 0 : area.hashCode());
		result = prime * result
				+ ((categoria == null) ? 0 : categoria.hashCode());
		result = prime * result
				+ ((dataabertura == null) ? 0 : dataabertura.hashCode());
		result = prime * result
				+ ((datafechamento == null) ? 0 : datafechamento.hashCode());
		result = prime * result
				+ ((descricao == null) ? 0 : descricao.hashCode());
		result = prime
				* result
				+ ((emailsadicionais == null) ? 0 : emailsadicionais.hashCode());
		result = prime * result
				+ ((favorecido == null) ? 0 : favorecido.hashCode());
		result = prime * result
				+ ((historicos == null) ? 0 : historicos.hashCode());
		result = prime * result
				+ ((horaabertura == null) ? 0 : horaabertura.hashCode());
		result = prime * result
				+ ((horafechamento == null) ? 0 : horafechamento.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Solicitacao other = (Solicitacao) obj;
		if (area == null) {
			if (other.area != null)
				return false;
		} else if (!area.equals(other.area))
			return false;
		if (categoria != other.categoria)
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
		if (emailsadicionais == null) {
			if (other.emailsadicionais != null)
				return false;
		} else if (!emailsadicionais.equals(other.emailsadicionais))
			return false;
		if (favorecido == null) {
			if (other.favorecido != null)
				return false;
		} else if (!favorecido.equals(other.favorecido))
			return false;
		if (historicos == null) {
			if (other.historicos != null)
				return false;
		} else if (!historicos.equals(other.historicos))
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
		if (solicitante == null) {
			if (other.solicitante != null)
				return false;
		} else if (!solicitante.equals(other.solicitante))
			return false;
		return true;
	}
	
}
