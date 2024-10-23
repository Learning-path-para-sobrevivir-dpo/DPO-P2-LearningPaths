package modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Profesor extends Usuario{
	
	public Map<String, LearningPath> learningPathsCreados;
	public List<Actividad> actCreadas;
	
	public Profesor(String login, String correo, String contraseña, String tipo) {
		super(login, correo, contraseña, tipo);
		learningPathsCreados = new HashMap<String,LearningPath>();
		actCreadas = new ArrayList<Actividad>();
	}
	

	/**
	 * Método crea una nueva actividad. Se debe buscar y actuar diferente segun el tipo de 
	 * actividad a crear dado que Actividad es una clase abstracta.
	 */
	public Actividad crearActividad(String titulo, String descripcion, int nivelDificultad, int duracionMin, boolean obligatorio,
	        int tiempoCompletarSugerido, String tipo) {

		Actividad newAct = null;
		
	    // Usar Scanner para recibir entradas del usuario
	    Scanner scanner = new Scanner(System.in);

	    if (tipo.equals("Recurso Educativo")) {
	        System.out.print("Ingrese el tipo de recurso: ");
	        String tipoRecurso = scanner.nextLine();

	        System.out.print("Ingrese el contenido del recurso: ");
	        String contenido = scanner.nextLine();

	        System.out.print("Ingrese el enlace del recurso: ");
	        String enlace = scanner.nextLine();

	        newAct = new RecursoEducativo(titulo, descripcion, nivelDificultad, duracionMin, obligatorio, 
	                tiempoCompletarSugerido, tipo, tipoRecurso, contenido, enlace);
	        
	    } else if (tipo.equals("Prueba")) {
	    	
	        System.out.print("Ingrese el tipo de prueba (quiz, encuesta o examen): ");
	        String tipoPrueba = scanner.nextLine();
	    	
	        if (tipoPrueba.equals("Examen")) {
		    	newAct = new Examen(titulo, descripcion, nivelDificultad, duracionMin, obligatorio,
		    			tiempoCompletarSugerido, tipo, tipoPrueba);
		    	
	        }else if (tipoPrueba.equals("Quiz")) {
	        	
		        System.out.print("Ingrese la calificación mínima: ");
		        String calif = scanner.nextLine();
		        float calificacionMinima = Float.parseFloat(calif); 
		        
		    	newAct = new Quiz(titulo, descripcion, nivelDificultad, duracionMin, obligatorio,
		    			tiempoCompletarSugerido, tipo, calificacionMinima, tipoPrueba);
		    	
	        } else if (tipoPrueba.equals("Encuesta")) {
	        	
	        	newAct = new Encuesta(titulo, descripcion, nivelDificultad, duracionMin, obligatorio,
	        		 tiempoCompletarSugerido, tipo, tipoPrueba);
	        }

	    } else if (tipo.equals("Tarea")) {
	    	
	        System.out.print("Ingrese el contenido del recurso: ");
	        String contenido = scanner.nextLine();
	        
	    	newAct = new Tarea(titulo, descripcion, nivelDificultad, duracionMin, obligatorio,
	    			tiempoCompletarSugerido, tipo, contenido);
	    } else {
		    scanner.close();  
	        // Si el tipo no coincide con ninguna opción válida, lanzamos una excepción
	        throw new IllegalArgumentException("Tipo de actividad no válido: " + tipo);
	    }
	    scanner.close();  

	    actCreadas.add(newAct);
	    return newAct;
	}
	

	/**
	 * Método que permite que el profesor cree un LearningPath nuevo.
	 * @param titulo: nombre del Learning Path
	 * @param descripcion: descripción del Learning Path
	 * @param obj: objetivo del LearningPath
	 * @param dificultad: nivel de dificultad
	 */
	public LearningPath crearLearningPath(String titulo, String descripcion, String obj, int dificultad) {
		
        LocalDate fechaActual = LocalDate.now();
        String fecha = fechaActual.toString();
		
        LearningPath path = new LearningPath(titulo, descripcion, obj, dificultad, 0, fecha, fecha, 1, this);
        learningPathsCreados.put(titulo, path);
        
        return path;
	}
	
	/**
	 * Método que permite añadir una actividad a un Learning Path
	 * @param learningPath: Learning Path al cual se desea añadir la actividad.
	 * @param actividad: actividad que se va a añadir al Learning Path
	 * @param posicion: posición en la cual quedará la actividad.
	 */
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

	public Actividad clonarActividad(Actividad actividad) {
		Actividad nuevaActividad = null;
		try {
			nuevaActividad = (Actividad) actividad.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (nuevaActividad != null)
		{
			nuevaActividad.actividadClonadaProfesor();
			this.actCreadas.add(nuevaActividad);
		}
		return nuevaActividad;
	}
	
	/**
	 * Obtiene una actividad del profesor dado su nombre
	 * @param nombreActividad: nombre de la actividad
	 * @return La actividad buscada. Null si no se encuentra
	 */
	public Actividad obtenerActividad(String nombreActividad){
		Actividad act = null;
		Iterator<Actividad> it = this.actCreadas.iterator();
		boolean encontrada = false;
		while (!encontrada && it.hasNext())
		{
			act = it.next();
			if (nombreActividad.equals(act.titulo))
			{
				encontrada = true;
			}
		}
		if (!encontrada)
		{
			act = null;
		}
		return act;
	}
	
	/**
	 * Obtiene un Learning Path del profesor dado su titulo
	 * @param nombreLP: titulo del Learning Path
	 * @return El Learning Path buscado. Es Null si el profesor no tiene un
	 * learning path que coincida con la busqueda
	 */
	public LearningPath obtenerLearningPath(String nombreLP)
	{
		LearningPath lp = this.learningPathsCreados.get(nombreLP);
		return lp;
	}
	
}
