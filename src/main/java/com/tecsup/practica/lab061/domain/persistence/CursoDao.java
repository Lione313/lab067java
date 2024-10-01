package com.tecsup.practica.lab061.domain.persistence;

import com.tecsup.practica.lab061.domain.entities.Curso;
import org.springframework.data.repository.CrudRepository;

public interface CursoDao extends CrudRepository<Curso,Integer> {
}

