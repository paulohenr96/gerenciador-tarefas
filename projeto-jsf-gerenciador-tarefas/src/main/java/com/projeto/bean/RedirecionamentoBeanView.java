package com.projeto.bean;

import java.io.IOException;

import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.servlet.http.HttpServletRequest;

import com.projeto.bean.util.Contexto;

@SessionScoped
@ManagedBean(name = "redirecionamentoBeanView")
public class RedirecionamentoBeanView extends Contexto {

	
	
	
	public void index() throws IOException {
		System.out.println("redirecionando para o index...");
		HttpServletRequest req=(HttpServletRequest)getContext().getRequest();
		
		getContext().redirect(req.getContextPath()+"/index.jsf");
	}
	
	public void minhasTarefas() throws IOException {
		System.out.println("redirecionando para o minhastarefas...");
		HttpServletRequest req=(HttpServletRequest)getContext().getRequest();
		
		getContext().redirect(req.getContextPath()+"/restrito/minhastarefas.jsf");
	}
}
