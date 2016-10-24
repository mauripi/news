package br.com.mauricio.news.util;

public class ValidaCNPJ {
	private static final int[] pesoCNPJ = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
	private static int calcularDigito(String str, int[] peso) {
      int soma = 0;
      for (int indice=str.length()-1, digito; indice >= 0; indice-- ) {
         digito = Integer.parseInt(str.substring(indice,indice+1));
         soma += digito*peso[peso.length-str.length()+indice];
      }
      soma = 11 - soma % 11;
      return soma > 9 ? 0 : soma;
    }
	public static boolean isValidCNPJ(String cnpj) {
		if ((cnpj==null)||(cnpj.length()!=14)) return false;
	    if ((cnpj.equals("00000000000000")) || (cnpj.equals("11111111111111")) || (cnpj.equals("22222222222222")) || (cnpj.equals("33333333333333")) || (cnpj.equals("44444444444444"))) return false;
	    if ((cnpj.equals("55555555555555")) || (cnpj.equals("66666666666666")) || (cnpj.equals("77777777777777")) || (cnpj.equals("88888888888888")) || (cnpj.equals("99999999999999"))) return false;

		Integer digito1 = calcularDigito(cnpj.substring(0,12), pesoCNPJ);
		Integer digito2 = calcularDigito(cnpj.substring(0,12) + digito1, pesoCNPJ);
		return cnpj.equals(cnpj.substring(0,12) + digito1.toString() + digito2.toString());
	}
   
}
