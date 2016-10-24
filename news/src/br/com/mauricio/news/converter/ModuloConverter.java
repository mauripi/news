package br.com.mauricio.news.converter;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import br.com.mauricio.news.ln.GenericLN;
import br.com.mauricio.news.model.Modulo;

@FacesConverter("moduloConverter")
public class ModuloConverter implements Converter{

	   public Modulo getAsObject(FacesContext fc, UIComponent uic, String value) {
		        if(value != null && value.trim().length() > 0) {
	            try {
	        		GenericLN<Modulo> glm = new GenericLN<Modulo>();
	        		List<Modulo> list =  glm.listWithoutRemoved("modulo", "id");
	            	for(Modulo m:list)
	            		if(m.getId()==Integer.parseInt(value))
	                		return m; 

	            	return null;
	            } catch(NumberFormatException e) {
	                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro na conversão do Modulo", "Modulo não válido."));
	            }
	        }
	        else {
	            return null;
	        }
	    }
	 
	    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
	        if(object != null) {
	            return String.valueOf(((Modulo) object).getId());
	        }
	        else {
	            return null;
	        }
	    } 
	    

}
