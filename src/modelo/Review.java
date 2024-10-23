package modelo;

public class Review {
	
	public String fecha;
	public String contenido; 
	public float rating;
	//Galarza: borre autor porque no lo considero necesrio
	public String tipo;
	
	public Review(String fecha, String contenido, String tipo) {
		super();
		this.fecha = fecha;
		this.contenido = contenido;
		this.rating = 0;
		//this.autor = autor;
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
