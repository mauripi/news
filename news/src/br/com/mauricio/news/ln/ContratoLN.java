package br.com.mauricio.news.ln;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;

import org.primefaces.event.FileUploadEvent;

import br.com.mauricio.news.cnn.Conexao;
import br.com.mauricio.news.dao.ContratoDao;
import br.com.mauricio.news.dao.GenericDao;
import br.com.mauricio.news.model.Contrato;
import br.com.mauricio.news.model.Login;
import br.com.mauricio.news.util.SaveFile;


public class ContratoLN implements Serializable {

	private static final long serialVersionUID = 1L;
	@SuppressWarnings("unused")
	private GenericDao<Contrato> dao;
	private EntityManager manager;
	private static String CAMINHO_PASTA_CONTRATOS = "C:\\ARQUIVOS_CONTRATOS\\";
	
	public ContratoLN() {
		Conexao c = new Conexao();
		this.manager = c.getEntityManager();
	}

	public String delete(){
		return null;
	}

	public List<Contrato> list(Login userLogado) {
		ContratoDao cdao = new ContratoDao(manager);
		String codigo = userLogado.getCusto().getCodigo();
		List<Contrato> list = new ArrayList<Contrato>();
		if(codigo.equals("30006")||codigo.equals("30018")||userLogado.getChapa().equals("000755")){
			list = cdao.listaContratosAtivos();
		}else{
			List<String> custos = Arrays.asList("30034", "30071","30107");
			list = cdao.listaContratosByCCusto(custos);
		}
		return list;
	}

	public List<String> getAnexos(Contrato contrato) {
		String caminho = CAMINHO_PASTA_CONTRATOS + contrato.getId().toString() + "\\";
		List<String> anexos = getListaArquivos(caminho);
		arquivosParaWeb(anexos,contrato);
		return anexos;
	}

    public static List<String> getListaArquivos(String caminho) {
    	List<String> arquivos = new ArrayList<String>();
    	
    	File directory = new File(caminho);
    	
        if(directory.isDirectory()) {
            String[] subDirectory = directory.list();
            if(subDirectory != null) 
                for(String dir : subDirectory)
                	arquivos.add(dir);                  
        }
		return arquivos;
    }

    private void arquivosParaWeb(List<String> anexos, Contrato c){
    	String caminho = CAMINHO_PASTA_CONTRATOS + c.getId().toString() + "\\";
    	String path = FacesContext.getCurrentInstance().getExternalContext().getRealPath("");
		path = path+"\\sistema\\tmp\\";
		for(String arquivo:anexos){
			String dest = path+c.getId()+"\\"+arquivo;
			String origem = caminho + arquivo;
			SaveFile.copiar(origem, dest);
		}
    }
    
	public void criaPasta(Contrato contrato) {
		String caminho = CAMINHO_PASTA_CONTRATOS + contrato.getId().toString();
		SaveFile.criarPasta(caminho);		
	}

	public void recebeArquivoUpload(FileUploadEvent upload,Contrato contrato) {
		try {
			InputStream initialStream = upload.getFile().getInputstream();
			String caminho = CAMINHO_PASTA_CONTRATOS + contrato.getId().toString();
			byte[] buffer = new byte[initialStream.available()];
			initialStream.read(buffer);
		    File targetFile = new File(caminho+"//"+upload.getFile().getFileName());
		    criaPasta(contrato);
		    OutputStream outStream = new FileOutputStream(targetFile);
			outStream.write(buffer);
			outStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	
}
