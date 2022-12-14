package com.entra21.eventoapp.models;

import java.io.Serializable;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

// Nesta classe fica estabelecidA a entidade para BANCO DE DADOS


@Entity
public class Evento implements Serializable{ //Implements Gera ID automático
	
	private static final long serialVersionUID = 1L; //Gerar ID automático
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO) // " IDENTITY "
	private long codigo;
	@NotBlank
	private String nome;
	@NotBlank
	private String local;
	@NotBlank
	private String data;
	@NotBlank
	private String horario;
	
	@OneToMany
	private List<Convidado> convidados;

	
	public List<Convidado> getConvidados() {
		return convidados;
	}

	public void setConvidados(List<Convidado> convidados) {
		this.convidados = convidados;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getHorario() {
		return horario;
	}

	public void setHorario(String horario) {
		this.horario = horario;
	}

	public long getCodigo() {
		return codigo;
	}

	public void setCodigo(long codigo) {
		this.codigo = codigo;
	}	
}
