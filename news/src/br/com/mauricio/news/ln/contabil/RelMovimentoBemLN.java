package br.com.mauricio.news.ln.contabil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import br.com.mauricio.news.model.contabil.MovimentoBem;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;


public class RelMovimentoBemLN implements Serializable{

	private static final long serialVersionUID = 6474755493685831412L;


	@SuppressWarnings("unchecked")
	public void imprimir(MovimentoBem m) throws Exception {
		String CAMINHO_PARA_SALVAR_ARQUIVO = "C:\\windows\\temp\\contabil\\";
        File folder = new File(CAMINHO_PARA_SALVAR_ARQUIVO);
        if (!folder.exists())
            folder.mkdirs();
        
		List<MovimentoBem> list = new ArrayList<MovimentoBem>();
		list.add(m);
		
		String nomeRelatorio = "movimentobem.jasper";	
	    JRBeanCollectionDataSource jrds = new JRBeanCollectionDataSource(list);
	    @SuppressWarnings("rawtypes")
	    HashMap param = new HashMap();
	    FacesContext fc = FacesContext.getCurrentInstance();
	    ExternalContext ec = fc.getExternalContext();
		   
	    File reportFile = new File(ec.getRealPath("/sistema/relatorio/" +nomeRelatorio));
	   
	    param.put("id", m.getId()); 
	    param.put("localorigem", m.getLocalOrigem()); 
	    param.put("localdestino", m.getLocalDestino()); 
	    param.put("nomeProprietarioDestino", m.getNomeProprietarioDestino()); 	
	    param.put("cnpjProprietarioDestino", m.getCnpjProprietarioDestino()); 		    		    	    
	    param.put("nomeTransportadora", m.getNomeTransportadora()); 
	    param.put("cnpjTransportadora", m.getCnpjTransportadora());
	    param.put("nomeResponsavelRecepcao", m.getNomeResponsavelRecepcao());
	    param.put("cpfResponsavelRecepcao", m.getCpfResponsavelRecepcao());
	    param.put("rgResponsavelRecepcao", m.getRgResponsavelRecepcao());
	    param.put("dataemissao", m.getDataemissao());
	    param.put("datasaida", m.getDatasaida());
	    
	    if(m.getEspacoLocado()==0) param.put("espacoLocado", "Não");
	    else param.put("espacoLocado", "Sim");
	    
	    if(m.getComFrete()==0) param.put("comFrete", "Não");
	    else param.put("comFrete", "Sim");
	    
	    param.put("motivo", m.getMotivo());
	    param.put("solicitante", m.getSolicitante().getNome());

	    param.put("REPORT_LOCALE", new Locale("pt", "BR"));
	    param.put("logo", ec.getRealPath("/sistema/img/logo.jpg"));

	    HttpServletResponse httpServletResponse=(HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();  	    
	    httpServletResponse.addHeader("Content-disposition", "inline; filename=movimentobem_"+m.getId()+".pdf");  
	    ServletOutputStream servletOutputStream=httpServletResponse.getOutputStream();  
	    JasperPrint jasperPrint = JasperFillManager.fillReport(reportFile.getPath(),param, jrds);
		JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream); 
		FacesContext.getCurrentInstance().responseComplete();  
	
	}
	
	
	@SuppressWarnings({ "unchecked" })
	public void geraRelatorio(MovimentoBem m, int codigo) throws IOException{
		
		String CAMINHO_PARA_SALVAR_ARQUIVO = "C:\\windows\\temp\\contabil\\";
        File folder = new File(CAMINHO_PARA_SALVAR_ARQUIVO);
        if (!folder.exists())
            folder.mkdirs();
        
		List<MovimentoBem> list = new ArrayList<MovimentoBem>();
		list.add(m);
		
		String nomeRelatorio = "movimentobem.jasper";	
	    JRBeanCollectionDataSource jrds = new JRBeanCollectionDataSource(list);
	    @SuppressWarnings("rawtypes")
	    HashMap param = new HashMap();
	    FacesContext fc = FacesContext.getCurrentInstance();
	    ExternalContext ec = fc.getExternalContext();
		   
	    File reportFile = new File(ec.getRealPath("/sistema/relatorio/" +nomeRelatorio));
	   
	    param.put("id", m.getId()); 
	    param.put("localorigem", m.getLocalOrigem()); 
	    param.put("localdestino", m.getLocalDestino()); 
	    param.put("nomeProprietarioDestino", m.getNomeProprietarioDestino()); 	
	    param.put("cnpjProprietarioDestino", m.getCnpjProprietarioDestino()); 		    		    	    
	    param.put("nomeTransportadora", m.getNomeTransportadora()); 
	    param.put("cnpjTransportadora", m.getCnpjTransportadora());
	    param.put("nomeResponsavelRecepcao", m.getNomeResponsavelRecepcao());
	    param.put("cpfResponsavelRecepcao", m.getCpfResponsavelRecepcao());
	    param.put("rgResponsavelRecepcao", m.getRgResponsavelRecepcao());
	    param.put("dataemissao", m.getDataemissao());
	    param.put("datasaida", m.getDatasaida());
	    
	    if(m.getEspacoLocado()==0) param.put("espacoLocado", "Não");
	    else param.put("espacoLocado", "Sim");
	    
	    if(m.getComFrete()==0) param.put("comFrete", "Não");
	    else param.put("comFrete", "Sim");
	    
	    param.put("motivo", m.getMotivo());
	    param.put("solicitante", m.getSolicitante().getNome());

	    param.put("REPORT_LOCALE", new Locale("pt", "BR"));
	    param.put("logo", ec.getRealPath("/sistema/img/logo.jpg"));

	    OutputStream os = new FileOutputStream(CAMINHO_PARA_SALVAR_ARQUIVO+"movimentobem_"+m.getId()+".pdf");
	    JasperPrint jasperPrint;    
    
		try {
			jasperPrint = JasperFillManager.fillReport(reportFile.getPath(),param, jrds);
			JasperExportManager.exportReportToPdfStream(jasperPrint, os); 
		} catch (JRException e) {
			os.close();
			e.printStackTrace();
		}finally{
		    if (os != null) {
		        try {
		            os.close();
		        } catch (IOException ex) {}
		    }
		}

 	}

	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
