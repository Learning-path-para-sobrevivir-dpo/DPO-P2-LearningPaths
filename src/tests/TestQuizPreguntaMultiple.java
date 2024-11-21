package tests;

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
import modelo.actividades.PreguntaMultiple;
import modelo.actividades.PreguntaVerdaderoFalso;
import modelo.actividades.QuizOpcionMultiple;

public class TestQuizPreguntaMultiple {
	private PreguntaMultiple p1;
	private PreguntaMultiple p2;
	private PreguntaMultiple p3;
	private PreguntaMultiple p4;
	private PreguntaMultiple p5;
	private List<PreguntaMultiple> preguntas;
	
	private QuizOpcionMultiple quizPrueba;
	
	@BeforeEach
	public void setUp()
	{
		List<String> opciones = new ArrayList<String>(List.of("op1", "op2", "op3", "op4", "op5"));
		p1 = new PreguntaMultiple("Una pregunta 1 multiple");
		p2 = new PreguntaMultiple("Una pregunta 2 multiple");
		p3 = new PreguntaMultiple("Una pregunta 3 multiple");
		p4 = new PreguntaMultiple("Una pregunta 4 multiple");
		p5 = new PreguntaMultiple("Una pregunta 5 multiple");
		
		int i = 1;
		for (String opcion: opciones)
		{
			p1.addOpcion(i, opcion, false);
			p2.addOpcion(i, opcion, false);
			p3.addOpcion(i, opcion, false);
			p4.addOpcion(i, opcion, false);
			p5.addOpcion(i, opcion, false);
			i++;
		}
		
		p1.setOpcionCorrecta(1);
		p2.setOpcionCorrecta(2);
		p3.setOpcionCorrecta(3);
		p4.setOpcionCorrecta(4);
		p5.setOpcionCorrecta(5);
		
		preguntas = new ArrayList<PreguntaMultiple>(List.of(p1, p2, p3, p4, p5));
		
		quizPrueba = new QuizOpcionMultiple("Un quiz extremadamente difícil", "Ni Ivan pasa el quiz", 3, 100, true, 100, "Prueba", 3, "Quiz Opcion Multiple");
	}
	
	@AfterEach
	public void tearDown()
	{
		p1 = null;
		p2 = null;
		p3 = null;
		p4 = null;
		p5 = null;
		quizPrueba = null;
	}
	
	@Test
	public void testSetPreguntas()
	{
		quizPrueba.setPreguntas(preguntas);
		List<PreguntaMultiple> preguntasObtenidas = quizPrueba.getPreguntas();
		
		assertEquals(preguntas, preguntasObtenidas, "No se añadieron las preguntas correctamente");
		
		//Si se añaden correctamente, cada pregunta tendra ahora un número asignado
		assertEquals(1, p1.getNumero(), "No se les asigno correctamente un número a las preguntas en el orden que se añadieron");
		assertEquals(2, p2.getNumero(), "No se les asigno correctamente un número a las preguntas en el orden que se añadieron");
		assertEquals(3, p3.getNumero(), "No se les asigno correctamente un número a las preguntas en el orden que se añadieron");
		assertEquals(4, p4.getNumero(), "No se les asigno correctamente un número a las preguntas en el orden que se añadieron");
		assertEquals(5, p5.getNumero(), "No se les asigno correctamente un número a las preguntas en el orden que se añadieron");
	}
	
	@Test
	public void testGetPreguntaPorNumero()
	{
		quizPrueba.setPreguntas(preguntas);
		
		assertEquals(p3, quizPrueba.getPreguntaPorNumero(3), "No se obtuvo la pregunta esperada");
	}
	
	@Test
	public void testGetPreguntaPorNumeroInvalido()
	{
		quizPrueba.setPreguntas(preguntas);
		
		assertEquals(null, quizPrueba.getPreguntaPorNumero(0), "No se retorno null cuando se esperaba");
		assertEquals(null, quizPrueba.getPreguntaPorNumero(6), "No se retorno null cuando se esperaba");
	}
	
	@Test
	public void testAddPregunta() throws TipoDePreguntaInvalidaException
	{
		assumeTrue(0 == p5.getNumero());
		assumeTrue(0 == p2.getNumero());
		assumeTrue(0 == p3.getNumero());
		
		quizPrueba.addPregunta(p5);
		quizPrueba.addPregunta(p2);
		quizPrueba.addPregunta(p3);
		
		assertEquals(p5, quizPrueba.getPreguntaPorNumero(1), "No se añadio la pregunta correctamente");
		assertEquals(p2, quizPrueba.getPreguntaPorNumero(2), "No se añadio la pregunta correctamente");
		assertEquals(p3, quizPrueba.getPreguntaPorNumero(3), "No se añadio la pregunta correctamente");
		
		assertEquals(1, p5.getNumero(), "No se le asigno el número a la pregunta correctamente");
		assertEquals(2, p2.getNumero(), "No se le asigno el número a la pregunta correctamente");
		assertEquals(3, p3.getNumero(), "No se le asigno el número a la pregunta correctamente");
	}
	
