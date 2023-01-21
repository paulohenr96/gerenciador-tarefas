package com.projeto.dao;

import java.util.List;

import com.projeto.model.ModelTarefa;
import com.projeto.util.JPAUtil;

public class DAOTarefas extends DAOGeneric<ModelTarefa> {
	
	public List<ModelTarefa> findAll(){
		
		
		return findAll(JPAUtil.getEntityManager().createNamedQuery("Tarefa.findAll"));
	}

}
