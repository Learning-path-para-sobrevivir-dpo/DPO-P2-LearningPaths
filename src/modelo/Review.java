package modelo;

public class Review {
	
	public String fecha;
	public String contenido; 
	public float rating;
	public Usuario autor;
	
	public Review(String fecha, String contenido, Usuario autor) {
		super();
		this.fecha = fecha;
		this.contenido = contenido;
		this.rating = 0;
		this.autor = autor;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getContenido() {
		return contenido;
	}

	public void setContenido(String contenido) {
		this.contenido = contenido;
	}

	public float getRating() {
		return rating;
	}

	public void setRating(float rating) {
		this.rating = rating;
	}

	public Usuario getAutor() {
		return autor;
	}

	public void setAutor(Usuario autor) {
		this.autor = autor;
	} 

}
