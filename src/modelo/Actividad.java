package modelo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
	private String id;
	private String tipoActividad;
	private String estado;
	
	//Un HashSet con todos los IDs que ya se han utilizado para actividades
	private static Set<String> ids = new HashSet<String>( );
	
	public static final int FACIL = 1;
	public static final int INTERMEDIO = 2;
	public static final int DIFICIL = 3;
	
	public Actividad(String titulo, String descripcion, int nivelDificultad, int duracionMin, boolean obligatorio,
			int tiempoCompletarSugerido, String tipo) {
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
		this.setTipoActividad(tipo);
		this.estado = "Sin completar";
		
		//Para crear un identificador unico para la actividad
		int numero = ( int ) ( Math.random( ) * 10e7 );
        String codigo = "" + numero;
        while( ids.contains( codigo ) )
        {
            numero = ( int ) ( Math.random( ) * 10e7 );
            codigo = "" + numero;
        }

        while( codigo.length( ) < 7 )
            codigo = "0" + codigo;
        
        this.id = codigo;
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
	

	public String getId() {
		return id;
	}
	
	public String getTipoActividad() {
		return tipoActividad;
	}

	public void setTipoActividad(String tipoActividad) {
		this.tipoActividad = tipoActividad;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public void addActividadPrevia(Actividad actividadPrevia)
	{
		if (actividadPrevia != null)
		{
			this.actPreviasSugeridas.add(actividadPrevia);
		}
	}
	
	/**
	 * Registra los IDs usados cuando se cargan los datos en ManejoDeDatos
	 * @param actividad: actividad cargada
	 */
	public static void registrarIDActividad(Actividad actividad)
	{
		String unID = actividad.getId();
		ids.add(unID);
	}
	
	/**
	 * Añade un review a la actividad y actualiza el rating promedio de la actividad
	 * @param review: review a agregar
	 */
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
	
	/**
	 * Actualiza el rating promedio cuando un usuario le da un rating a una actividad
	 * @param rating: rating dado
	 */
	public void addRating(float rating)
	{
		this.ratingAcumulado += rating;
		this.numRatings +=1;
		this.calcularRatingPromedio();
	}
	
	/**
	 * Elimina un review de la lista de reviews de la actividad. Actualiza el rating promedio
	 * @param review: review a eliminar
	 */
	public void eliminarReview(Review review)
	{
		if (review != null)
		{
			int i = 0;
			boolean eliminado = false;
			int tamanio = this.reviews.size();
			Review current;
			while (!eliminado && i < tamanio)
			{
				current = this.reviews.get(i);
				if (current.equals(review))
				{
					this.reviews.remove(i);
					this.numRatings--;
					this.ratingAcumulado -= review.getRating();
					this.calcularRatingPromedio();
					eliminado = true;
				}
				i++;
			}
		}
	}
	
	/**
	 * Funcion para calcular el rating promedio de la actividad en base a
	 * los reviews y ratings dejados por usuarios
	 */
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