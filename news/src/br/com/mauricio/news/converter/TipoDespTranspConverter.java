package br.com.mauricio.news.converter;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import br.com.mauricio.news.ln.GenericLN;
import br.com.mauricio.news.model.transporte.TipoDespTransp;

@FacesConverter("tipoDespTranspConverter")
public class TipoDespTranspConverter implements Converter{

	   public TipoDespTransp getAsObject(FacesContext fc, UIComponent uic, String value) {
		        if(value != null && value.trim().length() > 0) {
	            try {
	            	TipoDespTransp selecionado = new TipoDespTransp();
	            	GenericLN<TipoDespTransp> gln = new GenericLN<TipoDespTransp>();
	            	List<TipoDespTransp> list = gln.listWithoutRemoved("tipodesptransp", "id");
	            	for(TipoDespTransp t:list)
	            		if(t.getId() ==Integer.parseInt(value))
	            			selecionado=t;
	                return selecionado;
	            } catch(NumberFormatException e) {
	                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro na conversão do Tipo Desp Transp", "Tipo de Despesa não válido."));
	            }
	        }
	        else {
	            return null;
	        }
	    }
	 
	    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
	        if(object != null) {
	            return String.valueOf(((TipoDespTransp) object).getId());
	        }
	        else {
	            return null;
	        }
	    } 
	    

}
