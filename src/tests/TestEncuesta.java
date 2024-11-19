package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import modelo.*;
import modelo.actividades.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import excepciones.RespuestasInconsistentesPruebaException;
import excepciones.TipoDePreguntaInvalidaException;

class EncuestaTest {

    private Encuesta encuesta;
    private List<PreguntaAbierta> preguntasIniciales;

    @BeforeEach
    void setUp() {
        preguntasIniciales = new ArrayList<>();
        preguntasIniciales.add(new PreguntaAbierta("Pregunta 1"));
        preguntasIniciales.add(new PreguntaAbierta("Pregunta 2"));

        encuesta = new Encuesta(
                "Título de Encuesta",
                "Objetivo de la encuesta",
                1, // Nivel de dificultad
                10, // Duración en minutos
                true, // Obligatorio
                15, // Tiempo sugerido para completar
                "Encuesta", // Tipo de actividad
                preguntasIniciales,
                "Encuesta"
        );
    }

    @Test
    void testSetPreguntas() {
        List<PreguntaAbierta> nuevasPreguntas = new ArrayList<>();
        nuevasPreguntas.add(new PreguntaAbierta("Pregunta A"));
        nuevasPreguntas.add(new PreguntaAbierta("Pregunta B"));

        encuesta.setPreguntas(nuevasPreguntas);
        assertEquals(2, encuesta.getPreguntas().size());
        assertEquals("Pregunta A", encuesta.getPreguntas().get(0).getTexto());
        assertEquals(1, encuesta.getPreguntas().get(0).getNumero());
    }

    @Test
    void testResponderEncuestaConRespuestasCorrectas() throws RespuestasInconsistentesPruebaException {
        List<String> respuestas = Arrays.asList("Respuesta 1", "Respuesta 2");

        encuesta.responderEncuesta(respuestas);
        assertTrue(encuesta.isRespondida());
        assertEquals("Respuesta 1", encuesta.getPreguntas().get(0).getRespuesta());
        assertEquals("Respuesta 2", encuesta.getPreguntas().get(1).getRespuesta());
    }

    @Test
    void testResponderEncuestaConRespuestasInconsistentes() {
        List<String> respuestasIncorrectas = Arrays.asList("Solo una respuesta");

        assertThrows(RespuestasInconsistentesPruebaException.class, () -> {
            encuesta.responderEncuesta(respuestasIncorrectas);
        });
    }

    @Test
    void testAddPreguntaValida() throws TipoDePreguntaInvalidaException {
        PreguntaAbierta nuevaPregunta = new PreguntaAbierta("Pregunta 3");
        encuesta.addPregunta(nuevaPregunta);

        assertEquals(3, encuesta.getPreguntas().size());
        assertEquals("Pregunta 3", encuesta.getPreguntas().get(2).getTexto());
    }

    @Test
    void testAddPreguntaInvalida() {
        Pregunta preguntaInvalida = new Pregunta("Pregunta Invalida", "Otro tipo");

        assertThrows(TipoDePreguntaInvalidaException.class, () -> {
            encuesta.addPregunta(preguntaInvalida);
        });
    }

    @Test
    void testEliminarPregunta() {
        encuesta.eliminarPregunta(1);

        assertEquals(1, encuesta.getPreguntas().size());
        assertEquals("Pregunta 2", encuesta.getPreguntas().get(0).getTexto());
    }

    @Test
    void testCalcularCalificacion() throws RespuestasInconsistentesPruebaException {
        List<String> respuestas = Arrays.asList("Respuesta 1", "Respuesta 2");
        encuesta.responderEncuesta(respuestas);

        encuesta.calcularCalificacion();
        assertEquals(5, encuesta.getCalificacion());
    }
}
