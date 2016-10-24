package br.com.mauricio.news.model.contabil;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PersistenceUnit;
/**
*
* @author MAURICIO CRUZ
*/
@Entity(name="itemmovimento")
@PersistenceUnit(unitName="news")
public class ItemMovimento  implements Serializable{
	private static final long serialVersionUID = 1L;
	@GeneratedValue
	@Id
	private Integer id;
    @Column(length=30)  
    private String patrimonio;
    @Column(length=300)  
    private String descricao;
    @Column(length=120)  
    private String nomeFornecedor;
    @Column(length=20)  
    private String notafiscal;    
	@ManyToOne
	@JoinColumn(name="movimentobem_id")
	private MovimentoBem movimentobem;
    @Column(length=30)  
    private String numpla;

    
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
	public MovimentoBem getMovimentobem() {
		return movimentobem;
	}
	public void setMovimentobem(MovimentoBem movimentobem) {
		this.movimentobem = movimentobem;
	}
	public String getNumpla() {
		return numpla;
	}
	public void setNumpla(String numpla) {
		this.numpla = numpla;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((movimentobem == null) ? 0 : movimentobem.hashCode());
		result = prime * result
				+ ((nomeFornecedor == null) ? 0 : nomeFornecedor.hashCode());
		result = prime * result
				+ ((notafiscal == null) ? 0 : notafiscal.hashCode());
		result = prime * result + ((numpla == null) ? 0 : numpla.hashCode());
		result = prime * result
				+ ((patrimonio == null) ? 0 : patrimonio.hashCode());
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
		ItemMovimento other = (ItemMovimento) obj;
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
		if (movimentobem == null) {
			if (other.movimentobem != null)
				return false;
		} else if (!movimentobem.equals(other.movimentobem))
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
		return true;
	}	
	
}
