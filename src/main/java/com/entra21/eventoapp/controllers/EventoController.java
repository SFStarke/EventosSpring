package com.entra21.eventoapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.entra21.eventoapp.models.Evento;
import com.entra21.eventoapp.repository.EventoRepository;

@Controller
public class EventoController {
	
	@Autowired
	private EventoRepository er;
	
	@RequestMapping(value = "/cadastrarEvento", method= RequestMethod.GET)
	public String form(){
	return "evento/formEvento";	
	}
	
	@RequestMapping(value = "/cadastrarEvento", method= RequestMethod.POST)
	public String form(Evento evento){
		er.save(evento); //É feito sua ingeção de dependência
	return "redirect:/cadastrarEvento";	
	}
	
	
}
