package modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import excepciones.RespuestasInconsistentesPruebaException;

public class Examen extends Prueba{

	private List<PreguntaAbierta> preguntas;
	private boolean calificado;

	public Examen(String titulo, String descripcion, int nivelDificultad, int duracionMin, boolean obligatorio,
			int tiempoCompletarSugerido, String tipoActividad, List<PreguntaAbierta> preguntas, String tipoPrueba) {
		super(titulo, descripcion, nivelDificultad, duracionMin, obligatorio, tiempoCompletarSugerido, tipoActividad, tipoPrueba);
		this.preguntas = preguntas;
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

	public List<PreguntaAbierta> getPreguntas() {
		return preguntas;
	}

	public void addPregunta(PreguntaAbierta pregunta) {
		this.preguntas.add(pregunta);
	}
	
	public void addPreguntaPorPosicion(PreguntaAbierta pregunta, int pos) {
		this.preguntas.add(pos, pregunta);
	}
	
	public void eliminarPregunta(int numPregunta)
	{
		if (numPregunta > 0)
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
	
	@Override
	public void responderPrueba() {
		// TODO Auto-generated method stub
		List<String> respuestas = new ArrayList<String>();
		Scanner scanner = new Scanner(System.in);
		for (PreguntaAbierta pregunta: preguntas)
		{
			System.out.println(pregunta.getEnunciado());
			boolean entradaValida = false;
			String respuesta = null;
			while (!entradaValida)
			{
				System.out.println("Escriba su respuesta: ");
				respuesta = scanner.nextLine();
	            if (!respuesta.trim().isEmpty()) {
	                entradaValida = true;
	            } else {
	                System.out.println("La entrada no puede estar vacía. Inténtalo de nuevo.");
	            }
			}
			respuestas.add(respuesta);
		}
		scanner.close();
		try {
			this.responderExamen(respuestas);
		} catch (RespuestasInconsistentesPruebaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	protected void setReviews(List<Review> listaReviews) {
		this.reviews= listaReviews;
		
	}
	
}
