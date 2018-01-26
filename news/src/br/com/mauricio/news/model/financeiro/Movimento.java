package br.com.mauricio.news.model.financeiro;

import java.math.BigDecimal;
import java.util.Date;

public class Movimento {
	private String numcco;
	private Date datmov;
	private Integer seqmov;
	private String codtns;
	private String docmov;
	private String hismov;
	private BigDecimal vlrmov;
	private String debcre;
	private Integer clsflx;
	private Integer codfil;

	
	public String getNumcco() {
		return numcco;
	}
	public void setNumcco(String numcco) {
		this.numcco = numcco;
	}
	public Date getDatmov() {
		return datmov;
	}
	public void setDatmov(Date datmov) {
		this.datmov = datmov;
	}
	public Integer getSeqmov() {
		return seqmov;
	}
	public void setSeqmov(Integer seqmov) {
		this.seqmov = seqmov;
	}
	public String getCodtns() {
		return codtns;
	}
	public void setCodtns(String codtns) {
		this.codtns = codtns;
	}
	public String getDocmov() {
		return docmov;
	}
	public void setDocmov(String docmov) {
		this.docmov = docmov;
	}
	public String getHismov() {
		return hismov;
	}
	public void setHismov(String hismov) {
		this.hismov = hismov;
	}
	public BigDecimal getVlrmov() {
		return vlrmov;
	}
	public void setVlrmov(BigDecimal vlrmov) {
		this.vlrmov = vlrmov;
	}
	public String getDebcre() {
		return debcre;
	}
	public void setDebcre(String debcre) {
		this.debcre = debcre;
	}
	public Integer getClsflx() {
		return clsflx;
	}
	public void setClsflx(Integer clsflx) {
		this.clsflx = clsflx;
	}
	public Integer getCodfil() {
		return codfil;
	}
	public void setCodfil(Integer codfil) {
		this.codfil = codfil;
	}

}
