package br.com.mauricio.news.model.engenharia;

import java.io.Serializable;
import java.util.List;

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
@Entity(name="posto")
@PersistenceUnit(unitName="news")
public class Posto implements Serializable{

	private static final long serialVersionUID = -6072090749475125730L;

	@Id
	@GeneratedValue
	private Integer id;	
	@Column(length=60)
	private String nome;
	@Column(length=300)
	private String endereco;
	@OneToMany(mappedBy = "posto", targetEntity = FotoPosto.class)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<FotoPosto> fotos;

	
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
	public String getEndereco() {
		return endereco;
	}
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	public List<FotoPosto> getFotos() {
		return fotos;
	}
	public void setFotos(List<FotoPosto> fotos) {
		this.fotos = fotos;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((endereco == null) ? 0 : endereco.hashCode());
		result = prime * result + ((fotos == null) ? 0 : fotos.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Posto other = (Posto) obj;
		if (endereco == null) {
			if (other.endereco != null)
				return false;
		} else if (!endereco.equals(other.endereco))
			return false;
		if (fotos == null) {
			if (other.fotos != null)
				return false;
		} else if (!fotos.equals(other.fotos))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}
	
}
