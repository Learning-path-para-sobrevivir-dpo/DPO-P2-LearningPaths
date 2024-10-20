package modelo;

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
		this.progresosLearningPaths = progresosLP;
		this.actProgreso = actProgreso;
		this.actPendientes = actPendientes;
		this.actCompletadas = actCompletadas;
		LearningPaths = learningPaths;
	}
	
	

}
