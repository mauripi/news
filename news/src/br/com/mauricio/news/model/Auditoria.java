package br.com.mauricio.news.model;

/**
*
* @author MAURICIO CRUZ
*/
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
@MappedSuperclass
public class Auditoria {
	@OneToOne
	@JoinColumn(name="usuarioCriacao_id")
	private Login usuarioCriacao;
	private Date dataCriacao;
	@OneToOne
	@JoinColumn(name="usuarioAlteracao_id")	
	private Login usuarioAlteracao;
	private Date dataAlteracao;
	@Column(columnDefinition="bit default 0")
	private Integer removido;
	public Login getUsuarioCriacao() {
		return usuarioCriacao;
	}
	public void setUsuarioCriacao(Login usuarioCriacao) {
		this.usuarioCriacao = usuarioCriacao;
	}
	public Date getDataCriacao() {
		return dataCriacao;
	}
	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}
	public Login getUsuarioAlteracao() {
		return usuarioAlteracao;
	}
	public void setUsuarioAlteracao(Login usuarioAlteracao) {
		this.usuarioAlteracao = usuarioAlteracao;
	}
	public Date getDataAlteracao() {
		return dataAlteracao;
	}
	public void setDataAlteracao(Date dataAlteracao) {
		this.dataAlteracao = dataAlteracao;
	}
	public Integer getRemovido() {
		return removido;
	}
	public void setRemovido(Integer removido) {
		this.removido = removido;
	}

}
