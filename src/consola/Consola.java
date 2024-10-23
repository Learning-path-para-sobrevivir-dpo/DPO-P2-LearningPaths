package consola;

import modelo.*;
import persistencia.*;

public class Consola {
	public static void main(String[] args) {
	Examen examen = new Examen("Parcial 1", "El parcial mas dificil de la historia", 3, 90, true, 60, "Prueba","Examen" );
	PreguntaAbierta pregunta = new PreguntaAbierta("Resuelva todos los misterios univerasales");
	examen.addPregunta(pregunta);
	Review review = new Review("2023", "Muy buena", "Profesor");
	ManejoDatos datos = new ManejoDatos();
	examen.addReview(review);
	datos.addActividad(examen);
	examen.addRating(4);
}
}