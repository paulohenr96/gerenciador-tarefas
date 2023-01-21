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
	
	private List<ModelTarefa> minhasTarefas=new ArrayList<ModelTarefa>();

	public void minhasTarefas () {
	
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


	public List<ModelTarefa> getMinhasTarefas() {
		ModelUsuario userLogado = super.getUserLogado();
		minhasTarefas=daoTarefas.findAll(JPAUtil.getEntityManager().createNamedQuery("Tarefa.findAllDono").setParameter("dono", userLogado));
		
		return minhasTarefas;
	}


	public void setMinhasTarefas(List<ModelTarefa> minhasTarefas) {
		this.minhasTarefas = minhasTarefas;
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
		return (time/(1000*3600*24));
	}
	
	
	

}
