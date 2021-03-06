package br.com.mauricio.news.model;

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
* @author MAURICIO CRUZ and CAROLINE MARQUES
*/
@PersistenceUnit(unitName="news")
@Entity(name="empresaramal")
public class EmpresaRamal implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@GeneratedValue
	@Id
	private Integer id;
	
	@Column(length=100,nullable=false)
	private String nome;

	@OneToMany(mappedBy = "empresa", cascade=CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)
    private List<LocalRamal> locais;

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

	public List<LocalRamal> getLocais() {
		return locais;
	}

	public void setLocais(List<LocalRamal> locais) {
		this.locais = locais;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((locais == null) ? 0 : locais.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
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
		EmpresaRamal other = (EmpresaRamal) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (locais == null) {
			if (other.locais != null)
				return false;
		} else if (!locais.equals(other.locais))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}
	
}
