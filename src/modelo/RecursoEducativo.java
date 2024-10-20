package modelo;

public class RecursoEducativo extends Actividad {
	
	private String tipoRecurso;
	private String contenido;
	private String enlace;
	
	
	public RecursoEducativo(String titulo, String descripcion, int nivelDificultad, int duracionMin,
			boolean obligatorio, int tiempoCompletarSugerido, String tipoActividad, String tipoRecurso, String contenido, String enlace) {
		super(titulo, descripcion, nivelDificultad, duracionMin, obligatorio, tiempoCompletarSugerido, tipoActividad);
		this.tipoRecurso = tipoRecurso;
		this.contenido = contenido;
		this.enlace = enlace;
	}

	public RecursoEducativo(String titulo, String descripcion, int nivelDificultad, int duracionMin,
			boolean obligatorio, int tiempoCompletarSugerido, String tipoActividad, String tipoRecurso, String contenido) {
		super(titulo, descripcion, nivelDificultad, duracionMin, obligatorio, tiempoCompletarSugerido, tipoActividad);
		this.tipoRecurso = tipoRecurso;
		this.contenido = contenido;
		this.enlace = null;
	}
	
	

}
