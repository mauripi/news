package br.com.mauricio.news.model.contabil;

import java.io.Serializable;
import java.util.Date;

/**
*
* @author MAURICIO CRUZ
*/

public class LancamentoConsolidado implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String tipo;
	private Integer ctadeb;
	private Integer ctacre;
	private Double vlrlct;
	private Integer clacta;
	private String descta;
	private Date datlct;
	private String historico;

	
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public Integer getCtadeb() {
		return ctadeb;
	}
	public void setCtadeb(Integer ctadeb) {
		this.ctadeb = ctadeb;
	}
	public Integer getCtacre() {
		return ctacre;
	}
	public void setCtacre(Integer ctacre) {
		this.ctacre = ctacre;
	}
	public Double getVlrlct() {
		return vlrlct;
	}
	public void setVlrlct(Double vlrlct) {
		this.vlrlct = vlrlct;
	}
	public Integer getClacta() {
		return clacta;
	}
	public void setClacta(Integer clacta) {
		this.clacta = clacta;
	}
	public String getDescta() {
		return descta;
	}
	public void setDescta(String descta) {
		this.descta = descta;
	}
	public Date getDatlct() {
		return datlct;
	}
	public void setDatlct(Date datlct) {
		this.datlct = datlct;
	}
	public String getHistorico() {
		return historico;
	}
	public void setHistorico(String historico) {
		this.historico = historico;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((clacta == null) ? 0 : clacta.hashCode());
		result = prime * result + ((ctacre == null) ? 0 : ctacre.hashCode());
		result = prime * result + ((ctadeb == null) ? 0 : ctadeb.hashCode());
		result = prime * result + ((datlct == null) ? 0 : datlct.hashCode());
		result = prime * result + ((descta == null) ? 0 : descta.hashCode());
		result = prime * result
				+ ((historico == null) ? 0 : historico.hashCode());
		result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
		result = prime * result + ((vlrlct == null) ? 0 : vlrlct.hashCode());
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
		LancamentoConsolidado other = (LancamentoConsolidado) obj;
		if (clacta == null) {
			if (other.clacta != null)
				return false;
		} else if (!clacta.equals(other.clacta))
			return false;
		if (ctacre == null) {
			if (other.ctacre != null)
				return false;
		} else if (!ctacre.equals(other.ctacre))
			return false;
		if (ctadeb == null) {
			if (other.ctadeb != null)
				return false;
		} else if (!ctadeb.equals(other.ctadeb))
			return false;
		if (datlct == null) {
			if (other.datlct != null)
				return false;
		} else if (!datlct.equals(other.datlct))
			return false;
		if (descta == null) {
			if (other.descta != null)
				return false;
		} else if (!descta.equals(other.descta))
			return false;
		if (historico == null) {
			if (other.historico != null)
				return false;
		} else if (!historico.equals(other.historico))
			return false;
		if (tipo == null) {
			if (other.tipo != null)
				return false;
		} else if (!tipo.equals(other.tipo))
			return false;
		if (vlrlct == null) {
			if (other.vlrlct != null)
				return false;
		} else if (!vlrlct.equals(other.vlrlct))
			return false;
		return true;
	}
	
}
