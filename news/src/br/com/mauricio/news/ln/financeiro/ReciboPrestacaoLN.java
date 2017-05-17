package br.com.mauricio.news.ln.financeiro;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.LocalDate;

import br.com.mauricio.news.model.financeiro.PrestacaoConta;
import br.com.mauricio.news.util.DateUtil;
import br.com.mauricio.news.util.FormataNumero;
import br.com.mauricio.news.util.ValorPorEstenso;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class ReciboPrestacaoLN implements Serializable{

	private static final long serialVersionUID = 1L;

	
	@SuppressWarnings("unchecked")
	public void gerarRecibo(PrestacaoConta p, Date dataRecibo, Double valor){
		List<PrestacaoConta> ps = new ArrayList<PrestacaoConta>();
		ps.add(p);
		String nomeRelatorio = "reciboprestacao.jasper";	
	    JRBeanCollectionDataSource jrds = new JRBeanCollectionDataSource(ps,false);
	    @SuppressWarnings("rawtypes")
	    HashMap param = new HashMap();
	    FacesContext fc = FacesContext.getCurrentInstance();
	    ExternalContext ec = fc.getExternalContext();		
	    try{
	    	LocalDate dataref = new LocalDate(dataRecibo);
			String data = "São Paulo, "+dataref.getDayOfMonth()+" de " + DateUtil.mesPorExtenso(dataref.getMonthOfYear()) + " de " + dataref.getYear() + ".";
		    File reportFile = new File(ec.getRealPath("/sistema/relatorio/" +nomeRelatorio));	
		    param.put("datarecibo",  data);	
		    param.put("valor", "R$ " +FormataNumero.doubleTOMoedaReal(valor));
		    param.put("valorextenso",  ValorPorEstenso.converter(valor));
		    param.put("REPORT_LOCALE", new Locale("pt", "BR"));
		    param.put("logo", ec.getRealPath("/sistema/img/logo.jpg"));	    
		    HttpServletResponse httpServletResponse=(HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();  	    
		    httpServletResponse.addHeader("Content-disposition", "inline; filename=despesa_"+p.getId()+".pdf");  
		    ServletOutputStream servletOutputStream=httpServletResponse.getOutputStream();  
		    JasperPrint jasperPrint = JasperFillManager.fillReport(reportFile.getPath(),param, jrds); 
		    JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);  
		    FacesContext.getCurrentInstance().responseComplete();  		
	    }catch(Exception e){
	    	System.out.println(e.getLocalizedMessage());
	    }
	}
	
}
