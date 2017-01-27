package br.com.mauricio.news.ln.contabil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.servlet.ServletContext;

import br.com.mauricio.news.cnn.Conexao;
import br.com.mauricio.news.dao.GenericDao;
import br.com.mauricio.news.dao.contabil.BaixaBemDao;
import br.com.mauricio.news.dao.contabil.BemDao;
import br.com.mauricio.news.model.Login;
import br.com.mauricio.news.model.contabil.BaixaBem;
import br.com.mauricio.news.model.contabil.Patrimonio;
import br.com.mauricio.news.util.Email;
import br.com.mauricio.news.util.SaveFile;
import br.com.mauricio.news.util.ValidaEmail;

public class BaixaBemLN implements Serializable {

	private static final long serialVersionUID = 1L;
	private GenericDao<BaixaBem> dao;
	private EntityManager manager;
	
	public BaixaBemLN(){
		Conexao c = new Conexao();
		manager = c.getEntityManager();
	}

	public BaixaBemLN(EntityManager manager){
		this.manager = manager;
	}	
	
	public List<Patrimonio> obterPatrimonios(String codigo, Date data){
		BemDao dao = new BemDao();
		try {
			return dao.obterPatrimonios(codigo, data);
		} catch (IOException e) {
			System.out.println(e.getLocalizedMessage());
			return new ArrayList<Patrimonio>();
		}
	}
	
	public List<BaixaBem> listaPorUsuario(Login usuario){
		BaixaBemDao dao = new BaixaBemDao();
		return dao.listaPorUsuario(usuario);
	}
	
	public void enviarEmailExclusao(BaixaBem bem) {
		List<String> destinatarios = new ArrayList<String>();
		destinatarios.add("acorrea@recordnews.com.br");
		destinatarios.add("flettieri@recordnews.com.br");
		destinatarios.add("aarias@recordnews.com.br");
		if(ValidaEmail.validar(bem.getSolicitante().getEmail()))
			destinatarios.add(bem.getSolicitante().getEmail());
		List<File> anexos = new ArrayList<File>();
		Email email = new Email("Intranet Record News", destinatarios, "Exlusão da Solicitação de Baixa de Patrimônio", montaCorpodoEmailExclusao(bem),anexos);
		email.start();
	}
	
	private String montaCorpodoEmailExclusao(BaixaBem bem) {
		StringBuilder sb = new StringBuilder();
		sb.append("<html>");
		sb.append("<body>");
		sb.append("<h1>");
		sb.append("<span style='font-family: arial; font-size: 18pt';>Controle de Baixa de Patrim&ocirc;nios </span>");
		sb.append("</h1>");
		sb.append("<p>&nbsp;</p>");
		sb.append("<p><strong>Solicita&ccedil;&atilde;o de Baixa de Bem Nro. "+ bem.getId()+" removida.:</strong> </p>");
		sb.append("<p><strong>Usu&aacute;rio:</strong> "+ bem.getSolicitante().getNome() +"</p>");		
		sb.append("<p>&nbsp;</p>");	
		sb.append("<p>&nbsp;</p>");	
		sb.append("<p>Favor, n&atilde;o responder.</p><p>Atenciosamente.</p>");		
		sb.append("<p>&nbsp;</p>");	
		sb.append("<p><span style='font-family: arial; font-size: 10pt';>Este email foi enviado automaticamente do TI Sistemas.</span></p>");
		sb.append("<p><span style='font-family: arial; font-size: 10pt;'>Abaixo segue detalhes da baixa do bem e relat&oacute;rio em anexo :</span></p>");
		sb.append("</body>");	
		sb.append("</html>");	
		return sb.toString();		

	}

