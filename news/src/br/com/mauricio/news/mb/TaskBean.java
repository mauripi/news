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

import org.primefaces.component.organigram.OrganigramHelper;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.organigram.OrganigramNodeCollapseEvent;
import org.primefaces.event.organigram.OrganigramNodeDragDropEvent;
import org.primefaces.event.organigram.OrganigramNodeExpandEvent;
import org.primefaces.event.organigram.OrganigramNodeSelectEvent;
import org.primefaces.model.DefaultOrganigramNode;
import org.primefaces.model.OrganigramNode;
import br.com.mauricio.news.ln.GenericLN;
import br.com.mauricio.news.ln.TaskLN;
import br.com.mauricio.news.model.Login;
import br.com.mauricio.news.model.Task;
import br.com.mauricio.news.model.UserProject;

@ViewScoped
@ManagedBean(name="taskMB")
public class TaskBean implements Serializable{

	private static final long serialVersionUID = 1L;
	private OrganigramNode rootNode;
    private OrganigramNode selection;
    private Task task; 
    private Task projeto; 
    private List<Task> projetos;
    private OrganigramNode montaNode;
    private Login responsavel;
    private List<Login> allusers;
    private List<UserProject> participantes;
    private UserProject participante;
    private List<UserProject> allParticipantes;
    private List<String> msgs;
    private Login usu;
    
    
    @PostConstruct
    public void init(){
    	listarTodosParticipantes();
    	listarTodosUsuarios();
    }
    
    private void listarTodosParticipantes() {
		GenericLN<UserProject> gln = new GenericLN<UserProject>();
		allParticipantes = gln.listWithoutRemoved("userproject", "id");		
	}
    
    private void listarTodosUsuarios(){
    	GenericLN<Login> gln = new GenericLN<Login>();
    	allusers = gln.listWithoutRemoved("login", "id");		
    }
    
	public void novo(){
    	projeto = new Task();
    	projeto.setPretask(null);
    	participantes = new ArrayList<UserProject>();
    }
    
    public void novaTask(){
    	task = new Task();
    	task.setPretask(null);
    	participantes = new ArrayList<UserProject>();
    }
    
    public void salvarUsuario(){
    	GenericLN<UserProject> gln = new GenericLN<UserProject>();
		if(participante.getNome().length() < 1){
			msgs.add("O campo Nome não pode estar vazio.");
		}else{
			participante.setUsersystem(usu);
			gln.add(participante);
			listarTodosParticipantes();
		}
    }
    
    public List<UserProject> autoCompleteParticpante(String query) {
        List<UserProject> results = new ArrayList<UserProject>();
        for(UserProject l:allParticipantes) 
        	if(l.getNome().toLowerCase().contains(query.toLowerCase()))
        		results.add(l);     
        return results;
    }
 
    public List<Login> autoCompleteResponsavel(String query) {
        List<Login> results = new ArrayList<Login>();
        for(Login l:allusers) 
        	if(l.getNome().toLowerCase().contains(query.toLowerCase()))
        		results.add(l);     
        return results;
    }

    public void novoParticipante(){
    	participante = new UserProject();
    	usu = new Login();   	
    }
    
    public void addParticipante(){
    	participantes.add(participante);
    	participante = new UserProject();
    }
  
    public void onItemSelect(SelectEvent event) {
       usu =  (Login) event.getObject();
       participante.setNome(usu.getNome());
       participante.setEmail(usu.getEmail());
    }
    
    public void criarNovoProjeto(){
        rootNode = new DefaultOrganigramNode("root", projeto, null);
        rootNode.setCollapsible(true);
        rootNode.setDroppable(true);
        rootNode.setSelectable(true);
        selection = rootNode;
        save(projeto);
    	task = new Task();
    	task.setPretask(null);
    	participantes = new ArrayList<UserProject>();
      }
 
    public void listarProjetos(){
    	TaskLN ln = new TaskLN();
		projetos = ln.listarProjetos();
    }

    public void open(Task t){
        rootNode = new DefaultOrganigramNode("root", t, null);
        rootNode.setCollapsible(true);
        rootNode.setDroppable(true);
        rootNode.setSelectable(true);
        selection = rootNode;
        montaNode = null;
        montaListaDoProjeto(t);
    }
    
    private void save(Task t){
    	GenericLN<Task> gln = new GenericLN<Task>();
    	Task pai = null;
 
    	if(t.getPretask() != null)
    		pai = gln.find(new Task(), t.getPretask().getId()); 

    	t.setPretask(pai);
    	gln.add(t);    	
    }
    
