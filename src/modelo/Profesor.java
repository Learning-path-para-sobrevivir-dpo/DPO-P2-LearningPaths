package modelo;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class Profesor extends Usuario{
	
	public Map<String, LearningPath> learningPathsCreados;
	public Map<String, Actividad> actCreadas;
	
	public Profesor(String login, String correo, String contraseña, String tipo) {
		super(login, correo, contraseña, tipo);
		learningPathsCreados = new HashMap<String,LearningPath>();
		actCreadas = new HashMap<String,Actividad>();
	}
	
	//public IActividad crearActividad() {}
	
	public void crearLearningPath(String titulo, String descripcion, String obj, int dificultad) {
		
        LocalDate fechaActual = LocalDate.now();
        String fecha = fechaActual.toString();
		
        LearningPath path = new LearningPath(titulo, descripcion, obj, dificultad, 0, fecha, fecha, 1, this);
        learningPathsCreados.put(titulo, path);
        
	}
	
	public void addActividadToLearningPath(LearningPath learningPath, Actividad actividad, int posicion) {
	    String titulo = learningPath.getTitulo();

	    if (learningPathsCreados.containsKey(titulo)) {
	        LearningPath path = learningPathsCreados.get(titulo);
	        if (posicion <= 0)
	        {
	        	path.addActividadDeUltimas(actividad); 
	        }
	        else
	        {
	        	path.addActividadPorPos(actividad, posicion);
	        }
	    } else {
	        // Lanzar excepción si el LearningPath no fue creado por este profesor
	        throw new IllegalArgumentException("LearningPath no encontrado: " + titulo);
	    }
	}

	
	
	
	
}
