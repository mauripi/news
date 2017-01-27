package br.com.mauricio.news.Teste;

public class TestString {

	public static void main(String[] args) {
		String s = "2";
		System.out.println(ehInteiro(s)); 
		

	}

	public static boolean ehInteiro( String s ) {
	    // cria um array de char
	    char[] c = s.toCharArray();
	    boolean d = true;
	    for ( int i = 0; i < c.length; i++ ){
	        // verifica se o char não é um dígito
	        if ( !Character.isDigit( c[ i ] ) ) {
	            d = false;
	            break;
	        }
	    }
	    return d;
	}
}
