package br.com.mauricio.news.ln.compra;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.servlet.ServletContext;

import br.com.mauricio.news.cnn.Conexao;
import br.com.mauricio.news.dao.GenericDao;
import br.com.mauricio.news.dao.compra.ContratoDao;
import br.com.mauricio.news.model.compra.Cliente;
import br.com.mauricio.news.model.compra.Contrato;
import br.com.mauricio.news.model.compra.DocContrato;
import br.com.mauricio.news.model.compra.Fornecedor;
import br.com.mauricio.news.model.compra.TipoContrato;
import br.com.mauricio.news.util.SaveFile;


public class ContratoLN implements Serializable {

	private static final long serialVersionUID = 1L;
	private GenericDao<Contrato> dao;
	private EntityManager manager;

	public ContratoLN(){
		Conexao c = new Conexao();
		manager = c.getEntityManager();
	}

	public ContratoLN(EntityManager manager){
		this.manager = manager;
	}
	
	public Contrato getById(int id) {
		dao = new GenericDao<Contrato>();
		return (Contrato) dao.findById(Contrato.class,id);
	}

	public List<Contrato> getList() {
		dao = new GenericDao<Contrato>();
		return dao.listWithoutRemoved("contrato", "id");
	}

	public List<Contrato> listaContratosAtivos() {
		ContratoDao daoc = new ContratoDao(manager);
		return daoc.listaContratosAtivos();
	}
	
	public String create(Contrato c) {
		dao = new GenericDao<Contrato>();
		GenericDao<TipoContrato> daot = new GenericDao<TipoContrato>();
		c.setTipo((TipoContrato) daot.findById(TipoContrato.class, c.getTipo().getId()));
		
		if(c.getCliente().getId()!=null){
			GenericDao<Cliente> daoc = new GenericDao<Cliente>();
			c.setCliente((Cliente) daoc.findById(Cliente.class, c.getCliente().getId()));
		}else{
			c.setCliente(null);
		}
		
		if(c.getFornecedor().getId()!=null){
			GenericDao<Fornecedor> daof = new GenericDao<Fornecedor>();
			c.setFornecedor((Fornecedor) daof.findById(Fornecedor.class, c.getFornecedor().getId()));
		}else{
			c.setFornecedor(null);
		}
		
		dao.save(c);
		return "Castrado com sucesso.";
	}

	public String update(Contrato c) {
		dao = new GenericDao<Contrato>();
		if(c.getCliente().getId()!=null){
			GenericDao<Cliente> daoc = new GenericDao<Cliente>();
			c.setCliente((Cliente) daoc.findById(Cliente.class, c.getCliente().getId()));
		}else{
			c.setCliente(null);
		}
		
		if(c.getFornecedor().getId()!=null){
			GenericDao<Fornecedor> daof = new GenericDao<Fornecedor>();
			c.setFornecedor((Fornecedor) daof.findById(Fornecedor.class, c.getFornecedor().getId()));
		}else{
			c.setFornecedor(null);
		}
		dao.update(c);		
		return "Atualizado com sucesso.";
	}

	public String delete(Contrato c) {
		ContratoDao daoc = new ContratoDao();
		daoc.delete(c);
		return "Removido com sucesso.";
	}

	public void carregaArquivo(Contrato c,String caminhoOrigem) {
		ServletContext sContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		String caminhoDestino =sContext.getRealPath("/temp/compras/"+c.getId()+"/");

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
			System.out.println("Erro localizado em: ContratoLN.recebeArquivoUpload() catch (FileNotFoundException e)" + e.getLocalizedMessage());
		} catch (IOException e) {
			erro = "Ocorreu erro ao importar arquivo.";
			System.out.println("Erro localizado em: ContratoLN.recebeArquivoUpload()  catch (IOException e) " + e.getLocalizedMessage());
		} finally {
			if(os!=null)
				try {
					os.close();
				} catch (IOException e) {
					erro = "Ocorreu erro ao importar arquivo.";
					System.out.println("Erro localizado em: ContratoLN.recebeArquivoUpload() finally os.close()  " + e.getLocalizedMessage());
				}
        }
		return erro;
	}

	private void criarPasta(String caminho) {
        File folder = new File(caminho);
        if (!folder.exists())
            folder.mkdirs();	
	}
	
	public String salvarDocumeto(DocContrato documento) {
        GenericDao<DocContrato> dao = new GenericDao<DocContrato>(manager);
    	dao.save(documento);
		return "Documento salvo.";   		
	}
	
	public GenericDao<Contrato> getDao() {
		return dao;
	}

	public void setDao(GenericDao<Contrato> dao) {
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
