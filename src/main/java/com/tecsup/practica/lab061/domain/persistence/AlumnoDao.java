package com.tecsup.practica.lab061.domain.persistence;

import com.tecsup.practica.lab061.domain.entities.Alumno;
import org.springframework.data.repository.CrudRepository;

public interface AlumnoDao extends CrudRepository<Alumno,Integer> {
}

