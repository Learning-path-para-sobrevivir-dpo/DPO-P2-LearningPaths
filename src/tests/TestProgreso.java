package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import excepciones.LearningPathIncorrectoProgresoException;
import modelo.LearningPath;
import modelo.Progreso;
import modelo.actividades.Actividad;
import modelo.actividades.Encuesta;
import modelo.actividades.Examen;
import modelo.actividades.QuizOpcionMultiple;
import modelo.actividades.QuizVerdaderoFalso;
import modelo.actividades.RecursoEducativo;
import modelo.actividades.Tarea;

public class TestProgreso {
	private LearningPath lpPrueba;
	private Progreso progreso;
	private Actividad act1;
	private Actividad act2;
	private Actividad act3;
	private Actividad act4;
	private Actividad act5;
	private Actividad act6;
	
	@BeforeEach
	public void setUp()
	{
		lpPrueba =  new LearningPath("Como hacer un test con JUnit", "Ayuda, es una tortura", "Sacar un 5 en el proyecto 2 de DPOO" , 3, 0, "25 de Diciembre", "31 de Diciembre", 1, "Goku");
		
		act1 = new RecursoEducativo("Tips para no perder la cabeza haciendo tests", "No perder la cabeza", 3, 10, true, 20, "Recurso Educativo", "Tutorial", "Haz click en el enlace", "https://www.youtube.com/watch?v=dQw4w9WgXcQ&pp=ygUJcmljayByb2xs");
		lpPrueba.addActividadDeUltimas(act1);
		
		act2 = new Tarea("Ponerle un 5 a este proyecto", "Ponganos un 5, pliss :)", 1, 5, true, 5, "Tarea", "En bloque neon nos ponen 5");
		lpPrueba.addActividadDeUltimas(act2);
		
		act3 = new QuizOpcionMultiple("El quiz más botado de DPOO", "Si no la se, la C", 1, 20, false, 30, "Prueba", 4, "Quiz Opcion Multiple");
		lpPrueba.addActividadDeUltimas(act3);
		
		act4 = new QuizVerdaderoFalso("Verdadero o Falso: los Unicornios existen", "Unicornios", 2, 20, false, 30, "Prueba", 3, "Quiz Verdadero Falso");
		lpPrueba.addActividadDeUltimas(act4);
		
		act5 = new Encuesta("Opiniones sobre la pizza con piña", "Te gusta la pizza con piña?", 1, 10, false, 20, "Prueba", "Encuesta");
		lpPrueba.addActividadDeUltimas(act5);
		
		act6 = new Examen("Examen Proyecto 2", "Changua", 3, 60, true, 60, "Prueba", "Examen");
		lpPrueba.addActividadDeUltimas(act6);
		
		progreso = new Progreso(lpPrueba.getTitulo(), "Shrek");
	}
	
	@AfterEach
	public void tearDown()
	{
		lpPrueba = null;
		act1 = null;
		act2 = null;
		act3 = null;
		act4 = null;
		act5 = null;
		act6 = null;
		progreso = null;
	}
	
	@Order(1)
	@Test
	public void testObtenerActividadesPath() throws LearningPathIncorrectoProgresoException
	{
		Map<Integer, Actividad> actividadesEsperadas = lpPrueba.getActividades();
		progreso.obtenerActividadesPath(lpPrueba);
		Map<String, Actividad> actividadesObtenidas =  progreso.getIdActividadesOriginales();
		
		assertEquals(actividadesEsperadas.size() ,actividadesObtenidas.size(), "No se cargaron correctamente las actividades");
		
		Actividad actEsperada;
		for (Integer i: actividadesEsperadas.keySet())
		{
			actEsperada = actividadesEsperadas.get(i);
			assertTrue(actividadesObtenidas.containsKey(actEsperada.getId()), "No se cargaron correctamente las actividades");
		}
		
		assertFalse(progreso.getActPendientes().isEmpty(), "No se cargaron correctamente las actividades");
		assertEquals(actividadesEsperadas.size() ,progreso.getActPendientes().size(), "No se cargaron correctamente las actividades");
		assertFalse(progreso.getActObligatoriasPendientes().isEmpty(), "No se cargaron correctamente las actividades");
		
		assertTrue(progreso.getActCompletadas().isEmpty(), "No se cargaron correctamente las actividades");
		assertTrue(progreso.getActObligatoriasCompletadas().isEmpty(), "No se cargaron correctamente las actividades");
	}
	
	@Test
	public void testLearningPathIncorrectoProgresoException()
	{
		LearningPath lpIncorrecto =  new LearningPath("Como NO hacer un test con JUnit", "Ayuda, es una tortura", "Sacar un 5 en el proyecto 2 de DPOO" , 3, 0, "25 de Diciembre", "31 de Diciembre", 1, "Goku");
		
		assertThrows(LearningPathIncorrectoProgresoException.class, ()->progreso.obtenerActividadesPath(lpIncorrecto));
	}
	
	@Test
	public void testEliminarActividadPendiente() throws LearningPathIncorrectoProgresoException
	{
		progreso.obtenerActividadesPath(lpPrueba);
		Actividad actEliminar = progreso.obtenerActividadPorNum(1);
		progreso.eliminarActividadPendiente(actEliminar);
		List<Actividad> listaPendientesEsperada = new ArrayList<Actividad>(List.of(act2, act3, act4, act5, act6));
		List<Actividad> listaObtenida = progreso.getActPendientes();
		
		assertEquals(listaPendientesEsperada.size(), listaObtenida.size(), "No se elimino la actividad de la lista de pendientes correctamente");
		
		Actividad actObtenida;
		Actividad actEsperada;
		for (int i = 0; i < listaObtenida.size(); i++)
		{
			actObtenida = listaObtenida.get(i);
			actEsperada = listaPendientesEsperada.get(i);
			
			assertEquals(actEsperada.getId(), actObtenida.getId(),"No se elimino la actividad de la lista de pendientes correctamente");
		}
	}
}
