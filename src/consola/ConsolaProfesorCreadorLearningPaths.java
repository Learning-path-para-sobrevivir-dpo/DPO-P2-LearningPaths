package consola;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import excepciones.LearningPathOActividadNoEncontradoException;
import excepciones.TipoDePreguntaInvalidaException;
import modelo.Estudiante;
import modelo.LearningPath;
import modelo.Profesor;
import modelo.Progreso;
import modelo.Review;
import modelo.Usuario;
import modelo.actividades.Actividad;
import modelo.actividades.Encuesta;
import modelo.actividades.Examen;
import modelo.actividades.PreguntaAbierta;
import modelo.actividades.PreguntaMultiple;
import modelo.actividades.PreguntaVerdaderoFalso;
import modelo.actividades.Prueba;
import modelo.actividades.Quiz;
import modelo.actividades.QuizOpcionMultiple;
import modelo.actividades.QuizVerdaderoFalso;
import modelo.actividades.RecursoEducativo;
import modelo.actividades.Tarea;
import persistencia.ManejoDatos;

public class ConsolaProfesorCreadorLearningPaths {

	public static void main(String[] args) throws LearningPathOActividadNoEncontradoException {
		ManejoDatos datos = new ManejoDatos();
		Scanner scanner = new Scanner(System.in);
		ConsolaProfesorCreadorLearningPaths consola = new ConsolaProfesorCreadorLearningPaths();
		ImprimirConsola imprimir = new ImprimirConsola();
		
		datos.cargarDatos();
		Map<List<String>, Usuario> usuarios = datos.getUsuarios();
		
		//consola.iniciarAplicacion(datos, scanner, imprimir);
		scanner.close(); 
		
	}

	public ConsolaProfesorCreadorLearningPaths() {
		super();
	}
	
	public void iniciarAplicacion(ManejoDatos datos, Scanner scan, ImprimirConsola imprimir, Usuario usuario) throws LearningPathOActividadNoEncontradoException
	{
		int op = 1;
		Profesor prof = (Profesor) usuario;
		while (op != 0)
		{
			op = mostrarOpcionesApp(scan);
			routerOpciones(prof, op, imprimir, datos, scan);
		}
	}

	
	/**
	 * Muestra las opciones de la aplicación
	 * @param scan scanner para leer inputs
	 * @return La opción seleccionada por el usuario
	 */
	private int mostrarOpcionesApp(Scanner scan)
	{
		int op;
		System.out.println("Bienvenido a la app de profesor (creador Learning Paths)");
	    System.out.println("Seleccione lo que quiere hacer: ");
	    System.out.println("1. Ver todos mis Learning Paths");
	    System.out.println("2. Crear Learning Path");
	    System.out.println("3. Ver reseñas");
	    System.out.println("4. Editar Learning Path");
	    System.out.println("5. Clonar actividad");
	    System.out.println("6. Crear actividad");
	    System.out.println("7. Editar Actividad");
	    System.out.println("8. Añadir reseña a Actividad");

	    System.out.println("0. Guardar cambios y salir de la aplicacion");
	    
	    System.out.print("Opción: ");
        op = scan.nextInt();
		return op;
	}
	
	private void routerOpciones(Profesor prof, int op, ImprimirConsola imprimir, ManejoDatos datos, Scanner scan) throws LearningPathOActividadNoEncontradoException
	{
		switch (op)
		{
			
		case 1:
			verLearningPathsCreados(prof, imprimir);
			break;
			
		case 2:
			crearLearningPath(prof, imprimir, scan, datos);
			break;
			
		case 3:
			verReseñas(prof, imprimir, scan);
			break;
			
		case 4:
            try {
                editarLearningPaths(prof, imprimir, scan, datos);
            } catch (LearningPathOActividadNoEncontradoException e) {
                System.out.println(e.getMessage()); // Mensaje de error
            }
			break;
		case 5:
			clonarActividad(prof, imprimir, scan, datos);
			break;
            
        case 6:   	
        	crearActividad(prof, scan, datos);
            break;
            
        case 7: 
            try {
            	editarActividad(prof, imprimir, scan, datos);
            } catch (LearningPathOActividadNoEncontradoException e) {
                System.out.println(e.getMessage()); // Mensaje de error
            }
			break;
			
            
        case 8:   	
        	añadirReseña(prof, scan, datos);
            break;
            
        	
						
        case 0:
            System.out.println("Gracias por usar la aplicación! Saliendo...");
            datos.guardarDatos();
            
            System.exit(0);
         
            break;
            
        default:
            System.out.println("Opción no válida");
            break;
		}
	}

	



	private void añadirReseña(Profesor prof, Scanner scan, ManejoDatos datos) throws LearningPathOActividadNoEncontradoException {
		
		String contenido = "";
		
		System.out.println("Para escoger la actividad que se desea reseñar: ");
		Actividad act = seleccionarActividadDatos(prof,scan, datos);
		

	    if (scan.hasNextLine()) {
	        scan.nextLine();
	    }
		
		while (contenido.trim().isEmpty())
		{
			System.out.println("Ingrese el contenido de la reseña: ");
			contenido = scan.nextLine();
		}


		System.out.println("1. Añadir rating (true/false)");
		boolean hasRating = leerBooleano(scan);
			
		if (hasRating) {
			double rating = leerDouble(scan);
			Review rev = prof.addReviewRating(contenido, rating);
			act.addReview(rev);
		}
		else {
			Review rev= prof.addReview(contenido);
			act.addReview(rev);

		}
		
		datos.actualizarActividad(act);
		datos.actualizarUsuario(prof);

		
	}

