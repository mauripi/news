package br.com.mauricio.news.model.contabil;

import java.io.Serializable;
import java.math.BigDecimal;

public class CustoDespesaContabil implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer ctasig;
	private String descta;
	private BigDecimal jan = BigDecimal.ZERO;
	private BigDecimal fev = BigDecimal.ZERO;
	private BigDecimal mar = BigDecimal.ZERO;
	private BigDecimal abr = BigDecimal.ZERO;
	private BigDecimal mai = BigDecimal.ZERO;
	private BigDecimal jun = BigDecimal.ZERO;
	private BigDecimal jul = BigDecimal.ZERO;
	private BigDecimal ago = BigDecimal.ZERO;
	private BigDecimal set = BigDecimal.ZERO;
	private BigDecimal out = BigDecimal.ZERO;
	private BigDecimal nov = BigDecimal.ZERO;
	private BigDecimal dec = BigDecimal.ZERO;
	
	public CustoDespesaContabil (){}
	
	public CustoDespesaContabil (Integer ctasig,String descta){
		this.ctasig = ctasig;
		this.descta = descta;
	}
	
	public Integer getCtasig() {
		return ctasig;
	}
	public void setCtasig(Integer ctasig) {
		this.ctasig = ctasig;
	}
	public String getDescta() {
		return descta;
	}
	public void setDescta(String descta) {
		this.descta = descta;
	}
	public BigDecimal getJan() {
		return jan;
	}
	public void setJan(BigDecimal jan) {
		this.jan = jan;
	}
	public BigDecimal getFev() {
		return fev;
	}
	public void setFev(BigDecimal fev) {
		this.fev = fev;
	}
	public BigDecimal getMar() {
		return mar;
	}
	public void setMar(BigDecimal mar) {
		this.mar = mar;
	}
	public BigDecimal getAbr() {
		return abr;
	}
	public void setAbr(BigDecimal abr) {
		this.abr = abr;
	}
	public BigDecimal getMai() {
		return mai;
	}
	public void setMai(BigDecimal mai) {
		this.mai = mai;
	}
	public BigDecimal getJun() {
		return jun;
	}
	public void setJun(BigDecimal jun) {
		this.jun = jun;
	}
	public BigDecimal getJul() {
		return jul;
	}
	public void setJul(BigDecimal jul) {
		this.jul = jul;
	}
	public BigDecimal getAgo() {
		return ago;
	}
	public void setAgo(BigDecimal ago) {
		this.ago = ago;
	}
	public BigDecimal getSet() {
		return set;
	}
	public void setSet(BigDecimal set) {
		this.set = set;
	}
	public BigDecimal getOut() {
		return out;
	}
	public void setOut(BigDecimal out) {
		this.out = out;
	}
	public BigDecimal getNov() {
		return nov;
	}
	public void setNov(BigDecimal nov) {
		this.nov = nov;
	}
	public BigDecimal getDec() {
		return dec;
	}
	public void setDec(BigDecimal dec) {
		this.dec = dec;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((abr == null) ? 0 : abr.hashCode());
		result = prime * result + ((ago == null) ? 0 : ago.hashCode());
		result = prime * result + ((ctasig == null) ? 0 : ctasig.hashCode());
		result = prime * result + ((dec == null) ? 0 : dec.hashCode());
		result = prime * result + ((descta == null) ? 0 : descta.hashCode());
		result = prime * result + ((fev == null) ? 0 : fev.hashCode());
		result = prime * result + ((jan == null) ? 0 : jan.hashCode());
		result = prime * result + ((jul == null) ? 0 : jul.hashCode());
		result = prime * result + ((jun == null) ? 0 : jun.hashCode());
		result = prime * result + ((mai == null) ? 0 : mai.hashCode());
		result = prime * result + ((mar == null) ? 0 : mar.hashCode());
		result = prime * result + ((nov == null) ? 0 : nov.hashCode());
		result = prime * result + ((out == null) ? 0 : out.hashCode());
		result = prime * result + ((set == null) ? 0 : set.hashCode());
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
		CustoDespesaContabil other = (CustoDespesaContabil) obj;
		if (abr == null) {
			if (other.abr != null)
				return false;
		} else if (!abr.equals(other.abr))
			return false;
		if (ago == null) {
			if (other.ago != null)
				return false;
		} else if (!ago.equals(other.ago))
			return false;
		if (ctasig == null) {
			if (other.ctasig != null)
				return false;
		} else if (!ctasig.equals(other.ctasig))
			return false;
		if (dec == null) {
			if (other.dec != null)
				return false;
		} else if (!dec.equals(other.dec))
			return false;
		if (descta == null) {
			if (other.descta != null)
				return false;
		} else if (!descta.equals(other.descta))
			return false;
		if (fev == null) {
			if (other.fev != null)
				return false;
		} else if (!fev.equals(other.fev))
			return false;
		if (jan == null) {
			if (other.jan != null)
				return false;
		} else if (!jan.equals(other.jan))
			return false;
		if (jul == null) {
			if (other.jul != null)
				return false;
		} else if (!jul.equals(other.jul))
			return false;
		if (jun == null) {
			if (other.jun != null)
				return false;
		} else if (!jun.equals(other.jun))
			return false;
		if (mai == null) {
			if (other.mai != null)
				return false;
		} else if (!mai.equals(other.mai))
			return false;
		if (mar == null) {
			if (other.mar != null)
				return false;
		} else if (!mar.equals(other.mar))
			return false;
		if (nov == null) {
			if (other.nov != null)
				return false;
		} else if (!nov.equals(other.nov))
			return false;
		if (out == null) {
			if (other.out != null)
				return false;
		} else if (!out.equals(other.out))
			return false;
		if (set == null) {
			if (other.set != null)
				return false;
		} else if (!set.equals(other.set))
			return false;
		return true;
	}
}
