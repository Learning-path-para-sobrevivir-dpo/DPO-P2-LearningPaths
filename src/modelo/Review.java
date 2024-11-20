package modelo;

public class Review {
	
	public String fecha;
	public String contenido; 
	public double rating;
	public String tipo;
	
	public Review(String fecha, String contenido, String tipo) {
		super();
		this.fecha = fecha;
		this.contenido = contenido;
		this.rating = 0;
		this.tipo = tipo;
	}
	
	public Review(String fecha, String contenido, String tipo, double rating ) {
		super();
		this.fecha = fecha;
		this.contenido = contenido;
		this.rating = rating;
		this.tipo = tipo;
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


	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	

}
