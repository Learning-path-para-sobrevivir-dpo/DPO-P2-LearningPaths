package modelo;

import java.util.ArrayList;
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
	}
	
	public PreguntaMultiple(String enunciado) {
		super(enunciado);
		this.opciones = new ArrayList<String>();
		this.opcionCorrecta = -1;
	}

	public List<String> getOpciones() {
		return opciones;
	}

	public void addOpcion(int pos, String opcion, boolean correcta) {
		this.opciones.add(pos, opcion);
		if (correcta)
		{
			this.opcionCorrecta = pos - 1;
		} else
		{
			if (this.opcionCorrecta >= pos - 1)
			{
				this.opcionCorrecta++;
			}
		}
	}
	
	public void eliminarOpcion(int pos) {
		this.opciones.remove(pos - 1);
		if (this.opcionCorrecta == pos - 1)
		{
			this.opcionCorrecta = -1;
		} else if (this.opcionCorrecta > pos - 1)
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
