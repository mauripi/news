package br.com.mauricio.news.model.manutencao;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PersistenceUnit;
import javax.persistence.Temporal;

import br.com.mauricio.news.model.Login;

/**
*
* @author MAURICIO CRUZ and CAROLINE MARQUES
*/
@Entity(name="pedidomanuntinter")
@PersistenceUnit(unitName="news")
public class PedidoManutencaoInteracao implements Serializable {

	private static final long serialVersionUID = 1L;
	@GeneratedValue
	@Id
	private Integer id;	
	@Temporal(javax.persistence.TemporalType.DATE)
	private Date data;	
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	private Date hora;	
	@Column(length=1000,nullable=false)
	private String descricao;
	@ManyToOne
	@JoinColumn(name = "pedidomanutencao_id")
	private PedidoManutencao pedidomanutencao;	
	@OneToOne
	@JoinColumn(name="user_interacao_id")
	private Login user_interacao;

	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public Date getHora() {
		return hora;
	}
	public void setHora(Date hora) {
		this.hora = hora;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public PedidoManutencao getPedidomanutencao() {
		return pedidomanutencao;
	}
	public void setPedidomanutencao(PedidoManutencao pedidomanutencao) {
		this.pedidomanutencao = pedidomanutencao;
	}
	public Login getUser_interacao() {
		return user_interacao;
	}
	public void setUser_interacao(Login user_interacao) {
		this.user_interacao = user_interacao;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result + ((hora == null) ? 0 : hora.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((pedidomanutencao == null) ? 0 : pedidomanutencao.hashCode());
		result = prime * result + ((user_interacao == null) ? 0 : user_interacao.hashCode());
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
		PedidoManutencaoInteracao other = (PedidoManutencaoInteracao) obj;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
			return false;
		if (hora == null) {
			if (other.hora != null)
				return false;
		} else if (!hora.equals(other.hora))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (pedidomanutencao == null) {
			if (other.pedidomanutencao != null)
				return false;
		} else if (!pedidomanutencao.equals(other.pedidomanutencao))
			return false;
		if (user_interacao == null) {
			if (other.user_interacao != null)
				return false;
		} else if (!user_interacao.equals(other.user_interacao))
			return false;
		return true;
	}

	
}
