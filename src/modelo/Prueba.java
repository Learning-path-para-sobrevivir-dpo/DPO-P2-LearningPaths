package modelo;

public abstract class Prueba implements IActividad{

	public String tipo;
	private int calificacion;
	
	public Prueba(String tipo, int calificacion) {
		this.tipo = tipo;
		this.calificacion = calificacion;
	}
	
	
}
