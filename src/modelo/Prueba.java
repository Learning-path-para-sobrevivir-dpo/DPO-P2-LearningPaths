package modelo;

public abstract class Prueba extends Actividad{

	private float calificacion;
	private boolean respondida;
	public String tipoPrueba;
	
	public Prueba(String titulo, String descripcion, int nivelDificultad, int duracionMin, boolean obligatorio,
			int tiempoCompletarSugerido, String tipoActividad, String tipoPrueba) {
		super(titulo, descripcion, nivelDificultad, duracionMin, obligatorio, tiempoCompletarSugerido, tipoActividad);
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
			if (this.getEstado().equals("Enviada") || this.getEstado().equals("Exitosa"))
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

	public abstract void calcularCalificacion();
	
	public abstract void responderPrueba();
}
