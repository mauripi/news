package br.com.mauricio.news.ln.rh;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.faces.context.FacesContext;

import br.com.mauricio.news.dao.GenericDao;
import br.com.mauricio.news.dao.rh.BaseDao;
import br.com.mauricio.news.dao.rh.VencimentoDao;
import br.com.mauricio.news.dao.rh.VetorhDao;
import br.com.mauricio.news.model.rh.Base;
import br.com.mauricio.news.model.rh.Vencimento;


public class ImportaHoleriteLN implements Serializable {
	private static final long serialVersionUID = -745593021993066877L;
	private static final String CAMINHO_PARA_SALVAR_ARQUIVO_IMPORTADO = "C:\\Windows\\Temp\\rh\\";
    private Properties props;  
    private String nomeDoProperties = "importabase.properties"; 
	private static final String CAMINHO_PROPERTIES = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/")+"\\resource";    
	private List<Base> bases = new ArrayList<Base>();

    
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
            gravaNomeDoUltimoArquivoImportado(nome);
        } catch (FileNotFoundException e) {
        	erro = "Ocorreu erro ao importar arquivo.";
			System.out.println("Erro localizado em: ImportaHoleriteLN().recebeArquivoUpload() catch (FileNotFoundException e)" + e.getLocalizedMessage());
		} catch (IOException e) {
			erro = "Ocorreu erro ao importar arquivo.";
			System.out.println("Erro localizado em: ImportaHoleriteLN().recebeArquivoUpload()  catch (IOException e) " + e.getLocalizedMessage());
		} finally {
			if(os!=null)
				try {
					os.close();
				} catch (IOException e) {
					erro = "Ocorreu erro ao importar arquivo.";
					System.out.println("Erro localizado em: ImportaHoleriteLN().recebeArquivoUpload() finally os.close()  " + e.getLocalizedMessage());
				}
        }
		return erro;
	}
	
	public String validaArquivo(String mes, String ano, Integer periodo){
		Integer iMes = Integer.parseInt(mes);
		Integer iAno = Integer.parseInt(ano);
		String erro="";
		if (buscaNomeDoUltimoArquivoImportado().equals("")){
			System.out.println("Erro localizado em: ImportaHoleriteLN().validaArquivo()  buscaNomeDoUltimoArquivoImportado().equals() vazio");
			erro = "Ocorreu erro ao importar arquivo.";
		}else{
			String nomeArquivo = buscaNomeDoUltimoArquivoImportado();
			try {
				lerArquivo(CAMINHO_PARA_SALVAR_ARQUIVO_IMPORTADO+nomeArquivo);
			} catch (IOException e) {
				System.out.println("Erro localizado em: ImportaHoleriteLN().validaArquivo()  catch (IOException e) " + e.getLocalizedMessage());
				erro = "Ocorreu erro ao importar arquivo.";
			}catch (NumberFormatException e ) {
	        	System.out.println("Erro localizado em: ImportaHoleriteLN().validaArquivo()  catch (NumberFormatException e) " + e.getLocalizedMessage());
				erro = "Ocorreu erro ao importar arquivo.";
	        }		
			if(verificaMesAnoPeriodoInformado(mes,ano,periodo)){
	        	System.out.println("Erro localizado em: ImportaHoleriteLN().validaArquivo().verificaMesAnoPeriodoInformado(mes,ano,periodo)  Mês ou Ano informado diferente do arquivo importado!.) ");
				erro = "Mês ou Ano ou Tipo de Holerite informado diferente do arquivo importado!.";				
			}else{

				verificaSeExistemRegistrosDeBaseNoBanco(iMes,iAno,periodo);
				gravarBases();
				verificaSeExistemRegistrosDeVencimentoNoBanco(iMes,iAno,periodo);
				gravarVencimentos(iMes,iAno,periodo);
				erro="Arquivo importado com sucesso!";				
			}
		}
		return erro;		
	}
  
    private String buscaNomeDoUltimoArquivoImportado(){  
            props = new Properties(); 
            String nomeArquivo="";
            try{  
            	InputStream in = new FileInputStream(CAMINHO_PROPERTIES+File.separator+nomeDoProperties);  
                props.load(in);  
                in.close();  
                nomeArquivo = (String)props.getProperty("ultimoarquivo"); 
            }  
            catch(IOException e){
            	nomeArquivo="";
            	System.out.println("Erro localizado em: ImportaHoleriteLN().buscaNomeDoUltimoArquivoImportado()  catch (IOException e) " + e.getLocalizedMessage());
            }  
            return nomeArquivo;
    }  
  
    public void gravaNomeDoUltimoArquivoImportado(String nome) {

    	OutputStream out = null;
    	props = new Properties();
        try {
        	props.setProperty("ultimoarquivo", nome);
            out = new FileOutputStream(CAMINHO_PROPERTIES+File.separator+nomeDoProperties);
            props.store(out,null);
            out.close();
        }
        catch (Exception e ) {
        	System.out.println("Erro localizado em: ImportaHoleriteLN().gravaNomeDoUltimoArquivoImportado()  catch (IOException e) " + e.getLocalizedMessage());
        }
    }	
	
	public void lerArquivo(String arquivo) throws IOException,NumberFormatException {
		bases = new ArrayList<Base>();
		
		FileReader arq = new FileReader(arquivo); 
		BufferedReader lerArq = new BufferedReader(arq); 
		String linha = lerArq.readLine();
		
		while (lerArq.ready()) { 
			Base b = new Base();
			linha = lerArq.readLine();

			if(linha.trim().length()!=0){
				String[] s = linha.split(";");
				if(s[0]!=" ")
					b.setAno(Integer.parseInt(s[0].replace(" ", "")));			
				if(s[1]!="")
					b.setBaseFGTS(new Double(s[1].replace(" ", "").replace(",", ".")));			
				if(s[2]!="")
					b.setBaseINSS(Double.parseDouble(s[2].replace(" ", "").replace(",", ".")));
				if(s[3]!="")
					b.setBaseIRRF(Double.parseDouble(s[3].replace(" ", "").replace(",", ".")));
				if(s[4]!="")
					b.setCbo(s[4].replace(" ", ""));			
				if(s[5]!="")
					b.setChapa(s[5].replace(" ", ""));				
				if(s[6]!="")
					b.setFuncao(s[6]);					
				if(s[7]!="")
					b.setMes(Integer.parseInt(s[7].replace(" ", "")));				
				if(s[8]!="")
					b.setPeriodo(Integer.parseInt(s[8].replace(" ", "")));
				if(s[9]!="")
					b.setSalarioBase(Double.parseDouble(s[9].replace(" ", "").replace(",", ".")));	
			}
			if(b.getAno()!=0)
				bases.add(b);	
			
		} 
		arq.close(); 
	}
	
	public Boolean verificaMesAnoPeriodoInformado(String mes, String ano, Integer periodo){
		Boolean erro=false;
		int i = 0;
		if(periodo==11)
			i=2;
		if(periodo==31||periodo==32)
			i=3;
		if(periodo==14)
			i=14;
		for(Base b:bases)
			if(b.getAno()!=Integer.parseInt(ano)||b.getMes()!=Integer.parseInt(mes)||b.getPeriodo()!=i)
				erro=true;			
		return erro;
	}

	public void verificaSeExistemRegistrosDeBaseNoBanco(int mes, int ano, int periodo){
		int i = 0;
		if(periodo==11)
			i=2;
		if(periodo==31||periodo==32)
			i=3;
		if(periodo==14)
			i=14;
		BaseDao dao = new BaseDao();
		dao.deletaPorMesAnoPeriodo(mes, ano, i);
	}

	public void verificaSeExistemRegistrosDeVencimentoNoBanco(int mes, int ano, int periodo){
		int i = 0;
		if(periodo==11)
			i=2;
		if(periodo==31||periodo==32)
			i=3;
		if(periodo==14)
			i=14;
		VencimentoDao dao = new VencimentoDao();
		dao.deletaPorMesAnoPeriodo(mes, ano, i);
	}
	
	public void gravarBases(){
		GenericDao<Base> dao = new GenericDao<Base>();
		dao.saveList(bases);		
	}
	
	
	public void gravarVencimentos(int mes, int ano, int periodo){
		GenericDao<Vencimento> daog = new GenericDao<Vencimento>();
		VetorhDao dao = new VetorhDao();
		daog.saveList(dao.buscaEventosFolha(ano, mes, periodo));		
	}	
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public static String getCaminhoParaSalvarArquivoImportado() {
		return CAMINHO_PARA_SALVAR_ARQUIVO_IMPORTADO;
	}

	public Properties getProps() {
		return props;
	}

	public void setProps(Properties props) {
		this.props = props;
	}

	public String getNomeDoProperties() {
		return nomeDoProperties;
	}

	public void setNomeDoProperties(String nomeDoProperties) {
		this.nomeDoProperties = nomeDoProperties;
	}

	public List<Base> getBases() {
		return bases;
	}

	public void setBases(List<Base> bases) {
		this.bases = bases;
	}

	public static String getCaminhoProperties() {
		return CAMINHO_PROPERTIES;
	}
	
}
