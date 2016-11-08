package br.com.mauricio.news.model.engenharia;

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

@Entity(name="controlesinal")
@PersistenceUnit(unitName="news")
public class ControleSinal  implements Serializable{

	private static final long serialVersionUID = 1L;
	@GeneratedValue
	@Id
	private Integer id;	
	@Column(length=150)
	private String tecnico;	
	@Column(length=254)
	private String problema;	
	@Temporal(javax.persistence.TemporalType.DATE)
	private Date dataproblema;
	@Temporal(javax.persistence.TemporalType.DATE)
	private Date datasolucao;	
	@Temporal(javax.persistence.TemporalType.DATE)
	private Date proximapreventiva;		
	@Column(length=1500)
	private String solucao;
	@Column(length=1500)
	private String pendencia;	
	@Column(length=15)
	private String tipomanutencao;
	@Column(length=30)
	private String tipoequipamento;
	private Double valoratendimento;
	private Double valorpeca;
	@Column(length=3)
	private String comprapeca;
	@OneToOne
	@JoinColumn(name="posto_id")	
	private Posto posto;
	@Column(length=100)
	private String arquivo;
	@Column(length=250)
	private String patrimonio;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTecnico() {
		return tecnico;
	}
	public void setTecnico(String tecnico) {
		this.tecnico = tecnico;
	}
	public String getProblema() {
		return problema;
	}
	public void setProblema(String problema) {
		this.problema = problema;
	}
	public Date getDataproblema() {
		return dataproblema;
	}
	public void setDataproblema(Date dataproblema) {
		this.dataproblema = dataproblema;
	}
	public Date getDatasolucao() {
		return datasolucao;
	}
	public void setDatasolucao(Date datasolucao) {
		this.datasolucao = datasolucao;
	}
	public Date getProximapreventiva() {
		return proximapreventiva;
	}
	public void setProximapreventiva(Date proximapreventiva) {
		this.proximapreventiva = proximapreventiva;
	}
	public String getSolucao() {
		return solucao;
	}
	public void setSolucao(String solucao) {
		this.solucao = solucao;
	}
	public String getPendencia() {
		return pendencia;
	}
	public void setPendencia(String pendencia) {
		this.pendencia = pendencia;
	}
	public String getTipomanutencao() {
		return tipomanutencao;
	}
	public void setTipomanutencao(String tipomanutencao) {
		this.tipomanutencao = tipomanutencao;
	}
	public String getTipoequipamento() {
		return tipoequipamento;
	}
	public void setTipoequipamento(String tipoequipamento) {
		this.tipoequipamento = tipoequipamento;
	}
	public Double getValoratendimento() {
		return valoratendimento;
	}
	public void setValoratendimento(Double valoratendimento) {
		this.valoratendimento = valoratendimento;
	}
	public Double getValorpeca() {
		return valorpeca;
	}
	public void setValorpeca(Double valorpeca) {
		this.valorpeca = valorpeca;
	}
	public String getComprapeca() {
		return comprapeca;
	}
	public void setComprapeca(String comprapeca) {
		this.comprapeca = comprapeca;
	}
	public Posto getPosto() {
		return posto;
	}
	public void setPosto(Posto posto) {
		this.posto = posto;
	}
	public String getArquivo() {
		return arquivo;
	}
	public void setArquivo(String arquivo) {
		this.arquivo = arquivo;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getPatrimonio() {
		return patrimonio;
	}
	public void setPatrimonio(String patrimonio) {
		this.patrimonio = patrimonio;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((arquivo == null) ? 0 : arquivo.hashCode());
		result = prime * result
				+ ((comprapeca == null) ? 0 : comprapeca.hashCode());
		result = prime * result
				+ ((dataproblema == null) ? 0 : dataproblema.hashCode());
		result = prime * result
				+ ((datasolucao == null) ? 0 : datasolucao.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((patrimonio == null) ? 0 : patrimonio.hashCode());
		result = prime * result
				+ ((pendencia == null) ? 0 : pendencia.hashCode());
		result = prime * result + ((posto == null) ? 0 : posto.hashCode());
		result = prime * result
				+ ((problema == null) ? 0 : problema.hashCode());
		result = prime
				* result
				+ ((proximapreventiva == null) ? 0 : proximapreventiva
						.hashCode());
		result = prime * result + ((solucao == null) ? 0 : solucao.hashCode());
		result = prime * result + ((tecnico == null) ? 0 : tecnico.hashCode());
		result = prime * result
				+ ((tipoequipamento == null) ? 0 : tipoequipamento.hashCode());
		result = prime * result
				+ ((tipomanutencao == null) ? 0 : tipomanutencao.hashCode());
		result = prime
				* result
				+ ((valoratendimento == null) ? 0 : valoratendimento.hashCode());
		result = prime * result
				+ ((valorpeca == null) ? 0 : valorpeca.hashCode());
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
		ControleSinal other = (ControleSinal) obj;
		if (arquivo == null) {
			if (other.arquivo != null)
				return false;
		} else if (!arquivo.equals(other.arquivo))
			return false;
		if (comprapeca == null) {
			if (other.comprapeca != null)
				return false;
		} else if (!comprapeca.equals(other.comprapeca))
			return false;
		if (dataproblema == null) {
			if (other.dataproblema != null)
				return false;
		} else if (!dataproblema.equals(other.dataproblema))
			return false;
		if (datasolucao == null) {
			if (other.datasolucao != null)
				return false;
		} else if (!datasolucao.equals(other.datasolucao))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (patrimonio == null) {
			if (other.patrimonio != null)
				return false;
		} else if (!patrimonio.equals(other.patrimonio))
			return false;
		if (pendencia == null) {
			if (other.pendencia != null)
				return false;
		} else if (!pendencia.equals(other.pendencia))
			return false;
		if (posto == null) {
			if (other.posto != null)
				return false;
		} else if (!posto.equals(other.posto))
			return false;
		if (problema == null) {
			if (other.problema != null)
				return false;
		} else if (!problema.equals(other.problema))
			return false;
		if (proximapreventiva == null) {
			if (other.proximapreventiva != null)
				return false;
		} else if (!proximapreventiva.equals(other.proximapreventiva))
			return false;
		if (solucao == null) {
			if (other.solucao != null)
				return false;
		} else if (!solucao.equals(other.solucao))
			return false;
		if (tecnico == null) {
			if (other.tecnico != null)
				return false;
		} else if (!tecnico.equals(other.tecnico))
			return false;
		if (tipoequipamento == null) {
			if (other.tipoequipamento != null)
				return false;
		} else if (!tipoequipamento.equals(other.tipoequipamento))
			return false;
		if (tipomanutencao == null) {
			if (other.tipomanutencao != null)
				return false;
		} else if (!tipomanutencao.equals(other.tipomanutencao))
			return false;
		if (valoratendimento == null) {
			if (other.valoratendimento != null)
				return false;
		} else if (!valoratendimento.equals(other.valoratendimento))
			return false;
		if (valorpeca == null) {
			if (other.valorpeca != null)
				return false;
		} else if (!valorpeca.equals(other.valorpeca))
			return false;
		return true;
	}

}