	/**
	 * Método para ver todos los Learning Paths creados por un profesor
	 * @param prof instancia de la sesion iniciada del profesor
	 * @param imprimir clase para imprimir formateados los progresos de estudiantes del profesor
	 */
	private void verLearningPathsCreados(Profesor prof, ImprimirConsola imprimir)
	{
	    Map<String, LearningPath> lps = prof.getLearningPathsCreados();
	    boolean hayLearningPaths = !lps.isEmpty();

	    if (hayLearningPaths) {
	        for (String nombreLp : lps.keySet()) {
	            LearningPath lp = lps.get(nombreLp);
	            System.out.println("\n\n");
	            imprimir.imprimirLearningPath(lp); 
	        }
	    } else {
	        System.out.println("No tiene Learning Paths creados");
	    }
	    System.out.println();
	}

	
	private void crearLearningPath(Profesor prof, ImprimirConsola imprimir, Scanner scan, ManejoDatos datos) {
		
		String titulo = "";
		String descripcion = "";
		String objetivo = "";
		int nivelDificultad = 0;
		

	    if (scan.hasNextLine()) {
	        scan.nextLine();
	    }
		
		while (titulo.trim().isEmpty())
		{
			System.out.println("Ingrese el título del Learning Path: ");
			titulo = scan.nextLine();
		}
		
		while (descripcion.trim().isEmpty())
		{
			System.out.println("Ingrese la descripción: ");
			descripcion = scan.nextLine();
		}
		
		while (objetivo.trim().isEmpty())
		{
			System.out.println("Ingrese el objetivo: ");
			objetivo = scan.nextLine();
		}
		
		while (nivelDificultad == (0))
		{
			System.out.println("Ingrese la dificultad de 1 (fácil) a 3 (difícil): ");
			nivelDificultad = leerEntero(scan);
	
		}
		
		
		LearningPath pathCreado = prof.crearLearningPath(titulo, descripcion, objetivo, nivelDificultad);
		datos.addLearningPath(pathCreado);
		datos.actualizarUsuario(prof);
	}
	
	public void verReseñas(Profesor prof, ImprimirConsola imprimir, Scanner scan) throws LearningPathOActividadNoEncontradoException {
	    // Obtener los Learning Paths creados por el profesor
	    Map<String, LearningPath> paths = prof.getLearningPathsCreados();

	    if (paths.isEmpty()) {
	        throw new LearningPathOActividadNoEncontradoException("No ha creado ningún Learning Path.");
	    }

	    System.out.println("Sus Learning Paths creados son:");
	    for (String titulo : paths.keySet()) {
	        System.out.println("- " + titulo);
	    }

	    String nomConsultar = "";
	    LearningPath seleccionado = null;

	    if (scan.hasNextLine()) {
	        scan.nextLine();
	    }
	    
	    while (nomConsultar.trim().isEmpty()) {
	        System.out.println("Ingrese el nombre del Learning Path del que desea ver las reseñas:");
	        nomConsultar = scan.nextLine().trim();


	        seleccionado = paths.get(nomConsultar);
	        if (seleccionado == null) {
	            System.out.println("El Learning Path '" + nomConsultar + "' no existe. Intente nuevamente.");
	        }

	    }

	    // Mostrar el rating del Learning Path seleccionado
	    System.out.println("Rating del Learning Path '" + seleccionado.getTitulo() + "':");
	    int rating = seleccionado.getRating(); // Asegúrate de tener este método en la clase LearningPath
	    System.out.println(rating);

	    Map<Integer, Actividad> actsSeleccionado = seleccionado.getActividades();

	    for (Actividad act : actsSeleccionado.values()) {
	        List<Review> revs = act.getReviews();

	        for (Review rev : revs) {
	            System.out.println("- Rating: " + rev.getRating() + "; Contenido: " + rev.getContenido() + "; Publicado: " + rev.getFecha());
	        }
	    }
	}



