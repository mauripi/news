package br.com.mauricio.news.ln.compra;

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

import br.com.mauricio.news.model.compra.RequisicaoContrato;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;


public class RelRequisicaoContratoLN implements Serializable{

	private static final long serialVersionUID = 6474755493685831412L;


	@SuppressWarnings({ "unchecked" })
	public void geraRelatorio(RequisicaoContrato r) throws IOException{
		
		String CAMINHO_PARA_SALVAR_ARQUIVO = "C:\\windows\\temp\\compra\\";

		List<RequisicaoContrato> list = new ArrayList<RequisicaoContrato>();
		list.add(r);
		
		String nomeRelatorio = "requisicaocontrato.jasper";	
	    JRBeanCollectionDataSource jrds = new JRBeanCollectionDataSource(list);
	    @SuppressWarnings("rawtypes")
	    HashMap param = new HashMap();
	    FacesContext fc = FacesContext.getCurrentInstance();
	    ExternalContext ec = fc.getExternalContext();
		   
	    File reportFile = new File(ec.getRealPath("/sistema/relatorio/" +nomeRelatorio));
	   

	    param.put("solicitante", r.getSolicitante()); 
	    param.put("departamento", r.getDepartamento()); 
	    param.put("datarequisicao", r.getDatarequisicao()); 	
	    param.put("razaosocial", r.getRazaosocial()); 		    		    	    
	    param.put("cnpj", r.getCnpj()); 
	    param.put("telefone", r.getTelefone());
	    param.put("contato", r.getContato());
	    param.put("email", r.getEmail());
	    param.put("observacao", r.getObservacao());
	    param.put("vigencia", r.getVigencia());
	    param.put("valor", r.getValor());
	    
	    if(r.getInternoexterno()==1){
	    	param.put("interno", "(X)");
	    	param.put("externo", "(  )");
	    }else{
	    	param.put("interno", "(  )");
	    	param.put("externo", "(X)");
	    }
	    
	    if(r.getTipo().equals("Serviços")){
	    	param.put("tipo1", "(X)");
	    	param.put("tipo2", "(  )");
	    	param.put("tipo3", "(  )");	    	
	    	param.put("tipo4", "(  )");	    	
	    	param.put("tipo5", "(  )");	    	
	    }
	    
	    if(r.getTipo().equals("Permuta")){
	    	param.put("tipo1", "(  )");
	    	param.put("tipo2", "(X)");
	    	param.put("tipo3", "(  )");	    	
	    	param.put("tipo4", "(  )");	    	
	    	param.put("tipo5", "(  )");	    	
	    }
	    if(r.getTipo().equals("Licenciamento de Programa")){
	    	param.put("tipo1", "(  )");
	    	param.put("tipo2", "(  )");
	    	param.put("tipo3", "(X)");	    	
	    	param.put("tipo4", "(  )");	    	
	    	param.put("tipo5", "(  )");	    	
	    }
	    
	    if(r.getTipo().equals("Carta Acordo")){
	    	param.put("tipo1", "(  )");
	    	param.put("tipo2", "(  )");
	    	param.put("tipo3", "(  )");	    	
	    	param.put("tipo4", "(X)");	    	
	    	param.put("tipo5", "(  )");	    	
	    }
	    
	    if(r.getTipo().equals("Outros")){
	    	param.put("tipo1", "(  )");
	    	param.put("tipo2", "(  )");
	    	param.put("tipo3", "(  )");	    	
	    	param.put("tipo4", "(  )");	    	
	    	param.put("tipo5", "(X)");	    	
	    }    

	    // para resolver o problema da moeda
	    param.put("REPORT_LOCALE", new Locale("pt", "BR"));
	    param.put("logo", ec.getRealPath("/sistema/img/logo.jpg"));
	    
	    OutputStream os = new FileOutputStream(CAMINHO_PARA_SALVAR_ARQUIVO+"requisicaocontrato"+r.getId()+".pdf");
	    HttpServletResponse httpServletResponse=(HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();  	    
	    httpServletResponse.addHeader("Content-disposition", "inline; filename=requisicaocontrato"+r.getId()+".pdf");  
	    ServletOutputStream servletOutputStream=httpServletResponse.getOutputStream();  
	    JasperPrint jasperPrint;
	    
		try {
			jasperPrint = JasperFillManager.fillReport(reportFile.getPath(),param, jrds);
			JasperExportManager.exportReportToPdfStream(jasperPrint, os); 
			JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream); 
			FacesContext.getCurrentInstance().responseComplete();  
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
