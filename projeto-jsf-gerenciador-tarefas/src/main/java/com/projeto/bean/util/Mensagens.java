package com.projeto.bean.util;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class Mensagens {
	
	
	
	public void mensagemError(String msg) {
		FacesContext.getCurrentInstance().addMessage("msg",new FacesMessage(FacesMessage.SEVERITY_ERROR,msg,msg));

	}
	
	public void mensagemInfo(String msg) {
		FacesContext.getCurrentInstance().addMessage("msg",new FacesMessage(FacesMessage.SEVERITY_INFO,msg,msg));

	}
	public void mensagemSucesso(String msg) {
		FacesContext.getCurrentInstance().addMessage("msg",new FacesMessage(FacesMessage.SEVERITY_INFO,msg,msg));

	}
}
