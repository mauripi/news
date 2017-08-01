package br.com.mauricio.news.model.comercial;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PersistenceUnit;

/**
*
* @author MAURICIO CRUZ and CAROLINE MARQUES
*/
@Entity(name="configcomercial")
@PersistenceUnit(unitName="news")
public class ConfigComercial  implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@GeneratedValue
	@Id
	private Integer id;
	private String pathfrontendapp;

	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getPathfrontendapp() {
		return pathfrontendapp;
	}
	public void setPathfrontendapp(String pathfrontendapp) {
		this.pathfrontendapp = pathfrontendapp;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((pathfrontendapp == null) ? 0 : pathfrontendapp.hashCode());
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
		ConfigComercial other = (ConfigComercial) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (pathfrontendapp == null) {
			if (other.pathfrontendapp != null)
				return false;
		} else if (!pathfrontendapp.equals(other.pathfrontendapp))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return pathfrontendapp;
	}

	
}
