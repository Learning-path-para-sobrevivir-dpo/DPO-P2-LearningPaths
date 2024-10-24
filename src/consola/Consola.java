package consola;

import java.util.Scanner;

import excepciones.YaExisteActividadEnProgresoException;
import modelo.Actividad;
import modelo.Estudiante;
import modelo.Examen;
import modelo.LearningPath;
import modelo.Profesor;
import modelo.Progreso;
import modelo.Quiz;
import modelo.RecursoEducativo;
import modelo.Review;
import modelo.Tarea;
import persistencia.ManejoDatos;

public class Consola {

    public static void main(String[] args) {
        ManejoDatos datos = new ManejoDatos();
        Consola consola = new Consola();
        Scanner scanner = new Scanner(System.in);

        consola.iniciarAplicacion(datos, scanner);

        scanner.close();  
    }

    public Consola() {
        super();
    }

    public void iniciarAplicacion(ManejoDatos datos, Scanner scanner) {
   
        int op = 1;
        while (op!=0) {
        	this.mostrarMenuInicio(datos, scanner); 
        	op = this.mostrarMenuInicio(datos, scanner);
        }
    }

    public int mostrarMenuInicio(ManejoDatos datos, Scanner scanner) {
        System.out.println("Bienvenido a ......");
        System.out.println("Seleccione lo que quiere hacer: ");
        System.out.println("1. Iniciar Sesión");
        System.out.println("2. Crear Perfil");
        System.out.println("3. Profesor: Crear Learning Path");
        System.out.println("4. Profesor: Crear Actividad");
        System.out.println("5. Profesor: Clonar Actividad");
        System.out.println("6. Profesor: Añadir Actividad a Learning Path");
        System.out.println("7. Estudiante: Inscribirse a Learning Path");
        System.out.println("8. Estudiante: Completar Actividad");
        System.out.println("9. Dejar Review");
        System.out.println("0. Salir");

        System.out.print("Opción: ");
        int opcion = scanner.nextInt();

        
        //Elementos a utilizar para probar el programa
        Estudiante estudiante1 = new Estudiante("Juliana Hernandez", "j.hernandez@gmail.com", "Juli091103", "Estudiante");
        Profesor profesor1 = new Profesor("Marta Martínez", "m.martinez@gmail.com", 
        		"Mart123", "Profesor");
        
        //Se crea un LP al que la estudiante ya está inscrita
        LearningPath path0 = new LearningPath ("Arte y Sociedad", "Curso creativo de arte", "Aprender sobre arte y sociedad", 
        		1, 0, "10/03/2024", "13/05/2024", 2, profesor1.getLogin());
        estudiante1.inscribirLearningPath(path0);
        Progreso pEst1LP0 = new Progreso(path0.getTitulo(), estudiante1.getLogin());
        
        //Add actividad a Path0
        Examen examenArtSoc = new Examen("Final Arte y Sociedad", "Examen final de art y soc.", 3,
        		60, true, 60, "Prueba", "Examen");
        path0.addActividadPorPos(examenArtSoc, 1);
        
        //Asumimos que estudiante 1 ya está haciendo la actividad 1 de path0
        estudiante1.iniciarActividad(1, "Arte y Sociedad");
        try {
			pEst1LP0.empezarActividad(examenArtSoc);
		} catch (YaExisteActividadEnProgresoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        LearningPath path1 = new LearningPath("Estructuras de Datos en Java", "Curso que te enseña todo sobre Estructuras de Datos en Java",
        		"Conocer todo sobre Estructuras de Datos en Java", 1, 0,
    			"22/10/2024", "22/10/2024", 1, profesor1.getLogin());
        RecursoEducativo recurso1 = new RecursoEducativo("Listas y Arreglos", "Video sobre listas y arreglos en Java", 1, 40,
        		false, 60, "Recurso Educativo", "Video", "Enlace a video sobre listas y arreglos en Java", "www.youtube.com/listasyarreglosjava");
        Tarea tarea1 = new Tarea("Ejercicios Mapas Java", "Completar estos ejercicios de mapas", 1, 40, true,
        		10, "Tarea", "1. Hallar mapas de los datos. 2. Hacer un mapa con los datos de clase.");

        datos.addActividad(tarea1);
        datos.addActividad(recurso1);
        datos.addActividad(examenArtSoc);
        datos.addLearningPath(path0);
        datos.addLearningPath(path1);
        
        // Procesamos la opción seleccionada
        switch (opcion) {
        
            case 1:
                System.out.println("Has seleccionado 'Iniciar Sesión'");
                String login1 = estudiante1.getLogin();
                boolean existe = datos.existeLogin(login1);
                if (existe) {
                	System.out.println("Inicio de Sesión exitoso!");
                }else {
                	System.out.println("Inicio de Sesión fallido :(");
                }
                break;
                
            case 2:
                System.out.println("Has seleccionado 'Crear Perfil'");
                datos.addUsuario(estudiante1);
                datos.addUsuario(profesor1);
                break;
                
            case 3:
                System.out.println("Has seleccionado 'Profesor: Crear Learning Path'");
                LearningPath path2 = profesor1.crearLearningPath("Curso básico excel", "Curso para aprender Excel.",
                		"Conocer lo básico para utilizar Excel.", 3);
                datos.addLearningPath(path2);
                System.out.println("Se creó correctamente el Learning Path"+ path2.getTitulo());
                break;
                
            case 4:
                System.out.println("Has seleccionado 'Profesor: Crear Actividad'");
                System.out.println("Cuál actividad: ");
                System.out.println("1. Recurso Educativo");
                System.out.println("2. Examen");
                System.out.println("3. Quiz");
                System.out.println("4. Encuesta");
                System.out.println("5. Tarea");
                
                System.out.print("Opción: ");
                int actop = scanner.nextInt();
                
                switch (actop) {
                
                case 1:
                    
                	Actividad rec = profesor1.crearRecursoEducativo("Recurso 1", "", 1, 1, false, 1, "", "", "", "");
                	datos.addActividad(rec);
                    break;
                    
                case 2: 
                	
                	Actividad exam = profesor1.crearExamen("Ex Prueba", "", 1, 20, true,
                	        20, "Prueba", "Examen");
                	datos.addActividad(exam);
                    break;
                    
                case 3: 
                	
                	Actividad quiz = profesor1.crearQuiz("Quiz Prueba", "", 1, 30,true,
                	        50, "Prueba","Quiz", 1);
                	datos.addActividad(quiz);
                    break;
                    
                case 4: 
                	
                	Actividad encuesta = profesor1.crearEncuesta("Encuesta Prueba", "", 2, 20, false,
                	        10, "Prueba", "Encuesta");
                	datos.addActividad(encuesta);
                    break;
                   
                case 5: 
                	
                	Actividad tarea = profesor1.crearTarea("Quiz Datos Java", "Quiz sobre datos en Java", 2, 20, true,
                	        30, "Quiz", "");
                	datos.addActividad(tarea);
                    break;
                }
                      
                break;
                
            case 5:
                System.out.println("Has seleccionado 'Profesor: Clonar Actividad'");
                
                Actividad actAClonar = datos.getActividad(tarea1.getId());
                System.out.println("Actividad a Clonar: "+ actAClonar);
                
                Actividad actClonada = profesor1.clonarActividad(actAClonar);
                datos.addActividad(actClonada);
                
                System.out.println("Actividad se clonó correctamente: " + actClonada);
                break;
               
            case 6:
                System.out.println("Has seleccionado 'Profesor: Añadir Actividad a Learning Path'");
                
                String nomLP = path0.getTitulo();
                LearningPath pathAñadir = datos.getLearningPath(nomLP);
                
                Actividad actAñadir = datos.getActividad(tarea1.getId());

                profesor1.addActividadToLearningPath(pathAñadir, actAñadir, 1);
                
                System.out.println("Las actividades actualizadas de" + pathAñadir.getTitulo()+ "son" + pathAñadir.getActividades());
               
                break;
                
            case 7:
                System.out.println("Has seleccionado 'Estudiante: Inscribirse a Learning Path'");
                
                LearningPath pathInscribir = datos.getLearningPath(path1.getTitulo());
                estudiante1.inscribirLearningPath(pathInscribir);

                System.out.println("Quedó inscrito al Path" + pathInscribir.getTitulo());
               
                break;
                
            case 8:
                System.out.println("Has seleccionado 'Estudiante: Completar Actividad'");
                
                //Asumimos que el estudiante 1 va a completar una actividad de Path 0

                Actividad actCompletar = datos.getActividad(examenArtSoc.getId());
                estudiante1.completarActividad(1, "Arte y Sociedad");
			try {
				pEst1LP0.completarActividad(actCompletar);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
                
                break;
                
            case 9:
                System.out.println("Has seleccionado 'Dejar Review'");
                
                Review revArtSoc = estudiante1.addReviewRating("El examen fue muy difícil y no evaluaba lo que vimos en clase.", examenArtSoc.getTipoActividad(), 1.0);
                Actividad actReviewed = datos.getActividad(examenArtSoc.getId());
                actReviewed.addReview(revArtSoc);
                actReviewed.addRating(revArtSoc.getRating());
                System.out.println("Reseña "+ revArtSoc.getContenido()+ " añadida a: "+ actReviewed.getTitulo());
                
                break;
                
            case 0:
                System.out.println("Saliendo...");
                System.exit(0);
                break;
                
            default:
                System.out.println("Opción no válida");
                break;
        }
        return opcion;
    }


}