	public void editarLearningPaths(Profesor prof, ImprimirConsola imprimir, Scanner scan, ManejoDatos datos) throws LearningPathOActividadNoEncontradoException {
		
		LearningPath seleccionado = seleccionarLearningPath(prof, imprimir, scan);
		
		System.out.println("Para tu learning path puedes: ");
		System.out.println("1. Añadir actividad ");
		System.out.println("2. Eliminar actividad ");
		System.out.println("3. Modificar título ");
		System.out.println("4. Modificar descripción ");
		System.out.println("5. Modificar objetivo ");
		System.out.println("6. Modificar nivel de dificultad ");
        System.out.print("Opción: ");
        int actop = scan.nextInt();
        
        switch (actop) {
        
        case 1:

        	añadirActividad(prof, imprimir, scan, datos, seleccionado); 
            
            break;
            
        case 2: 
        	System.out.println("Las actividades del path son: ");
        	Map<Integer,Actividad> acts = seleccionado.getActividades();
        	for (int pos: acts.keySet()) {
        		System.out.println(pos+": "+ (acts.get(pos)).getTitulo());
        	}
        	eliminarActividad(prof, imprimir, scan, datos, seleccionado); 

        	
            break;
            
        case 3: 
        	String nuevoTitulo = "";
        	
        	
    		while (nuevoTitulo.trim().isEmpty())
    		{
    			System.out.println("Ingrese el nuevo título para el Learning Path: ");
    			nuevoTitulo = scan.nextLine();
    		}
        	
    		seleccionado.setTitulo(nuevoTitulo);

            break;
            
        case 4: 

        	String nuevaDes = "";
        	
    		while (nuevaDes.trim().isEmpty())
    		{
    			System.out.println("Ingrese la nueva descripción: ");
    			nuevaDes = scan.nextLine();
    		}
        	
    		seleccionado.setDescripcion(nuevaDes);
            break;
           
        case 5: 
        	String nuevoObj = "";
        	
    		while (nuevoObj.trim().isEmpty())
    		{
    			System.out.println("Ingrese el nuevo objetivo: ");
    			nuevoObj = scan.nextLine();
    		}
        	
    		seleccionado.setObjetivo(nuevoObj);
        	
            break;
            
        case 6: 
        	int nuevoNiv = 0;
        	
    		while (nuevoNiv == (0))
    		{
    			System.out.println("Ingrese la nueva dificultad de 1 (fácil) a 3 (difícil): ");
    			nuevoNiv = leerEntero(scan);
  
    		}
    		seleccionado.setNivelDificultad(nuevoNiv);

            break;    
        }
              
		

	}

	
	public void añadirActividad(Profesor prof, ImprimirConsola imprimir, Scanner scan, ManejoDatos datos, LearningPath path) throws LearningPathOActividadNoEncontradoException {
	    int pos = -1;

	    
	    if (scan.hasNextLine()) {
	        scan.nextLine();
	    }
	    

	    System.out.println("Para escoger actividad: ");
	    
	    Actividad actParaAñadir = seleccionarActividadDatos(prof, scan, datos);
	    
	    
	    
	    System.out.println("Ingrese la posición a la que desea agregarla (-1 si desea dejarla al final):  ");
	    pos = leerEntero(scan);

	    // Obtener la actividad del sistema

	    // Verificar si la actividad existe
	    if (actParaAñadir == null) {
	        throw new IllegalArgumentException("Error: La actividad con nombre no existe.");
	    }
	    
	    if (pos>=0) {
	    	path.addActividadPorPos(actParaAñadir, pos);
	    }
	    else {
	    	path.addActividadDeUltimas(actParaAñadir);
	    }
	    
	    datos.actualizarLearningPath(path);

	}
	
	
	public void eliminarActividad(Profesor prof, ImprimirConsola imprimir, Scanner scan, ManejoDatos datos, LearningPath path) {
	    int pos = 0;


	    System.out.println("Ingrese la posición de la actividad que desea eliminar:  ");
	    pos = leerEntero(scan);

   
	    path.eliminarActividadPorPos(pos);
	    
	    
	    datos.actualizarLearningPath(path);

	}

	
	public void clonarActividad(Profesor prof, ImprimirConsola imprimir, Scanner scan, ManejoDatos datos) throws LearningPathOActividadNoEncontradoException {
	    
		if (scan.hasNextLine()) {
	        scan.nextLine();
	    }
	    
		System.out.println("Seleccione la actividad que desea clonar: ");
		
	    Actividad actClonar = seleccionarActividadDatos(prof,scan,datos);

		
		Actividad actClonada = prof.clonarActividad(actClonar);
		datos.addActividad(actClonada);
		datos.actualizarUsuario(prof);
	}
	
	
	private void crearActividad(Profesor prof, Scanner scan, ManejoDatos datos) {
		
        System.out.println("Has seleccionado 'Crear Actividad'");
        System.out.println("Cuál actividad?: ");
        System.out.println("1. Recurso Educativo");
        System.out.println("2. Examen");
        System.out.println("31. Quiz Multiple");
        System.out.println("32. Quiz Verdadero Falso");
        System.out.println("4. Encuesta");
        System.out.println("5. Tarea");
        
        System.out.print("Opción: ");
        int actop = scan.nextInt();
        

	    if (scan.hasNextLine()) {
	        scan.nextLine();
	    }
		
		// Leer título
		System.out.print("Título: ");
		String titulo = scan.nextLine();

		// Leer descripción
		System.out.print("Descripción: ");
		String descripcion = scan.nextLine();

		// Leer nivel educativo
		System.out.print("Nivel de Dificultad de 1 (fácil) a 3 (difícil): : ");
		int niv = leerEntero(scan);
		
		// Leer duración
		System.out.print("Duración estimada (en minutos): ");
		int dur = leerEntero(scan);

		// Leer si es obligatorio
		System.out.print("¿Es obligatorio? (true/false): ");
		boolean obligatorio = leerBooleano(scan);

		// Leer tiempo recomendado
		System.out.print("Tiempo recomendado de dedicación (en minutos): ");
		int tiempo = leerEntero(scan);

		// Leer tipo de actividad
		System.out.print("Tipo de actividad: ");
		String tipo = scan.nextLine();

        
        switch (actop) {
        
        case 1:
        	crearRecursoEducativo(prof, scan, datos, titulo, descripcion, niv, dur, obligatorio, tiempo, tipo);
            
            break;
            
        case 2: 
        	crearExamen(prof, scan, datos, titulo, descripcion, niv, dur, obligatorio, tiempo, tipo);

            break;
            
        case 31: 
        	crearQuizMultiple(prof, scan, datos, titulo, descripcion, niv, dur, obligatorio, tiempo, tipo);

            break;
            
            
        case 32: 
        	crearQuizVoF(prof, scan, datos, titulo, descripcion, niv, dur, obligatorio, tiempo, tipo);

            break;
            
        case 4: 
        	crearEncuesta(prof,scan, datos, titulo, descripcion, niv, dur, obligatorio, tiempo, tipo);
        	
            break;
           
        case 5: 
        	crearTarea(prof, scan, datos, titulo, descripcion, niv, dur, obligatorio, tiempo, tipo);
        	
            break;
        }		
	}
	
	
	public void crearRecursoEducativo(Profesor prof, Scanner scan, ManejoDatos datos, String titulo, String descripcion, int niv, int dur, boolean obligatorio, int tiempo, String tipo) {
		
		System.out.println("Para crear un Recurso Educativo, proporciona la siguiente información:");

		// Leer tipo de recurso
		System.out.print("Tipo de recurso (ej. Video, Documento, Quiz): ");
		String tipoRecurso = scan.nextLine();

		// Leer contenido
		System.out.print("Contenido breve o resumen del recurso: ");
		String contenido = scan.nextLine();

		// Leer enlace
		System.out.print("Enlace al recurso (URL): ");
		String enlace = scan.nextLine();

		// Llamar al método del profesor para crear el recurso educativo
		RecursoEducativo recCreado = prof.crearRecursoEducativo(titulo, descripcion, niv, dur, obligatorio, tiempo, tipo, tipoRecurso, contenido, enlace);

		datos.addActividad(recCreado);
		datos.actualizarUsuario(prof);
		
		System.out.println("¡Recurso educativo creado con éxito!");
		
	}

