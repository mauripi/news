package br.com.mauricio.news.converter;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import br.com.mauricio.news.ln.GenericLN;
import br.com.mauricio.news.model.marketing.ProgramaIbope;

@FacesConverter("programaibopeConverter")
public class ProgramaIbopeConverter implements Converter{

	   public ProgramaIbope getAsObject(FacesContext fc, UIComponent uic, String value) {
		        if(value != null && value.trim().length() > 0) {
	            try {
	        		GenericLN<ProgramaIbope> gln = new GenericLN<ProgramaIbope>();
	            	List<ProgramaIbope> list =  gln.listWithoutRemoved("programaibope", "id");
	            	for(ProgramaIbope l:list){
	            		if(l.getId()==Integer.parseInt(value)){
	                		return l; 
	            		}
	            	}
	            	return null;
	            } catch(NumberFormatException e) {
	                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro na conversão do programaibope", "programaibope não válido."));
	            }
	        }
	        else {
	            return null;
	        }
	    }
	 
	    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
	        if(object != null) {
	            return String.valueOf(((ProgramaIbope) object).getId());
	        }
	        else {
	            return null;
	        }
	    } 
	    

}
