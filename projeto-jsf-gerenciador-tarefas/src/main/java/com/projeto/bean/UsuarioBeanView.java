package com.projeto.bean;

import java.beans.JavaBean;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.persistence.Query;

import com.projeto.bean.util.Contexto;
import com.projeto.bean.util.Mensagens;
import com.projeto.dao.DAOUsuario;
import com.projeto.lazy.ModelUsuarioDataLazyDataModel;
import com.projeto.model.ModelUsuario;
import com.projeto.util.JPAUtil;

@ViewScoped
@ManagedBean(name = "usuarioBeanView")
public class UsuarioBeanView extends Contexto {

	private DAOUsuario daoUsuario = new DAOUsuario();

	private ModelUsuario usuarioSelecionado = new ModelUsuario();
	private String login = "";
	private List<ModelUsuario> usuarios = new ArrayList<>();
	private String nomePesquisa="";
	private ModelUsuarioDataLazyDataModel lazyUsuario = new ModelUsuarioDataLazyDataModel();

	public ModelUsuario getUsuarioSelecionado() {
		return usuarioSelecionado;
	}

	public void setUsuarioSelecionado(ModelUsuario usuarioSelecionado) {
		this.usuarioSelecionado = usuarioSelecionado;
	}

	public String salvar() {
		System.out.println("teste");
		getUserLogado();
		if (usuarioSelecionado != null) {
			if (daoUsuario.checarLogin(usuarioSelecionado.getLogin()) > 0) {
				mensagemError("Já existe usuario com este login");
			} else {
				daoUsuario.save(usuarioSelecionado);
				usuarioSelecionado = new ModelUsuario();
				mensagemInfo("Usuario cadastrado com sucesso");
			}

		}

		return "index.jsf";
	}

	public List<ModelUsuario> getUsuarios() {
//		usuarios = (List<ModelUsuario>) JPAUtil.getEntityManager().createNamedQuery("Usuarios.findAll").getResultList();

		return usuarios;
	}

	public String novo() {
		limpar();
		mensagemSucesso("Formulário foi limpo com sucesso !");

		return "index.jsf";
	}

	public void limpar() {
		usuarioSelecionado = new ModelUsuario();
	}

	public String remover() {
		System.out.println("Remover");
		daoUsuario.delete(usuarioSelecionado);
		usuarioSelecionado = new ModelUsuario();

		return "";
	}

	public void setUsuarios(List<ModelUsuario> usuarios) {
		this.usuarios = usuarios;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String editar() {
		System.out.println("Editar");
		String login = usuarioSelecionado.getLogin();
		if (usuarioSelecionado.getId() == null) {
			mensagemError("Este usuário não está cadastrado");
		} else {
			ModelUsuario find = JPAUtil.getEntityManager().find(ModelUsuario.class, usuarioSelecionado.getId());

			if (!find.getLogin().equals(login)) {
				boolean loginNovo = daoUsuario.checarLogin(login).intValue() == 0;

				if (loginNovo) {
					usuarioSelecionado.setLogin(login);
					daoUsuario.merge(usuarioSelecionado);
					mensagemSucesso("Usuario editado");
					limpar();

				} else {
					mensagemError("Já existe usuario com este login");

				}

			} else {
				usuarioSelecionado.setLogin(login);

				daoUsuario.merge(usuarioSelecionado);
				mensagemSucesso("Usuario editado");
				limpar();

			}

		}
		return "index.jsf";
	}

	public ModelUsuarioDataLazyDataModel getLazyUsuario() {
		lazyUsuario.setNome(nomePesquisa);
		
			
		
		return lazyUsuario;
	}

	public void setLazyUsuario(ModelUsuarioDataLazyDataModel lazyUsuario) {
		this.lazyUsuario = lazyUsuario;
	}
	
	public void pesquisaUsuario(){
		if (nomePesquisa.equals("")) {
			Query query = JPAUtil.getEntityManager().createNamedQuery("Usuarios.findAll");
			usuarios=daoUsuario.findAll(query);
					
		}else if (!nomePesquisa.equals("")) {
			
			usuarios= daoUsuario.pesquisaUsuario(nomePesquisa);
		}
		
		
	}

	public String getNomePesquisa() {
		return nomePesquisa;
	}

	public void setNomePesquisa(String nomePesquisa) {
		this.nomePesquisa = nomePesquisa;
	}
	
}
