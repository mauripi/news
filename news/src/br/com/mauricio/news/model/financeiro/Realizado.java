package br.com.mauricio.news.model.financeiro;

import java.math.BigDecimal;
import java.util.Date;


public class Realizado {
	private String numtit;
	private Date vctpro;
	private Date datmov;
	private String nomclifor;
	private String obstit;
	private BigDecimal vlrliq;
	private Integer clsflx;

	public String getNumtit() {
		return numtit;
	}
	public void setNumtit(String numtit) {
		this.numtit = numtit;
	}
	public Date getVctpro() {
		return vctpro;
	}
	public void setVctpro(Date vctpro) {
		this.vctpro = vctpro;
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
	public Integer getClsflx() {
		return clsflx;
	}
	public void setClsflx(Integer clsflx) {
		this.clsflx = clsflx;
	}
	
}
