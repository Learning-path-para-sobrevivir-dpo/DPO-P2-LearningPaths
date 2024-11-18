package tests;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeFalse;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import excepciones.TipoDePreguntaInvalidaException;
import modelo.LearningPath;
import modelo.Profesor;
import modelo.actividades.Actividad;
import modelo.actividades.Examen;
import modelo.actividades.PreguntaAbierta;
import modelo.actividades.PreguntaMultiple;
import modelo.actividades.Quiz;
import modelo.actividades.QuizOpcionMultiple;
import modelo.actividades.QuizVerdaderoFalso;
import modelo.actividades.RecursoEducativo;
import modelo.actividades.Tarea;

public class TestProfesor {

	private Profesor prof;
	private LearningPath path1;
	private QuizOpcionMultiple act1;
	private Actividad act2;
	private Examen act3;
	private Actividad act4;
	private Actividad actParaClonar;
	
	
	
	@BeforeEach
	public void setUp() {
		
		prof = new Profesor("Amanda Fernández", "a.fernandez@gmail.com", "afer26", "profesor");
		path1 = new LearningPath ("Algoritmos en Excel", "algoritmos en excel", "Aprender algoritmos", 3, 4, "2023-10-20", "11", 1, prof.getLogin());
		
		actParaClonar = new Tarea("Tarea 1", "Practicar el conocimiento", 1, 20, false, 360, "Tarea", "Hacer los ejercicios 4-14");
		
		act1 = new QuizOpcionMultiple("Quiz Multiple 1","Quiz que evalua los conocimientos.", 2, 40, true, 50, "Prueba", 1, "Quiz Opcion Multiple");
		act2 = new QuizVerdaderoFalso("Quiz VoF 2", "Evaluar conocimientos", 3, 40, true, 50, "Prueba", 1, "Quiz Verdadero Falso");
				
		act3 = new Examen("Examen final", "Evalua todo el curso", 3, 120, true, 120, "Prueba", "Examen");
	}
	
	@AfterEach
	public void tearDown()
	{
		prof = null;

	}
	
	
	@Test
	public void testCrearLearningPath() {
		LearningPath pathTest = prof.crearLearningPath("Excel para principiantes", "Curso para aprender Excel.", "Conocimientos básicos Excel.", 2);
		
		assertEquals("Excel para principiantes", pathTest.getTitulo(), "El título no coincide.");
		assertEquals("2024-11-17", pathTest.getFechaCreacion(), "No es la fecha de creación esperada");
	}
	
	@Test
	public void testAddActividadToLearningPath() {
		LearningPath pathTest = prof.crearLearningPath("Excel para principiantes", "Curso para aprender Excel.", "Conocimientos básicos Excel.", 2);
		prof.addActividadToLearningPath(pathTest, act2, 1);
		
		Map<Integer, Actividad> actsTest= pathTest.getActividades();
		assertEquals("Quiz VoF 2", (actsTest.get(1)).getTitulo(), "No tiene la posicion esperada");
		
		prof.addActividadToLearningPath(pathTest, act1, 0);
		assertEquals("Quiz Multiple 1", (actsTest.get(2)).getTitulo(), "No tiene la posicion esperada");

		
	}
	
