package com.entra21.eventoapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.entra21.eventoapp.models.Evento;

// Esta INTERFACE é usada para apenas INSTÂNCIAR e realizar o CRUD já implementado por sua extenção
public interface EventoRepository extends JpaRepository<Evento, String> {

	Evento findByCodigo(long codigo);
	
}
