package com.projeto.lazy;

import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

import com.projeto.dao.DAOTarefas;
import com.projeto.model.ModelTarefa;
import com.projeto.util.JPAUtil;

public class ModelTarefaDataLazy extends LazyDataModel<ModelTarefa>{

	private Query queryContar;
	private Query queryBusca;
	private DAOTarefas daoTarefas=new DAOTarefas();
	private int total;
	@Override
	public int count(Map<String, FilterMeta> filterBy) {
		// TODO Auto-generated method stub
		System.out.println(getQueryContar());
		setTotal(daoTarefas.count(getQueryContar()).intValue());
		
		return getTotal();
	}

	@Override
	public List<ModelTarefa> load(int first, int pageSize, Map<String, SortMeta> sortBy,
			Map<String, FilterMeta> filterBy) {
		// TODO Auto-generated method stub
		Query query = getQueryBusca().setFirstResult(first).setMaxResults(pageSize);
		return daoTarefas.findAll(query) ;
	}

	public Query getQueryContar() {
		return queryContar;
	}
	
	public void setQueryContar(Query queryContar) {
		this.queryContar = queryContar;
	}

	public Query getQueryBusca() {
		return queryBusca;
	}

	public void setQueryBusca(Query queryBusca) {
		this.queryBusca = queryBusca;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}


}
