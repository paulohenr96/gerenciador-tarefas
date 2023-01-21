package com.projeto.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.NamedQuery;

@NamedQuery(name="Usuarios.findAll",query ="FROM ModelUsuario")
@NamedQuery(name="Usuarios.findUser",query ="FROM ModelUsuario where login=:login and senha=:senha")
@NamedQuery(name="Usuarios.findByLogin",query="FROM ModelUsuario where login=:login")
@NamedQuery(name="Usuarios.findAllNomeLike",query="FROM ModelUsuario u where upper(u.nome) like upper(:nome)")
@NamedQuery(name="Usuarios.countAll",query="SELECT count(1) FROM ModelUsuario")
@NamedQuery(name="Usuarios.countAllNomeLike",query="SELECT count(1) FROM ModelUsuario where upper(nome) like upper(:nome)")
@NamedQuery(name="Usuarios.countAutenticar",query="SELECT count(1) FROM ModelUsuario where login=:login and senha=:senha")

@Entity
public class ModelUsuario implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String nome;
	
	private String email;
	
	private String login;
	
	private String senha;
	
	@Column
	private boolean admin;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public boolean getAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}
	
}
