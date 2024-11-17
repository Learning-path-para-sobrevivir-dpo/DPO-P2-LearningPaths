package excepciones;


@SuppressWarnings("serial")
public class LearningPathNoEncontradoException extends Exception{
	
    public LearningPathNoEncontradoException(String message) {
        super(message);
    }
}
