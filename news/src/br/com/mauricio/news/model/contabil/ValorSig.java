package br.com.mauricio.news.model.contabil;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class ValorSig implements Serializable{
	private static final long serialVersionUID = 1L;
	private Integer chave;
	private BigDecimal vlrsis;
	private BigDecimal persis;
	private BigDecimal vlrorc;
	private List<ValorSig> parentes;
	
	
	
	public Integer getChave() {
		return chave;
	}
	public void setChave(Integer chave) {
		this.chave = chave;
	}
	public BigDecimal getVlrsis() {
		return vlrsis;
	}
	public void setVlrsis(BigDecimal vlrsis) {
		this.vlrsis = vlrsis;
	}
	public BigDecimal getPersis() {
		return persis;
	}
	public void setPersis(BigDecimal persis) {
		this.persis = persis;
	}
	public BigDecimal getVlrorc() {
		return vlrorc;
	}
	public void setVlrorc(BigDecimal vlrorc) {
		this.vlrorc = vlrorc;
	}
	public List<ValorSig> getParentes() {
		return parentes;
	}
	public void setParentes(List<ValorSig> parentes) {
		this.parentes = parentes;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((chave == null) ? 0 : chave.hashCode());
		result = prime * result + ((parentes == null) ? 0 : parentes.hashCode());
		result = prime * result + ((persis == null) ? 0 : persis.hashCode());
		result = prime * result + ((vlrorc == null) ? 0 : vlrorc.hashCode());
		result = prime * result + ((vlrsis == null) ? 0 : vlrsis.hashCode());
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
		ValorSig other = (ValorSig) obj;
		if (chave == null) {
			if (other.chave != null)
				return false;
		} else if (!chave.equals(other.chave))
			return false;
		if (parentes == null) {
			if (other.parentes != null)
				return false;
		} else if (!parentes.equals(other.parentes))
			return false;
		if (persis == null) {
			if (other.persis != null)
				return false;
		} else if (!persis.equals(other.persis))
			return false;
		if (vlrorc == null) {
			if (other.vlrorc != null)
				return false;
		} else if (!vlrorc.equals(other.vlrorc))
			return false;
		if (vlrsis == null) {
			if (other.vlrsis != null)
				return false;
		} else if (!vlrsis.equals(other.vlrsis))
			return false;
		return true;
	}

}
