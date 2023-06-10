package com.projeto.bean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;

import com.projeto.bean.util.Contexto;
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
	private ModelTarefa tarefa;
	
	
	
	
	@PostConstruct
	public void init() {
		System.out.println(" === "+"INICIALIZANDO LOGINBEANVIEW"+" === ");
		
	}
	
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

		Long total = daoUsuario.checarLogin(login,senha);
//		Query query = JPAUtil.getEntityManager().createNamedQuery("Usuarios.countAutenticar")
//				.setParameter("login", login).setParameter("senha", senha);
		JPAUtil.getEntityManager().isOpen();
		Query queryConsultar= JPAUtil.getEntityManager().createNamedQuery("Usuarios.findUser").setParameter("login", login).setParameter("senha", senha);
		if (total.intValue() == 0) {
			mensagemError("Usuário ou senha incorreta.");
		

		}else {
			ModelUsuario usuarioLogado = daoUsuario.findEntity(queryConsultar);
			HttpServletRequest req=(HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
			req.getSession().setAttribute("userlogado", usuarioLogado);
			if (usuarioLogado.getAdmin()) {
				getContext().redirect("administrador/usuarios.jsf");

			}else {
				getContext().redirect("restrito/minhastarefas.jsf");

			}
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

	public boolean verificarAdmin() {
		return getUserLogado().getAdmin();
	}
	public void setTarefa(ModelTarefa tarefa) {
		System.out.println("Setando Tarefa...");
		this.tarefa = tarefa;
	}
	
	
	

	


	
	


	

	


	
	
	
	
	
	





	

	


}
