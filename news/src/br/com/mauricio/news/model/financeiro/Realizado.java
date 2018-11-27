package br.com.mauricio.news.model.financeiro;

import java.math.BigDecimal;
import java.util.Date;


public class Realizado {
	private String numtit;
	private Date datmov;
	private String nomclifor;
	private String obstit;
	private BigDecimal vlrliq;
	private Integer ctared;

	public String getNumtit() {
		return numtit;
	}
	public void setNumtit(String numtit) {
		this.numtit = numtit;
	}
	public Date getDatmov() {
		return datmov;
	}
	public void setDatmov(Date datmov) {
		this.datmov = datmov;
	}
	public String getNomclifor() {
		return nomclifor;
	}
	public void setNomclifor(String nomclifor) {
		this.nomclifor = nomclifor;
	}
	public String getObstit() {
		return obstit;
	}
	public void setObstit(String obstit) {
		this.obstit = obstit;
	}
	public BigDecimal getVlrliq() {
		return vlrliq;
	}
	public void setVlrliq(BigDecimal vlrliq) {
		this.vlrliq = vlrliq;
	}
	public Integer getCtared() {
		return ctared;
	}
	public void setCtared(Integer ctared) {
		this.ctared = ctared;
	}

	
}
