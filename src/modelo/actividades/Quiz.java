package modelo.actividades;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import excepciones.RespuestasInconsistentesPruebaException;
import excepciones.TipoDePreguntaInvalidaException;
import modelo.Review;

public class Quiz extends Prueba{

	public float calificacionMinima;
	public List<PreguntaMultiple> preguntas;
	
	public Quiz(String titulo, String descripcion, int nivelDificultad, int duracionMin, boolean obligatorio,
			int tiempoCompletarSugerido, String tipoActividad, float calificacionMinima,
			List<PreguntaMultiple> preguntas, String tipoPrueba) {
		super(titulo, descripcion, nivelDificultad, duracionMin, obligatorio, tiempoCompletarSugerido, tipoActividad, tipoPrueba);
		this.calificacionMinima = calificacionMinima;
		this.setPreguntas(preguntas);
		this.setTipoActividad("Prueba");
		this.setTipoPrueba("Quiz");
	}
	
	public Quiz(String titulo, String descripcion, int nivelDificultad, int duracionMin, boolean obligatorio,
			int tiempoCompletarSugerido, String tipoActividad, float calificacionMinima, String tipoPrueba) {
		super(titulo, descripcion, nivelDificultad, duracionMin, obligatorio, tiempoCompletarSugerido, tipoActividad, tipoPrueba);
		this.calificacionMinima = calificacionMinima;
		this.preguntas = new ArrayList<PreguntaMultiple>();
		this.setTipoActividad("Prueba");
		this.setTipoPrueba("Quiz");
	}
	
	public float getCalificacionMinima() {
		return calificacionMinima;
	}

	public void setCalificacionMinima(float calificacionMinima) {
		this.calificacionMinima = calificacionMinima;
	}
	
	public void setPreguntas(List<PreguntaMultiple> preguntas)
	{
		int i = 1;
		for(PreguntaMultiple pregunta: preguntas)
		{
			pregunta.setNumero(i);
			i++;
		}
		this.preguntas = preguntas;
	}

	public List<PreguntaMultiple> getPreguntas() {
		return preguntas;
	}
	
	@Override
	public void addPregunta(Pregunta pregunta) throws TipoDePreguntaInvalidaException {
		if (!(pregunta instanceof PreguntaMultiple))
		{
			throw new TipoDePreguntaInvalidaException(pregunta.getTipo(), this.getTipoPrueba());
		}
		
		int numPregunta = pregunta.getNumero();
		if (numPregunta <= 0 || numPregunta > this.preguntas.size())
		{
			pregunta.setNumero(this.preguntas.size());
			this.preguntas.add((PreguntaMultiple) pregunta);
		}
		else
		{
			this.preguntas.add(numPregunta - 1, (PreguntaMultiple) pregunta);
		}
	}
	
	@Override
	public void eliminarPregunta(int numPregunta)
	{
		if (numPregunta > 0 && numPregunta <= this.preguntas.size())
		{
			this.preguntas.remove(numPregunta - 1);
		}
	}
	
// Solo usar estos métodos en las copias de las actividades en los usuarios de los estudiantes////
	@Override
	public void calcularCalificacion() {
		// TODO Auto-generated method stub
		if (this.isRespondida())
		{
			float calificacionObtenida;
			int respuestasCorrectas = 0;
			int cantidadPreguntas = this.preguntas.size();
			for (PreguntaMultiple pregunta: this.preguntas)
			{
				if (pregunta.getOpcionCorrecta() == pregunta.getOpcionSeleccionada())
				{
					respuestasCorrectas++;
				}
			}
			calificacionObtenida = respuestasCorrectas * 5 / cantidadPreguntas;
			this.setCalificacion(calificacionObtenida);
			if (this.getCalificacion() >= this.calificacionMinima)
			{
				this.setEstado("Exitosa");
			}
			else
			{
				this.setEstado("No Exitosa");
			}
		}
	}

	public void responderQuiz(List<Integer> respuestas) throws RespuestasInconsistentesPruebaException{
		// TODO Auto-generated method stub
		int numRespuestasEsperadas = this.preguntas.size();
		if (numRespuestasEsperadas != respuestas.size())
		{
			throw new RespuestasInconsistentesPruebaException(numRespuestasEsperadas, respuestas.size());
		}
		List<PreguntaMultiple> preguntasRespondidas = new ArrayList<PreguntaMultiple>();
		PreguntaMultiple preguntaRespondida;
		for (int i = 0; i < numRespuestasEsperadas; i++)
		{
			try {
				preguntaRespondida = (PreguntaMultiple) this.preguntas.get(i).clone();
				preguntaRespondida.setOpcionSeleccionada(respuestas.get(i));
				preguntasRespondidas.add(preguntaRespondida);
			} catch (CloneNotSupportedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		this.preguntas = preguntasRespondidas;
		this.setRespondida(true);
		this.calcularCalificacion();
	}

	@Override
	public void responderPrueba() {
		// TODO Auto-generated method stub
		List<Integer> respuestas = new ArrayList<Integer>();
		Scanner scanner = new Scanner(System.in);
		for (PreguntaMultiple pregunta: preguntas)
		{
			System.out.println(pregunta.getEnunciado());
			int i = 0;
			for (String opcion: pregunta.getOpciones())
			{
				System.out.println(Integer.toString(i) + ". " + opcion);
				i++;
			}
			boolean entradaValida = false;
			int respuesta = 1;
			while (!entradaValida)
			{
				System.out.println("Escoja el número de su respuesta: ");
				if (scanner.hasNextInt()) {
	                respuesta = scanner.nextInt();
	                entradaValida = true;
	            } else {
	                System.out.println("Eso no es una respuesta válida. Inténtalo de nuevo.");
	                scanner.next(); // Descartar la entrada no válida
	            }
			}
			respuestas.add(respuesta);
		}
		scanner.close();
		try {
			this.responderQuiz(respuestas);
		} catch (RespuestasInconsistentesPruebaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setReviews(List<Review> listaReviews) {
		this.reviews= listaReviews; 	
	}
	
}
