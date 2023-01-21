package com.projeto.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.NamedQuery;

@NamedQuery(name = "Tarefa.findAll",query = "FROM ModelTarefa")
@NamedQuery(name = "Tarefa.findAllDono",query = "FROM ModelTarefa where dono=:dono")

@NamedQuery(name = "Tarefa.donoCount",query="select count(1) FROM ModelTarefa where dono=:dono")

@Entity
public class ModelTarefa {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne(optional = false)
	private ModelUsuario dono = new ModelUsuario();

	@ManyToMany
	private List<ModelUsuario> convidados = new ArrayList<ModelUsuario>();

	private String descricao;
	private Date dataInicial;
	private Date dataFinal;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public ModelUsuario getDono() {
		return dono;
	}
	public void setDono(ModelUsuario dono) {
		this.dono = dono;
	}
	public List<ModelUsuario> getConvidados() {
		return convidados;
	}
	public void setConvidados(List<ModelUsuario> convidados) {
		this.convidados = convidados;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public Date getDataInicial() {
		return dataInicial;
	}
	public void setDataInicial(Date dataInicial) {
		this.dataInicial = dataInicial;
	}
	public Date getDataFinal() {
		return dataFinal;
	}
	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}

}
