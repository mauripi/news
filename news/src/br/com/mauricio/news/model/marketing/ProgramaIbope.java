package br.com.mauricio.news.model.marketing;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PersistenceUnit;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

/**
*
* @author MAURICIO CRUZ
*/
@Entity(name="programaibope")
@PersistenceUnit(unitName="news")
public class ProgramaIbope  implements Serializable{

	private static final long serialVersionUID = 1L;
	@GeneratedValue
	@Id
	private Integer id;
	private Integer codigoibope;	
	@Column(length=150,nullable=false)
	private String nome;
	@Column(length=20)
	private String wmask;

	@OneToMany(mappedBy = "programaibope", cascade=CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<LiveIbope> lives;

	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCodigoibope() {
		return codigoibope;
	}

	public void setCodigoibope(Integer codigoibope) {
		this.codigoibope = codigoibope;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getWmask() {
		return wmask;
	}

	public void setWmask(String wmask) {
		this.wmask = wmask;
	}

	public List<LiveIbope> getLives() {
		return lives;
	}

	public void setLives(List<LiveIbope> lives) {
		this.lives = lives;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((codigoibope == null) ? 0 : codigoibope.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((lives == null) ? 0 : lives.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((wmask == null) ? 0 : wmask.hashCode());
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
		ProgramaIbope other = (ProgramaIbope) obj;
		if (codigoibope == null) {
			if (other.codigoibope != null)
				return false;
		} else if (!codigoibope.equals(other.codigoibope))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (lives == null) {
			if (other.lives != null)
				return false;
		} else if (!lives.equals(other.lives))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (wmask == null) {
			if (other.wmask != null)
				return false;
		} else if (!wmask.equals(other.wmask))
			return false;
		return true;
	}
}
