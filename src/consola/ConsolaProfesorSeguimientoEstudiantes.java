package consola;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import excepciones.LearningPathIncorrectoProgresoException;
import excepciones.YaExisteActividadEnProgresoException;
import modelo.Estudiante;
import modelo.LearningPath;
import modelo.Profesor;
import modelo.Progreso;
import modelo.Usuario;
import modelo.actividades.Actividad;
import modelo.actividades.Examen;
import modelo.actividades.Tarea;
import persistencia.ManejoDatos;

public class ConsolaProfesorSeguimientoEstudiantes {
	
	public static void main(String[] args) {
		ManejoDatos datos = new ManejoDatos();
		Scanner scanner = new Scanner(System.in);
		ConsolaProfesorSeguimientoEstudiantes consola = new ConsolaProfesorSeguimientoEstudiantes();
		ImprimirConsola imprimir = new ImprimirConsola();
		
		datos.cargarDatos();
		Map<List<String>, Usuario> usuarios = datos.getUsuarios();
		
		Usuario u = null;
		Profesor up;
		Estudiante ue = null;
		int i = 0;
		for (List<String> llave: usuarios.keySet())
		{
			u = usuarios.get(llave);
			System.out.println(u.getLogin());
			if (i == 0)
			{
				ue = (Estudiante) u;
			}
			i++;
		}
		up = (Profesor) u;
		Map<String, LearningPath> m = up.getLearningPathsCreados();
		System.out.println(m);
		LearningPath lp = m.get("Arte y Sociedad");
		System.out.println(lp.getActividades());
		Progreso pr = lp.getProgresosEstudiantiles().get("Juliana Hernandez");
		
		
		System.out.println(ue.getProgresosLearningPaths());
		for (String p : ue.getProgresosLearningPaths().keySet())
		{
			imprimir.imprimirProgreso(ue.getProgresosLearningPaths().get(p));
		}
		
		
//		consola.iniciarAplicacion(datos, scanner, imprimir);
		scanner.close(); 
	}
	
	public ConsolaProfesorSeguimientoEstudiantes() {
		super();
	}

	public void iniciarAplicacion(ManejoDatos datos, Scanner scan, ImprimirConsola imprimir)
	{
		int op = 1;
		Profesor usuario = null;
		while (op != 0)
		{
			op = mostrarOpcionesInicial(scan);
			if (op == 1)
			{
				usuario = iniciarSesion(datos, scan);
				if (usuario != null)
				{
					while (op != 0)
					{
						op = mostrarOpcionesApp(scan);
						routerOpciones(usuario, op, imprimir, datos, scan);
					}
					op = 1;
				}
				else
				{
					System.out.println("Contraseña o usuario incorrectos");
				}
			}
			else if (op == 2)
			{
				crearUsuario(datos, scan);
			}
		}
	}
	
	private int mostrarOpcionesInicial(Scanner scan)
	{
		int op;
		System.out.println("Bienvenido a la app de profesores");
	    System.out.println("Seleccione lo que quiere hacer: ");
	    System.out.println("1. Iniciar Sesión");
	    System.out.println("2. Crear perfil");
	    System.out.println("0. Salir de la aplicacion");
	    
	    System.out.print("Opción: ");
        op = scan.nextInt();
		return op;
	}
	
	/**
	 * Muestra las opciones de la aplicación
	 * @param scan scanner para leer inputs
	 * @return La opción seleccionada por el usuario
	 */
	private int mostrarOpcionesApp(Scanner scan)
	{
		int op;
		System.out.println("Bienvenido a Seguimiento de Estudiantes");
	    System.out.println("Seleccione lo que quiere hacer: ");
	    System.out.println("1. Ver todos mis estudiantes");
	    System.out.println("2. Ver estudiantes inscritos en un Learning Path");
	    System.out.println("3. Ver todas las actividades pendientes por calificar");
	    System.out.println("4. Ver las actividades pendientes por calificar de un Learning Path");
	    System.out.println("5. Calificar una actividad de un estudiante");
	    System.out.println("0. Salir de la aplicacion");
	    
	    System.out.print("Opción: ");
        op = scan.nextInt();
		return op;
	}
	
