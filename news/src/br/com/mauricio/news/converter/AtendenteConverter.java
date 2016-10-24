package br.com.mauricio.news.converter;

import javax.faces.convert.FacesConverter;

import br.com.mauricio.news.model.ti.Atendente;

@FacesConverter("atendenteConverter")
public class AtendenteConverter extends AbstractConverter{
	
    @Override
    public String getConversionId(Object value) {

        if (value instanceof Atendente) {
        	Atendente entity = (Atendente) value;
            StringBuilder sb = new StringBuilder();
            sb.append(entity.getClass().getSimpleName());
            sb.append("@");
            sb.append(entity.getId());
            return sb.toString();
        }
        return null;
    }
}
