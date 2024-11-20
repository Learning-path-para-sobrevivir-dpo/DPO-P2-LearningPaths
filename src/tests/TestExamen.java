package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import excepciones.RespuestasInconsistentesPruebaException;
import excepciones.TipoDePreguntaInvalidaException;
import modelo.actividades.*;

public class TestExamen {
	

    private Examen examen;
    private List<PreguntaAbierta> preguntas;

    @BeforeEach
    public void setUp() {
        preguntas = new ArrayList<>();
        preguntas.add(new PreguntaAbierta("Pregunta 1", 1));
        preguntas.add(new PreguntaAbierta("Pregunta 2", 2));
        
        examen = new Examen(
                "Examen de prueba", 
                "Objetivo de prueba", 
                3, 
                60, 
                true, 
                30, 
                "Prueba", 
                preguntas, 
                "Examen"
        );
    }

    @Test
    public void testAgregarPregunta() throws TipoDePreguntaInvalidaException {
        PreguntaAbierta nuevaPregunta = new PreguntaAbierta("Pregunta nueva", 0);
        examen.addPregunta(nuevaPregunta);

        assertEquals(3, examen.getPreguntas().size());
        assertEquals("Pregunta nueva", examen.getPreguntas().get(2).getEnunciado());
    }

    @Test
    public void testAgregarPreguntaInvalida() {
        Exception exception = assertThrows(TipoDePreguntaInvalidaException.class, () -> {
            examen.addPregunta(new PreguntaVerdaderoFalso("Pregunta tipo incorrecto", 0) {
                @Override
                public String getTipo() {
                    return "Invalido";
                }
            });
        });

        String expectedMessage = "Invalido";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testExamenSinPreguntas() {
    	 examen = new Examen(
                 "No hay titulo creativo",
                 "No hacer nada",
                 1, 
                 10, 
                 true, 
                 15, 
                 "Prueba", 
                 "Examen"
         );
    	 assertEquals("No hay titulo creativo", examen.getTitulo());
    	 assertEquals("No hacer nada", examen.getObjetivo());
    	 assertTrue(examen.getPreguntas().isEmpty());
     }
    
    @Test
    public void testEliminarPregunta() {
        examen.eliminarPregunta(1);

        assertEquals(1, examen.getPreguntas().size());
        assertEquals("Pregunta 2", examen.getPreguntas().get(0).getEnunciado());
    }

    @Test
    public void testResponderExamen() throws RespuestasInconsistentesPruebaException {
        List<String> respuestas = new ArrayList<>();
        respuestas.add("Respuesta 1");
        respuestas.add("Respuesta 2");

        examen.responderExamen(respuestas);

        assertEquals("Respuesta 1", examen.getPreguntas().get(0).getRespuesta());
        assertEquals("Respuesta 2", examen.getPreguntas().get(1).getRespuesta());
    }

    @Test
    public void testCalificarExamen() throws RespuestasInconsistentesPruebaException {
        List<String> respuestas = new ArrayList<>();
        respuestas.add("Respuesta 1");
        respuestas.add("Respuesta 2");
        examen.responderExamen(respuestas);

        List<String> respuestasCalificadas = new ArrayList<>();
        respuestasCalificadas.add("correcta");
        respuestasCalificadas.add("incorrecta");

        examen.calificar(respuestasCalificadas);

        assertTrue(examen.getPreguntas().get(0).isCorrecta());
        assertFalse(examen.getPreguntas().get(1).isCorrecta());
        assertTrue(examen.isCalificado());
    }

    @Test
    public void testCalcularCalificacion() throws RespuestasInconsistentesPruebaException {
        List<String> respuestas = new ArrayList<>();
        respuestas.add("Respuesta 1");
        respuestas.add("Respuesta 2");
        examen.responderExamen(respuestas);

        List<String> respuestasCalificadas = new ArrayList<>();
        respuestasCalificadas.add("correcta");
        respuestasCalificadas.add("correcta");

	        examen.calificar(respuestasCalificadas);
	        examen.calcularCalificacion();

	        assertEquals(5.0, examen.getCalificacion());
	    }
	}

