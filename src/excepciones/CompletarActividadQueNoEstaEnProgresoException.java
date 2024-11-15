package excepciones;

import modelo.actividades.Actividad;

@SuppressWarnings("serial")
public class CompletarActividadQueNoEstaEnProgresoException extends Exception {
	private Actividad actividadSinEmpezar;
	
	public CompletarActividadQueNoEstaEnProgresoException(Actividad actividadSinEmpezar) {
		super("");
		this.actividadSinEmpezar = actividadSinEmpezar;
	}
	
	@Override
	public String getMessage( )
	{
		return "Se intento completar la actividad '"+ actividadSinEmpezar.getTitulo() +"' sin antes empezarla";
	}
}