	@Test
	public void testAddPreguntaAlFinal() throws TipoDePreguntaInvalidaException
	{
		quizPrueba.setPreguntas(preguntas);
		PreguntaMultiple p6 = new PreguntaMultiple("Una pregunta 6 multiple");
		quizPrueba.addPregunta(p6);
		
		assertEquals(p6, quizPrueba.getPreguntaPorNumero(6), "No se añadio la pregunta correctamente");
	}
	
	@Test
	public void testAddPreguntaAlInicio() throws TipoDePreguntaInvalidaException
	{
		quizPrueba.setPreguntas(preguntas);
		PreguntaMultiple p6 = new PreguntaMultiple("Una pregunta 6 multiple");
		p6.setNumero(1);
		quizPrueba.addPregunta(p6);
		
		assertEquals(p6, quizPrueba.getPreguntaPorNumero(1), "No se añadio la pregunta correctamente");
		
		assertEquals(2, p1.getNumero(), "No se reasignaron los números de las preguntas correctamente");
		assertEquals(3, p2.getNumero(), "No se reasignaron los números de las preguntas correctamente");
		assertEquals(4, p3.getNumero(), "No se reasignaron los números de las preguntas correctamente");
		assertEquals(5, p4.getNumero(), "No se reasignaron los números de las preguntas correctamente");
		assertEquals(6, p5.getNumero(), "No se reasignaron los números de las preguntas correctamente");
	}
	
	@Test
	public void testAddPreguntaEnLaMitad() throws TipoDePreguntaInvalidaException
	{
		quizPrueba.setPreguntas(preguntas);
		PreguntaMultiple p6 = new PreguntaMultiple("Una pregunta 6 multiple");
		p6.setNumero(3);
		quizPrueba.addPregunta(p6);
		
		assertEquals(p6, quizPrueba.getPreguntaPorNumero(3), "No se añadio la pregunta correctamente");
		
		assertEquals(4, p3.getNumero(), "No se reasignaron los números de las preguntas correctamente");
		assertEquals(5, p4.getNumero(), "No se reasignaron los números de las preguntas correctamente");
		assertEquals(6, p5.getNumero(), "No se reasignaron los números de las preguntas correctamente");
	}
	
	@Test
	public void testTipoDePreguntaInvalidaException()
	{
		PreguntaVerdaderoFalso pInvalida = new PreguntaVerdaderoFalso("Pregunta Invalida");
		
		assertThrows(TipoDePreguntaInvalidaException.class, ()->quizPrueba.addPregunta(pInvalida));
	}
	
	@Test
	public void testEliminarPreguntaFinal()
	{
		quizPrueba.setPreguntas(preguntas);
		quizPrueba.eliminarPregunta(preguntas.size());
		
		assertFalse(quizPrueba.getPreguntas().contains(p5), "No se elimino la pregunta como era de esperarse");
	}
	
	@Test
	public void testEliminarPreguntaInicio()
	{
		quizPrueba.setPreguntas(preguntas);
		quizPrueba.eliminarPregunta(1);
		
		assertFalse(quizPrueba.getPreguntas().contains(p1), "No se elimino la pregunta como era de esperarse");
		
		assertEquals(1, p2.getNumero(), "No se reasignaron los números de las preguntas correctamente");
		assertEquals(2, p3.getNumero(), "No se reasignaron los números de las preguntas correctamente");
		assertEquals(3, p4.getNumero(), "No se reasignaron los números de las preguntas correctamente");
		assertEquals(4, p5.getNumero(), "No se reasignaron los números de las preguntas correctamente");
	}
	
	@Test
	public void testEliminarPreguntaMitad()
	{
		quizPrueba.setPreguntas(preguntas);
		quizPrueba.eliminarPregunta(3);
		
		assertFalse(quizPrueba.getPreguntas().contains(p3), "No se elimino la pregunta como era de esperarse");
		
		assertEquals(3, p4.getNumero(), "No se reasignaron los números de las preguntas correctamente");
		assertEquals(4, p5.getNumero(), "No se reasignaron los números de las preguntas correctamente");
	}
	
