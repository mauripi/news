package br.com.mauricio.news.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PersistenceUnit;

import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Type;

/**
*
* @author MAURICIO CRUZ
*/
@PersistenceUnit(unitName="news")
@Entity(name="login")
public class Login extends Auditoria implements Serializable{

	private static final long serialVersionUID = -6072090749475125730L;
	@GeneratedValue
	@Id
	private Integer id;
	@Column(length=100,nullable=false)
	private String nome;
	@Column(length=6,nullable=false,unique=true)
	private String chapa;
	@Column(length=11,nullable=false,unique=true)
	private String cpf;	
	@Column(length=35,nullable=false)
	private String senha;
	@Column(length=85)
	private String email;	
	@OneToOne(cascade = {CascadeType.ALL})
	private Filial filial;
	@OneToOne(cascade = {CascadeType.ALL})
	private CCusto custo;	
	@Column(columnDefinition="bit")
	private int trocarSenha;
	
	@ManyToMany(cascade=CascadeType.MERGE)
    @JoinTable(name = "acesso", joinColumns = @JoinColumn(name = "login_id"), inverseJoinColumns = @JoinColumn(name = "modulo_id"))
	@ForeignKey(name="FK_login_modulo")
	@LazyCollection(LazyCollectionOption.FALSE)	
    private List<Modulo> acessos;
	
	@OneToMany(cascade=CascadeType.ALL,fetch = FetchType.LAZY)
	@LazyCollection(LazyCollectionOption.FALSE)
    @JoinTable(name="permissaoprestacao", joinColumns={@JoinColumn(name="permissao_de")}, inverseJoinColumns={@JoinColumn(name="permisao_para")})
    private List<Login> usuariosPrestacao;
	
	@Column(nullable = false,columnDefinition="bit default 1")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private Boolean ativo=true;

	@Override
	public String toString() {
		return nome;
	};
	
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
	public String getChapa() {
		return chapa;
	}
	public void setChapa(String chapa) {
		this.chapa = chapa;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Filial getFilial() {
		return filial;
	}
	public void setFilial(Filial filial) {
		this.filial = filial;
	}
	public CCusto getCusto() {
		return custo;
	}
	public void setCusto(CCusto custo) {
		this.custo = custo;
	}
	public int getTrocarSenha() {
		return trocarSenha;
	}
	public void setTrocarSenha(int trocarSenha) {
		this.trocarSenha = trocarSenha;
	}
	public List<Modulo> getAcessos() {
		return acessos;
	}
	public void setAcessos(List<Modulo> acessos) {
		this.acessos = acessos;
	}
	public List<Login> getUsuariosPrestacao() {
		return usuariosPrestacao;
	}
	public void setUsuariosPrestacao(List<Login> usuariosPrestacao) {
		this.usuariosPrestacao = usuariosPrestacao;
	}
	public Boolean getAtivo() {
		return ativo;
	}
	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((acessos == null) ? 0 : acessos.hashCode());
		result = prime * result + ((ativo == null) ? 0 : ativo.hashCode());
		result = prime * result + ((chapa == null) ? 0 : chapa.hashCode());
		result = prime * result + ((cpf == null) ? 0 : cpf.hashCode());
		result = prime * result + ((custo == null) ? 0 : custo.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((filial == null) ? 0 : filial.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((senha == null) ? 0 : senha.hashCode());
		result = prime * result + trocarSenha;
		result = prime
				* result
				+ ((usuariosPrestacao == null) ? 0 : usuariosPrestacao
						.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		Login other = (Login) obj;
		if (id == other.id)
			return true;		
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		if (acessos == null) {
			if (other.acessos != null)
				return false;
		} else if (!acessos.equals(other.acessos))
			return false;
		if (ativo == null) {
			if (other.ativo != null)
				return false;
		} else if (!ativo.equals(other.ativo))
			return false;
		if (chapa == null) {
			if (other.chapa != null)
				return false;
		} else if (!chapa.equals(other.chapa))
			return false;
		if (cpf == null) {
			if (other.cpf != null)
				return false;
		} else if (!cpf.equals(other.cpf))
			return false;
		if (custo == null) {
			if (other.custo != null)
				return false;
		} else if (!custo.equals(other.custo))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (filial == null) {
			if (other.filial != null)
				return false;
		} else if (!filial.equals(other.filial))
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
		if (senha == null) {
			if (other.senha != null)
				return false;
		} else if (!senha.equals(other.senha))
			return false;
		if (trocarSenha != other.trocarSenha)
			return false;
		if (usuariosPrestacao == null) {
			if (other.usuariosPrestacao != null)
				return false;
		} else if (!usuariosPrestacao.equals(other.usuariosPrestacao))
			return false;
		return true;
	}

}
