package br.com.mauricio.news.model.comercial;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PersistenceUnit;

/**
*
* @author MAURICIO CRUZ and CAROLINE MARQUES
*/
@Entity(name="linkprogramacao")
@PersistenceUnit(unitName="news")
public class LinkProgramacao implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@GeneratedValue
	@Id
	private Integer id;
	private String url;
	private String titulo;
	private String pathimg;
	@ManyToOne
	@JoinColumn(name = "programacaocomercial_id")
	private ProgramacaoComercial programacao;

	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getPathimg() {
		return pathimg;
	}
	public void setPathimg(String pathimg) {
		this.pathimg = pathimg;
	}
	public ProgramacaoComercial getProgramacao() {
		return programacao;
	}
	public void setProgramacao(ProgramacaoComercial programacao) {
		this.programacao = programacao;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((pathimg == null) ? 0 : pathimg.hashCode());
		result = prime * result + ((programacao == null) ? 0 : programacao.hashCode());
		result = prime * result + ((titulo == null) ? 0 : titulo.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
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
		LinkProgramacao other = (LinkProgramacao) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (pathimg == null) {
			if (other.pathimg != null)
				return false;
		} else if (!pathimg.equals(other.pathimg))
			return false;
		if (programacao == null) {
			if (other.programacao != null)
				return false;
		} else if (!programacao.equals(other.programacao))
			return false;
		if (titulo == null) {
			if (other.titulo != null)
				return false;
		} else if (!titulo.equals(other.titulo))
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		return true;
	}
	
}
