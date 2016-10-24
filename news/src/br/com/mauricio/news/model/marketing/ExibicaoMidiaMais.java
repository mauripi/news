package br.com.mauricio.news.model.marketing;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PersistenceUnit;
import javax.persistence.Temporal;

/**
*
* @author MAURICIO CRUZ
*/
@Entity(name="exibicaomidiamais")
@PersistenceUnit(unitName="news")
public class ExibicaoMidiaMais  implements Serializable{

	private static final long serialVersionUID = 1L;
	@GeneratedValue
	@Id
	private Integer id;	
	@Column(length=100)
	private String cliente;
	@Column(length=150)
	private String titulo;
	@Column(length=150)
	private String programa;
	@Column(length=150)
	private String praca;	
	@Column(length=10)
	private String duracao;
	@Temporal(javax.persistence.TemporalType.DATE)
	private Date data;
	@Column(length=10)
	private String horario;
	@Column(length=30)
	private String situacao;
	private Double audiencia;
	@OneToOne
	@JoinColumn(name="programamidiamais_id")
	private ProgramaMidiaMais programamidiamais;

	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCliente() {
		return cliente;
	}
	public void setCliente(String cliente) {
		this.cliente = cliente;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getPrograma() {
		return programa;
	}
	public void setPrograma(String programa) {
		this.programa = programa;
	}
	public String getPraca() {
		return praca;
	}
	public void setPraca(String praca) {
		this.praca = praca;
	}
	public String getDuracao() {
		return duracao;
	}
	public void setDuracao(String duracao) {
		this.duracao = duracao;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public String getHorario() {
		return horario;
	}
	public void setHorario(String horario) {
		this.horario = horario;
	}
	public String getSituacao() {
		return situacao;
	}
	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}
	public Double getAudiencia() {
		return audiencia;
	}
	public void setAudiencia(Double audiencia) {
		this.audiencia = audiencia;
	}
	public ProgramaMidiaMais getProgramamidiamais() {
		return programamidiamais;
	}
	public void setProgramamidiamais(ProgramaMidiaMais programamidiamais) {
		this.programamidiamais = programamidiamais;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((audiencia == null) ? 0 : audiencia.hashCode());
		result = prime * result + ((cliente == null) ? 0 : cliente.hashCode());
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + ((duracao == null) ? 0 : duracao.hashCode());
		result = prime * result + ((horario == null) ? 0 : horario.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((praca == null) ? 0 : praca.hashCode());
		result = prime * result
				+ ((programa == null) ? 0 : programa.hashCode());
		result = prime
				* result
				+ ((programamidiamais == null) ? 0 : programamidiamais
						.hashCode());
		result = prime * result
				+ ((situacao == null) ? 0 : situacao.hashCode());
		result = prime * result + ((titulo == null) ? 0 : titulo.hashCode());
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
		ExibicaoMidiaMais other = (ExibicaoMidiaMais) obj;
		if (audiencia == null) {
			if (other.audiencia != null)
				return false;
		} else if (!audiencia.equals(other.audiencia))
			return false;
		if (cliente == null) {
			if (other.cliente != null)
				return false;
		} else if (!cliente.equals(other.cliente))
			return false;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		if (duracao == null) {
			if (other.duracao != null)
				return false;
		} else if (!duracao.equals(other.duracao))
			return false;
		if (horario == null) {
			if (other.horario != null)
				return false;
		} else if (!horario.equals(other.horario))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (praca == null) {
			if (other.praca != null)
				return false;
		} else if (!praca.equals(other.praca))
			return false;
		if (programa == null) {
			if (other.programa != null)
				return false;
		} else if (!programa.equals(other.programa))
			return false;
		if (programamidiamais == null) {
			if (other.programamidiamais != null)
				return false;
		} else if (!programamidiamais.equals(other.programamidiamais))
			return false;
		if (situacao == null) {
			if (other.situacao != null)
				return false;
		} else if (!situacao.equals(other.situacao))
			return false;
		if (titulo == null) {
			if (other.titulo != null)
				return false;
		} else if (!titulo.equals(other.titulo))
			return false;
		return true;
	}	
}
