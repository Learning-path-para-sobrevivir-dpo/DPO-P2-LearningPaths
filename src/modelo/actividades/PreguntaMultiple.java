package modelo.actividades;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PreguntaMultiple extends Pregunta{

	private List<String> opciones;
	private int opcionCorrecta; //numero de la opción correcta
	private int opcionSeleccionada;
	
	public PreguntaMultiple(String enunciado, List<String> opciones, int opcionCorrecta) {
		super(enunciado);
		this.opciones = opciones;
		this.opcionCorrecta = opcionCorrecta - 1;
		this.opcionSeleccionada = -1;
		this.setTipo("Pregunta Multiple");
	}
	
	public PreguntaMultiple(String enunciado) {
		super(enunciado);
		this.opciones = new ArrayList<String>();
		this.opcionCorrecta = -1;
		this.setTipo("Pregunta Multiple");
	}
	
	public PreguntaMultiple(String enunciado, int numero) {
		super(enunciado);
		this.opciones = new ArrayList<String>();
		this.opcionCorrecta = 0;
		this.setNumero(numero);
		this.setTipo("Pregunta Multiple");
	}

	public List<String> getOpciones() {
		return opciones;
	}

	public void addOpcion(int pos, String opcion, boolean correcta) {
		opciones.add(pos-1, opcion);
		if (correcta)
		{
			this.opcionCorrecta = pos;
		} else
		{
			if (this.opcionCorrecta >= pos)
			{
				this.opcionCorrecta++;
			}
		}
	}
	
	public void eliminarOpcion(int pos) {
		this.opciones.remove(pos - 1);
		if (this.opcionCorrecta == pos)
		{
			this.opcionCorrecta = 0;
		} else if (this.opcionCorrecta > pos)
		{
			this.opcionCorrecta--;
		}
	}

	public int getOpcionCorrecta() {
		return opcionCorrecta;
	}

	public void setOpcionCorrecta(int opcionCorrecta) {
		this.opcionCorrecta = opcionCorrecta;
	}

	public int getOpcionSeleccionada() {
		return opcionSeleccionada;
	}

	//Solo se deben usar para las copias de actividades dentro
	//de los perfiles de los estudiantes
	public void setOpcionSeleccionada(int opcionSeleccionada) {
		this.opcionSeleccionada = opcionSeleccionada;
	}

	public void setOpciones(List<String> opciones) {
		this.opciones = opciones;
	}
	
}
