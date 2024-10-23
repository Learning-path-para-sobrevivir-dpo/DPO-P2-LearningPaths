package consola;

import java.util.Scanner;

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
        this.mostrarMenuInicio(datos, scanner);  
    }

    public void mostrarMenuInicio(ManejoDatos datos, Scanner scanner) {
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
        Estudiante estudiante1 = new Estudiante("Juliana Hernandez", "j.hernandez@gmail.com", "Juli091103", "Estudiante", null);
        Profesor profesor1 = new Profesor("Marta Martínez", "m.martinez@gmail.com", 
        		"Mart123", "Profesor");
        
        //Se crea un LP al que la estudiante ya está inscrita
        LearningPath path0 = new LearningPath ("Arte y Sociedad", "Curso creativo de arte", "Aprender sobre arte y sociedad", 
        		1, 0, "10/03/2024", "13/05/2024", 2, profesor1);
        estudiante1.inscribirLearningPath(path0);
        Progreso pEst1LP0 = new Progreso(path0, estudiante1);
        
        //Add actividad a Path0
        Examen examenArtSoc = new Examen("Final Arte y Sociedad", "Examen final de art y soc.", 3,
        		60, true, 60, "Prueba", "Examen");
        path0.addActividadPorPos(examenArtSoc, 1);
        
        //Asumimos que estudiante 1 ya está haciendo la actividad 1 de path0
        estudiante1.iniciarActividad(1, "Arte y Sociedad");
        pEst1LP0.empezarActividad(examenArtSoc);
        
        LearningPath path1 = new LearningPath("Estructuras de Datos en Java", "Curso que te enseña todo sobre Estructuras de Datos en Java",
        		"Conocer todo sobre Estructuras de Datos en Java", 1, 0,
    			"22/10/2024", "22/10/2024", 1, profesor1);
        RecursoEducativo recurso1 = new RecursoEducativo("Listas y Arreglos", "Video sobre listas y arreglos en Java", 1, 40,
        		false, 60, "Recurso Educativo", "Video", "Enlace a video sobre listas y arreglos en Java", "www.youtube.com/listasyarreglosjava");
        Tarea tarea1 = new Tarea("Ejercicios Mapas Java", "Completar estos ejercicios de mapas", 1, 40, true,
        		10, "Tarea", "1. Hallar mapas de los datos. 2. Hacer un mapa con los datos de clase.");


        
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
                Actividad quiz1 = profesor1.crearActividad("Quiz Datos Java", "Quiz sobre datos en Java", 2, 20, true,
            	        30, "Quiz");
                datos.addActividad(tarea1);
                datos.addActividad(recurso1);   
                break;
                
            case 5:
                System.out.println("Has seleccionado 'Profesor: Clonar Actividad'");
                
                Actividad actAClonar = datos.getActividadPorID(tarea1.getId());
                System.out.println("Actividad a Clonar: "+ actAClonar);
                
                Actividad actClonada = profesor1.clonarActividad(actAClonar);
                datos.addActividad(actClonada);
                
                System.out.println("Actividad se clonó correctamente: " + actClonada);
                break;
               
            case 6:
                System.out.println("Has seleccionado 'Profesor: Añadir Actividad a Learning Path'");
                
                String nomLP = path1.getTitulo();
                LearningPath pathAñadir = datos.getLearningPath(nomLP);
                
                Actividad actAñadir = datos.getActividadPorID(tarea1.getId());

                profesor1.addActividadToLearningPath(pathAñadir, actAñadir, 1);
                
                System.out.println("Las actividades actualizadas de" + pathAñadir.getTitulo()+ "son" + pathAñadir.getActividades());
               
                break;
                
            case 7:
                System.out.println("Has seleccionado 'Estudiante: Inscribirse a Learning Path'");
                
                estudiante1.inscribirLearningPath(path1);
                Progreso pEst1LP1 = new Progreso(path1, estudiante1);
                
               
                break;
                
            case 8:
                System.out.println("Has seleccionado 'Estudiante: Completar Actividad'");
                
                //Asumimos que el estudiante 1 va a completar una actividad de Path 0

                Actividad actCompletar = datos.getActividadPorID(examenArtSoc.getIdEstudiante());
                estudiante1.completarActividad(1, "Arte y Sociedad");
                pEst1LP0.completarActividad(actCompletar);
                
                break;
                
            case 9:
                System.out.println("Has seleccionado 'Dejar Review'");
                
                Review revArtSoc = estudiante1.addReviewWithRating("El examen fue muy difícil y no evaluaba lo que vimos en clase.", examenArtSoc, 1);
                Actividad actReviewed = datos.getActividadPorID(examenArtSoc.getId());
                actReviewed.addReview(revArtSoc);
                actReviewed.addRating(revArtSoc.getRating());
                break;
                
            case 0:
                System.out.println("Saliendo...");
                System.exit(0);
                break;
                
            default:
                System.out.println("Opción no válida");
                break;
        }
    }


}
