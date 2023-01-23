package com.projeto.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import com.projeto.model.ModelTarefa;
import com.projeto.model.ModelUsuario;
import com.projeto.util.JPAUtil;

public class DAOTarefas extends DAOGeneric<ModelTarefa> {
	
	public List<ModelTarefa> findAll(){
		
		
		return findAll(JPAUtil.getEntityManager().createNamedQuery("Tarefa.findAll"));
	}

	public void adicionarConvidados(ModelUsuario usuarioSelecionado,ModelTarefa tarefa) {
		// TODO Auto-generated method stub
		tarefa.getConvidados().add(usuarioSelecionado);
		merge(tarefa);

	}
	
	public Long contarTodos(ModelTarefa tarefa) {
		
		String hql = "SELECT COUNT(tu) FROM TarefaUsuario tu WHERE tu.tarefaUsuarioId.tarefa = :tarefa";
		Query query = JPAUtil.getEntityManager().createQuery(hql);
		query.setParameter("tarefa", tarefa.getId());
		
		
		return count(query);
	}
	
	public void removerConvidadoTarefa(ModelTarefa tarefa,ModelUsuario user) {
		EntityManager entityManager = JPAUtil.getEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		Query query = entityManager.createQuery("DELETE TarefaUsuario tu WHERE tu.tarefaUsuarioId.tarefa = :tarefa and tu.tarefaUsuarioId.usuario=:user");
		query.setParameter("tarefa", tarefa.getId()).setParameter("user", user.getId());
		query.executeUpdate();
		transaction.commit();
		entityManager.close();
		
	}
	
	public List<ModelTarefa> userTarefasConvocado(ModelUsuario user){
		Query query = JPAUtil.getEntityManager().createNamedQuery("UsuarioTarefa.findTarefas").setParameter("user", user.getId());
		
		return findAll(query);
	}
		
}
