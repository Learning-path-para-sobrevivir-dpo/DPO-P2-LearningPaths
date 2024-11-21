package tests;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import modelo.Review;
import modelo.actividades.RecursoEducativo;
import modelo.actividades.Tarea;


public class TestTarea {

	private Tarea tarea;
	private Review rev1;
	private Review rev2;
	
	
	
	@BeforeEach
	public void setUp() {
		
		tarea = new Tarea ("Tarea", "Practicar conocimientos", 1, 30, true,  360, "Tarea",  "Hacer 5 ejercicios.", "");
		rev1 = new Review("2023-10-11", "Me encantó el video", "Reseña", 5);
	
		rev2 = new Review("2023-9-10", "Muy interesante", "Reseña", 4);

		
	}
	
	@AfterEach
	public void tearDown()
	{
		tarea = null;

		rev1= null;
		rev2 = null;
		
	}
	
	
	@Test
	public void testCompletarDescompletarActividad() {
		
		tarea.setEnviado(true);
		boolean completada = tarea.completarActividad();
		
		assertEquals(true, completada, "No se marcó completada correctamente.");
		
		tarea.setEstado("No Exitosa");
		tarea.descompletarActividad();
		
		assertEquals(false, tarea.isEnviado(), "No se marcó descompletada correctamente.");

		
	}
	
	
	@Test
	public void testReviews() {
		
		List<Review> revs = new ArrayList<Review>();
		
		revs.add(rev1);
		revs.add(rev2);
		
		tarea.setReviews(revs);
		
		assertEquals(2, tarea.getReviews().size(), "No tiene las reviews esperadas");
		assertEquals(4.5, tarea.getRatingPromedio(), "No tiene el promedio esperado.");
		
		
		
	}
	
	
}
