package com.entra21.eventoapp.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import org.springframework.security.core.GrantedAuthority;

@SuppressWarnings("serial")
@Entity
public class Role implements GrantedAuthority{

	@Id
	private String nomeRole;
	
	@ManyToMany
	private List<Usuario> user;
	
	public String getNomeRole() {
		return nomeRole;
	}

	public void setNomeRole(String nomeRole) {
		this.nomeRole = nomeRole;
	}

	public String getAuthority() {
		return this.nomeRole;
	}

	public List<Usuario> getUser() {
		return user;
	}

	public void setUser(List<Usuario> user) {
		this.user = user;
	}

}
