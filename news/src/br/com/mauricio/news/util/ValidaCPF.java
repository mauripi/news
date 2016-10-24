package br.com.mauricio.news.util;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator(value = "cpfValidator")
public class ValidaCPF implements Validator {
	private static final int[] pesoCPF = {11, 10, 9, 8, 7, 6, 5, 4, 3, 2};
	private static int calcularDigito(String str, int[] peso) {
      int soma = 0;
      for (int indice=str.length()-1, digito; indice >= 0; indice-- ) {
         digito = Integer.parseInt(str.substring(indice,indice+1));
         soma += digito*peso[peso.length-str.length()+indice];
      }
      soma = 11 - soma % 11;
      return soma > 9 ? 0 : soma;
    }
    public static boolean isValidCPF(String cpf) {
      if ((cpf==null) || (cpf.length()!=11)) return false;
      if ((cpf.equals("00000000000")) || (cpf.equals("11111111111")) || (cpf.equals("22222222222")) || (cpf.equals("33333333333")) || (cpf.equals("44444444444"))) return false;
      if ((cpf.equals("55555555555")) || (cpf.equals("66666666666")) || (cpf.equals("77777777777")) || (cpf.equals("88888888888")) || (cpf.equals("99999999999"))) return false;
      Integer digito1 = calcularDigito(cpf.substring(0,9), pesoCPF);
      Integer digito2 = calcularDigito(cpf.substring(0,9) + digito1, pesoCPF);
      return cpf.equals(cpf.substring(0,9) + digito1.toString() + digito2.toString());
	}
    
    @Override
    public void validate(FacesContext context, UIComponent component,Object value) throws ValidatorException {
        String cpf = String.valueOf(value);
        boolean valid = isValidCPF(cpf);
        if (!valid) {          
            FacesMessage message = new FacesMessage(
                    FacesMessage.SEVERITY_ERROR, "CPF inválido!",
                    "Número CPF inválido.");
            //throw new ValidatorException(message);
            context.addMessage(null, message);  
        }
    }
}
