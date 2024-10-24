package modelo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LearningPath {
	
	public String titulo;
	public String descripcion;
	public String objetivo;
	public int nivelDificultad;
	public int duracion;
	public int rating;
	public String fechaCreacion;
	public String fechaModificacion;
	public int version;
	private Map<String, Progreso> progresosEstudiantiles;
	private String autor;  //Cambie Profesor por String
	private List<String> estudiantes; //Galarza: Cambie lista de estudiante por lista de strings
	//Mapa donde las actividades estan identificadas por un número que indica
	//el orden sugerido para completar las actividades
	private Map<Integer,Actividad> actividades;
	
	public LearningPath(String titulo, String descripcion, String objetivo, int nivelDificultad, int rating,
			String fechaCreacion, String fechaModificacion, int version, String autor) {
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.objetivo = objetivo;
		this.nivelDificultad = nivelDificultad;
		this.duracion = 0;
		this.rating = rating;
		this.fechaCreacion = fechaCreacion;
		this.fechaModificacion = fechaModificacion;
		this.version = version;
		this.progresosEstudiantiles = new HashMap<String, Progreso>();
		this.autor = autor;
		this.estudiantes = new ArrayList<String>();
		this.actividades = new HashMap<Integer,Actividad>();
	}
	

	//Getters y Setters
	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getObjetivo() {
		return objetivo;
	}

	public void setObjetivo(String objetivo) {
		this.objetivo = objetivo;
	}

	public int getNivelDificultad() {
		return nivelDificultad;
	}

	public void setNivelDificultad(int nivelDificultad) {
		this.nivelDificultad = nivelDificultad;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public String getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(String fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public String getFechaModificacion() {
		return fechaModificacion;
	}

	public void setFechaModificacion(String fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public Map<String, Progreso> getProgresosEstudiantiles() {
		return progresosEstudiantiles;
	}

	public String getAutor() {
		return autor;
	}

	public List<String> getEstudiantes() {
		return estudiantes;
	}

	public void setEstudiantes(List<String> estudiantes) {
		this.estudiantes = estudiantes;
	}

	public Map<Integer, Actividad> getActividades() {
		return actividades;
	}

	public void setActividades(Map<Integer,Actividad> actividades) {
		this.actividades = actividades;
		this.calcularDuracion();
	}
	
	public int getDuracion() {
		return duracion;
	}
	
	/**
	 * Calcula la duración aproximada del Learning Path de acuerdo
	 * a a la duración de sus actividades
	 */
    private void calcularDuracion() {
        int nuevaDuracion = 0;

        for (Actividad act : actividades.values()) {
            // Sumar la duración de cada actividad
            nuevaDuracion += act.getDuracionMin();
        }

        // Actualizar duracion
        this.duracion = nuevaDuracion;
    }
    
    /**
     * Añade una actividad de últimas en el orden enumerado de actividades en el Learning Path
     * @param act: actividad a añadir
     */
    public void addActividadDeUltimas(Actividad act)
    {
    	int numActividades = this.actividades.size();
    	this.actividades.put(numActividades+1, act);
    }
    
    /**
     * Añade una actividad en la posición indicada siguiend el orden 
     * enumerado de actividades en el Learning Path
     * @param act: actividad a añadir
     * @param pos: posición en la que se debe poner la actividad
     */
    public void addActividadPorPos (Actividad act, int pos) {
    	int numActividades = this.actividades.size();
    	if (pos > numActividades)
    	{
    		this.addActividadDeUltimas(act);
    	}
    	else if (pos > 0  && pos <= numActividades)
    	{
    		int i = pos;
    		Actividad tempAct1 = this.actividades.get(i);
    		Actividad tempAct2;
    		this.actividades.replace(i, act);
    		i++;
    		int tamanio = this.actividades.size();
    		while (i<=tamanio + 1)
    		{
    			if (i <= tamanio)
    			{
    				tempAct2 = this.actividades.get(i);
        			this.actividades.replace(i, tempAct1);
        			tempAct1 = tempAct2;
    			} else
    			{
    				this.addActividadDeUltimas(tempAct1);
    			}
    			i++;
    		} 
    	}
    }

	/**
     * Añade un Estudiante inscrito y su progreso al Learning Path
     * @param progreso: progreso del estudiante
     */
    public void addProgresoEstudiante(Progreso progreso)
    {
    	String estudiante = progreso.getEstudiante();
    	this.estudiantes.add(estudiante);
    	this.progresosEstudiantiles.put(estudiante, progreso);
    }

}

