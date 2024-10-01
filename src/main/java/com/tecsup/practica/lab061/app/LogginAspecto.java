package com.tecsup.practica.lab061.app;

import com.tecsup.practica.lab061.domain.entities.Auditoria;
import com.tecsup.practica.lab061.domain.entities.Curso;
import com.tecsup.practica.lab061.domain.entities.Alumno; // Importación añadida
import com.tecsup.practica.lab061.domain.persistence.AuditoriaDao;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Arrays;
import java.util.Calendar;

@Component
@Aspect
public class LogginAspecto {

    private Long tx;

    @Autowired
    private AuditoriaDao auditoriaDao;

    @Around("execution(* com.tecsup.practica.lab061.domain.services*(..))")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = null;
        Long currTime = System.currentTimeMillis();
        tx = System.currentTimeMillis();
        Logger logger = LoggerFactory.getLogger(joinPoint.getTarget().getClass());
        String metodo = "tx[" + tx + "] - " + joinPoint.getSignature().getName();

        if (joinPoint.getArgs().length > 0) {
            logger.info(metodo + "() INPUT:" + Arrays.toString(joinPoint.getArgs()));
        }

        try {
            result = joinPoint.proceed();
        } catch (Throwable e) {
            logger.error(e.getMessage());
        }

        logger.info(metodo + "(): tiempo transcurrido " + (System.currentTimeMillis() - currTime) + " ms.");
        return result;
    }

    @After("execution(* com.tecsup.practica.lab061.controllers.*Controller.guardar*(..)) ||" +
            "execution(* com.tecsup.practica.lab061.controllers.*Controller.editar*(..)) ||" +
            "execution(* com.tecsup.practica.lab061.controllers.*Controller.eliminar*(..))")
    public void auditoria(JoinPoint joinPoint) throws Throwable {
        Logger logger = LoggerFactory.getLogger(joinPoint.getTarget().getClass());
        String metodo = joinPoint.getSignature().getName();
        Integer id = null;

        // Obtener los parámetros del método
        Object[] parametros = joinPoint.getArgs();

        // Verificar el método llamado
        if (metodo.startsWith("guardar")) {
            // Verifica el tipo de objeto en lugar de asumir que es siempre un Curso
            if (parametros[0] instanceof Alumno) {
                Alumno alumno = (Alumno) parametros[0];
                id = alumno.getId(); // Asegúrate de que Alumno tiene un método getId()
            } else if (parametros[0] instanceof Curso) {
                Curso curso = (Curso) parametros[0];
                id = curso.getId();
            }
        } else if (metodo.startsWith("editar")) {
            id = (Integer) parametros[0];
        } else if (metodo.startsWith("eliminar")) {
            id = (Integer) parametros[0];
        }

        String traza = "tx[" + tx + "] - " + metodo;
        logger.info(traza + "(): registrando auditoria...");

        auditoriaDao.save(new Auditoria("cursos", id, Calendar.getInstance().getTime(),
                "usuario", metodo));
    }
}
