package tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import modelo.*;
import modelo.actividades.*;

public class TestPreguntaMultiple {
	private PreguntaMultiple pregunta;

    @BeforeEach
    void setUp() {
        List<String> opciones = Arrays.asList("Opción 1", "Opción 2", "Opción 3");
        pregunta = new PreguntaMultiple("¿Cuál es la opción correcta?", opciones, 2);
    }

    @Test
    void testConstructorConOpciones() {
        assertEquals("¿Cuál es la opción correcta?", pregunta.getEnunciado());
        assertEquals(3, pregunta.getOpciones().size());
        assertEquals(1, pregunta.getOpcionCorrecta()); // La opción correcta es la 2, indexada en 0.
        assertEquals(-1, pregunta.getOpcionSeleccionada());
        assertEquals("Pregunta Multiple", pregunta.getTipo());
    }

    @Test
    void testConstructorSinOpciones() {
        PreguntaMultiple preguntaSinOpciones = new PreguntaMultiple("Pregunta sin opciones");
        assertEquals("Pregunta sin opciones", preguntaSinOpciones.getEnunciado());
        assertTrue(preguntaSinOpciones.getOpciones().isEmpty());
        assertEquals(-1, preguntaSinOpciones.getOpcionCorrecta());
        assertEquals("Pregunta Multiple", preguntaSinOpciones.getTipo());
    }

    @Test
    void testAddOpcion() {
        pregunta.addOpcion(2, "Nueva Opción", false);
        assertEquals(4, pregunta.getOpciones().size());
        assertEquals("Nueva Opción", pregunta.getOpciones().get(1));
        assertEquals(1, pregunta.getOpcionCorrecta()); // La opción correcta sigue siendo la 2.
    }

    @Test
    void testAddOpcionCorrecta() {
        pregunta.addOpcion(1, "Nueva Opción Correcta", true);
        assertEquals(4, pregunta.getOpciones().size());
        assertEquals("Nueva Opción Correcta", pregunta.getOpciones().get(0));
        assertEquals(0, pregunta.getOpcionCorrecta()); // Nueva opción correcta.
    }

    @Test
    void testEliminarOpcion() {
        pregunta.eliminarOpcion(2);
        assertEquals(2, pregunta.getOpciones().size());
        assertEquals(-1, pregunta.getOpcionCorrecta()); // Se eliminó la opción correcta.
    }

    @Test
    void testEliminarOpcionReajustarCorrecta() {
        pregunta.addOpcion(1, "Nueva Opción", false);
        pregunta.eliminarOpcion(1);
        assertEquals(3, pregunta.getOpciones().size());
        assertEquals(0, pregunta.getOpcionCorrecta()); // La opción correcta se ajusta.
    }

    @Test
    void testSetOpcionSeleccionada() {
        pregunta.setOpcionSeleccionada(2);
        assertEquals(2, pregunta.getOpcionSeleccionada());
    }

    @Test
    void testSetOpciones() {
        List<String> nuevasOpciones = Arrays.asList("Nueva 1", "Nueva 2", "Nueva 3");
        pregunta.setOpciones(nuevasOpciones);
        assertEquals(3, pregunta.getOpciones().size());
        assertEquals("Nueva 1", pregunta.getOpciones().get(0));
        assertEquals(-1, pregunta.getOpcionCorrecta()); // Se resetea la opción correcta.
    }
}

