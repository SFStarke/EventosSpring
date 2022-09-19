package com.entra21.eventoapp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.entra21.eventoapp.models.Usuario;
import com.entra21.eventoapp.repository.UsuarioRepository;

@Repository //Esta anotation é requerida por capturar exeções de persistência
@Transactional
public class ImplementsUserDetailsService  implements UserDetailsService{
	@Autowired
	private UsuarioRepository ur; // injeção de dependência

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		Usuario usuario = ur.findByLogin(username); //Busca o usuario pelo nome
		
		if(usuario == null) {
		throw new UsernameNotFoundException("Usuário não Encontrado");
		}
		return new User(usuario.getUsername(), usuario.getPassword(),true,true,true,true, usuario.getAuthorities());
	}
	
}

