package modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public abstract class Usuario {
	
	private String login;
	private String correo;
	private String contraseña;
	public String tipo;
	public List<Review> reviews;
	public static final String PROFESOR = "profesor";
	public static final String ESTUDIANTE = "estudiante";
	
	public Usuario(String login, String correo, String contraseña, String tipo) {
		this.login = login;
		this.correo = correo;
		this.contraseña = contraseña;
		this.reviews = new ArrayList<Review>();
		if (tipo.toLowerCase().equals(PROFESOR))
		{
			this.tipo = PROFESOR;
		} else if (tipo.toLowerCase().equals(ESTUDIANTE))
		{
			this.tipo = ESTUDIANTE;
		}
		
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getContraseña() {
		return contraseña;
	}

	public void setContraseña(String contraseña) {
		this.contraseña = contraseña;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	public void addReview(String contenido, Actividad actividad) {
		
        LocalDate fechaActual = LocalDate.now();
        String fecha = fechaActual.toString();
        
		Review review = new Review(fecha, contenido, this);
		
		reviews.add(review);
		
		//Buscar actividad y añadir la reseña a la reseña a la respectiva actividad.
		
	}

}
