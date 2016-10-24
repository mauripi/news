package br.com.mauricio.news.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import br.com.mauricio.news.dao.GenericDao;
import br.com.mauricio.news.dao.LoginDao;
import br.com.mauricio.news.model.Filial;
import br.com.mauricio.news.model.Login;
public class ImportacaoFuncionario {
	
	public List<String> importar(File file){
		List<String> erros = new ArrayList<String>();
		String linha;
		BufferedReader br = null;
		LoginDao lDao = new LoginDao();
		GenericDao<Login> gDao = new GenericDao<Login>();
		
		try {
 			br = new BufferedReader(new FileReader("C:\\funcionarios.txt"));
			while ((linha = br.readLine()) != null) {
				String [] s=linha.split(";");
				if(s.length!=3){
					erros.add("Erro na seguinte linha: (" + linha + ") verifique a quantidade de colunas.");
				}else{
					Login l = new Login();
					boolean t = false;
					t=lDao.exiteLogin(s[2].toString());
					if(t){
						erros.add("A seguinte linha: (" + linha + ") não foi importada, pois já existe o registro.");						
					}else{
						Filial f = new Filial();
						int id =Integer.parseInt(s[2].toString().substring(0, 1));
						if(id==0){
							f.setId(1);
						}else{
							f.setId(2);
						}
						l.setTrocarSenha(1);
						l.setRemovido(0);
						l.setFilial(f);
						l.setChapa(s[2].toString());
						l.setCpf(s[1].toString());
						l.setNome(s[0].toString());
						String pass = s[1].toString().substring(0, 4);

						try {
							l.setSenha(Cripto.criptografa(pass));
						} catch (NoSuchAlgorithmException e) {
							e.printStackTrace();
						}
						 gDao.save(l);
					}
				}
			}
 
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return erros;
	}
}
