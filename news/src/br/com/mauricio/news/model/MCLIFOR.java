package br.com.mauricio.news.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PersistenceUnit;

@PersistenceUnit(unitName="news")
@Entity(name="mclifor")
public class MCLIFOR implements Serializable{

	private static final long serialVersionUID = 1L;
	@GeneratedValue
	@Id	
	private Integer id;
	private Integer codcli;
	private Integer codfor;
	@Column(length=120,nullable=false)
	private String nomraz;
	@Column(length=120,nullable=false)
	private String nomfan;	
	private String cgccpf;
	private Integer tippes;//F,J
	private Integer clifor;//C,F,A
	@Column(length=100)
	private String endrua;
	@Column(length=10)
	private String endnum;
	@Column(length=100)
	private String endcpl;
	@Column(length=100)
	private String endbai;
	@Column(length=9)
	private String endcep;
	@Column(length=100)
	private String endcid;
	@Column(length=2)
	private String endest;
	@Column(length=20)
	private String foncon;
	@Column(length=50)
	private String nomcon;
	@Column(length=50)
	private String emacon;

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getCodcli() {
		return codcli;
	}
	public void setCodcli(Integer codcli) {
		this.codcli = codcli;
	}
	public Integer getCodfor() {
		return codfor;
	}
	public void setCodfor(Integer codfor) {
		this.codfor = codfor;
	}
	public String getNomraz() {
		return nomraz;
	}
	public void setNomraz(String nomraz) {
		this.nomraz = nomraz;
	}
	public String getNomfan() {
		return nomfan;
	}
	public void setNomfan(String nomfan) {
		this.nomfan = nomfan;
	}
	public String getCgccpf() {
		return cgccpf;
	}
	public void setCgccpf(String cgccpf) {
		this.cgccpf = cgccpf;
	}
	public Integer getTippes() {
		return tippes;
	}
	public void setTippes(Integer tippes) {
		this.tippes = tippes;
	}
	public Integer getClifor() {
		return clifor;
	}
	public void setClifor(Integer clifor) {
		this.clifor = clifor;
	}
	public String getEndrua() {
		return endrua;
	}
	public void setEndrua(String endrua) {
		this.endrua = endrua;
	}
	public String getEndnum() {
		return endnum;
	}
	public void setEndnum(String endnum) {
		this.endnum = endnum;
	}
	public String getEndcpl() {
		return endcpl;
	}
	public void setEndcpl(String endcpl) {
		this.endcpl = endcpl;
	}
	public String getEndbai() {
		return endbai;
	}
	public void setEndbai(String endbai) {
		this.endbai = endbai;
	}
	public String getEndcep() {
		return endcep;
	}
	public void setEndcep(String endcep) {
		this.endcep = endcep;
	}
	public String getEndcid() {
		return endcid;
	}
	public void setEndcid(String endcid) {
		this.endcid = endcid;
	}
	public String getEndest() {
		return endest;
	}
	public void setEndest(String endest) {
		this.endest = endest;
	}
	public String getFoncon() {
		return foncon;
	}
	public void setFoncon(String foncon) {
		this.foncon = foncon;
	}
	public String getNomcon() {
		return nomcon;
	}
	public void setNomcon(String nomcon) {
		this.nomcon = nomcon;
	}
	public String getEmacon() {
		return emacon;
	}
	public void setEmacon(String emacon) {
		this.emacon = emacon;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cgccpf == null) ? 0 : cgccpf.hashCode());
		result = prime * result + ((clifor == null) ? 0 : clifor.hashCode());
		result = prime * result + ((codcli == null) ? 0 : codcli.hashCode());
		result = prime * result + ((codfor == null) ? 0 : codfor.hashCode());
		result = prime * result + ((emacon == null) ? 0 : emacon.hashCode());
		result = prime * result + ((endbai == null) ? 0 : endbai.hashCode());
		result = prime * result + ((endcep == null) ? 0 : endcep.hashCode());
		result = prime * result + ((endcid == null) ? 0 : endcid.hashCode());
		result = prime * result + ((endcpl == null) ? 0 : endcpl.hashCode());
		result = prime * result + ((endest == null) ? 0 : endest.hashCode());
		result = prime * result + ((endnum == null) ? 0 : endnum.hashCode());
		result = prime * result + ((endrua == null) ? 0 : endrua.hashCode());
		result = prime * result + ((foncon == null) ? 0 : foncon.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nomcon == null) ? 0 : nomcon.hashCode());
		result = prime * result + ((nomfan == null) ? 0 : nomfan.hashCode());
		result = prime * result + ((nomraz == null) ? 0 : nomraz.hashCode());
		result = prime * result + ((tippes == null) ? 0 : tippes.hashCode());
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
		MCLIFOR other = (MCLIFOR) obj;
		if (cgccpf == null) {
			if (other.cgccpf != null)
				return false;
		} else if (!cgccpf.equals(other.cgccpf))
			return false;
		if (clifor == null) {
			if (other.clifor != null)
				return false;
		} else if (!clifor.equals(other.clifor))
			return false;
		if (codcli == null) {
			if (other.codcli != null)
				return false;
		} else if (!codcli.equals(other.codcli))
			return false;
		if (codfor == null) {
			if (other.codfor != null)
				return false;
		} else if (!codfor.equals(other.codfor))
			return false;
		if (emacon == null) {
			if (other.emacon != null)
				return false;
		} else if (!emacon.equals(other.emacon))
			return false;
		if (endbai == null) {
			if (other.endbai != null)
				return false;
		} else if (!endbai.equals(other.endbai))
			return false;
		if (endcep == null) {
			if (other.endcep != null)
				return false;
		} else if (!endcep.equals(other.endcep))
			return false;
		if (endcid == null) {
			if (other.endcid != null)
				return false;
		} else if (!endcid.equals(other.endcid))
			return false;
		if (endcpl == null) {
			if (other.endcpl != null)
				return false;
		} else if (!endcpl.equals(other.endcpl))
			return false;
		if (endest == null) {
			if (other.endest != null)
				return false;
		} else if (!endest.equals(other.endest))
			return false;
		if (endnum == null) {
			if (other.endnum != null)
				return false;
		} else if (!endnum.equals(other.endnum))
			return false;
		if (endrua == null) {
			if (other.endrua != null)
				return false;
		} else if (!endrua.equals(other.endrua))
			return false;
		if (foncon == null) {
			if (other.foncon != null)
				return false;
		} else if (!foncon.equals(other.foncon))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nomcon == null) {
			if (other.nomcon != null)
				return false;
		} else if (!nomcon.equals(other.nomcon))
			return false;
		if (nomfan == null) {
			if (other.nomfan != null)
				return false;
		} else if (!nomfan.equals(other.nomfan))
			return false;
		if (nomraz == null) {
			if (other.nomraz != null)
				return false;
		} else if (!nomraz.equals(other.nomraz))
			return false;
		if (tippes == null) {
			if (other.tippes != null)
				return false;
		} else if (!tippes.equals(other.tippes))
			return false;
		return true;
	}
}
