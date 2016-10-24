package br.com.mauricio.news.converter;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import br.com.mauricio.news.ln.GenericLN;
import br.com.mauricio.news.model.compra.Cliente;

@FacesConverter("clienteConverter")
public class ClienteConverter  implements Converter{

	   public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
	        if(value != null && value.trim().length() > 0) {
	            try {
	            	Cliente selecionado = new Cliente();
	            	GenericLN<Cliente> gln = new GenericLN<Cliente>();
	            	List<Cliente> list = gln.listWithoutRemoved("cliente", "id");
	            	for(Cliente t:list)
	            		if(t.getId() ==Integer.parseInt(value))
	            			selecionado=t;
	                return selecionado;
	            } catch(NumberFormatException e) {
	                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro na conversão do Cliente", "Cliente não válido."));
	            }
	        }
	        else {
	            return null;
	        }
	    }
	 
	    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
	        if(object != null) {
	            return String.valueOf(((Cliente) object).getId());
	        }
	        else {
	            return null;
	        }
	    } 
}
