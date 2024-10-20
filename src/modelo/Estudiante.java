package modelo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Estudiante extends Usuario {
	
	private Map<String, Progreso> progresosLearningPaths;
private Actividad actProgreso;
	private List<Actividad> actPendientes;
	private List<Actividad> actCompletadas;
	private Map<String, LearningPath> LearningPaths;

	public Estudiante(String login, String correo, String contraseña, String tipo, Map<String, Progreso> progresosLP,
			Actividad actProgreso, List<Actividad> actPendientes, List<Actividad> actCompletadas,
			Map<String, LearningPath> learningPaths) {
		super(login, correo, contraseña, tipo);
		this.progresosLearningPaths = new HashMap<String, Progreso>();
		this.actProgreso = actProgreso;
		this.actPendientes = new ArrayList<IActividad>();
		this.actCompletadas = new ArrayList<IActividad>();
		this.learningPaths = new HashMap<String, LearningPath>();
	}
	
    public void iniciarActividad(IActividad nuevaActividad) {
  
        if (actProgreso == null) {
            if (actPendientes.contains(nuevaActividad)) {
                actProgreso = nuevaActividad;
                actPendientes.remove(nuevaActividad);
            }
        } else {
            throw new IllegalStateException("Ya hay una actividad en progreso: " + actProgreso.getTitulo());
        }
    }
	
    public void completarActividad() {
        if (actProgreso != null) {
            actCompletadas.add(actProgreso);  
            actProgreso = null;  
        } else {
            // Cuando no hay act en progreso
            System.out.println("No hay actividad en progreso para completar.");
        }
    }
	
    
    public void inscribirLearningPath(LearningPath nuevoLP) {
        String titulo = nuevoLP.getTitulo();

        if (learningPaths.containsKey(titulo)) {
            throw new IllegalStateException("Ya está inscrito en" + titulo);
        }
        //Si aún no está inscrito
        learningPaths.put(titulo, nuevoLP);

        for (IActividad actividad : nuevoLP.getActividades().values()) {
            if (!actCompletadas.contains(actividad)) {
                actPendientes.add(actividad);
            }
        }
    }
}
