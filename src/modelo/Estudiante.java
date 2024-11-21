package modelo;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import excepciones.YaExisteActividadEnProgresoException;
import modelo.actividades.Actividad;
import modelo.actividades.Prueba;
import modelo.actividades.Tarea;

public class Estudiante extends Usuario {
	
	private Map<String, Progreso> progresosLearningPaths;
	private Map<String, LearningPath> learningPaths;

	public Estudiante(String login, String correo, String contraseña, String tipo) {
		super(login, correo, contraseña, tipo);
		this.progresosLearningPaths = new HashMap<String, Progreso>();
		this.learningPaths = new HashMap<String, LearningPath>();
	}
	
	/**
	 * Inscribe a un estudiante a un Learning Path. La función crea un progreso
	 * nuevo del estudiante para el Learning Path inscrito y lo guarda en el 
	 * mapa de progresos por el título del Learning Path
	 * @param nuevoLP: Learning path a inscribir en el perfil del estudiante
	 */
	public Progreso inscribirLearningPath(LearningPath nuevoLP) {
        String titulo = nuevoLP.getTitulo();

        if (learningPaths.containsKey(titulo)) {
            throw new IllegalStateException("Ya está inscrito en " + titulo);
        }
        //Si aún no está inscrito
        learningPaths.put(titulo, nuevoLP);
        
        Progreso newProgreso = new Progreso(titulo, this.getLogin());//Galarza: cambie nuevoLp por titulo, y agregue el getLogic() para que en el progreso guarde el usuario
        this.progresosLearningPaths.put(titulo, newProgreso);
        nuevoLP.addProgresoEstudiante(newProgreso);
        return newProgreso;
    }
	
	/**
	 * Obtiene una actividad específica de un Learning Path dado su número
	 * @param nombrePath: nombre del Learning Path
	 * @param numActividad: número de la actividad
	 * @return La actividad buscada. Si no se encuentra la actividad o si el 
	 * estudiante no tiene inscrito el Learning Path buscado, se retorna null.
	 */
	public Actividad obtenerActividadDePath(String nombrePath, int numActividad) {
		if (!this.progresosLearningPaths.containsKey(nombrePath))
		{
			return null;
		}
		Progreso progreso = this.progresosLearningPaths.get(nombrePath);
		Actividad actividad = progreso.obtenerActividadPorNum(numActividad);
		return actividad;
	}
	
	/**
	 * Inicia una actividad dado un número de actividad y el nombre de un Learning Path
	 * @param numActividad: número de la actividad a empezar
	 * @param nombrePath: nombre del Learning Path en donde esta la actividad
	 * @return True si se inicio la actividad con exito o False si no se pudo iniciar la actividad
	 */
    public boolean iniciarActividad(int numActividad, String nombrePath) {
    	boolean inicioExitoso = false;
    	if (this.progresosLearningPaths.containsKey(nombrePath))
    	{
    		Progreso progreso = this.progresosLearningPaths.get(nombrePath);
    		Actividad actividad = obtenerActividadDePath(nombrePath, numActividad);
    		if (actividad != null && progreso.getActividadEnProgreso() == null)
    		{
    			try {
					progreso.empezarActividad(actividad);
					inicioExitoso = true;
				} catch (YaExisteActividadEnProgresoException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    		}
    	}
    	return inicioExitoso;    	
    }
	
    /**
     * Marca como completada una actividad en progreso dado un número de actividad y el nombre de un Learning Path
     * @param numActividad: número de la actividad a completar
     * @param nombrePath: nombre del Learning Path
     * @return True si se completo con exito la actividad, False de lo contrario
     */
    public boolean completarActividad(int numActividad, String nombrePath) {
    	boolean completada = false;
    	Actividad actividad = this.obtenerActividadDePath(nombrePath, numActividad);
    	if (actividad == null)
        {
        	return completada;
        }
        String tipoActividad = actividad.getTipoActividad();
        Progreso progreso = this.progresosLearningPaths.get(nombrePath);
        if (tipoActividad.equals("Prueba"))
        {
        	Prueba prueba = (Prueba) actividad;
        	if (prueba.isRespondida())
        	{
        		completada = true;
        	}
        } 
        else if (tipoActividad.equals("Tarea"))
        {
			Tarea tarea = (Tarea) actividad;
			if (tarea.isEnviado() && tarea.getMedioEntrega() != null)
			{
				completada = true;
			}
        }
        else if (tipoActividad.equals("Recurso Educativo"))
        {
        	completada = true;
        }
        
        try {
        	completada = progreso.completarActividad(actividad);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return completada;
    }
    
    public boolean completarActividadA(Actividad actividad, String nombrePath) {
    	boolean completada = false;
    	if (actividad == null)
        {
        	return completada;
        }
        String tipoActividad = actividad.getTipoActividad();
        Progreso progreso = this.progresosLearningPaths.get(nombrePath);
        if (tipoActividad.equals("Prueba"))
        {
        	Prueba prueba = (Prueba) actividad;
        	if (prueba.isRespondida())
        	{
        		completada = true;
        	}
        } 
        else if (tipoActividad.equals("Tarea"))
        {
			Tarea tarea = (Tarea) actividad;
			if (tarea.isEnviado() && tarea.getMedioEntrega() != null)
			{
				completada = true;
			}
        }
        else if (tipoActividad.equals("Recurso Educativo"))
        {
        	completada = true;
        }
        
        try {
        	completada = progreso.completarActividad(actividad);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return completada;
    }

	public Map<String, Progreso> getProgresosLearningPaths() {
		return this.progresosLearningPaths;
	}

	public Map<String, LearningPath> getLearningPaths() {
		return this.learningPaths;
	}
}
