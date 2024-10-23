package consola;

import java.util.Scanner;

import modelo.Estudiante;
import modelo.LearningPath;
import modelo.Profesor;
import modelo.Progreso;
import modelo.RecursoEducativo;
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
        System.out.println("5. Profesor: Editar Actividad");
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
        LearningPath path1 = new LearningPath("Estructuras de Datos en Java", "Curso que te enseña todo sobre Estructuras de Datos en Java",
        		"Conocer todo sobre Estructuras de Datos en Java", 1, 0,
    			"22/10/2024", "22/10/2024", 1, profesor1);
        RecursoEducativo recurso1 = new RecursoEducativo("Listas y Arreglos", "Video sobre listas y arreglos en Java", 1, 40,
        		false, 60, "Recurso Educativo", "Video", "Enlace a video sobre listas y arreglos en Java", "www.youtube.com/listasyarreglosjava");
        Tarea tarea1 = new Tarea("Ejercicios Mapas Java", "Completar estos ejercicios de mapas", 1, 40, true,
        		10, "Tarea", "1. Hallar mapas de los datos. 2. Hacer un mapa con los datos de clase.");
        Progreso progresoEst1 = new Progreso(path1, estudiante1);

        
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
                datos.addLearningPath(path1);
                break;
                
            case 4:
                System.out.println("Has seleccionado 'Profesor: Crear Actividad'");
                datos.addActividad(tarea1);
                datos.addActividad(recurso1);   
                break;
                
            case 5:
                System.out.println("Has seleccionado 'Profesor: Editar Actividad'");

                break;
               
            case 6:
                System.out.println("Has seleccionado 'Profesor: Añadir Actividad a Learning Path'");
                path1.addActividadPorPos(tarea1, 1);
                path1.addActividadPorPos(recurso1,2);
                datos.actualizarLearningPath(path1);
                break;
                
            case 7:
                System.out.println("Has seleccionado 'Estudiante: Inscribirse a Learning Path'");
                estudiante1.inscribirLearningPath(path1);
                progresoEst1.obtenerActividadesPath();
                datos.actualizarUsuario(estudiante1);
                break;
                
            case 8:
                System.out.println("Has seleccionado 'Estudiante: Completar Actividad'");
                try {
                    estudiante1.completarActividad(1, "Estructuras de Datos en Java");
                    progresoEst1.completarActividad(tarea1);
                    datos.actualizarUsuario(estudiante1);
                } catch (Exception e) {
                    System.out.println("Ocurrió un error al completar la actividad: " + e.getMessage());
                }
                break;
                
            case 9:
                System.out.println("Has seleccionado 'Dejar Review'");
                
                estudiante1.addReview("La actividad fue muy buena y logré el objetivo", tarea1);
                datos.getActividadPorID(tarea1.getId());
                break;
                
            case 0:
                System.out.println("Saliendo...");
                // Lógica para salir de la aplicación
                break;
            default:
                System.out.println("Opción no válida");
                break;
        }
    }


}
