package br.com.mauricio.news.ln.engenharia;

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

import org.apache.commons.io.FileUtils;

import br.com.mauricio.news.cnn.Conexao;
import br.com.mauricio.news.dao.GenericDao;
import br.com.mauricio.news.model.engenharia.FotoPosto;
import br.com.mauricio.news.model.engenharia.Posto;
import br.com.mauricio.news.util.SaveFile;

public class PostoLN implements Serializable {

	private static final long serialVersionUID = 1L;
	private GenericDao<Posto> dao;
	private EntityManager manager;
	private String msg;
	
	public PostoLN(){
		Conexao c = new Conexao();
		manager = c.getEntityManager();
	}
	
	public String add(Posto p){
		try {
			GenericDao<Posto> dao = new GenericDao<Posto>();
			dao.save(p);		
			GenericDao<FotoPosto> fdao = new GenericDao<FotoPosto>();
			for(FotoPosto f:p.getFotos()){
				f.setPosto(p);
				fdao.update(f);
			}
			msg = "Cadastrado com sucesso.";
		} catch (Exception e) {
			System.out.println(e.getMessage() + " -- Erro na Adição");
			msg = "Erro ao adicionar. Verifique se os campos foram preenchidos, caso persista, envie email para maucruz@recordnews.com.br";
		}					
		return msg;
	}
	public String update(Posto p){
		try {
			GenericDao<Posto> dao = new GenericDao<Posto>();
			dao.update(p);			
			msg = "Atualizado com sucesso.";
		} catch (Exception e) {
			System.out.println(e.getMessage() + " -- Erro na Atualização");
			msg = "Erro ao atualizar. Verifique se os campos foram preenchidos, caso persista,  envie email para maucruz@recordnews.com.br";
		}
		return msg;
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
			System.out.println("Erro localizado em: PostoLN.recebeArquivoUpload() catch (FileNotFoundException e)" + e.getLocalizedMessage());
		} catch (IOException e) {
			erro = "Ocorreu erro ao importar arquivo.";
			System.out.println("Erro localizado em: PostoLN.recebeArquivoUpload()  catch (IOException e) " + e.getLocalizedMessage());
		} finally {
			if(os!=null)
				try {
					os.close();
				} catch (IOException e) {
					erro = "Ocorreu erro ao importar arquivo.";
					System.out.println("Erro localizado em: PostoLN.recebeArquivoUpload() finally os.close()  " + e.getLocalizedMessage());
				}
        }
		return erro;
	}
	
	public void carregaArquivo(Posto p,String caminhoOrigem) {
		ServletContext sContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		String caminhoDestino =sContext.getRealPath("/temp/engenharia/posto/"+p.getNome()+"/");
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
	
	public List<String> carregaFotosPosto(Posto p,String caminhoOrigem) {
		List<String> list = new ArrayList<String>();
		ServletContext sContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		String caminhoDestino =sContext.getRealPath("/temp/engenharia/posto/"+p.getNome()+"/");

		File folder = new File(caminhoDestino);
        try {
        	FileUtils.deleteDirectory(folder);
        } catch (IOException e) {
        	e.printStackTrace();
        }
        if (!folder.exists())
             folder.mkdirs();
        File raiz = new File(caminhoOrigem);
        if(raiz.listFiles() != null){
	 		for(File f: raiz.listFiles()) {
	 			if(f.isFile()) {
	 	             String arquivo = caminhoDestino + File.separator + f.getName(); 	 	             
	 	             try {
	 					SaveFile.criaArquivo(f, arquivo);
	 					list.add(f.getName());
	 				} catch (IOException e) {
	 					e.printStackTrace();
	 				}
	 			}
	 		}
        }
		return list;
	}

	public void salvarFoto(FotoPosto foto) {
		GenericDao<FotoPosto> gdao = new GenericDao<FotoPosto>();
		gdao.save(foto);		
	}


	private void criarPasta(String caminho) {
        File folder = new File(caminho);
        if (!folder.exists())
            folder.mkdirs();					
	}

	public GenericDao<Posto> getDao() {
		return dao;
	}

	public void setDao(GenericDao<Posto> dao) {
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

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
     
}
