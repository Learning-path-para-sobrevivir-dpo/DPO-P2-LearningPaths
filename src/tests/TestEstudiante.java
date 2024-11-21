package tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import excepciones.YaExisteActividadEnProgresoException;
import modelo.actividades.*;
import modelo.*;
import java.util.*;

public class TestEstudiante {
    
    private Estudiante estudiante;
    private RecursoEducativo recursoEducativo;
    private LearningPath learningPath;
    
    @BeforeEach
    void setUp() {
        // Crear un estudiante
        estudiante = new Estudiante("Juan", "juan@example.com", "123456", "Estudiante");

        // Crear un recurso educativo
        recursoEducativo = new RecursoEducativo(
            "Introducción a Java", 
            "Este recurso cubre los fundamentos de Java", 
            Actividad.FACIL, 
            60, 
            true, 
            90, 
            "Recurso Educativo", 
            "Tutorial", 
            "Contenido Java", 
            "www.java.com"
        );

        // Crear un LearningPath y agregar el recurso
        learningPath = new LearningPath("Java para Principiantes", "Aprende Java desde cero","Conocer todo",2,4,"11/09/24","11/09/24",1,"Jaime");
        learningPath.addActividadDeUltimas(recursoEducativo);

        // Inscribir al estudiante en el LearningPath
        estudiante.inscribirLearningPath(learningPath);
    }

    @Test
    void testInscribirLearningPath() {
        // Inscribir al estudiante en el LearningPath
    	learningPath = new LearningPath("Java para Pros", "Aprende Java desde cero","Conocer todo",2,4,"11/09/24","11/09/24",1,"Jaime");
        Progreso progresoCreado = estudiante.inscribirLearningPath(learningPath);

        // Verificar que el progreso del estudiante ha sido creado correctamente
        assertNotNull(progresoCreado);
        assertEquals(progresoCreado.getLearningPath(), learningPath.getTitulo());

        // Verificar que el LearningPath está en el progreso del estudiante
        Map<String, Progreso> progresos = estudiante.getProgresosLearningPaths();
        assertTrue(progresos.containsKey(learningPath.getTitulo()));
    }

    @Test
    void testCompletarActividad() {
        // Completar la actividad
        boolean completada = estudiante.completarActividadA(recursoEducativo, learningPath.getTitulo());
        assertTrue(completada);
        assertFalse(recursoEducativo.isCompletada());
    }

    
    @Test
    void testIniciarActividad() {
        // Iniciar la actividad

    	Progreso progreso = estudiante.getProgresosLearningPaths().get(learningPath.getTitulo());
    	try {
			progreso.empezarActividad(recursoEducativo);
		} catch (YaExisteActividadEnProgresoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	estudiante.iniciarActividad(0, recursoEducativo.getTitulo());
        assertEquals("Sin completar",recursoEducativo.getEstado());
    }
    



}
