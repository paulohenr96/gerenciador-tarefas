package com.projeto.model;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import org.hibernate.annotations.NamedQuery;

@NamedQuery(name="Usuarios.findAll",query ="FROM ModelUsuario")
@NamedQuery(name="Usuarios.findUser",query ="FROM ModelUsuario where login=:login and senha=:senha")
@NamedQuery(name="Usuarios.findByLogin",query="FROM ModelUsuario where login=:login")
@NamedQuery(name="Usuarios.findAllNomeLike",query="FROM ModelUsuario u where upper(u.nome) like upper(:nome)")
@NamedQuery(name="Usuarios.countAll",query="SELECT count(1) FROM ModelUsuario")
@NamedQuery(name="Usuarios.countAllNomeLike",query="SELECT count(1) FROM ModelUsuario where upper(nome) like upper(:nome)")
@NamedQuery(name="Usuarios.countAutenticar",query="SELECT count(1) FROM ModelUsuario where login=:login and senha=:senha")
@NamedQuery(name="Usuarios.findAllConvidados",query ="FROM ModelTarefa t join t.convidados WHERE t.id = :idTarefa")

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
	
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ModelUsuario other = (ModelUsuario) obj;
		return Objects.equals(id, other.id);
	}

	private String email;
	
	private String login;
	
	private String senha;
	
	@Column
	private boolean admin;

	@OneToMany(mappedBy = "dono",fetch = FetchType.LAZY)
	private List<ModelTarefa> minhasTarefas; 

	@ManyToMany(mappedBy = "convidados",fetch = FetchType.LAZY)
	private List<ModelTarefa> tarefasConvidado;
	
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

	@Override
	public String toString() {
		return id + ";" + nome + ";" + email + ";" + login + ";" + senha + ";" + admin + ";";
	}

	public List<ModelTarefa> getTarefasConvidado() {
		return tarefasConvidado;
	}

	public void setTarefasConvidado(List<ModelTarefa> tarefasConvidado) {
		this.tarefasConvidado = tarefasConvidado;
	}
	
}
