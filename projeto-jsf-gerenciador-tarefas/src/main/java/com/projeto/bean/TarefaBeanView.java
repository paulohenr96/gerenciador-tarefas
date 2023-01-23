package com.projeto.bean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.AjaxBehaviorEvent;
import javax.persistence.Query;

import com.projeto.bean.util.Contexto;
import com.projeto.bean.util.Mensagens;
import com.projeto.dao.DAOTarefas;
import com.projeto.dao.DAOUsuario;
import com.projeto.model.ModelTarefa;
import com.projeto.model.ModelUsuario;
import com.projeto.util.JPAUtil;

@ViewScoped
@ManagedBean(name = "tarefaBeanView")
public class TarefaBeanView extends Contexto {
	
	private ModelTarefa tarefa = new ModelTarefa();
	private String donoTarefa="";
	private DAOTarefas daoTarefas = new DAOTarefas();
	private DAOUsuario daoUsuario = new DAOUsuario();
	private List<ModelTarefa> todasTarefas= new ArrayList<>();
	private List<ModelUsuario> usuarios = new ArrayList<ModelUsuario>();
	
	public String salvar () {
			Long login = daoUsuario.checarLogin(getDonoTarefa());
			if (login.intValue()>0) {
				ModelUsuario dono = (ModelUsuario) JPAUtil.getEntityManager()
						.createNamedQuery("Usuarios.findByLogin")
						.setParameter("login", getDonoTarefa()).getSingleResult();
				tarefa.setDono(dono);
				daoTarefas.save(tarefa);
				mensagemSucesso("Tarefa cadastrada com sucesso !!");	
			}else {
				mensagemError("Este login não existe.");
			}
			
			tarefa=new ModelTarefa();
		
		return "";
	}
	
	public ModelTarefa getTarefa() {
		return tarefa;
	}

	public void setTarefa(ModelTarefa tarefa) {
		this.tarefa = tarefa;
	}

	public String getDonoTarefa() {
		return donoTarefa;
	}

	public void setDonoTarefa(String donoTarefa) {
		this.donoTarefa = donoTarefa;
	}

	public List<ModelTarefa> getTodasTarefas() {
		todasTarefas=daoTarefas.findAll();
		return todasTarefas;
	}
	
	public void pesquisaUsuario(){
		JPAUtil.getEntityManager().close();
		usuarios=JPAUtil.getEntityManager().createNamedQuery("Usuarios.findAll")
				.getResultList();
		
	}

	public void setTodasTarefas(List<ModelTarefa> todasTarefas) {
		this.todasTarefas = todasTarefas;
	}
	
	public List<ModelUsuario> getUsuarios() {
		return usuarios;
	}
	public void limpar() {
		tarefa=new ModelTarefa();
	}
	public void remover() {
		daoTarefas.delete(tarefa);
		limpar();
		
	}
	

	
	

}
