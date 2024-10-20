package modelo;

import java.util.Map;

public class Profesor extends Usuario{
	
	public Map<String, LearningPath> LearningPathsCreados;
	public Map<String, Actividad> ActCreadas;
	
	public Profesor(String login, String correo, String contraseña, String tipo,
			Map<String, LearningPath> learningPathsCreados, Map<String, Actividad> actCreadas) {
		super(login, correo, contraseña, tipo);
		LearningPathsCreados = learningPathsCreados;
		ActCreadas = actCreadas;
	}
	

	
}
