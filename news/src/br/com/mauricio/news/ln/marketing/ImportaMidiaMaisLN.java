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
import br.com.mauricio.news.dao.marketing.ImportaArquivoMktDao;
import br.com.mauricio.news.dao.marketing.MidiaMaisDao;
import br.com.mauricio.news.model.marketing.ExibicaoMidiaMais;
import br.com.mauricio.news.model.marketing.MidiaMais;
import br.com.mauricio.news.model.marketing.ProgramaMidiaMais;
import br.com.mauricio.news.model.marketing.ProgramaRelacionamento;

public class ImportaMidiaMaisLN  implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final String CAMINHO_PARA_SALVAR_ARQUIVO_IMPORTADO = "C:\\Windows\\Temp\\marketing\\";
	
	public String importarArquivo(String excelFilePath){

		String msg="Importado com sucesso!";
		
		List<MidiaMais> midias = new ArrayList<MidiaMais>();
		ImportaArquivoMktDao dao = new ImportaArquivoMktDao();
		List<ExibicaoMidiaMais> exibicoes = new ArrayList<ExibicaoMidiaMais>();
		GenericDao<ProgramaMidiaMais> pmDao = new GenericDao<ProgramaMidiaMais>();
		MidiaMaisDao exbDao = new MidiaMaisDao();
		GenericDao<ProgramaRelacionamento> progrelDao = new GenericDao<ProgramaRelacionamento>();
		
		try {
			midias = dao.importarMidiaMais(CAMINHO_PARA_SALVAR_ARQUIVO_IMPORTADO+excelFilePath);
			for(MidiaMais m:midias){
				ProgramaMidiaMais pm = exbDao.buscaProgramaPorNome(m.getPrograma());
				ExibicaoMidiaMais e = new ExibicaoMidiaMais();

				e.setCliente(m.getCliente());
				e.setTitulo(m.getTitulo());
				e.setPrograma(m.getPrograma());
				e.setPraca(m.getPraca());
				e.setDuracao(m.getDuracao());
				e.setData(m.getData());
				e.setHorario(m.getHorario());
				e.setSituacao(m.getSituacao());
				e.setAudiencia(m.getAudiencia());
								
				if(pm!=null){
					e.setProgramamidiamais(pm);											
				}else{ //programa não está gravado no banco
					ProgramaMidiaMais p = new ProgramaMidiaMais();
					ProgramaRelacionamento progrel = new ProgramaRelacionamento();
					p.setNome(m.getPrograma());
					pmDao.save(p);
					progrel.setProgramamidiamais(p);
					progrelDao.save(progrel);
					e.setProgramamidiamais(p);
				}
				exibicoes.add(e);
			}
			
		} catch (EncryptedDocumentException | InvalidFormatException | IOException e) {
			System.out.println("Ocorreu erro ao importar arquivo do Midia Mais || " + e.getLocalizedMessage());
			e.printStackTrace();
		}

		exbDao.apagaExibicoesDoBanco(exibicoes);

		GenericDao<ExibicaoMidiaMais> gexbDao = new GenericDao<ExibicaoMidiaMais>();
		gexbDao.saveList(exibicoes);		
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
			System.out.println("Erro localizado em: ImportaMidiaMaisLN().recebeArquivoUpload() catch (FileNotFoundException e)" + e.getLocalizedMessage());
		} catch (IOException e) {
			erro = "Ocorreu erro ao importar arquivo.";
			System.out.println("Erro localizado em: ImportaMidiaMaisLN().recebeArquivoUpload()  catch (IOException e) " + e.getLocalizedMessage());
		} finally {
			if(os!=null)
				try {
					os.close();
				} catch (IOException e) {
					erro = "Ocorreu erro ao importar arquivo.";
					System.out.println("Erro localizado em: ImportaMidiaMaisLN().recebeArquivoUpload() finally os.close()  " + e.getLocalizedMessage());
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
	

}
