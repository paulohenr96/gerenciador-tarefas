package com.projeto.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import com.projeto.util.JPAUtil;

public class DAOGeneric<E> {
	private EntityManager entityManager = JPAUtil.getEntityManager();

	
	public void save(E e) {
		entityManager.getTransaction().begin();
		entityManager.flush();

		entityManager.persist(e);
		entityManager.getTransaction().commit();
	}	
	
	public void merge(E e) {
		entityManager.getTransaction().begin();
		entityManager.flush();

		entityManager.merge(e);
		entityManager.getTransaction().commit();
		
	}
	public Long count(Query hql) {
	
		Long resultado=(Long) hql.getSingleResult();

		return resultado;
	}
	
	public List<E> findAll(Query query) {
		List<E> lista;
		try {
			lista = (List<E>)query.getResultList();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;

		}
		
		return lista;
		
		

	}
	public E findEntity(String Hql) {
		E singleResult;
		try {
			singleResult = (E) entityManager.createQuery(Hql).getSingleResult();
		} catch (NoResultException e) {
			// TODO Auto-generated catch block
			return null;
		}
		return singleResult;
		
	}
	public E findEntity(Query q) {
		return (E) q.getSingleResult();
		
	}
	public void delete (E e) {
		
		entityManager.getTransaction().begin();
		entityManager.createQuery("DELETE from "+e.getClass().getSimpleName()+" u where u=:u")
			.setParameter("u", e).executeUpdate();
		entityManager.getTransaction().commit();

	}
	public void update (Query hql) {
		

		entityManager.getTransaction().begin();
		
		hql.executeUpdate();
		
		
		entityManager.getTransaction().commit();
	}
	
	
	
}
