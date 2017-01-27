package br.com.mauricio.news.ln.contabil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.servlet.ServletContext;

import br.com.mauricio.news.cnn.Conexao;
import br.com.mauricio.news.dao.GenericDao;
import br.com.mauricio.news.dao.contabil.BemDao;
import br.com.mauricio.news.dao.contabil.MovimentoBemDao;
import br.com.mauricio.news.model.Login;
import br.com.mauricio.news.model.contabil.MovimentoBem;
import br.com.mauricio.news.model.contabil.DocMovimentoBem;
import br.com.mauricio.news.model.contabil.ItemMovimento;
import br.com.mauricio.news.model.contabil.Patrimonio;
import br.com.mauricio.news.util.Email;
import br.com.mauricio.news.util.SaveFile;
import br.com.mauricio.news.util.ValidaEmail;

public class MovimentoBemLN implements Serializable {

	private static final long serialVersionUID = 1L;
	private GenericDao<MovimentoBem> dao;
	private EntityManager manager;
	
	public MovimentoBemLN(){
		Conexao c = new Conexao();
		manager = c.getEntityManager();
	}

	public MovimentoBemLN(EntityManager manager){
		this.manager = manager;
	}	
	
	public List<Patrimonio> obterPatrimonios(String codigo){
		BemDao dao = new BemDao();
		try {
			return dao.obterPatrimonios(codigo);
		} catch (IOException e) {
			System.out.println(e.getLocalizedMessage());
			return new ArrayList<Patrimonio>();
		}
	}
	
	public List<MovimentoBem> listaPorUsuario(Login usuario){
		MovimentoBemDao dao = new MovimentoBemDao();
		return dao.listaPorUsuario(usuario);
	}

	public void gerarRelatorio(MovimentoBem m) {
		RelMovimentoBemLN rel = new RelMovimentoBemLN();
		try {
			rel.geraRelatorio(m,1);
		} catch (IOException e) {
			//msg = msg + " Mas não foi possivel gerar relatório.";
			System.out.println(e.getLocalizedMessage() + " MovimentoBemLN.gerarRelatorio() IOException");
			//mensagens();
		}
	}	
	
	public void enviarEmail(MovimentoBem m, String caminho){
		List<String> destinatarios = new ArrayList<String>();
		
		destinatarios.add("acorrea@recordnews.com.br");
		destinatarios.add("flettieri@recordnews.com.br");
		destinatarios.add("aarias@recordnews.com.br");
	
		if(ValidaEmail.validar(m.getSolicitante().getEmail()))
			destinatarios.add(m.getSolicitante().getEmail());
		
		gerarRelatorio(m);
		File file = new File("C:\\windows\\temp\\contabil\\"+"movimentobem_"+m.getId()+".pdf");
		List<File> anexos = new ArrayList<File>();
		anexos.add(file);
		
		if(m.getDocumentos()!=null){
			for(DocMovimentoBem doc:m.getDocumentos()){
				File f = new File(caminho+doc.getArquivo());
				anexos.add(f);
			}
		}
		
		Email email = new Email("Intranet Record News", destinatarios, "Movimentação de Patrimônio", montaCorpodoEmail(m),anexos);
		email.start();
	}

	
	
	private String montaCorpodoEmail(MovimentoBem m){
		StringBuilder sb = new StringBuilder();
		sb.append("<html>");
		sb.append("<body>");
		sb.append("<h1>");
		sb.append("<span style='font-family: arial; font-size: 18pt';>Controle de Movimenta&ccedil;&atilde;o de Patrim&ocirc;nios </span>");
		sb.append("</h1>");
		sb.append("<p><span style='font-family: arial; font-size: 10pt';>Este email foi enviado automaticamente do TI Sistemas.</span></p>");
		sb.append("<p><span style='font-family: arial; font-size: 10pt;'>Abaixo segue detalhes da baixa do bem e relat&oacute;rio em anexo :</span></p>");
		sb.append("<p>&nbsp;</p>");
		sb.append("<p><strong>Solicitante:</strong> "+ m.getSolicitante().getNome() +"</p>");
	//	sb.append("<p><strong>Filial:</strong> "+ bem.getFilial().getNome() +"</p>");
	//	sb.append("<p><strong>Centro de Custo:</strong> "+ bem.getCcusto().getNome() +"</p>");
		sb.append("<p>&nbsp;</p>");	
		sb.append("<p>Favor, n&atilde;o responder.</p><p>Atenciosamente.</p>");		
		sb.append("</body>");		
		sb.append("</html>");	
		return sb.toString();		
	}

