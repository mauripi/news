package br.com.mauricio.news.model.contabil;

import java.util.ArrayList;
import java.util.List;

public class ViewConsolidado {
	private Integer clacta;
	private Double janeiro = new Double("0.0");
	private Double fevereiro = new Double("0.0");
	private Double marco = new Double("0.0");
	private Double abril = new Double("0.0");
	private Double maio = new Double("0.0");
	private Double junho = new Double("0.0");
	private Double julho = new Double("0.0");
	private Double agosto = new Double("0.0");
	private Double setembro = new Double("0.0");
	private Double outubro = new Double("0.0");
	private Double novembro = new Double("0.0");
	private Double dezembro = new Double("0.0");
	private List<ViewConsolidado> analiticos = new ArrayList<ViewConsolidado>();
	
	
	public Integer getClacta() {
		return clacta;
	}
	public void setClacta(Integer clacta) {
		this.clacta = clacta;
	}
	public Double getJaneiro() {
		return janeiro;
	}
	public void setJaneiro(Double janeiro) {
		this.janeiro = janeiro;
	}
	public Double getFevereiro() {
		return fevereiro;
	}
	public void setFevereiro(Double fevereiro) {
		this.fevereiro = fevereiro;
	}
	public Double getMarco() {
		return marco;
	}
	public void setMarco(Double marco) {
		this.marco = marco;
	}
	public Double getAbril() {
		return abril;
	}
	public void setAbril(Double abril) {
		this.abril = abril;
	}
	public Double getMaio() {
		return maio;
	}
	public void setMaio(Double maio) {
		this.maio = maio;
	}
	public Double getJunho() {
		return junho;
	}
	public void setJunho(Double junho) {
		this.junho = junho;
	}
	public Double getJulho() {
		return julho;
	}
	public void setJulho(Double julho) {
		this.julho = julho;
	}
	public Double getAgosto() {
		return agosto;
	}
	public void setAgosto(Double agosto) {
		this.agosto = agosto;
	}
	public Double getSetembro() {
		return setembro;
	}
	public void setSetembro(Double setembro) {
		this.setembro = setembro;
	}
	public Double getOutubro() {
		return outubro;
	}
	public void setOutubro(Double outubro) {
		this.outubro = outubro;
	}
	public Double getNovembro() {
		return novembro;
	}
	public void setNovembro(Double novembro) {
		this.novembro = novembro;
	}
	public Double getDezembro() {
		return dezembro;
	}
	public void setDezembro(Double dezembro) {
		this.dezembro = dezembro;
	}
	public List<ViewConsolidado> getAnaliticos() {
		return analiticos;
	}
	public void setAnaliticos(List<ViewConsolidado> analiticos) {
		this.analiticos = analiticos;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((abril == null) ? 0 : abril.hashCode());
		result = prime * result + ((agosto == null) ? 0 : agosto.hashCode());
		result = prime * result
				+ ((analiticos == null) ? 0 : analiticos.hashCode());
		result = prime * result + ((clacta == null) ? 0 : clacta.hashCode());
		result = prime * result
				+ ((dezembro == null) ? 0 : dezembro.hashCode());
		result = prime * result
				+ ((fevereiro == null) ? 0 : fevereiro.hashCode());
		result = prime * result + ((janeiro == null) ? 0 : janeiro.hashCode());
		result = prime * result + ((julho == null) ? 0 : julho.hashCode());
		result = prime * result + ((junho == null) ? 0 : junho.hashCode());
		result = prime * result + ((maio == null) ? 0 : maio.hashCode());
		result = prime * result + ((marco == null) ? 0 : marco.hashCode());
		result = prime * result
				+ ((novembro == null) ? 0 : novembro.hashCode());
		result = prime * result + ((outubro == null) ? 0 : outubro.hashCode());
		result = prime * result
				+ ((setembro == null) ? 0 : setembro.hashCode());
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
		ViewConsolidado other = (ViewConsolidado) obj;
		if (abril == null) {
			if (other.abril != null)
				return false;
		} else if (!abril.equals(other.abril))
			return false;
		if (agosto == null) {
			if (other.agosto != null)
				return false;
		} else if (!agosto.equals(other.agosto))
			return false;
		if (analiticos == null) {
			if (other.analiticos != null)
				return false;
		} else if (!analiticos.equals(other.analiticos))
			return false;
		if (clacta == null) {
			if (other.clacta != null)
				return false;
		} else if (!clacta.equals(other.clacta))
			return false;
		if (dezembro == null) {
			if (other.dezembro != null)
				return false;
		} else if (!dezembro.equals(other.dezembro))
			return false;
		if (fevereiro == null) {
			if (other.fevereiro != null)
				return false;
		} else if (!fevereiro.equals(other.fevereiro))
			return false;
		if (janeiro == null) {
			if (other.janeiro != null)
				return false;
		} else if (!janeiro.equals(other.janeiro))
			return false;
		if (julho == null) {
			if (other.julho != null)
				return false;
		} else if (!julho.equals(other.julho))
			return false;
		if (junho == null) {
			if (other.junho != null)
				return false;
		} else if (!junho.equals(other.junho))
			return false;
		if (maio == null) {
			if (other.maio != null)
				return false;
		} else if (!maio.equals(other.maio))
			return false;
		if (marco == null) {
			if (other.marco != null)
				return false;
		} else if (!marco.equals(other.marco))
			return false;
		if (novembro == null) {
			if (other.novembro != null)
				return false;
		} else if (!novembro.equals(other.novembro))
			return false;
		if (outubro == null) {
			if (other.outubro != null)
				return false;
		} else if (!outubro.equals(other.outubro))
			return false;
		if (setembro == null) {
			if (other.setembro != null)
				return false;
		} else if (!setembro.equals(other.setembro))
			return false;
		return true;
	}
}
