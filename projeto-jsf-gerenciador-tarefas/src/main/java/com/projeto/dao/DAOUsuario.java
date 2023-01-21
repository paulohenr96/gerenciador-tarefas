package com.projeto.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.projeto.model.ModelUsuario;
import com.projeto.util.JPAUtil;

public class DAOUsuario extends DAOGeneric<ModelUsuario> {
	
	
	public void deletar(ModelUsuario user) {
		EntityManager entityManager = JPAUtil.getEntityManager();
		entityManager.getTransaction().begin();
		entityManager.createQuery("DELETE from ModelUsuario u where u.id=:id")
			.setParameter("id", user.getId()).executeUpdate();
		entityManager.getTransaction().commit();
		entityManager.close();
	}
	
	public Long checarLogin (String login) {
		
		Query query = JPAUtil.getEntityManager().createQuery("select count(1) from ModelUsuario where upper(login)=upper(:login)").setParameter("login", login);
		return count(query);
	}
	
	public List<ModelUsuario> pesquisaUsuario (String nome){
		
		Query query = JPAUtil.getEntityManager().createNamedQuery("Usuarios.findAllNomeLike").setParameter("nome", "%"+nome+"%");
		
		return findAll(query);
	}
	
	

}
