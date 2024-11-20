package tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import excepciones.RespuestasInconsistentesPruebaException;
import excepciones.TipoDePreguntaInvalidaException;
import modelo.actividades.Pregunta;
import modelo.actividades.PreguntaMultiple;
import modelo.actividades.PreguntaVerdaderoFalso;
import modelo.actividades.QuizVerdaderoFalso;

public class TestQuizVerdaderoFalso {
	private QuizVerdaderoFalso quizPrueba;
	private PreguntaVerdaderoFalso p1;
	private PreguntaVerdaderoFalso p2;
	private PreguntaVerdaderoFalso p3;
	private PreguntaVerdaderoFalso p4;
	
	@BeforeEach
	public void setUp()
	{
		quizPrueba = new QuizVerdaderoFalso("Un quiz", "Pasable", 1, 10, false, 30, "Prueba", 3, "Quiz");
		p1 = new PreguntaVerdaderoFalso("Es verdadero", true);
		p2 = new PreguntaVerdaderoFalso("Es falso", false);
		p3 = new PreguntaVerdaderoFalso("Es verdadero", true);
		p4 = new PreguntaVerdaderoFalso("Es falso", false);
	}
	
	@AfterEach
	public void tearDown()
	{
		quizPrueba = null;
		p1 = null;
		p2 = null;
		p3 = null;
		p4 = null;
	}
	
	@Test
	public void testAddPregunta() throws TipoDePreguntaInvalidaException
	{
		quizPrueba.addPregunta(p1);
		
		assertEquals(p1, quizPrueba.getPreguntas().get(0), "La pregunta no se añadio correctamente");
	}
	
	@Test
	public void testAddPreguntasEnOrden() throws TipoDePreguntaInvalidaException
	{
		quizPrueba.addPregunta(p1);
		quizPrueba.addPregunta(p2);
		quizPrueba.addPregunta(p3);
		quizPrueba.addPregunta(p4);
		
		assertEquals(p1, quizPrueba.getPreguntas().get(0), "El orden de las preguntas no es el esperado");
		assertEquals(p2, quizPrueba.getPreguntas().get(1), "El orden de las preguntas no es el esperado");
		assertEquals(p3, quizPrueba.getPreguntas().get(2), "El orden de las preguntas no es el esperado");
		assertEquals(p4, quizPrueba.getPreguntas().get(3), "El orden de las preguntas no es el esperado");
	}
	
	@Test
	public void testAddPreguntasEnUnOrdenEspecífico() throws TipoDePreguntaInvalidaException
	{
		
		// Se espera que la pregunta 2 se ponga como la pregunta 1
		p2.setNumero(1);
		quizPrueba.addPregunta(p1);
		quizPrueba.addPregunta(p3);
		quizPrueba.addPregunta(p4);
		quizPrueba.addPregunta(p2);
		
		assertEquals(p2, quizPrueba.obtenerPreguntaPorNumero(1), "No se agrego la pregunta en el lugar esperado");
	}
	
	@Test
	public void testTipoDePreguntaInvalidaException()
	{
		Pregunta pInvalida1 = new Pregunta("Pregunta invalida");
		PreguntaMultiple pInvalida2 = new PreguntaMultiple("Pregunta invalida");
		
		
		assertThrows(TipoDePreguntaInvalidaException.class, ()-> quizPrueba.addPregunta(pInvalida1), "No se lanzó la excepción esperada cuando se intento añadir un tipo invalido de pregunta");
		assertThrows(TipoDePreguntaInvalidaException.class, ()-> quizPrueba.addPregunta(pInvalida2), "No se lanzó la excepción esperada cuando se intento añadir un tipo invalido de pregunta");
	}
	
