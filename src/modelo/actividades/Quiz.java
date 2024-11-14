package modelo.actividades;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import excepciones.RespuestasInconsistentesPruebaException;
import excepciones.TipoDePreguntaInvalidaException;
import modelo.Review;

public abstract class Quiz extends Prueba{

	public float calificacionMinima;
	
	public Quiz(String titulo, String objetivo, int nivelDificultad, int duracionMin, boolean obligatorio,
			int tiempoCompletarSugerido, String tipoActividad, float calificacionMinima, String tipoPrueba) {
		super(titulo, objetivo, nivelDificultad, duracionMin, obligatorio, tiempoCompletarSugerido, tipoActividad, tipoPrueba);
		this.calificacionMinima = calificacionMinima;
		this.setTipoActividad("Prueba");
	}
	
	public Quiz(String titulo, String descripcion, int nivelDificultad, int duracionMin, boolean obligatorio,
			int tiempoCompletarSugerido, String tipoActividad, float calificacionMinima, String tipoPrueba, String id, String idEstudiante) {
		super(titulo, descripcion, nivelDificultad, duracionMin, obligatorio, tiempoCompletarSugerido, tipoActividad, tipoPrueba, id, idEstudiante);
		this.calificacionMinima = calificacionMinima;
		this.setTipoActividad("Prueba");
	}
	
	public float getCalificacionMinima() {
		return calificacionMinima;
	}

	public void setCalificacionMinima(float calificacionMinima) {
		this.calificacionMinima = calificacionMinima;
	}
	
	public void setReviews(List<Review> listaReviews) {
		this.reviews= listaReviews; 	
	}

}
