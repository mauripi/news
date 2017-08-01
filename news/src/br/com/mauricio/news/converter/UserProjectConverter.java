package br.com.mauricio.news.converter;

import javax.faces.convert.FacesConverter;

import br.com.mauricio.news.model.UserProject;

@FacesConverter("userprojectConverter")
public class UserProjectConverter extends AbstractConverter{
	
    @Override
    public String getConversionId(Object value) {

        if (value instanceof UserProject) {
        	UserProject entity = (UserProject) value;
            StringBuilder sb = new StringBuilder();
            sb.append(entity.getClass().getSimpleName());
            sb.append("@");
            sb.append(entity.getNome());
            return sb.toString();
        }
        return null;
    }
}
