package br.com.mauricio.news.converter;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import br.com.mauricio.news.dao.GenericDao;
import br.com.mauricio.news.model.ti.TipoEquipamento;

@FacesConverter("tipoEquipamentoConverter")
public class TipoEquipamentoConverter  implements Converter{

	   public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
	        if(value != null && value.trim().length() > 0) {
	            try {
	        		GenericDao<TipoEquipamento> dao = new GenericDao<TipoEquipamento>();
	                List<TipoEquipamento> tipos = dao.list("tipoequipamento", "id");
	                for(TipoEquipamento t: tipos)
	                	if(t.getId()==Integer.parseInt(value))
	                		return t;
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
	            return String.valueOf(((TipoEquipamento) object).getId());
	        }
	        else {
	            return null;
	        }
	    } 
}
