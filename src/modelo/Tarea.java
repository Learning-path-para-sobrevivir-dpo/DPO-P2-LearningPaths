package modelo;

public class Tarea extends Actividad{
	
	private String medioEntrega;
	private String contenido;
	private boolean enviado;

	public Tarea(String titulo, String descripcion, int nivelDificultad, int duracionMin, boolean obligatorio,
			int tiempoCompletarSugerido, String tipo, String contenido) {
		super(titulo, descripcion, nivelDificultad, duracionMin, obligatorio, tiempoCompletarSugerido, tipo);
		this.contenido = contenido;
		this.enviado = false;
	}

	public String getMedioEntrega() {
		return medioEntrega;
	}

	public void setMedioEntrega(String medioEntrega) {
		this.medioEntrega = medioEntrega;
	}

	public String getContenido() {
		return contenido;
	}

	public void setContenido(String contenido) {
		this.contenido = contenido;
	}
	
	//Solo se deben usar para las copias de actividades dentro
	//de los perfiles de los estudiantes
	public boolean isEnviado() {
		return enviado;
	}

	public void setEnviado(boolean enviado) {
		this.enviado = enviado;
	}
}
