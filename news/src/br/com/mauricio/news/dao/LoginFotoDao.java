package br.com.mauricio.news.dao;

import java.io.File;
import java.io.IOException;

import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.commons.io.FileUtils;
import br.com.mauricio.news.model.Login;
import br.com.mauricio.news.util.SaveFile;

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
		byte[] foto = (byte[]) manager.createNativeQuery(sql).setParameter("chapa", chapa).getSingleResult();
		fechaConexao();		
		try {
			if (foto!=null){
		        String path = FacesContext.getCurrentInstance().getExternalContext().getRealPath("");
		        SaveFile.criarPasta(path+"\\sistema\\tmp\\");
		        String dest = path+"\\sistema\\tmp\\"+login.getCpf()+".jpg";
				FileUtils.writeByteArrayToFile(new File(dest), foto);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
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
}
