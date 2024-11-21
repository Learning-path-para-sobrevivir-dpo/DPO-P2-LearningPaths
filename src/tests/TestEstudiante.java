package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import excepciones.TipoDePreguntaInvalidaException;
import excepciones.YaExisteActividadEnProgresoException;
import modelo.actividades.*;
import modelo.*;
import consola.ImprimirConsola;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

class TestEstudiante {

	private Profesor profesor;
    private Estudiante estudiante;
    private LearningPath learningPath;
    private Progreso progreso;
    private QuizOpcionMultiple act1;
    private QuizVerdaderoFalso act2;
    private List<Boolean> res;

    @BeforeEach
    void setUp() {
    	res = new ArrayList<Boolean>();
    	res.add(false);
        act1 = new QuizOpcionMultiple("Quiz Multiple 1","Quiz que evalua los conocimientos.", 2, 40, true, 50, "Prueba", 1, "Quiz Opcion Multiple");
		act2 = new QuizVerdaderoFalso("Quiz VoF 2", "Evaluar conocimientos", 3, 40, true, 50, "Prueba", 1, "Quiz Verdadero Falso");
		try {
			act2.addPregunta(new PreguntaVerdaderoFalso("Es feliz?",false));
		} catch (TipoDePreguntaInvalidaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	profesor = new Profesor("Amelie Gautier", "a.gautier@gmail.com", "1234", "profesor");
        estudiante = new Estudiante("Mariana Marinez", "mmar@gmail.com", "1234", "estudiante");
        learningPath = new LearningPath("Origami para principiantes","Todo lo necesario para que aprendas a hacer tus propios origamis","Aprender a hacer un grulla y un barquito de origami.",1,4,"18/11/2024","18/11/2024",1,profesor.getLogin());
        Map<Integer, Actividad> acts = new HashMap<Integer, Actividad>();
    	
		acts.put(1, act1);
		acts.put(2, act2);
		
		learningPath.setActividades(acts);
    }

    @Test
    void testInscribirLearningPath() {
        Progreso progresoCreado = estudiante.inscribirLearningPath(learningPath);

        assertNotNull(progresoCreado);
        assertEquals(progresoCreado.getLearningPath(), learningPath.getTitulo());

        Map<String, Progreso> progresos = estudiante.getProgresosLearningPaths();
        assertTrue(progresos.containsKey(learningPath.getTitulo()));
    }

    @Test
    void testObtenerActividadDePath() {
    	Progreso progresoCreado = estudiante.inscribirLearningPath(learningPath);
    	ImprimirConsola.imprimirLearningPath(learningPath);
        String lp2 = progresoCreado.getLearningPath();

        Actividad actividadObtenida = estudiante.obtenerActividadDePath(lp2, 1);
        assertNotNull(actividadObtenida);
        assertEquals(act1, actividadObtenida);
    }

    @Test
    void testIniciarActividad() throws YaExisteActividadEnProgresoException {
    	estudiante.inscribirLearningPath(learningPath);
        progreso = estudiante.getProgresosLearningPaths().get(learningPath.getTitulo());
        boolean inicioExitoso = estudiante.iniciarActividad(0, learningPath.getTitulo());

        Progreso progreso2 = estudiante.getProgresosLearningPaths().get(learningPath.getTitulo());
        assertEquals(act1, progreso2.getActividadEnProgreso());
    }

    @Test
    void testCompletarActividad() throws Exception {
        estudiante.inscribirLearningPath(learningPath);
        learningPath.addActividadPorPos(act2,0);
        progreso = estudiante.getProgresosLearningPaths().get(learningPath.getTitulo());

       progreso.empezarActividad(act2);
       act2.responderQuiz(res);
        boolean completado = estudiante.completarActividad(0,learningPath.getTitulo());

        assertTrue(completado,"No se completo correctamente la actividad");
    }

    @Test
    void testInscribirLearningPathYaInscrito() {
        estudiante.inscribirLearningPath(learningPath);

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            estudiante.inscribirLearningPath(learningPath);
        });

        assertEquals("Ya está inscrito en " + learningPath.getTitulo(), exception.getMessage());
    }
}
