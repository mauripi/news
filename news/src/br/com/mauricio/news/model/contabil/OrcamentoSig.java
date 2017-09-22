package br.com.mauricio.news.model.contabil;

import java.math.BigDecimal;
import java.util.Arrays;

public class OrcamentoSig {
	private Integer conta;
	private BigDecimal totais[] = new BigDecimal[12];	

	
	public OrcamentoSig() {
		Arrays.fill(this.totais, BigDecimal.ZERO);
	}

	
	public Integer getConta() {
		return conta;
	}
	public void setConta(Integer conta) {
		this.conta = conta;
	}
	public BigDecimal[] getTotais() {
		return totais;
	}
	public void setTotais(BigDecimal[] totais) {
		this.totais = totais;
	}
	
}