	@Test
	public void testEliminarPregunta() throws TipoDePreguntaInvalidaException
	{
		quizPrueba.addPregunta(p1);
		quizPrueba.addPregunta(p2);
		quizPrueba.addPregunta(p3);
		quizPrueba.addPregunta(p4);
		List<PreguntaVerdaderoFalso> preguntasEsperadas = new ArrayList<PreguntaVerdaderoFalso>(List.of(p1, p2, p3, p4));
		List<PreguntaVerdaderoFalso> preguntasObtenidas = quizPrueba.getPreguntas();
		
		assumeTrue(preguntasObtenidas.contains(p1));
		assumeTrue(preguntasObtenidas.contains(p2));
		assumeTrue(preguntasObtenidas.contains(p3));
		assumeTrue(preguntasObtenidas.contains(p4));
		
		//Se elimina la pregunta 3
		preguntasEsperadas.remove(2);
		quizPrueba.eliminarPregunta(3);
		preguntasObtenidas = quizPrueba.getPreguntas();
		assertFalse(preguntasObtenidas.contains(p3), "No se elimino la pregunta como se esperaba");
		assertEquals(preguntasEsperadas.size(), preguntasObtenidas.size(), "No se elimino la pregunta como se esperaba");
		
		//se verifica que se haya actualizado el número de la pregunta 4
		assertEquals(3, p4.getNumero(), "No se reasignaron los números de las preguntas correctamente");
	}
		
	
	@Test
	public void testEliminarPreguntaCasosBorde() throws TipoDePreguntaInvalidaException
	{
		quizPrueba.addPregunta(p1);
		quizPrueba.addPregunta(p2);
		quizPrueba.addPregunta(p3);
		quizPrueba.addPregunta(p4);
		List<PreguntaVerdaderoFalso> preguntasEsperadas = List.of(p1, p2, p3, p4);
		List<PreguntaVerdaderoFalso> preguntasObtenidas = quizPrueba.getPreguntas();
		
		assumeTrue(preguntasObtenidas.contains(p1));
		assumeTrue(preguntasObtenidas.contains(p2));
		assumeTrue(preguntasObtenidas.contains(p3));
		assumeTrue(preguntasObtenidas.contains(p4));
		
		//Casos de borde
		quizPrueba.eliminarPregunta(0);
		assertEquals(preguntasEsperadas, preguntasObtenidas, "Se elimino un elemento de la lista de preguntas cuando no se debia");
		quizPrueba.eliminarPregunta(5);
		assertEquals(preguntasEsperadas, preguntasObtenidas, "Se elimino un elemento de la lista de preguntas cuando no se debia");
	}
	
	@Test
	public void testObtenerPreguntaPorNumero() throws TipoDePreguntaInvalidaException
	{
		quizPrueba.addPregunta(p4); //pregunta #1
		quizPrueba.addPregunta(p3); //pregunta #2
		quizPrueba.addPregunta(p2); //pregunta #3
		quizPrueba.addPregunta(p1); //pregunta #4
		
		assertEquals(p4, quizPrueba.obtenerPreguntaPorNumero(1), "No se obtuvo la pregunta esperada");
		assertEquals(p3, quizPrueba.obtenerPreguntaPorNumero(2), "No se obtuvo la pregunta esperada");
		assertEquals(p2, quizPrueba.obtenerPreguntaPorNumero(3), "No se obtuvo la pregunta esperada");
		assertEquals(p1, quizPrueba.obtenerPreguntaPorNumero(4), "No se obtuvo la pregunta esperada");
	}
	
	@Test
	public void testObtenerPreguntaPorNumeroInvalido() throws TipoDePreguntaInvalidaException
	{
		quizPrueba.addPregunta(p4); //pregunta #1
		quizPrueba.addPregunta(p3); //pregunta #2
		quizPrueba.addPregunta(p2); //pregunta #3
		quizPrueba.addPregunta(p1); //pregunta #4
		
		assertEquals(null, quizPrueba.obtenerPreguntaPorNumero(5), "La funcion no devolvio null");
		assertEquals(null, quizPrueba.obtenerPreguntaPorNumero(0), "La funcion no devolvio null");
	}
	
