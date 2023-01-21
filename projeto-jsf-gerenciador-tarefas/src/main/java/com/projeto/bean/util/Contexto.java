package com.projeto.bean.util;

import javax.faces.context.FacesContext;

import com.projeto.model.ModelUsuario;

public class Contexto extends Mensagens{

	
	public ModelUsuario getUserLogado() {
		ModelUsuario user = (ModelUsuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("userlogado");
		
		return user;
	}
}
