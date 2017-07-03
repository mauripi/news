package br.com.mauricio.news.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PersistenceUnit;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

/**
*
* @author MAURICIO CRUZ and CAROLINE MARQUES
*/
@PersistenceUnit(unitName="news")
@Entity(name="deptoramal")
public class DeptoRamal implements Serializable {

	private static final long serialVersionUID = 1L;

	@GeneratedValue
	@Id
	private Integer id;
	
	@Column(length=100,nullable=false)
	private String nome;
	
	@ManyToOne
	@JoinColumn(name = "local_id")
	private LocalRamal local;

	@OneToMany(mappedBy = "depto", cascade=CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)
    private List<Ramal> ramais;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public LocalRamal getLocal() {
		return local;
	}

	public void setLocal(LocalRamal local) {
		this.local = local;
	}

	public List<Ramal> getRamais() {
		return ramais;
	}

	public void setRamais(List<Ramal> ramais) {
		this.ramais = ramais;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((local == null) ? 0 : local.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((ramais == null) ? 0 : ramais.hashCode());
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
		DeptoRamal other = (DeptoRamal) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (local == null) {
			if (other.local != null)
				return false;
		} else if (!local.equals(other.local))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (ramais == null) {
			if (other.ramais != null)
				return false;
		} else if (!ramais.equals(other.ramais))
			return false;
		return true;
	}


}
