package consola;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import excepciones.*;
import modelo.*;
import modelo.actividades.Actividad;
import persistencia.*;

public class ConsolaEstudiantes {
	
	public static void main(String[] args) {
		ManejoDatos datos = new ManejoDatos();
		Scanner scanner = new Scanner(System.in);
		ConsolaEstudiantes consola = new ConsolaEstudiantes();
		ImprimirConsola imprimir = new ImprimirConsola();
		
		datos.cargarDatos();
		Map<List<String>, Usuario> usuarios = datos.getUsuarios();
		
//		consola.iniciarAplicacion(datos, scanner, imprimir);
		scanner.close(); 
	}
	
	public ConsolaEstudiantes() {
		super();
	}

	public void iniciarAplicacion(ManejoDatos datos, Scanner scan, ImprimirConsola imprimir)
	{
		int op = 1;
		Estudiante usuario = null;
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
		System.out.println("Bienvenido a la app de estudiantes");
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
		System.out.println("Bienvenido al menu de Estudiantes");
	    System.out.println("Seleccione lo que quiere hacer: ");
	    System.out.println("1. Inscribir un Learning Path");
	    System.out.println("2. Ver Learning Paths inscritos");
	    System.out.println("3. Ver actividades del Learning Path");
	    System.out.println("4. Ver progreso de Learning Path");
	    System.out.println("5. Iniciar actividad");
	    System.out.println("6. Continuar una actividad iniciada");
	    System.out.println("0. Salir de la aplicacion");
	    
	    System.out.print("Opción: ");
        op = scan.nextInt();
		return op;
	}
	
	private void routerOpciones(Estudiante es, int op, ImprimirConsola imprimir, ManejoDatos datos, Scanner scan)
	{
		switch (op)
		{
		case 0:
			System.out.println("Gracias por usar la aplicación!!!");
			
		case 1:
			verInscribirLearningPath(es, imprimir);
			
		case 2:
			verLearningPaths(es, imprimir, scan);
			
		case 3:
			verActividadesLearningPath(es, imprimir, scan);
			
		case 4:
			verProgresoLearningPath(es, imprimir, scan);
			
		case 5:
			verIniciarActividad(es, imprimir, scan);
			
		case 6:
			verContinuarActividad(es, imprimir, scan);
		}
	}
	
	/**
	 * Funcion para ver todos los Learning Paths a inscribir
	 * @param prof instancia de la sesion iniciada del estudiante
	 * @param imprimir clase para imprimir formateados los Learning Paths disponibles
	 */
	private void verInscribirLearningPath(Estudiante es, ImprimirConsola imprimir)
	{
	
	}
	
	/**
	 * Funcion para ver los estudiantes de un Learning Path específico
	 * @param prof instancia de la sesion iniciada del profesor
	 * @param imprimir
	 * @param scan
	 */
	private void verLearningPaths(Estudiante es, ImprimirConsola imprimir, Scanner scan)
	{
		
		LearningPath lpSeleccionado = seleccionarLearningPath(es, imprimir, scan);
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
	
	private void verActividadesLearningPath(Estudiante es, ImprimirConsola imprimir, Scanner scan)
	{
		
	}
	
	private void verProgresoLearningPath(Estudiante es, ImprimirConsola imprimir, Scanner scan)
	{
		
	}
	
	private void verIniciarActividad(Estudiante es, ImprimirConsola imprimir, Scanner scan)
	{
		
	}
	
	private void verContinuarActividad(Estudiante es, ImprimirConsola imprimir, Scanner scan)
	{
		
	}
	
	
	/**
	 * Funcion para iniciar sesion en la aplicacion
	 * @param datos datos de la aplicacion
	 * @param scan scanner para leer inputs
	 * @return El usuario del profesor. Si el usuario no existe o si es el usuario
	 * de un estudiante registrado, retorna null.
	 */
	private Estudiante iniciarSesion(ManejoDatos datos, Scanner scan)
	{
		Usuario usuario = null;
		Estudiante es = null;
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
		if (usuario instanceof Estudiante)
		{
			es = (Estudiante) usuario;
		}
		
		return es;
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

	private LearningPath seleccionarLearningPath(Estudiante es, ImprimirConsola imprimir, Scanner scan)
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
}
