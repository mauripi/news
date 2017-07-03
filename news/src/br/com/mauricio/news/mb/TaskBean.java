package br.com.mauricio.news.mb;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.component.organigram.OrganigramHelper;
import org.primefaces.event.organigram.OrganigramNodeCollapseEvent;
import org.primefaces.event.organigram.OrganigramNodeDragDropEvent;
import org.primefaces.event.organigram.OrganigramNodeExpandEvent;
import org.primefaces.event.organigram.OrganigramNodeSelectEvent;
import org.primefaces.model.DefaultOrganigramNode;
import org.primefaces.model.OrganigramNode;

import br.com.mauricio.news.ln.GenericLN;
import br.com.mauricio.news.ln.TaskLN;
import br.com.mauricio.news.model.Task;

@ViewScoped
@ManagedBean(name="taskMB")
public class TaskBean implements Serializable{

	private static final long serialVersionUID = 1L;
	private OrganigramNode rootNode;
    private OrganigramNode selection;
    private Task task; 
    private Task projeto; 
    private List<Task> projetos;
    
    
    public void novo(){
    	projeto = new Task();
    	projeto.setPretask(null);	  	
    }
    
    public void novaTask(){
    	task = new Task();
    	task.setPretask(null);
    }
    
    public void criarNovoProjeto(){
        rootNode = new DefaultOrganigramNode("root", projeto, null);
        rootNode.setCollapsible(true);
        rootNode.setDroppable(true);
        rootNode.setSelectable(true);
        selection = rootNode;
        save(projeto);
        novaTask();
      }
 
    public void listarProjetos(){
    	TaskLN ln = new TaskLN();
		projetos = ln.listarProjetos();
    }

    private void save(Task t){
    	GenericLN<Task> gln = new GenericLN<Task>();
    	Task pai = null;
    	if(t.getPretask()!=null)
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
        System.out.println();
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
 
	private static void montaListaDoProjeto(Task t){
		for(Task x : t.getTasks())
			montaListaDoProjeto(x);			
	}   
    
    
    public void nodeDragDropListener(OrganigramNodeDragDropEvent event) {
        
    }
 
    public void nodeSelectListener(OrganigramNodeSelectEvent event) {
        
    }
 
    public void nodeCollapseListener(OrganigramNodeCollapseEvent event) {
        
    }
 
    public void nodeExpandListener(OrganigramNodeExpandEvent event) {
        
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
    
    
    
    
    
}
