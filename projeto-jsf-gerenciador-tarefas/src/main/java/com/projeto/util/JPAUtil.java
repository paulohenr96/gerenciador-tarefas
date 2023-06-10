package com.projeto.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {

	private static EntityManagerFactory entityManagerFactory= null;
	private static EntityManager entityManager=null;
	static {
		if (entityManagerFactory==null) {
			entityManagerFactory = Persistence.createEntityManagerFactory("projeto-jsf-gerenciador-tarefas");
		}
		
	}
	
	
	
	public static EntityManager getEntityManager() {
		if (entityManagerFactory==null) {
			entityManagerFactory = Persistence.createEntityManagerFactory("projeto-jsf-gerenciador-tarefas");
		}
		if (entityManager==null) {
			entityManager=entityManagerFactory.createEntityManager();
			System.out.println(entityManager.isOpen());
		}
		
		return entityManager;
	}
	
	
	

}
