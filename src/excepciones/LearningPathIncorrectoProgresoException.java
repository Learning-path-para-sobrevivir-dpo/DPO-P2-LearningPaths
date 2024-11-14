package excepciones;

@SuppressWarnings("serial")
public class LearningPathIncorrectoProgresoException extends Exception {
	private String lpIncorrecto;
	private String lpEsperado;
	public LearningPathIncorrectoProgresoException(String lpIncorrecto, String lpEsperado) {
		super("");
		this.lpIncorrecto = lpIncorrecto;
		this.lpEsperado = lpEsperado;
	}
	
	@Override
    public String getMessage( )
    {
        return "Se intento obtener las actividades del learning path " + lpIncorrecto + " en un progreso del LearningPath "+lpEsperado;
        
    }
}
