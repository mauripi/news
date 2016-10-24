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
import br.com.mauricio.news.model.engenharia.ControleSinal;
import br.com.mauricio.news.util.SaveFile;

public class ControleSinalLN implements Serializable {

	private static final long serialVersionUID = 1L;
	private GenericDao<ControleSinal> dao;
	private EntityManager manager;
	
	
	public ControleSinalLN(){
		Conexao c = new Conexao();
		manager = c.getEntityManager();
	}
	
	public ControleSinal getById(int id) {
		dao = new GenericDao<ControleSinal>();
		return (ControleSinal) dao.findById(ControleSinal.class,id);
	}

	public List<ControleSinal> getList() {
		dao = new GenericDao<ControleSinal>();
		return dao.listWithoutRemoved("controlesinal", "posto,id");
	}
	
	public String create(ControleSinal c) {
		dao = new GenericDao<ControleSinal>();
		dao.save(c);
		return "Castrado com sucesso.";
	}

	public String update(ControleSinal c) {
		dao = new GenericDao<ControleSinal>();
		dao.update(c);		
		return "Atualizado com sucesso.";
	}

	public String delete(ControleSinal c) {
		dao = new GenericDao<ControleSinal>();
		dao.delete((ControleSinal)dao.findById(c.getClass(), c.getId()));
		return "Removido com sucesso.";
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
			System.out.println("Erro localizado em: ControleSinalLN.recebeArquivoUpload() catch (FileNotFoundException e)" + e.getLocalizedMessage());
		} catch (IOException e) {
			erro = "Ocorreu erro ao importar arquivo.";
			System.out.println("Erro localizado em: ControleSinalLN.recebeArquivoUpload()  catch (IOException e) " + e.getLocalizedMessage());
		} finally {
			if(os!=null)
				try {
					os.close();
				} catch (IOException e) {
					erro = "Ocorreu erro ao importar arquivo.";
					System.out.println("Erro localizado em: ControleSinalLN.recebeArquivoUpload() finally os.close()  " + e.getLocalizedMessage());
				}
        }
		return erro;
	}
	
	public void carregaArquivo(ControleSinal c,String caminhoOrigem) {
		ServletContext sContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		String caminhoDestino =sContext.getRealPath("/temp/"+c.getPosto().getNome()+"/"+c.getId()+"/");

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
	
	public List<String> carregaFotosPosto(ControleSinal c,String caminhoOrigem) {
		List<String> list = new ArrayList<String>();
		ServletContext sContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		String caminhoDestino =sContext.getRealPath("/temp/"+c.getPosto().getNome()+"/"+c.getId()+"/fotos/");

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

	
	private void criarPasta(String caminho) {
        File folder = new File(caminho);
        if (!folder.exists())
            folder.mkdirs();					
	}

	public GenericDao<ControleSinal> getDao() {
		return dao;
	}

	public void setDao(GenericDao<ControleSinal> dao) {
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
