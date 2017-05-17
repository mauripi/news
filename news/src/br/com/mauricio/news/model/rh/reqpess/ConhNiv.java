package br.com.mauricio.news.model.rh.reqpess;

public enum ConhNiv {
	BÁSICO(0),INTERMEDIÁRIO(1),AVANÇADO(2);
	
    @SuppressWarnings("unused")
	private int value;
    private ConhNiv conhniv;
    
    private ConhNiv(int value) {
        this.value = value;
     }

 	public ConhNiv getConhNiv() {
 		return conhniv;
 	}

 	public void setConhNiv(ConhNiv conhniv) {
 		this.conhniv = conhniv;
 	}
}
