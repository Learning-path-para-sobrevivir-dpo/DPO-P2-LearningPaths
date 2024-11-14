package modelo.actividades;

public class PreguntaVerdaderoFalso extends Pregunta{
	
	private boolean respuestaCorrecta;
	private boolean opcionSeleccionada;

	public PreguntaVerdaderoFalso(String enunciado, boolean respuestaCorrecta) {
		super(enunciado);
		this.respuestaCorrecta = respuestaCorrecta;
		this.opcionSeleccionada = false;
		this.setTipo("Pregunta Verdadero Falso");
	}
	
	public PreguntaVerdaderoFalso(String enunciado) {
		super(enunciado);
		this.opcionSeleccionada = false;
		this.setTipo("Pregunta Verdadero Falso");
		this.setNumero(0);
	}

	
	public PreguntaVerdaderoFalso(String enunciado, int numero) {
		super(enunciado, numero);
		this.opcionSeleccionada = false;
		this.setTipo("Pregunta Verdadero Falso");
	}

	public boolean isRespuestaCorrecta() {
		return respuestaCorrecta;
	}

	public void setRespuestaCorrecta(boolean respuestaCorrecta) {
		this.respuestaCorrecta = respuestaCorrecta;
	}

	public boolean isOpcionSeleccionada() {
		return opcionSeleccionada;
	}

	public void setOpcionSeleccionada(boolean opcionSeleccionada) {
		this.opcionSeleccionada = opcionSeleccionada;
	}

}