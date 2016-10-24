package br.com.mauricio.news.converter;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import br.com.mauricio.news.ln.GenericLN;
import br.com.mauricio.news.model.engenharia.Praca;

@FacesConverter("pracaConverter")
public class PracaConverter implements Converter{

	   public Praca getAsObject(FacesContext fc, UIComponent uic, String value) {
			if(value != null && value.trim().length() > 0) {
				try {
					Praca selecionado = new Praca();
					GenericLN<Praca> gln = new GenericLN<Praca>();
					List<Praca> list = gln.listWithoutRemoved("praca", "id");
					for(Praca t:list)
						if(t.getId() ==Integer.parseInt(value))
							selecionado=t;
				    return selecionado;
				} catch(NumberFormatException e) {
				    throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro na convers�o da Pra�a", "Pra�a n�o v�lido."));
				}
			}else {
			    return null;
			}
	    }
	 
	    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
	        if(object != null) 
	            return String.valueOf(((Praca) object).getId());	        
	        else 
	            return null;	        
	    } 	    

}
