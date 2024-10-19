package modelo;

import java.util.List;

public class Quiz extends Prueba{

	public int calificacionMinima;
	public List<PreguntaMultiple> preguntas;
	
	public Quiz(String tipo, int calificacion, int calificacionMinima, List<PreguntaMultiple> preguntas) {
		super(tipo, calificacion);
		this.calificacionMinima = calificacionMinima;
		this.preguntas = preguntas;
	}
	
}
