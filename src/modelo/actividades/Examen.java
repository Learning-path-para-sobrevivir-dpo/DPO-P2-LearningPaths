package modelo.actividades;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import excepciones.RespuestasInconsistentesPruebaException;
import excepciones.TipoDePreguntaInvalidaException;
import modelo.Review;

public class Examen extends Prueba{

	private List<PreguntaAbierta> preguntas;
	private boolean calificado;

	public Examen(String titulo, String objetivo, int nivelDificultad, int duracionMin, boolean obligatorio,
			int tiempoCompletarSugerido, String tipoActividad, List<PreguntaAbierta> preguntas, String tipoPrueba) {
		super(titulo, objetivo, nivelDificultad, duracionMin, obligatorio, tiempoCompletarSugerido, tipoActividad, tipoPrueba);
		setPreguntas(preguntas);
		this.calificado = false;
		this.setTipoActividad("Prueba");
		this.setTipoPrueba("Examen");
	}
	
	public Examen(String titulo, String descripcion, int nivelDificultad, int duracionMin, boolean obligatorio,
			int tiempoCompletarSugerido, String tipoActividad, List<PreguntaAbierta> preguntas, String tipoPrueba, String id, String idEstudiante) {
		super(titulo, descripcion, nivelDificultad, duracionMin, obligatorio, tiempoCompletarSugerido, tipoActividad, tipoPrueba, id, idEstudiante);
		setPreguntas(preguntas);
		this.calificado = false;
		this.setTipoActividad("Prueba");
		this.setTipoPrueba("Examen");
	}

	public Examen(String titulo, String descripcion, int nivelDificultad, int duracionMin, boolean obligatorio,
			int tiempoCompletarSugerido, String tipoActividad, String tipoPrueba) {
		super(titulo, descripcion, nivelDificultad, duracionMin, obligatorio, tiempoCompletarSugerido, tipoActividad, tipoPrueba);
		this.preguntas = new ArrayList<PreguntaAbierta>();
		this.calificado = false;
		this.setTipoActividad("Prueba");
		this.setTipoPrueba("Examen");
	}
	
	public void setPreguntas(List<PreguntaAbierta> preguntas)
	{
		int i = 1;
		for(PreguntaAbierta pregunta: preguntas)
		{
			pregunta.setNumero(i);
			i++;
		}
		this.preguntas = preguntas;
	}

	public List<PreguntaAbierta> getPreguntas() {
		return preguntas;
	}
	
	@Override
	public void addPregunta(Pregunta pregunta) throws TipoDePreguntaInvalidaException {
		int numPregunta = pregunta.getNumero();
		
		if (!(pregunta instanceof PreguntaAbierta))
		{
			throw new TipoDePreguntaInvalidaException(pregunta.getTipo(), this.getTipoActividad());
		}
		
		if (numPregunta <= 0 || numPregunta > this.preguntas.size())
		{
			pregunta.setNumero(this.preguntas.size());
			this.preguntas.add((PreguntaAbierta) pregunta);
		} 
		else
		{
			this.preguntas.add(numPregunta - 1, (PreguntaAbierta) pregunta);
		}
	}
	
	@Override
	public void eliminarPregunta(int numPregunta) {
		if (numPregunta > 0 && numPregunta <= this.preguntas.size())
		{
			this.preguntas.remove(numPregunta - 1);
		}
	}

	public boolean isCalificado() {
		return calificado;
	}

	public void setCalificado(boolean calificado) {
		this.calificado = calificado;
	}

//metodos solo para cuando un estudiante clona una prueba/////
	@Override
	public void calcularCalificacion() {
		// TODO Auto-generated method stub
		if (calificado)
		{
			float calificacion;
			int preguntasCorrectas = 0;
			int preguntasTotales = this.preguntas.size();
			for (PreguntaAbierta pregunta: preguntas)
			{
				if (pregunta.isCorrecta())
				{
					preguntasCorrectas++;
				}
			}
			calificacion = preguntasCorrectas * 5 / preguntasTotales;
			this.setCalificacion(calificacion);
		}
	}
	
	public void responderExamen(List<String> respuestas) throws RespuestasInconsistentesPruebaException {
		int numRespuestasEsperadas = this.preguntas.size();
		if (numRespuestasEsperadas != respuestas.size())
		{
			throw new RespuestasInconsistentesPruebaException(numRespuestasEsperadas, respuestas.size());
		}
		
		List<PreguntaAbierta> preguntasRespondidas = new ArrayList<PreguntaAbierta>();
		PreguntaAbierta preguntaRespondida;
		for (int i = 0; i < numRespuestasEsperadas; i++)
		{
			try {
				preguntaRespondida = (PreguntaAbierta) this.preguntas.get(i).clone();
				preguntaRespondida.setRespuesta(respuestas.get(i));
				preguntasRespondidas.add(preguntaRespondida);
			} catch (CloneNotSupportedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		this.preguntas = preguntasRespondidas;
		this.setRespondida(true);
	}
	
	public void calificar(List<String> respuestasCalificadas) throws RespuestasInconsistentesPruebaException{
		int numRespuestasEsperadas = this.preguntas.size();
		if (numRespuestasEsperadas != respuestasCalificadas.size())
		{
			throw new RespuestasInconsistentesPruebaException(numRespuestasEsperadas, respuestasCalificadas.size());
		}
		if (this.isRespondida())
		{
			PreguntaAbierta preguntaRespondida;
			String calificacion;
			for (int i = 0; i < numRespuestasEsperadas; i++)
			{
				preguntaRespondida = this.preguntas.get(i);
				calificacion = respuestasCalificadas.get(i);
				if (calificacion.equals("correcta"))
				{
					preguntaRespondida.setCorrecta(true);
				} else
				{
					preguntaRespondida.setCorrecta(false);
				}
			}
			this.setCalificado(true);
		}
	}

	@Override
	public void setReviews(List<Review> listaReviews) {
		this.reviews= listaReviews;
		
	}
	
}
