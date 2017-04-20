package br.com.mauricio.news.ln.financeiro;

import java.io.File;
import java.io.FileNotFoundException;
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
import javax.persistence.EntityManager;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import br.com.mauricio.news.cnn.Conexao;
import br.com.mauricio.news.dao.GenericDao;
import br.com.mauricio.news.dao.financeiro.AdiantamentoDao;
import br.com.mauricio.news.model.Login;
import br.com.mauricio.news.model.financeiro.Adiantamento;
import br.com.mauricio.news.util.Email;
import br.com.mauricio.news.util.ValidaEmail;
import br.com.mauricio.news.util.ValorPorEstenso;

public class AdiantamentoLN implements Serializable{

	private static final long serialVersionUID = 1L;
	private GenericDao<Adiantamento> dao;
	private EntityManager manager;
	
	public AdiantamentoLN(){
		Conexao c = new Conexao();
		manager = c.getEntityManager();
	}
	
	public AdiantamentoLN(EntityManager manager){
		this.manager = manager;
	}
	
	public Adiantamento getById(int id) {
		dao = new GenericDao<Adiantamento>();
		return (Adiantamento) dao.findById(Adiantamento.class,id);
	}

	public List<Adiantamento> listByUsuario(Login usuario){
		AdiantamentoDao sdao = new AdiantamentoDao();
		return sdao.listByUsuario(usuario);			
	}

	@SuppressWarnings("unchecked")
	private void gerarRelatorio(Adiantamento adto){
		String CAMINHO_PARA_SALVAR_ARQUIVO = "C:\\Windows\\Temp\\financeiro\\";
        File folder = new File(CAMINHO_PARA_SALVAR_ARQUIVO);
        if (!folder.exists())
            folder.mkdirs();
        
		List<Adiantamento> list = new ArrayList<Adiantamento>();
		list.add(adto);
		
		String nomeRelatorio = "adiantamento.jasper";	
	    JRBeanCollectionDataSource jrds = new JRBeanCollectionDataSource(list);
	    @SuppressWarnings("rawtypes")
	    HashMap param = new HashMap();
	    FacesContext fc = FacesContext.getCurrentInstance();
	    ExternalContext ec = fc.getExternalContext();
		   
	    File reportFile = new File(ec.getRealPath("/sistema/relatorio/" +nomeRelatorio));
	   
	    param.put("id", adto.getId()); 
	    param.put("data", adto.getData()); 
	    param.put("favorecido", adto.getFavorecido().getNome().toUpperCase()); 
	    param.put("departamento",adto.getFavorecido().getCusto().getNome()); 	
	    param.put("motivo", adto.getMotivo()); 		 
	    param.put("valor", adto.getValor()); 
	    param.put("valorestenso", "("+ValorPorEstenso.converter(adto.getValor())+")");
	    param.put("observacao", adto.getObservacao()); 
	    param.put("REPORT_LOCALE", new Locale("pt", "BR"));
	    param.put("logo", ec.getRealPath("/sistema/img/logo.jpg"));
	    OutputStream os = null;
	    JasperPrint jasperPrint;
		try {
			os = new FileOutputStream(CAMINHO_PARA_SALVAR_ARQUIVO+"adiantamento"+adto.getId()+".pdf");			    
			jasperPrint = JasperFillManager.fillReport(reportFile.getPath(),param, jrds);
			JasperExportManager.exportReportToPdfStream(jasperPrint, os); 
		} catch (JRException | FileNotFoundException e) {
			try {
				os.close();
			} catch (IOException e1) {				
				e1.printStackTrace();
			}
			e.printStackTrace();
		}finally{
		    if (os != null) {
		        try {
		            os.close();
		        } catch (IOException ex) {}
		    }
		}
		
	}
	
	public void enviarEmail(Adiantamento adto){
		gerarRelatorio(adto);
		List<String> dest = new ArrayList<String>();

		dest.add("lrnascimento@recordnews.com.br");
		dest.add("ecoletto@recordnews.com.br");
		dest.add("eksouza@recordnews.com.br ");
		dest.add("ctsouza@recordnews.com.br");

		
		if(ValidaEmail.validar(adto.getFavorecido().getEmail()))
			dest.add(adto.getFavorecido().getEmail());
		if(!adto.getFavorecido().getId().equals(adto.getSolicitante().getId()))
			if(ValidaEmail.validar(adto.getSolicitante().getEmail()))
				dest.add(adto.getSolicitante().getEmail());
			
		if(ValidaEmail.validar(adto.getFavorecido().getEmail()))
			dest.add(adto.getFavorecido().getEmail());
		gerarRelatorio(adto);
		File file = new File("C:\\Windows\\Temp\\financeiro\\"+"adiantamento"+adto.getId()+".pdf");
		List<File> anexos = new ArrayList<File>();
		anexos.add(file);		
		Email email = new Email("Intranet Record News", dest, "Adiantamento de Despesas Nro." +adto.getId(), montaCorpodoEmail(adto),anexos);
		email.start();
	}
	
	private String montaCorpodoEmail(Adiantamento adto){
		StringBuilder sb = new StringBuilder();
		sb.append("<html>");
		sb.append("<body>");
		sb.append("<h1>");
		sb.append("<span style='font-family: arial; font-size: 18pt';>Adiantamento de Despesas </span>");
		sb.append("</h1>");
		sb.append("<p>&nbsp;</p>");
		sb.append("<p><strong>Favorecido:</strong> "+ adto.getFavorecido().getNome() +"</p>");
		sb.append("<p><strong>Filial:</strong> "+ adto.getFavorecido().getFilial().getNome() +"</p>");
		sb.append("<p><strong>Departamento:</strong> "+ adto.getFavorecido().getCusto().getNome() +"</p>");
		sb.append("<p>&nbsp;</p>");
		sb.append("<p><span style='font-family: arial; font-size: 10pt';>Este email foi enviado automaticamente do Intranet News.</span></p>");	
		sb.append("<p>Favor, n&atilde;o responder.</p><p>Atenciosamente.</p>");		
		sb.append("</body>");		
		sb.append("</html>");	
		return sb.toString();		
	}	
	
	public GenericDao<Adiantamento> getDao() {
		return dao;
	}

	public void setDao(GenericDao<Adiantamento> dao) {
		this.dao = dao;
	}

	public EntityManager getManager() {
		return manager;
	}

	public void setManager(EntityManager manager) {
		this.manager = manager;
	}
	
}
