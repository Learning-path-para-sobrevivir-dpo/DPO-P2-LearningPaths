package modelo;

public class Tarea extends Actividad{
	
	private boolean exito;
	public String medioEntrega;
	
	public Tarea(boolean exito, String medioEntrega) {
		this.exito = exito;
		this.medioEntrega = medioEntrega;
	}

}
