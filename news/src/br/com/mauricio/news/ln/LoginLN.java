/**
*
* @author MAURICIO CRUZ
*/package br.com.mauricio.news.ln;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

import br.com.mauricio.news.dao.AcessoDao;
import br.com.mauricio.news.dao.GenericDao;
import br.com.mauricio.news.dao.LoginDao;
import br.com.mauricio.news.dao.LoginFotoDao;
import br.com.mauricio.news.dao.rh.BaseDao;
import br.com.mauricio.news.model.CCusto;
import br.com.mauricio.news.model.Filial;
import br.com.mauricio.news.model.Login;
import br.com.mauricio.news.model.Modulo;
import br.com.mauricio.news.util.Cripto;
import br.com.mauricio.news.util.SendMail;
import br.com.mauricio.news.util.UsuarioLogado;

public class LoginLN implements Serializable{

	private static final long serialVersionUID = 7471463119119687066L;
	private String msg;
	private Login login = new Login();

	
	//verifica se usuario tem permiss�o de acesso
	public Boolean autorizaLogin(String cpf, String senha){
		boolean autoriza = false;
		LoginDao dao = new LoginDao();
		autoriza=dao.validaLogin(cpf, senha);
		if(autoriza==true){//se tiver acesso coloca usuario na sessao
			login = dao.retornaUsuario(cpf, senha);
			FacesContext cx = FacesContext.getCurrentInstance();
	        HttpSession sessao = (HttpSession) cx.getExternalContext().getSession(false);
	        sessao.setAttribute("login", login);
			LoginFotoDao daoL = new LoginFotoDao();
			daoL.userFoto(login);	        
		}
		return autoriza;		
	}

	public String adiciona(Login l){
		try {
			GenericDao<CCusto> daoc = new GenericDao<CCusto>();
			GenericDao<Filial> daof = new GenericDao<Filial>();
			GenericDao<Login> dao = new GenericDao<Login>();
			l.setCusto(daoc.findById(l.getCusto().getClass(), l.getCusto().getId()));
			l.setFilial(daof.findById(l.getFilial().getClass(), l.getFilial().getId()));
			l.setSenha(Cripto.criptografa(l.getCpf().substring(0, 4)));
			dao.save(l);
			l.setAcessos(getModulosDefault());
			atualiza(l);
			msg = "Usu�rio cadastrado com sucesso.";
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage() + " -- Erro na Adi��o do Usu�rio"+e.getMessage());
			e.printStackTrace();
			msg = "Erro ao adicionar. Verifique se os campos foram preenchidos, caso persista, feche o sistema e tente novamente";
		} 

