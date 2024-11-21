package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import modelo.LearningPath;
import modelo.Profesor;
import modelo.Progreso;
import modelo.actividades.Actividad;
import modelo.actividades.Examen;
import modelo.actividades.QuizOpcionMultiple;
import modelo.actividades.QuizVerdaderoFalso;
import modelo.actividades.Tarea;

public class TestLearningPath {
	
	private LearningPath path1;
	private QuizOpcionMultiple act1;
	private Actividad act2;
	private Examen act3;
	private Actividad act4;
	private Progreso progreso;
	
	@BeforeEach
	public void setUp() {
		
		path1 = new LearningPath ("Algoritmos en Excel", "algoritmos en excel", "Aprender algoritmos", 3, 4, "2023-10-20", "11", 1, "profesor1");
		
		
		act1 = new QuizOpcionMultiple("Quiz Multiple 1","Quiz que evalua los conocimientos.", 2, 40, true, 50, "Prueba", 1, "Quiz Opcion Multiple");
		act2 = new QuizVerdaderoFalso("Quiz VoF 2", "Evaluar conocimientos", 3, 40, true, 50, "Prueba", 1, "Quiz Verdadero Falso");
				
		act3 = new Examen("Examen final", "Evalua todo el curso", 3, 120, true, 120, "Prueba", "Examen");
		act4 = new Tarea("Tarea 1", "Practicar el conocimiento", 1, 20, false, 360, "Tarea", "Hacer los ejercicios 4-14", "Bloque Neon");

		progreso = new Progreso(path1.getTitulo(), "Shrek");

	}
	
	@AfterEach
	public void tearDown()
	{
		path1 = null;
		act4= null;
		act1 = null;
		act2 = null;
		act3 = null;

		
	}
	
	
	@Test
	public void testSetActividades() {
		
		Map<Integer, Actividad> acts = new HashMap<Integer, Actividad>();
	
		acts.put(1, act1);
		acts.put(2, act2);
		
		path1.setActividades(acts);
		
		Map<Integer, Actividad> actsActuales = path1.getActividades();
		
		assertEquals(acts.size(), actsActuales.size(), "No hay la cantidad esperada de actividades.");
		assertEquals("Quiz Multiple 1", (actsActuales.get(1)).getTitulo(), "No es la actividad esperada");
		assertEquals("Quiz VoF 2", (actsActuales.get(2)).getTitulo(), "No es la actividad esperada");
		
		
		
	}
	
	@Test
	public void testAddEliminarActividad() {
		path1.addActividadDeUltimas(act2);
		
		Map<Integer, Actividad> actsTest= path1.getActividades();
		assertEquals("Quiz VoF 2", (actsTest.get(1)).getTitulo(), "No tiene la posicion esperada");
		
		path1.addActividadPorPos(act1, 2);
		assertEquals("Quiz Multiple 1", (actsTest.get(2)).getTitulo(), "No tiene la posicion esperada");

		path1.addActividadPorPos(act3, 2);
		actsTest= path1.getActividades();
		assertEquals("Examen final", (actsTest.get(2)).getTitulo(), "No tiene la posicion esperada");
		assertEquals("Quiz Multiple 1", (actsTest.get(3)).getTitulo(), "No tiene la posicion esperada");

		//Test de que si la actividad ya estaba en el path, la elimina y cambia su posición por la nueva posición
		path1.addActividadPorPos(act3, 3);
		actsTest= path1.getActividades();
		assertEquals("Examen final", (actsTest.get(3)).getTitulo(), "No tiene la posicion esperada");
		assertEquals("Quiz Multiple 1", (actsTest.get(2)).getTitulo(), "No tiene la posicion esperada");

		path1.eliminarActividadPorPos(2);
		actsTest= path1.getActividades();
		assertEquals(2, actsTest.size(), "No se eliminó correctamente");	
		assertEquals("Examen final", (actsTest.get(2)).getTitulo(), "No se eliminó correctamente");

		path1.addActividadDeUltimas(act4);
		actsTest= path1.getActividades();
		assertEquals(3, actsTest.size(), "No se eliminó correctamente");	
		assertEquals("Tarea 1", (actsTest.get(3)).getTitulo(), "No se añadió correctamente de última"+actsTest);

		
		assertThrows(IllegalArgumentException.class, ()->path1.eliminarActividadPorPos(-2));
	}
	
	
	@Test
	public void testRatings() {
		
		path1.addRating(3);
		path1.addRating(5);
		
		assertEquals(4, path1.getRating(),"No se calcula correctamente el rating.");
		
	}
	
	
	@Test
	public void testAddProgreso() {
		
		path1.addProgresoEstudiante(progreso);
		
		Map<String, Progreso> progresos = path1.getProgresosEstudiantiles();
		
		List<String> estudiantes = path1.getEstudiantes();
		
		assertEquals(1, progresos.size(),"No se añadió correctamente el progreso.");
	
		assertTrue(estudiantes.contains("Shrek"), "No se añadió correctamente el progreso.");
	}
	
}
