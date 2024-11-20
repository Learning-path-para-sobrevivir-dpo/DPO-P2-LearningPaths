package modelo.actividades;

import java.util.List;

import modelo.Review;

public class RecursoEducativo extends Actividad {
	
	private String tipoRecurso;
	private String contenido;
	private String enlace;
	
	
	public RecursoEducativo(String titulo, String objetivo, int nivelDificultad, int duracionMin,
			boolean obligatorio, int tiempoCompletarSugerido, String tipoActividad, String tipoRecurso, String contenido, String enlace) {
		super(titulo, objetivo, nivelDificultad, duracionMin, obligatorio, tiempoCompletarSugerido, tipoActividad);
		this.tipoRecurso = tipoRecurso;
		this.contenido = contenido;
		this.enlace = enlace;
		this.setTipoActividad("Recurso Educativo");
	}
	
	public RecursoEducativo(String titulo, String descripcion, int nivelDificultad, int duracionMin,
			boolean obligatorio, int tiempoCompletarSugerido, String tipoActividad, String tipoRecurso, String contenido, String enlace, String id, String idEstudiante) {
		super(titulo, descripcion, nivelDificultad, duracionMin, obligatorio, tiempoCompletarSugerido, tipoActividad, id, idEstudiante);
		this.tipoRecurso = tipoRecurso;
		this.contenido = contenido;
		this.enlace = enlace;
		this.setTipoActividad("Recurso Educativo");
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
		for (Review rev: listaReviews) {
			if (rev.getRating()>0) {
				this.addRating(rev.getRating());
			}
			
		}
		super.calcularRatingPromedio();
	}
	

}
