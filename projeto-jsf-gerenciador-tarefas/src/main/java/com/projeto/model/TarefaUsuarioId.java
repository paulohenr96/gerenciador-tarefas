package com.projeto.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class TarefaUsuarioId implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	 @Column(name = "tarefa_id")
	private Long tarefa;
	
	@Column(name = "usuario_id")
	private Long usuario;
	public Long getTarefa() {
		return tarefa;
	}
	public void setTarefa(Long tarefa) {
		this.tarefa = tarefa;
	}
	public Long getUsuario() {
		return usuario;
	}
	public void setUsuario(Long usuario) {
		this.usuario = usuario;
	}
	@Override
	public int hashCode() {
		return Objects.hash(tarefa, usuario);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TarefaUsuarioId other = (TarefaUsuarioId) obj;
		return Objects.equals(tarefa, other.tarefa) && Objects.equals(usuario, other.usuario);
	}
	
}