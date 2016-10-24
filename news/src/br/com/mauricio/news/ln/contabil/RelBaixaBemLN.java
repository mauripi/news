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


		List<BaixaBem> list = montaListaBem(b);
	
		String nomeRelatorio = "baixabem.jasper";	
	    JRBeanCollectionDataSource jrds = new JRBeanCollectionDataSource(list);
	    @SuppressWarnings("rawtypes")
	    HashMap param = new HashMap();
	    FacesContext fc = FacesContext.getCurrentInstance();
	    ExternalContext ec = fc.getExternalContext();
		   
	    File reportFile = new File(ec.getRealPath("/sistema/relatorio/" +nomeRelatorio));
	   
	    param.put("id", b.getId()); 
	    param.put("filial", b.getFilial().getNome()); 
	    param.put("ccusto", b.getCcusto().getNome()); 
	    param.put("motivo", b.getTipoBaixa().name()); 	
	    param.put("solicitante", b.getSolicitante().getNome()); 		    		    	    
	    param.put("justificativa", b.getJustificativa()); 
	    param.put("dtvenda", b.getDatavenda());
	    param.put("dtemissao", b.getDataemissao());
	    param.put("vlrvenda", b.getVlrvenda());
	    param.put("list", list);

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

		List<BaixaBem> list = montaListaBem(b);
	
		String nomeRelatorio = "baixabem.jasper";	
	    JRBeanCollectionDataSource jrds = new JRBeanCollectionDataSource(list);
	    @SuppressWarnings("rawtypes")
	    HashMap param = new HashMap();
	    FacesContext fc = FacesContext.getCurrentInstance();
	    ExternalContext ec = fc.getExternalContext();
		   
	    File reportFile = new File(ec.getRealPath("/sistema/relatorio/" +nomeRelatorio));
	   
	    param.put("id", b.getId()); 
	    param.put("filial", b.getFilial().getNome()); 
	    param.put("ccusto", b.getCcusto().getNome()); 
	    param.put("motivo", b.getTipoBaixa().name()); 	
	    param.put("solicitante", b.getSolicitante().getNome()); 		    		    	    
	    param.put("justificativa", b.getJustificativa()); 
	    param.put("dtvenda", b.getDatavenda());
	    param.put("dtemissao", b.getDataemissao());
	    param.put("vlrvenda", b.getVlrvenda());
	    param.put("list", list);

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
	
	private List<BaixaBem> montaListaBem(BaixaBem bem) {		
		List<BaixaBem> list = new ArrayList<BaixaBem>();	
		BaixaBem b = new BaixaBem();
		
		b.setSolicitante(bem.getSolicitante());
		b.setCcusto(bem.getCcusto());
		b.setFilial(bem.getFilial());
		b.setDatavenda(bem.getDatavenda());
		b.setVlrvenda(bem.getVlrvenda());
		b.setDataemissao(bem.getDataemissao());
		b.setJustificativa(bem.getJustificativa());
		b.setTipoBaixa(bem.getTipoBaixa());
		
		b.setDataaquisicao(bem.getDataaquisicao());
		b.setDescricaoBem(bem.getDescricaoBem());
		b.setPatrimonio(bem.getPatrimonio());
		b.setVlraquisicao(bem.getVlraquisicao());
		b.setVlrresidual(bem.getVlrresidual());
		list.add(b);
		for(int i=0;i<bem.getAgregados().size();i++){
			BaixaBem bx = new BaixaBem();
			bx.setSolicitante(bem.getAgregados().get(i).getSolicitante());
			bx.setCcusto(bem.getAgregados().get(i).getCcusto());
			bx.setFilial(bem.getAgregados().get(i).getFilial());
			bx.setDatavenda(bem.getAgregados().get(i).getDatavenda());
			bx.setVlrvenda(bem.getAgregados().get(i).getVlrvenda());
			bx.setDataemissao(bem.getAgregados().get(i).getDataemissao());
			bx.setJustificativa(bem.getAgregados().get(i).getJustificativa());
			bx.setTipoBaixa(bem.getAgregados().get(i).getTipoBaixa());
			
			bx.setDataaquisicao(bem.getAgregados().get(i).getDataaquisicao());
			bx.setDescricaoBem(bem.getAgregados().get(i).getDescricaoBem());
			bx.setPatrimonio(bem.getAgregados().get(i).getPatrimonio());
			bx.setVlraquisicao(bem.getAgregados().get(i).getVlraquisicao());
			bx.setVlrresidual(bem.getAgregados().get(i).getVlrresidual());
			list.add(bx);			
		}
		return list;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
