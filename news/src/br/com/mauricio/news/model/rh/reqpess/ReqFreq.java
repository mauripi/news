package br.com.mauricio.news.model.rh.reqpess;

public enum ReqFreq {
	FREQUENTEMENTE(0),EXPORADICAMENTE(1),RARAMENTE(2);
	
    @SuppressWarnings("unused")
	private int value;
    private ReqFreq reqfreq;
    
    private ReqFreq(int value) {
        this.value = value;
     }

 	public ReqFreq getReqFreq() {
 		return reqfreq;
 	}

 	public void setReqFreq(ReqFreq reqfreq) {
 		this.reqfreq = reqfreq;
 	}
}
