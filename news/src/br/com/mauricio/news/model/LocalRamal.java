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
@Entity(name="localramal")
public class LocalRamal implements Serializable {

	private static final long serialVersionUID = 1L;

	@GeneratedValue
	@Id
	private Integer id;
	
	@Column(length=100,nullable=false)
	private String nome;
	
	@ManyToOne
	@JoinColumn(name = "empresa_id")
	private EmpresaRamal empresa;

	@OneToMany(mappedBy = "local", cascade=CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)
    private List<DeptoRamal> deptos;

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

	public EmpresaRamal getEmpresa() {
		return empresa;
	}

	public void setEmpresa(EmpresaRamal empresa) {
		this.empresa = empresa;
	}

	public List<DeptoRamal> getDeptos() {
		return deptos;
	}

	public void setDeptos(List<DeptoRamal> deptos) {
		this.deptos = deptos;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((deptos == null) ? 0 : deptos.hashCode());
		result = prime * result + ((empresa == null) ? 0 : empresa.hashCode());
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
		LocalRamal other = (LocalRamal) obj;
		if (deptos == null) {
			if (other.deptos != null)
				return false;
		} else if (!deptos.equals(other.deptos))
			return false;
		if (empresa == null) {
			if (other.empresa != null)
				return false;
		} else if (!empresa.equals(other.empresa))
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