    private void remove(Task t){
    	TaskLN ln = new TaskLN();
    	ln.remove(t);
    }

    public void addTask(String type) {
        OrganigramNode currentSelection = OrganigramHelper.findTreeNode(rootNode, selection);
        Task pai=null;
        if(currentSelection!=null){
	        if(currentSelection.getType().equals("root"))
	        	pai = projeto;
        	else
	        	pai = (Task) currentSelection.getData();
        }else{
        	pai=projeto;
        	currentSelection=rootNode;
        }
        OrganigramNode node = new DefaultOrganigramNode(type, task, currentSelection);
        node.setDraggable(true);
        node.setSelectable(true);
        node.setDroppable(true);
        task.setPretask(pai);
        save(task);
        novaTask();
    }
    
    public void removeTask() {    	
        OrganigramNode currentSelection = OrganigramHelper.findTreeNode(rootNode, selection);
        if (currentSelection != null) 
        	if(currentSelection.getParent()!=null)
        		if(currentSelection.getParent().getChildren()!=null){
        			currentSelection.getParent().getChildren().remove(currentSelection);
        
			        Task t = (Task) currentSelection.getData();
			        remove(t);
			        novaTask();
        		}
    }  
 
	private void montaListaDoProjeto(Task t){
		if(t.getPretask() != null) montaNode = new DefaultOrganigramNode("division", t, montaNode );
		else montaNode = rootNode;
		for(Task x : t.getTasks()){
			criaNode(x, montaNode, t);
			montaListaDoProjeto(x);		
		}
	}   

	private void criaNode(Task x, OrganigramNode nodePai, Task t){
        OrganigramNode node = new DefaultOrganigramNode("division", x, nodePai );
        node.setDraggable(true);
        node.setSelectable(true);
        node.setDroppable(true);
    }
	
    public void nodeDragDropListener(OrganigramNodeDragDropEvent event) {
        
    }
 
    public void nodeSelectListener(OrganigramNodeSelectEvent event) {
        
    }
 
    public void nodeCollapseListener(OrganigramNodeCollapseEvent event) {
        
    }
 
    public void nodeExpandListener(OrganigramNodeExpandEvent event) {
        
    }
    
    @SuppressWarnings("unused")
	private void mensagens(List<String> ms) {
        FacesContext context = FacesContext.getCurrentInstance(); 
        for(String m:ms)
            context.addMessage(null, new FacesMessage(m,m));              
    }   
    
    
   /*   Getters and Setters */
	public OrganigramNode getRootNode() {
		return rootNode;
	}

	public void setRootNode(OrganigramNode rootNode) {
		this.rootNode = rootNode;
	}

	public OrganigramNode getSelection() {
		return selection;
	}

	public void setSelection(OrganigramNode selection) {
		this.selection = selection;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public List<Task> getProjetos() {
		return projetos;
	}

	public void setProjetos(List<Task> projetos) {
		this.projetos = projetos;
	}

	public Task getProjeto() {
		return projeto;
	}

	public void setProjeto(Task projeto) {
		this.projeto = projeto;
	}

	public OrganigramNode getMontaNode() {
		return montaNode;
	}

	public void setMontaNode(OrganigramNode montaNode) {
		this.montaNode = montaNode;
	}

	public Login getResponsavel() {
		return responsavel;
	}

	public void setResponsavel(Login responsavel) {
		this.responsavel = responsavel;
	}

	public List<UserProject> getParticipantes() {
		return participantes;
	}

	public void setParticipantes(List<UserProject> participantes) {
		this.participantes = participantes;
	}

	public List<UserProject> getAllParticipantes() {
		return allParticipantes;
	}

	public void setAllParticipantes(List<UserProject> allParticipantes) {
		this.allParticipantes = allParticipantes;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public UserProject getParticipante() {
		return participante;
	}

	public void setParticipante(UserProject participante) {
		this.participante = participante;
	}

	public List<Login> getAllusers() {
		return allusers;
	}

	public void setAllusers(List<Login> allusers) {
		this.allusers = allusers;
	}

	public List<String> getMsgs() {
		return msgs;
	}

	public void setMsgs(List<String> msgs) {
		this.msgs = msgs;
	}

	public Login getUsu() {
		return usu;
	}

	public void setUsu(Login usu) {
		this.usu = usu;
	}
      
}
