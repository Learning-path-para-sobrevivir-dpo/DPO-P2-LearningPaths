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
	
	

}
