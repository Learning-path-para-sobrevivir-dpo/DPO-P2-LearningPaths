package consola;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

import modelo.LearningPath;
import modelo.Profesor;
import modelo.Progreso;
import modelo.Usuario;
import modelo.actividades.Actividad;
import persistencia.ManejoDatos;

public class ConsolaProfesorCreadorLearningPaths {

	public static void main(String[] args) {
		ManejoDatos datos = new ManejoDatos();
		Scanner scanner = new Scanner(System.in);
		ConsolaProfesorCreadorLearningPaths consola = new ConsolaProfesorCreadorLearningPaths();
		ImprimirConsola imprimir = new ImprimirConsola();
		
		datos.cargarDatos();
		Map<List<String>, Usuario> usuarios = datos.getUsuarios();
		
//		consola.iniciarAplicacion(datos, scanner, imprimir);
		scanner.close(); 
	}

	public ConsolaProfesorCreadorLearningPaths() {
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
	    System.out.println("5. Añadir actividades a Learning Path");
	    System.out.println("6. Clonar actividad");
	    System.out.println("7. Crear actividad");

	    System.out.println("0. Salir de la aplicacion");
	    
	    System.out.print("Opción: ");
        op = scan.nextInt();
		return op;
	}
	
	private void routerOpciones(Profesor prof, int op, ImprimirConsola imprimir, ManejoDatos datos, Scanner scan)
	{
		switch (op)
		{
			
		case 1:
			verLearningPathsCreados(prof, imprimir);
			
		case 2:
			crearLearningPath(prof, imprimir, scan);
			
		case 3:
			verReseñas(prof, imprimir, scan);
			
		case 4:
			editarLearningPaths(prof, imprimir, scan);
			
		case 5:
			añadirActividadesLearningPath(prof, imprimir, scan, datos);
			
		case 6:
			clonarActividad(prof, imprimir, scan, datos);
			
            
        case 7:
            System.out.println("Has seleccionado 'Crear Actividad'");
            System.out.println("Cuál actividad?: ");
            System.out.println("1. Recurso Educativo");
            System.out.println("2. Examen");
            System.out.println("3. Quiz");
            System.out.println("4. Encuesta");
            System.out.println("5. Tarea");
            
            System.out.print("Opción: ");
            int actop = scan.nextInt();
            
            switch (actop) {
            
            case 1:
                
                break;
                
            case 2: 

                break;
                
            case 3: 

                break;
                
            case 4: 

                break;
               
            case 5: 

                break;
            }
                  
            break;
						
        case 0:
            System.out.println("Gracias por usar la aplicación! Saliendo...");
            System.exit(0);
            break;
            
        default:
            System.out.println("Opción no válida");
            break;
		}
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
	            imprimir.imprimirLearningPath(lp); 
	        }
	    } else {
	        System.out.println("No tiene Learning Paths creados");
	    }
	    System.out.println();
	}

	
	private void crearLearningPath(Profesor prof, ImprimirConsola imprimir, Scanner scan) {
		
		String titulo = "";
		String descripcion = "";
		String objetivo = "";
		int nivelDificultad = 0;
		
		
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
			String niv = scan.nextLine();
			try {
				nivelDificultad = Integer.parseInt(niv);
			} catch (NumberFormatException e) {
			    System.out.println("No se ingresó un número.");
			}
		}
		
		
		prof.crearLearningPath(titulo, descripcion, objetivo, nivelDificultad);
	}
	
	
	
	
	
}
