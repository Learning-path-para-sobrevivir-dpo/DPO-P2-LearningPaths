package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import modelo.LearningPath;
import modelo.Progreso;
import modelo.Review;
import modelo.actividades.Examen;
import modelo.actividades.QuizOpcionMultiple;
import modelo.actividades.QuizVerdaderoFalso;
import modelo.actividades.RecursoEducativo;
import modelo.actividades.Tarea;

public class TestRecursoEducativo {

	private RecursoEducativo recurso;
	private Review rev1;
	private Review rev2;
	
	
	
	@BeforeEach
	public void setUp() {
		
		recurso = new RecursoEducativo ("Video-ensayo de una película", "Analizar pelicula", 1, 15, true,  360, "Recurso Educativo", "Video", "Análisis de una película", "https://www.youtube.com/watch?v=VvclV0_o0JE");
		rev1 = new Review("2023-10-11", "Me encantó el video", "Reseña", 5);
	
		rev2 = new Review("2023-9-10", "Muy interesante", "Reseña", 4);

		
	}
	
	@AfterEach
	public void tearDown()
	{
		recurso = null;

		rev1= null;
		rev2 = null;
		
	}
	
	
	@Test
	public void testCompletarDescompletarActividad() {
		
		recurso.completarActividad();
		
		assertEquals("Exitosa", recurso.getEstado(), "No se marcó completada correctamente.");
		
		recurso.descompletarActividad();
		
		assertEquals("", recurso.getEstado(), "No se marcó descompletada correctamente.");

		
	}
	
	
	@Test
	public void testReviews() {
		
		List<Review> revs = new ArrayList<Review>();
		
		revs.add(rev1);
		revs.add(rev2);
		
		recurso.setReviews(revs);
		
		assertEquals(2, recurso.getReviews().size(), "No tiene las reviews esperadas");
		assertEquals(4.5, recurso.getRatingPromedio(), "No tiene el promedio esperado.");
		
		
		
	}
	

	
}
