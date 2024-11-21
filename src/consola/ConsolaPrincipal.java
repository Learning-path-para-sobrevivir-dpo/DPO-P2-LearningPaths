package consola;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import excepciones.LearningPathOActividadNoEncontradoException;
import modelo.Estudiante;
import modelo.Profesor;
import modelo.Usuario;
import persistencia.ManejoDatos;

public class ConsolaPrincipal {
	
	public static void main(String[] args) {
		ManejoDatos datos = new ManejoDatos();
		Scanner scanner = new Scanner(System.in);
		ConsolaPrincipal consola = new ConsolaPrincipal();
		ConsolaProfesorSeguimientoEstudiantes consolaSeguimiento = new ConsolaProfesorSeguimientoEstudiantes();
		ConsolaProfesorCreadorLearningPaths consolaCreador = new ConsolaProfesorCreadorLearningPaths();
		ConsolaEstudiantes consolaEstudiantes = new ConsolaEstudiantes();
		ImprimirConsola imprimir = new ImprimirConsola();
		
		datos.cargarDatos();
		consola.iniciarAplicacion(datos, scanner, imprimir, consolaSeguimiento, consolaCreador, consolaEstudiantes);
	}
	
	public ConsolaPrincipal() {
		super();
	}
	
	public void iniciarAplicacion(ManejoDatos datos, Scanner scan, ImprimirConsola imprimir, 
			ConsolaProfesorSeguimientoEstudiantes consolaSeguimiento, ConsolaProfesorCreadorLearningPaths consolaCreador, 
			ConsolaEstudiantes consolaEstudiantes)
	{
		int op = 1;
		Usuario usuario = null;
		while (op != 0)
		{
			op = mostrarOpcionesInicial(scan);
			if (op == 1)
			{
				usuario = iniciarSesion(datos, scan);
				if (usuario != null)
				{
					if (usuario instanceof Estudiante)
					{
						consolaEstudiantes.iniciarAplicacion(datos, scan, imprimir, usuario);
					}
					else if (usuario instanceof Profesor)
					{
						op = mostrarOpcionesProfesor(scan);
						if (op == 1)
						{
							try {
								consolaCreador.iniciarAplicacion(datos, scan, imprimir, usuario);
							} catch (LearningPathOActividadNoEncontradoException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						else if (op == 2)
						{
							consolaSeguimiento.iniciarAplicacion(datos, scan, imprimir, usuario);
						}
					}
					op = 0;
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
		System.out.println("Bienvenido a la app");
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
	private Usuario iniciarSesion(ManejoDatos datos, Scanner scan)
	{
		Usuario usuario = null;
		String login = "";
		String password = "";
		
		System.out.println("Ingrese su nombre de usuario: ");
		while (login.trim().isEmpty())
		{
			login = scan.nextLine();
		}
		
		while (password.trim().isEmpty())
		{
			System.out.println("Ingrese su contraseña: ");
			password = scan.nextLine();
		}
		
		usuario = datos.getUsuario(login, password);
		
		return usuario;
	}
	
	/**
	 * Funcion para crear un usuario en la app
	 * @param datos
	 * @param scan
	 */
	private void crearUsuario(ManejoDatos datos, Scanner scan)
	{
		HashMap<List<String>, Usuario> usuarios = datos.getUsuarios();
		Set<List<String>> loginsContraseñas = usuarios.keySet();
		List<String> logins = new ArrayList<String>();
		
		for (List<String> lc: loginsContraseñas)
		{
			logins.add(lc.getFirst());
		}
		
		System.out.println("Seleccione el tipo de Usuario: ");
		System.out.println("1. Estudiante");
		System.out.println("2. Profesor");
		int opTipo = 0;
		opTipo = scan.nextInt();
		while (opTipo <1 || opTipo > 2)
		{
			System.out.println("Opcion invalida. Ingrese su eleccion de nuevo: ");
			opTipo = scan.nextInt();
		}
		
		String login = "";
		String password = "";
		String correo = "";
		
		while (login.trim().isEmpty())
		{
			System.out.println("Ingrese su nombre de usuario: ");
			login = scan.nextLine();
		}
		
		while (logins.contains(login))
		{
			System.out.println("El nombre de usuario ingresado ya esta en uso. Ingrese otro: ");
			login = scan.nextLine();
		}
		
		while (correo.trim().isEmpty())
		{
			System.out.println("Ingrese su correo: ");
			correo = scan.nextLine();
		}
		
		while (password.trim().isEmpty())
		{
			System.out.println("Ingrese su contraseña: ");
			password = scan.nextLine();
		}
		
		Usuario u = null;
		if (opTipo == 1)
		{
			Estudiante e = new Estudiante(login, correo, password, "Estudiante");
			u = e;
		}
		if (opTipo == 2)
		{
			Profesor p = new Profesor(login, correo, password, "Profesor");
			u = p;
		}
		
		datos.addUsuario(u);
	}
	
	private int mostrarOpcionesProfesor(Scanner scan)
	{
		int op;
		System.out.println("Bienvenido a la app de profesores");
	    System.out.println("Seleccione lo que quiere hacer: ");
	    System.out.println("1. Gestionar Learning Paths y Actividades");
	    System.out.println("2. Seguimiento de estudiantes");
	    System.out.println("0. Salir de la aplicacion");
	    
	    System.out.print("Opción: ");
        op = scan.nextInt();
		return op;
	}
}