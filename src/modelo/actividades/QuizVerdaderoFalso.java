package modelo.actividades;

import java.util.ArrayList;
import java.util.List;

import excepciones.RespuestasInconsistentesPruebaException;
import excepciones.TipoDePreguntaInvalidaException;

public class QuizVerdaderoFalso extends Quiz {
	
	private List<PreguntaVerdaderoFalso> preguntas;

	public QuizVerdaderoFalso(String titulo, String objetivo, int nivelDificultad, int duracionMin,
			boolean obligatorio, int tiempoCompletarSugerido, String tipoActividad, float calificacionMinima,
			String tipoPrueba) {
		super(titulo, objetivo, nivelDificultad, duracionMin, obligatorio, tiempoCompletarSugerido, tipoActividad,
				calificacionMinima, tipoPrueba);
		this.setTipoPrueba("Quiz Verdadero Falso");
		this.setTipoActividad("Prueba");
		this.preguntas = new ArrayList<PreguntaVerdaderoFalso>();
	}
	
	public QuizVerdaderoFalso(String titulo, String descripcion, int nivelDificultad, int duracionMin,
			boolean obligatorio, int tiempoCompletarSugerido, String tipoActividad, float calificacionMinima,
			String tipoPrueba, List<PreguntaVerdaderoFalso> preguntas) {
		super(titulo, descripcion, nivelDificultad, duracionMin, obligatorio, tiempoCompletarSugerido, tipoActividad,
				calificacionMinima, tipoPrueba);
		this.setTipoActividad("Prueba");
		this.setTipoPrueba("Quiz Verdadero Falso");
		this.setPreguntas(preguntas);
	}
	
	public QuizVerdaderoFalso(String titulo, String descripcion, int nivelDificultad, int duracionMin,
			boolean obligatorio, int tiempoCompletarSugerido, String tipoActividad, float calificacionMinima,
			String tipoPrueba, List<PreguntaVerdaderoFalso> preguntas, String id, String idEstudiante) {
		super(titulo, descripcion, nivelDificultad, duracionMin, obligatorio, tiempoCompletarSugerido, tipoActividad,
				calificacionMinima, tipoPrueba, id, idEstudiante);
		this.setTipoPrueba("Quiz Verdadero Falso");
		this.setTipoActividad("Prueba");
		this.setPreguntas(preguntas);
	}
	
	public List<PreguntaVerdaderoFalso> getPreguntas() {
		return preguntas;
	}
	
	public PreguntaVerdaderoFalso obtenerPreguntaPorNumero(int numeroPregunta)
	{
		PreguntaVerdaderoFalso pregunta = null;
		if (numeroPregunta > 0 && numeroPregunta <= preguntas.size())
		{
			pregunta = preguntas.get(numeroPregunta-1);
		}
		return pregunta;
	}

	public void setPreguntas(List<PreguntaVerdaderoFalso> preguntas) {
		int i = 1;
		for(PreguntaVerdaderoFalso pregunta: preguntas)
		{
			pregunta.setNumero(i);
			i++;
		}
		this.preguntas = preguntas;
	}
	
	@Override
	public void addPregunta(Pregunta pregunta) throws TipoDePreguntaInvalidaException {
		if (!(pregunta instanceof PreguntaVerdaderoFalso))
		{
			throw new TipoDePreguntaInvalidaException(pregunta.getTipo(), this.getTipoPrueba());
		}
		
		int numPregunta = pregunta.getNumero();
		if (numPregunta <= 0 || numPregunta > this.preguntas.size())
		{
			pregunta.setNumero(this.preguntas.size());
			this.preguntas.add((PreguntaVerdaderoFalso) pregunta);
		}
		else
		{
			this.preguntas.add(numPregunta - 1, (PreguntaVerdaderoFalso) pregunta);
			int i = 1;
			for (PreguntaVerdaderoFalso p: preguntas)
			{
				p.setNumero(i);
				i++;
			}
		}

	}

	@Override
	public void eliminarPregunta(int numPregunta) {
		if (numPregunta > 0 && numPregunta <= this.preguntas.size())
		{
			this.preguntas.remove(numPregunta - 1);
			int i = 1;
			for (PreguntaVerdaderoFalso p: preguntas)
			{
				p.setNumero(i);
				i++;
			}
		}
	}

	@Override
	public void calcularCalificacion() {
		if (this.isRespondida())
		{
			float calificacionObtenida;
			int respuestasCorrectas = 0;
			int cantidadPreguntas = this.preguntas.size();
			for (PreguntaVerdaderoFalso pregunta: this.preguntas)
			{
				if (pregunta.isCorrecta())
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
				this.setRespondida(false);
			}
		}
	}
	
	public void responderQuiz(List<Boolean> respuestas) throws RespuestasInconsistentesPruebaException{
		// TODO Auto-generated method stub
		int numRespuestasEsperadas = this.preguntas.size();
		if (numRespuestasEsperadas != respuestas.size())
		{
			throw new RespuestasInconsistentesPruebaException(numRespuestasEsperadas, respuestas.size());
		}
		List<PreguntaVerdaderoFalso> preguntasRespondidas = new ArrayList<PreguntaVerdaderoFalso>();
		PreguntaVerdaderoFalso preguntaRespondida;
		for (int i = 0; i < numRespuestasEsperadas; i++)
		{
			try {
				preguntaRespondida = (PreguntaVerdaderoFalso) this.preguntas.get(i).clone();
				preguntaRespondida.setOpcionSeleccionada(respuestas.get(i));
				preguntaRespondida.verificarCorrecta();
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

}
