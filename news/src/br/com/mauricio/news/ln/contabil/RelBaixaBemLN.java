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

import br.com.mauricio.news.model.contabil.BaixaBem;
import br.com.mauricio.news.model.contabil.ItemBaixaBem;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;


public class RelBaixaBemLN implements Serializable{

	private static final long serialVersionUID = 6474755493685831412L;


	@SuppressWarnings({ "unchecked" })
	public void geraRelatorioBaixa(BaixaBem b) throws IOException{
		
		String CAMINHO_PARA_SALVAR_ARQUIVO = "C:\\windows\\temp\\contabil\\";

		List<BaixaBem> list = new ArrayList<BaixaBem>();
		list.add(b);
	
		String nomeRelatorio = "baixabem.jasper";	
	    JRBeanCollectionDataSource jrds = new JRBeanCollectionDataSource(list);
	    @SuppressWarnings("rawtypes")
	    HashMap param = new HashMap();
	    FacesContext fc = FacesContext.getCurrentInstance();
	    ExternalContext ec = fc.getExternalContext();
		   
	    File reportFile = new File(ec.getRealPath("/sistema/relatorio/" +nomeRelatorio));

		Double totalCompra = new Double("0.0");
		Double totalResidual = new Double("0.0");	    
	    for (ItemBaixaBem i:b.getItens()){
			totalCompra = totalCompra+i.getVlraquisicao();
			totalResidual = totalResidual+i.getVlrresidual();	    	
	    }
	    
	    param.put("id", b.getId()); 
	    param.put("filial", b.getFilial().getNome()); 
	    param.put("ccusto", b.getCcusto().getNome()); 
	    param.put("motivo", b.getTipoBaixa().name()); 	
	    param.put("solicitante", b.getSolicitante().getNome()); 		    		    	    
	    param.put("justificativa", b.getJustificativa()); 
	    param.put("dtvenda", b.getDatavenda());
	    param.put("dtemissao", b.getDataemissao());
	    param.put("vlrvenda", b.getVlrvenda());
	    param.put("vlrtotalcompra", totalCompra);
	    param.put("vlrtotalresidual", totalResidual);

	    // para resolver o problema da moeda
	    param.put("REPORT_LOCALE", new Locale("pt", "BR"));
	    param.put("logo", ec.getRealPath("/sistema/img/logo.jpg"));
	    
	    OutputStream os = new FileOutputStream(CAMINHO_PARA_SALVAR_ARQUIVO+"baixabem"+b.getId()+".pdf");

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

	@SuppressWarnings({ "unchecked" })
	public void imprimir(BaixaBem b) throws IOException, JRException{

		List<BaixaBem> list = new ArrayList<BaixaBem>();
		list.add(b);
	
		String nomeRelatorio = "baixabem.jasper";	
	    JRBeanCollectionDataSource jrds = new JRBeanCollectionDataSource(list);
	    @SuppressWarnings("rawtypes")
	    HashMap param = new HashMap();
	    FacesContext fc = FacesContext.getCurrentInstance();
	    ExternalContext ec = fc.getExternalContext();
		   
	    File reportFile = new File(ec.getRealPath("/sistema/relatorio/" +nomeRelatorio));

		Double totalCompra = new Double("0.0");
		Double totalResidual = new Double("0.0");	    
	    for (ItemBaixaBem i:b.getItens()){
			totalCompra = totalCompra+i.getVlraquisicao();
			totalResidual = totalResidual+i.getVlrresidual();	    	
	    }
	    
	    param.put("id", b.getId()); 
	    param.put("filial", b.getFilial().getNome()); 
	    param.put("ccusto", b.getCcusto().getNome()); 
	    param.put("motivo", b.getTipoBaixa().name()); 	
	    param.put("solicitante", b.getSolicitante().getNome()); 		    		    	    
	    param.put("justificativa", b.getJustificativa()); 
	    param.put("dtvenda", b.getDatavenda());
	    param.put("dtemissao", b.getDataemissao());
	    param.put("vlrvenda", b.getVlrvenda());
	    param.put("vlrtotalcompra", totalCompra);
	    param.put("vlrtotalresidual", totalResidual);

	    // para resolver o problema da moeda
	    param.put("REPORT_LOCALE", new Locale("pt", "BR"));
	    param.put("logo", ec.getRealPath("/sistema/img/logo.jpg"));
 
	    HttpServletResponse httpServletResponse=(HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();  	    
	    httpServletResponse.addHeader("Content-disposition", "inline; filename=baixadebem"+b.getId()+".pdf");  
	    ServletOutputStream servletOutputStream=httpServletResponse.getOutputStream();  
	    JasperPrint jasperPrint = JasperFillManager.fillReport(reportFile.getPath(),param, jrds); 
	    JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);  
	    FacesContext.getCurrentInstance().responseComplete();  
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
