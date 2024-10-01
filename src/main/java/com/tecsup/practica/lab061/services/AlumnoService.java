package com.tecsup.practica.lab061.services;

import com.tecsup.practica.lab061.domain.entities.Alumno;
import java.util.List;

public interface AlumnoService {

    // Método para guardar un alumno (crear o actualizar)
    public void grabaralumno(Alumno alumno);

    // Método para eliminar un alumno por ID
    public void eliminaralumno(int id);

    // Método para buscar un alumno por ID
    public Alumno buscaralumno(Integer id);

    // Método para listar todos los alumnos
    public List<Alumno> listaralumno();
}
