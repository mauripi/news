package br.com.mauricio.news.model.contabil;

public class MCTB002 {
	private Integer codemp;
	private String codccu;
	private String desccu;
	private String paiccu;
	private String claccu;

	
	public Integer getCodemp() {
		return codemp;
	}
	public void setCodemp(Integer codemp) {
		this.codemp = codemp;
	}
	public String getCodccu() {
		return codccu;
	}
	public void setCodccu(String codccu) {
		this.codccu = codccu;
	}
	public String getDesccu() {
		return desccu;
	}
	public void setDesccu(String desccu) {
		this.desccu = desccu;
	}
	public String getPaiccu() {
		return paiccu;
	}
	public void setPaiccu(String paiccu) {
		this.paiccu = paiccu;
	}
	public String getClaccu() {
		return claccu;
	}
	public void setClaccu(String claccu) {
		this.claccu = claccu;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((claccu == null) ? 0 : claccu.hashCode());
		result = prime * result + ((codccu == null) ? 0 : codccu.hashCode());
		result = prime * result + ((codemp == null) ? 0 : codemp.hashCode());
		result = prime * result + ((desccu == null) ? 0 : desccu.hashCode());
		result = prime * result + ((paiccu == null) ? 0 : paiccu.hashCode());
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
		MCTB002 other = (MCTB002) obj;
		if (claccu == null) {
			if (other.claccu != null)
				return false;
		} else if (!claccu.equals(other.claccu))
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
		if (desccu == null) {
			if (other.desccu != null)
				return false;
		} else if (!desccu.equals(other.desccu))
			return false;
		if (paiccu == null) {
			if (other.paiccu != null)
				return false;
		} else if (!paiccu.equals(other.paiccu))
			return false;
		return true;
	}

}
