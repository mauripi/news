package br.com.mauricio.news.model.contabil;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
*
* @author MAURICIO CRUZ
*/

public class LancamentoContabil implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private Integer ctared;
	private Integer codccu;
	private Integer filrat;
	private Integer mes;
	private Integer ano;	
	private Date datlct;
	private String debcre;
	private BigDecimal vlrrat;
	
	public LancamentoContabil(){}
	
	public LancamentoContabil(Integer ctared, Integer codccu, Integer filrat, 
			      Integer mes, Integer ano, Date datlct,String debcre, BigDecimal vlrrat) {
		this.ctared = ctared;
		this.codccu = codccu;
		this.filrat = filrat;
		this.mes = mes;
		this.ano = ano;
		this.datlct = datlct;
		this.debcre = debcre;
		this.vlrrat = vlrrat;
	}


	public Integer getCtared() {
		return ctared;
	}
	public void setCtared(Integer ctared) {
		this.ctared = ctared;
	}
	public Integer getCodccu() {
		return codccu;
	}
	public void setCodccu(Integer codccu) {
		this.codccu = codccu;
	}
	public Integer getFilrat() {
		return filrat;
	}
	public void setFilrat(Integer filrat) {
		this.filrat = filrat;
	}
	public Integer getMes() {
		return mes;
	}
	public void setMes(Integer mes) {
		this.mes = mes;
	}
	public Integer getAno() {
		return ano;
	}
	public void setAno(Integer ano) {
		this.ano = ano;
	}
	public Date getDatlct() {
		return datlct;
	}
	public void setDatlct(Date datlct) {
		this.datlct = datlct;
	}
	public String getDebcre() {
		return debcre;
	}
	public void setDebcre(String debcre) {
		this.debcre = debcre;
	}
	public BigDecimal getVlrrat() {
		return vlrrat;
	}
	public void setVlrrat(BigDecimal vlrrat) {
		this.vlrrat = vlrrat;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ano == null) ? 0 : ano.hashCode());
		result = prime * result + ((codccu == null) ? 0 : codccu.hashCode());
		result = prime * result + ((ctared == null) ? 0 : ctared.hashCode());
		result = prime * result + ((datlct == null) ? 0 : datlct.hashCode());
		result = prime * result + ((debcre == null) ? 0 : debcre.hashCode());
		result = prime * result + ((filrat == null) ? 0 : filrat.hashCode());
		result = prime * result + ((mes == null) ? 0 : mes.hashCode());
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
		LancamentoContabil other = (LancamentoContabil) obj;
		if (ano == null) {
			if (other.ano != null)
				return false;
		} else if (!ano.equals(other.ano))
			return false;
		if (codccu == null) {
			if (other.codccu != null)
				return false;
		} else if (!codccu.equals(other.codccu))
			return false;
		if (ctared == null) {
			if (other.ctared != null)
				return false;
		} else if (!ctared.equals(other.ctared))
			return false;
		if (datlct == null) {
			if (other.datlct != null)
				return false;
		} else if (!datlct.equals(other.datlct))
			return false;
		if (debcre == null) {
			if (other.debcre != null)
				return false;
		} else if (!debcre.equals(other.debcre))
			return false;
		if (filrat == null) {
			if (other.filrat != null)
				return false;
		} else if (!filrat.equals(other.filrat))
			return false;
		if (mes == null) {
			if (other.mes != null)
				return false;
		} else if (!mes.equals(other.mes))
			return false;
		if (vlrrat == null) {
			if (other.vlrrat != null)
				return false;
		} else if (!vlrrat.equals(other.vlrrat))
			return false;
		return true;
	}

}
