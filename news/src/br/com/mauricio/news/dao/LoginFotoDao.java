package br.com.mauricio.news.dao;

import java.io.File;
import java.io.IOException;

import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;

import org.apache.commons.io.FileUtils;
import br.com.mauricio.news.model.Login;
import br.com.mauricio.news.util.SaveFile;
import sun.misc.BASE64Decoder;

/**
*
* @author MAURICIO CRUZ
*/
public class LoginFotoDao {
	private EntityManagerFactory factory;
	private EntityManager manager;

	
	public void userFoto(Login login){
		abrirConexao();
		Integer chapa = Integer.parseInt(login.getChapa());
		String sql = "select fotemp from r034fot where numcad= :chapa";
		byte[] foto=null;
        String path = FacesContext.getCurrentInstance().getExternalContext().getRealPath("");
        SaveFile.criarPasta(path+"\\sistema\\tmp\\");
        String dest = path+"\\sistema\\tmp\\"+login.getCpf()+".jpg";
		try {
			foto = (byte[]) manager.createNativeQuery(sql).setParameter("chapa", chapa).getSingleResult();
			FileUtils.writeByteArrayToFile(new File(dest), foto);	
		} catch (NoResultException e) {
			BASE64Decoder decoder = new BASE64Decoder();
			try {
				foto = decoder.decodeBuffer(foto());
				FileUtils.writeByteArrayToFile(new File(dest), foto);
			} catch (IOException e1) {
				foto=null;
			}
		}catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
		}
		fechaConexao();	
	}
	
	private EntityManager abrirConexao(){
		this.factory = Persistence.createEntityManagerFactory("vetorh");
		this.manager = this.factory.createEntityManager();
		this.manager.getTransaction().begin();
		return this.manager;
		
	}
	
	private void fechaConexao(){
		this.manager.close();
		this.factory.close();		
	}	
	
	
	private String foto(){
		return "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"
				+ "RHUtKS0OAAAAAElFTkSuQmCC";
	}
}
