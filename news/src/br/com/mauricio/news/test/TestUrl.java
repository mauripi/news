package br.com.mauricio.news.test;

public class TestUrl {
	public static void main(String[] args) {
		buscaUrl();
	}

	public static void buscaUrl(){
		String url = "https://www.youtube.com/watch?v=CxYRbzGi8Rg";
		String [] partes = url.split("=");
		String id = partes[partes.length-1];
		String newUrl = "https://www.youtube.com/embed/"+id;
		System.out.println(newUrl);
	}
}
