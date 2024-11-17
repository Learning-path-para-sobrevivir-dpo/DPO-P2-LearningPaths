package modelo.actividades;

import java.util.List;

import excepciones.TipoDePreguntaInvalidaException;

public abstract class Prueba extends Actividad{

	private float calificacion;
	private boolean respondida;
	public String tipoPrueba;
	
	public Prueba(String titulo, String objetivo, int nivelDificultad, int duracionMin, boolean obligatorio,
			int tiempoCompletarSugerido, String tipoActividad, String tipoPrueba) {
		super(titulo, objetivo, nivelDificultad, duracionMin, obligatorio, tiempoCompletarSugerido, tipoActividad);
		// TODO Auto-generated constructor stub
		this.calificacion = 0;
		this.respondida = false;
		this.tipoPrueba = tipoPrueba;
	}
	
	public Prueba(String titulo, String descripcion, int nivelDificultad, int duracionMin, boolean obligatorio,
			int tiempoCompletarSugerido, String tipoActividad, String tipoPrueba, String id, String idEstudiante) {
		super(titulo, descripcion, nivelDificultad, duracionMin, obligatorio, tiempoCompletarSugerido, tipoActividad, id, idEstudiante);
		// TODO Auto-generated constructor stub
		this.calificacion = 0;
		this.respondida = false;
		this.tipoPrueba = tipoPrueba;
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
	
	public String getTipoPrueba() {
		return tipoPrueba;
	}

	public void setTipoPrueba(String tipoPrueba) {
		this.tipoPrueba = tipoPrueba;
	}
	
	@Override
	public boolean completarActividad() {
		boolean completada = false;
		if (this.respondida)
		{
			if (this instanceof Quiz)
			{
				Quiz q = (Quiz) this;
				if (q.getCalificacion() >= q.getCalificacionMinima() && (this.isRespondida() || this.getEstado().equals("Exitosa")))
				{
					this.setCompletada(true);
					completada = true;
				}
			}
			else if (this.isRespondida() || this.getEstado().equals("Exitosa"))
			{
				this.setCompletada(true);
				completada = true;
			}
		}
		return completada;
	}
	
	@Override
	public void descompletarActividad() {
		if (this.getEstado().equals("No Exitosa"))
		{
			this.setCompletada(false);
		}
	}
	
	/**
	 * Añade una pregunta a la prueba
	 * @param pregunta pregunta a añadir
	 * @throws TipoDePreguntaInvalidaException se lanza si el tipo de pregunta
	 * a añadir no es el tipo esperado para la prueba
	 */
	public abstract void addPregunta(Pregunta pregunta) throws TipoDePreguntaInvalidaException;
	
	/**
	 * Elimina una pregunta por su número/posicion. Si numPregunta es mayor
	 * o es menor o igual a 0, no se elimina nada
	 * @param numPregunta número de la pregunta a eliminar
	 */
	public abstract void eliminarPregunta(int numPregunta);

	public abstract void calcularCalificacion();

}
