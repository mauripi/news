package br.com.mauricio.news.model.opec;

public class Vetrix {
	private Integer id;
	private String programa;
	private String tipo;
	private String dataInicio;
	private String horaInicio;
	private String dataFim;
	private String horaFim;

	@Override
	public String toString() {
		return programa + ";" + tipo + ";" + dataInicio+ ";" + horaInicio + ";" + horaFim;		
	}
	
	public String getPrograma() {
		return programa;
	}
	public void setPrograma(String programa) {
		this.programa = programa; 
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getDataInicio() {
		return dataInicio;
	}
	public void setDataInicio(String dataInicio) {
		this.dataInicio = dataInicio;
	}
	public String getHoraInicio() {
		return horaInicio;
	}
	public void setHoraInicio(String horaInicio) {
		this.horaInicio = horaInicio.replace(" ", "");
	}
	public String getDataFim() {
		return dataFim;
	}
	public void setDataFim(String dataFim) {
		this.dataFim = dataFim;
	}
	public String getHoraFim() {
		return horaFim;
	}
	public void setHoraFim(String horaFim) {
		this.horaFim = horaFim.replace(" ", "");
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

}
