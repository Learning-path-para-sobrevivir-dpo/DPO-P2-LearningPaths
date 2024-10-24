package modelo;

import java.util.List;

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

	public String getTipoRecurso() {
		return tipoRecurso;
	}

	public void setTipoRecurso(String tipoRecurso) {
		this.tipoRecurso = tipoRecurso;
	}

	public String getContenido() {
		return contenido;
	}

	public void setContenido(String contenido) {
		this.contenido = contenido;
	}

	public String getEnlace() {
		return enlace;
	}

	public void setEnlace(String enlace) {
		this.enlace = enlace;
	}

	@Override
	public boolean completarActividad() {
		// TODO Auto-generated method stub
		boolean completada = true;
		this.setCompletada(completada);
		this.setEstado("Exitosa");
		return completada;
	}

	@Override
	public void descompletarActividad() {
		// TODO Auto-generated method stub
		this.setCompletada(false);
		this.setEstado("");
	}
	
	public void setReviews(List<Review> listaReviews) {
		this.reviews= listaReviews; 	
	}
	

}
