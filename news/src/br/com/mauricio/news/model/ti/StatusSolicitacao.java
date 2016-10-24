package br.com.mauricio.news.model.ti;

public enum StatusSolicitacao {
	ABERTA(0),EM_ATENDIMENTO(1),RESOLVIDA(2),FINALIZADA(3),CANCELADA(4),FEEDBACK(5),EM_TRANSFERENCIA(6),EM_ATENDIMENTO_TERCEIRO(7),PAUSADA(8),NOVA_INTERACAO(9);
    @SuppressWarnings("unused")
	private int value;

    private StatusSolicitacao(int value) {
       this.value = value;
    }
}
