package com.projeto.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NamedQuery;


@NamedQuery(name = "UsuarioTarefa.findTarefas",query = "Select tarefa FROM ModelTarefa tarefa JOIN tarefa.convidados c where c.id=:user and tarefa.dataTermino=null")
@NamedQuery(name = "UsuarioTarefa.findTarefasPendentes",query = "Select tarefa FROM ModelTarefa tarefa JOIN tarefa.convidados c where c.id=:user and tarefa.dataTermino!=null")


@Table(name = "tarefa_usuario")
@Entity
public class TarefaUsuario {



    @EmbeddedId
	private TarefaUsuarioId tarefaUsuarioId;
    
	
	public TarefaUsuarioId getTarefaUsuarioId() {
		return tarefaUsuarioId;
	}

	public void setTarefaUsuarioId(TarefaUsuarioId tarefaUsuarioId) {
		this.tarefaUsuarioId = tarefaUsuarioId;
	}

}