	private void crearQuizMultiple(Profesor prof, Scanner scan, ManejoDatos datos, String titulo, String descripcion, int niv, int dur, boolean obligatorio, int tiempo, String tipo) {

		
		System.out.println("Para crear un Quiz, proporciona la siguiente información:");

	    if (scan.hasNextLine()) {
	        scan.nextLine();
	    }


		System.out.print("Calificación mínima: ");
		float califMin = leerFloat(scan);



		String tipoPrueba = "Quiz Opcion Multiple";

		QuizOpcionMultiple quiz = prof.crearQuizMultiple(titulo, descripcion, niv, dur, obligatorio, tiempo, tipo, tipoPrueba, califMin);
		
		datos.addActividad(quiz);
		System.out.println("¡Quiz creado con éxito!");

		
		System.out.println("Aún no tiene preguntas, debes crearlas: ");

		boolean continuar = true;
		
		while(continuar) {
			
			System.out.println("Su quiz es de tipo " + tipoPrueba + ", por ende, las preguntas deben ser de ese tipo.");
			System.out.println("1. Añadir pregunta");
			System.out.println("2. Finalizar adición de preguntas");
			
	        System.out.print("Ingrese su opción: ");
	        
	        int actop = scan.nextInt();
	        
	        switch (actop) {
	            case 1:

	    			añadirPreguntaMultiple( prof,  scan,  quiz, datos);
	    			break;
	    			
	            case 2:
	                continuar = false;
	                break;
	            default:
	                System.out.println("Opción inválida. Intente de nuevo.");
	        }
			
		}
		datos.actualizarActividad(quiz);
		datos.actualizarUsuario(prof);
	}
	
	

