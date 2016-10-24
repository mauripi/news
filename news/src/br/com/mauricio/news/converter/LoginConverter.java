package br.com.mauricio.news.converter;

import javax.faces.convert.FacesConverter;

import br.com.mauricio.news.model.Login;

@FacesConverter("loginConverter")
public class LoginConverter extends AbstractConverter{
	
    @Override
    public String getConversionId(Object value) {

        if (value instanceof Login) {
        	Login entity = (Login) value;
            StringBuilder sb = new StringBuilder();
            sb.append(entity.getClass().getSimpleName());
            sb.append("@");
            sb.append(entity.getNome());
            return sb.toString();
        }
        return null;
    }
}
