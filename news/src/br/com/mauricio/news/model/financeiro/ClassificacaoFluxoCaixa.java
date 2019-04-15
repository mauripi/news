package br.com.mauricio.news.model.financeiro;

import java.io.Serializable;

public class ClassificacaoFluxoCaixa implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer ctared;
	private String descta;

	
	public Integer getCtared() {
		return ctared;
	}
	public void setCtared(Integer ctared) {
		this.ctared = ctared;
	}
	public String getDescta() {
		return descta;
	}
	public void setDescta(String descta) {
		this.descta = descta;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ctared == null) ? 0 : ctared.hashCode());
		result = prime * result + ((descta == null) ? 0 : descta.hashCode());
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
		ClassificacaoFluxoCaixa other = (ClassificacaoFluxoCaixa) obj;
		if (ctared == null) {
			if (other.ctared != null)
				return false;
		} else if (!ctared.equals(other.ctared))
			return false;
		if (descta == null) {
			if (other.descta != null)
				return false;
		} else if (!descta.equals(other.descta))
			return false;
		return true;
	}

}
