package br.com.mauricio.news.model.rh.reqpess;

public enum ReqIdade {
	ENTRE(0),INFERIOR(1),SUPERIOR(2);
	
    @SuppressWarnings("unused")
	private int value;
    private ReqIdade reqidade;
    
    private ReqIdade(int value) {
        this.value = value;
     }

 	public ReqIdade getReqIdade() {
 		return reqidade;
 	}

 	public void setReqIdade(ReqIdade reqidade) {
 		this.reqidade = reqidade;
 	}
}
