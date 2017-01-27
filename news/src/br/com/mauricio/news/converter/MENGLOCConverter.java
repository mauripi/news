package br.com.mauricio.news.converter;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import br.com.mauricio.news.ln.GenericLN;
import br.com.mauricio.news.model.engenharia.MENGLOC;

@FacesConverter("menglocConverter")
public class MENGLOCConverter implements Converter{

	   public MENGLOC getAsObject(FacesContext fc, UIComponent uic, String value) {
			if(value != null && value.trim().length() > 0) {
				try {
					MENGLOC selecionado = new MENGLOC();
					GenericLN<MENGLOC> gln = new GenericLN<MENGLOC>();
					List<MENGLOC> list = gln.listWithoutRemoved("mengloc", "id");
					for(MENGLOC t:list)
						if(t.getId() ==Integer.parseInt(value))
							selecionado=t;
				    return selecionado;
				} catch(NumberFormatException e) {
				    throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro na conversão do Local (mengloc)", "Praça não válido."));
				}
			}else {
			    return null;
			}
	    }
	 
	    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
	        if(object != null) 
	            return String.valueOf(((MENGLOC) object).getId());	        
	        else 
	            return null;	        
	    } 	    

}
