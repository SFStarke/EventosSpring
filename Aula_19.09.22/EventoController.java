package com.entra21.eventoapp.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.entra21.eventoapp.models.Convidado;
import com.entra21.eventoapp.models.Evento;
import com.entra21.eventoapp.repository.ConvidadoRepository;
import com.entra21.eventoapp.repository.EventoRepository;

@Controller
public class EventoController {
	
	@Autowired
	private EventoRepository er;
	@Autowired
	private ConvidadoRepository cr; 
	
	@RequestMapping(value ="/cadastrarEvento",method= RequestMethod.GET)
	public String form(){
		return "evento/formEvento";
	}
	
	@RequestMapping(value ="/cadastrarEvento",method= RequestMethod.POST)
	public String form(@Valid Evento evento, BindingResult result, RedirectAttributes attributes ){
		if(result.hasErrors()) {
			attributes.addFlashAttribute("mensagem", "Verifique os campos!");
			return "redirect:/cadastrarEvento";
		}
		er.save(evento);
		attributes.addFlashAttribute("mensagem", "Evento adicionado com sucesso!");
		return "redirect:/cadastrarEvento";
	}
	
	@RequestMapping("/eventos")	
	public ModelAndView listaEventos(){		
		ModelAndView mv = new 									
		ModelAndView("evento/listaEvento");	
		Iterable<Evento> eventos = er.findAll();		
		mv.addObject("leventos", eventos); // leventos atributo 								  
										   //que está no HTML		
		return mv;
	}
	
	 @RequestMapping(value = "/{codigo}", method = RequestMethod.GET)
     public ModelAndView detalhesEvento(@PathVariable("codigo") long codigo) {
         Evento evento = er.findByCodigo(codigo);
         ModelAndView mv = new ModelAndView("evento/detalhesEvento");
         mv.addObject("evento",evento);
         
         Iterable<Convidado> convidados = cr.findByEvento(evento);
         mv.addObject("convidados",convidados)
         ;
         return mv;
     }
	 
	 @RequestMapping(value = "/{codigo}", method = RequestMethod.POST)
     public String detalhesEventoPost(@PathVariable("codigo") long codigo, @Valid Convidado convidado, 
    		 BindingResult result, RedirectAttributes attributes) {
		 	if(result.hasErrors()) {
				attributes.addFlashAttribute("mensagem", "Verifique os campos!");
				return "redirect:/{codigo}";
			}
				
			Evento evento = er.findByCodigo(codigo);
			convidado.setEvento(evento);
			cr.save(convidado);
			attributes.addFlashAttribute("mensagem", "Convidado adicionado com sucesso!");
			return "redirect:/{codigo}";
     }

	 @RequestMapping("/deletarEvento")
		public String deletarEvento(long codigo) {
			Evento evento = er.findByCodigo(codigo);
			er.delete(evento);
			
			return "redirect:/eventos";	
			
	}
	 
	 @RequestMapping("/deletarConvidado")
		public String deletarConvidado(String rg) {
			Convidado convidado = cr.findByRg(rg);
			cr.delete(convidado);
			
			Evento evento =  convidado.getEvento();
			long codigoLong = evento.getCodigo();
			String codigo = "" + codigoLong;
			
			return "redirect:/" + codigo ;		
	}
	 
	 @RequestMapping(value= "/editarEvento", method=RequestMethod.GET)
		public ModelAndView editarEvento(long codigo){
			
			Evento evento = er.findByCodigo(codigo);
			ModelAndView mv = new ModelAndView("evento/editaEvento");
			
			mv.addObject("evento", evento);
		
			return mv;
	}
		
	//METODO QUE EDITA O EVENTO
	@RequestMapping(value="/editarEvento", method=RequestMethod.POST)
	public String editarEventoPost(Evento evento){
			er.save(evento);
			return "redirect:/eventos";
	}
	
	//METODO QUE EDITA O CONVIDADO
	 @RequestMapping(value="editarConvidado/{codigo}/{rg}", method=RequestMethod.GET)
     public ModelAndView editarConvidado(@PathVariable("codigo") long codigo, @PathVariable("rg") String rg) {
         Evento evento = er.findByCodigo(codigo);
         ModelAndView mv = new ModelAndView("evento/editaConvidado");
         mv.addObject("evento",evento);
         
         Convidado convidado = cr.findByRg(rg);
	     convidado.setEvento(evento);
//		
			mv.addObject("evento",evento);
			mv.addObject("convidado",convidado);
         return mv;
     }
	 
	 @RequestMapping(value="editarConvidado/{codigo}/{rg}", method=RequestMethod.POST)
     public String editarConvidadoPost(@PathVariable("codigo") long codigo, @PathVariable("rg") String rg, Convidado convidado) {
		 	
			Evento evento = er.findByCodigo(codigo);
			convidado.setEvento(evento);
			cr.save(convidado);
			
			return "redirect:/{codigo}";
     }	

}
