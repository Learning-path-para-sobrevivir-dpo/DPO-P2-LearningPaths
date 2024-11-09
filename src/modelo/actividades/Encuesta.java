package modelo.actividades;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import excepciones.RespuestasInconsistentesPruebaException;
import excepciones.TipoDePreguntaInvalidaException;
import modelo.Review;

public class Encuesta extends Prueba {
	
	public List<PreguntaAbierta> preguntas;

	public Encuesta(String titulo, String descripcion, int nivelDificultad, int duracionMin, boolean obligatorio,
			int tiempoCompletarSugerido, String tipoActividad, List<PreguntaAbierta> preguntas, String tipoPrueba) {
		super(titulo, descripcion, nivelDificultad, duracionMin, obligatorio, tiempoCompletarSugerido, tipoActividad, tipoPrueba);
		this.setPreguntas(preguntas);
		this.setTipoActividad("Prueba");
		this.setTipoPrueba("Encuesta");
	}
	
	public Encuesta(String titulo, String descripcion, int nivelDificultad, int duracionMin, boolean obligatorio,
			int tiempoCompletarSugerido, String tipoActividad, String tipoPrueba) {
		super(titulo, descripcion, nivelDificultad, duracionMin, obligatorio, tiempoCompletarSugerido, tipoActividad, tipoPrueba);
		this.preguntas = new ArrayList<PreguntaAbierta>();
		this.setTipoActividad("Prueba");
		this.setTipoPrueba("Encuesta");
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
	
	public void responderEncuesta(List<String> respuestas) throws RespuestasInconsistentesPruebaException {
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
				preguntaRespondida.setCorrecta(true);
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
			this.responderEncuesta(respuestas);
		} catch (RespuestasInconsistentesPruebaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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

	@Override
	public void calcularCalificacion() {
		// TODO Auto-generated method stub
		if (this.isRespondida())
		{
			this.setCalificacion(5);
		}
	}
	
	@Override
	public void setReviews(List<Review> listaReviews) {
		this.reviews= listaReviews; 	
	}
	

}
