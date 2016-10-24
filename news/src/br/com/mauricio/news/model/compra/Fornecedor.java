package br.com.mauricio.news.model.compra;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PersistenceUnit;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import br.com.mauricio.news.model.Auditoria;
/**
*
* @author MAURICIO CRUZ
*/
@PersistenceUnit(unitName="news")
@Entity(name="fornecedor")
public class Fornecedor extends Auditoria implements Serializable{

	private static final long serialVersionUID = 1L;
	@GeneratedValue
	@Id
	private Integer id;
	@Column(length=100)
	private String nome;
	@Column(length=20)
	private String cnpjcpf;
	@Column(length=400)
	private String endereco;
	@OneToMany(mappedBy = "fornecedor", targetEntity = ContatoFor.class,cascade=CascadeType.ALL)
	@BatchSize(size = 5)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<ContatoFor> contatos;
	
	@OneToMany(mappedBy = "fornecedor", targetEntity = Contrato.class,cascade=CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Contrato> contratos;
	
	private Integer codfor;

	@Override
	public String toString() {
		return nome;
	}

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

	public String getCnpjcpf() {
		return cnpjcpf;
	}

	public void setCnpjcpf(String cnpjcpf) {
		this.cnpjcpf = cnpjcpf;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public List<ContatoFor> getContatos() {
		return contatos;
	}

	public void setContatos(List<ContatoFor> contatos) {
		this.contatos = contatos;
	}

	public List<Contrato> getContratos() {
		return contratos;
	}

	public void setContratos(List<Contrato> contratos) {
		this.contratos = contratos;
	}

	public Integer getCodfor() {
		return codfor;
	}

	public void setCodfor(Integer codfor) {
		this.codfor = codfor;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cnpjcpf == null) ? 0 : cnpjcpf.hashCode());
		result = prime * result + ((codfor == null) ? 0 : codfor.hashCode());
		result = prime * result
				+ ((contatos == null) ? 0 : contatos.hashCode());
		result = prime * result
				+ ((contratos == null) ? 0 : contratos.hashCode());
		result = prime * result
				+ ((endereco == null) ? 0 : endereco.hashCode());
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
		Fornecedor other = (Fornecedor) obj;
		if (cnpjcpf == null) {
			if (other.cnpjcpf != null)
				return false;
		} else if (!cnpjcpf.equals(other.cnpjcpf))
			return false;
		if (codfor == null) {
			if (other.codfor != null)
				return false;
		} else if (!codfor.equals(other.codfor))
			return false;
		if (contatos == null) {
			if (other.contatos != null)
				return false;
		} else if (!contatos.equals(other.contatos))
			return false;
		if (contratos == null) {
			if (other.contratos != null)
				return false;
		} else if (!contratos.equals(other.contratos))
			return false;
		if (endereco == null) {
			if (other.endereco != null)
				return false;
		} else if (!endereco.equals(other.endereco))
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
