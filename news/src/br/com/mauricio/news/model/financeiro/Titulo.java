package br.com.mauricio.news.model.financeiro;

import java.math.BigDecimal;
import java.util.Date;


public class Titulo {
	private String numtit;
	private Integer codfil;
	private String codtpt;
	private Integer codclifor;
	private String nomclifor;
	private BigDecimal vlrori;
	private BigDecimal vlrabe;
	private String obstit;
	private Date vencOriginal;
	private Date vencAtual;
	private Date provPagto;
	private Integer clsflx;
	private String oritit;
	private String codtns;
	
	public String getNumtit() {
		return numtit;
	}
	public void setNumtit(String numtit) {
		this.numtit = numtit;
	}
	public Integer getCodfil() {
		return codfil;
	}
	public void setCodfil(Integer codfil) {
		this.codfil = codfil;
	}
	public String getCodtpt() {
		return codtpt;
	}
	public void setCodtpt(String codtpt) {
		this.codtpt = codtpt;
	}
	public Integer getCodclifor() {
		return codclifor;
	}
	public void setCodclifor(Integer codclifor) {
		this.codclifor = codclifor;
	}
	public String getNomclifor() {
		return nomclifor;
	}
	public void setNomclifor(String nomclifor) {
		this.nomclifor = nomclifor;
	}
	public BigDecimal getVlrori() {
		return vlrori;
	}
	public void setVlrori(BigDecimal vlrori) {
		this.vlrori = vlrori;
	}
	public BigDecimal getVlrabe() {
		return vlrabe;
	}
	public void setVlrabe(BigDecimal vlrabe) {
		this.vlrabe = vlrabe;
	}
	public String getObstit() {
		return obstit;
	}
	public void setObstit(String obstit) {
		this.obstit = obstit;
	}
	public Date getVencOriginal() {
		return vencOriginal;
	}
	public void setVencOriginal(Date vencOriginal) {
		this.vencOriginal = vencOriginal;
	}
	public Date getVencAtual() {
		return vencAtual;
	}
	public void setVencAtual(Date vencAtual) {
		this.vencAtual = vencAtual;
	}
	public Date getProvPagto() {
		return provPagto;
	}
	public void setProvPagto(Date provPagto) {
		this.provPagto = provPagto;
	}
	public Integer getClsflx() {
		return clsflx;
	}
	public void setClsflx(Integer clsflx) {
		this.clsflx = clsflx;
	}
	public String getOritit() {
		return oritit;
	}
	public void setOritit(String oritit) {
		this.oritit = oritit;
	}
	public String getCodtns() {
		return codtns;
	}
	public void setCodtns(String codtns) {
		this.codtns = codtns;
	}
	
}
