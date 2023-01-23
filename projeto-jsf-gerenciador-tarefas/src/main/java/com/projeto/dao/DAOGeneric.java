package com.projeto.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.projeto.util.JPAUtil;

public class DAOGeneric<E> {

	
	public void save(E e) {
		EntityManager entityManager = JPAUtil.getEntityManager();
		entityManager.getTransaction().begin();;
		entityManager.persist(e);
		entityManager.getTransaction().commit();
		entityManager.close();
	}	
	
	public void merge(E e) {
		EntityManager entityManager = JPAUtil.getEntityManager();
		entityManager.getTransaction().begin();;
		entityManager.merge(e);
		entityManager.getTransaction().commit();
		entityManager.close();
		
	}
	public Long count(Query hql) {
		EntityManager entityManager = JPAUtil.getEntityManager();
		Long resultado=(Long) hql.getSingleResult();
		entityManager.close();

		return resultado;
	}
	
	public List<E> findAll(Query query) {
		JPAUtil.getEntityManager().close();
		List<E> lista=(List<E>)query.getResultList();
		JPAUtil.getEntityManager().close();
		
		return lista;
		
		

	}
	
	public E findEntity(Query q) {
		JPAUtil.getEntityManager().close();
		return (E) q.getSingleResult();
		
	}
	
	public void delete (E e) {
		
		EntityManager entityManager = JPAUtil.getEntityManager();
		entityManager.getTransaction().begin();
		entityManager.createQuery("DELETE from "+e.getClass().getSimpleName()+" u where u=:u")
			.setParameter("u", e).executeUpdate();
		entityManager.getTransaction().commit();
		entityManager.close();

	}
	public void update (Query hql) {
		

		EntityManager entityManager = JPAUtil.getEntityManager();
		entityManager.getTransaction().begin();
		
		hql.executeUpdate();
		
		
		entityManager.getTransaction().commit();
		entityManager.close();
	}
	
	
	
}
