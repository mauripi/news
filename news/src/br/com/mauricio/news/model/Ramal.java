package br.com.mauricio.news.model;

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
* @author MAURICIO CRUZ and CAROLINE MARQUES
*/
@PersistenceUnit(unitName="news")
@Entity(name="ramal")
public class Ramal implements Serializable {

	private static final long serialVersionUID = 1L;

	@GeneratedValue
	@Id
	private Integer id;
	
	@Column(length=100)
	private String nome;
	
	@Column(length=100)
	private String email;
	
	private String cargo;
	
	@ManyToOne
	@JoinColumn(name = "depto_id")
	private DeptoRamal depto;
	
	private String telefone;
	
	private Integer ramalint;
	
	private Integer ramalext;

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public DeptoRamal getDepto() {
		return depto;
	}

	public void setDepto(DeptoRamal depto) {
		this.depto = depto;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public Integer getRamalint() {
		return ramalint;
	}

	public void setRamalint(Integer ramalint) {
		this.ramalint = ramalint;
	}

	public Integer getRamalext() {
		return ramalext;
	}

	public void setRamalext(Integer ramalext) {
		this.ramalext = ramalext;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cargo == null) ? 0 : cargo.hashCode());
		result = prime * result + ((depto == null) ? 0 : depto.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((ramalext == null) ? 0 : ramalext.hashCode());
		result = prime * result + ((ramalint == null) ? 0 : ramalint.hashCode());
		result = prime * result + ((telefone == null) ? 0 : telefone.hashCode());
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
		Ramal other = (Ramal) obj;
		if (cargo == null) {
			if (other.cargo != null)
				return false;
		} else if (!cargo.equals(other.cargo))
			return false;
		if (depto == null) {
			if (other.depto != null)
				return false;
		} else if (!depto.equals(other.depto))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
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
		if (ramalext == null) {
			if (other.ramalext != null)
				return false;
		} else if (!ramalext.equals(other.ramalext))
			return false;
		if (ramalint == null) {
			if (other.ramalint != null)
				return false;
		} else if (!ramalint.equals(other.ramalint))
			return false;
		if (telefone == null) {
			if (other.telefone != null)
				return false;
		} else if (!telefone.equals(other.telefone))
			return false;
		return true;
	}

	
}
