package modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import modelo.actividades.Actividad;

public class LearningPath {
	
	public String titulo;
	public String descripcion;
	public String objetivo;
	public int nivelDificultad;
	public int duracion;
	public int rating;
	public List<Integer> listRatings;
	public String fechaCreacion;
	public String fechaModificacion;
	public int version;
	private Map<String, Progreso> progresosEstudiantiles;
	private String autor;  //Cambie Profesor por String
	private List<String> estudiantes; //Galarza: Cambie lista de estudiante por lista de strings
	//Mapa donde las actividades estan identificadas por un número que indica
	//el orden sugerido para completar las actividades
	private Map<Integer,Actividad> actividades;
	private List<Actividad> posActs;
	
	public LearningPath(String titulo, String descripcion, String objetivo, int nivelDificultad, int rating,
			String fechaCreacion, String fechaModificacion, int version, String autor) {
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.objetivo = objetivo;
		this.nivelDificultad = nivelDificultad;
		this.duracion = 0;
		this.rating = rating;
        LocalDate fechaActual = LocalDate.now();
        String fecha = fechaActual.toString();
		this.fechaCreacion = fecha;
		this.fechaModificacion = fechaModificacion;
		this.version = version;
		this.progresosEstudiantiles = new HashMap<String, Progreso>();
		this.autor = autor;
		this.estudiantes = new ArrayList<String>();
		this.actividades = new HashMap<Integer,Actividad>();
		this.posActs = new ArrayList<Actividad>();
		this.listRatings = new ArrayList<Integer>();
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
		this.getPosActividades();
		this.calcularDuracion();
	}
	

	public List<Actividad> getPosActs() {
		return posActs;
	}


	public void setPosActs(List<Actividad> posActs) {
		this.posActs = posActs;
	}
	
	
	public int getDuracion() {
		return duracion;
	}
	
	private void getPosActividades()
	{
		Map<Integer,Actividad> acts = this.actividades;
		List<Actividad> posActs = new ArrayList<Actividad>();
		for (int i = 1; i <= this.actividades.size(); i++)
		{
			posActs.add(acts.get(i));
		}
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
    	this.posActs.add(act);
    	calcularDuracion();
    }
    
    /**
     * Añade una actividad en la posición indicada siguiend el orden 
     * enumerado de actividades en el Learning Path
     * @param act: actividad a añadir
     * @param pos: posición en la que se debe poner la actividad
     */
    public void addActividadPorPos(Actividad act, int pos) {
        int numActividades = this.actividades.size();

        if (pos > numActividades) {
            this.addActividadDeUltimas(act);
        } else if (pos > 0 && pos <= numActividades) {
            if (this.posActs.contains(act)) {
                this.posActs.remove(act);
                this.posActs.add(pos - 1, act);
            } else {
                this.posActs.add(pos - 1, act);
            }

            Map<Integer, Actividad> acts = new HashMap<>();
            for (int i = 0; i < this.posActs.size(); i++) {
                acts.put(i + 1, this.posActs.get(i)); 
            }
            this.actividades = acts;
        }

        calcularDuracion();
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


    /**
     * Elimina una actividad de la posición indicada, manteniendo el orden
     * enumerado de actividades en el Learning Path.
     * @param pos: posición de la actividad a eliminar (1-indexado)
     * @throws IllegalArgumentException si la posición es inválida
     */
    public void eliminarActividadPorPos(int pos) {
        int numActividades = this.actividades.size();

        if (pos <= 0 || pos > numActividades) {
            throw new IllegalArgumentException("Posición inválida: " + pos);
        }

        // Eliminar la actividad 
        this.posActs.remove(pos - 1);

        // Reconstruir el HashMap de actividades con las nuevas posiciones
        Map<Integer, Actividad> acts = new HashMap<>();
        for (int i = 0; i < this.posActs.size(); i++) {
            acts.put(i + 1, this.posActs.get(i));
        }
        this.actividades = acts;

        // Recalcular la duración del Learning Path
        calcularDuracion();
    }


    public void addRating(int rating) {
    	
    	listRatings.add(rating);
    	calcularRating();
    }
    
    public void calcularRating() {
    	int promedio = 0;
		int suma = 0;

    	for (int rating: listRatings) {
    		
    		suma = suma+rating;	
    		
    	}
    	
    	promedio = suma/listRatings.size();
    	this.setRating(promedio);
    }





}

