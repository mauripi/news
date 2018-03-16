package br.com.mauricio.news.model.financeiro;

import java.math.BigDecimal;
import java.util.Date;


public class Previsto {
	private String retour;
	private BigDecimal vlrour;	
	private Integer qtdpar;
	private String numtit;
	private Date vctpro;
	private String nomclifor;
	private String obstit;
	private BigDecimal vlrabe;
	private Integer clsflx;

	public String getRetour() {
		return retour;
	}
	public void setRetour(String retour) {
		this.retour = retour;
	}
	public BigDecimal getVlrour() {
		return vlrour;
	}
	public void setVlrour(BigDecimal vlrour) {
		this.vlrour = vlrour;
	}
	public Integer getQtdpar() {
		return qtdpar;
	}
	public void setQtdpar(Integer qtdpar) {
		this.qtdpar = qtdpar;
	}
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
	public BigDecimal getVlrabe() {
		return vlrabe;
	}
	public void setVlrabe(BigDecimal vlrabe) {
		this.vlrabe = vlrabe;
	}
	public Integer getClsflx() {
		return clsflx;
	}
	public void setClsflx(Integer clsflx) {
		this.clsflx = clsflx;
	}

}