	@Test
	public void testClonarAct() {
	    prof.clonarActividad(actParaClonar);

	    List<Actividad> actsCreadas = prof.getActCreadas();
	    Actividad actClonada = null;

	    for (Actividad act : actsCreadas) {
	        if (act.getTitulo().equals(actParaClonar.getTitulo())) {
	            actClonada = act;
	            break;
	        }
	    }

	    assertNotNull(actClonada, "La actividad clonada no fue encontrada en la lista de actividades creadas.");

	    assertEquals( actParaClonar.getTitulo(), actClonada.getTitulo(), "El título de la actividad clonada no coincide.");
	    assertEquals( actParaClonar.getObjetivo(), actClonada.getObjetivo(), "El objetivo de la actividad clonada no coincide.");
	    assertNotEquals( actParaClonar.getId(), actClonada.getId(), "El ID de la actividad clonada debe ser diferente al de la original.");
	    assertEquals( actParaClonar.getDuracionMin(), actClonada.getDuracionMin(), "La duración de la actividad clonada no coincide.");

	    assertTrue(actsCreadas.contains(actClonada), "La actividad clonada no está en la lista de actividades creadas.");
	}
	
	
	@Test
	public void testCrearQuiz() {
		
		Quiz quizParaCrear = prof.crearQuiz("Quiz 3", "quiz del curso", 2, 120, true, 120, "Prueba", "Quiz Opcion Multiple", 1);
		
		
	    List<Actividad> actsCreadas = prof.getActCreadas();

	    Actividad quizCreado = null;

	    for (Actividad act : actsCreadas) {
	        if (act.getTitulo().equals(quizParaCrear.getTitulo())) {
	            quizCreado = act;
	            break;
	        }
	    }
	    
	    assertNotNull(quizCreado, "El quiz creado no fue encontrada en la lista de actividades creadas.");

	    assertEquals( "Quiz 3", quizCreado.getTitulo(), "El título del quiz no coincide.");
	    assertEquals( "quiz del curso", quizCreado.getObjetivo(), "El objetivo del quiz a no coincide.");
	    assertEquals( 120, quizCreado.getTiempoCompletarSugerido(), "El tiempo sugerido no coincide.");
	    assertEquals( 120, quizCreado.getDuracionMin(), "La duración del quiz no coincide.");

	    assertTrue(actsCreadas.contains(quizParaCrear), "El quiz no está en la lista de actividades creadas.");
	
		
	}
	
	
	@Test
	public void testCrearRecEducativo() {
		
		RecursoEducativo recParaCrear = prof.crearRecursoEducativo("Video 1", "video para el curso", 1, 20, false, 360, "Recurso Educativo", "Video", "Video de instrucciones", "https://www.youtube.com/watch?v=dQw4w9WgXcQ");
		
		
	    List<Actividad> actsCreadas = prof.getActCreadas();
	    
	    Actividad recursoCreado = null;

	    for (Actividad act : actsCreadas) {
	        if (act.getTitulo().equals(recParaCrear.getTitulo())) {
	            recursoCreado = act;
	            break;
	        }
	    }

	    RecursoEducativo recCreado = (RecursoEducativo)recursoCreado;
	    assertNotNull(recursoCreado, "El recurso no fue encontrada en la lista de actividades creadas.");

	    assertEquals( "Video 1", recursoCreado.getTitulo(), "El título del recurso no coincide.");
	    assertEquals( "video para el curso", recursoCreado.getObjetivo(), "El objetivo del recurso no coincide.");
	    assertEquals( 20, recursoCreado.getDuracionMin(), "La duración del recurso no coincide.");
	    assertEquals("https://www.youtube.com/watch?v=dQw4w9WgXcQ" , recCreado.getEnlace(), "El enlace del recurso no coincide.");

	    
	    assertTrue(actsCreadas.contains(recCreado), "El recurso no está en la lista de actividades creadas.");
	
		
	}

	
	@Test
	public void testAñadirPreguntasQuiz() throws TipoDePreguntaInvalidaException {
		
		List<String> opciones = Arrays.asList("Hay que hacerlo a mano", "Sumando cada valor y diviendolo entre la cantidad de valores", "Usando =MEDIAN (en inglés)") ;
		PreguntaMultiple pregunta = prof.crearPreguntaMultiple("¿Cómo sacar promedios en Excel?", opciones, 2);
		
		prof.addPreguntaQuizMultiple(act1, pregunta);
	
		List<PreguntaMultiple> pregsQuiz = act1.getPreguntas();
		
		PreguntaMultiple preguntaEnQuiz = null;
		
		for (PreguntaMultiple preg: pregsQuiz) {
			if (preg.getEnunciado().equals("¿Cómo sacar promedios en Excel?")) {
				preguntaEnQuiz = preg;
				
			}
		}
		
	    assertNotNull(preguntaEnQuiz, "La pregunta no se encontró en las preguntas del quiz.");

		assertTrue(pregsQuiz.contains(pregunta), "El quiz no contiene la pregunta.");
		
		String[] opcionesArray = preguntaEnQuiz.getOpciones().toArray(new String[0]);
		assertEquals("Usando =MEDIAN (en inglés)", opcionesArray[2], "No es la opcion esperada");
		
		
	}
	
	@Test
	public void testAñadirPreguntasExamen() throws TipoDePreguntaInvalidaException {
		
		PreguntaAbierta pregunta = prof.crearPreguntaAbierta("Cree un modelo de Excel para resolver el problema del tablero.");
		
		prof.addPreguntaExamen(act3, pregunta);
	
		List<PreguntaAbierta> pregsExam = act3.getPreguntas();
		
		PreguntaAbierta preguntaEnExam = null;
		
		for (PreguntaAbierta preg: pregsExam) {
			if (preg.getEnunciado().equals("Cree un modelo de Excel para resolver el problema del tablero.")) {
				preguntaEnExam = preg;
				
			}
		}
		
		assertNotNull(preguntaEnExam, "La pregunta no estaba en el examen.");
		assertTrue(pregsExam.contains(pregunta), "El examen no contiene la pregunta.");
		

		
	}
	
}
