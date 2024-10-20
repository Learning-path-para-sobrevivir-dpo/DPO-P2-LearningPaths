package modelo;

public abstract class Prueba extends Actividad{

	public String tipo;
	private int calificacion;
	
	public Prueba(String titulo, String descripcion, int nivelDificultad, int duracionMin, boolean obligatorio,
			int tiempoCompletarSugerido) {
		super(titulo, descripcion, nivelDificultad, duracionMin, obligatorio, tiempoCompletarSugerido);
		// TODO Auto-generated constructor stub
	}
}
