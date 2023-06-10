package com.projeto.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;

import com.projeto.bean.util.Contexto;
import com.projeto.dao.DAOTarefas;
import com.projeto.dao.DAOUsuario;
import com.projeto.lazy.ModelTarefaDataLazy;
import com.projeto.model.ModelTarefa;
import com.projeto.model.ModelUsuario;
import com.projeto.model.TarefaUsuarioId;
import com.projeto.util.JPAUtil;

@ViewScoped
@ManagedBean(name="minhasTarefasBeanView")
public class MinhasTarefasBeanView extends Contexto {

	private DAOTarefas daoTarefas = new DAOTarefas();
	private DAOUsuario daoUsuario = new DAOUsuario();

	private Date hoje = new Date();
	private ModelTarefa tarefa;
	private ModelTarefa tarefaPendente;

	private Long idTarefa = 0L;
	private String usuarioParaConvidar = "";
	private List<ModelUsuario> usuariosConvidados = new ArrayList<ModelUsuario>();
	private ModelUsuario usuarioSelecionado = new ModelUsuario();
	private ModelTarefaDataLazy tarefaDataLazy = new ModelTarefaDataLazy();
	private ModelTarefaDataLazy tarefaDataLazyCompletas = new ModelTarefaDataLazy();

	@PostConstruct
	public void init() {
		System.out.println(" ----> Iniciando");
	}

	
	// Completar uma tarefa depois de confirmar no dlg_tarefa
	public void completarTarefa() {
		System.out.println(tarefaPendente + " ==>> Tarefa Completar");
		if (tarefaPendente!=null) {
			ModelTarefa findEntity = daoTarefas.findEntity("from ModelTarefa where id=" + tarefaPendente.getId());
			if (findEntity != null) {
				findEntity.setDataTermino(hoje);
				daoTarefas.merge(findEntity);
				mensagemSucesso("Parabéns, você completou esta tarefa !");
			} else {
				mensagemError("Insira uma tarefa válida.");

			}
			tarefaPendente=new ModelTarefa();
		}else{
			mensagemError("Erro ao completar a tarefa");
		}
	

	}
	
	
	// Adicionar  novo convidado à tarefa.
	public void convidar() {
		System.out.println("Convidando para a Tarefa : " + getTarefaPendente());
		
		if (usuarioParaConvidar == null) {
			mensagemError("Selecione um convidado para a tarefa.");

			
			return;
		}
		Long checarLogin = daoUsuario.pesquisarLogin(usuarioParaConvidar);

		if (checarLogin.intValue() == 0) {
			mensagemError("O usuário não existe !");

		} else {
			usuarioSelecionado = daoUsuario.findEntityByLogin(usuarioParaConvidar);
			if (getUsuarioSelecionado() != null) {

				if (!daoUsuario.contemConvidado(usuarioSelecionado, getTarefaPendente())) {
					daoTarefas.adicionarConvidados(usuarioSelecionado, getTarefaPendente());
//					usuarioSelecionado.getTarefasConvidado().add(getTarefaPendente());
//					JPAUtil.getEntityManager().merge(usuarioSelecionado);
					pegarConvidadosDaTarefa();
					tarefaPendente=new ModelTarefa();
					mensagemSucesso("O usuário foi convidado");
				} else {
					mensagemError("O usuário já está como convidado desta tarefa !");
				}

			}
		}

	}

	
	// Calcular a quantidade de dias restantes para o término da tarefa
	public long restante(ModelTarefa tarefa) {

		long time = tarefa.getDataFinal().getTime() - hoje.getTime();
		long horas = time / (1000 * 3600);
		long dias = horas / 24;
		return (time / (1000 * 3600 * 24)) + 1;
	}

	
	// Pegar a lista de tarefas pendentes para paginação
	public ModelTarefaDataLazy getTarefasPendentes() {
		tarefaDataLazy.setQueryContar(JPAUtil.getEntityManager().createNamedQuery("Tarefa.donoCountPendentes")
				.setParameter("dono", getUserLogado()));
		tarefaDataLazy.setQueryBusca(JPAUtil.getEntityManager().createNamedQuery("Tarefa.findAllDonoPendentes")
				.setParameter("dono", getUserLogado()));
		System.out.println("Lista de tarefas pendentes ");
		System.out.println(tarefaDataLazy.getWrappedData());
		return tarefaDataLazy;

	}
	
	
	
	
	// Pegar a lista de tarefas Completas para paginação
	public ModelTarefaDataLazy getMinhasTarefasCompletas() {

		tarefaDataLazyCompletas.setQueryBusca(JPAUtil.getEntityManager().createNamedQuery("Tarefa.findAllDonoCompletas")
				.setParameter("dono", getUserLogado()));
		tarefaDataLazyCompletas.setQueryContar(JPAUtil.getEntityManager().createNamedQuery("Tarefa.donoCountCompletas")
				.setParameter("dono", getUserLogado()));
		return tarefaDataLazyCompletas;
	}

	
	// Consultar todos os convidados da tarefa
	public void pegarConvidadosDaTarefa() {
		if (getTarefaPendente()!=null) {
			System.out.println("Pegando convidados da tarefa = >>>"+getTarefaPendente());
			usuariosConvidados = daoUsuario.findAllUsuarioTarefa(getTarefaPendente());
		}else {
			mensagemError("Erro ao consultar os convidados da tarefa.");
		}
		

	}

	public void removerConvidado() {

		
		if (usuarioSelecionado != null && getTarefaPendente() != null) {
			daoTarefas.removerConvidadoTarefa(getTarefaPendente(), usuarioSelecionado);
			mensagemError("Usuario removido com sucesso !");
			pegarConvidadosDaTarefa();
		}else {
			mensagemError("Erro ao remover o convidados da tarefa.");

		}

	}
	
	
	// GETTERS E SETTERS

	public Long getIdTarefa() {
		return idTarefa;
	}

	public void setIdTarefa(Long idTarefa) {
		this.idTarefa = idTarefa;
	}

	public String getUsuarioParaConvidar() {
		return usuarioParaConvidar;
	}

	public void setUsuarioParaConvidar(String usuarioParaConvidar) {
		this.usuarioParaConvidar = usuarioParaConvidar;
	}

	public ModelUsuario getUsuarioSelecionado() {
		return usuarioSelecionado;
	}

	public List<ModelUsuario> getUsuariosConvidados() {

		return usuariosConvidados;
	}

	public ModelTarefaDataLazy getTarefaDataLazy() {
		return tarefaDataLazy;
	}

	public void setTarefaDataLazy(ModelTarefaDataLazy tarefaDataLazy) {
		this.tarefaDataLazy = tarefaDataLazy;
	}

	public void setUsuariosConvidados(List<ModelUsuario> usuariosConvidados) {
		this.usuariosConvidados = usuariosConvidados;
	}

	public void setUsuarioSelecionado(ModelUsuario usuarioSelecionado) {
		this.usuarioSelecionado = usuarioSelecionado;
	}

	public int contarConvidadosTarefa(ModelTarefa t) {

		return daoTarefas.contarTodos(t).intValue();
	}

	public ModelTarefa getTarefa() {
		return tarefa;
	}

	public void setTarefa(ModelTarefa tarefa) {
		this.tarefa = tarefa;
	}



	public ModelTarefa getTarefaPendente() {
		return tarefaPendente;
	}

	public void setTarefaPendente(ModelTarefa tarefaPendente) {
		this.tarefaPendente = tarefaPendente;
	}

}
