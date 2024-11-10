package modelo.actividades;

import java.util.ArrayList;
import java.util.List;

import excepciones.TipoDePreguntaInvalidaException;
import modelo.Review;

public class QuizVerdaderoFalso extends Quiz {
	
	private List<PreguntaVerdaderoFalso> preguntas;

	public QuizVerdaderoFalso(String titulo, String descripcion, int nivelDificultad, int duracionMin,
			boolean obligatorio, int tiempoCompletarSugerido, String tipoActividad, float calificacionMinima,
			String tipoPrueba) {
		super(titulo, descripcion, nivelDificultad, duracionMin, obligatorio, tiempoCompletarSugerido, tipoActividad,
				calificacionMinima, tipoPrueba);
		this.setTipoPrueba("Quiz Verdadero Falso");
		this.preguntas = new ArrayList<PreguntaVerdaderoFalso>();
	}
	
	public QuizVerdaderoFalso(String titulo, String descripcion, int nivelDificultad, int duracionMin,
			boolean obligatorio, int tiempoCompletarSugerido, String tipoActividad, float calificacionMinima,
			String tipoPrueba, List<PreguntaVerdaderoFalso> preguntas) {
		super(titulo, descripcion, nivelDificultad, duracionMin, obligatorio, tiempoCompletarSugerido, tipoActividad,
				calificacionMinima, tipoPrueba);
		this.setTipoPrueba("Quiz Verdadero Falso");
		this.setPreguntas(preguntas);
	}
	
	public List<PreguntaVerdaderoFalso> getPreguntas() {
		return preguntas;
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
		}

	}

	@Override
	public void eliminarPregunta(int numPregunta) {
		if (numPregunta > 0 && numPregunta <= this.preguntas.size())
		{
			this.preguntas.remove(numPregunta - 1);
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
				if (pregunta.isRespuestaCorrecta() == pregunta.isOpcionSeleccionada())
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

}