	private void routerOpciones(Profesor prof, int op, ImprimirConsola imprimir, ManejoDatos datos, Scanner scan)
	{
		switch (op)
		{
		case 0:
			System.out.println("Gracias por usar la aplicación!!!");
			
		case 1:
			verEstudiantesProfesor(prof, imprimir);
			
		case 2:
			verEstudiantesLearningPath(prof, imprimir, scan);
			
		case 3:
			verActividadesPendientesCalificar(prof, imprimir, scan);
			
		case 4:
			verActividadesPendientesCalificarLearningPath(prof, imprimir, scan);
			
		case 5:
			calificarActividadEstudiante(prof, imprimir, scan, datos);
		}
	}
	
	/**
	 * Funcion para ver todos los estudiantes de un profesor
	 * @param prof instancia de la sesion iniciada del profesor
	 * @param imprimir clase para imprimir formateados los progresos de estudiantes del profesor
	 */
	private void verEstudiantesProfesor(Profesor prof, ImprimirConsola imprimir)
	{
		Map<String, LearningPath> lps = prof.getLearningPathsCreados();
		LearningPath lp;
		Progreso progEstudiante;
		boolean hayEstudiantes = false;
		for (String nombreLp: lps.keySet())
		{
			lp = lps.get(nombreLp);
			List<String> estudiantesLp = lp.getEstudiantes();
			Map<String, Progreso> progEstudiantes = lp.getProgresosEstudiantiles();
			if (!hayEstudiantes && !progEstudiantes.isEmpty())
			{
				hayEstudiantes = true;
			}
			for (String loginEst: estudiantesLp)
			{
				progEstudiante = progEstudiantes.get(loginEst);
				if (progEstudiante != null)
					imprimir.imprimirProgreso(progEstudiante);
			}
		}
		if (!hayEstudiantes)
			System.out.println("No tiene estudiantes inscritos");
		System.out.println();
	}
	
	/**
	 * Funcion para ver los estudiantes de un Learning Path específico
	 * @param prof instancia de la sesion iniciada del profesor
	 * @param imprimir
	 * @param scan
	 */
	private void verEstudiantesLearningPath(Profesor prof, ImprimirConsola imprimir, Scanner scan)
	{
		
		LearningPath lpSeleccionado = seleccionarLearningPaths(prof, imprimir, scan);
		if (lpSeleccionado != null)
		{
			String nombreLpSeleccionado = lpSeleccionado.getTitulo();
			Map<String, Progreso> progEstudiantes = lpSeleccionado.getProgresosEstudiantiles();
			if (!progEstudiantes.isEmpty())
			{
				System.out.println("\nEstos son los estudiantes para el Learning Path '"+nombreLpSeleccionado+"'\n");
				Progreso progEstudiante;
				for (String loginEst: progEstudiantes.keySet())
				{
					progEstudiante = progEstudiantes.get(loginEst);
					if (progEstudiante != null)
						imprimir.imprimirProgreso(progEstudiante);
				}
			}
			else
			{
				System.out.println("No hay estudiantes inscritos al Learning Path '"+nombreLpSeleccionado+"'\n");
			}
		}
	}
	
	private void verActividadesPendientesCalificar(Profesor prof, ImprimirConsola imprimir, Scanner scan)
	{
		Map<String, LearningPath>lps = prof.getLearningPathsCreados();
		if (!lps.isEmpty())
		{
			LearningPath lp;
			for (String nomLp: lps.keySet())
			{
				lp = lps.get(nomLp);
				seleccionarMostrarActividadesPendientes(prof, imprimir, scan, lp, false);
			}
		}
		else
		{
			System.out.println("No tiene Learning Paths creados");
		}
	}
	
