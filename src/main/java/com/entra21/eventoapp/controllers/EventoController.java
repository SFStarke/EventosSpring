package com.entra21.eventoapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

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
	@RequestMapping("/eventos")	
	public ModelAndView listaEventos(){		
		ModelAndView mv = new 									
		ModelAndView("evento/listaEvento");	
		Iterable<Evento> eventos = er.findAll(); // Busca do banco de dados e transforma em sql		
		mv.addObject("leventos", eventos); // leventos atributo "L de lista" 								  
						   //que está no HTML		
		return mv;
	}
	@RequestMapping("/{codigo}")
	public ModelAndView detalhesEvento(@PathVariable("codigo") long codigo) {
		Evento evento = er.findByCodigo(codigo);
		ModelAndView mv = new ModelAndView("evento/detalhesEvento");
		mv.addObject("evento", evento);
		return mv;
	}
}