	private void crearQuizVoF(Profesor prof, Scanner scan, ManejoDatos datos, String titulo, String descripcion, int niv, int dur, boolean obligatorio, int tiempo, String tipo) {
		
		System.out.println("Para crear un Quiz, proporciona la siguiente información:");

	    if (scan.hasNextLine()) {
	        scan.nextLine();
	    }


		System.out.print("Calificación mínima: ");
		float califMin = leerFloat(scan);


		
		String tipoPrueba = "Quiz Verdadero Falso";


		QuizVerdaderoFalso quiz = prof.crearQuizVoF(titulo, descripcion, niv, dur, obligatorio, tiempo, tipo, tipoPrueba, califMin);
		
		
		datos.addActividad(quiz);
		System.out.println("¡Quiz creado con éxito!");

		
		System.out.println("Aún no tiene preguntas, debes crearlas: ");

		boolean continuar = true;
		
		while(continuar) {
			
			System.out.println("Su quiz es de tipo " + tipoPrueba + ", por ende, las preguntas deben ser de ese tipo.");
			System.out.println("1. Añadir pregunta");
			System.out.println("2. Finalizar adición de preguntas");
			
	        System.out.print("Ingrese su opción: ");
	        
	        int actop = scan.nextInt();
	        
	        switch (actop) {
	            case 1:

	    			añadirPreguntaVoF( prof,  scan,  quiz, datos);
	    			                break;
	            case 2:
	                continuar = false;
	                break;
	            default:
	                System.out.println("Opción inválida. Intente de nuevo.");
	        }
			
		}
		datos.actualizarActividad(quiz);
		datos.actualizarUsuario(prof);
	}
	
	
	private void añadirPreguntaMultiple(Profesor prof, Scanner scan, QuizOpcionMultiple quiz, ManejoDatos datos) {
	    System.out.println("Creando pregunta de opción múltiple: ");
	    
	    
	    // Leer contenido de la pregunta
	    System.out.print("Ingrese el contenido de la pregunta: ");
	    String contenido = scan.nextLine();

	    // Leer las opciones
	    List<String> opciones = new ArrayList<>();
	    System.out.println("Ingrese las opciones (mínimo 4): ");
	    for (int i = 1; i <= 4; i++) {
	        System.out.print("Opción " + i + ": ");
	        opciones.add(scan.nextLine());
	    }

	    // Leer la opción correcta
	    System.out.print("Ingrese el número de la opción correcta (1-4): ");
	    int correcta = leerEntero(scan);

	    // Crear y añadir la pregunta
	    try {
	        PreguntaMultiple pregunta = prof.crearPreguntaMultiple(contenido, opciones, correcta - 1);
	        prof.addPreguntaQuizMultiple((QuizOpcionMultiple) quiz, pregunta);
		    datos.addPregunta(pregunta);

	        System.out.println("Pregunta de opción múltiple añadida con éxito.");
	    } catch (ClassCastException e) {
	        System.out.println("Error: El quiz no admite preguntas de opción múltiple.");
	    } catch (TipoDePreguntaInvalidaException e) {
	        System.out.println("Error: Tipo de pregunta inválido.");
	    }
	}

	
	private void añadirPreguntaVoF(Profesor prof, Scanner scan, QuizVerdaderoFalso quiz, ManejoDatos datos) {
	    System.out.println("Creando pregunta de verdadero/falso: ");


	    if (scan.hasNextLine()) {
	        scan.nextLine();
	    }
	    // Leer contenido de la pregunta
	    System.out.print("Ingrese el contenido de la pregunta: ");
	    String contenido = scan.nextLine();

	    // Leer la respuesta correcta
	    System.out.print("¿La afirmación es verdadera? (true/false): ");
	    boolean correcta = leerBooleano(scan);

	    // Crear y añadir la pregunta
	    try {
	        PreguntaVerdaderoFalso pregunta = prof.crearPreguntaVoF(contenido, correcta);
	        prof.addPreguntaVoF((QuizVerdaderoFalso) quiz, pregunta);
		    datos.addPregunta(pregunta);

	        System.out.println("Pregunta de verdadero/falso añadida con éxito.");
	    } catch (ClassCastException e) {
	        System.out.println("Error: El quiz no admite preguntas de verdadero/falso.");
	    } catch (TipoDePreguntaInvalidaException e) {
	        System.out.println("Error: Tipo de pregunta inválido.");
	    }
	    
	}

	
	
	private void crearExamen(Profesor prof, Scanner scan, ManejoDatos datos, String titulo, String descripcion, int niv, int dur, boolean obligatorio, int tiempo, String tipo) {
		

		String tipoPrueba = "Examen";


		// Llamar al método del profesor para crear el examen
		Examen exam = prof.crearExamen(titulo, descripcion, niv, dur, obligatorio, tiempo, tipo, tipoPrueba);
		datos.addActividad(exam);
		
		System.out.println("¡Examen creado con éxito!");
		
		System.out.println("Aún no tiene preguntas, debes crearlas: ");

		boolean continuar = true;
		
		while(continuar) {
			
			System.out.println("Su prueba es examen, por ende, las preguntas deben ser abiertas.");
			System.out.println("1. Añadir pregunta");
			System.out.println("2. Finalizar adición de preguntas");
			
	        System.out.print("Ingrese su opción: ");
	        
	        int opcion = leerEntero(scan);
	        switch (opcion) {
	            case 1:
	            	añadirPreguntaAbierta(prof, scan, exam, datos);
	            	break;
	            case 2:
	                continuar = false;
	                break;
	            default:
	                System.out.println("Opción inválida. Intente de nuevo.");
	        }
			
		}
		datos.actualizarActividad(exam);
		datos.actualizarUsuario(prof);

	}
	
