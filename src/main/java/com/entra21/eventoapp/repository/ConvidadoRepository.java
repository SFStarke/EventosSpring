package com.entra21.eventoapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.entra21.eventoapp.models.Convidado;
import com.entra21.eventoapp.models.Evento;

//Esta INTERFACE é usada para apenas INSTÂNCIAR e realizar o CRUD já implementado por sua extenção
public interface ConvidadoRepository extends JpaRepository<Convidado, String>{

	Iterable<Convidado>findByEvento(Evento evento);

	Convidado findByRg(String rg);
	
	
}
