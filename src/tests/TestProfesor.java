package tests;

import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import excepciones.TipoDePreguntaInvalidaException;
import modelo.LearningPath;
import modelo.Profesor;
import modelo.actividades.Actividad;
import modelo.actividades.Encuesta;
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
	private Actividad actParaClonar;
	
	
	
	@BeforeEach
	public void setUp() {
		
		prof = new Profesor("Amanda Fernández", "a.fernandez@gmail.com", "afer26", "profesor");
		path1 = new LearningPath ("Algoritmos en Excel", "algoritmos en excel", "Aprender algoritmos", 3, 4, "2023-10-20", "11", 1, prof.getLogin());
		
		actParaClonar = new Tarea("Tarea 1", "Practicar el conocimiento", 1, 20, false, 360, "Tarea", "Hacer los ejercicios 4-14", "");
		
		act1 = new QuizOpcionMultiple("Quiz Multiple 1","Quiz que evalua los conocimientos.", 2, 40, true, 50, "Prueba", 1, "Quiz Opcion Multiple");
		act2 = new QuizVerdaderoFalso("Quiz VoF 2", "Evaluar conocimientos", 3, 40, true, 50, "Prueba", 1, "Quiz Verdadero Falso");
				
		act3 = new Examen("Examen final", "Evalua todo el curso", 3, 120, true, 120, "Prueba", "Examen");
	}
	
	@AfterEach
	public void tearDown()
	{
		prof = null;
		path1 = null;
		actParaClonar= null;
		act1 = null;
		act2 = null;
		act3 = null;

		
	}
	
	
	@Test
	public void testCrearLearningPath() {
		LearningPath pathTest = prof.crearLearningPath("Excel para principiantes", "Curso para aprender Excel.", "Conocimientos básicos Excel.", 2);
		
		LocalDate fechaActual = LocalDate.now();
        String fecha = fechaActual.toString();
		
		assertEquals("Excel para principiantes", pathTest.getTitulo(), "El título no coincide.");
		assertEquals(fecha, pathTest.getFechaCreacion(), "No es la fecha de creación esperada");
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
	public void testAddActividadPorPos() {
		LearningPath pathTest = prof.crearLearningPath("Excel para principiantes", "Curso para aprender Excel.", "Conocimientos básicos Excel.", 2);
		prof.addActividadToLearningPath(pathTest, act2, 1);
		
		Map<Integer, Actividad> actsTest= pathTest.getActividades();
		assertEquals("Quiz VoF 2", (actsTest.get(1)).getTitulo(), "No tiene la posicion esperada");
		
		prof.addActividadToLearningPath(pathTest, act1, 0);
		assertEquals("Quiz Multiple 1", (actsTest.get(2)).getTitulo(), "No tiene la posicion esperada");

		prof.addActividadToLearningPath(pathTest, act3, 2);
		actsTest= pathTest.getActividades();
		assertEquals("Examen final", (actsTest.get(2)).getTitulo(), "No tiene la posicion esperada");
		assertEquals("Quiz Multiple 1", (actsTest.get(3)).getTitulo(), "No tiene la posicion esperada");

		//Test de que si la actividad ya estaba en el path, la elimina y cambia su posición por la nueva posición
		prof.addActividadToLearningPath(pathTest, act3, 3);
		actsTest= pathTest.getActividades();
		assertEquals("Examen final", (actsTest.get(3)).getTitulo(), "No tiene la posicion esperada");
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
		
		QuizOpcionMultiple quizParaCrear = prof.crearQuizMultiple("Quiz 3", "quiz del curso", 2, 120, true, 120, "Prueba", "Quiz Opcion Multiple", 1);
		
		
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
	    assertNotNull(recursoCreado, "El recurso no fue encontrado en la lista de actividades creadas.");

	    assertEquals( "Video 1", recursoCreado.getTitulo(), "El título del recurso no coincide.");
	    assertEquals( "video para el curso", recursoCreado.getObjetivo(), "El objetivo del recurso no coincide.");
	    assertEquals( 20, recursoCreado.getDuracionMin(), "La duración del recurso no coincide.");
	    assertEquals("https://www.youtube.com/watch?v=dQw4w9WgXcQ" , recCreado.getEnlace(), "El enlace del recurso no coincide.");

	    
	    assertTrue(actsCreadas.contains(recCreado), "El recurso no está en la lista de actividades creadas.");
	
		
	}

	
	@Test
	public void testCrearExamen() {
		
		Examen examParaCrear = prof.crearExamen("Examen parcial", "examen en la mitad del curso", 3, 60, true, 60, "Prueba", "Examen");
		
		
	    List<Actividad> actsCreadas = prof.getActCreadas();
	    
	    Actividad examenCreado = null;

	    for (Actividad act : actsCreadas) {
	        if (act.getTitulo().equals(examParaCrear.getTitulo())) {
	        	examenCreado = act;
	            break;
	        }
	    }

	    Examen examCreado = (Examen)examenCreado;
	    assertNotNull(examenCreado, "El examen no fue encontrado en la lista de actividades creadas.");

	    assertEquals( "Examen parcial", examCreado.getTitulo(), "El título del examen no coincide.");
	    assertEquals( "examen en la mitad del curso", examCreado.getObjetivo(), "El objetivo del examen no coincide.");
	    assertEquals( 60, examCreado.getDuracionMin(), "La duración del examen no coincide.");
	    assertEquals(3, examCreado.getNivelDificultad(), "El nivel de dificultad del examen no coincide.");

	    
	    assertTrue(actsCreadas.contains(examCreado), "El examen no está en la lista de actividades creadas.");
	
		
	}
	
	
	@Test
	public void testCrearEncuesta() {
		
		Encuesta encParaCrear = prof.crearEncuesta("Encuesta", "encuesta sobre el curso", 1, 15, false, 120, "Prueba", "Encuesta");
		
		
	    List<Actividad> actsCreadas = prof.getActCreadas();
	    
	    Actividad encuestaCreada = null;

	    for (Actividad act : actsCreadas) {
	        if (act.getTitulo().equals(encParaCrear.getTitulo())) {
	    	    encuestaCreada = act;
	            break;
	        }
	    }

	    Encuesta encCreada = (Encuesta)encuestaCreada;
	    assertNotNull(encuestaCreada, "La encuesta no fue encontrada en la lista de actividades creadas.");

	    assertEquals( "Encuesta", encCreada.getTitulo(), "El título de la encuesta no coincide.");
	    assertEquals( "encuesta sobre el curso", encCreada.getObjetivo(), "El objetivo de la encuesta no coincide.");
	    assertEquals( 15, encCreada.getDuracionMin(), "La duración de la encuesta no coincide.");
	    assertEquals(1, encCreada.getNivelDificultad(), "El nivel de dificultad de la encuesta no coincide.");

	    
	    assertTrue(actsCreadas.contains(encCreada), "La encuesta no está en la lista de actividades creadas.");
	
		
	}
	
	
	@Test
	public void testCrearTarea() {
		
		Tarea tareaParaCrear = prof.crearTarea("Tarea 2", "Tarea corta para practicar", 1, 30, false, 120, "Tarea", "Ejercicios 12-25", "");
		
		
	    List<Actividad> actsCreadas = prof.getActCreadas();
	    
	    Actividad tCreada = null;

	    for (Actividad act : actsCreadas) {
	        if (act.getTitulo().equals(tareaParaCrear.getTitulo())) {
	        	tCreada = act;
	            break;
	        }
	    }

	    Tarea tareaCreada = (Tarea)tCreada;
	    assertNotNull(tCreada, "La tarea no fue encontrada en la lista de actividades creadas.");

	    assertEquals( "Tarea 2", tareaCreada.getTitulo(), "El título de la encuesta no coincide.");
	    assertEquals( "Tarea corta para practicar", tareaCreada.getObjetivo(), "El objetivo de la tarea no coincide.");
	    assertEquals( 30, tareaCreada.getDuracionMin(), "La duración de la tarea no coincide.");
	    assertEquals(1, tareaCreada.getNivelDificultad(), "El nivel de dificultad de la tarea no coincide.");
	    
	    
	    assertTrue(actsCreadas.contains(tareaCreada), "La tarea no está en la lista de actividades creadas.");
	
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
