package br.com.mauricio.news.model.contabil;

public enum TipoBaixa {
	VENDA(0),OBSOLENCIA(1),SUCATEAR(2),FURTO(3),DEVOLUÇÃO(4), DOAÇÂO(5);
    @SuppressWarnings("unused")
	private int value;
    private TipoBaixa tipoBaixa;
    
    private TipoBaixa(int value) {
       this.value = value;
    }

	public TipoBaixa getTipoBaixa() {
		return tipoBaixa;
	}

	public void setTipoBaixa(TipoBaixa tipoBaixa) {
		this.tipoBaixa = tipoBaixa;
	}
}
