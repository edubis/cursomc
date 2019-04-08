package com.curso.repositories;

import org.springframework.stereotype.Repository;

import com.curso.domain.Categoria;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {

	
	
	
}
