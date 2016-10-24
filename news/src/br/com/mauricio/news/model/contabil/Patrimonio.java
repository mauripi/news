package br.com.mauricio.news.model.contabil;

import java.io.Serializable;
import java.util.Date;

public class Patrimonio implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String codbem;
	private String desbem;
	private Date dataqi;
	private Double vlrbas;
	private Double vlrres;
	private String nomfor;
	private String numdoc;
	private String numpla;

	public String getCodbem() {
		return codbem;
	}
	public void setCodbem(String codbem) {
		this.codbem = codbem;
	}
	public String getDesbem() {
		return desbem;
	}
	public void setDesbem(String desbem) {
		this.desbem = desbem;
	}
	public Date getDataqi() {
		return dataqi;
	}
	public void setDataqi(Date dataqi) {
		this.dataqi = dataqi;
	}
	public Double getVlrbas() {
		return vlrbas;
	}
	public void setVlrbas(Double vlrbas) {
		this.vlrbas = vlrbas;
	}
	public Double getVlrres() {
		return vlrres;
	}
	public void setVlrres(Double vlrres) {
		this.vlrres = vlrres;
	}
	public String getNomfor() {
		return nomfor;
	}
	public void setNomfor(String nomfor) {
		this.nomfor = nomfor;
	}
	public String getNumdoc() {
		return numdoc;
	}
	public void setNumdoc(String numdoc) {
		this.numdoc = numdoc;
	}
	public String getNumpla() {
		return numpla;
	}
	public void setNumpla(String numpla) {
		this.numpla = numpla;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codbem == null) ? 0 : codbem.hashCode());
		result = prime * result + ((dataqi == null) ? 0 : dataqi.hashCode());
		result = prime * result + ((desbem == null) ? 0 : desbem.hashCode());
		result = prime * result + ((nomfor == null) ? 0 : nomfor.hashCode());
		result = prime * result + ((numdoc == null) ? 0 : numdoc.hashCode());
		result = prime * result + ((numpla == null) ? 0 : numpla.hashCode());
		result = prime * result + ((vlrbas == null) ? 0 : vlrbas.hashCode());
		result = prime * result + ((vlrres == null) ? 0 : vlrres.hashCode());
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
		Patrimonio other = (Patrimonio) obj;
		if (codbem == null) {
			if (other.codbem != null)
				return false;
		} else if (!codbem.equals(other.codbem))
			return false;
		if (dataqi == null) {
			if (other.dataqi != null)
				return false;
		} else if (!dataqi.equals(other.dataqi))
			return false;
		if (desbem == null) {
			if (other.desbem != null)
				return false;
		} else if (!desbem.equals(other.desbem))
			return false;
		if (nomfor == null) {
			if (other.nomfor != null)
				return false;
		} else if (!nomfor.equals(other.nomfor))
			return false;
		if (numdoc == null) {
			if (other.numdoc != null)
				return false;
		} else if (!numdoc.equals(other.numdoc))
			return false;
		if (numpla == null) {
			if (other.numpla != null)
				return false;
		} else if (!numpla.equals(other.numpla))
			return false;
		if (vlrbas == null) {
			if (other.vlrbas != null)
				return false;
		} else if (!vlrbas.equals(other.vlrbas))
			return false;
		if (vlrres == null) {
			if (other.vlrres != null)
				return false;
		} else if (!vlrres.equals(other.vlrres))
			return false;
		return true;
	}		
}
