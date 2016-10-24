package br.com.mauricio.news.model.ti;

public enum CategoriaSolicitacao {
	HELPDESK(0),SISTEMAS(1),TELEFONIA(2),INFRA_ESTRUTURA(3),INTERNO(4);
	
    @SuppressWarnings("unused")
	private int value;
    private CategoriaSolicitacao categoria;
    
    private CategoriaSolicitacao(int value) {
       this.value = value;
    }

	public CategoriaSolicitacao getCategoria() {
		return categoria;
	}

	public void setCategoria(CategoriaSolicitacao categoria) {
		this.categoria = categoria;
	}
    

    
    
}
