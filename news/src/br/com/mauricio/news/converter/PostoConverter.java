package br.com.mauricio.news.converter;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import br.com.mauricio.news.ln.GenericLN;
import br.com.mauricio.news.model.engenharia.Posto;

@FacesConverter("postoConverter")
public class PostoConverter  implements Converter{

	   public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
	        if(value != null && value.trim().length() > 0) {
	            try {
	            	Posto posto = new Posto();
	        		GenericLN<Posto> gln = new GenericLN<Posto>();
	        		List<Posto> postos = gln.listWithoutRemoved("posto", "id");
	        		for(Posto x:postos){
	        			if(x.getId().equals(Integer.parseInt(value))){
	        				posto = x;
	        				break;
	        			}
	        		}
	                return posto;
	            } catch(NumberFormatException e) {
	                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro na conversão do Posto", "Posto não válido."));
	            }
	        }
	        else {
	            return null;
	        }
	    }
	 
	    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
	        if(object != null) {
	            return String.valueOf(((Posto) object).getId());
	        }
	        else {
	            return null;
	        }
	    } 
}
