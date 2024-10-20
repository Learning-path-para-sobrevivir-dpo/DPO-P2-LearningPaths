package modelo;

import java.util.List;
import java.util.Map;

public class LearningPath {
	
	public String titulo;
	public String descripcion;
	public int nivelDificultad;
	public int duracion;
	public int rating;
	public String fechaCreacion;
	public String fechaModificacion;
	public int version;
	public Map<String, Progreso> progresosEstudiantiles;
	public Profesor autor;
	public List<Estudiante> estudiantes;
	public Map<String, Actividad> actividades;
	
	public LearningPath(String titulo, String descripcion, int nivelDificultad, int duracion, int rating,
			String fechaCreacion, String fechaModificacion, int version, Map<String, Progreso> progresosEstudiantiles,
			Profesor autor, List<Estudiante> estudiantes, Map<String, Actividad> actividades) {
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.nivelDificultad = nivelDificultad;
		this.duracion = duracion;
		this.rating = rating;
		this.fechaCreacion = fechaCreacion;
		this.fechaModificacion = fechaModificacion;
		this.version = version;
		this.progresosEstudiantiles = progresosEstudiantiles;
		this.autor = autor;
		this.estudiantes = estudiantes;
		this.actividades = actividades;
	}
	

}
