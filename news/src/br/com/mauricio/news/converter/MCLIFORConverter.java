package br.com.mauricio.news.converter;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import br.com.mauricio.news.ln.GenericLN;
import br.com.mauricio.news.model.MCLIFOR;

@FacesConverter("mcliforConverter")
public class MCLIFORConverter implements Converter{

	   public MCLIFOR getAsObject(FacesContext fc, UIComponent uic, String value) {
			if(value != null && value.trim().length() > 0) {
				try {
					MCLIFOR selecionado = new MCLIFOR();
					GenericLN<MCLIFOR> gln = new GenericLN<MCLIFOR>();
					List<MCLIFOR> list = gln.listWithoutRemoved("mclifor", "id");
					for(MCLIFOR t:list)
						if(t.getId() ==Integer.parseInt(value))
							selecionado=t;
				    return selecionado;
				} catch(NumberFormatException e) {
				    throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro na conversão do Cliente ou Fornecedor (mclifor)", "clifor não válido."));
				}
			}else {
			    return null;
			}
	    }
	 
	    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
	        if(object != null) 
	            return String.valueOf(((MCLIFOR) object).getId());	        
	        else 
	            return null;	        
	    } 	    

}
