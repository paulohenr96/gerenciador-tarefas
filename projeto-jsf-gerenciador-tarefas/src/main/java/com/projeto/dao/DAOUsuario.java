package com.projeto.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.projeto.model.ModelTarefa;
import com.projeto.model.ModelUsuario;
import com.projeto.util.JPAUtil;

public class DAOUsuario extends DAOGeneric<ModelUsuario> {
	
	
	
	public Long checarLogin (String login, String senha) {
		
		Query query = JPAUtil.getEntityManager().createQuery("select count(1) from ModelUsuario where upper(login)=upper(:login) AND senha=:senha").setParameter("login", login).setParameter("senha", senha);
		return count(query);
	}
public Long pesquisarLogin (String login) {
		
		Query query = JPAUtil.getEntityManager().createQuery("select count(1) from ModelUsuario where upper(login)=upper(:login)").setParameter("login", login);
		return count(query);
	}
	
	public List<ModelUsuario> pesquisaUsuario (String nome){
		
		Query query = JPAUtil.getEntityManager().createNamedQuery("Usuarios.findAllNomeLike").setParameter("nome", "%"+nome+"%");
		
		return findAll(query);
	}

	public ModelUsuario findEntityByLogin(String usuarioParaConvidar) {
		// TODO Auto-generated method stub
		Query query = JPAUtil.getEntityManager().createNamedQuery("Usuarios.findByLogin").setParameter("login", usuarioParaConvidar);
		return findEntity(query);
	}
	
	
	public boolean contemConvidado(ModelUsuario user,ModelTarefa tarefa) {
		
		List<ModelUsuario> lista = findAllUsuarioTarefa(tarefa);
		
		if (lista.contains(user)) {
			return true;
		}else {
			return false;
		}
		
		
		
	}
	
	public List<ModelUsuario> findAllUsuarioTarefa (ModelTarefa tarefa){
		List<ModelUsuario> lista=(List<ModelUsuario>)JPAUtil.getEntityManager()
				.createQuery("select t.convidados from ModelTarefa t where t.id=:idTarefa")
				.setParameter("idTarefa", tarefa.getId()).getResultList();
		
		return lista;
	}
	
	

}
