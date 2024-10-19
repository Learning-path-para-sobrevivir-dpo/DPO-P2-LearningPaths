package modelo;

import java.util.List;

public class Encuesta extends Prueba {
	
	public List<Pregunta> preguntas;

	public Encuesta(String tipo, int calificacion, List<Pregunta> preguntas) {
		super(tipo, calificacion);
		this.preguntas = preguntas;
	}
	
	

}
