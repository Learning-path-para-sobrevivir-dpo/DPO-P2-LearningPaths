package modelo.actividades;

import java.util.ArrayList;
import java.util.List;

import excepciones.RespuestasInconsistentesPruebaException;
import excepciones.TipoDePreguntaInvalidaException;

public class QuizOpcionMultiple extends Quiz {
	
	public List<PreguntaMultiple> preguntas;

	public QuizOpcionMultiple(String titulo, String objetivo, int nivelDificultad, int duracionMin,
			boolean obligatorio, int tiempoCompletarSugerido, String tipoActividad, float calificacionMinima,
			List<PreguntaMultiple> preguntas, String tipoPrueba) {
		super(titulo, objetivo, nivelDificultad, duracionMin, obligatorio, tiempoCompletarSugerido, tipoActividad,
				calificacionMinima, tipoPrueba);
		// TODO Auto-generated constructor stub
		this.setPreguntas(preguntas);
		this.setTipoPrueba("Quiz Opcion Multiple");
		this.setTipoActividad("Prueba");
	}
	
	public QuizOpcionMultiple(String titulo, String descripcion, int nivelDificultad, int duracionMin,
			boolean obligatorio, int tiempoCompletarSugerido, String tipoActividad, float calificacionMinima,
			List<PreguntaMultiple> preguntas, String tipoPrueba, String id, String idEstudiante) {
		super(titulo, descripcion, nivelDificultad, duracionMin, obligatorio, tiempoCompletarSugerido, tipoActividad,
				calificacionMinima, tipoPrueba, id, idEstudiante);
		// TODO Auto-generated constructor stub
		this.setPreguntas(preguntas);
		this.setTipoPrueba("Quiz Opcion Multiple");
		this.setTipoActividad("Prueba");
	}
	
	public QuizOpcionMultiple(String titulo, String descripcion, int nivelDificultad, int duracionMin,
			boolean obligatorio, int tiempoCompletarSugerido, String tipoActividad, float calificacionMinima,
			String tipoPrueba) {
		super(titulo, descripcion, nivelDificultad, duracionMin, obligatorio, tiempoCompletarSugerido, tipoActividad,
				calificacionMinima, tipoPrueba);
		// TODO Auto-generated constructor stub
		this.preguntas = new ArrayList<PreguntaMultiple>();
		this.setTipoPrueba("Quiz Opcion Multiple");
		this.setTipoActividad("Prueba");
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
	
	public PreguntaMultiple getPreguntaPorNumero(int numeroPregunta)
	{
		PreguntaMultiple p = null;
		if (numeroPregunta > 0 && numeroPregunta <= preguntas.size())
		{
			p = preguntas.get(numeroPregunta-1);
		}
		return p;
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
			pregunta.setNumero(this.preguntas.size()+1);
			this.preguntas.add((PreguntaMultiple) pregunta);
		}
		else
		{
			this.preguntas.add(numPregunta - 1, (PreguntaMultiple) pregunta);
			int i = 1;
			for (PreguntaMultiple p: preguntas)
			{
				p.setNumero(i);
				i++;
			}
		}
	}
	
	@Override
	public void eliminarPregunta(int numPregunta)
	{
		if (numPregunta > 0 && numPregunta <= this.preguntas.size())
		{
			this.preguntas.remove(numPregunta - 1);
			int i = 1;
			for (PreguntaMultiple p: preguntas)
			{
				p.setNumero(i);
				i++;
			}
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
				this.setRespondida(false);
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

}
