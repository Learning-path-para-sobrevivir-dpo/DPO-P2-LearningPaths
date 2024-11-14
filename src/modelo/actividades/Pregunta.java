package modelo.actividades;

public class Pregunta implements Cloneable{
	
	private String enunciado;
	private int numero;
	private String tipo;

	public Pregunta(String enunciado) {
		this.enunciado = enunciado;
		this.numero = 0;
		this.tipo = null;
	}
	
	public Pregunta(String enunciado, int numero) {
		this.enunciado = enunciado;
		this.numero = numero;
		this.tipo = null;
	}

	public String getEnunciado() {
		return enunciado;
	}

	public void setEnunciado(String enunciado) {
		this.enunciado = enunciado;
	}
	
	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException
	{
		return super.clone();
	}

}
