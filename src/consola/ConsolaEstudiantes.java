package consola;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.json.JSONArray;

import excepciones.*;
import modelo.*;
import modelo.actividades.*;
import persistencia.*;

public class ConsolaEstudiantes {
	
	public static void main(String[] args) {
		ManejoDatos datos = new ManejoDatos();
		Scanner scanner = new Scanner(System.in);
		ConsolaEstudiantes consola = new ConsolaEstudiantes();
		ImprimirConsola imprimir = new ImprimirConsola();
		
		datos.cargarDatos();
		//consola.iniciarAplicacion(datos, scanner, imprimir);
		scanner.close(); 
	}
	
	public ConsolaEstudiantes() {
		super();
	}

	public void iniciarAplicacion(ManejoDatos datos, Scanner scan, ImprimirConsola imprimir, Usuario usuario)
	{
		int op = 1;
		Estudiante estudiante = (Estudiante) usuario;
		while (op != 0)
		{
			op = mostrarOpcionesApp(scan);
			routerOpciones(estudiante, op, imprimir, datos, scan);
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
		System.out.println("\nBienvenido al menu de Estudiantes");
	    System.out.println("Seleccione lo que quiere hacer: ");
	    System.out.println("1. Inscribir un Learning Path");
	    System.out.println("2. Ver Learning Paths inscritos");
	    System.out.println("3. Ver actividades del Learning Path");
	    System.out.println("4. Ver progreso de Learning Path");
	    System.out.println("5. Iniciar actividad o continuar una actividad iniciada");
	    System.out.println("0. Salir de la aplicacion");
	    
	    System.out.print("Opción: ");
        op = scan.nextInt();
        scan.nextLine();
		return op;
	}
	
	private void routerOpciones(Estudiante es, int op, ImprimirConsola imprimir, ManejoDatos datos, Scanner scan)
	{
		switch (op)
		{
		case 0:
			datos.guardarDatos();
			System.out.println("Gracias por usar la aplicación!!!");
			break;
		case 1:
			verInscribirLearningPath(datos, es, imprimir, scan);
			break;
		case 2:
			verLearningPaths(es, imprimir, scan);
			break;
		case 3:
			verActividadesLearningPath(es, imprimir, scan);
			break;
		case 4:
			verProgresoLearningPath(es, imprimir, scan);
			break;
		case 5:
			verIniciarActividad(es, imprimir, scan);
			break;
		}
	}
	
	/**
	 * Funcion para inscribir un Learning Path
	 * @param es instancia de la sesion iniciada del estudiante
	 * @param imprimir
	 * @param scan
	 */
	private void verInscribirLearningPath(ManejoDatos datos, Estudiante es, ImprimirConsola imprimir, Scanner scan)
	{
		LearningPath lpSeleccionado = seleccionarLearningPathD(datos, imprimir, scan);
		if (lpSeleccionado != null)
		{
			String nombreLpSeleccionado = lpSeleccionado.getTitulo();
			try {
	            es.inscribirLearningPath(lpSeleccionado);
	            System.out.println("\nEl Learning Path '" + nombreLpSeleccionado + "' fue inscrito exitosamente\n");
	        } catch (IllegalStateException e) {
	            // Manejo de la excepción: imprime el mensaje de error y continúa.
	            System.out.println("\nError: " + e.getMessage() + "\n");
	        }
	    } else {
				System.out.println("Hubo un problema al tratar de inscribir el Learning Path\n");
			}
		return;
	}
	
	/**
	 * Funcion para ver Learning Paths inscritos
	 * @param es instancia de la sesion iniciada del estudiante
	 * @param imprimir
	 * @param scan
	 */
	private void verLearningPaths(Estudiante es, ImprimirConsola imprimir, Scanner scan)
	{	
		Map<String, LearningPath> learningPaths = es.getLearningPaths();
		if (!learningPaths.isEmpty())
		{
			for (LearningPath learningPath: learningPaths.values())
			{
				imprimir.imprimirLearningPath(learningPath);
				
			}
		}
		
		else {
			System.out.println("\nNo hay Learning Paths inscritos\n");
		}
		return;
	}
	
	private void verActividadesLearningPath(Estudiante es, ImprimirConsola imprimir, Scanner scan)
	{
		LearningPath lpSeleccionado = seleccionarLearningPathE(es, imprimir, scan);
		if (lpSeleccionado != null)
		{
			Map<Integer, Actividad> actividades = lpSeleccionado.getActividades();
			if (!actividades.isEmpty())
			{
				for (Actividad actividad: actividades.values())
				{
					imprimir.imprimirActividad(actividad, true, true, true);
				}
			}
			
			else {
				System.out.println("\nNo hay actividades en este Learning Path\n");
			}
			}
		else
			{
				System.out.println("Este Learning Path no existe\n");
			}
		return;
	}
	
	private void verProgresoLearningPath(Estudiante es, ImprimirConsola imprimir, Scanner scan)
	{
		LearningPath lpSeleccionado = seleccionarLearningPathE(es, imprimir, scan);
		Map<String, Progreso> progresos = lpSeleccionado.getProgresosEstudiantiles();
		Progreso progreso = progresos.get(es.getLogin());
		imprimir.imprimirProgreso(progreso);
		
		return;
	}
	
	private void verIniciarActividad(Estudiante es, ImprimirConsola imprimir, Scanner scan)
	{
		LearningPath lpSeleccionado = seleccionarLearningPathE(es, imprimir, scan);
		Progreso progreso = es.getProgresosLearningPaths().get(lpSeleccionado.getTitulo());
		Actividad actividad = seleccionarActividad(es, imprimir, scan, lpSeleccionado, true);
		if (actividad == null) {
            System.out.println("No se seleccionó una actividad válida o no hay actividades disponibles.");
            return;
        }

		try {
			progreso.empezarActividad(actividad);
		} catch (YaExisteActividadEnProgresoException e) {
			System.out.println("Ya hay una actividad en progreso");
		}
		String tipoActividad = actividad.getTipoActividad();
		boolean completado;
		switch (tipoActividad) {
		case "Prueba":
			String tipoPrueba = ((Prueba) actividad).getTipoPrueba();
			switch (tipoPrueba) {
			case "Encuesta":
				completado = iniciarEncuesta((Encuesta) actividad,scan); 
				break;
			case "Quiz Opcion Multiple":
				completado = iniciarQuizMultiple((QuizOpcionMultiple) actividad,scan); 
				break;
			case "Quiz Verdadero Falso":
				completado = iniciarQuizVF((QuizVerdaderoFalso) actividad,scan);
				break;
			case "Examen":
				completado = iniciarExamen((Examen) actividad,scan); 
				break;
			default:
				throw new IllegalArgumentException("Tipo de prueba desconocido: " + tipoPrueba);
			}
			break;
		case "Tarea":
			completado = responderTarea((Tarea)actividad,scan);
			break;
		case "Recurso Educativo":
			completado = completarRecurso((RecursoEducativo) actividad);
			break;
		default:
			throw new IllegalArgumentException("Tipo de actividad desconocido: " + tipoActividad);
		}
		if (completado) {
		try {
			progreso.completarActividad(actividad);
			progreso.desempezarActividad();
		} catch (CompletarActividadQueNoEstaEnProgresoException e) {
		    e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Hubo un error al calcular el progreso.");
		}}
		else {
			try {
				progreso.desempezarActividad();
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("Hubo un error con la actividad, vuelva a intentar con otra actividad.");
		}
		return;
	}


	private LearningPath seleccionarLearningPathD(ManejoDatos datos, ImprimirConsola imprimir, Scanner scan)
	{
		LearningPath lpSeleccionado = null;
		Map<String, LearningPath>lps = datos.getLearningPaths();
		if (!lps.isEmpty())
		{
			int i = 1;
			Map<Integer, String> indexLPs = new HashMap<Integer, String>();

			System.out.println("Learning Paths disponibles:");
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
			System.out.println("No hay ningun Learning Path para inscribir");
		}
		return lpSeleccionado;
	}
	
	private LearningPath seleccionarLearningPathE(Estudiante es, ImprimirConsola imprimir, Scanner scan)
	{
		LearningPath lpSeleccionado = null;
		Map<String, LearningPath>lps = es.getLearningPaths();
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
			System.out.println("No tiene ningun Learning Path inscrito");
		}
		return lpSeleccionado;
	}
	
	private Actividad seleccionarActividad(Estudiante es, ImprimirConsola imprimir, Scanner scan, LearningPath lpSeleccionado, boolean seleccionar)
	{
		Actividad act = null;
		String nombreLpSeleccionado = lpSeleccionado.getTitulo();

		Map<Integer, Actividad> actividades = lpSeleccionado.getActividades();
		if (!actividades.isEmpty())
		{
			Actividad actividad;
			System.out.println("\nEstos son las actividades (en el orden sugerido para completarlas) del Learning Path '"+nombreLpSeleccionado+"':\n");
		
			for (Integer orden: actividades.keySet())
			{
				actividad = actividades.get(orden);
				System.out.println(orden);
				imprimir.imprimirActividad(actividad, false, true, true);
				
			}
			if (seleccionar)
			{
				int op = -1;
				System.out.println();
				System.out.println("Seleccione el número de la actividad que quiere: ");
				op = scan.nextInt();
				if (!actividades.containsKey(op))
				{
					System.out.println("Opcion invalida. Intente de nuevo: ");
					op = scan.nextInt();
				}
				act = actividades.get(op);
			}
		}
		else
		{
			System.out.println("No hay actividades en el Learning Path '"+nombreLpSeleccionado+"'\n");
		}
		return act;
	}
	
	public boolean iniciarQuizMultiple(QuizOpcionMultiple quiz, Scanner scanner) 
	{
        List<Integer> respuestas = new ArrayList<>();

        System.out.println("Quiz: " + quiz.getTitulo());
        System.out.println("Objetivo: " + quiz.getObjetivo());
        System.out.println("-----------------------------------");
        
        if (quiz.getPreguntas().isEmpty()) {
        	System.out.println("Este quiz no tiene preguntas.");
        	return false;
        }

        // Mostrar todas las preguntas y opciones primero
        for (PreguntaMultiple pregunta : quiz.getPreguntas()) {
            System.out.println("Pregunta " + pregunta.getNumero() + ": " + pregunta.getEnunciado());

            // Mostrar opciones de respuesta
            List<String> opciones = pregunta.getOpciones();
            for (int i = 0; i < opciones.size(); i++) {
                System.out.println("   " + (i + 1) + ". " + opciones.get(i));
            }
            System.out.println("-----------------------------------");
        }

        // Solicitar respuestas para cada pregunta
        System.out.println("Ahora ingresa tus respuestas para cada pregunta:");
        for (int i = 0; i < quiz.getPreguntas().size(); i++) {
            int respuesta = -1;
            while (respuesta < 1 || respuesta > quiz.getPreguntas().get(i).getOpciones().size()) {
                System.out.print("Respuesta para la pregunta " + (i + 1) + ": ");
                try {
                    respuesta = Integer.parseInt(scanner.nextLine());
                    if (respuesta < 1 || respuesta > quiz.getPreguntas().get(i).getOpciones().size()) {
                        System.out.println("Respuesta no válida. Intenta de nuevo.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Por favor, ingresa un número válido.");
                }
            }
            respuestas.add(respuesta - 1); 
        }

        // Intentar calificar el quiz y mostrar el resultado
        try {
            quiz.responderQuiz(respuestas);
            System.out.println("Quiz completado.");
            System.out.println("Calificación: " + quiz.getCalificacion());
            System.out.println("Estado: " + quiz.getEstado());
        } catch (RespuestasInconsistentesPruebaException e) {
            System.out.println("Número de respuestas incorrecto. Inténtalo de nuevo.");
        }
        
        return true;
    }
	
	public boolean iniciarQuizVF(QuizVerdaderoFalso quiz, Scanner scanner) 
	{
        List<Boolean> respuestas = new ArrayList<>();

        System.out.println("Quiz: " + quiz.getTitulo());
        System.out.println("Objetivo: " + quiz.getObjetivo());
        System.out.println("-----------------------------------");

        if (quiz.getPreguntas().isEmpty()) {
        	System.out.println("Este quiz no tiene preguntas.");
        	return false;
        }
        
        // Mostrar todas las preguntas 
        for (PreguntaVerdaderoFalso pregunta : quiz.getPreguntas()) {
            System.out.println("Pregunta " + pregunta.getNumero() + ": " + pregunta.getEnunciado());
            System.out.println("-----------------------------------");
        }

        // Solicitar respuestas para cada pregunta
        System.out.println("Ahora ingresa tus respuestas para cada pregunta:");
        for (int i = 0; i < quiz.getPreguntas().size(); i++) {
            Boolean respuesta = null;
            while (respuesta == null) {
                System.out.print("Respuesta para la pregunta " + (i + 1) + " (V/F): ");
                String input = scanner.nextLine().trim().toUpperCase();
                
                if (input.equals("V")) {
                    respuesta = true;
                } else if (input.equals("F")) {
                    respuesta = false;
                } else {
                    System.out.println("Respuesta no válida. Por favor ingresa 'V' para Verdadero o 'F' para Falso.");
                }
            }
            respuestas.add(respuesta);
        }

        // Intentar calificar el quiz y mostrar el resultado
        try {
            quiz.responderQuiz(respuestas);
            System.out.println("Quiz completado.");
            System.out.println("Calificación: " + quiz.getCalificacion());
            System.out.println("Estado: " + quiz.getEstado());
        } catch (RespuestasInconsistentesPruebaException e) {
            System.out.println("Número de respuestas incorrecto. Inténtalo de nuevo.");
        }
        
        return true;
    }

	public boolean iniciarEncuesta(Encuesta quiz, Scanner scanner) 
	{
        List<String> respuestas = new ArrayList<>();

        System.out.println("Quiz: " + quiz.getTitulo());
        System.out.println("Descripción: " + quiz.getObjetivo());
        System.out.println("-----------------------------------");
        
        if (quiz.getPreguntas().isEmpty()) {
        	System.out.println("Este quiz no tiene preguntas.");
        	return false;
        }

        // Mostrar todas las preguntas de verdadero/falso
        for (PreguntaAbierta pregunta : quiz.getPreguntas()) {
            System.out.println("Pregunta " + pregunta.getNumero() + ": " + pregunta.getEnunciado());
            System.out.println("-----------------------------------");
        }

        // Solicitar respuestas para cada pregunta
        System.out.println("Ahora ingresa tus respuestas para cada pregunta:");
        for (int i = 0; i < quiz.getPreguntas().size(); i++) { 
            System.out.print("Respuesta para la pregunta " + (i + 1) + ": ");
            String input = scanner.nextLine().trim().toUpperCase();
            respuestas.add(input);
        }

        // Intentar calificar el quiz y mostrar el resultado
        try {
            quiz.responderEncuesta(respuestas);
            System.out.println("Quiz completado.");
            System.out.println("Calificación: " + quiz.getCalificacion());
            System.out.println("Estado: " + quiz.getEstado());
        } catch (RespuestasInconsistentesPruebaException e) {
            System.out.println("Número de respuestas incorrecto. Inténtalo de nuevo.");
        }
        
        return true;
    }

	private boolean iniciarExamen(Examen examen, Scanner scanner) 
	{	
        System.out.println("Respondiendo el examen...");
        List<String> respuestas = new ArrayList<>();
        
        for (PreguntaAbierta pregunta : examen.getPreguntas()) {
            System.out.print("Respuesta a la pregunta " + pregunta.getNumero() + ": ");
            respuestas.add(scanner.nextLine());
        }
        
        if (examen.getPreguntas().isEmpty()) {
        	System.out.println("Este examen no tiene preguntas.");
        	return false;
        }
        try {
            examen.responderExamen(respuestas);
            System.out.println("Examen respondido exitosamente.");
        } catch (RespuestasInconsistentesPruebaException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return true;
    }

	private boolean responderTarea(Tarea tarea, Scanner scanner) {
        if (tarea.isEnviado()) {
            System.out.println("La tarea ya ha sido enviada.");
            return false;
        }
        
        if (scanner.hasNextLine()) {
            scanner.nextLine();
        }

        System.out.print("Especifique el medio de entrega (por ejemplo, correo electrónico, plataforma): ");
        String medioEntrega = scanner.nextLine();
        tarea.setMedioEntrega(medioEntrega);

        tarea.setEnviado(true);
        System.out.println("La tarea ha sido marcada como enviada.");
        return true;
    }

	private boolean completarRecurso(RecursoEducativo recursoEducativo) {
        if (recursoEducativo.isCompletada()) {
            System.out.println("El recurso ya está completado.");
            return false;
        } else {
            recursoEducativo.completarActividad();
            System.out.println("El recurso ha sido marcado como completado.");
        }
        return true;
    }
	
}