	private void verActividadesPendientesCalificarLearningPath(Profesor prof, ImprimirConsola imprimir, Scanner scan)
	{
		LearningPath lpSeleccionado = seleccionarLearningPaths(prof, imprimir, scan);
		if (lpSeleccionado != null)
		{
			seleccionarMostrarActividadesPendientes(prof, imprimir, scan, lpSeleccionado, false);
		}
	}
	
	private void calificarActividadEstudiante(Profesor prof, ImprimirConsola imprimir, Scanner scan, ManejoDatos datos)
	{
		System.out.println("Seleccione el Learning Path al que pertenece el estudiante: \n");
		LearningPath lpSeleccionado = seleccionarLearningPaths(prof, imprimir, scan);
		if (lpSeleccionado != null)
		{
			Map<String, Actividad> seleccionado = seleccionarMostrarActividadesPendientes(prof, imprimir, scan, lpSeleccionado, true);
			if (!seleccionado.isEmpty())
			{
				String estudiante = null;
				Actividad act = null;
				for (String e: seleccionado.keySet())
				{
					estudiante = e;
					act = seleccionado.get(e);
				}
				if (estudiante != null && act != null)
				{
					System.out.println("La actividad del estudiante "+ estudiante + " escogida es: ");
					imprimir.imprimirActividad(act, false, true, true);
					System.out.println("Desea calificarla?(y/n): ");
					String op = scan.nextLine();
					if (!op.equals("y") || !op.equals("n"))
					{
						System.out.println("Opcion invalida. Intente de nuevo: ");
						op = scan.nextLine();
					}
					if (op.equals("y"))
					{
						calificar(scan, act, datos, lpSeleccionado, estudiante, imprimir);
					}
					else
					{
						System.out.println("Regresando al menu...\n");
					}
				}
				
			}
		}
	}
	
	private void calificar(Scanner scan, Actividad act, ManejoDatos datos, LearningPath lp, String loginEstudiante, ImprimirConsola imprimir)
	{
		int op;
		Progreso progreso = datos.obtenerProgreso(lp.getTitulo(), loginEstudiante);
		if (act instanceof Tarea)
		{
			System.out.println("Como desea marcar la Tarea: ");
			System.out.println("1. Exitosa");
			System.out.println("2. No Exitosa");
			System.out.println("\n Escoja el número de la opción que desea: ");
			op = scan.nextInt();
			if (op < 1 || op > 2)
			{
				System.out.println("Opcion invalida. Intente de nuevo: ");
				op = scan.nextInt();
			}
			if (op == 1)
			{
				act.setEstado("Exitosa");
			}
			else if (op == 2)
			{
				act.setEstado("No Exitosa");
				progreso.descompletarActividad(act);
			}
			datos.actualizarActividad(act);
			try {
				datos.addProgreso(progreso);
			} catch (LearningPathIncorrectoProgresoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			datos.actualizarLearningPath(lp);
		}
		else if (act instanceof Examen)
		{
			Examen ex = (Examen) act;
			imprimir.imprimirPreguntasyRespuestasExamen(ex);
			System.out.println("Ingrese la calificacion (ingreselo como un número decimal con punto):");
			float calificacion = scan.nextFloat();
			ex.setCalificacion(calificacion);
			act = ex;
			System.out.println("Como desea marcar el Examen: ");
			System.out.println("1. Exitosa");
			System.out.println("2. No Exitosa");
			System.out.println("\n Escoja el número de la opción que desea: ");
			op = scan.nextInt();
			if (op < 1 || op > 2)
			{
				System.out.println("Opcion invalida. Intente de nuevo: ");
				op = scan.nextInt();
			}
			if (op == 1)
			{
				act.setEstado("Exitosa");
			}
			else if (op == 2)
			{
				act.setEstado("No Exitosa");
				progreso.descompletarActividad(act);
			}
			datos.actualizarActividad(act);
			try {
				datos.addProgreso(progreso);
			} catch (LearningPathIncorrectoProgresoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			datos.actualizarLearningPath(lp);
		}
		else
		{
			System.out.println("La actividad no es calificable");
		}
	}
	
	private LearningPath seleccionarLearningPaths(Profesor prof, ImprimirConsola imprimir, Scanner scan)
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
			System.out.println("No tiene ningun Learning Path creado");
		}
		return lpSeleccionado;
	}
	
