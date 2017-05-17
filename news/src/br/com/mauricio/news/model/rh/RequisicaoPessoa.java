package br.com.mauricio.news.model.rh;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PersistenceUnit;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Type;

import br.com.mauricio.news.model.CCusto;
import br.com.mauricio.news.model.Login;
import br.com.mauricio.news.model.rh.reqpess.ReqFreq;
import br.com.mauricio.news.model.rh.reqpess.ReqIdade;
import br.com.mauricio.news.model.rh.reqpess.RexExp;
import br.com.mauricio.news.model.rh.reqpess.Sexo;

/**
*
* @author MAURICIO CRUZ
*/
@PersistenceUnit(unitName="news")
@Entity(name="reqpes")
public class RequisicaoPessoa  implements Serializable{

	private static final long serialVersionUID = 1L;
	@GeneratedValue
	@Id
	private Integer id;
	@OneToOne
	@JoinColumn(name="reqges_id")
	private Login reqges;
	@OneToOne
	@JoinColumn(name="reqccu_id")
	private CCusto reqccu;	
	@Column(length=100)
	private String reqloc;
	@Column(length=100)
	private String reqhor;
	@Column(nullable = false,columnDefinition="bit default 0")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private Boolean horext=false;	
	@Enumerated(EnumType.ORDINAL)
	private ReqFreq reqfreq;	
	@Column(length=1000)
	private String reqatv; //atividades	
	@Column(length=1000)
	private String reqmdc; //maior dificuldade do cargo	
	@Enumerated(EnumType.ORDINAL)
	private Sexo sexo;	
	@Enumerated(EnumType.ORDINAL)
	private ReqIdade reqidade;	
	private Integer idaenta;
	private Integer idaentb;
	private Integer idainf;
	private Integer idasup;
	@Column(length=500)
	private String formin; 	
	@Column(length=500)
	private String fordes; 
	@Column(length=500)
	private String unifor; 		
	@Column(length=50)
	private String anoper; 		
	@Enumerated(EnumType.ORDINAL)
	private RexExp reqexp;		
	@Column(length=500)
	private String expout;
	
	@OneToMany(cascade=CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)	
	private List<RequisicaoConhecimento> conhecimentos = new ArrayList<RequisicaoConhecimento>();
	
	@Column(length=500)
	private String reqobs;	
	
	@Column(length=1000)
	private String fatess;		
	@Column(length=1000)
	private String fatdes;	
	@Column(length=1000)
	private String fatind;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Login getReqges() {
		return reqges;
	}
	public void setReqges(Login reqges) {
		this.reqges = reqges;
	}
	public CCusto getReqccu() {
		return reqccu;
	}
	public void setReqccu(CCusto reqccu) {
		this.reqccu = reqccu;
	}
	public String getReqloc() {
		return reqloc;
	}
	public void setReqloc(String reqloc) {
		this.reqloc = reqloc;
	}
	public String getReqhor() {
		return reqhor;
	}
	public void setReqhor(String reqhor) {
		this.reqhor = reqhor;
	}
	public Boolean getHorext() {
		return horext;
	}
	public void setHorext(Boolean horext) {
		this.horext = horext;
	}
	public ReqFreq getReqfreq() {
		return reqfreq;
	}
	public void setReqfreq(ReqFreq reqfreq) {
		this.reqfreq = reqfreq;
	}
	public String getReqatv() {
		return reqatv;
	}
	public void setReqatv(String reqatv) {
		this.reqatv = reqatv;
	}
	public String getReqmdc() {
		return reqmdc;
	}
	public void setReqmdc(String reqmdc) {
		this.reqmdc = reqmdc;
	}
	public Sexo getSexo() {
		return sexo;
	}
	public void setSexo(Sexo sexo) {
		this.sexo = sexo;
	}
	public ReqIdade getReqidade() {
		return reqidade;
	}
	public void setReqidade(ReqIdade reqidade) {
		this.reqidade = reqidade;
	}
	public Integer getIdaenta() {
		return idaenta;
	}
	public void setIdaenta(Integer idaenta) {
		this.idaenta = idaenta;
	}
	public Integer getIdaentb() {
		return idaentb;
	}
	public void setIdaentb(Integer idaentb) {
		this.idaentb = idaentb;
	}
	public Integer getIdainf() {
		return idainf;
	}
	public void setIdainf(Integer idainf) {
		this.idainf = idainf;
	}
	public Integer getIdasup() {
		return idasup;
	}
	public void setIdasup(Integer idasup) {
		this.idasup = idasup;
	}
	public String getFormin() {
		return formin;
	}
	public void setFormin(String formin) {
		this.formin = formin;
	}
	public String getFordes() {
		return fordes;
	}
	public void setFordes(String fordes) {
		this.fordes = fordes;
	}
	public String getUnifor() {
		return unifor;
	}
	public void setUnifor(String unifor) {
		this.unifor = unifor;
	}
	public String getAnoper() {
		return anoper;
	}
	public void setAnoper(String anoper) {
		this.anoper = anoper;
	}
	public RexExp getReqexp() {
		return reqexp;
	}
	public void setReqexp(RexExp reqexp) {
		this.reqexp = reqexp;
	}
	public String getExpout() {
		return expout;
	}
	public void setExpout(String expout) {
		this.expout = expout;
	}
	public List<RequisicaoConhecimento> getConhecimentos() {
		return conhecimentos;
	}
	public void setConhecimentos(List<RequisicaoConhecimento> conhecimentos) {
		this.conhecimentos = conhecimentos;
	}
	public String getReqobs() {
		return reqobs;
	}
	public void setReqobs(String reqobs) {
		this.reqobs = reqobs;
	}
	public String getFatess() {
		return fatess;
	}
	public void setFatess(String fatess) {
		this.fatess = fatess;
	}
	public String getFatdes() {
		return fatdes;
	}
	public void setFatdes(String fatdes) {
		this.fatdes = fatdes;
	}
	public String getFatind() {
		return fatind;
	}
	public void setFatind(String fatind) {
		this.fatind = fatind;
	}		
	

}
