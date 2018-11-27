package br.com.mauricio.news.model.ti;

import java.io.Serializable;

public class ConsumoEmbratelPK implements Serializable{

	private static final long serialVersionUID = 1L;
	private String fatura;
	private Integer sequencia;
	
	public ConsumoEmbratelPK() {}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fatura == null) ? 0 : fatura.hashCode());
		result = prime * result + ((sequencia == null) ? 0 : sequencia.hashCode());
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
		ConsumoEmbratelPK other = (ConsumoEmbratelPK) obj;
		if (fatura == null) {
			if (other.fatura != null)
				return false;
		} else if (!fatura.equals(other.fatura))
			return false;
		if (sequencia == null) {
			if (other.sequencia != null)
				return false;
		} else if (!sequencia.equals(other.sequencia))
			return false;
		return true;
	}
	
}
