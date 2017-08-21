package br.com.mauricio.news.model.contabil;

import java.math.BigDecimal;
import java.util.Arrays;

public class ContaValorTotalFilial {
	private Integer ctared;
	private Integer filrat;
	private BigDecimal totais[] = new BigDecimal[12];
	
	public ContaValorTotalFilial() {
		Arrays.fill(this.totais, BigDecimal.ZERO);
	}
	
	public Integer getCtared() {
		return ctared;
	}
	public BigDecimal[] getTotais() {
		return totais;
	}
	public void setTotais(BigDecimal[] totais) {
		this.totais = totais;
	}
	public void setCtared(Integer ctared) {
		this.ctared = ctared;
	}
	public Integer getFilrat() {
		return filrat;
	}
	public void setFilrat(Integer filrat) {
		this.filrat = filrat;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ctared == null) ? 0 : ctared.hashCode());
		result = prime * result + ((filrat == null) ? 0 : filrat.hashCode());
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
		ContaValorTotalFilial other = (ContaValorTotalFilial) obj;
		if (ctared == null) {
			if (other.ctared != null)
				return false;
		} else if (!ctared.equals(other.ctared))
			return false;
		if (filrat == null) {
			if (other.filrat != null)
				return false;
		} else if (!filrat.equals(other.filrat))
			return false;
		return true;
	}

}
