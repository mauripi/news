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
@PersistenceUnit(unitName="news")
@Entity(name="docbaixabem")
public class DocBaixaBem implements Serializable{

	private static final long serialVersionUID = 1L;
	@GeneratedValue
	@Id
	private Integer id;
	@Column(length=50)
	private String descricao;
	@Column(length=30)
	private String arquivo;
	@ManyToOne
	@JoinColumn(name = "baixabem_id")
	private BaixaBem baixabem;

	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getArquivo() {
		return arquivo;
	}
	public void setArquivo(String arquivo) {
		this.arquivo = arquivo;
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
		result = prime * result + ((arquivo == null) ? 0 : arquivo.hashCode());
		result = prime * result
				+ ((baixabem == null) ? 0 : baixabem.hashCode());
		result = prime * result
				+ ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		DocBaixaBem other = (DocBaixaBem) obj;
		if (arquivo == null) {
			if (other.arquivo != null)
				return false;
		} else if (!arquivo.equals(other.arquivo))
			return false;
		if (baixabem == null) {
			if (other.baixabem != null)
				return false;
		} else if (!baixabem.equals(other.baixabem))
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
		return true;
	}
}
