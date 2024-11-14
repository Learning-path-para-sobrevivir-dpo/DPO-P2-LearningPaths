package excepciones;

import modelo.actividades.Actividad;

/**
 * Esta excepción se utiliza cuando el programa, por alguna razón, deja al estudiante
 * iniciar con una nueva actividad sin antes marcar como completada la actividad que tiene 
 * en progreso
 */
@SuppressWarnings("serial")
public class YaExisteActividadEnProgresoException extends Exception {
	Actividad actividadExistente;

	public YaExisteActividadEnProgresoException(Actividad actividadExistente) {
		super("");
		this.actividadExistente = actividadExistente;
	}
	
	@Override
	public String getMessage( )
	{
		return "Se intento empezar una nueva actividad sin antes completar la actividad previa: '"+ actividadExistente.getTitulo() + "'";
	}
}