	public void enviarEmail(BaixaBem bem){
		List<String> destinatarios = new ArrayList<String>();
		destinatarios.add("acorrea@recordnews.com.br");
		destinatarios.add("flettieri@recordnews.com.br");
		destinatarios.add("aarias@recordnews.com.br");
		if(ValidaEmail.validar(bem.getSolicitante().getEmail()))
			destinatarios.add(bem.getSolicitante().getEmail());
		
		File file = new File("C:\\windows\\temp\\contabil\\"+"baixabem"+bem.getId()+".pdf");
		List<File> anexos = new ArrayList<File>();
		anexos.add(file);			
		
		Email email = new Email("Intranet Record News", destinatarios, "Baixa de Patrimônio", montaCorpodoEmail(bem),anexos);
		email.start();
	}
	
	private String montaCorpodoEmail(BaixaBem bem){
		StringBuilder sb = new StringBuilder();
		sb.append("<html>");
		sb.append("<body>");
		sb.append("<h1>");
		sb.append("<span style='font-family: arial; font-size: 18pt';>Controle de Baixa de Patrim&ocirc;nios </span>");
		sb.append("</h1>");
		sb.append("<p><span style='font-family: arial; font-size: 10pt';>Este email foi enviado automaticamente do TI Sistemas.</span></p>");
		sb.append("<p><span style='font-family: arial; font-size: 10pt;'>Abaixo segue detalhes da baixa do bem e relat&oacute;rio em anexo :</span></p>");
		sb.append("<p>&nbsp;</p>");
		sb.append("<p><strong>Solicitante:</strong> "+ bem.getSolicitante().getNome() +"</p>");
		sb.append("<p><strong>Filial:</strong> "+ bem.getFilial().getNome() +"</p>");
		sb.append("<p><strong>Centro de Custo:</strong> "+ bem.getCcusto().getNome() +"</p>");
		sb.append("<p>&nbsp;</p>");	
		sb.append("<p>Favor, n&atilde;o responder.</p><p>Atenciosamente.</p>");		
		sb.append("</body>");		
		sb.append("</html>");	
		return sb.toString();		
	}

	
	public void carregaArquivo(BaixaBem bem,String caminhoOrigem) {
		ServletContext sContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		String caminhoDestino =sContext.getRealPath("/temp/contabil/"+bem.getId()+"/");

		File folder = new File(caminhoDestino);

        if (!folder.exists())
             folder.mkdirs();
        File raiz = new File(caminhoOrigem);
        if(raiz.listFiles() != null){
	 		for(File f: raiz.listFiles()) {
	 			if(f.isFile()) {
	 	             String arquivo = caminhoDestino + File.separator + f.getName(); 	
	 	             try {
	 					SaveFile.criaArquivo(f, arquivo);
	 				} catch (IOException e) {
	 					e.printStackTrace();
	 				}
	 			}
	 		}
        }
	}

	public String recebeArquivoUpload(InputStream is, String nome, String caminho){
		
        FileOutputStream os = null;
        String erro="";
        try {
        	criarPasta(caminho);      	
            File file = new File(caminho + nome);        	
            os = new FileOutputStream(file);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
        } catch (FileNotFoundException e) {
        	erro = "Ocorreu erro ao importar arquivo.";
			System.out.println("Erro localizado em: BaixaBemLN.recebeArquivoUpload() catch (FileNotFoundException e)" + e.getLocalizedMessage());
		} catch (IOException e) {
			erro = "Ocorreu erro ao importar arquivo.";
			System.out.println("Erro localizado em: BaixaBemLN.recebeArquivoUpload()  catch (IOException e) " + e.getLocalizedMessage());
		} finally {
			if(os!=null)
				try {
					os.close();
				} catch (IOException e) {
					erro = "Ocorreu erro ao importar arquivo.";
					System.out.println("Erro localizado em: BaixaBemLN.recebeArquivoUpload() finally os.close()  " + e.getLocalizedMessage());
				}
        }
		return erro;
	}
	
	private void criarPasta(String caminho) {
        File folder = new File(caminho);
        if (!folder.exists())
            folder.mkdirs();	
	}	
	
	
	
	public GenericDao<BaixaBem> getDao() {
		return dao;
	}

	public void setDao(GenericDao<BaixaBem> dao) {
		this.dao = dao;
	}

	public EntityManager getManager() {
		return manager;
	}

	public void setManager(EntityManager manager) {
		this.manager = manager;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
