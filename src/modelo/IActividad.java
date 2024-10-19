package modelo;

import java.util.List;

public interface IActividad {
	
	public String descripcion;
	public int nivelDificultad;
	public int duracionMin;
	public boolean obligatorio;
	public int tiempoCompletarSugerido;
	public int ratingPromedio;
	public static final int FACIL = 1;
	public static final int INTERMEDIO = 2;
	public static final int DIFICIL = 3;
	public List<IActividad> actPreviasSugeridas;
	public List<Review> reviews;
	
	
	

}
