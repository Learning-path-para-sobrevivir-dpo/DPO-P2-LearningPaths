package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import modelo.actividades.PreguntaAbierta;



public class TestPreguntaAbierta {


	private PreguntaAbierta pregunta;

	
	
	@BeforeEach
	public void setUp() {
		
		pregunta = new PreguntaAbierta ("¿Cuál es la capital de España?", 2);
	}
	
	@AfterEach
	public void tearDown()
	{
		pregunta = null;

		
	}
	
	@Test
	public void testRespuesta() {
		
		pregunta.setRespuesta("Madrid");
		
		assertEquals("Madrid", pregunta.getRespuesta(), "No es la respuesta.");
		
	}
	
	
}
