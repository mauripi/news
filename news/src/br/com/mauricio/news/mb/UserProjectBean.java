package br.com.mauricio.news.mb;
/**
*
* @author MAURICIO CRUZ and CAROLINE MARQUES
*/

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.SelectEvent;

import br.com.mauricio.news.ln.GenericLN;
import br.com.mauricio.news.model.Login;
import br.com.mauricio.news.model.UserProject;

@ViewScoped
@ManagedBean(name="userProjectMB")
public class UserProjectBean implements Serializable{

	private static final long serialVersionUID = 1L;
	private UserProject userProject;
	private List<UserProject> users;
	private List<Login> userlogins;
	private Login usu;
	private int controlaCadastro = 0;
	private List<String> msgs;
	private String msg;
	
	@PostConstruct
	public void init(){
		lista();
		listaLogin();
	}
	
    public List<Login> completeText(String query) {
        List<Login> results = new ArrayList<Login>();
        for(Login l:userlogins) 
        	if(l.getNome().toLowerCase().contains(query.toLowerCase()))
        		results.add(l);     
        return results;
    }
		
	public void novo(){
		userProject = new UserProject();
		controlaCadastro = 1;
	}

	public void edita(){
		controlaCadastro = 2;
	}

	public void lista(){
		GenericLN<UserProject> gln = new GenericLN<UserProject>();
		users = gln.listWithoutRemoved("userproject", "nome");
	}	
	
	public void listaLogin(){
		GenericLN<Login> gln = new GenericLN<Login>();
		userlogins = gln.listWithoutRemoved("login", "id");
	}	
	
	public void exclui (){
		GenericLN<UserProject> gln = new GenericLN<UserProject>();
		msg = gln.remove(gln.find(new UserProject(), userProject.getId()));
		mensagens();
		lista();
	}
	
	public void limpaCadastro(){
		userProject = new UserProject();
		controlaCadastro = 0;
		lista();
	}
	
	public void grava(){
		userProject.setUsersystem(usu);
		GenericLN<UserProject> gln = new GenericLN<UserProject>();
		if(validar()){
			if(controlaCadastro==1) msg = gln.add(userProject);
			if(controlaCadastro==2) msg = gln.update(userProject);
			mensagens();
			limpaCadastro();
		}
	}
	
	public void selecao(SelectEvent event){
		userProject=(UserProject) event.getObject();
		usu=userProject.getUsersystem();
		edita();
	}
	
	private boolean validar() {
		boolean isValid = true;
		if(userProject.getNome().length() < 1){
			isValid = false;
			msgs.add("O campo Nome não pode estar vazio.");
		}
		return isValid;
	}

    @SuppressWarnings("unused")
	private void mensagens(List<String> ms) {
        FacesContext context = FacesContext.getCurrentInstance(); 
        for(String m:ms)
            context.addMessage(null, new FacesMessage(m,m));              
    }
    
    public void mensagens(){
        FacesContext context = FacesContext.getCurrentInstance();                
        context.addMessage(null, new FacesMessage(msg,""));          
    }
	

	public UserProject getUserProject() {
		return userProject;
	}
	public void setUserProject(UserProject userProject) {
		this.userProject = userProject;
	}
	public List<UserProject> getUsers() {
		return users;
	}
	public void setUsers(List<UserProject> users) {
		this.users = users;
	}
	public List<Login> getUserlogins() {
		return userlogins;
	}
	public void setUserlogins(List<Login> userlogins) {
		this.userlogins = userlogins;
	}
	public Login getUsu() {
		return usu;
	}
	public void setUsu(Login usu) {
		this.usu = usu;
	}
	public int getControlaCadastro() {
		return controlaCadastro;
	}
	public void setControlaCadastro(int controlaCadastro) {
		this.controlaCadastro = controlaCadastro;
	}
	public List<String> getMsgs() {
		return msgs;
	}
	public void setMsgs(List<String> msgs) {
		this.msgs = msgs;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
