package br.com.mauricio.news.mb.ti;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import br.com.mauricio.news.ln.GenericLN;
import br.com.mauricio.news.model.ti.Licenca;
import br.com.mauricio.news.util.UsuarioLogado;
/**
*
* @author MAURICIO CRUZ
*/
@ManagedBean(name="licencaMB")
@ViewScoped
public class LicencaBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private Licenca lic = new Licenca();
	private Licenca licSel = new Licenca();
	private GenericLN<Licenca> gln = new GenericLN<Licenca>();
	private List<Licenca> licencas = new ArrayList<Licenca>();
	private int controlaCadastro = 0;
	private String msg;
	
	@PostConstruct
	public void init(){
		lic = new Licenca();
		listar();		
	}

	public void listar(){
		GenericLN<Licenca> gln = new GenericLN<Licenca>();
		licencas = gln.listAll(new Licenca(), "descricao");
	}

	public void novo(){
		lic = new Licenca();
		controlaCadastro=1;
	}

	public void edita(){
		controlaCadastro=2;
	}

	public void exclui(){
		if(lic.getId()==null){
			msg = "Nenhum registro selecionado para exclusão.";
			mensagens();			
		}else{
			gln = new GenericLN<Licenca>();
			lic.setRemovido(1);
			lic.setDataAlteracao(new Date(System.currentTimeMillis()));
			lic.setUsuarioAlteracao(UsuarioLogado.getUser());
			msg = gln.update(lic);
			mensagens();
			limpaCadastro();
			listar();
		}		
	}

	
	public void grava(){		
		if(validaCampos()){
			gln = new GenericLN<Licenca>();
			if(controlaCadastro==1){
				lic.setDataCriacao(new Date(System.currentTimeMillis()));			
				lic.setUsuarioCriacao(UsuarioLogado.getUser());
				lic.setRemovido(0);
				lic.setQtdDisponivel(lic.getQtd());
				msg = gln.add(lic);
			}
			if(controlaCadastro==2)	{	
				lic.setDataAlteracao(new Date(System.currentTimeMillis()));
				lic.setUsuarioAlteracao(UsuarioLogado.getUser());	
				msg = gln.update(lic);
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
		if(lic.getDescricao()=="")
			return false;
		return true;		
	}
	
	public void limpaCadastro(){
		lic = licSel = new Licenca();
		controlaCadastro=0;
	}

	public void selecao(){
		lic = licSel;
		controlaCadastro=0;
		edita();
	}
	    
	public void mensagens(){
        FacesContext context = FacesContext.getCurrentInstance();  	          
        context.addMessage(null, new FacesMessage("",msg));  		
	}
	//--------------------------GETTERS E SETTERS ----------------------------------

	public Licenca getLic() {
		return lic;
	}

	public void setLic(Licenca lic) {
		this.lic = lic;
	}

	public Licenca getLicSel() {
		return licSel;
	}

	public void setLicSel(Licenca licSel) {
		this.licSel = licSel;
	}

	public GenericLN<Licenca> getGln() {
		return gln;
	}

	public void setGln(GenericLN<Licenca> gln) {
		this.gln = gln;
	}

	public List<Licenca> getLicencas() {
		return licencas;
	}

	public void setLicencas(List<Licenca> licencas) {
		this.licencas = licencas;
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

}
