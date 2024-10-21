package persistencia;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;


import modelo.Actividad;
import modelo.LearningPath;
import modelo.Usuario;


public class ManejoDatos {
	private HashMap<List<String>, Usuario> usuarios;
    private HashMap<String, LearningPath> learningPaths;
    private HashMap<String, Actividad> actividades;
	
	public ManejoDatos() {
		super();
		this.usuarios = PersistenciaUsuarios.cargarUsuarios();;
		this.learningPaths = PersistenciaLearningPaths.cargarLearningPaths();
		this.actividades = PersistenciaActividades.cargarActividades();
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
		}
	}
	
	/**
	 * Encuentra todas las actividades con un nombre
	 * @param nombreActividad: nombre de la actividad
	 * @return Una lista de actividades que tengan el nombre buscado
	 */
	public List<Actividad> getActividadPorNombre(String nombreActividad) {
		List<Actividad> actividades = new ArrayList<Actividad>();
		Actividad actividad = null;
		Set<String> ids = this.actividades.keySet();
		Iterator<String> iterador = ids.iterator();
		while (iterador.hasNext())
		{
			String id = iterador.next();
			actividad = this.actividades.get(id);
			if (actividad != null && actividad.getTitulo().equals(nombreActividad))
			{
				actividades.add(actividad);
			}
		}
		return actividades;
	}
	
	/**
	 * Encuentra una actividad dado su ID
	 * @param id: id de la actividad
	 * @return La actividad que tiene el id dado. Si no se encuentra, retorna null
	 */
	public Actividad getActividadPorID(String id)
	{
		Actividad actividad = null;
		if (this.actividades.containsKey(id))
		{
			actividad = this.actividades.get(id);
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

}