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
	
	@Autowired // Ingeção de dependências 
	private EventoRepository er; // É criada um nova Instância de sua INTERFACE
	@Autowired
	private ConvidadoRepository cr;
	
	// "value" recebe referência. & Definição de um método. (GET retorna formulário)
	@RequestMapping(value = "/cadastrarEvento", method= RequestMethod.GET)
	public String form(){
	return "evento/formEvento";	
	}
	
	// "POST" quando salvo, envia para requisição em DB. 
	@RequestMapping(value = "/cadastrarEvento", method= RequestMethod.POST)
	public String form(@Valid Evento evento,  BindingResult result,
			RedirectAttributes attributes){
		if(result.hasErrors()) {
	        attributes.addFlashAttribute("mensagem", "Verifique os campos!");
	        return "redirect:/cadastrarEvento"; // Redireciona Cadastrar Evento
	    }
		
	    er.save(evento); //É salvo o evento em "DB"
	    
	    attributes.addFlashAttribute("mensagem", "Evento cadastrado com sucesso!");
	    
	return "redirect:/cadastrarEvento";	
	}
	
	// Método para retorno "Busca" da Lista de evento
	@RequestMapping("/eventos")	
	public ModelAndView listaEventos(){		
		ModelAndView mv = new ModelAndView("evento/listaEvento");	// Objeto para receber a lista de eventos "listaEvento"
		Iterable<Evento> eventos = er.findAll(); // Objeto Interable, Busca do banco de dados e transforma em sql		
		mv.addObject("leventos", eventos); // Faz aparecer na view [ "leventos" está em <tr th:each="evento : ${leventos}">] 								  
						   		
		return mv;
	}
	
	@RequestMapping(value = "/{codigo}", method = RequestMethod.GET)
	public ModelAndView detalhesEvento(@PathVariable("codigo") long codigo) {
		Evento evento = er.findByCodigo(codigo);
		ModelAndView mv = new ModelAndView("evento/detalhesEvento"); 
		mv.addObject("evento", evento);
		// INTERABLE faz busca no "DB"
		Iterable<Convidado> convidados = cr.findByEvento(evento);
		mv.addObject("convidados", convidados);
		return mv;
	}
	
	@RequestMapping(value = "/{codigo}", method = RequestMethod.POST)
	public String detalhesEventoPost(@PathVariable("codigo") long codigo,
			@Valid Convidado convidado, BindingResult result,
			RedirectAttributes attributes) {
		if(result.hasErrors()) {
	        attributes.addFlashAttribute("mensagem", "Verifique os campos!");
	        return "redirect:/{codigo}";
	    }
	    Evento evento = er.findByCodigo(codigo);
	    convidado.setEvento(evento);
	    cr.save(convidado);
	    attributes.addFlashAttribute("mensagem", "Convidado adicionado com sucesso!");
	    return "redirect:/{codigo}";
	    
//		ModelAndView mv = new ModelAndView("evento/detalhesEvento");
//		Evento evento = er.findByCodigo(codigo);
//		convidado.setEvento(evento);
//		cr.save(convidado);
//		mv.addObject("evento", evento);
//		return "redirect:/{codigo}";
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
	
//	@RequestMapping("update")
//	public String update(long codigo) {
//		Evento evento = er.findByCodigo(codigo);
//		er.findByCodigo(codigo);
//		return "redirect:/editarEvento";
//	}
	
//	@RequestMapping(value = "/{codigo}", method = RequestMethod.GET)
//	public String update(long codigo) {
//		Evento evento = er.findByCodigo(codigo);
//		er.findByCodigo(codigo);
//		return "redirect:/evento/editarEvento";
//	}
	
//	@RequestMapping(value = "/editar/{codigo}", method = RequestMethod.GET)
//	public ModelAndView update(@PathVariable("codigo") long codigo) {
	@RequestMapping(value= "/editarEvento", method=RequestMethod.GET)
	public ModelAndView editarEvento(long codigo){
		
		Evento evento = er.findByCodigo(codigo);
		ModelAndView mv = new ModelAndView("evento/editarEvento"); 
	
		mv.addObject("evento", evento);
		Iterable<Convidado> convidados = cr.findByEvento(evento);
		mv.addObject("convidados", convidados);
		return mv;
	}
	
	//METODO QUE EDITA O EVENTO
		@RequestMapping(value="/editarEvento", method=RequestMethod.POST)
		public String editarEventoPost(Evento evento){
				er.save(evento);
				return "redirect:/eventos";
		}

//	@RequestMapping(value = "/editar/{codigo}", method = RequestMethod.POST)
//	public String updatePost(@PathVariable("codigo") long codigo,
//			@Valid Evento evento, BindingResult result,
//			RedirectAttributes attributes) {
//		if(result.hasErrors()) { // Verefica se campos estão preenchidos
//	        attributes.addFlashAttribute("mensagem", "Verifique os campos!");
//	        return "redirect:editar_/{codigo}";
//	    }
//	    er.save(evento);
//	    attributes.addFlashAttribute("mensagem", "Evento atualizado com sucesso!");
//	    return "redirect:/editar/{codigo}";
//	}
	
// E D I T A R  C O N V I D A D O...	
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
