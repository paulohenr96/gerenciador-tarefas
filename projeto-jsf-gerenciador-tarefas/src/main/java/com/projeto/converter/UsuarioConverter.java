package com.projeto.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.projeto.model.ModelUsuario;
import com.projeto.util.JPAUtil;

@FacesConverter(forClass = ModelUsuario.class, value = "usuarioConverter")
public class UsuarioConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String login) {
		// TODO Auto-generated method stub
		if (login != null && !login.isEmpty()) {
			return JPAUtil.getEntityManager().createNamedQuery("Usuarios.findByLogin").setParameter("login", login);

		}
		return login;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		// TODO Auto-generated method stub

		if (value != null) {

			ModelUsuario m = (ModelUsuario) value;
			return m.getLogin();

		}

		return null;
	}

}
