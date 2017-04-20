package br.com.mauricio.news.model.rh.reqpess;

public enum RexExp {
	ESPECIFICA(0),AREA(1),OUTROS(2);
	
    @SuppressWarnings("unused")
	private int value;
    private RexExp reqexp;
    
    private RexExp(int value) {
        this.value = value;
     }

 	public RexExp getRexExp() {
 		return reqexp;
 	}

 	public void setRexExp(RexExp reqexp) {
 		this.reqexp = reqexp;
 	}
}
