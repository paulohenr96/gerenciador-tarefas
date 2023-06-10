package com.projeto.bean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.persistence.TypedQuery;

import com.projeto.bean.util.Contexto;
import com.projeto.dao.DAOTarefas;
import com.projeto.lazy.ModelTarefaDataLazy;
import com.projeto.model.ModelTarefa;
import com.projeto.util.JPAUtil;

@ViewScoped
@ManagedBean(name = "minhasTarefasConvidadoBeanView")
public class MinhasTarefasConvidadoBeanView extends Contexto {
	private DAOTarefas daoTarefas = new DAOTarefas();

	private ModelTarefaDataLazy tarefaDataLazyCompletas = new ModelTarefaDataLazy();
	private ModelTarefaDataLazy tarefaDataLazyPendentes = new ModelTarefaDataLazy();
	
	
	
	
	public ModelTarefaDataLazy getTarefasPendentes() {

		TypedQuery<ModelTarefa> query = JPAUtil.getEntityManager().createQuery("Select tarefa FROM ModelTarefa tarefa JOIN tarefa.convidados c where c.id=:user and tarefa.dataTermino is null", ModelTarefa.class);
		query.setParameter("user", getUserLogado().getId());
		
		tarefaDataLazyPendentes.setQueryBusca(query);
		tarefaDataLazyPendentes.setQueryContar(JPAUtil.getEntityManager().createQuery("Select count(1)  FROM ModelTarefa tarefa JOIN tarefa.convidados c where c.id=:user and tarefa.dataTermino is  null")
				.setParameter("user", getUserLogado().getId()));
		System.out.println(tarefaDataLazyPendentes.getTotal());
		return tarefaDataLazyPendentes;
	}
	public void setTarefaDataLazyPendentes(ModelTarefaDataLazy tarefaDataLazyPendentes) {
		this.tarefaDataLazyPendentes = tarefaDataLazyPendentes;
	}
	public ModelTarefaDataLazy getMinhasTarefasCompletas() {
    	String hqlConsulta="SELECT t FROM ModelTarefa t  JOIN t.convidados c Where t.dataTermino IS NOT  null AND c.id = :usuario";
    	String hqlContar="SELECT count(1) FROM ModelTarefa t  JOIN t.convidados c Where t.dataTermino IS NOT  null AND c.id = :usuario";

    	
    	TypedQuery<Long> queryContar = JPAUtil.getEntityManager().createQuery(hqlContar, Long.class);
		queryContar.setParameter("usuario", getUserLogado().getId()); // Substitua 'userId' pelo valor do usuário desejado
		
		tarefaDataLazyCompletas.setQueryContar(queryContar);
    	
    	
    	
		TypedQuery<ModelTarefa> query = JPAUtil.getEntityManager().createQuery(hqlConsulta,ModelTarefa.class);
		query.setParameter("usuario", getUserLogado().getId());
		
		
		tarefaDataLazyCompletas.setQueryBusca(query);
		
		
		
		
		System.out.println(tarefaDataLazyCompletas.getWrappedData()+" === userLogado : "+getUserLogado().getId());
		
		return tarefaDataLazyCompletas;
	}
	public void setTarefaDataLazyCompletas(ModelTarefaDataLazy tarefaDataLazyCompletas) {
		this.tarefaDataLazyCompletas = tarefaDataLazyCompletas;
	}
	
	
	
	
	
	
}
