package excepciones;
/**
 * Esta excepción se utiliza cuando para una prueba en específico se intenta añadir
 * una pregunta que no es del tipo esperado
 */
@SuppressWarnings("serial")
public class TipoDePreguntaInvalidaException extends Exception {
	String tipoPregunta;
	String tipoPrueba;

	public TipoDePreguntaInvalidaException(String tipoPregunta, String tipoPrueba) {
		super("");
		this.tipoPregunta = tipoPregunta;
		this.tipoPrueba = tipoPrueba;
	}
	
	@Override
	public String getMessage( )
	{
		return "Se intento agregar una pregunta de tipo "+ tipoPregunta + " a una prueba tipo "+ tipoPrueba;
	}
}
