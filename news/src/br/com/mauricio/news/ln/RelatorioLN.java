package br.com.mauricio.news.ln;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import br.com.mauricio.news.dao.rh.BaseDao;
import br.com.mauricio.news.ln.rh.HoleriteLN;
import br.com.mauricio.news.model.financeiro.Despesa;
import br.com.mauricio.news.model.financeiro.PrestacaoConta;
import br.com.mauricio.news.model.rh.Base;
import br.com.mauricio.news.model.rh.Evento;
import br.com.mauricio.news.model.rh.Holerite;
import br.com.mauricio.news.model.Login;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;


public class RelatorioLN implements Serializable{

	private static final long serialVersionUID = 6474755493685831412L;
	private Double totalDespesa = 0.0;
	private Double totalRestituir = 0.0;	
	private Double totalReceber = 0.0;


	@SuppressWarnings({ "unchecked" })
	public void geraRelatorioPedido(List<Holerite> hlrs,Double totalLiquido,Base base,
				Double totalProvento,Double totalDesconto,Login usuario) throws IOException, JRException{
		List<Evento> eventos = new ArrayList<Evento>();
		Evento e = new Evento(hlrs);
		eventos.add(e);
		HoleriteLN hLn = new HoleriteLN();
	    Double vFGTS = hLn.calculaFGTS(hlrs,base,usuario);
				
		String nomeRelatorio = "holerite.jasper";	
	    JRBeanCollectionDataSource jrds = new JRBeanCollectionDataSource(eventos);
	    @SuppressWarnings("rawtypes")
	    HashMap param = new HashMap();
	    FacesContext fc = FacesContext.getCurrentInstance();
	    ExternalContext ec = fc.getExternalContext();
		   
	    File reportFile = new File(ec.getRealPath("/sistema/relatorio/" +nomeRelatorio));
	   
	    param.put("totalLiquido", totalLiquido); 
	    param.put("totalDesconto", totalDesconto); 
	    param.put("totalProvento", totalProvento); 
	    param.put("chapa", usuario.getChapa()); 	
	    param.put("nome", usuario.getNome()); 		    		    	    
	    param.put("cbo", base.getCbo()); 
	    param.put("funcao", base.getFuncao());
	    param.put("bSalario", base.getSalarioBase());
	    param.put("bINSS", base.getBaseINSS());
	    param.put("bIRRF", base.getBaseIRRF());
	    param.put("bFGTS", base.getBaseFGTS());	 
	    param.put("vFGTS", vFGTS);
	    param.put("mes", base.getMes());	    
	    param.put("ano", base.getAno());
	    param.put("endereco", usuario.getFilial().getEndereco());	    
	    param.put("cnpj", usuario.getFilial().getCnpj());
	    param.put("mensagem",getMensagem());
	    // para resolver o problema da moeda
	    param.put("REPORT_LOCALE", new Locale("pt", "BR"));
	    param.put("logo", ec.getRealPath("/sistema/img/logo.jpg"));
	    
	    HttpServletResponse httpServletResponse=(HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();  	    
	    httpServletResponse.addHeader("Content-disposition", "inline; filename=holerite_"+base.getMes()+"_"+base.getAno()+".pdf");  
	    ServletOutputStream servletOutputStream=httpServletResponse.getOutputStream();  
	    JasperPrint jasperPrint = JasperFillManager.fillReport(reportFile.getPath(),param, jrds); 
	    JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);  
	    FacesContext.getCurrentInstance().responseComplete();  
	}

	@SuppressWarnings("unchecked")
	public void geraRelatorioDespesa(PrestacaoConta p) throws IOException, JRException{

    	totalDespesa =0.0;
		for(Despesa d:p.getDespesas())
			totalDespesa =totalDespesa + d.getValor();
		if(p.getValoradiantado()>=totalDespesa){
			totalRestituir =p.getValoradiantado()-totalDespesa;
			totalReceber=0.0;
		}else{
			totalRestituir =0.0;
			totalReceber=totalDespesa-p.getValoradiantado();			
		}
		BaseDao bdao = new BaseDao();
		String funcao = bdao.getUltimaFuncao(p.getColaborador());
		List<PrestacaoConta> ps = new ArrayList<PrestacaoConta>();
		ps.add(p);
		String nomeRelatorio = "despesa.jasper";	
	    JRBeanCollectionDataSource jrds = new JRBeanCollectionDataSource(ps,false);
	    @SuppressWarnings("rawtypes")
	    HashMap param = new HashMap();
	    FacesContext fc = FacesContext.getCurrentInstance();
	    ExternalContext ec = fc.getExternalContext();
		   
	    File reportFile = new File(ec.getRealPath("/sistema/relatorio/" +nomeRelatorio));

	    param.put("nome", p.getColaborador().getNome());
	    param.put("setor",p.getColaborador().getCusto().getNome());	    
	    param.put("cargo", funcao);
	    param.put("motivo",p.getMotivodespesa());
	    
	    param.put("dtadianta",  p.getDataadiantamento());	    
	    param.put("vladianta",  p.getValoradiantado());	    
	    param.put("vldespesa",  totalDespesa);	    
	    param.put("vlrestituir",  totalRestituir);	    
	    param.put("vlreceber",  totalReceber);	    
	    
	    
	    // para resolver o problema da moeda
	    param.put("REPORT_LOCALE", new Locale("pt", "BR"));
	    param.put("logo", ec.getRealPath("/sistema/img/logo.jpg"));
	    
	    HttpServletResponse httpServletResponse=(HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();  	    
	    httpServletResponse.addHeader("Content-disposition", "inline; filename=despesa_"+p.getId()+".pdf");  
	    ServletOutputStream servletOutputStream=httpServletResponse.getOutputStream();  
	    JasperPrint jasperPrint = JasperFillManager.fillReport(reportFile.getPath(),param, jrds); 
	    JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);  
	    FacesContext.getCurrentInstance().responseComplete();  
	}

	private String getMensagem(){
	    FacesContext fc = FacesContext.getCurrentInstance();
	    ExternalContext ec = fc.getExternalContext();
    	Properties prop = new Properties();
   	 
    	try {
            //load a properties file
    		prop.load(new FileInputStream(ec.getRealPath("/sistema/config.properties")));
            //get the property value and print it out
             return prop.getProperty("mensagem");

    	} catch (IOException ex) {
    		return "";
    	}
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Double getTotalDespesa() {
		return totalDespesa;
	}

	public void setTotalDespesa(Double totalDespesa) {
		this.totalDespesa = totalDespesa;
	}

	public Double getTotalRestituir() {
		return totalRestituir;
	}

	public void setTotalRestituir(Double totalRestituir) {
		this.totalRestituir = totalRestituir;
	}

	public Double getTotalReceber() {
		return totalReceber;
	}

	public void setTotalReceber(Double totalReceber) {
		this.totalReceber = totalReceber;
	}

}
