package modelo;

public class Review {
	
	public String fecha;
	public String contenido; 
	public float rating;
	public String tipo; //si es de profesor o estudiante
	
	public Review(String fecha, String contenido, String tipo) {
		super();
		this.fecha = fecha;
		this.contenido = contenido;
		this.rating = 0;
		this.tipo = tipo;
	}
	
	public Review(String fecha, String contenido, int rating, Usuario autor) {
		super();
		this.fecha = fecha;
		this.contenido = contenido;
		this.rating = rating;
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
	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

}
