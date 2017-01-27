package br.com.mauricio.news.model.contabil;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PersistenceUnit;
import javax.persistence.Temporal;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import br.com.mauricio.news.model.CCusto;
import br.com.mauricio.news.model.Filial;
import br.com.mauricio.news.model.Login;
/**
*
* @author MAURICIO CRUZ
*/
@Entity(name="baixabem")
@PersistenceUnit(unitName="news")
public class BaixaBem implements Serializable{

	private static final long serialVersionUID = 1L;
	@GeneratedValue
	@Id
	private Integer id;	
	@OneToOne
	@JoinColumn(name="login_id")
	private Login solicitante;
	@OneToOne
	@JoinColumn(name="filial_id")
	private Filial filial;
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ccusto_id")
	private CCusto ccusto;		
	@Temporal(javax.persistence.TemporalType.DATE)
	private Date dataemissao;	
	@Enumerated(EnumType.ORDINAL)
	private TipoBaixa tipoBaixa;	
	@Column(length=10,nullable=false)
	private String patrimonio;	
	@Column(length=250,nullable=false)
	private String descricaoBem;		
	@Temporal(javax.persistence.TemporalType.DATE)
	private Date dataaquisicao;		
	@Temporal(javax.persistence.TemporalType.DATE)
	private Date datavenda;		
	private Double vlraquisicao;
	private Double vlrresidual;
	private Double vlrvenda;	
	@Column(length=1500,nullable=false)
	private String justificativa;	
	@OneToMany(mappedBy = "baixabem", targetEntity = ItemBaixaBem.class,cascade=CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<ItemBaixaBem> itens;
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
	public Filial getFilial() {
		return filial;
	}
	public void setFilial(Filial filial) {
		this.filial = filial;
	}
	public CCusto getCcusto() {
		return ccusto;
	}
	public void setCcusto(CCusto ccusto) {
		this.ccusto = ccusto;
	}
	public Date getDataemissao() {
		return dataemissao;
	}
	public void setDataemissao(Date dataemissao) {
		this.dataemissao = dataemissao;
	}
	public TipoBaixa getTipoBaixa() {
		return tipoBaixa;
	}
	public void setTipoBaixa(TipoBaixa tipoBaixa) {
		this.tipoBaixa = tipoBaixa;
	}
	public String getPatrimonio() {
		return patrimonio;
	}
	public void setPatrimonio(String patrimonio) {
		this.patrimonio = patrimonio;
	}
	public String getDescricaoBem() {
		return descricaoBem;
	}
	public void setDescricaoBem(String descricaoBem) {
		this.descricaoBem = descricaoBem;
	}
	public Date getDataaquisicao() {
		return dataaquisicao;
	}
	public void setDataaquisicao(Date dataaquisicao) {
		this.dataaquisicao = dataaquisicao;
	}
	public Date getDatavenda() {
		return datavenda;
	}
	public void setDatavenda(Date datavenda) {
		this.datavenda = datavenda;
	}
	public Double getVlraquisicao() {
		return vlraquisicao;
	}
	public void setVlraquisicao(Double vlraquisicao) {
		this.vlraquisicao = vlraquisicao;
	}
	public Double getVlrresidual() {
		return vlrresidual;
	}
	public void setVlrresidual(Double vlrresidual) {
		this.vlrresidual = vlrresidual;
	}
	public Double getVlrvenda() {
		return vlrvenda;
	}
	public void setVlrvenda(Double vlrvenda) {
		this.vlrvenda = vlrvenda;
	}
	public String getJustificativa() {
		return justificativa;
	}
	public void setJustificativa(String justificativa) {
		this.justificativa = justificativa;
	}
	public List<ItemBaixaBem> getItens() {
		return itens;
	}
	public void setItens(List<ItemBaixaBem> itens) {
		this.itens = itens;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ccusto == null) ? 0 : ccusto.hashCode());
		result = prime * result
				+ ((dataaquisicao == null) ? 0 : dataaquisicao.hashCode());
		result = prime * result
				+ ((dataemissao == null) ? 0 : dataemissao.hashCode());
		result = prime * result
				+ ((datavenda == null) ? 0 : datavenda.hashCode());
		result = prime * result
				+ ((descricaoBem == null) ? 0 : descricaoBem.hashCode());
		result = prime * result + ((filial == null) ? 0 : filial.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((itens == null) ? 0 : itens.hashCode());
		result = prime * result
				+ ((justificativa == null) ? 0 : justificativa.hashCode());
		result = prime * result
				+ ((patrimonio == null) ? 0 : patrimonio.hashCode());
		result = prime * result
				+ ((solicitante == null) ? 0 : solicitante.hashCode());
		result = prime * result
				+ ((tipoBaixa == null) ? 0 : tipoBaixa.hashCode());
		result = prime * result
				+ ((vlraquisicao == null) ? 0 : vlraquisicao.hashCode());
		result = prime * result
				+ ((vlrresidual == null) ? 0 : vlrresidual.hashCode());
		result = prime * result
				+ ((vlrvenda == null) ? 0 : vlrvenda.hashCode());
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
		BaixaBem other = (BaixaBem) obj;
		if (ccusto == null) {
			if (other.ccusto != null)
				return false;
		} else if (!ccusto.equals(other.ccusto))
			return false;
		if (dataaquisicao == null) {
			if (other.dataaquisicao != null)
				return false;
		} else if (!dataaquisicao.equals(other.dataaquisicao))
			return false;
		if (dataemissao == null) {
			if (other.dataemissao != null)
				return false;
		} else if (!dataemissao.equals(other.dataemissao))
			return false;
		if (datavenda == null) {
			if (other.datavenda != null)
				return false;
		} else if (!datavenda.equals(other.datavenda))
			return false;
		if (descricaoBem == null) {
			if (other.descricaoBem != null)
				return false;
		} else if (!descricaoBem.equals(other.descricaoBem))
			return false;
		if (filial == null) {
			if (other.filial != null)
				return false;
		} else if (!filial.equals(other.filial))
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
		if (justificativa == null) {
			if (other.justificativa != null)
				return false;
		} else if (!justificativa.equals(other.justificativa))
			return false;
		if (patrimonio == null) {
			if (other.patrimonio != null)
				return false;
		} else if (!patrimonio.equals(other.patrimonio))
			return false;
		if (solicitante == null) {
			if (other.solicitante != null)
				return false;
		} else if (!solicitante.equals(other.solicitante))
			return false;
		if (tipoBaixa != other.tipoBaixa)
			return false;
		if (vlraquisicao == null) {
			if (other.vlraquisicao != null)
				return false;
		} else if (!vlraquisicao.equals(other.vlraquisicao))
			return false;
		if (vlrresidual == null) {
			if (other.vlrresidual != null)
				return false;
		} else if (!vlrresidual.equals(other.vlrresidual))
			return false;
		if (vlrvenda == null) {
			if (other.vlrvenda != null)
				return false;
		} else if (!vlrvenda.equals(other.vlrvenda))
			return false;
		return true;
	}

}
