package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import excepciones.YaExisteActividadEnProgresoException;
import modelo.actividades.*;
import modelo.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class TestEstudiante {

	private Profesor profesor;
    private Estudiante estudiante;
    private LearningPath learningPath;
    private Progreso progreso;
    private QuizOpcionMultiple quizOM;
    private Tarea tarea;

    @BeforeEach
    void setUp() {
    	List<String> opciones1 = new ArrayList<>();
        opciones1.add("Arte de doblar papel");
        opciones1.add("Técnica de escritura japonesa");
        opciones1.add("Danza tradicional japonesa");

        List<String> opciones2 = new ArrayList<>();
        opciones2.add("Japón");
        opciones2.add("China");
        opciones2.add("Corea del Sur");

        List<String> opciones3 = new ArrayList<>();
        opciones3.add("Grulla de papel");
        opciones3.add("Flor de loto");
        opciones3.add("Barco de papel");

       PreguntaMultiple pregunta1 = new PreguntaMultiple(
                "¿Qué significa la palabra origami?",
                opciones1,
                1 // Primera opción es correcta
        );

        PreguntaMultiple pregunta2 = new PreguntaMultiple(
                "¿En qué país se originó el origami?",
                opciones2,
                1 // Primera opción es correcta
        );

        PreguntaMultiple pregunta3 = new PreguntaMultiple(
                "¿Cuál es la figura más popular en el origami?",
                opciones3,
                1 // Primera opción es correcta
        );

        // Crear la lista de preguntas
        List<PreguntaMultiple> preguntas = new ArrayList<>();
        preguntas.add(pregunta1);
        preguntas.add(pregunta2);
        preguntas.add(pregunta3);

        quizOM = new QuizOpcionMultiple(
                "Quiz sobre Origami","Evaluar conocimientos básicos sobre el arte del origami",1, 30,true, 20, "Prueba", 70,preguntas, "Opción Múltiple" );
    	profesor = new Profesor("Amelie Gautier", "a.gautier@gmail.com", "1234", "profesor");
        estudiante = new Estudiante("Mariana Marinez", "mmar@gmail.com", "1234", "estudiante");
        learningPath = new LearningPath("Origami para principiantes","Todo lo necesario para que aprendas a hacer tus propios origamis","Aprender a hacer un grulla y un barquito de origami.",1,4,"18/11/2024","18/11/2024",1,profesor.getLogin());
        tarea = new Tarea("Hacer una grulla","Seguir un tutorial de como hacer una grulla",2,30,true, 20,"Tarea","link al video del youtube, debe enviar una foto al correo electronico");
    }

    @Test
    void testInscribirLearningPath() {
        Progreso progresoCreado = estudiante.inscribirLearningPath(learningPath);

        assertNotNull(progresoCreado);
        assertEquals(progresoCreado.getLearningPath(), learningPath);

        Map<String, Progreso> progresos = estudiante.getProgresosLearningPaths();
        assertTrue(progresos.containsKey(learningPath.getTitulo()));
    }

    @Test
    void testObtenerActividadDePath() {
        estudiante.inscribirLearningPath(learningPath);
        learningPath.addActividadDeUltimas(quizOM);

        Actividad actividadObtenida = estudiante.obtenerActividadDePath(learningPath.getTitulo(), 1);
        assertNotNull(actividadObtenida);
        assertEquals(quizOM, actividadObtenida);
    }

    @Test
    void testIniciarActividad() throws YaExisteActividadEnProgresoException {
        estudiante.inscribirLearningPath(learningPath);
        learningPath.addActividadDeUltimas(quizOM);
        Progreso progreso = estudiante.getProgresosLearningPaths().get(learningPath.getTitulo());

        boolean inicioExitoso = estudiante.iniciarActividad(progreso.getNumero(quizOM,false), learningPath.getTitulo());
        assertTrue(inicioExitoso);

        Progreso progreso2 = estudiante.getProgresosLearningPaths().get(learningPath.getTitulo());
        assertEquals(quizOM, progreso2.getActividadEnProgreso());
    }

    @Test
    void testCompletarActividad() throws Exception {
        estudiante.inscribirLearningPath(learningPath);
        learningPath.addActividadDeUltimas(tarea);
        Progreso progreso = estudiante.getProgresosLearningPaths().get(learningPath.getTitulo());

        estudiante.iniciarActividad(progreso.getNumero(tarea, false), learningPath.getTitulo());

        Tarea tarea2 = (Tarea) tarea;
        tarea2.setMedioEntrega("URL de entrega");
        tarea2.setEnviado(true);
        tarea2.completarActividad();

        boolean completada = estudiante.completarActividad(progreso.getNumero(tarea2,true), learningPath.getTitulo());
        assertTrue(completada);
    }

    @Test
    void testInscribirLearningPathYaInscrito() {
        estudiante.inscribirLearningPath(learningPath);

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            estudiante.inscribirLearningPath(learningPath);
        });

        assertEquals("Ya está inscrito en" + learningPath.getTitulo(), exception.getMessage());
    }
}
