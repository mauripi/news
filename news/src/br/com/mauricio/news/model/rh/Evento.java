package br.com.mauricio.news.model.rh;

import java.util.List;

public class Evento {
	
	List<Holerite> holerites;

	public Evento(List<Holerite> holerites) {
		this.holerites = holerites;
	}
	public List<Holerite> getHolerites() {
		return holerites;
	}

	public void setHolerites(List<Holerite> holerites) {
		this.holerites = holerites;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((holerites == null) ? 0 : holerites.hashCode());
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
		Evento other = (Evento) obj;
		if (holerites == null) {
			if (other.holerites != null)
				return false;
		} else if (!holerites.equals(other.holerites))
			return false;
		return true;
	}
	
}