	private Map<String, Actividad> seleccionarMostrarActividadesPendientes(Profesor prof, ImprimirConsola imprimir, Scanner scan, LearningPath lpSeleccionado, boolean seleccionar)
	{
		Map<String, Actividad> actEstudiante = new HashMap<String, Actividad>();
		Actividad act = null;
		String e = "";
		String nombreLpSeleccionado = lpSeleccionado.getTitulo();
		String num = "";
		int i = 1;
		Map<String, Progreso> progEstudiantes = lpSeleccionado.getProgresosEstudiantiles();
		if (!progEstudiantes.isEmpty())
		{
			Progreso progEstudiante;
			System.out.println("\nEstos son las actividades pendientes por calificar para el Learning Path '"+nombreLpSeleccionado+"' por cada estudiante inscrito:\n");
			Map<Integer, Actividad> acts = new HashMap<Integer, Actividad>();
			Map<Integer, String> estudiantes = new HashMap<Integer, String>();
			for (String loginEst: progEstudiantes.keySet())
			{
				progEstudiante = progEstudiantes.get(loginEst);
				System.out.println("Estudiante: "+ loginEst);
				boolean tieneActividades = false;
				for(Actividad actCompletada: progEstudiante.getActCompletadas())
				{
					if (actCompletada.getEstado().equals("Sin completar") || actCompletada.getEstado().equals("No Exitosa") )
					{
						num = Integer.toString(i) + ". ";					
						i++;
						acts.put(i, actCompletada);
						estudiantes.put(i, loginEst);
						System.out.print(num);
						imprimir.imprimirActividad(actCompletada, false, true, true);
						tieneActividades = true;	
					}
				}
				if (!tieneActividades)
				{
					System.out.println("No tiene actividades pendientes por calificar\n");
				}
			}
			if (seleccionar)
			{
				int op = -1;
				System.out.println();
				System.out.println("Seleccione el número de la actividad que quiere calificar: ");
				op = scan.nextInt();
				if (!acts.containsKey(op))
				{
					System.out.println("Opcion invalida. Intente de nuevo: ");
					op = scan.nextInt();
				}
				act = acts.get(op);
				e = estudiantes.get(op);
				actEstudiante.put(e, act);
			}
		}
		else
		{
			System.out.println("No hay actividades pendientes por calificar en el Learning Path '"+nombreLpSeleccionado+"'\n");
		}
		return actEstudiante;
	}
	
	/**
	 * Funcion para iniciar sesion en la aplicacion
	 * @param datos datos de la aplicacion
	 * @param scan scanner para leer inputs
	 * @return El usuario del profesor. Si el usuario no existe o si es el usuario
	 * de un estudiante registrado, retorna null.
	 */
	private Profesor iniciarSesion(ManejoDatos datos, Scanner scan)
	{
		Usuario usuario = null;
		Profesor prof = null;
		String login = "";
		String password = "";
		
		while (login.trim().isEmpty())
		{
			System.out.println("Ingrese su nombre de usuario: ");
			login = scan.nextLine();
		}
		
		while (password.trim().isEmpty())
		{
			System.out.println("Ingrese su contraseña: ");
			password = scan.nextLine();
		}
		
		usuario = datos.getUsuario(login, password);
		if (usuario instanceof Profesor)
		{
			prof = (Profesor) usuario;
		}
		
		return prof;
	}
	
	/**
	 * Funcion para crear un usuario en la app
	 * @param datos
	 * @param scan
	 */
	private void crearUsuario(ManejoDatos datos, Scanner scan)
	{
		System.out.println("");
	}
}
