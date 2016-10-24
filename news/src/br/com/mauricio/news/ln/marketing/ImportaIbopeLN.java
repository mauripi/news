package br.com.mauricio.news.ln.marketing;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import br.com.mauricio.news.dao.GenericDao;
import br.com.mauricio.news.dao.marketing.IbopeDao;
import br.com.mauricio.news.dao.marketing.ImportaArquivoMktDao;
import br.com.mauricio.news.model.marketing.Ibope;
import br.com.mauricio.news.model.marketing.LiveIbope;
import br.com.mauricio.news.model.marketing.ProgramaIbope;

public class ImportaIbopeLN  implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final String CAMINHO_PARA_SALVAR_ARQUIVO_IMPORTADO = "C:\\Windows\\Temp\\marketing\\";

	public String importarArquivo(String excelFilePath){
		String msg="Importado com sucesso!";
		
		List<Ibope> ibopes = new ArrayList<Ibope>();
		ImportaArquivoMktDao dao = new ImportaArquivoMktDao();
		List<LiveIbope> lives = new ArrayList<LiveIbope>();
		List<LiveIbope> livesParaGravarNoBanco = new ArrayList<LiveIbope>();
		GenericDao<ProgramaIbope> pmDao = new GenericDao<ProgramaIbope>();
		GenericDao<LiveIbope> gexbDao = new GenericDao<LiveIbope>();
		IbopeDao ibpDao = new IbopeDao();
		
		try {
			ibopes = dao.importarIbope(CAMINHO_PARA_SALVAR_ARQUIVO_IMPORTADO+excelFilePath);
			
			for(Ibope m:ibopes){
				if(isNumeroTry(m.getCodigo())){
					ProgramaIbope pm = ibpDao.buscaProgramaPorCodigo(Integer.parseInt(m.getCodigo()));
					LiveIbope l = new LiveIbope();
					l.setData(m.getData());
					
					if(isDoubleTry(m.getDratper()))
						l.setDratper(Double.parseDouble(m.getDratper()));
					else
						l.setDratper(new Double("0.0"));
					
					if(isDoubleTry(m.getDratabs()))
						l.setDratabs(Double.parseDouble(m.getDratabs()));
					else
						l.setDratabs(new Double("0.0"));
					
					if(isDoubleTry(m.getDcovper()))
						l.setDcovperc(Double.parseDouble(m.getDcovper()));
					else
						l.setDcovperc(new Double("0.0"));					
					
					if(isDoubleTry(m.getDcovabs()))
						l.setDcovabs(Double.parseDouble(m.getDcovabs()));
					else
						l.setDcovabs(new Double("0.0"));									
					
					if(isDoubleTry(m.getIratper()))
						l.setIratper(Double.parseDouble(m.getIratper()));
					else
						l.setIratper(new Double("0.0"));
					
					if(isDoubleTry(m.getIratabs()))
						l.setIratabs(Double.parseDouble(m.getIratabs()));
					else
						l.setIratabs(new Double("0.0"));
					
					if(isDoubleTry(m.getIcovper()))
						l.setIcovperc(Double.parseDouble(m.getIcovper()));
					else
						l.setIcovperc(new Double("0.0"));					
					
					if(isDoubleTry(m.getIcovabs()))
						l.setIcovabs(Double.parseDouble(m.getIcovabs()));
					else
						l.setIcovabs(new Double("0.0"));					
					
					l.setInicio(m.getInicio());				
					
					if(pm!=null){
						l.setProgramaibope(pm);											
					}else{ //programa não está gravado no banco
						ProgramaIbope p = new ProgramaIbope();
						p.setNome(m.getPrograma());
						p.setCodigoibope(Integer.parseInt(m.getCodigo()));
						p.setWmask(m.getWmask());
						pmDao.save(p);
						l.setProgramaibope(p);
					}
					lives.add(l);
				}
			}
			
		} catch (EncryptedDocumentException | InvalidFormatException | IOException e) {
			System.out.println("Ocorreu erro ao importar arquivo do Ibope ImportaIbopeLN.importarArquivo() || " + e.getLocalizedMessage());
			msg="Ocorreu erro ao importar arquivo do Ibope.";
		}		
		
		for(LiveIbope lv:lives)
			if(ibpDao.buscaLivePorProgramaDataHora(lv.getProgramaibope(), lv.getData(),lv.getInicio())==null)
				livesParaGravarNoBanco.add(lv);
		
		gexbDao.saveList(livesParaGravarNoBanco);
		return msg;
	}

	public String recebeArquivoUpload(InputStream is, String nome){
        FileOutputStream os = null;
        String erro="";
        try {
            File folder = new File(CAMINHO_PARA_SALVAR_ARQUIVO_IMPORTADO);
            if (!folder.exists())
                folder.mkdirs();
            File file = new File(CAMINHO_PARA_SALVAR_ARQUIVO_IMPORTADO + nome);   
            os = new FileOutputStream(file);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
        } catch (FileNotFoundException e) {
        	erro = "Ocorreu erro ao importar arquivo.";
			System.out.println("Erro localizado em: ImportaIbopeLN().recebeArquivoUpload() catch (FileNotFoundException e)" + e.getLocalizedMessage());
		} catch (IOException e) {
			erro = "Ocorreu erro ao importar arquivo.";
			System.out.println("Erro localizado em: ImportaIbopeLN().recebeArquivoUpload()  catch (IOException e) " + e.getLocalizedMessage());
		} finally {
			if(os!=null)
				try {
					os.close();
				} catch (IOException e) {
					erro = "Ocorreu erro ao importar arquivo.";
					System.out.println("Erro localizado em: ImportaIbopeLN().recebeArquivoUpload() finally os.close()  " + e.getLocalizedMessage());
				}
        }
		return erro;
	}

	public boolean isNumeroTry(String texto) {
		try {
			Integer.parseInt(texto);
			return true;
		} catch (NumberFormatException nfex) {
			return false;
		}
	}
	
	private boolean isDoubleTry(String texto) {
		try {
			Double.parseDouble(texto);
			return true;
		} catch (NumberFormatException nfex ) {
			return false;
		}
	}
}