	private void crearEncuesta(Profesor prof, Scanner scan, ManejoDatos datos, String titulo, String descripcion, int niv, int dur, boolean obligatorio, int tiempo, String tipo) {
		

		String tipoPrueba = "Encuesta";


		// Llamar al método del profesor para crear la encuesta
		Encuesta encuesta = prof.crearEncuesta(titulo, descripcion, niv, dur, obligatorio, tiempo, tipo, tipoPrueba);
		datos.addActividad(encuesta);
		
		System.out.println("¡Encuesta creada con éxito!");
		
		System.out.println("Aún no tiene preguntas, debes crearlas: ");

		boolean continuar = true;
		
		while(continuar) {
			
			System.out.println("Su prueba es encuesta, por ende, las preguntas deben ser abiertas.");
			System.out.println("1. Añadir pregunta");
			System.out.println("2. Finalizar adición de preguntas");
			
	        System.out.print("Ingrese su opción: ");
	        
	        int opcion = leerEntero(scan);
	        switch (opcion) {
	            case 1:
	            	añadirPreguntaAbierta(prof, scan, encuesta, datos);
	            	break;
	            case 2:
	                continuar = false;
	                break;
	            default:
	                System.out.println("Opción inválida. Intente de nuevo.");
	        }
			
		}
		datos.actualizarUsuario(prof);


	}
	
	
	private void añadirPreguntaAbierta(Profesor prof, Scanner scan, Object actividad, ManejoDatos datos) {
	    if (!(actividad instanceof Examen) && !(actividad instanceof Encuesta)) {
	        System.out.println("Error: La actividad no es válida para añadir preguntas.");
	        return;
	    }

	    System.out.println("Creando pregunta abierta:");

	    // Leer contenido de la pregunta
	    System.out.print("Ingrese el contenido de la pregunta: ");
	    String contenido = scan.nextLine();

	    // Crear y añadir la pregunta
	    try {
	        PreguntaAbierta pregunta = prof.crearPreguntaAbierta(contenido);

	        if (actividad instanceof Examen) {
	        	
	            prof.addPreguntaExamen((Examen) actividad, pregunta);
	            
	            System.out.println("Pregunta añadida con éxito al examen.");
	        } else if (actividad instanceof Encuesta) {
	            prof.addPreguntaEncuesta((Encuesta) actividad, pregunta);
	            System.out.println("Pregunta añadida con éxito a la encuesta.");
	        }
		    datos.addPregunta(pregunta);

	    } catch (TipoDePreguntaInvalidaException e) {
	        System.out.println("Error: No se pudo añadir la pregunta. Detalles: " + e.getMessage());
	    }
	    
	}
	
	
	public void crearTarea(Profesor prof, Scanner scan, ManejoDatos datos, String titulo, String descripcion, int niv, int dur, boolean obligatorio, int tiempo, String tipo) {
		
		System.out.println("Para crear una Tarea, proporciona la siguiente información:");

	    if (scan.hasNextLine()) {
	        scan.nextLine();
	    }


		System.out.print("Contenido de la tarea: ");
		String contenido = scan.nextLine();



		System.out.print("Medio de entrega de la tarea: ");
		String medioEntrega = scan.nextLine();


		// Llamar al método del profesor para crear la tarea
		Tarea tarea = prof.crearTarea(titulo, descripcion, niv, dur, obligatorio, tiempo, tipo, contenido, medioEntrega);

		datos.addActividad(tarea);
		datos.actualizarUsuario(prof);
		System.out.println("¡Tarea creada con éxito!");
		
	}
	
	
	private void editarActividad(Profesor prof, ImprimirConsola imprimir, Scanner scan, ManejoDatos datos) throws LearningPathOActividadNoEncontradoException {

	    Actividad seleccionado = seleccionarActividadProfesor(prof,scan);

	    if (scan.hasNextLine()) {
	        scan.nextLine();
	    }
	    
		
	    if (seleccionado == null) {
	    	throw new LearningPathOActividadNoEncontradoException("No hay actividad.");
		        }
	    	
		       
	    		    
	    
	    
		
		System.out.println("Para tu actividad puedes: ");
		System.out.println("1. Modificar título ");
		System.out.println("2. Modificar descripción ");
		System.out.println("3. Modificar nivel de dificultad ");
		System.out.println("4. Modificar duración ");
		System.out.println("5. Modificar obligatorio ");
		System.out.println("6. Modificar tiempo sugerido completar ");
		if (seleccionado instanceof Prueba) {
			System.out.println("7. Añadir pregunta ");
			System.out.println("8. Eliminar pregunta ");

		}
		else if (seleccionado instanceof RecursoEducativo||seleccionado instanceof Tarea) {
			System.out.println("9. Modificar contenido ");
			if (seleccionado instanceof RecursoEducativo) {
				
				System.out.println("10. Modificar Enlace ");
			}
		}
			
        System.out.print("Opción: ");
        int actop = scan.nextInt();
        
        switch (actop) {
        
        case 1:

        	String nuevoTitulo = "";
        	
    	    if (scan.hasNextLine()) {
    	        scan.nextLine();
    	    }

        	
    		while (nuevoTitulo.trim().isEmpty())
    		{
    			System.out.println("Ingrese el nuevo título para el Learning Path: ");
    			nuevoTitulo = scan.nextLine();
    		}
        	
    		seleccionado.setTitulo(nuevoTitulo);
           
            break;
            
        case 2: 

        	String nuevaDes = "";
        	
    	    if (scan.hasNextLine()) {
    	        scan.nextLine();
    	    }

        	
    		while (nuevaDes.trim().isEmpty())
    		{
    			System.out.println("Ingrese la nueva descripción: ");
    			nuevaDes = scan.nextLine();
    		}
        	
    		seleccionado.setObjetivo(nuevaDes);
        	
            break;
            
        case 3: 
        	int nuevoNiv = 0;
        	
    	    if (scan.hasNextLine()) {
    	        scan.nextLine();
    	    }

        	
    		while (nuevoNiv == (0))
    		{
    			System.out.println("Ingrese la nueva dificultad de 1 (fácil) a 3 (difícil): ");
    			nuevoNiv = leerEntero(scan);
  
    		}
    		seleccionado.setNivelDificultad(nuevoNiv);

        	
        	break;
            
        case 4: 

        	int nuevaDur = 0;
        	
    	    if (scan.hasNextLine()) {
    	        scan.nextLine();
    	    }

        	
    		while (nuevaDur == (0))
    		{
    			System.out.println("Ingrese la nueva duración: ");
    			nuevaDur = leerEntero(scan);
  
    		}
    		seleccionado.setDuracionMin(nuevaDur);
        	
            break;
           
        case 5: 
        	boolean obligatorio = seleccionado.isObligatorio();
        	
    	    if (scan.hasNextLine()) {
    	        scan.nextLine();
    	    }

        	
    		while (obligatorio == (seleccionado.isObligatorio()))
    		{
    			System.out.println("Es obligatorio? (true/false): ");
    			obligatorio = leerBooleano(scan);
    		}
        	
    		seleccionado.setObligatorio(obligatorio);
        	
            break;
            
        case 6: 

        	int nuevoTsug = 0;
        	
    	    if (scan.hasNextLine()) {
    	        scan.nextLine();
    	    }

        	
    		while (nuevoTsug == (0))
    		{
    			System.out.println("Ingrese el nuevo tiempo sugerido para completar: ");
    			nuevoTsug = leerEntero(scan);
  
    		}
    		seleccionado.setTiempoCompletarSugerido(nuevoTsug);

            break;    
        
        
        case 7: 
        	
        	if (seleccionado instanceof Prueba) {
        	    Prueba prueba = (Prueba) seleccionado;
        	    
            	
        	    if (scan.hasNextLine()) {
        	        scan.nextLine();
        	    }


        	    if (prueba instanceof Encuesta) {
        	    	Encuesta enc = (Encuesta)prueba;

        	    	añadirPreguntaAbierta(prof, scan, enc, datos);
        	    }


        	    else if (prueba instanceof Examen) {
        	    	Examen exam = (Examen)prueba;

        	    	añadirPreguntaAbierta(prof, scan, exam, datos);
            		
            	}
            	else if (prueba.getTipoPrueba().equals("Quiz Verdadero Falso")){
            		
            		if (prueba instanceof QuizVerdaderoFalso) {
            			QuizVerdaderoFalso quiz = (QuizVerdaderoFalso)prueba;
            
            		
            			añadirPreguntaVoF(prof, scan, quiz, datos);
            		}
            		
            	}
            	else if (prueba.getTipoPrueba()== "Quiz Opcion Multiple"){
            		
            		QuizOpcionMultiple quiz = (QuizOpcionMultiple)prueba;
            		
            		añadirPreguntaMultiple(prof, scan, quiz, datos);
            		
            	}
            	datos.actualizarActividad(prueba);
            	
            	
            	

        	} else {
        	    System.out.println("La actividad seleccionada no es una prueba.");
        	}

        
        	
        	break;
        	
        	
        	
        case 8: 
        	
    	    if (scan.hasNextLine()) {
    	        scan.nextLine();
    	    }

        	
        	Prueba prueba1 = (Prueba)seleccionado;

        	int posPreg = -1;
        	
    		while (posPreg == (-1))
    		{
    			System.out.println("Ingrese la posición de la pregunta a eliminar: ");
    			posPreg = leerEntero(scan);
  
    		}
    		
    		prueba1.eliminarPregunta(posPreg);
    		System.out.println("Pregunta eliminada exitosamente.");
        	
    		break;
        	 	
        case 9: 
        	
        	String newCont = "";
        	
    	    if (scan.hasNextLine()) {
    	        scan.nextLine();
    	    }

        	
    		while (newCont.trim().isEmpty())
    		{
    			System.out.println("Ingrese la nueva descripción: ");
    			newCont = scan.nextLine();
    		}

        	
        	if (seleccionado.getTipoActividad() == "Recurso Educativo") {
        		
        		RecursoEducativo act1 = (RecursoEducativo)seleccionado;
        		act1.setContenido(newCont);

        	}
        	else if (seleccionado.getTipoActividad() == "Tarea") {
        		
        		Tarea act1 = (Tarea)seleccionado;
        		act1.setContenido(newCont);
        	}
        	
        case 10: 
        	
    		RecursoEducativo rec = (RecursoEducativo)seleccionado;
    		
    	    if (scan.hasNextLine()) {
    	        scan.nextLine();
    	    }


        	
        	String newLink = "";
        	
    		while (newLink.trim().isEmpty())
    		{
    			System.out.println("Ingrese la nueva descripción: ");
    			newLink = scan.nextLine();
    		}

    		rec.setEnlace(newLink);
        	
        	break; 
        	
        }
        
	    
        	
		
	}

	
	private static int leerEntero(Scanner scan) {
		while (true) {
			try {
				return Integer.parseInt(scan.nextLine());
			} catch (NumberFormatException e) {
				System.out.print("Por favor, ingresa un número entero válido: ");
			}
		}
	}
	
