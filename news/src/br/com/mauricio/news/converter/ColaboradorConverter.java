package br.com.mauricio.news.converter;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import br.com.mauricio.news.ln.LoginLN;
import br.com.mauricio.news.model.Login;

@FacesConverter("colaboradorConverter")
public class ColaboradorConverter implements Converter{

	   public Login getAsObject(FacesContext fc, UIComponent uic, String value) {
		        if(value != null && value.trim().length() > 0) {
	            try {
	            	LoginLN ln = new LoginLN();
	            	List<Login> list = ln.listaColaboradoresAtivos();
	            	for(Login l:list){
	            		if(l.getId()==Integer.parseInt(value)){
	                		return l; 
	            		}
	            	}
	            	return null;
	            } catch(NumberFormatException e) {
	                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro na conversão do Colaborador", "Colaborador não válido."));
	            }
	        }
	        else {
	            return null;
	        }
	    }
	 
	    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
	        if(object != null) {
	            return String.valueOf(((Login) object).getId());
	        }
	        else {
	            return null;
	        }
	    } 
	    

}
