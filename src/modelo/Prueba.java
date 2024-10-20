package modelo;

public abstract class Prueba extends Actividad{

	private float calificacion;
	private boolean respondida;
	
	public Prueba(String titulo, String descripcion, int nivelDificultad, int duracionMin, boolean obligatorio,
			int tiempoCompletarSugerido, String tipoActividad) {
		super(titulo, descripcion, nivelDificultad, duracionMin, obligatorio, tiempoCompletarSugerido, tipoActividad);
		// TODO Auto-generated constructor stub
		this.calificacion = 0;
		this.respondida = false;
	}
		
	public float getCalificacion() {
		return calificacion;
	}

	public void setCalificacion(float calificacion) {
		this.calificacion = calificacion;
	}

	public boolean isRespondida() {
		return respondida;
	}

	public void setRespondida(boolean respondida) {
		this.respondida = respondida;
	}

	public abstract void calcularCalificacion();
}
