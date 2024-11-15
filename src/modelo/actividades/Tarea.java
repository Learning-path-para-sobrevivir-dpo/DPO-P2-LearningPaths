package modelo.actividades;

import java.util.List;

import modelo.Review;

public class Tarea extends Actividad{
	
	private String medioEntrega;
	private String contenido;
	private boolean enviado;

	public Tarea(String titulo, String objetivo, int nivelDificultad, int duracionMin, boolean obligatorio,
			int tiempoCompletarSugerido, String tipo, String contenido) {
		super(titulo, objetivo, nivelDificultad, duracionMin, obligatorio, tiempoCompletarSugerido, tipo);
		this.contenido = contenido;
		this.enviado = false;
		this.setTipoActividad("Tarea");
	}
	
	public Tarea(String titulo, String descripcion, int nivelDificultad, int duracionMin, boolean obligatorio,
			int tiempoCompletarSugerido, String tipo, String contenido, String id, String idEstudiante) {
		super(titulo, descripcion, nivelDificultad, duracionMin, obligatorio, tiempoCompletarSugerido, tipo, id, idEstudiante);
		this.contenido = contenido;
		this.enviado = false;
		this.setTipoActividad("Tarea");
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

	@Override
	public boolean completarActividad() {
		// TODO Auto-generated method stub
		boolean completada = false;
		if (this.isEnviado() || this.getEstado().equals("Exitosa"))
		{
			this.setCompletada(true);
			completada = true;
			this.setEnviado(true);
		}
		return completada;
	}

	@Override
	public void descompletarActividad() {
		// TODO Auto-generated method stub
		if (this.getEstado().equals("No Exitosa"))
		{
			this.setCompletada(false);
			this.setEnviado(false);
		}
	}
	
	public void setReviews(List<Review> listaReviews) {
		this.reviews= listaReviews; 	
	}
}