	@Test
	public void testSetPreguntas()
	{
		List<PreguntaVerdaderoFalso> preguntasEsperadas = new ArrayList<PreguntaVerdaderoFalso>(List.of(p1, p2, p3, p4));
		quizPrueba.setPreguntas(preguntasEsperadas);
		
		assertEquals(p1, quizPrueba.obtenerPreguntaPorNumero(1), "No se cargaron las preguntas en el orden esperado");
		assertEquals(p2, quizPrueba.obtenerPreguntaPorNumero(2), "No se cargaron las preguntas en el orden esperado");
		assertEquals(p3, quizPrueba.obtenerPreguntaPorNumero(3), "No se cargaron las preguntas en el orden esperado");
		assertEquals(p4, quizPrueba.obtenerPreguntaPorNumero(4), "No se cargaron las preguntas en el orden esperado");
	}
	
	@Test
	public void testResponderQuiz() throws RespuestasInconsistentesPruebaException
	{
		List<PreguntaVerdaderoFalso> preguntasEsperadas = new ArrayList<PreguntaVerdaderoFalso>(List.of(p1, p2, p3, p4));
		quizPrueba.setPreguntas(preguntasEsperadas);
		
		List<Boolean> respuestas = new ArrayList<Boolean>(List.of(true, false, true, false));
		quizPrueba.responderQuiz(respuestas);
		
		//Se verifica que las preguntas del quiz hayan sido clonadas correctamente cuando se respondieron
		List<PreguntaVerdaderoFalso> preguntasObtenidas = quizPrueba.getPreguntas();
		
		assertEquals(preguntasEsperadas.size(), preguntasObtenidas.size(), "No se clonaron las preguntas correctamente");
		
		Iterator<PreguntaVerdaderoFalso> obtenidas = preguntasObtenidas.iterator();
		Iterator<PreguntaVerdaderoFalso> esperadas = preguntasEsperadas.iterator();
		
		//Se cambian las instancias originales con las respuestas esperadas para
		//comparar que las instancias clonadas sean iguales a las originales
		p1.setOpcionSeleccionada(true);
		p2.setOpcionSeleccionada(false);
		p3.setOpcionSeleccionada(true);
		p4.setOpcionSeleccionada(false);
		
		PreguntaVerdaderoFalso pEsperada;
		PreguntaVerdaderoFalso pObtenida;
		while (esperadas.hasNext() && obtenidas.hasNext())
		{
			
			pEsperada = esperadas.next();
			pObtenida = obtenidas.next();
			//Se verifica que sean instancias diferentes
			assertNotEquals(pEsperada, pObtenida, "Cuando se respondio el quiz no se clonaron las preguntas correctamente");
			
			// Se compara la info de las instancias originales
			assertEquals(pEsperada.isOpcionSeleccionada(), pObtenida.isOpcionSeleccionada());
			assertEquals(pEsperada.isRespuestaCorrecta(), pObtenida.isRespuestaCorrecta());
			assertEquals(pEsperada.getEnunciado(), pObtenida.getEnunciado());
			assertEquals(pEsperada.getNumero(), pObtenida.getNumero());
			assertEquals(pEsperada.getTipo(), pObtenida.getTipo());
			//Nota: se hizo así porque el assertTrue(pEsperada.equals(pObtenida)) falla a 
			//pesar que las pruebas de assertEquals de todos sus atributos pasan
		}
	}
	
	@Test
	public void testRespuestasInconsistentesPruebaException()
	{
		List<PreguntaVerdaderoFalso> preguntasEsperadas = new ArrayList<PreguntaVerdaderoFalso>(List.of(p1, p2, p3, p4));
		quizPrueba.setPreguntas(preguntasEsperadas);
		
		//Menos respuestas de las esperadas
		List<Boolean> respuestas = new ArrayList<Boolean>(List.of(true, false, true));
		assertThrows(RespuestasInconsistentesPruebaException.class, ()->quizPrueba.responderQuiz(respuestas), "No se lanzo la excepcion esperada");
		
		//Más respuestas de las esperadas
		List<Boolean> respuestas2 = new ArrayList<Boolean>(List.of(true, false, true, true, false));
		assertThrows(RespuestasInconsistentesPruebaException.class, ()->quizPrueba.responderQuiz(respuestas2), "No se lanzo la excepcion esperada");
	}
	
