package com.projeto.bean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;

import com.projeto.bean.util.Contexto;
import com.projeto.bean.util.Mensagens;
import com.projeto.dao.DAOTarefas;
import com.projeto.dao.DAOUsuario;
import com.projeto.lazy.ModelTarefaDataLazy;
import com.projeto.model.ModelTarefa;
import com.projeto.model.ModelUsuario;
import com.projeto.util.JPAUtil;

@ViewScoped
@ManagedBean(name = "loginBeanView")
public class LoginBeanView extends Contexto {

	private DAOUsuario daoUsuario = new DAOUsuario();
	private DAOTarefas daoTarefas = new DAOTarefas();

	private String login;
	private String senha;
	private Date hoje= new Date();
	private String usuarioParaConvidar="";
	private ModelUsuario usuarioSelecionado=new ModelUsuario();
	private ModelTarefa tarefa=new ModelTarefa();
	private List<ModelUsuario> usuariosConvidados = new ArrayList<ModelUsuario> (); 
	private List<ModelTarefa> minhasTarefasConvidado=new ArrayList<ModelTarefa>();
	private List<ModelTarefa> minhasTarefasConvidadoPendentes=new ArrayList<ModelTarefa>();
	
	private ModelTarefaDataLazy tarefaDataLazy= new ModelTarefaDataLazy();
	
	
	
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public void logar() throws IOException {

		Long total = daoUsuario.checarLogin(login);
		Query query = JPAUtil.getEntityManager().createNamedQuery("Usuarios.countAutenticar")
				.setParameter("login", login).setParameter("senha", senha);
		Query queryConsultar= JPAUtil.getEntityManager().createNamedQuery("Usuarios.findUser").setParameter("login", login).setParameter("senha", senha);
		if (total.intValue() == 0) {
			mensagemError("Não existe usuário com este login.");
		} else if (daoUsuario.count(query).intValue() == 0) {
			mensagemError("Senha incorreta.");

		}else {
			ModelUsuario usuarioLogado = daoUsuario.findEntity(queryConsultar);
			HttpServletRequest req=(HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
			req.getSession().setAttribute("userlogado", usuarioLogado);
			 FacesContext.getCurrentInstance().getExternalContext().redirect("restrito/usuarios.jsf");
			mensagemInfo("Usuário Logado com Sucesso");
		}

	}
	
	public void deslogar() throws IOException {
		HttpServletRequest req=(HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		
		req.getSession().invalidate();
		 FacesContext.getCurrentInstance().getExternalContext().redirect(req.getContextPath()+"/index.jsf");

	}


//	public List<ModelTarefa> getMinhasTarefas() {
//		ModelUsuario userLogado = super.getUserLogado();
//		minhasTarefas=daoTarefas.findAll(JPAUtil.getEntityManager().createNamedQuery("Tarefa.findAllDonoPendentes").setParameter("dono", userLogado));
//		
//		return minhasTarefas;
//	}
	public ModelTarefaDataLazy getTarefasPendentes() {
		tarefaDataLazy.setQueryContar(JPAUtil.getEntityManager().createNamedQuery("Tarefa.donoCountPendentes").setParameter("dono", getUserLogado()));
		tarefaDataLazy.setQueryBusca(JPAUtil.getEntityManager().createNamedQuery("Tarefa.findAllDonoPendentes").setParameter("dono", getUserLogado()));

		
		return tarefaDataLazy;
		
	}




	public Date getHoje() {
		return hoje;
	}


	public void setHoje(Date hoje) {
		this.hoje = hoje;
	}
	
	public long restante(ModelTarefa tarefa) {
		
		long time = tarefa.getDataFinal().getTime()- hoje.getTime();
		long horas= time/(1000*3600);
		long dias = horas/24;
		return (time/(1000*3600*24))+1;
	}


	public ModelTarefa getTarefa() {
		return tarefa;
	}


	public void setTarefa(ModelTarefa tarefa) {
		this.tarefa = tarefa;
	}
	
	
	public void completarTarefa () {
		tarefa.setDataTermino(hoje);
		daoTarefas.merge(tarefa);
		mensagemSucesso("Parabéns, você completou esta tarefa !");
	}


	public ModelTarefaDataLazy getMinhasTarefasCompletas() {
		
		tarefaDataLazy.setQueryBusca(JPAUtil.getEntityManager().createNamedQuery("Tarefa.findAllDonoCompletas").setParameter("dono",getUserLogado()));
		tarefaDataLazy.setQueryContar(JPAUtil.getEntityManager().createNamedQuery("Tarefa.donoCountCompletas").setParameter("dono",getUserLogado()));
		return tarefaDataLazy;
	}


	
	public void convidar() {
		System.out.println("Convidando para a Tarefa : "+tarefa.getId());
		Long checarLogin = daoUsuario.checarLogin(usuarioParaConvidar);
		if (checarLogin.intValue()==0) {
			mensagemError("O usuário não existe !");

		}
		else {
			usuarioSelecionado=daoUsuario.findEntityByLogin(usuarioParaConvidar);
			if (getUsuarioSelecionado()!=null) {
				
				if (!daoUsuario.contemConvidado(usuarioSelecionado, tarefa)) {
					daoTarefas.adicionarConvidados(usuarioSelecionado,tarefa);
					pegarConvidadosDaTarefa();
					mensagemSucesso("O usuário foi convidado");
				}else {
					mensagemError("O usuário já está como convidado desta tarefa !");
				}
				

			}
		}
		
		
	}


	
	public ModelUsuario getUsuarioSelecionado() {
		return usuarioSelecionado;
	}


	public void setUsuarioSelecionado(ModelUsuario usuarioSelecionado) {
		this.usuarioSelecionado = usuarioSelecionado;
	}


	public String getUsuarioParaConvidar() {
		return usuarioParaConvidar;
	}


	public void setUsuarioParaConvidar(String usuarioParaConvidar) {
		this.usuarioParaConvidar = usuarioParaConvidar;
	}

	public int contarConvidadosTarefa(ModelTarefa t) {
		
		return daoTarefas.contarTodos(t).intValue();
	}

	public void pegarConvidadosDaTarefa() {
		usuariosConvidados=daoUsuario.findAllUsuarioTarefa(tarefa);

	}
	public List<ModelUsuario> getUsuariosConvidados() {
	
		return usuariosConvidados;
	}


	public void setUsuariosConvidados(List<ModelUsuario> usuariosConvidados) {
		this.usuariosConvidados = usuariosConvidados;
	}
	
	public void removerConvidado( ) {
		
		if (usuarioSelecionado!=null && tarefa!=null) {
			daoTarefas.removerConvidadoTarefa(tarefa,usuarioSelecionado);
			mensagemError("Usuario removido com sucesso !");
			pegarConvidadosDaTarefa();
		}
		
	}
	
	


	public List<ModelTarefa> getMinhasTarefasConvidado() {
		minhasTarefasConvidado= daoTarefas.userTarefasConvocado(getUserLogado());
		
		return minhasTarefasConvidado;
	}


	public void setMinhasTarefasConvidado(List<ModelTarefa> minhasTarefasConvidado) {
		this.minhasTarefasConvidado = minhasTarefasConvidado;
	}


	public List<ModelTarefa> getMinhasTarefasConvidadoPendentes() {
		minhasTarefasConvidadoPendentes=daoTarefas.findAll(JPAUtil.getEntityManager()
				.createNamedQuery("UsuarioTarefa.findTarefasPendentes")
				.setParameter("user", getUserLogado().getId()));
		return minhasTarefasConvidadoPendentes;
	}


	public void setMinhasTarefasConvidadoPendentes(List<ModelTarefa> minhasTarefasConvidadoPendentes) {
		this.minhasTarefasConvidadoPendentes = minhasTarefasConvidadoPendentes;
	}


	public ModelTarefaDataLazy getTarefaDataLazy() {
		return tarefaDataLazy;
	}


	public void setTarefaDataLazy(ModelTarefaDataLazy tarefaDataLazy) {
		this.tarefaDataLazy = tarefaDataLazy;
	}
}
