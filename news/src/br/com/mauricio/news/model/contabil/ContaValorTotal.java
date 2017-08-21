package br.com.mauricio.news.model.contabil;

import java.math.BigDecimal;
import java.util.Arrays;

public class ContaValorTotal {
	private Integer ctared;
	private BigDecimal totais[] = new BigDecimal[12];
	
	public ContaValorTotal() {
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

}