	@Test
	public void testResponderQuiz() throws RespuestasInconsistentesPruebaException
	{
		quizPrueba.setPreguntas(preguntas);
		List<Integer> respuestas = new ArrayList<Integer>(List.of(1,2,3,4,5));
		
		quizPrueba.responderQuiz(respuestas);
		//Se verifica que las preguntas del quiz hayan sido clonadas correctamente cuando se respondieron
		List<PreguntaMultiple> preguntasObtenidas = quizPrueba.getPreguntas();

		assertEquals(preguntas.size(), preguntasObtenidas.size(), "No se clonaron las preguntas correctamente");

		Iterator<PreguntaMultiple> obtenidas = preguntasObtenidas.iterator();
		Iterator<PreguntaMultiple> esperadas = preguntas.iterator();

		//Se cambian las instancias originales con las respuestas esperadas para
		//comparar que las instancias clonadas sean iguales a las originales
		p1.setOpcionSeleccionada(1);
		p2.setOpcionSeleccionada(2);
		p3.setOpcionSeleccionada(3);
		p4.setOpcionSeleccionada(4);
		p5.setOpcionSeleccionada(5);

		PreguntaMultiple pEsperada;
		PreguntaMultiple pObtenida;
		while (esperadas.hasNext() && obtenidas.hasNext())
		{

			pEsperada = esperadas.next();
			pObtenida = obtenidas.next();
			//Se verifica que sean instancias diferentes
			assertNotEquals(pEsperada, pObtenida, "Cuando se respondio el quiz no se clonaron las preguntas correctamente");

			// Se compara la info de las instancias originales
			assertEquals(pEsperada.getOpcionSeleccionada(), pObtenida.getOpcionSeleccionada());
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
		quizPrueba.setPreguntas(preguntas);
		
		//Menos respuestas de las esperadas
		List<Integer> respuestas = new ArrayList<Integer>(List.of(1,2,3,4));
		assertThrows(RespuestasInconsistentesPruebaException.class, ()->quizPrueba.responderQuiz(respuestas), "No se lanzo la excepcion esperada");
		
		//Más respuestas de las esperadas
		List<Integer> respuestas2 = new ArrayList<Integer>(List.of(1,2,3,4,5,6));
		assertThrows(RespuestasInconsistentesPruebaException.class, ()->quizPrueba.responderQuiz(respuestas2), "No se lanzo la excepcion esperada");
	}
	
	@Test
	public void testCalcularCalificacionRespuestasIncorrectas() throws RespuestasInconsistentesPruebaException
	{
		quizPrueba.setPreguntas(preguntas);
		
		List<Integer> respuestas = new ArrayList<Integer>(List.of(2,3,4,5,1));
		quizPrueba.responderQuiz(respuestas);
		
		assertEquals(0, quizPrueba.getCalificacion(), "No se califico correctamente el Quiz");
		
		assertEquals("No Exitosa", quizPrueba.getEstado(), "No se actualizo el estado correctamente luego de calificarlo");
	}
	
	@Test
	public void testCalcularCalificacionRespuestasCorrectas() throws RespuestasInconsistentesPruebaException
	{
		quizPrueba.setPreguntas(preguntas);
		
		List<Integer> respuestas = new ArrayList<Integer>(List.of(1,2,3,4,5));
		quizPrueba.responderQuiz(respuestas);
		
		assertEquals(5, quizPrueba.getCalificacion(), "No se califico correctamente el Quiz");
		
		assertEquals("Exitosa", quizPrueba.getEstado(), "No se actualizo el estado correctamente luego de calificarlo");
	}
	
	@Test
	public void testCalcularCalificacionPasarQuiz() throws RespuestasInconsistentesPruebaException
	{
		quizPrueba.setPreguntas(preguntas);
		
		//Tres respuestas correctas, pasa el quiz
		List<Integer> respuestas = new ArrayList<Integer>(List.of(1,2,1,4,1));
		quizPrueba.responderQuiz(respuestas);
		
		assertTrue(quizPrueba.getCalificacion() >= quizPrueba.calificacionMinima,"No se califico correctamente el Quiz");
		assertEquals(3, quizPrueba.getCalificacion(), "No se califico correctamente el Quiz");
		assertEquals("Exitosa", quizPrueba.getEstado(), "No se actualizo el estado correctamente luego de calificarlo");
	}
	
	@Test
	public void testCalcularCalificacionPerderQuiz() throws RespuestasInconsistentesPruebaException
	{
		quizPrueba.setPreguntas(preguntas);
		
		//Tres respuestas incorrectas, pierde el quiz (calificación minima de 3)
		List<Integer> respuestas = new ArrayList<Integer>(List.of(1,4,1,4,2));
		quizPrueba.responderQuiz(respuestas);
		
		assertTrue(quizPrueba.getCalificacion() < quizPrueba.calificacionMinima,"No se califico correctamente el Quiz");
		assertEquals(2, quizPrueba.getCalificacion(), "No se califico correctamente el Quiz");
		assertEquals("No Exitosa", quizPrueba.getEstado(), "No se actualizo el estado correctamente luego de calificarlo");
	}
}
