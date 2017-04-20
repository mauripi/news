package br.com.mauricio.news.model.rh;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PersistenceUnit;

import br.com.mauricio.news.model.rh.reqpess.ConhFreq;
import br.com.mauricio.news.model.rh.reqpess.ConhNiv;

/**
*
* @author MAURICIO CRUZ
*/
@PersistenceUnit(unitName="news")
@Entity(name="reqconhecimento")
public class RequisicaoConhecimento implements Serializable{

	private static final long serialVersionUID = 1L;
	@GeneratedValue
	@Id
	private Integer id;

	@Column(length=50)
	private String descricao;	
	@Enumerated(EnumType.ORDINAL)
	private ConhFreq conhfreq;
	@Enumerated(EnumType.ORDINAL)
	private ConhNiv conhniv;

	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public ConhFreq getConhfreq() {
		return conhfreq;
	}
	public void setConhfreq(ConhFreq conhfreq) {
		this.conhfreq = conhfreq;
	}
	public ConhNiv getConhniv() {
		return conhniv;
	}
	public void setConhniv(ConhNiv conhniv) {
		this.conhniv = conhniv;
	}
	
	
}
