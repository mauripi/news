/**
*
* @author MAURICIO CRUZ
*/package br.com.mauricio.news.ln;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import br.com.mauricio.news.dao.AcessoDao;
import br.com.mauricio.news.dao.GenericDao;
import br.com.mauricio.news.dao.LoginDao;
import br.com.mauricio.news.dao.rh.BaseDao;
import br.com.mauricio.news.model.CCusto;
import br.com.mauricio.news.model.Filial;
import br.com.mauricio.news.model.Login;
import br.com.mauricio.news.model.Modulo;
import br.com.mauricio.news.util.Cripto;
import br.com.mauricio.news.util.UsuarioLogado;

public class LoginLN implements Serializable{

	private static final long serialVersionUID = 7471463119119687066L;
	private String msg;
	private Login login = new Login();

	
	//verifica se usuario tem permissão de acesso
	public Boolean autorizaLogin(String cpf, String senha){
		boolean autoriza = false;
		LoginDao dao = new LoginDao();
		autoriza=dao.validaLogin(cpf, senha);
		if(autoriza==true){//se tiver acesso coloca usuario na sessao
			this.login = dao.retornaUsuario(cpf, senha);
			FacesContext cx = FacesContext.getCurrentInstance();
	        HttpSession sessao = (HttpSession) cx.getExternalContext().getSession(false);
	        sessao.setAttribute("login", this.login);
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
			msg = "Usuário cadastrado com sucesso.";
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage() + " -- Erro na Adição do Usuário"+e.getMessage());
			e.printStackTrace();
			msg = "Erro ao adicionar. Verifique se os campos foram preenchidos, caso persista, feche o sistema e tente novamente";
		} 

		return msg;
	}

	public String atualiza(Login l){
		GenericDao<Login> dao = new GenericDao<Login>();
		GenericDao<CCusto> daoc = new GenericDao<CCusto>();
		GenericDao<Filial> daof = new GenericDao<Filial>();
		l.setCusto(daoc.findById(l.getCusto().getClass(), l.getCusto().getId()));
		l.setFilial(daof.findById(l.getFilial().getClass(), l.getFilial().getId()));
		removeModulos(l);
		removePermissao(l);
		List<Modulo> modulos =l.getAcessos(); 
		l.setAcessos(new ArrayList<Modulo>());
		GenericDao<Modulo> daom = new GenericDao<Modulo>();
		for(Modulo m:modulos)
			l.getAcessos().add(daom.findById(Modulo.class, m.getId()));
		
		List<Login> logins =l.getUsuariosPrestacao(); 
		l.setUsuariosPrestacao(new ArrayList<Login>());
		GenericDao<Login> daol = new GenericDao<Login>();
		for(Login lg:logins)
			l.getUsuariosPrestacao().add(daol.findById(Login.class, lg.getId()));

		dao.update(l);
		msg = "Usuário atualizado com sucesso.";
		try {
			
		} catch (Exception e) {
			System.out.println(e.getMessage() + " -- Erro na Atualização do Usuário");
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
			msg = "Usuário excluido com sucesso.";
		} catch (Exception e) {
			System.out.println(e.getMessage() + " -- Erro na exclusao do usuário");
			msg = "O registro não pode ser excluido. Talvez ele esteja associado a outros registros no sistema.";
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
