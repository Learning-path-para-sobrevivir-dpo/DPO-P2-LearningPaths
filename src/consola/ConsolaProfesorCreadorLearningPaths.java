package consola;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

import excepciones.LearningPathNoEncontradoException;
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
	
	public void iniciarAplicacion(ManejoDatos datos, Scanner scan, ImprimirConsola imprimir) throws LearningPathNoEncontradoException
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
	    System.out.println("5. Clonar actividad");
	    System.out.println("6. Crear actividad");

	    System.out.println("0. Salir de la aplicacion");
	    
	    System.out.print("Opción: ");
        op = scan.nextInt();
		return op;
	}
	
	private void routerOpciones(Profesor prof, int op, ImprimirConsola imprimir, ManejoDatos datos, Scanner scan) throws LearningPathNoEncontradoException
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
			editarLearningPaths(prof, imprimir, scan, datos);
			
			
		case 5:
			clonarActividad(prof, imprimir, scan, datos);
			
            
        case 6:
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
	
	
	public void verReseñas(Profesor prof, ImprimirConsola imprimir, Scanner scan) throws LearningPathNoEncontradoException {
	    // Obtener los Learning Paths creados por el profesor
	    Map<String, LearningPath> paths = prof.getLearningPathsCreados();

	    if (paths.isEmpty()) {
	        throw new LearningPathNoEncontradoException("No ha creado ningún Learning Path.");
	    }

	    System.out.println("Sus Learning Paths creados son:");
	    for (String titulo : paths.keySet()) {
	        System.out.println("- " + titulo);
	    }

	    String nomConsultar = "";
	    LearningPath seleccionado = null;

	    while (seleccionado == null) {
	        System.out.println("¿De cuál Learning Path desea ver las reseñas? (Escriba 'salir' para cancelar)");
	        nomConsultar = scan.nextLine().trim();

	        // Permitir salir del bucle
	        if (nomConsultar.equalsIgnoreCase("salir")) {
	            System.out.println("Operación cancelada.");
	            return;
	        }

	        // Buscar el Learning Path en el mapa
	        seleccionado = paths.get(nomConsultar);

	        // Si no existe, lanzar excepción
	        if (seleccionado == null) {
	            throw new LearningPathNoEncontradoException("El Learning Path '" + nomConsultar + "' no existe.");
	        }
	    }

	    // Mostrar las reseñas del Learning Path seleccionado
	    System.out.println("Reseñas del Learning Path '" + seleccionado.getTitulo() + "':");
	    int reseñas = seleccionado.getRating(); // Asegúrate de tener este método en la clase LearningPath
	    System.out.println(reseñas);
	    
//	    if (reseñas.isEmpty()) {
//	        System.out.println("No hay reseñas para este Learning Path.");
//	    } else {
//	        for (String reseña : reseñas) {
//	            System.out.println("- " + reseña);
//	        }
//	    }
	}


	public void editarLearningPaths(Profesor prof, ImprimirConsola imprimir, Scanner scan, ManejoDatos datos) throws LearningPathNoEncontradoException {
		
	    Map<String, LearningPath> paths = prof.getLearningPathsCreados();

	    if (paths.isEmpty()) {
	        throw new LearningPathNoEncontradoException("No ha creado ningún Learning Path.");
	    }

	    System.out.println("Sus Learning Paths creados son:");
	    for (String titulo : paths.keySet()) {
	        System.out.println("- " + titulo);
	    }

	    String nomConsultar = "";
	    LearningPath seleccionado = null;

	    while (seleccionado == null) {
	        System.out.println("¿De cuál Learning Path desea ver las reseñas? (Escriba 'salir' para cancelar)");
	        nomConsultar = scan.nextLine().trim();

	        // Buscar el Learning Path en el mapa
	        seleccionado = paths.get(nomConsultar);

	        // Si no existe, lanzar excepción
	        if (seleccionado == null) {
	            throw new LearningPathNoEncontradoException("El Learning Path '" + nomConsultar + "' no existe.");
	        }
	    }
		
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
    			String niv = scan.nextLine();
    			try {
    				nuevoNiv = Integer.parseInt(niv);
    			} catch (NumberFormatException e) {
    			    System.out.println("No se ingresó un número.");
    			}
    		}
    		seleccionado.setNivelDificultad(nuevoNiv);

            break;    
        }
              
		

	}

	
	public void añadirActividad(Profesor prof, ImprimirConsola imprimir, Scanner scan, ManejoDatos datos, LearningPath path) {
	    String nomAct = "";
	    
	    int numActs = (path.getPosActs().size());
	    int pos = -1;

	    while (nomAct.trim().isEmpty()) {
	        System.out.println("Ingrese el nombre de la actividad que desea agregar: ");
	        nomAct = scan.nextLine();
	    }
	    
	    System.out.println("Ingrese la posición a la que desea agregarla (-1 si desea dejarla al final):  ");
	    String rtaPos = scan.nextLine();
	    pos = Integer.parseInt(rtaPos);

	    // Obtener la actividad del sistema
	    Actividad actParaAñadir = datos.getActividad(nomAct);

	    // Verificar si la actividad existe
	    if (actParaAñadir == null) {
	        throw new IllegalArgumentException("Error: La actividad con nombre '" + nomAct + "' no existe.");
	    }
	    
	    if (pos>=0) {
	    	path.addActividadPorPos(actParaAñadir, pos);
	    }
	    else {
	    	path.addActividadDeUltimas(actParaAñadir);
	    }

	}
	
	
	public void eliminarActividad(Profesor prof, ImprimirConsola imprimir, Scanner scan, ManejoDatos datos, LearningPath path) {
	    int pos = 0;


	    System.out.println("Ingrese la posición de la actividad que desea eliminar:  ");
	    String rtaPos = scan.nextLine();
	    pos = Integer.parseInt(rtaPos);

   
	    path.eliminarActividadPorPos(pos);
	    

	}

	
	public void clonarActividad(Profesor prof, ImprimirConsola imprimir, Scanner scan, ManejoDatos datos) {
	    String nomAct = "";

	    while (nomAct.trim().isEmpty()) {
	        System.out.println("Ingrese el nombre de la actividad que desea clonar: ");
	        nomAct = scan.nextLine();
	    }
	    
	    Actividad actClonar = datos.getActividad(nomAct);

		
		prof.clonarActividad(actClonar);
	}
	
	
}
