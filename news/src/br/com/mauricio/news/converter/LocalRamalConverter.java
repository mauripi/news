package br.com.mauricio.news.converter;

import javax.faces.convert.FacesConverter;

import br.com.mauricio.news.model.LocalRamal;

@FacesConverter("localRamalConverter")
public class LocalRamalConverter extends AbstractConverter{
	
    @Override
    public String getConversionId(Object value) {

        if (value instanceof LocalRamal) {
        	LocalRamal entity = (LocalRamal) value;
            StringBuilder sb = new StringBuilder();
            sb.append(entity.getClass().getSimpleName());
            sb.append("@");
            sb.append(entity.getNome());
            return sb.toString();
        }
        return null;
    }
}
