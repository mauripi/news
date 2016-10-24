package br.com.mauricio.news.model.engenharia;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
*
* @author MAURICIO CRUZ
*/
@Entity(name="fotoposto")
public class FotoPosto implements Serializable{

	private static final long serialVersionUID = -3694155906429358342L;
	@Id
    @GeneratedValue
    private Integer id;
	@Column(length=100) 
	private String nome;
	@ManyToOne(targetEntity=Posto.class)
	@JoinColumn(name="posto_id")
	private Posto posto;

	
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
	public Posto getPosto() {
		return posto;
	}
	public void setPosto(Posto posto) {
		this.posto = posto;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((posto == null) ? 0 : posto.hashCode());
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
		FotoPosto other = (FotoPosto) obj;
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
		if (posto == null) {
			if (other.posto != null)
				return false;
		} else if (!posto.equals(other.posto))
			return false;
		return true;
	}

}
