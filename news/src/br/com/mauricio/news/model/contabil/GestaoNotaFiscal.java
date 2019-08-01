package br.com.mauricio.news.model.contabil;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class GestaoNotaFiscal implements Serializable{

	private static final long serialVersionUID = 1L;
	private Integer codfil;
	private Integer codfor;
	private Integer numnfc;
	private String codsnf;
	private BigDecimal vlrbru;
	private Date datent;
	private Integer numctr;
	private Integer qtdpar;
	private Integer propar;
	private String nomfor;
	private String objctr;
	private Integer diabas;
	private Date ultgoc;
	private String status;
	private Date datven;
	
	public Integer getCodfil() {
		return codfil;
	}
	public void setCodfil(Integer codfil) {
		this.codfil = codfil;
	}
	public Integer getCodfor() {
		return codfor;
	}
	public void setCodfor(Integer codfor) {
		this.codfor = codfor;
	}
	public Integer getNumnfc() {
		return numnfc;
	}
	public void setNumnfc(Integer numnfc) {
		this.numnfc = numnfc;
	}
	public String getCodsnf() {
		return codsnf;
	}
	public void setCodsnf(String codsnf) {
		this.codsnf = codsnf;
	}
	public BigDecimal getVlrbru() {
		return vlrbru;
	}
	public void setVlrbru(BigDecimal vlrbru) {
		this.vlrbru = vlrbru;
	}
	public Date getDatent() {
		return datent;
	}
	public void setDatent(Date datent) {
		this.datent = datent;
	}
	public Integer getNumctr() {
		return numctr;
	}
	public void setNumctr(Integer numctr) {
		this.numctr = numctr;
	}
	public Integer getQtdpar() {
		return qtdpar;
	}
	public void setQtdpar(Integer qtdpar) {
		this.qtdpar = qtdpar;
	}
	public Integer getPropar() {
		return propar;
	}
	public void setPropar(Integer propar) {
		this.propar = propar;
	}
	public String getNomfor() {
		return nomfor;
	}
	public void setNomfor(String nomfor) {
		this.nomfor = nomfor;
	}
	public String getObjctr() {
		return objctr;
	}
	public void setObjctr(String objctr) {
		this.objctr = objctr;
	}
	public Integer getDiabas() {
		return diabas;
	}
	public void setDiabas(Integer diabas) {
		this.diabas = diabas;
	}
	public Date getUltgoc() {
		return ultgoc;
	}
	public void setUltgoc(Date ultgoc) {
		this.ultgoc = ultgoc;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getDatven() {
		return datven;
	}
	public void setDatven(Date datven) {
		this.datven = datven;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codfil == null) ? 0 : codfil.hashCode());
		result = prime * result + ((codfor == null) ? 0 : codfor.hashCode());
		result = prime * result + ((codsnf == null) ? 0 : codsnf.hashCode());
		result = prime * result + ((datent == null) ? 0 : datent.hashCode());
		result = prime * result + ((datven == null) ? 0 : datven.hashCode());
		result = prime * result + ((diabas == null) ? 0 : diabas.hashCode());
		result = prime * result + ((nomfor == null) ? 0 : nomfor.hashCode());
		result = prime * result + ((numctr == null) ? 0 : numctr.hashCode());
		result = prime * result + ((numnfc == null) ? 0 : numnfc.hashCode());
		result = prime * result + ((objctr == null) ? 0 : objctr.hashCode());
		result = prime * result + ((propar == null) ? 0 : propar.hashCode());
		result = prime * result + ((qtdpar == null) ? 0 : qtdpar.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((ultgoc == null) ? 0 : ultgoc.hashCode());
		result = prime * result + ((vlrbru == null) ? 0 : vlrbru.hashCode());
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
		GestaoNotaFiscal other = (GestaoNotaFiscal) obj;
		if (codfil == null) {
			if (other.codfil != null)
				return false;
		} else if (!codfil.equals(other.codfil))
			return false;
		if (codfor == null) {
			if (other.codfor != null)
				return false;
		} else if (!codfor.equals(other.codfor))
			return false;
		if (codsnf == null) {
			if (other.codsnf != null)
				return false;
		} else if (!codsnf.equals(other.codsnf))
			return false;
		if (datent == null) {
			if (other.datent != null)
				return false;
		} else if (!datent.equals(other.datent))
			return false;
		if (datven == null) {
			if (other.datven != null)
				return false;
		} else if (!datven.equals(other.datven))
			return false;
		if (diabas == null) {
			if (other.diabas != null)
				return false;
		} else if (!diabas.equals(other.diabas))
			return false;
		if (nomfor == null) {
			if (other.nomfor != null)
				return false;
		} else if (!nomfor.equals(other.nomfor))
			return false;
		if (numctr == null) {
			if (other.numctr != null)
				return false;
		} else if (!numctr.equals(other.numctr))
			return false;
		if (numnfc == null) {
			if (other.numnfc != null)
				return false;
		} else if (!numnfc.equals(other.numnfc))
			return false;
		if (objctr == null) {
			if (other.objctr != null)
				return false;
		} else if (!objctr.equals(other.objctr))
			return false;
		if (propar == null) {
			if (other.propar != null)
				return false;
		} else if (!propar.equals(other.propar))
			return false;
		if (qtdpar == null) {
			if (other.qtdpar != null)
				return false;
		} else if (!qtdpar.equals(other.qtdpar))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (ultgoc == null) {
			if (other.ultgoc != null)
				return false;
		} else if (!ultgoc.equals(other.ultgoc))
			return false;
		if (vlrbru == null) {
			if (other.vlrbru != null)
				return false;
		} else if (!vlrbru.equals(other.vlrbru))
			return false;
		return true;
	}

}