	public void removeItensMovimento(List<ItemMovimento> list){
		MovimentoBemDao dao = new MovimentoBemDao();
		dao.deleteItens(list);
	}
		
	public String remove(MovimentoBem m){
		String msg="";
		try{
		GenericDao<MovimentoBem> dao = new GenericDao<MovimentoBem>();	
		removeItensMovimento(m.getItens());
		dao.delete(dao.findById(MovimentoBem.class, m.getId()));
		enviarEmailExclusao(m);
		msg="Movimentação removida!";
		}catch(Exception e){
			System.out.println("MovimentoBemLN.remove  " + e.getMessage());
			msg="Não foi possível efetuar a remoção";
		}
		return msg;
	}

 	public void enviarEmailExclusao(MovimentoBem m) {
		List<String> destinatarios = new ArrayList<String>();
		destinatarios.add("acorrea@recordnews.com.br");
		destinatarios.add("flettieri@recordnews.com.br");
		destinatarios.add("aarias@recordnews.com.br");
		if(ValidaEmail.validar(m.getSolicitante().getEmail()))
			destinatarios.add(m.getSolicitante().getEmail());
		List<File> anexos = new ArrayList<File>();
		Email email = new Email("Intranet Record News", destinatarios, "Exlusão da Solicitação de Movimentação de Bem", montaCorpodoEmailExclusao(m),anexos);
		email.start();
	}
	
	private String montaCorpodoEmailExclusao(MovimentoBem m) {
		StringBuilder sb = new StringBuilder();
		sb.append("<html>");
		sb.append("<body>");
		sb.append("<h1>");
		sb.append("<span style='font-family: arial; font-size: 18pt';>Controle de Movimento de Bens </span>");
		sb.append("</h1>");
		sb.append("<p>&nbsp;</p>");
		sb.append("<p><strong>Movimento de Bem Nro. "+ m.getId()+" removido.:</strong> </p>");
		sb.append("<p><strong>Usu&aacute;rio:</strong> "+ m.getSolicitante().getNome() +"</p>");		
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
	
	public String update(MovimentoBem m, List<ItemMovimento> itensRemover) {
		GenericDao<MovimentoBem> dao = new GenericDao<MovimentoBem>();
		GenericDao<ItemMovimento> daod = new GenericDao<ItemMovimento>();
		dao.update(m);	

		if(itensRemover.size()>0)
			for(ItemMovimento i:itensRemover)
				if(i.getId()!=null)
					daod.delete(daod.findById(ItemMovimento.class, i.getId()));
				
		return "Atualizado com sucesso.";

	}

	public String add(MovimentoBem m) {
		GenericDao<MovimentoBem> dao = new GenericDao<MovimentoBem>();
		GenericDao<ItemMovimento> daod = new GenericDao<ItemMovimento>();
		dao.save(m);;	

		if(m.getItens().size()>0){
			for(ItemMovimento i:m.getItens())
				i.setMovimentobem(m);				
			daod.saveList(m.getItens());
		}				
		return "Gravado com sucesso.";

	}
	
	public void carregaArquivo(MovimentoBem m,String caminhoOrigem) {
		ServletContext sContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		String caminhoDestino =sContext.getRealPath("/temp/contabil/"+m.getId()+"/");
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
			System.out.println("Erro localizado em: MovimentoBemLN.recebeArquivoUpload() catch (FileNotFoundException e)" + e.getLocalizedMessage());
		} catch (IOException e) {
			erro = "Ocorreu erro ao importar arquivo.";
			System.out.println("Erro localizado em: MovimentoBemLN.recebeArquivoUpload()  catch (IOException e) " + e.getLocalizedMessage());
		} finally {
			if(os!=null)
				try {
					os.close();
				} catch (IOException e) {
					erro = "Ocorreu erro ao importar arquivo.";
					System.out.println("Erro localizado em: MovimentoBemLN.recebeArquivoUpload() finally os.close()  " + e.getLocalizedMessage());
				}
        }
		return erro;
	}
	
	private void criarPasta(String caminho) {
        File folder = new File(caminho);
        if (!folder.exists())
            folder.mkdirs();	
	}	

	public String salvarDocumeto(DocMovimentoBem documento) {
        GenericDao<DocMovimentoBem> ddao = new GenericDao<DocMovimentoBem>(manager);
    	ddao.save(documento);
		return "Documento salvo.";   		
	}

	
	
	public GenericDao<MovimentoBem> getDao() {
		return dao;
	}

	public void setDao(GenericDao<MovimentoBem> dao) {
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
