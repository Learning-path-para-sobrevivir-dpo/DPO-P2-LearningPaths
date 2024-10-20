package excepciones;

/**
 * Esta excepcion se utiliza para cuando un estudiante responde una prueba y cuando se actualiza la prueba
 * hay un número de respuestas recibido que no coincide con el número de preguntas
 */
@SuppressWarnings("serial")
public class RespuestasInconsistentesPruebaException extends Exception {
	
	Integer numRespuestasEsperado;
	Integer numRespuestasObtenido;
	public RespuestasInconsistentesPruebaException(int numRespuestasEsperado, int numRespuestasObtenido) {
		super("");
		this.numRespuestasEsperado = numRespuestasEsperado;
		this.numRespuestasObtenido = numRespuestasObtenido;
	}
	
	@Override
    public String getMessage( )
    {
        return "Número de respuestas recibidas incorrecto: se esperaban " + Integer.toString(this.numRespuestasEsperado) + "  respuestas y se recibieron " + Integer.toString(this.numRespuestasObtenido);
    }
}
