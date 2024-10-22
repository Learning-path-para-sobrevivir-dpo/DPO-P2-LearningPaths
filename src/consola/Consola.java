package consola;

import java.util.Scanner;

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

	public void iniciarAplicacion(ManejoDatos datos, Scanner scanner)
	{
		this.mostrarMenuInicio();
	}

	public void mostrarMenuInicio()
	{
		System.out.println("Bienvenido a ......");
		System.out.println("Seleccione lo que quiere hacer: ");
		System.out.println("1. Iniciar Sesión");
		System.out.println("2. Crear Perfil");
		System.out.println("3. Salir");
	}
}
