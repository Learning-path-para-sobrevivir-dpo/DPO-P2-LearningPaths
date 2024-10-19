package modelo;

import java.util.List;

public class PreguntaMultiple extends Pregunta{

	public List<String> opciones;
	private String opcionCorrecta;
	
	public PreguntaMultiple(String enunciado, List<String> opciones, String opcionCorrecta) {
		super(enunciado);
		this.opciones = opciones;
		this.opcionCorrecta = opcionCorrecta;
	}
	
	
}