		return msg;
	}

	public String atualiza(Login l){
		try {
			GenericDao<Login> dao = new GenericDao<Login>();
			dao.update(l);
			msg = "Usu�rio atualizado com sucesso.";
			
			
		} catch (Exception e) {
			System.out.println(e.getMessage() + " -- Erro na Atualiza��o do Usu�rio");
			msg = "Erro ao atualizar. Verifique se os campos foram preenchidos, caso persista, feche o sistema e tente novamente";
		}
		return msg;
	}

	public String remove(Login l){
		try {
			GenericDao<Login> dao = new GenericDao<Login>();
			removeModulos(l);
			removePermissao(l);
			dao.delete(l);
			msg = "Usu�rio excluido com sucesso.";
		} catch (Exception e) {
			System.out.println(e.getMessage() + " -- Erro na exclusao do usu�rio");
			msg = "O registro n�o pode ser excluido. Talvez ele esteja associado a outros registros no sistema.";
		}
		return msg;		
	}

	public Login findById(Login l){
		GenericDao<Login> gdao = new GenericDao<Login>();
		return gdao.findById(Login.class, l.getId());
	}
	
	public List<Login> listaUsuarios(String orderBy){
		GenericDao<Login> dao = new GenericDao<Login>();
		return dao.listWithoutRemoved("login", orderBy);		
	}

	public List<Login> listaUsuariosAutoComplete(String query){
		LoginDao dao = new LoginDao();
		return dao.findUserByQuery(query);		
	}
	
	public List<Login> listAtivos(){
		LoginDao dao = new LoginDao();
		return dao.listAtivos();		
	}
	
	public String reiniciarSenha(Login l){
		l.setTrocarSenha(1);
		l.setDataAlteracao(new Date(System.currentTimeMillis()));
		l.setUsuarioAlteracao(UsuarioLogado.getUser());
		GenericDao<Login> dao = new GenericDao<Login>();
		l = dao.findById(Login.class, l.getId());
		try {
			l.setSenha(Cripto.criptografa(l.getCpf().substring(0, 4)));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		atualiza(l);
		return "Senha reiniciada.";		
	}
	
 	public String getFuncao(Login l){
		BaseDao dao = new BaseDao();
		return dao.getUltimaFuncao(l);		
	}	

 	public List<Modulo> getModulosDefault(){
 		ModuloLN ln = new ModuloLN();
 		List<Modulo> list = new ArrayList<Modulo>();
 		List<Modulo> allModulos  = ln.listaModulos();
 		for (Modulo m: allModulos)
 			if(m.getNome().equals("holerite")||m.getNome().equals("impostoderenda")||m.getNome().equals("prestacaodecontas"))
 				list.add(m);		
		return list; 		
 	}
 	
	public List<Login> listaColaboradoresAtivos() {
		GenericDao<Login> daoF = new GenericDao<Login>();
		List<Login> loginsTodos = new ArrayList<Login>();
		List<Login> loginsAtivos = new ArrayList<Login>();
		loginsTodos = daoF.list("login", "nome");
		for(Login l :loginsTodos)
			if(l.getAtivo().equals(1))
				loginsAtivos.add(l);
		 return loginsAtivos;
	}	
 	
 	private void removeModulos(Login l){
 		AcessoDao dao = new AcessoDao();
 		dao.deletarAcessos(l);
 	}
 	
 	private void removePermissao(Login l){
 		AcessoDao dao = new AcessoDao();
 		dao.deletarPermissaoPrestacao(l);
 	} 	
 	
 	public String recuperaSenha(String cpf){
 		AcessoDao dao = new AcessoDao();
 		Map<Boolean, Login> result = dao.recuperaSenha(cpf);
 		Set<Boolean> chaves = result.keySet();
 		for (Boolean chave : chaves){
 			if(!chave){
 				return "N�o foi poss�vel recuperar sua senha, favor entrar em contato com a TI."; 				
 			}else{
 				try{
 					Login login = result.get(chave);
	 				if(login.getEmail().length()<1 || login.getEmail()==null)
	 					return "N�o foi poss�vel recuperar sua senha, n�o h� email cadastrado. Favor entrar em contato com a TI.";
	 				String senha = Cripto.gerarSenha();
	 				login.setSenha(Cripto.criptografa(senha));
	 				atualiza(findById(login));
	 				msg = enviarEmailSenha(senha,login.getEmail());
 				}catch(Exception e){
 					return "N�o foi poss�vel recuperar sua senha, favor entrar em contato com a TI.";
 				}				
 			}
 		}		
		return msg;
 	}
 	
	private String enviarEmailSenha(String senha, String emailTo) {
	    try {
	    	SimpleEmail email = new SimpleEmail();
	    	email.setSubject( "Recupera��o de senha da Intranet" );
			email.setMsg( "Sua nova senha �: " + senha);
			email.addTo(emailTo);
			SendMail.sendSimple(email);
			return "Sua senha voi enviada para: " + emailTo;
		} catch (EmailException e) {
			e.printStackTrace();
			return "N�o foi poss�vel enviar o emial com a nova senha. Entre em contato com a TI";
		}		
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Login getLogin() {
		return login;
	}

	public void setLogin(Login login) {
		this.login = login;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
