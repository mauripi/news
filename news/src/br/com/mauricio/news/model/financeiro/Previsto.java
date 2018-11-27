package br.com.mauricio.news.model.financeiro;

import java.math.BigDecimal;
import java.util.Date;


public class Previsto {

	private String numtit;
	private Date vctpro;
	private String nomclifor;
	private String obstit;
	private BigDecimal vlrabe;
	private Integer ctared;

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
	public Integer getCtared() {
		return ctared;
	}
	public void setCtared(Integer ctared) {
		this.ctared = ctared;
	}

}
