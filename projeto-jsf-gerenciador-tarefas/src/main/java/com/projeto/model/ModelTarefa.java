package com.projeto.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;


import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.NamedQuery;

@NamedQuery(name = "Tarefa.findAll",query = "FROM ModelTarefa")
@NamedQuery(name = "Tarefa.findAllDonoPendentes",query = "FROM ModelTarefa where dono=:dono AND dataTermino = null")
@NamedQuery(name = "Tarefa.findAllDonoCompletas",query = "FROM ModelTarefa where dono=:dono AND dataTermino != null")
@NamedQuery(name = "Tarefa.donoCount",query="select count(1) FROM ModelTarefa where dono=:dono")
@NamedQuery(name = "Tarefa.donoCountCompletas",query="select count(1) FROM ModelTarefa where dono=:dono AND dataTermino!=null")
@NamedQuery(name = "Tarefa.donoCountPendentes",query="select count(1) FROM ModelTarefa where dono=:dono AND dataTermino=null")


@Entity
public class ModelTarefa {
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
		ModelTarefa other = (ModelTarefa) obj;
		return Objects.equals(id, other.id);
	}
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne(optional = false)
	private ModelUsuario dono = new ModelUsuario();

	 @JoinTable(name = "tarefa_usuario",
		        joinColumns = {@JoinColumn(name = "tarefa_id")},
		        inverseJoinColumns = {@JoinColumn(name = "usuario_id")}
		    )
	@ManyToMany(fetch=FetchType.LAZY)
	private List<ModelUsuario> convidados = new ArrayList<ModelUsuario>();
	
	
	private String descricao;
	private Date dataInicial;
	private Date dataFinal;
	
	@Column
	private Date dataTermino;
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
	public Date getDataTermino() {
		return dataTermino;
	}
	public void setDataTermino(Date dataTermino) {
		this.dataTermino = dataTermino;
	}

}
