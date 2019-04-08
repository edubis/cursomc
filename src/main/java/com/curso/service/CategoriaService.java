package com.curso.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.curso.domain.Categoria;
import com.curso.repositories.CategoriaRepository;

@Service
public class CategoriaService {
	
	//declarando uma dependencia numa classe
	@Autowired
	private CategoriaRepository repo;
	
	public Categoria buscar(Integer id) {

		Optional<Categoria> obj = repo.findById(id);
		
		return obj.orElse(null);
		
	}
	
}
