package modelo;

import java.util.List;
import java.util.Map;

public class Estudiante extends Usuario {
	
	private Map<String, Progreso> progresosLearningPaths;
	private IActividad actProgreso;
	private List<IActividad> actPendientes;
	private List<IActividad> actCompletadas;
	private Map<String, LearningPath> LearningPaths;

	public Estudiante(String login, String correo, String contraseña, String tipo, Map<String, Progreso> progresosLP,
			IActividad actProgreso, List<IActividad> actPendientes, List<IActividad> actCompletadas,
			Map<String, LearningPath> learningPaths) {
		super(login, correo, contraseña, tipo);
		this.progresosLearningPaths = progresosLP;
		this.actProgreso = actProgreso;
		this.actPendientes = actPendientes;
		this.actCompletadas = actCompletadas;
		LearningPaths = learningPaths;
	}
	
	

}
