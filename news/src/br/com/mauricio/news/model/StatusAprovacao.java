package br.com.mauricio.news.model;

public enum StatusAprovacao {

	APROVADO(0),RECUSADO(1),AGUARDANDO(2);
	private int value;
	private StatusAprovacao status;
	private StatusAprovacao (int value ){
		this.value = value;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public StatusAprovacao getStatus() {
		return status;
	}
	public void setStatus(StatusAprovacao status) {
		this.status = status;
	}
	
}
