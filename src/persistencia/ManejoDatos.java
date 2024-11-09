package persistencia;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;


import modelo.*;
import modelo.actividades.Actividad;
import modelo.actividades.Pregunta;

public class ManejoDatos {
	private HashMap<List<String>, Usuario> usuarios; 
    private HashMap<String, LearningPath> learningPaths;
    private HashMap<String, Actividad> actividades;
    private HashMap<String, Pregunta> preguntas;
    private HashMap<List<String>, Progreso> progresos;
    private HashMap<String, Review> reviews;
    
    public ManejoDatos() {
    	this.usuarios = new HashMap<List<String>, Usuario>();
        this.learningPaths = new HashMap<String, LearningPath>();
        this.actividades = new HashMap<String, Actividad>();
        this.preguntas = new HashMap<String, Pregunta>();
        this.progresos = new HashMap<List<String>, Progreso>();
        this.reviews = new HashMap<String, Review>();
    }
    
    public void cargarDatos() {
    	this.reviews = PersistenciaReviews.cargarReviews();
    	this.preguntas = PersistenciaPreguntas.cargarPreguntas();
    	this.actividades = PersistenciaActividades.cargarActividades(reviews, preguntas);
    	this.progresos = PersistenciaProgresos.cargarProgresos(actividades);
    	this.learningPaths = PersistenciaLearningPaths.cargarLearningPaths(progresos,actividades);
    	this.usuarios = PersistenciaUsuarios.cargarUsuarios(progresos, learningPaths, reviews, actividades);
    }
    
	public HashMap<List<String>, Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(HashMap<List<String>, Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public HashMap<String, Actividad> getActividades() {
		return actividades;
	}

	public void setActividades(HashMap<String, Actividad> actividades) {
		this.actividades = actividades;
	}

	public HashMap<String, LearningPath> getLearningPaths() {
		return learningPaths;
	}

	public void setLearningPaths(HashMap<String, LearningPath> learningPaths) {
		this.learningPaths = learningPaths;
	}
	
	//Manejo de Usuarios////////////////////////////////////////
	
	private List<String> crearLlaveUsuario(String login, String password)
	{
		List<String> infoUsuario = new ArrayList<String>();
		infoUsuario.add(login);
		infoUsuario.add(password);
		return infoUsuario;
	}
	
	/**
	 * Añade un usuario a los usuarios de la plataforma
	 * @param usuario: perfil del usuario a añadir
	 */
	public void addUsuario(Usuario usuario)
	{
		if (usuario != null)
		{
			List<String> infoUsuario = crearLlaveUsuario(usuario.getLogin(), usuario.getContraseña());		
			this.usuarios.put(infoUsuario, usuario);
			PersistenciaUsuarios.guardarUsuarios(usuarios);
		}
	}
	
	/**
	 * Retorna un usuario dado su login y password. 
	 * @param login: login del usuario
	 * @param password: contraseña del usuario
	 * @return el usuario que corresponde al login y al password. 
	 * 
	 */
	public Usuario getUsuario(String login, String password) {
		Usuario usuario = null;
		List<String> infoUsuario = crearLlaveUsuario(login, password);
		if (this.usuarios.containsKey(infoUsuario))
		{
			usuario = this.usuarios.get(infoUsuario);
		}
		return usuario;
	}
	
	/**
	 * Actualiza la información de un usuario
	 * @param usuario: perfil del usuario actualizado
	 */
	public void actualizarUsuario(Usuario usuario)
	{
		if (usuario != null)
		{
			List<String> infoUsuario = crearLlaveUsuario(usuario.getLogin(), usuario.getContraseña());
			this.usuarios.replace(infoUsuario, usuario);
		}
	}
	
	/**
	 * Verifica si un login dado ya ha sido utilizado por uno de los 
	 * usuarios existentes para evitar logins duplicados
	 * @param login: login buscado
	 * @return true si el login ya existe y false de lo contrario
	 */
	public boolean existeLogin(String login)
	{
		boolean existe = false;
		if (this.usuarios.isEmpty())
			return existe;
		
		Set<List<String>> infoUsuarios = this.usuarios.keySet();
		Iterator<List<String>> iterador = infoUsuarios.iterator();
		List<String> infoUsuario;
		String loginUsuario; 
		while(!existe && iterador.hasNext())
		{
			infoUsuario = iterador.next();
			loginUsuario = infoUsuario.get(0);
			if (login.equals(loginUsuario))
			{
				existe = true;
			}
		}
		
		return existe;
	}
	
	//Manejo de Actividades///////////////////////////////////
	
	public void addActividad(Actividad actividad)
	{
		if (actividad != null)
		{
			actividades.put(actividad.getId(), actividad);
	        PersistenciaActividades.guardarActividades(actividades);
        }}
	
	/**
	 * Encuentra una actividad por su nombre
	 * @param nombreActividad: nombre de la actividad
	 * @return La actividad buscada. Si no se encuentra retorna null.
	 */
	public Actividad getActividad(String nombreActividad) {
		Actividad actividad = null;
		if (this.actividades.containsKey(nombreActividad))
		{
			actividad = this.actividades.get(nombreActividad);
		}
		return actividad;
	}
	
	//Manejo de Learning Paths /////////////////////////////////////
	
	/**
	 * Añade un Learning path a los datos
	 * @param path: learning path a añadir
	 */
	public void addLearningPath(LearningPath path)
	{
		if (path != null)
		{
			this.learningPaths.put(path.getTitulo(), path);
			PersistenciaLearningPaths.guardarLearningPaths(learningPaths);
		}
	}
	
	/**
	 * Encuentra un LearningPath dado su nombre
	 * @param nombreLearningPath: nombre del Learning Path
	 * @return El Learning Path buscado. Si no se encuentra, retorna null
	 */
	public LearningPath getLearningPath(String nombreLearningPath) {
		LearningPath path = null;
		if (this.learningPaths.containsKey(nombreLearningPath))
		{
			path = this.learningPaths.get(nombreLearningPath);
		}
		return path;
	}
	
	/**
	 * Actualiza la información de un Learning Path
	 * @param path: learning path actualizado
	 */
	public void actualizarLearningPath(LearningPath path)
	{
		if (path != null)
		{
			this.learningPaths.replace(path.getTitulo(), path);
		}
	}
//Manejo de progreso, reviews, preguntas
	
	public void addProgreso(Progreso progreso)
	{
		if (progreso != null)
		{
			this.progresos.put(List.of(progreso.getLearningPath(),progreso.getEstudiante()), progreso);
			PersistenciaProgresos.guardarProgreso(progresos);
		}
	}
	
	public void addReview(Review review)
	{
		if (review != null)
		{
			this.reviews.put(review.getContenido(), review);
			PersistenciaReviews.guardarReviews(reviews);
		}
	}
	
	public void addPregunta(Pregunta pregunta)
	{
		if (pregunta != null)
		{
			this.preguntas.put(pregunta.getEnunciado(), pregunta);
			PersistenciaPreguntas.guardarPreguntas(preguntas);;
		}
	}
}