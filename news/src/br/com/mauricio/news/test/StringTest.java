package br.com.mauricio.news.test;

public class StringTest {

	public static void main(String[] args) {
		System.out.println(primeiroNome("Mauri De Oli Cru"));
		
	}

	private static String primeiroNome(String nome){
		String primeiroNome = "";
		for (int i=0;i<nome.length();i++){
			if ((i==0) && (nome.substring(i, i+1).equalsIgnoreCase(" "))){
				System.out.println("Erro: Nome digitado iniciado com tecla ESPAÇO.");
				break;
			}
			else if (!nome.substring(i, i+1).equalsIgnoreCase(" ")){
				primeiroNome += nome.substring(i, i+1);
			}
			else
				break;
		}
		return primeiroNome;
	}
}
