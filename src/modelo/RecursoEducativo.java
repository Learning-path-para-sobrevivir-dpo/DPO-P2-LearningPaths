package modelo;

public class RecursoEducativo implements IActividad {
	
	public String tipo;
	protected String enlace;
	
	public RecursoEducativo(String tipo, String enlace) {
		this.tipo = tipo;
		this.enlace = enlace;
	}

}
