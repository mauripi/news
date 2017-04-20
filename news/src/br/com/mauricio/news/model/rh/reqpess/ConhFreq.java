package br.com.mauricio.news.model.rh.reqpess;

public enum ConhFreq {
	SEMPRE(0),ALGUMAS_VEZES(1),NUNCA(2);
	
    @SuppressWarnings("unused")
	private int value;
    private ConhFreq conhfreq;
    
    private ConhFreq(int value) {
        this.value = value;
     }

 	public ConhFreq getConhFreq() {
 		return conhfreq;
 	}

 	public void setConhFreq(ConhFreq conhfreq) {
 		this.conhfreq = conhfreq;
 	}
}
