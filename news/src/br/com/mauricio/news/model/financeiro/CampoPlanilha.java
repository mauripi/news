package br.com.mauricio.news.model.financeiro;

public class CampoPlanilha {
	private String campoData;
	private String campoDoc;
	private String campohis;
	private String campoEnt;
	private String campoSai;
	private String campoSal;
	private String campoTipo;
	
	
	public String getCampoData() {
		return campoData;
	}
	public void setCampoData(String campoData) {
		this.campoData = campoData;
	}
	public String getCampoDoc() {
		return campoDoc;
	}
	public void setCampoDoc(String campoDoc) {
		this.campoDoc = campoDoc;
	}
	public String getCampohis() {
		return campohis;
	}
	public void setCampohis(String campohis) {
		this.campohis = campohis;
	}
	public String getCampoEnt() {
		return campoEnt;
	}
	public void setCampoEnt(String campoEnt) {
		this.campoEnt = campoEnt;
	}
	public String getCampoSai() {
		return campoSai;
	}
	public void setCampoSai(String campoSai) {
		this.campoSai = campoSai;
	}
	public String getCampoSal() {
		return campoSal;
	}
	public void setCampoSal(String campoSal) {
		this.campoSal = campoSal;
	}
	public String getCampoTipo() {
		return campoTipo;
	}
	public void setCampoTipo(String campoTipo) {
		this.campoTipo = campoTipo;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((campoData == null) ? 0 : campoData.hashCode());
		result = prime * result
				+ ((campoDoc == null) ? 0 : campoDoc.hashCode());
		result = prime * result
				+ ((campoEnt == null) ? 0 : campoEnt.hashCode());
		result = prime * result
				+ ((campoSai == null) ? 0 : campoSai.hashCode());
		result = prime * result
				+ ((campoSal == null) ? 0 : campoSal.hashCode());
		result = prime * result
				+ ((campoTipo == null) ? 0 : campoTipo.hashCode());
		result = prime * result
				+ ((campohis == null) ? 0 : campohis.hashCode());
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
		CampoPlanilha other = (CampoPlanilha) obj;
		if (campoData == null) {
			if (other.campoData != null)
				return false;
		} else if (!campoData.equals(other.campoData))
			return false;
		if (campoDoc == null) {
			if (other.campoDoc != null)
				return false;
		} else if (!campoDoc.equals(other.campoDoc))
			return false;
		if (campoEnt == null) {
			if (other.campoEnt != null)
				return false;
		} else if (!campoEnt.equals(other.campoEnt))
			return false;
		if (campoSai == null) {
			if (other.campoSai != null)
				return false;
		} else if (!campoSai.equals(other.campoSai))
			return false;
		if (campoSal == null) {
			if (other.campoSal != null)
				return false;
		} else if (!campoSal.equals(other.campoSal))
			return false;
		if (campoTipo == null) {
			if (other.campoTipo != null)
				return false;
		} else if (!campoTipo.equals(other.campoTipo))
			return false;
		if (campohis == null) {
			if (other.campohis != null)
				return false;
		} else if (!campohis.equals(other.campohis))
			return false;
		return true;
	}
}
