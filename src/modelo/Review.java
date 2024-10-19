package modelo;

public class Review {
	
	public String fecha;
	public String contenido; 
	public int rating;
	public Usuario autor;
	
	public Review(String fecha, String contenido, int rating, Usuario autor) {
		super();
		this.fecha = fecha;
		this.contenido = contenido;
		this.rating = rating;
		this.autor = autor;
	} 
	
	

}
