package br.com.mauricio.news.converter;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import br.com.mauricio.news.ln.GenericLN;
import br.com.mauricio.news.model.TipoContrato;

@FacesConverter("tipoContratoConverter")
public class TipoContratoConverter  implements Converter{

	   public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
	        if(value != null && value.trim().length() > 0) {
	            try {
	            	TipoContrato selecionado = new TipoContrato();
	            	GenericLN<TipoContrato> gtln = new GenericLN<TipoContrato>();
	            	List<TipoContrato> tipos = gtln.listWithoutRemoved("tipocontrato", "descricao");
	            	for(TipoContrato t:tipos)
	            		if(t.getId() ==Integer.parseInt(value))
	            			selecionado=t;
	                return selecionado;
	            } catch(NumberFormatException e) {
	                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro na conversão do Tipo de Contrato", "Tipo de Contrato não válido."));
	            }
	        }
	        else {
	            return null;
	        }
	    }
	 
	    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
	        if(object != null) {
	            return String.valueOf(((TipoContrato) object).getId());
	        }
	        else {
	            return null;
	        }
	    } 
}
