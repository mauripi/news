package br.com.mauricio.news.converter;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import br.com.mauricio.news.dao.CCustoDao;
import br.com.mauricio.news.model.CCusto;

@FacesConverter("ccustoConverter")
public class CentroCustoConverter  implements Converter{

	   public CCusto getAsObject(FacesContext fc, UIComponent uic, String value) {
	        if(value != null && value.trim().length() > 0) {
	            try {
	            	CCustoDao dao = new CCustoDao();
	            	List<CCusto> list = dao.list();
	            	for(CCusto c:list){
	            		if(c.getId()==Integer.parseInt(value)){
	                		return c;
	            		}
	            	}
	            	return null;
	            } catch(NumberFormatException e) {
	                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro na conversão do Centro Custo", "Centro Custo não válido."));
	            }
	        }
	        else {
	            return null;
	        }
	    }
	 
	    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
	        if(object != null) {
	            return String.valueOf(((CCusto) object).getId());
	        }
	        else {
	            return null;
	        }
	    } 
}
