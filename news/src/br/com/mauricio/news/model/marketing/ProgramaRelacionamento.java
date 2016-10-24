package br.com.mauricio.news.model.marketing;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PersistenceUnit;

/**
*
* @author MAURICIO CRUZ
*/
@Entity(name="programarelacionamento")
@PersistenceUnit(unitName="news")
public class ProgramaRelacionamento implements Serializable{

	private static final long serialVersionUID = 1L;
	@GeneratedValue
	@Id
	private Integer id;	
	@OneToOne
	private ProgramaMidiaMais programamidiamais;
	@OneToOne
	private ProgramaIbope programaibope;

	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public ProgramaMidiaMais getProgramamidiamais() {
		return programamidiamais;
	}
	public void setProgramamidiamais(ProgramaMidiaMais programamidiamais) {
		this.programamidiamais = programamidiamais;
	}
	public ProgramaIbope getProgramaibope() {
		return programaibope;
	}
	public void setProgramaibope(ProgramaIbope programaibope) {
		this.programaibope = programaibope;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((programaibope == null) ? 0 : programaibope.hashCode());
		result = prime
				* result
				+ ((programamidiamais == null) ? 0 : programamidiamais
						.hashCode());
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
		ProgramaRelacionamento other = (ProgramaRelacionamento) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (programaibope == null) {
			if (other.programaibope != null)
				return false;
		} else if (!programaibope.equals(other.programaibope))
			return false;
		if (programamidiamais == null) {
			if (other.programamidiamais != null)
				return false;
		} else if (!programamidiamais.equals(other.programamidiamais))
			return false;
		return true;
	}
	
}
