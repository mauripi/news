package br.com.mauricio.news.model.contabil;

import java.math.BigDecimal;

import org.joda.time.DateTime;

public class MCTB001 {
	private Integer codemp;
	private Integer codfil;
	private Integer codfor;
	private DateTime datent;	
	private String codsnf;
	private String codtns;
	private Integer numnfc;	
	private String codpro;
	private String cplipc;	
	private BigDecimal vlrrat;
	private String tipdes;
	private Integer ctared;
	private String codccu;	
	private String codfam;
	private String classificacao;

	
	public Integer getCodemp() {
		return codemp;
	}
	public void setCodemp(Integer codemp) {
		this.codemp = codemp;
	}
	public Integer getCodfil() {
		return codfil;
	}
	public void setCodfil(Integer codfil) {
		this.codfil = codfil;
	}
	public Integer getCodfor() {
		return codfor;
	}
	public void setCodfor(Integer codfor) {
		this.codfor = codfor;
	}
	public DateTime getDatent() {
		return datent;
	}
	public void setDatent(DateTime datent) {
		this.datent = datent;
	}
	public String getCodsnf() {
		return codsnf;
	}
	public void setCodsnf(String codsnf) {
		this.codsnf = codsnf;
	}
	public String getCodtns() {
		return codtns;
	}
	public void setCodtns(String codtns) {
		this.codtns = codtns;
	}
	public Integer getNumnfc() {
		return numnfc;
	}
	public void setNumnfc(Integer numnfc) {
		this.numnfc = numnfc;
	}
	public String getCodpro() {
		return codpro;
	}
	public void setCodpro(String codpro) {
		this.codpro = codpro;
	}
	public String getCplipc() {
		return cplipc;
	}
	public void setCplipc(String cplipc) {
		this.cplipc = cplipc;
	}
	public BigDecimal getVlrrat() {
		return vlrrat;
	}
	public void setVlrrat(BigDecimal vlrrat) {
		this.vlrrat = vlrrat;
	}
	public String getTipdes() {
		return tipdes;
	}
	public void setTipdes(String tipdes) {
		this.tipdes = tipdes;
	}
	public Integer getCtared() {
		return ctared;
	}
	public void setCtared(Integer ctared) {
		this.ctared = ctared;
	}
	public String getCodccu() {
		return codccu;
	}
	public void setCodccu(String codccu) {
		this.codccu = codccu;
	}
	public String getCodfam() {
		return codfam;
	}
	public void setCodfam(String codfam) {
		this.codfam = codfam;
	}
	public String getClassificacao() {
		return classificacao;
	}
	public void setClassificacao(String classificacao) {
		this.classificacao = classificacao;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((classificacao == null) ? 0 : classificacao.hashCode());
		result = prime * result + ((codccu == null) ? 0 : codccu.hashCode());
		result = prime * result + ((codemp == null) ? 0 : codemp.hashCode());
		result = prime * result + ((codfam == null) ? 0 : codfam.hashCode());
		result = prime * result + ((codfil == null) ? 0 : codfil.hashCode());
		result = prime * result + ((codfor == null) ? 0 : codfor.hashCode());
		result = prime * result + ((codpro == null) ? 0 : codpro.hashCode());
		result = prime * result + ((codsnf == null) ? 0 : codsnf.hashCode());
		result = prime * result + ((codtns == null) ? 0 : codtns.hashCode());
		result = prime * result + ((cplipc == null) ? 0 : cplipc.hashCode());
		result = prime * result + ((ctared == null) ? 0 : ctared.hashCode());
		result = prime * result + ((datent == null) ? 0 : datent.hashCode());
		result = prime * result + ((numnfc == null) ? 0 : numnfc.hashCode());
		result = prime * result + ((tipdes == null) ? 0 : tipdes.hashCode());
		result = prime * result + ((vlrrat == null) ? 0 : vlrrat.hashCode());
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
		MCTB001 other = (MCTB001) obj;
		if (classificacao == null) {
			if (other.classificacao != null)
				return false;
		} else if (!classificacao.equals(other.classificacao))
			return false;
		if (codccu == null) {
			if (other.codccu != null)
				return false;
		} else if (!codccu.equals(other.codccu))
			return false;
		if (codemp == null) {
			if (other.codemp != null)
				return false;
		} else if (!codemp.equals(other.codemp))
			return false;
		if (codfam == null) {
			if (other.codfam != null)
				return false;
		} else if (!codfam.equals(other.codfam))
			return false;
		if (codfil == null) {
			if (other.codfil != null)
				return false;
		} else if (!codfil.equals(other.codfil))
			return false;
		if (codfor == null) {
			if (other.codfor != null)
				return false;
		} else if (!codfor.equals(other.codfor))
			return false;
		if (codpro == null) {
			if (other.codpro != null)
				return false;
		} else if (!codpro.equals(other.codpro))
			return false;
		if (codsnf == null) {
			if (other.codsnf != null)
				return false;
		} else if (!codsnf.equals(other.codsnf))
			return false;
		if (codtns == null) {
			if (other.codtns != null)
				return false;
		} else if (!codtns.equals(other.codtns))
			return false;
		if (cplipc == null) {
			if (other.cplipc != null)
				return false;
		} else if (!cplipc.equals(other.cplipc))
			return false;
		if (ctared == null) {
			if (other.ctared != null)
				return false;
		} else if (!ctared.equals(other.ctared))
			return false;
		if (datent == null) {
			if (other.datent != null)
				return false;
		} else if (!datent.equals(other.datent))
			return false;
		if (numnfc == null) {
			if (other.numnfc != null)
				return false;
		} else if (!numnfc.equals(other.numnfc))
			return false;
		if (tipdes == null) {
			if (other.tipdes != null)
				return false;
		} else if (!tipdes.equals(other.tipdes))
			return false;
		if (vlrrat == null) {
			if (other.vlrrat != null)
				return false;
		} else if (!vlrrat.equals(other.vlrrat))
			return false;
		return true;
	}
}
