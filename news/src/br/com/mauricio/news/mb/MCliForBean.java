package br.com.mauricio.news.mb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import br.com.mauricio.news.ln.GenericLN;
import br.com.mauricio.news.ln.MCLIFORLN;
import br.com.mauricio.news.model.Endereco;
import br.com.mauricio.news.model.MCLIFOR;
import br.com.mauricio.news.util.ValidaCNPJ;
import br.com.mauricio.news.util.ValidaCPF;
import br.com.mauricio.news.util.VerificaString;
import br.com.mauricio.news.util.WebServiceCep;

@ManagedBean(name = "cliforBean")
@ViewScoped
public class MCliForBean implements Serializable{

	private static final long serialVersionUID = 1L;
	private List<MCLIFOR> allCliFor = new ArrayList<MCLIFOR>();
	private MCLIFOR clifor;
    @ManagedProperty("#{mcliforService}")
    private MCLIFORService service;	
    
    @PostConstruct
    public void init() {
    	allCliFor = service.getAllCliFor();
    	clifor = new MCLIFOR();
    }
    
    public List<MCLIFOR> completeCliFor(String query) {       
        List<MCLIFOR> filteredCliFor = new ArrayList<MCLIFOR>();
         
        for (int i = 0; i < allCliFor.size(); i++) {
        	MCLIFOR skin = allCliFor.get(i);
        	if(VerificaString.isInt(query)){
        		if(skin.getCgccpf().toString().startsWith(query.toLowerCase())) 
        			filteredCliFor.add(skin);         		
        	}else{
        		if(skin.getNomfan().toLowerCase().contains(query.toLowerCase())) 
        			filteredCliFor.add(skin);        		      		
        	}
        }         
        return filteredCliFor;
    }

    public void selectCliForFromDialog(MCLIFOR cf) {
        RequestContext.getCurrentInstance().closeDialog(cf);
    }

    public void cancelCadCliForFromDialog() {
        RequestContext.getCurrentInstance().closeDialog(new MCLIFOR());
    }   
    
    public void salvarCadCliForFromDialog() {
    	if(validar()){
	    	gravar();
	    	service.init();
	        RequestContext.getCurrentInstance().closeDialog(findCliFor());
    	}
    }     
    
    private boolean validar() {
    	boolean isValid=true;
    	String tmpCnpjCpf = clifor.getCgccpf().toString();
		int tam = tmpCnpjCpf.length();
		if (tam>0 && tam<=11){
			if(!ValidaCPF.isValidCPF(tmpCnpjCpf)){
				isValid=false;
				mensagens("Verifique o CNPJ ou CPF.");
			}
		}
		if (tam>11 && tam<=14){
			if(!ValidaCNPJ.isValidCNPJ(tmpCnpjCpf)){
				isValid=false;
				mensagens("Verifique o CNPJ ou CPF.");
			}
		}		
		if(clifor.getNomfan().length()==0){
			isValid=false;
			mensagens("Verifique a Razão.");
		}else{
			clifor.setNomraz(clifor.getNomfan());
		}
		return isValid;
	}

    private MCLIFOR findCliFor(){
    	MCLIFORLN ln = new MCLIFORLN();
    	return ln.findByNomeCnpj(clifor.getNomfan(), clifor.getCgccpf());
    }
    
	private void gravar(){
    	GenericLN<MCLIFOR> gln = new GenericLN<MCLIFOR>();
    	if(clifor.getId()!=null){
    		gln.add(clifor);
    	}else{
    		gln.update(clifor);
    	}  		
    }
 
	public void localizaEnderecoPorCep(){
		Endereco endereco = WebServiceCep.buscaCep(clifor.getEndcep().toString());
		if(endereco != null){
			clifor.setEndcep(endereco.getCep());
			clifor.setEndrua(endereco.getLogradouro());
			clifor.setEndbai(endereco.getBairro());
			clifor.setEndcid(endereco.getCidade());
			clifor.setEndest(endereco.getUf());
		}else{
			mensagens("Endereço não localizado.");
		}
	}
	
	public void onRowSelect(SelectEvent event) {
		clifor = (MCLIFOR) event.getObject();
    }
    
	public void mensagens(String msg){
        FacesContext context = FacesContext.getCurrentInstance();  	          
        context.addMessage(null, new FacesMessage(msg,msg));  		
	}
/*======================================================*/
	public List<MCLIFOR> getAllCliFor() {
		return allCliFor;
	}
	public void setAllCliFor(List<MCLIFOR> allCliFor) {
		this.allCliFor = allCliFor;
	}
	public MCLIFOR getClifor() {
		return clifor;
	}
	public void setClifor(MCLIFOR clifor) {
		this.clifor = clifor;
	}
	public void setService(MCLIFORService service) {
		this.service = service;
	}

}
