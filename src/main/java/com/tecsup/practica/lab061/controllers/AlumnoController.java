package com.tecsup.practica.lab061.controllers;

import com.tecsup.practica.lab061.domain.entities.Alumno;
import com.tecsup.practica.lab061.services.AlumnoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import java.util.Map;

@Controller
@SessionAttributes("alumno")
public class AlumnoController {

    @Autowired
    AlumnoService alumnoService;

    @RequestMapping(value = "/listarAlumnos", method = RequestMethod.GET)
    public String listarAlumnos(Model model) {
        model.addAttribute("alumnos", alumnoService.listaralumno());
        return "listaralumnos"; // Vista para listar alumnos
    }

    @RequestMapping(value = "/formAlumno")
    public String crearalumnos(Map<String, Object> model) {
        Alumno alumno = new Alumno();
        model.put("alumno", alumno);
        return "formalumno"; // Vista para crear/editar alumnos
    }

    @RequestMapping(value = "/formalumno", method = RequestMethod.POST)
    public String guardaralumnos(@Valid Alumno alumno, BindingResult result, Model model, SessionStatus status) {
        if (result.hasErrors()) {
            return "formalumno"; // Retornar a la vista de formulario si hay errores
        }

        alumnoService.grabaralumno(alumno);
        status.setComplete();
        return "redirect:listarAlumnos"; // Redireccionar al listado de alumnos
    }

    @RequestMapping(value = "/eliminarAlumno/{id}")
    public String eliminar(@PathVariable(value = "id") Integer id) {
        if (id > 0) {
            alumnoService.eliminaralumno(id);
        }
        return "redirect:/listarAlumnos"; // Redireccionar al listado de alumnos
    }

    @RequestMapping(value = "/form/alumno/{id}", method = RequestMethod.GET)
    public String editarAlumno(@PathVariable Integer id, Model model) {
        Alumno alumno = alumnoService.buscaralumno(id); // Busca el alumno por ID
        if (alumno != null) {
            model.addAttribute("alumno", alumno);
            return "formalumno"; // Redirige a la vista de formulario para editar
        } else {
            return "redirect:/error"; // Maneja el caso de que el alumno no se encuentre
        }
    }
    @RequestMapping(value = "/verAlumnos")
    public String verAlumnos(Model model) {
        model.addAttribute("alumnos", alumnoService.listaralumno());
        model.addAttribute("titulo", "Lista de Alumnos");

        return "alumno/ver"; // Cambia esto a la plantilla correspondiente
    }




}
