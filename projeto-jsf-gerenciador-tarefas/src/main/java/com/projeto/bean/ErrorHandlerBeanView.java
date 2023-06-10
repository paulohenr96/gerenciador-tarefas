package com.projeto.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import com.projeto.bean.util.Contexto;

@RequestScoped
@ManagedBean(name="errorHandlerBeanView")
public class ErrorHandlerBeanView extends Contexto {

	
	public String getCodigo() {
		return ""+getContext().getRequestMap().get("javax.servlet.error.status_code");
	}
	public String getMensagem() {
		return (String)getContext().getRequestMap().get("javax.servlet.error.message");

	}
	
	public String getExceptionType(){
		String val =getContext().
			getRequestMap().get("javax.servlet.error.exception_type").toString();
		return val;
	}

	public String getException(){
		String val =  (String)((Exception)getContext().
			getRequestMap().get("javax.servlet.error.exception")).toString();
		return val;
	}

	public String getRequestURI(){
		return (String)getContext().
			getRequestMap().get("javax.servlet.error.request_uri");
	}

	public String getServletName(){
		return (String)getContext().
			getRequestMap().get("javax.servlet.error.servlet_name");
	}
}
