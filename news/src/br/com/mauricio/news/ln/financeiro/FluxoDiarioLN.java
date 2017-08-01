package br.com.mauricio.news.ln.financeiro;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import br.com.mauricio.news.cnn.Conexao;
import br.com.mauricio.news.dao.GenericDao;
import br.com.mauricio.news.dao.financeiro.FluxoDiarioDao;
import br.com.mauricio.news.model.financeiro.CampoPlanilha;
import br.com.mauricio.news.model.financeiro.Custo;
import br.com.mauricio.news.model.financeiro.FluxoDiario;


public class FluxoDiarioLN implements Serializable {

	private static final long serialVersionUID = 1L;
	private EntityManager manager;
	private static final String CAMINHO_PARA_SALVAR_ARQUIVO_IMPORTADO = "C:\\Windows\\Temp\\financeiro\\";

	public FluxoDiarioLN(){
		Conexao c = new Conexao();
		manager = c.getEntityManager();
	}
	
	public FluxoDiarioLN(EntityManager manager){
		this.manager = manager;
	}

	public String recebeArquivoUpload(InputStream is, String nome){
        FileOutputStream os = null;
        String erro="";
        try {
            File file = new File(CAMINHO_PARA_SALVAR_ARQUIVO_IMPORTADO + nome);        	
            os = new FileOutputStream(file);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
           // gravaNomeDoUltimoArquivoImportado(nome);
        } catch (FileNotFoundException e) {
        	erro = "Ocorreu erro ao importar arquivo.";
			System.out.println("Erro localizado em: FluxoDiarioLN().recebeArquivoUpload() catch (FileNotFoundException e)" + e.getLocalizedMessage());
		} catch (IOException e) {
			erro = "Ocorreu erro ao importar arquivo.";
			System.out.println("Erro localizado em: FluxoDiarioLN().recebeArquivoUpload()  catch (IOException e) " + e.getLocalizedMessage());
		} finally {
			if(os!=null)
				try {
					os.close();
				} catch (IOException e) {
					erro = "Ocorreu erro ao importar arquivo.";
					System.out.println("Erro localizado em: FluxoDiarioLN().recebeArquivoUpload() finally os.close()  " + e.getLocalizedMessage());
				}
        }
		return erro;
	}
	
	public String importarArquivoBD(String nomeArquivo, CampoPlanilha c, Integer mes, Integer ano ) {
		FluxoDiarioDao dao = new FluxoDiarioDao(manager);
		try {
			FluxoDiarioDao fdao = new FluxoDiarioDao(manager);
			fdao.removePeriodoDoBanco(mes, ano);
			List<FluxoDiario> list = dao.lerArquivo(CAMINHO_PARA_SALVAR_ARQUIVO_IMPORTADO + nomeArquivo,c);
			GenericDao<FluxoDiario> gdao = new GenericDao<FluxoDiario>(manager);
			gdao.saveList(list);
		} catch (IOException e) {
			System.out.println("Erro em FluxoDiarioLN.importarArquivoBD " + e.getLocalizedMessage());
			return "Erro ao importar arquivo";			
		}
		return "Arquivo importado com sucesso!";
	}	

	public List<Custo> getCustos(Integer ano) {
		FluxoDiarioDao dao = new FluxoDiarioDao(manager);
		return dao.custos(ano);
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

	public List<FluxoDiario> listaPorMesAnoTipo(int mes, Integer ano, String tipo) {
		FluxoDiarioDao dao = new FluxoDiarioDao(manager);		
		return dao.listaPorMesAnoTipo(mes,ano,tipo);
	}



    
}
