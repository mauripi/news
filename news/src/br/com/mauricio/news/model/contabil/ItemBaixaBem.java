package br.com.mauricio.news.model.contabil;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PersistenceUnit;
import javax.persistence.Temporal;
/**
*
* @author MAURICIO CRUZ
*/
@Entity(name="itembaixabem")
@PersistenceUnit(unitName="news")
public class ItemBaixaBem  implements Serializable{
	private static final long serialVersionUID = 1L;
	@GeneratedValue
	@Id
	private Integer id;
    @Column(length=12)  
    private String patrimonio;
    @Column(length=300)  
    private String descricao;
    @Column(length=120)  
    private String nomeFornecedor;
    @Column(length=20)  
    private String notafiscal;    
    @Column(length=30)  
    private String numpla;
    private Double quantidade;
    
	@Temporal(javax.persistence.TemporalType.DATE)
	private Date dataaquisicao;		
	@Temporal(javax.persistence.TemporalType.DATE)
	private Date datavenda;		
	
	private Double vlraquisicao;
	private Double vlrresidual;
	private Double vlrvenda;
	@ManyToOne
	@JoinColumn(name="baixabem_id")
	private BaixaBem baixabem;

	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getPatrimonio() {
		return patrimonio;
	}
	public void setPatrimonio(String patrimonio) {
		this.patrimonio = patrimonio;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getNomeFornecedor() {
		return nomeFornecedor;
	}
	public void setNomeFornecedor(String nomeFornecedor) {
		this.nomeFornecedor = nomeFornecedor;
	}
	public String getNotafiscal() {
		return notafiscal;
	}
	public void setNotafiscal(String notafiscal) {
		this.notafiscal = notafiscal;
	}
	public String getNumpla() {
		return numpla;
	}
	public void setNumpla(String numpla) {
		this.numpla = numpla;
	}
	public Double getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(Double quantidade) {
		this.quantidade = quantidade;
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
	public BaixaBem getBaixabem() {
		return baixabem;
	}
	public void setBaixabem(BaixaBem baixabem) {
		this.baixabem = baixabem;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((baixabem == null) ? 0 : baixabem.hashCode());
		result = prime * result
				+ ((dataaquisicao == null) ? 0 : dataaquisicao.hashCode());
		result = prime * result
				+ ((datavenda == null) ? 0 : datavenda.hashCode());
		result = prime * result
				+ ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((nomeFornecedor == null) ? 0 : nomeFornecedor.hashCode());
		result = prime * result
				+ ((notafiscal == null) ? 0 : notafiscal.hashCode());
		result = prime * result + ((numpla == null) ? 0 : numpla.hashCode());
		result = prime * result
				+ ((patrimonio == null) ? 0 : patrimonio.hashCode());
		result = prime * result
				+ ((quantidade == null) ? 0 : quantidade.hashCode());
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
		ItemBaixaBem other = (ItemBaixaBem) obj;
		if (baixabem == null) {
			if (other.baixabem != null)
				return false;
		} else if (!baixabem.equals(other.baixabem))
			return false;
		if (dataaquisicao == null) {
			if (other.dataaquisicao != null)
				return false;
		} else if (!dataaquisicao.equals(other.dataaquisicao))
			return false;
		if (datavenda == null) {
			if (other.datavenda != null)
				return false;
		} else if (!datavenda.equals(other.datavenda))
			return false;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nomeFornecedor == null) {
			if (other.nomeFornecedor != null)
				return false;
		} else if (!nomeFornecedor.equals(other.nomeFornecedor))
			return false;
		if (notafiscal == null) {
			if (other.notafiscal != null)
				return false;
		} else if (!notafiscal.equals(other.notafiscal))
			return false;
		if (numpla == null) {
			if (other.numpla != null)
				return false;
		} else if (!numpla.equals(other.numpla))
			return false;
		if (patrimonio == null) {
			if (other.patrimonio != null)
				return false;
		} else if (!patrimonio.equals(other.patrimonio))
			return false;
		if (quantidade == null) {
			if (other.quantidade != null)
				return false;
		} else if (!quantidade.equals(other.quantidade))
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
