package br.com.mauricio.news.mb.ti;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

import org.primefaces.event.TransferEvent;
import org.primefaces.model.DualListModel;

import br.com.mauricio.news.dao.GenericDao;
import br.com.mauricio.news.ln.FilialLN;
import br.com.mauricio.news.ln.GenericLN;
import br.com.mauricio.news.ln.LoginLN;
import br.com.mauricio.news.ln.ti.EquipamentoLN;
import br.com.mauricio.news.ln.ti.LicencaLN;
import br.com.mauricio.news.model.Filial;
import br.com.mauricio.news.model.Login;
import br.com.mauricio.news.model.ti.Equipamento;
import br.com.mauricio.news.model.ti.Licenca;
import br.com.mauricio.news.model.ti.LocalEquipamento;
import br.com.mauricio.news.model.ti.TipoEquipamento;
import br.com.mauricio.news.util.UsuarioLogado;

@ManagedBean(name="equipamentoMB")
@ViewScoped
public class EquipamentoBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private Equipamento equipamento = new Equipamento();
	private Equipamento equipamentoSel = new Equipamento();
	private EquipamentoLN ln = new EquipamentoLN();
	private Login colaborador = new Login();
	private LocalEquipamento local = new LocalEquipamento();
	private Filial filial = new Filial();
	private TipoEquipamento tipoEquipamento = new TipoEquipamento();
	private List<Equipamento> equipamentos = new ArrayList<Equipamento>();
	private List<Login> todosColaboradores = new ArrayList<Login>();
	private List<LocalEquipamento> locais = new ArrayList<LocalEquipamento>();
	private List<Licenca> todasLicencas = new ArrayList<Licenca>();
	private List<Filial> filiais = new ArrayList<Filial>();
	private List<Licenca> licencasSubtrairQuantidade = new ArrayList<Licenca>();
	private List<Licenca> licencasSomarQuantidade = new ArrayList<Licenca>();
	private List<Licenca> licencasDoEquipamento = new ArrayList<Licenca>();
	private List<TipoEquipamento> tipoEquipamentos = new ArrayList<TipoEquipamento>();
	private Boolean exibeCampoMemoriaHD = true;
	private int controlaCadastro = 0;
	private String msg;
	private DualListModel<Licenca> equipamentoLicencas;

	
	@PostConstruct
	public void init(){
		listar();
		listas();
		setEquipamentoLicencas(new DualListModel<Licenca>(todasLicencas, new ArrayList<Licenca>()));
		tipoEquipamento = new TipoEquipamento();
		tipoEquipamento=tipoEquipamentos.get(0);
	}

	public void listar(){
		ln = new EquipamentoLN();
		equipamentos = ln.getList();
	}
	
	private void listas(){
		FilialLN lnf = new FilialLN();
		LoginLN lnl = new LoginLN();
		GenericDao<TipoEquipamento> daoF = new GenericDao<TipoEquipamento>();
		GenericLN<Licenca> gll = new GenericLN<Licenca>();
		GenericLN<LocalEquipamento> glle = new GenericLN<LocalEquipamento>();
		
		locais = glle.listAll(new LocalEquipamento(), "descricao");
		todasLicencas = gll.listAll(new Licenca(), "descricao");		
		tipoEquipamentos = daoF.list("tipoequipamento", "id");		
		filiais = lnf.list();
		todosColaboradores = lnl.listaColaboradoresAtivos();
	}
	
	public void novo(){
		limpaCadastro();
		controlaCadastro=1;
	}

	public void edita(){
		controlaCadastro=2;
	}

	public void exclui(){
		if(equipamento.getId()==null){
			msg = "Nenhum registro selecionado para exclusão.";
			mensagens();			
		}else{
			ln = new EquipamentoLN();
			equipamento.setRemovido(1);
			equipamento.setDataAlteracao(new Date(System.currentTimeMillis()));
			equipamento.setUsuarioAlteracao(UsuarioLogado.getUser());			
			msg = ln.update(equipamento);
			ln.liberaLicencasEquipamentoExcluido(equipamento);
			mensagens();
			limpaCadastro();
			listar();
		}		
	}
	
	public void grava(){		
		if(validaCampos()){
			ln = new EquipamentoLN();
			preparaGravacao();
			if(controlaCadastro==1){
				equipamento.setRemovido(0);				
				equipamento.setDataCriacao(new Date(System.currentTimeMillis()));			
				equipamento.setUsuarioCriacao(UsuarioLogado.getUser());
				msg = ln.create(equipamento);
			}
			if(controlaCadastro==2)	{	
				equipamento.setDataAlteracao(new Date(System.currentTimeMillis()));
				equipamento.setUsuarioAlteracao(UsuarioLogado.getUser());	
				msg = ln.update(equipamento);
			}
			atualizaSaldoDasLicencas();
			mensagens(); 
			listar();
			limpaCadastro();	
		}else{
			msg = "Favor preencha todos campos com *, pois são obrigatórios.";
			mensagens(); 
		}
	}
	
	private void atualizaSaldoDasLicencas(){
		LicencaLN lnl = new LicencaLN();
		for(Licenca l:licencasSomarQuantidade)
			lnl.atualizaLicencasEquipamento(l, 1);
		for(Licenca l:licencasSubtrairQuantidade)
			lnl.atualizaLicencasEquipamento(l, 2);		
	}
	
	private void preparaGravacao(){
	    this.equipamento.setLicencas(equipamentoLicencas.getTarget());
	}
	
	private boolean validaCampos(){		
		equipamento.setTipo(tipoEquipamento);
		return true;	
	}
	
	public void limpaCadastro(){
		equipamentoSel = equipamento = new Equipamento();
		colaborador = new Login();
		local = new LocalEquipamento();
		licencasDoEquipamento = new ArrayList<Licenca>();
		filial = new Filial();
		controlaCadastro=0;
		if(tipoEquipamentos.size()>0)
			tipoEquipamento=tipoEquipamentos.get(0);
		exibeCampoMemoriaHD = true;
		LicencaLN lnl = new LicencaLN();
		todasLicencas = lnl.getLicencasDisponiveis();
    	licencasSubtrairQuantidade = new ArrayList<Licenca>();
    	licencasSomarQuantidade = new ArrayList<Licenca>();
    	setEquipamentoLicencas(new DualListModel<Licenca>(todasLicencas, new ArrayList<Licenca>()));
	}

 	public void selecao(){
		equipamento = equipamentoSel;
		colaborador=equipamento.getColaborador();
		local=equipamento.getLocal();
		tipoEquipamento=equipamento.getTipo();
		filial = equipamento.getFilial();
		controlaCadastro=0;
		edita();
		if(tipoEquipamento.getDescricao().equals("Computador")||tipoEquipamento.getDescricao().equals("Servidor")||tipoEquipamento.getDescricao().equals("Notebook")){
			atualizaLicencas();
			exibeCampoMemoriaHD = true;
		}else{
			setEquipamentoLicencas(new DualListModel<Licenca>(new ArrayList<Licenca>(), new ArrayList<Licenca>()));
			exibeCampoMemoriaHD = false;
		}
	}

	private void atualizaLicencas() {
		LicencaLN lnl = new LicencaLN();
		todasLicencas = lnl.getLicencasDisponiveis();
		todasLicencas.removeAll(equipamento.getLicencas());		
		setEquipamentoLicencas(new DualListModel<Licenca>(todasLicencas, equipamento.getLicencas()));		
	}

    public void onTransfer(TransferEvent event) {
    	for(Object l :  event.getItems()){
	    	if(event.isAdd())
	    		licencasSubtrairQuantidade.add((Licenca) l);
	    	if(event.isRemove())
	    		licencasSomarQuantidade.add((Licenca) l);	    	
    	}
    } 
    
	public void selecaoTipoEquipamento(final AjaxBehaviorEvent event){
		if(tipoEquipamento.getDescricao().equals("Computador")||tipoEquipamento.getDescricao().equals("Servidor")||tipoEquipamento.getDescricao().equals("Notebook")){
			setEquipamentoLicencas(new DualListModel<Licenca>(todasLicencas, new ArrayList<Licenca>()));
			exibeCampoMemoriaHD = true;
		}else{
			setEquipamentoLicencas(new DualListModel<Licenca>(new ArrayList<Licenca>(), new ArrayList<Licenca>()));
			exibeCampoMemoriaHD = false;
		}
	}
	
    public void mensagens(){
        FacesContext context = FacesContext.getCurrentInstance();  	          
        context.addMessage(null, new FacesMessage("",msg));  		
	}
	//--------------------------GETTERS E SETTERS ----------------------------------
	public Date getToday() {
        return new Date();
    }

	public Equipamento getEquipamento() {
		return equipamento;
	}

	public void setEquipamento(Equipamento equipamento) {
		this.equipamento = equipamento;
	}

	public Equipamento getEquipamentoSel() {
		return equipamentoSel;
	}

	public void setEquipamentoSel(Equipamento equipamentoSel) {
		this.equipamentoSel = equipamentoSel;
	}

	public EquipamentoLN getLn() {
		return ln;
	}

	public void setLn(EquipamentoLN ln) {
		this.ln = ln;
	}

	public List<Equipamento> getEquipamentos() {
		return equipamentos;
	}

	public void setEquipamentos(List<Equipamento> equipamentos) {
		this.equipamentos = equipamentos;
	}

	public Login getColaborador() {
		return colaborador;
	}

	public void setColaborador(Login colaborador) {
		this.colaborador = colaborador;
	}

	public LocalEquipamento getLocal() {
		return local;
	}

	public void setLocal(LocalEquipamento local) {
		this.local = local;
	}

	public TipoEquipamento getTipoEquipamento() {
		return tipoEquipamento;
	}

	public void setTipoEquipamento(TipoEquipamento tipoEquipamento) {
		this.tipoEquipamento = tipoEquipamento;
	}

	public List<Login> getTodosColaboradores() {
		return todosColaboradores;
	}

	public void setTodosColaboradores(List<Login> todosColaboradores) {
		this.todosColaboradores = todosColaboradores;
	}

	public List<LocalEquipamento> getLocais() {
		return locais;
	}

	public void setLocais(List<LocalEquipamento> locais) {
		this.locais = locais;
	}

	public List<Licenca> getTodasLicencas() {
		return todasLicencas;
	}

	public void setTodasLicencas(List<Licenca> todasLicencas) {
		this.todasLicencas = todasLicencas;
	}

	public List<Licenca> getLicencasDoEquipamento() {
		return licencasDoEquipamento;
	}

	public void setLicencasDoEquipamento(List<Licenca> licencasDoEquipamento) {
		this.licencasDoEquipamento = licencasDoEquipamento;
	}

	public List<TipoEquipamento> getTipoEquipamentos() {
		return tipoEquipamentos;
	}

	public void setTipoEquipamentos(List<TipoEquipamento> tipoEquipamentos) {
		this.tipoEquipamentos = tipoEquipamentos;
	}

	public int getControlaCadastro() {
		return controlaCadastro;
	}

	public void setControlaCadastro(int controlaCadastro) {
		this.controlaCadastro = controlaCadastro;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Filial getFilial() {
		return filial;
	}

	public void setFilial(Filial filial) {
		this.filial = filial;
	}

	public List<Filial> getFiliais() {
		return filiais;
	}

	public void setFiliais(List<Filial> filiais) {
		this.filiais = filiais;
	}

	public Boolean getExibeCampoMemoriaHD() {
		return exibeCampoMemoriaHD;
	}

	public void setExibeCampoMemoriaHD(Boolean exibeCampoMemoriaHD) {
		this.exibeCampoMemoriaHD = exibeCampoMemoriaHD;
	}

	public DualListModel<Licenca> getEquipamentoLicencas() {
		return equipamentoLicencas;
	}

	public void setEquipamentoLicencas(DualListModel<Licenca> equipamentoLicencas) {
		this.equipamentoLicencas = equipamentoLicencas;
	}

}
