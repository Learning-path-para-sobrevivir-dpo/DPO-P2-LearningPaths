package modelo;

public class PreguntaAbierta extends Pregunta {

	private String respuesta;
	private boolean correcta;
	
	public PreguntaAbierta(String enunciado) {
		super(enunciado);
		// TODO Auto-generated constructor stub
		respuesta = null;
		correcta = false;
	}

	public String getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}

	public boolean isCorrecta() {
		return correcta;
	}

	public void setCorrecta(boolean correcta) {
		this.correcta = correcta;
	}
}