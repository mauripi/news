package br.com.mauricio.news.mb.ti;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.FileUploadEvent;

import br.com.mauricio.news.ln.ti.ConsumoEmbratelLN;
import br.com.mauricio.news.model.ti.RateioFinalEmbratel;
import br.com.mauricio.news.util.SaveFile;
/**
*
* @author MAURICIO CRUZ
*/
@ManagedBean(name="consumoembratelMB")
@ViewScoped
public class ConsumoEmbratelBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private String arquivo;
	private String fatura;
	private InputStream is;
	private ConsumoEmbratelLN ln;
	private static final String CAMINHO_PARA_SALVAR_ARQUIVO_IMPORTADO = "C:\\Windows\\Temp\\ti\\"; 
	private List<RateioFinalEmbratel> rateios = new ArrayList<RateioFinalEmbratel>();
	private List<RateioFinalEmbratel> rateiosMtz = new ArrayList<RateioFinalEmbratel>();
	private List<RateioFinalEmbratel> rateiosAqa = new ArrayList<RateioFinalEmbratel>();
	private List<RateioFinalEmbratel> rateiosZft = new ArrayList<RateioFinalEmbratel>();
	private BigDecimal totalMtz;
	private BigDecimal totalAqa;
	private BigDecimal totalZft;
	
	@PostConstruct
	public void init(){
		arquivo="";
		fatura="";		
	}

	public void importarFatura(){
		ln = new ConsumoEmbratelLN();
		if(validaCampos()){
			if(ln.faturaJaExiste(fatura))
				mensagens("Fatura já foi importada.");				
			else	
				mensagens(ln.carregaArquivo(CAMINHO_PARA_SALVAR_ARQUIVO_IMPORTADO+arquivo, fatura));			
		}
	}
	
	public void cancelar(){
		arquivo="";
		fatura="";	
		is=null;
	}
	
	private boolean validaCampos() {
		if(arquivo.length() < 1){
			mensagens("Selecione um arquivo.");
			return false;
		}
		if(fatura.length() < 1 ){
			mensagens("Informe a fatura.");
			return false;
		}
		return true;
	}

	public void handleFileUpload(FileUploadEvent event) {
        arquivo =  event.getFile().getFileName();
    	try {
			is=event.getFile().getInputstream();
			SaveFile.recebeArquivoUpload(is,arquivo, CAMINHO_PARA_SALVAR_ARQUIVO_IMPORTADO);
		} catch (IOException e) {
			mensagens("Ocorreu erro ao receber o arquivo.");
			System.out.println("ConsumoEmbratelBean().handleFileUpload " + e.getLocalizedMessage());
			e.printStackTrace();
		} 
    }

	public void listar(){
		ln = new ConsumoEmbratelLN();
		rateios = ln.rateioFinal(fatura);
		rateiosMtz = rateios.stream().filter(x -> (x.getFilial().equals(1) && x.getServico().contains("VPE"))).collect(Collectors.toList());
		rateiosAqa = rateios.stream().filter(x -> (x.getFilial().equals(2) && x.getServico().contains("VPE"))).collect(Collectors.toList());
		rateiosZft = rateios.stream().filter(x -> (x.getFilial().equals(1) && x.getServico().contains("ZFT"))).collect(Collectors.toList());	
		totalMtz = totaliza(rateiosMtz);
		totalAqa = totaliza(rateiosAqa);
		totalZft = totaliza(rateiosZft);		
	}

	private BigDecimal totaliza(List<RateioFinalEmbratel> list) {
		return list.stream().map(RateioFinalEmbratel::getValor).reduce(BigDecimal.ZERO, BigDecimal::add);
	}
	
	public void mensagens(String msg){
        FacesContext context = FacesContext.getCurrentInstance();  	          
        context.addMessage(null, new FacesMessage("",msg));  		
	}
	//--------------------------GETTERS E SETTERS ----------------------------------

	public String getArquivo() {
		return arquivo;
	}

	public void setArquivo(String arquivo) {
		this.arquivo = arquivo;
	}

	public String getFatura() {
		return fatura;
	}

	public void setFatura(String fatura) {
		this.fatura = fatura;
	}

	public InputStream getIs() {
		return is;
	}

	public void setIs(InputStream is) {
		this.is = is;
	}

	public List<RateioFinalEmbratel> getRateiosMtz() {
		return rateiosMtz;
	}

	public void setRateiosMtz(List<RateioFinalEmbratel> rateiosMtz) {
		this.rateiosMtz = rateiosMtz;
	}

	public List<RateioFinalEmbratel> getRateiosAqa() {
		return rateiosAqa;
	}

	public void setRateiosAqa(List<RateioFinalEmbratel> rateiosAqa) {
		this.rateiosAqa = rateiosAqa;
	}

	public List<RateioFinalEmbratel> getRateiosZft() {
		return rateiosZft;
	}

	public void setRateiosZft(List<RateioFinalEmbratel> rateiosZft) {
		this.rateiosZft = rateiosZft;
	}

	public List<RateioFinalEmbratel> getRateios() {
		return rateios;
	}

	public void setRateios(List<RateioFinalEmbratel> rateios) {
		this.rateios = rateios;
	}

	public BigDecimal getTotalMtz() {
		return totalMtz;
	}

	public void setTotalMtz(BigDecimal totalMtz) {
		this.totalMtz = totalMtz;
	}

	public BigDecimal getTotalAqa() {
		return totalAqa;
	}

	public void setTotalAqa(BigDecimal totalAqa) {
		this.totalAqa = totalAqa;
	}

	public BigDecimal getTotalZft() {
		return totalZft;
	}

	public void setTotalZft(BigDecimal totalZft) {
		this.totalZft = totalZft;
	}

}
