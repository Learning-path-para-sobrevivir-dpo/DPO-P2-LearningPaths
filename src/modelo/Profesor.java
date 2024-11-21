package modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import excepciones.TipoDePreguntaInvalidaException;
import modelo.actividades.Actividad;
import modelo.actividades.Encuesta;
import modelo.actividades.Examen;
import modelo.actividades.PreguntaAbierta;
import modelo.actividades.PreguntaMultiple;
import modelo.actividades.PreguntaVerdaderoFalso;
import modelo.actividades.Quiz;
import modelo.actividades.QuizOpcionMultiple;
import modelo.actividades.QuizVerdaderoFalso;
import modelo.actividades.RecursoEducativo;
import modelo.actividades.Tarea;

public class Profesor extends Usuario{
	
	public Map<String, LearningPath> learningPathsCreados;
	public List<Actividad> actCreadas;
	
	public Profesor(String login, String correo, String contraseña, String tipo) {
		super(login, correo, contraseña, tipo);
		learningPathsCreados = new HashMap<String,LearningPath>();
		actCreadas = new ArrayList<Actividad>();
	}
	
	
	/**
	 * Métodos para crear todas las actividades diferentes
	 */
	public RecursoEducativo crearRecursoEducativo(String titulo, String descripcion, int nivelDificultad, int duracionMin, boolean obligatorio,
	        int tiempoCompletarSugerido, String tipo, String tipoRecurso, String contenido, String enlace) {
		
		RecursoEducativo newAct = new RecursoEducativo(titulo, descripcion, nivelDificultad, duracionMin, obligatorio, 
                tiempoCompletarSugerido, tipo, tipoRecurso, contenido, enlace);
		this.actCreadas.add(newAct);
		return newAct;
	}
	
	public Examen crearExamen(String titulo, String descripcion, int nivelDificultad, int duracionMin, boolean obligatorio,
	        int tiempoCompletarSugerido, String tipo, String tipoPrueba) {
		
		Examen newAct = new Examen(titulo, descripcion, nivelDificultad, duracionMin, obligatorio,
    			tiempoCompletarSugerido, tipo, tipoPrueba);
		this.actCreadas.add(newAct);
		return newAct;
	}
	
	public QuizVerdaderoFalso crearQuizVoF(String titulo, String descripcion, int nivelDificultad, int duracionMin, boolean obligatorio,
	        int tiempoCompletarSugerido, String tipo, String tipoPrueba, float calificacionMinima) {
		QuizVerdaderoFalso newAct = null;

		newAct = new QuizVerdaderoFalso(titulo, descripcion, nivelDificultad, duracionMin, obligatorio, tiempoCompletarSugerido, tipo, calificacionMinima, tipoPrueba);
		
		this.actCreadas.add(newAct);
		return newAct;
	}
	
	public QuizOpcionMultiple crearQuizMultiple(String titulo, String descripcion, int nivelDificultad, int duracionMin, boolean obligatorio,
	        int tiempoCompletarSugerido, String tipo, String tipoPrueba, float calificacionMinima) {
		QuizOpcionMultiple newAct = null;

		newAct = new QuizOpcionMultiple(titulo, descripcion, nivelDificultad, duracionMin, obligatorio,tiempoCompletarSugerido, tipo, calificacionMinima, tipoPrueba);
		
		this.actCreadas.add(newAct);
		return newAct;
	}
				
	public Encuesta crearEncuesta(String titulo, String descripcion, int nivelDificultad, int duracionMin, boolean obligatorio,
	        int tiempoCompletarSugerido, String tipo, String tipoPrueba) {
	
			Encuesta newAct = new Encuesta(titulo, descripcion, nivelDificultad, duracionMin, obligatorio,
				tiempoCompletarSugerido, tipo, tipoPrueba);
		this.actCreadas.add(newAct);
		return newAct;
	}
	
	public Tarea crearTarea(String titulo, String descripcion, int nivelDificultad, int duracionMin, boolean obligatorio,
	        int tiempoCompletarSugerido, String tipo, String contenido, String medioEntrega) {
	
		Tarea newAct = new Tarea(titulo, descripcion, nivelDificultad, duracionMin, obligatorio,
    			tiempoCompletarSugerido, tipo, contenido, medioEntrega);
		this.actCreadas.add(newAct);
		return newAct;
	}
	
	
	/***
	 * Método crea una nueva actividad. Se debe buscar y actuar diferente segun el tipo de 
	 * actividad a crear dado que Actividad es una clase abstracta.
	 
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
	***/
	

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
		
        LearningPath path = new LearningPath(titulo, descripcion, obj, dificultad, 0, fecha, fecha, 1, this.getLogin());
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


	public Map<String, LearningPath> getLearningPathsCreados() {
		return this.learningPathsCreados;
	}


	public List<Actividad> getActCreadas() {
		return this.actCreadas;
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
	
	
	public PreguntaAbierta crearPreguntaAbierta(String contenido) {
		
		PreguntaAbierta pregunta = new PreguntaAbierta(contenido);
		return pregunta;
		
	}
	
	
	public PreguntaMultiple crearPreguntaMultiple(String contenido, List<String> opciones, int correcta ) {
		
		PreguntaMultiple pregunta = new PreguntaMultiple(contenido, opciones, correcta);
		return pregunta;
		
	}
	

	public PreguntaVerdaderoFalso crearPreguntaVoF(String contenido, boolean correcto) {
		
		PreguntaVerdaderoFalso pregunta = new PreguntaVerdaderoFalso(contenido, correcto);
		return pregunta;
		
	}
	
	public void addPreguntaQuizMultiple(QuizOpcionMultiple quiz, PreguntaMultiple pregunta) throws TipoDePreguntaInvalidaException{
		
		quiz.addPregunta(pregunta);
		
	}
	
	public void addPreguntaVoF(QuizVerdaderoFalso quiz, PreguntaVerdaderoFalso pregunta) throws TipoDePreguntaInvalidaException {
		
		quiz.addPregunta(pregunta);
	}
	
	
	public void addPreguntaExamen (Examen examen, PreguntaAbierta pregunta) throws TipoDePreguntaInvalidaException {
		
		examen.addPregunta(pregunta);
	}

	
	public void addPreguntaEncuesta (Encuesta encuesta, PreguntaAbierta pregunta) throws TipoDePreguntaInvalidaException {
		
		encuesta.addPregunta(pregunta);
	}
}
