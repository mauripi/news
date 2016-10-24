package br.com.mauricio.news.converter;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import br.com.mauricio.news.ln.ti.LicencaLN;
import br.com.mauricio.news.model.ti.Licenca;

@FacesConverter("licencaConverter")
public class LicencaConverter  implements Converter{

	   public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
	        if(value != null && value.trim().length() > 0) {
	            try {
	            	LicencaLN list = new LicencaLN();
	            	List<Licenca> licencas = list.getList();
	            	for(Licenca l:licencas)
	            		if(l.getId()==Integer.parseInt(value))
	            			return l;
	                return null;
	            } catch(NumberFormatException e) {
	                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro na conversão do Tipo de Equipamento", "Tipo de Equipamento não válido."));
	            }
	        }
	        else {
	            return null;
	        }
	    }
	 
	    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
	        if(object != null) {
	            return String.valueOf(((Licenca) object).getId());
	        }
	        else {
	            return null;
	        }
	    } 
}
