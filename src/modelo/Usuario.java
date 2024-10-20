package modelo;

public abstract class Usuario {
	
	private String correo;
	private String contraseña;
	public String tipo;
	public static final String PROFESOR = "profesor";
	public static final String ESTUDIANTE = "estudiante";
	
	public Usuario(String login, String correo, String contraseña, String tipo) {
		this.login = login;
		this.correo = correo;
		this.contraseña = contraseña;
		this.tipo = tipo;
	}
	
	

}
