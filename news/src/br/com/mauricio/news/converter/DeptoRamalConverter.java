package br.com.mauricio.news.converter;

import javax.faces.convert.FacesConverter;

import br.com.mauricio.news.model.DeptoRamal;

@FacesConverter("deptoRamalConverter")
public class DeptoRamalConverter extends AbstractConverter{
	
    @Override
    public String getConversionId(Object value) {

        if (value instanceof DeptoRamal) {
        	DeptoRamal entity = (DeptoRamal) value;
            StringBuilder sb = new StringBuilder();
            sb.append(entity.getClass().getSimpleName());
            sb.append("@");
            sb.append(entity.getNome());
            return sb.toString();
        }
        return null;
    }
}