	private static float leerFloat(Scanner scan) {
	    while (true) {
	        try {
	            return Float.parseFloat(scan.nextLine());
	        } catch (NumberFormatException e) {
	            System.out.print("Por favor, ingresa un número decimal válido (float): ");
	        }
	    }
	}

	private static double leerDouble(Scanner scan) {
	    while (true) {
	        try {
	            return Double.parseDouble(scan.nextLine());
	        } catch (NumberFormatException e) {
	            System.out.print("Por favor, ingresa un número decimal válido (double): ");
	        }
	    }
	}


	private static boolean leerBooleano(Scanner scan) {
		while (true) {
			String input = scan.nextLine().trim().toLowerCase();
			if (input.equals("true") || input.equals("false")) {
				return Boolean.parseBoolean(input);
			}
			System.out.print("Por favor, ingresa 'true' o 'false': ");
		}


		
	}
	
	
	
	private LearningPath seleccionarLearningPath(Profesor prof, ImprimirConsola imprimir, Scanner scan) throws LearningPathOActividadNoEncontradoException
	{
		LearningPath lpSeleccionado = null;
		Map<String, LearningPath>lps = prof.getLearningPathsCreados();
		if (!lps.isEmpty())
		{
			int i = 1;
			Map<Integer, String> indexLPs = new HashMap<Integer, String>();

			System.out.println("Tus Learning Paths:");
			System.out.println("-----------------------------------------------------");
			for (String nombreLp: lps.keySet())
			{
				System.out.println(Integer.toString(i) + ". "+ nombreLp);
				indexLPs.put(i, nombreLp);
				i++;
			}
		
			System.out.println("-----------------------------------------------------");
			System.out.println("\n Seleccione el número del Learning Path que quiere: ");
			int op = scan.nextInt();
			while(!indexLPs.containsKey(op))
			{
				System.out.println("Opción invalida");
				System.out.println("Ingrese el número del Learning Path que desea: ");
				op = scan.nextInt();
			}
			String nombreLpSeleccionado = indexLPs.get(op);
			lpSeleccionado = lps.get(nombreLpSeleccionado);
		}
		else
		{
	        throw new LearningPathOActividadNoEncontradoException("No ha creado ningún Learning Path.");
		}
		return lpSeleccionado;
	}
	
	
	
