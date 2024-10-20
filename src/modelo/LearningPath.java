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
	public Map<String, Progreso> progresosEstudiantiles;
	public Profesor autor;
	public List<Estudiante> estudiantes;
	public Map<String, IActividad> actividades;
    private boolean durActualizada;

	
	public LearningPath(String titulo, String descripcion, String objetivo, int nivelDificultad, int duracion, int rating,
			String fechaCreacion, String fechaModificacion, int version, Profesor autor) {
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.objetivo = objetivo;
		this.nivelDificultad = nivelDificultad;
		this.duracion = duracion;
		this.rating = rating;
		this.fechaCreacion = fechaCreacion;
		this.fechaModificacion = fechaModificacion;
		this.version = version;
		this.progresosEstudiantiles = new HashMap<String, Progreso>();
		this.autor = autor;
		this.estudiantes = new ArrayList<Estudiante>();
		this.actividades = new HashMap<String,IActividad>();
		this.durActualizada = false;
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

	public Profesor getAutor() {
		return autor;
	}

	public List<Estudiante> getEstudiantes() {
		return estudiantes;
	}

	public void setEstudiantes(List<Estudiante> estudiantes) {
		this.estudiantes = estudiantes;
	}

	public Map<String, IActividad> getActividades() {
		return actividades;
	}

	public void setActividades(Map<String, IActividad> actividades) {
		this.actividades = actividades;
	}
	
	public int getDuracion() {
		if (!durActualizada) {
			calcularDuracion();
			}
		return duracion;
	}
	
    public void calcularDuracion() {
        int nuevaDuracion = 0;

        for (IActividad act : actividades.values()) {
            // Sumar la duración de cada actividad
            nuevaDuracion += act.getDuracion();
        }

        // Actualizar duracion
        this.duracion = nuevaDuracion;
        this.durActualizada = true;
    }
    
    public void addActividad (IActividad act) {
    	
    	String nom = act.getNombre;
    	actividades.put(nom, act);
    	this.durActualizada = false;
    	
    }

}

