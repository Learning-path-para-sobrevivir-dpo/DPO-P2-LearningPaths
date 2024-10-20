package modelo;

public class Pregunta implements Cloneable{
	
	private String enunciado;

	public Pregunta(String enunciado) {
		this.enunciado = enunciado;
	}

	public String getEnunciado() {
		return enunciado;
	}

	public void setEnunciado(String enunciado) {
		this.enunciado = enunciado;
	}
	
	@Override
	protected Object clone() throws CloneNotSupportedException
	{
		return super.clone();
	}

}
