package consola;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

import excepciones.LearningPathIncorrectoProgresoException;
import modelo.Estudiante;
import modelo.LearningPath;
import modelo.Profesor;
import modelo.Progreso;
import modelo.Usuario;
import modelo.actividades.Actividad;
import persistencia.ManejoDatos;

public class ConsolaProfesorSeguimientoEstudiantes {
	
	public static void main(String[] args) {
		ManejoDatos datos = new ManejoDatos();
		Scanner scanner = new Scanner(System.in);
		ConsolaProfesorSeguimientoEstudiantes consola = new ConsolaProfesorSeguimientoEstudiantes();
		
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
		Progreso pr = ue.inscribirLearningPath(lp);
		try {
			datos.addProgreso(pr);
		} catch (LearningPathIncorrectoProgresoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(ue.getLearningPaths());
		
		
//		consola.iniciarAplicacion(datos, scanner);
		scanner.close(); 
	}
	
	public ConsolaProfesorSeguimientoEstudiantes() {
		super();
	}

	public void iniciarAplicacion(ManejoDatos datos, Scanner scan)
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
	
	private void crearUsuario(ManejoDatos datos, Scanner scan)
	{
		System.out.println("");
	}
}
