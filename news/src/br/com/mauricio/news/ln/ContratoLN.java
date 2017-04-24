package br.com.mauricio.news.ln;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
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
        List<String> anexos = SaveFile.listFilesInFolder(caminho);
        arquivosParaWeb(anexos,contrato);
        return anexos;
    }


    private void arquivosParaWeb(List<String> anexos, Contrato c){
        String caminho = CAMINHO_PASTA_CONTRATOS + c.getId().toString() + "\\";
        String path = FacesContext.getCurrentInstance().getExternalContext().getRealPath("");
        String dest = path+"\\sistema\\tmp\\"+c.getId()+"\\";
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

    public List<String> emailsCadastrados(){
        ContratoDao cdao = new ContratoDao(manager);
        return cdao.emailsCadastrados();
        
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

    
    
}