package tests;

import static org.junit.Assume.assumeTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeFalse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import excepciones.CompletarActividadQueNoEstaEnProgresoException;
import excepciones.LearningPathIncorrectoProgresoException;
import excepciones.YaExisteActividadEnProgresoException;
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
		
		act2 = new Tarea("Ponerle un 5 a este proyecto", "Ponganos un 5, pliss :)", 1, 5, true, 5, "Tarea", "En bloque neon nos ponen 5", "");
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
		assertEquals(actividadesEsperadas.size(), progreso.getActPendientes().size(), "No se cargaron correctamente las actividades");
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
	public void testObtenerActividadPorNum() throws LearningPathIncorrectoProgresoException
	{
		Actividad actObtenida;
		progreso.obtenerActividadesPath(lpPrueba);
		
		actObtenida = progreso.obtenerActividadPorNum(1);
		assertEquals(act1.getId(), actObtenida.getId(), "No se obtuvo la actividad esperada");
		
		actObtenida = progreso.obtenerActividadPorNum(2);
		assertEquals(act2.getId(), actObtenida.getId(), "No se obtuvo la actividad esperada");
		
		actObtenida = progreso.obtenerActividadPorNum(3);
		assertEquals(act3.getId(), actObtenida.getId(), "No se obtuvo la actividad esperada");
		
		actObtenida = progreso.obtenerActividadPorNum(4);
		assertEquals(act4.getId(), actObtenida.getId(), "No se obtuvo la actividad esperada");
		
		actObtenida = progreso.obtenerActividadPorNum(5);
		assertEquals(act5.getId(), actObtenida.getId(), "No se obtuvo la actividad esperada");
		
		actObtenida = progreso.obtenerActividadPorNum(6);
		assertEquals(act6.getId(), actObtenida.getId(), "No se obtuvo la actividad esperada");
		
		//Los outliers donde la actividad retorna null
		actObtenida = progreso.obtenerActividadPorNum(0);
		assertTrue(actObtenida == null, "No se obtuvo la actividad esperada");
		
		actObtenida = progreso.obtenerActividadPorNum(7);
		assertTrue(actObtenida == null, "No se obtuvo la actividad esperada");
	}
	
	@Test
	public void testEmpezarActividad() throws LearningPathIncorrectoProgresoException, YaExisteActividadEnProgresoException
	{
		progreso.obtenerActividadesPath(lpPrueba);
		
		Actividad actEmpezar = progreso.obtenerActividadPorNum(1);
		progreso.empezarActividad(actEmpezar);
		
		Actividad actEnProgreso = progreso.getActividadEnProgreso();
		
		assertEquals(actEmpezar, actEnProgreso, "No se empezo la actividad esperada");
	}
	
	@Test
	public void testYaExisteActividadEnProgresoException() throws LearningPathIncorrectoProgresoException, YaExisteActividadEnProgresoException
	{
		progreso.obtenerActividadesPath(lpPrueba);
		
		Actividad actEmpezar = progreso.obtenerActividadPorNum(1);
		progreso.empezarActividad(actEmpezar);
		
		Actividad actEmpezar2 = progreso.obtenerActividadPorNum(4);
		assertThrows(YaExisteActividadEnProgresoException.class, ()->progreso.empezarActividad(actEmpezar2));
	}
	
	@Test
	public void testCompletarActividadRecursoEducativo() throws LearningPathIncorrectoProgresoException, YaExisteActividadEnProgresoException, CompletarActividadQueNoEstaEnProgresoException
	{
		progreso.obtenerActividadesPath(lpPrueba);
		Actividad actCompletar = progreso.obtenerActividadPorNum(1); //act1: Recurso Educativo Obligatorio
		
		progreso.empezarActividad(actCompletar);
		
		boolean completada = progreso.completarActividad(actCompletar);
		
		assertTrue(completada, "No se completo el Recurso Educativo como era de esperarse");
		
		//Se verifica que se haya quitado de las listas correspondientes
		assertFalse(progreso.getActPendientes().contains(actCompletar), "No se actualizaron las listas de actividades pendientes y completadas como era de esperarse");
		assertTrue(progreso.getActCompletadas().contains(actCompletar), "No se actualizaron las listas de actividades pendientes y completadas como era de esperarse");
		
		//Como este Recurso Educativo era una actividad Obligatoria, también se verifica
		//que las listas de actividades obligatorias pendientes y cambien
		assertFalse(progreso.getActObligatoriasPendientes().contains(actCompletar), "No se actualizaron las listas de actividades pendientes y completadas como era de esperarse");
		assertTrue(progreso.getActObligatoriasCompletadas().contains(actCompletar), "No se actualizaron las listas de actividades pendientes y completadas como era de esperarse");
	}
	
	@Test
	public void testCompletarActividadTarea() throws LearningPathIncorrectoProgresoException, YaExisteActividadEnProgresoException, CompletarActividadQueNoEstaEnProgresoException
	{
		progreso.obtenerActividadesPath(lpPrueba);
		Actividad actCompletar = progreso.obtenerActividadPorNum(2); //act2: Tarea Obligatoria
		
		progreso.empezarActividad(actCompletar);
		
		boolean completada = progreso.completarActividad(actCompletar);
		
		assertFalse(completada, "Se completo la actividad sin antes cumplir con los requerimientos para marcarla como completada");
		
		//Se actualiza la actividad para que cumpla con los requerimientos
		//para marcarla como completada
		Tarea t = (Tarea) actCompletar;
		t.setEnviado(true);
		completada = progreso.completarActividad(actCompletar);
		
		assertTrue(completada, "No se completo la tarea como era de esperarse");
		
		//Se verifica que se haya quitado de las listas correspondientes
		assertFalse(progreso.getActPendientes().contains(actCompletar),  "No se actualizaron las listas de actividades pendientes y completadas como era de esperarse");
		assertTrue(progreso.getActCompletadas().contains(actCompletar),  "No se actualizaron las listas de actividades pendientes y completadas como era de esperarse");
		
		//Como esta Tarea era una actividad Obligatoria, también se verifica
		//que las listas de actividades obligatorias pendientes y completadas no cambi
		assertFalse(progreso.getActObligatoriasPendientes().contains(actCompletar),  "No se actualizaron las listas de actividades pendientes y completadas como era de esperarse");
		assertTrue(progreso.getActObligatoriasCompletadas().contains(actCompletar),  "No se actualizaron las listas de actividades pendientes y completadas como era de esperarse");
	}
	
	@Test
	public void testCompletarActividadQuizOpcionMultiple() throws LearningPathIncorrectoProgresoException, YaExisteActividadEnProgresoException, CompletarActividadQueNoEstaEnProgresoException
	{
		progreso.obtenerActividadesPath(lpPrueba);
		Actividad actCompletar = progreso.obtenerActividadPorNum(3); //act3: Quiz Opcion Multiple Opcional
		
		progreso.empezarActividad(actCompletar);
		
		boolean completada = progreso.completarActividad(actCompletar);
		
		assertFalse(completada, "Se completo la actividad sin antes cumplir con los requerimientos para marcarla como completada");
		
		QuizOpcionMultiple q = (QuizOpcionMultiple) actCompletar;
		q.setCalificacion(q.getCalificacionMinima() + (float) 0.5);
		completada = progreso.completarActividad(actCompletar);
		assertFalse(completada, "Se completo la actividad sin antes cumplir con los requerimientos para marcarla como completada");
		
		//Se actualiza la actividad para que cumpla con los requerimientos
		//para marcarla como completada
		q.setRespondida(true);
		completada = progreso.completarActividad(actCompletar);
		
		assertTrue(completada, "No se completo la tarea como era de esperarse");
		
		//Se verifica que se haya quitado de las listas correspondientes
		assertFalse(progreso.getActPendientes().contains(actCompletar),  "No se actualizaron las listas de actividades pendientes y completadas como era de esperarse");
		assertTrue(progreso.getActCompletadas().contains(actCompletar),  "No se actualizaron las listas de actividades pendientes y completadas como era de esperarse");
		
		//Como este Quiz no era una actividad Obligatoria, también se verifica
		//que las listas de actividades obligatorias pendientes y completadas no cambien
		assertFalse(progreso.getActObligatoriasPendientes().contains(actCompletar),  "No se actualizaron las listas de actividades pendientes y completadas como era de esperarse");
		assertFalse(progreso.getActObligatoriasCompletadas().contains(actCompletar),  "No se actualizaron las listas de actividades pendientes y completadas como era de esperarse");
	}
	
	@Test
	public void testCompletarActividadQuizVerdaderoFalso() throws LearningPathIncorrectoProgresoException, YaExisteActividadEnProgresoException, CompletarActividadQueNoEstaEnProgresoException
	{
		progreso.obtenerActividadesPath(lpPrueba);
		Actividad actCompletar = progreso.obtenerActividadPorNum(4); //act4: Quiz Verdadero o Falso Opcional
		
		progreso.empezarActividad(actCompletar);
		
		boolean completada = progreso.completarActividad(actCompletar);
		
		assertFalse(completada, "Se completo la actividad sin antes cumplir con los requerimientos para marcarla como completada");
		
		QuizVerdaderoFalso q = (QuizVerdaderoFalso) actCompletar;
		q.setCalificacion(q.getCalificacionMinima() + (float) 0.5);
		completada = progreso.completarActividad(actCompletar);
		assertFalse(completada, "Se completo la actividad sin antes cumplir con los requerimientos para marcarla como completada");
		
		//Se actualiza la actividad para que cumpla con los requerimientos
		//para marcarla como completada
		q.setRespondida(true);
		completada = progreso.completarActividad(actCompletar);
		
		assertTrue(completada, "No se completo la tarea como era de esperarse");
		
		//Se verifica que se haya quitado de las listas correspondientes
		assertFalse(progreso.getActPendientes().contains(actCompletar),  "No se actualizaron las listas de actividades pendientes y completadas como era de esperarse");
		assertTrue(progreso.getActCompletadas().contains(actCompletar),  "No se actualizaron las listas de actividades pendientes y completadas como era de esperarse");
		
		//Como este Quiz no era una actividad Obligatoria, también se verifica
		//que las listas de actividades obligatorias pendientes y completadas no cambien
		assertFalse(progreso.getActObligatoriasPendientes().contains(actCompletar),  "No se actualizaron las listas de actividades pendientes y completadas como era de esperarse");
		assertFalse(progreso.getActObligatoriasCompletadas().contains(actCompletar),  "No se actualizaron las listas de actividades pendientes y completadas como era de esperarse");
	}
	
	@Test
	public void testCompletarActividadEncuesta() throws LearningPathIncorrectoProgresoException, YaExisteActividadEnProgresoException, CompletarActividadQueNoEstaEnProgresoException
	{
		progreso.obtenerActividadesPath(lpPrueba);
		Actividad actCompletar = progreso.obtenerActividadPorNum(5); //act5: Encuesta Opcional
		
		progreso.empezarActividad(actCompletar);
		
		boolean completada = progreso.completarActividad(actCompletar);
		
		assertFalse(completada, "Se completo la actividad sin antes cumplir con los requerimientos para marcarla como completada");
		
		Encuesta en = (Encuesta) actCompletar;
		
		//Se actualiza la actividad para que cumpla con los requerimientos
		//para marcarla como completada
		en.setRespondida(true);
		completada = progreso.completarActividad(actCompletar);
		
		assertTrue(completada, "No se completo la tarea como era de esperarse");
		
		//Se verifica que se haya quitado de las listas correspondientes
		assertFalse(progreso.getActPendientes().contains(actCompletar),  "No se actualizaron las listas de actividades pendientes y completadas como era de esperarse");
		assertTrue(progreso.getActCompletadas().contains(actCompletar),  "No se actualizaron las listas de actividades pendientes y completadas como era de esperarse");
		
		//Como esta Encuesta no era una actividad Obligatoria, también se verifica
		//que las listas de actividades obligatorias pendientes y completadas no cambien
		assertFalse(progreso.getActObligatoriasPendientes().contains(actCompletar),  "No se actualizaron las listas de actividades pendientes y completadas como era de esperarse");
		assertFalse(progreso.getActObligatoriasCompletadas().contains(actCompletar),  "No se actualizaron las listas de actividades pendientes y completadas como era de esperarse");
	}
	
	@Test
	public void testCompletarActividadExamen() throws LearningPathIncorrectoProgresoException, YaExisteActividadEnProgresoException, CompletarActividadQueNoEstaEnProgresoException
	{
		progreso.obtenerActividadesPath(lpPrueba);
		Actividad actCompletar = progreso.obtenerActividadPorNum(6); //act6: Examen Obligatorio
		
		progreso.empezarActividad(actCompletar);
		
		boolean completada = progreso.completarActividad(actCompletar);
		
		assertFalse(completada, "Se completo la actividad sin antes cumplir con los requerimientos para marcarla como completada");
		
		//Se actualiza la actividad para que cumpla con los requerimientos
		//para marcarla como completada
		Examen ex = (Examen) actCompletar;
		ex.setRespondida(true);
		completada = progreso.completarActividad(actCompletar);
		
		assertTrue(completada, "No se completo la tarea como era de esperarse");
		
		//Se verifica que se haya quitado de las listas correspondientes
		assertFalse(progreso.getActPendientes().contains(actCompletar),  "No se actualizaron las listas de actividades pendientes y completadas como era de esperarse");
		assertTrue(progreso.getActCompletadas().contains(actCompletar),  "No se actualizaron las listas de actividades pendientes y completadas como era de esperarse");
		
		//Como este Examen era una actividad Obligatoria, también se verifica
		//que las listas de actividades obligatorias pendientes y completadas cambien
		assertFalse(progreso.getActObligatoriasPendientes().contains(actCompletar),  "No se actualizaron las listas de actividades pendientes y completadas como era de esperarse");
		assertTrue(progreso.getActObligatoriasCompletadas().contains(actCompletar),  "No se actualizaron las listas de actividades pendientes y completadas como era de esperarse");
	}
	
	@Test
	public void testCompletarActividadQueNoEstaEnProgresoExceptionSinActividadEmpezada() throws LearningPathIncorrectoProgresoException
	{
		progreso.obtenerActividadesPath(lpPrueba);
		Actividad actCompletar = progreso.obtenerActividadPorNum(1);
		
		assertThrows(CompletarActividadQueNoEstaEnProgresoException.class, ()->progreso.completarActividad(actCompletar));
	}
	
	@Test
	public void testCompletarActividadQueNoEstaEnProgresoExceptionConActividadEmpezada() throws LearningPathIncorrectoProgresoException, YaExisteActividadEnProgresoException
	{
		progreso.obtenerActividadesPath(lpPrueba);
		Actividad actEmpezada = progreso.obtenerActividadPorNum(5);
		Actividad actCompletar = progreso.obtenerActividadPorNum(1);
		
		progreso.empezarActividad(actEmpezada);
		
		assertThrows(CompletarActividadQueNoEstaEnProgresoException.class, ()->progreso.completarActividad(actCompletar));
	}
	
	@Test
	public void testDescompletarActividadObligatoriaCompletada() throws LearningPathIncorrectoProgresoException, YaExisteActividadEnProgresoException, CompletarActividadQueNoEstaEnProgresoException
	{
		progreso.obtenerActividadesPath(lpPrueba);
		Actividad actCompletar = progreso.obtenerActividadPorNum(1);
		progreso.empezarActividad(actCompletar);
		progreso.completarActividad(actCompletar);
		assumeTrue(progreso.getActCompletadas().contains(actCompletar));
		assumeTrue(progreso.getActObligatoriasCompletadas().contains(actCompletar)); //Como la actividad 1 es obligatoria, tambien tiene que estar en la lista de Obligatorias
		assumeTrue(actCompletar.isCompletada());
		
		progreso.descompletarActividad(actCompletar);
		assertFalse(progreso.getActCompletadas().contains(actCompletar), "No se actualizaron las listas de actividades pendientes y completadas como era de esperarse");
		assertFalse(progreso.getActObligatoriasCompletadas().contains(actCompletar), "No se actualizaron las listas de actividades pendientes y completadas como era de esperarse");
		
		assertTrue(progreso.getActPendientes().contains(actCompletar), "No se actualizaron las listas de actividades pendientes y completadas como era de esperarse");
		assertTrue(progreso.getActObligatoriasPendientes().contains(actCompletar), "No se actualizaron las listas de actividades pendientes y completadas como era de esperarse");
		
		assertFalse(actCompletar.isCompletada(), "No se actualizó el estado de la actividad");
	}
	
	@Test
	public void testDescompletarActividadNoObligatoriaCompletada() throws LearningPathIncorrectoProgresoException, YaExisteActividadEnProgresoException, CompletarActividadQueNoEstaEnProgresoException
	{
		progreso.obtenerActividadesPath(lpPrueba);
		Actividad actCompletar = progreso.obtenerActividadPorNum(5); //Encuesta no obligatoria
		progreso.empezarActividad(actCompletar);
		Encuesta p = (Encuesta) actCompletar;
		p.setRespondida(true);
		progreso.completarActividad(actCompletar);
		assumeTrue(progreso.getActCompletadas().contains(actCompletar));
		assumeFalse(progreso.getActObligatoriasCompletadas().contains(actCompletar)); //Como la actividad 1 es obligatoria, tambien tiene que estar en la lista de Obligatorias
		assumeTrue(actCompletar.isCompletada());
		
		progreso.descompletarActividad(actCompletar);
		assertFalse(progreso.getActCompletadas().contains(actCompletar), "No se actualizaron las listas de actividades pendientes y completadas como era de esperarse");
		assertFalse(progreso.getActObligatoriasCompletadas().contains(actCompletar), "No se actualizaron las listas de actividades pendientes y completadas como era de esperarse");
		
		assertTrue(progreso.getActPendientes().contains(actCompletar), "No se actualizaron las listas de actividades pendientes y completadas como era de esperarse");
		assertFalse(progreso.getActObligatoriasPendientes().contains(actCompletar), "No se actualizaron las listas de actividades pendientes y completadas como era de esperarse");
		assertTrue(actCompletar.isCompletada(), "No se actualizó el estado de la actividad");
	}
	
	@Test
	public void testDescompletarActividadNoCompletada() throws LearningPathIncorrectoProgresoException, YaExisteActividadEnProgresoException
	{
		progreso.obtenerActividadesPath(lpPrueba);
		Actividad actCompletar = progreso.obtenerActividadPorNum(1); //Actividad obligatoria
		progreso.empezarActividad(actCompletar);
		assumeFalse(progreso.getActCompletadas().contains(actCompletar));
		assumeFalse(progreso.getActObligatoriasCompletadas().contains(actCompletar)); 
		assumeFalse(actCompletar.isCompletada());
		
		progreso.descompletarActividad(actCompletar);
		assertFalse(progreso.getActCompletadas().contains(actCompletar), "No se actualizaron las listas de actividades pendientes y completadas como era de esperarse");
		assertFalse(progreso.getActObligatoriasCompletadas().contains(actCompletar), "No se actualizaron las listas de actividades pendientes y completadas como era de esperarse");
		
		assertTrue(progreso.getActPendientes().contains(actCompletar), "No se actualizaron las listas de actividades pendientes y completadas como era de esperarse");
		//Como la actividad 1 es obligatoria, tiene que estar en la lista de Obligatorias
		assertTrue(progreso.getActObligatoriasPendientes().contains(actCompletar), "No se actualizaron las listas de actividades pendientes y completadas como era de esperarse");
	}
}
