package br.com.mauricio.news.converter;

import javax.faces.convert.FacesConverter;

import br.com.mauricio.news.model.Filial;

@FacesConverter("filialConverter")
public class FilialConverter extends AbstractConverter{
	
    @Override
    public String getConversionId(Object value) {

        if (value instanceof Filial) {
        	Filial entity = (Filial) value;
            StringBuilder sb = new StringBuilder();
            sb.append(entity.getClass().getSimpleName());
            sb.append("@");
            sb.append(entity.getNome());
            return sb.toString();
        }
        return null;
    }
}
