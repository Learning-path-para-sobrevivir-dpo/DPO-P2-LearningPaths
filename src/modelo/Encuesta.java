package modelo;

import java.util.ArrayList;
import java.util.List;

import excepciones.RespuestasInconsistentesPruebaException;

public class Encuesta extends Prueba {
	
	public List<PreguntaAbierta> preguntas;

	public Encuesta(String titulo, String descripcion, int nivelDificultad, int duracionMin, boolean obligatorio,
			int tiempoCompletarSugerido, String tipoActividad, List<PreguntaAbierta> preguntas) {
		super(titulo, descripcion, nivelDificultad, duracionMin, obligatorio, tiempoCompletarSugerido, tipoActividad);
		this.preguntas = preguntas;
	}
	
	public Encuesta(String titulo, String descripcion, int nivelDificultad, int duracionMin, boolean obligatorio,
			int tiempoCompletarSugerido, String tipoActividad) {
		super(titulo, descripcion, nivelDificultad, duracionMin, obligatorio, tiempoCompletarSugerido, tipoActividad);
		this.preguntas = new ArrayList<PreguntaAbierta>();
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
	public void calcularCalificacion() {
		// TODO Auto-generated method stub
		if (this.isRespondida())
		{
			this.setCalificacion(5);
		}
	}
	

}
