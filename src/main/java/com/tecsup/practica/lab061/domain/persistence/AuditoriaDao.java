package com.tecsup.practica.lab061.domain.persistence;

import com.tecsup.practica.lab061.domain.entities.Auditoria;
import org.springframework.data.repository.CrudRepository;


public interface AuditoriaDao extends CrudRepository<Auditoria, Integer> {

}

