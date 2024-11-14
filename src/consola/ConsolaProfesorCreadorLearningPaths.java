package consola;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

import modelo.Usuario;
import persistencia.ManejoDatos;

public class ConsolaProfesorCreadorLearningPaths {

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
	
}
