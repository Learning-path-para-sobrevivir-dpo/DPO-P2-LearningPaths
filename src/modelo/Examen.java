package modelo;

import java.util.List;

public class Examen extends Prueba{

	public List<Pregunta> preguntas;

	public Examen(String tipo, int calificacion, List<Pregunta> preguntas) {
		super(tipo, calificacion);
		this.preguntas = preguntas;
	}
	
}
