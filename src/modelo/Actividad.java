package modelo;

import java.util.ArrayList;
import java.util.List;

public class Actividad implements Cloneable {
	private String titulo;
	private String descripcion;
	private int nivelDificultad;
	private int duracionMin;
	private boolean obligatorio;
	private int tiempoCompletarSugerido;
	private float ratingPromedio;
	private List<Actividad> actPreviasSugeridas;
	private List<Review> reviews;
	private float ratingAcumulado;
	private int numRatings;
	
	public static final int FACIL = 1;
	public static final int INTERMEDIO = 2;
	public static final int DIFICIL = 3;
	
	public Actividad(String titulo, String descripcion, int nivelDificultad, int duracionMin, boolean obligatorio,
			int tiempoCompletarSugerido) {
		super();
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.nivelDificultad = nivelDificultad;
		this.duracionMin = duracionMin;
		this.obligatorio = obligatorio;
		this.tiempoCompletarSugerido = tiempoCompletarSugerido;
		this.actPreviasSugeridas = new ArrayList<Actividad>();
		this.reviews = new ArrayList<Review>();
		this.ratingAcumulado = 0;
		this.ratingPromedio = 0;
		this.numRatings = 0;
	}
	
	public Actividad(String descripcion, String nivelDificultad, int duracionMin, boolean obligatorio,
			int tiempoCompletarSugerido) {
		super();
		this.descripcion = descripcion;	
		this.duracionMin = duracionMin;
		this.obligatorio = obligatorio;
		this.tiempoCompletarSugerido = tiempoCompletarSugerido;
		this.actPreviasSugeridas = new ArrayList<Actividad>();
		this.reviews = new ArrayList<Review>();
		
		if (nivelDificultad != null)
		{
			if (nivelDificultad.toLowerCase().equals("dificil"))
			{
				this.nivelDificultad = DIFICIL;
			} else if (nivelDificultad.toLowerCase().equals("intermedio"))
			{
				this.nivelDificultad = INTERMEDIO;
			} else
			{
				this.nivelDificultad = FACIL;
			}
		}
	}
	
	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public int getNivelDificultad() {
		return nivelDificultad;
	}

	public void setNivelDificultad(int nivelDificultad) {
		this.nivelDificultad = nivelDificultad;
	}

	public int getDuracionMin() {
		return duracionMin;
	}

	public void setDuracionMin(int duracionMin) {
		this.duracionMin = duracionMin;
	}

	public boolean isObligatorio() {
		return obligatorio;
	}

	public void setObligatorio(boolean obligatorio) {
		this.obligatorio = obligatorio;
	}

	public int getTiempoCompletarSugerido() {
		return tiempoCompletarSugerido;
	}

	public void setTiempoCompletarSugerido(int tiempoCompletarSugerido) {
		this.tiempoCompletarSugerido = tiempoCompletarSugerido;
	}

	public float getRatingPromedio() {
		return ratingPromedio;
	}

	public void setRatingPromedio(float ratingPromedio) {
		this.ratingPromedio = ratingPromedio;
	}

	public List<Actividad> getActPreviasSugeridas() {
		return actPreviasSugeridas;
	}

	public List<Review> getReviews() {
		return reviews;
	}

	public void addActividadPrevia(Actividad actividadPrevia)
	{
		if (actividadPrevia != null)
		{
			this.actPreviasSugeridas.add(actividadPrevia);
		}
	}
	
	public void addReview(Review review)
	{
		if (review != null)
		{
			this.reviews.add(review);
			this.ratingAcumulado += review.getRating();
			this.numRatings +=1;
			this.calcularRatingPromedio();
		}
	}
	
	public void addRating(float rating)
	{
		this.ratingAcumulado += rating;
		this.numRatings +=1;
		this.calcularRatingPromedio();
	}
	
	private void calcularRatingPromedio()
	{
		if (this.numRatings != 0)
		{
			this.ratingPromedio = this.ratingAcumulado / this.numRatings;
		}
	}
	
	@Override
	protected Object clone() throws CloneNotSupportedException
	{
		return super.clone();
	}

}
