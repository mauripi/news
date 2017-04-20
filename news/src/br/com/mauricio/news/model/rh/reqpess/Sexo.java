package br.com.mauricio.news.model.rh.reqpess;

public enum Sexo {
	MASCULINO(0),FEMININO(1),INDIFERENTE(2);
	
    @SuppressWarnings("unused")
	private int value;
    private Sexo sexo;
    
    private Sexo(int value) {
        this.value = value;
     }

 	public Sexo getSexo() {
 		return sexo;
 	}

 	public void setSexo(Sexo sexo) {
 		this.sexo = sexo;
 	}
}