	@Test
	public void testCalcularCalificacionRespuestasIncorrectas() throws RespuestasInconsistentesPruebaException
	{
		List<PreguntaVerdaderoFalso> preguntasEsperadas = new ArrayList<PreguntaVerdaderoFalso>(List.of(p1, p2, p3, p4));
		quizPrueba.setPreguntas(preguntasEsperadas);
		
		List<Boolean> respuestas = new ArrayList<Boolean>(List.of(false, true, false, true));
		quizPrueba.responderQuiz(respuestas);
		
		assertEquals(0, quizPrueba.getCalificacion(), "No se califico correctamente el Quiz");
		
		assertEquals("No Exitosa", quizPrueba.getEstado(), "No se actualizo el estado correctamente luego de calificarlo");
	}
	
	@Test
	public void testCalcularCalificacionRespuestasCorrectas() throws RespuestasInconsistentesPruebaException
	{
		List<PreguntaVerdaderoFalso> preguntasEsperadas = new ArrayList<PreguntaVerdaderoFalso>(List.of(p1, p2, p3, p4));
		quizPrueba.setPreguntas(preguntasEsperadas);
		
		List<Boolean> respuestas = new ArrayList<Boolean>(List.of(true, false, true, false));
		quizPrueba.responderQuiz(respuestas);
		
		assertEquals(5, quizPrueba.getCalificacion(), "No se califico correctamente el Quiz");
		
		assertEquals("Exitosa", quizPrueba.getEstado(), "No se actualizo el estado correctamente luego de calificarlo");
	}
	
	@Test
	public void testCalcularCalificacionPasarQuiz() throws RespuestasInconsistentesPruebaException
	{
		PreguntaVerdaderoFalso p5 = new PreguntaVerdaderoFalso("Pregunta extra", true);
		List<PreguntaVerdaderoFalso> preguntasEsperadas = new ArrayList<PreguntaVerdaderoFalso>(List.of(p1, p2, p3, p4, p5));
		quizPrueba.setPreguntas(preguntasEsperadas);
		
		//Tres respuestas correctas, pasa el quiz
		List<Boolean> respuestas = new ArrayList<Boolean>(List.of(true, false, true, true, false));
		quizPrueba.responderQuiz(respuestas);
		
		assertTrue(quizPrueba.getCalificacion() >= quizPrueba.calificacionMinima,"No se califico correctamente el Quiz");
		assertEquals(3, quizPrueba.getCalificacion(), "No se califico correctamente el Quiz");
		assertEquals("Exitosa", quizPrueba.getEstado(), "No se actualizo el estado correctamente luego de calificarlo");
	}
	
	@Test
	public void testCalcularCalificacionPerderQuiz() throws RespuestasInconsistentesPruebaException
	{
		PreguntaVerdaderoFalso p5 = new PreguntaVerdaderoFalso("Pregunta extra", true);
		List<PreguntaVerdaderoFalso> preguntasEsperadas = new ArrayList<PreguntaVerdaderoFalso>(List.of(p1, p2, p3, p4, p5));
		quizPrueba.setPreguntas(preguntasEsperadas);
		
		//Tres respuestas incorrectas, pierde el quiz (calificación minima de 3)
		List<Boolean> respuestas = new ArrayList<Boolean>(List.of(true, false, false, true, false));
		quizPrueba.responderQuiz(respuestas);
		
		assertTrue(quizPrueba.getCalificacion() < quizPrueba.calificacionMinima,"No se califico correctamente el Quiz");
		assertEquals(2, quizPrueba.getCalificacion(), "No se califico correctamente el Quiz");
		assertEquals("No Exitosa", quizPrueba.getEstado(), "No se actualizo el estado correctamente luego de calificarlo");
	}
}
