package tests;

import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeFalse;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import modelo.actividades.PreguntaVerdaderoFalso;

public class TestPreguntaVerdaderoFalso {
	
	private PreguntaVerdaderoFalso preguntaVerdadero;
	private PreguntaVerdaderoFalso preguntaFalso;
	
	@BeforeEach
	public void setUp()
	{
		preguntaFalso = new PreguntaVerdaderoFalso("La respuesta a esta pregunta es False", false);
		preguntaVerdadero = new PreguntaVerdaderoFalso("La respuesta a esta pregunta es True", true);
	}
	
	@AfterEach
	public void tearDown()
	{
		preguntaFalso = null;
		preguntaVerdadero = null;
	}
	
	@Test
	public void testVerificarCorrectaOpcionCorrecta()
	{
		preguntaFalso.setOpcionSeleccionada(false);
		preguntaVerdadero.setOpcionSeleccionada(true);
		
		assumeFalse(preguntaFalso.isCorrecta());
		assumeFalse(preguntaVerdadero.isCorrecta());
		
		preguntaFalso.verificarCorrecta();
		preguntaVerdadero.verificarCorrecta();
		
		assertTrue(preguntaFalso.isCorrecta(), "No se verifico correctamente si la respuesta seleccionada es correcta");
		assertTrue(preguntaVerdadero.isCorrecta(), "No se verifico correctamente si la respuesta seleccionada es correcta");
	}
	
	@Test
	public void testVerificarCorrectaOpcionInCorrecta()
	{
		preguntaFalso.setOpcionSeleccionada(true);
		preguntaVerdadero.setOpcionSeleccionada(false);
		
		assumeFalse(preguntaFalso.isCorrecta());
		assumeFalse(preguntaVerdadero.isCorrecta());
		
		preguntaFalso.verificarCorrecta();
		preguntaVerdadero.verificarCorrecta();
		
		assertFalse(preguntaFalso.isCorrecta(), "No se verifico correctamente si la respuesta seleccionada es correcta");
		assertFalse(preguntaVerdadero.isCorrecta(), "No se verifico correctamente si la respuesta seleccionada es correcta");
	}
}
