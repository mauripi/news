package br.com.mauricio.news.mb.compra;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.CellEditEvent;

import br.com.mauricio.news.ln.GenericLN;
import br.com.mauricio.news.model.compra.Cliente;
import br.com.mauricio.news.model.compra.ContatoCli;
import br.com.mauricio.news.util.UsuarioLogado;

@ManagedBean(name="clienteMB")
@ViewScoped
public class ClienteBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private Cliente cli = new Cliente();
	private Cliente cliSel = new Cliente();
	private GenericLN<Cliente> gln = new GenericLN<Cliente>();
	private List<Cliente> clientes = new ArrayList<Cliente>();
	private int controlaCadastro = 0;
	private String msg;
	private List<ContatoCli> contatos = new ArrayList<ContatoCli>();
	
	@PostConstruct
	public void init(){
		cli = new Cliente();
		listar();		
	}

	public void listar(){
		GenericLN<Cliente> gln = new GenericLN<Cliente>();
		clientes = gln.listWithoutRemoved("cliente", "nome");
	}

	public void novo(){
		cli = new Cliente();
		controlaCadastro=1;
	}

	public void edita(){
		controlaCadastro=2;
	}

	public void exclui(){
		if(cli.getId()==null){
			msg = "Nenhum registro selecionado para exclusão.";
			mensagens();			
		}else{
			gln = new GenericLN<Cliente>();
			cli.setRemovido(1);
			cli.setDataAlteracao(new Date(System.currentTimeMillis()));
			cli.setUsuarioAlteracao(UsuarioLogado.getUser());
			msg = gln.update(cli);
			mensagens();
			limpaCadastro();
			listar();
		}		
	}

	
	public void grava(){		
		if(validaCampos()){
			gln = new GenericLN<Cliente>();
			if(controlaCadastro==1){
				cli.setDataCriacao(new Date(System.currentTimeMillis()));			
				cli.setUsuarioCriacao(UsuarioLogado.getUser());
				msg = gln.add(cli);
			}
			if(controlaCadastro==2)	{	
				cli.setContatos(new ArrayList<ContatoCli>());
				cli.getContatos().addAll(contatos);
				cli.setDataAlteracao(new Date(System.currentTimeMillis()));
				cli.setUsuarioAlteracao(UsuarioLogado.getUser());	
				msg = gln.update(cli);
			}
			mensagens();
			listar();
			limpaCadastro();	
		}else{
			msg = "Favor preencha todos campos com *, pois são obrigatórios.";
			mensagens();
		}
	}
	
	private boolean validaCampos(){
		if(cli.getNome()=="")
			return false;
		return true;		
	}
	
	public void limpaCadastro(){
		cliSel = cli = new Cliente();
		contatos = new ArrayList<ContatoCli>();
		controlaCadastro=0;
	}

	public void selecao(){
		cli = cliSel;
		contatos = new ArrayList<ContatoCli>();
		contatos.addAll(cli.getContatos());
		controlaCadastro=0;
	}
	public void newContact(){
		ContatoCli c = new ContatoCli();
		if(getContatos()==null){
			setContatos(new ArrayList<ContatoCli>());
			c.setCliente(cli);
			getContatos().add(c);
		}else{
			c.setCliente(cli);
			getContatos().add(c);
		}
	}
	
    public void onCellEdit(CellEditEvent event) {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();
         
        if(newValue != null && !newValue.equals(oldValue)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cell Changed", "Old: " + oldValue + ", New:" + newValue);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
    
	public void mensagens(){
        FacesContext context = FacesContext.getCurrentInstance();  	          
        context.addMessage(null, new FacesMessage("",msg));  		
	}
	//--------------------------GETTERS E SETTERS ----------------------------------
	public Date getToday() {
        return new Date();
    }

	public Cliente getCli() {
		return cli;
	}

	public void setCli(Cliente cli) {
		this.cli = cli;
	}

	public Cliente getCliSel() {
		return cliSel;
	}

	public void setCliSel(Cliente cliSel) {
		this.cliSel = cliSel;
	}

	public GenericLN<Cliente> getGln() {
		return gln;
	}

	public void setGln(GenericLN<Cliente> gln) {
		this.gln = gln;
	}

	public List<Cliente> getClientes() {
		return clientes;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}

	public int getControlaCadastro() {
		return controlaCadastro;
	}

	public void setControlaCadastro(int controlaCadastro) {
		this.controlaCadastro = controlaCadastro;
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

	public List<ContatoCli> getContatos() {
		return contatos;
	}

	public void setContatos(List<ContatoCli> contatos) {
		this.contatos = contatos;
	}

}
