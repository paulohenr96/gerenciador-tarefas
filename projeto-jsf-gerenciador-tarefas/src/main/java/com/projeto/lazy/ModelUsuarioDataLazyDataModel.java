package com.projeto.lazy;

import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

import com.projeto.dao.DAOUsuario;
import com.projeto.model.ModelUsuario;
import com.projeto.util.JPAUtil;

public class ModelUsuarioDataLazyDataModel extends LazyDataModel<ModelUsuario> {
	
	private String nome="";
	private DAOUsuario daoUsuario=new DAOUsuario();
	@Override
	public int count(Map<String, FilterMeta> filterBy) {
		// TODO Auto-generated method stub
		int total=0;
		if (nome.equals("")) {
			total = daoUsuario.count(JPAUtil.getEntityManager().createNamedQuery("Usuarios.countAll")).intValue();

		}else {
			total = daoUsuario.count(JPAUtil.getEntityManager().createNamedQuery("Usuarios.countAllNomeLike").setParameter("nome", "%"+nome+"%")).intValue();
		}
		return total;
	}

	@Override
	public List<ModelUsuario> load(int first, int pageSize, Map<String, SortMeta> sortBy,
			Map<String, FilterMeta> filterBy) {
		// TODO Auto-generated method stub
		Query query= null;
//		if (nome.equals("")) {
//			query = JPAUtil.getEntityManager().createNamedQuery("Usuarios.findAll").setFirstResult(first).setMaxResults(pageSize);
//
//		}else {
//
//		}
		query = JPAUtil.getEntityManager().createNamedQuery("Usuarios.findAllNomeLike").setParameter("nome", "%"+nome+"%").setFirstResult(first).setMaxResults(pageSize);

		return daoUsuario.findAll(query);

	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	

}
