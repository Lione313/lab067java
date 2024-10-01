package com.tecsup.practica.lab061.services;

import com.tecsup.practica.lab061.domain.entities.Alumno;
import com.tecsup.practica.lab061.domain.persistence.AlumnoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AlumnoServiceImpl implements AlumnoService {

    @Autowired
    private AlumnoDao dao;

    @Override
    @Transactional
    public void grabaralumno(Alumno alumno) {
        dao.save(alumno);
    }

    @Override
    @Transactional
    public void eliminaralumno(int id) {
        dao.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Alumno buscaralumno(Integer id) {
        return dao.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Alumno> listaralumno() {
        return (List<Alumno>) dao.findAll();
    }
    
}
