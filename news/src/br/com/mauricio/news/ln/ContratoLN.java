package br.com.mauricio.news.ln;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;

import org.primefaces.event.FileUploadEvent;

import br.com.mauricio.news.cnn.Conexao;
import br.com.mauricio.news.dao.ContratoDao;
import br.com.mauricio.news.model.Contrato;
import br.com.mauricio.news.model.Login;
import br.com.mauricio.news.util.SaveFile;


public class ContratoLN implements Serializable {

    private static final long serialVersionUID = 1L;
    private EntityManager manager;
    private static String CAMINHO_PASTA_CONTRATOS = "C:\\ARQUIVOS_CONTRATOS\\";
    
    public ContratoLN() {
        Conexao c = new Conexao();
        this.manager = c.getEntityManager();
    }

    public String delete(Contrato contrato){
    	File pastaAnexos = new File(CAMINHO_PASTA_CONTRATOS+ contrato.getId().toString());
    	pastaAnexos.deleteOnExit();
    	GenericLN<Contrato> gln = new GenericLN<Contrato>();
    	gln.remove(gln.find(new Contrato(), contrato.getId()));    	
        return "Contrato removido!";
    }

    public List<Contrato> list(Login userLogado, int ativo ) {
    	boolean isAtivo=true;
    	if(ativo==1) isAtivo=false;
        ContratoDao cdao = new ContratoDao(manager);
        List<Contrato> list = new ArrayList<Contrato>();
        
		switch (userLogado.getId()) {
		case 116:
			list = cdao.listaContratosAtivosPorResponsavel(isAtivo, 1);
			break;
		case 88:
			list = cdao.listaContratosAtivos(isAtivo);
			break;		
		case 106:
			list = cdao.listaContratosAtivosPorResponsavel(isAtivo, 2);
			break;
		case 91:
			System.out.println(manager.getTransaction().toString());
			System.out.println(manager.getClass().getName());			
			list = cdao.listaContratosAtivos(isAtivo);
			break;
		case 411:
			list = cdao.listaContratosAtivos(isAtivo);
			break;	
		case 418:
			list = cdao.listaContratosAtivos(isAtivo);
			break;			
		case 117:
			list = cdao.listaContratosAtivos(isAtivo);
			break;			
		default:
			list =  new ArrayList<Contrato>();
			break;
		}

        return list;
    }

    public List<String> getAnexos(Contrato contrato) {
        String caminho = CAMINHO_PASTA_CONTRATOS + contrato.getId().toString() + "\\";
        List<String> anexos = SaveFile.listFilesInFolder(caminho);
        arquivosParaWeb(anexos,contrato);
        return anexos;
    }

    private void arquivosParaWeb(List<String> anexos, Contrato c){
        String caminho = CAMINHO_PASTA_CONTRATOS + c.getId().toString() + "\\";
        String path = FacesContext.getCurrentInstance().getExternalContext().getRealPath("");
        String dest = path+"\\sistema\\tmp\\"+c.getId()+"\\";
        SaveFile.criarPasta(dest);
        anexos.forEach(a -> SaveFile.copiar(caminho + a,dest + a));        
    }
    
    public void recebeArquivoUpload(FileUploadEvent upload,Contrato contrato) {
        try {
            String caminho = CAMINHO_PASTA_CONTRATOS + contrato.getId().toString();
            SaveFile.criaArquivo(upload, caminho);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

    public List<String> emailsCadastrados(String campo){
        ContratoDao cdao = new ContratoDao(manager);
        return cdao.emailsCadastrados(campo);
        
    }

    public String removeAnexo(Contrato contrato, String anexo) {
        try{
            String caminho = CAMINHO_PASTA_CONTRATOS + contrato.getId().toString();
            File targetFile = new File(caminho+"\\"+anexo);
            targetFile.delete();
            return "Anexo removido";
        }catch(Exception e){
            System.out.println(e);
            return "Ocorreu erro ao remover anexo.";
        }
    }


    public List<Contrato> listAtivo(){
    	ContratoDao dao = new ContratoDao(manager);
		return dao.listaContratosAtivos(true);
    }
 
    
    
}