	private Actividad seleccionarActividadDatos(Profesor prof, Scanner scan, ManejoDatos datos) throws LearningPathOActividadNoEncontradoException
	{
		
		Actividad actSeleccionada = null;
		Map<String, Actividad> actividades = datos.getActividades();
		if (!actividades.isEmpty())
		{
			int i = 1;
			Map<Integer, String> indexActs = new HashMap<Integer, String>();

			System.out.println("Las actividades en el sistema son:");
			System.out.println("-----------------------------------------------------");
			for (Actividad act: actividades.values())
			{
				String nombreAct = act.getTitulo();
				System.out.println(Integer.toString(i) + ". "+ nombreAct);
				indexActs.put(i, nombreAct);
				i++;
			}
		
			System.out.println("-----------------------------------------------------");
			System.out.println("\n Seleccione el número de Actividad que quiere: ");
			int op = scan.nextInt();
			while(!indexActs.containsKey(op))
			{
				System.out.println("Opción invalida");
				System.out.println("Ingrese el número de la Actividad que desea: ");
				op = scan.nextInt();
			}
			String nombreSeleccionado = indexActs.get(op);
			for (Actividad act : actividades.values()) {
				if (act.getTitulo().equals(nombreSeleccionado)){
					actSeleccionada = act;
				}
			}
			

		}
		else
		{
	        throw new LearningPathOActividadNoEncontradoException("No ha creado ningún Learning Path.");
		}
		return actSeleccionada;
	}
	

	
	private Actividad seleccionarActividadProfesor(Profesor prof, Scanner scan) throws LearningPathOActividadNoEncontradoException
	{
		
		Actividad actSeleccionada = null;
		List<Actividad> actividades = prof.getActCreadas();
		if (!actividades.isEmpty())
		{
			int i = 1;
			Map<Integer, String> indexActs = new HashMap<Integer, String>();

			System.out.println("Sus actividades creadas son:");
			System.out.println("-----------------------------------------------------");
			for (Actividad act: actividades)
			{
				String nombreAct = act.getTitulo();
				System.out.println(Integer.toString(i) + ". "+ nombreAct);
				indexActs.put(i, nombreAct);
				i++;
			}
		
			System.out.println("-----------------------------------------------------");
			System.out.println("\n Seleccione el número de Actividad que quiere: ");
			int op = scan.nextInt();
			while(!indexActs.containsKey(op))
			{
				System.out.println("Opción invalida");
				System.out.println("Ingrese el número de la Actividad Path que desea: ");
				op = scan.nextInt();
			}
			String nombreSeleccionado = indexActs.get(op);
			for (Actividad act : actividades) {
				if (act.getTitulo().equals(nombreSeleccionado)){
					actSeleccionada = act;
				}
			}
			

		}
		else
		{
	        throw new LearningPathOActividadNoEncontradoException("No ha creado ningún Learning Path.");
		}
		return actSeleccionada;
	}
	
	
}
