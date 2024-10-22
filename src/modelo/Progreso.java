package modelo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import excepciones.YaExisteActividadEnProgresoException;

public class Progreso {
	
	private LearningPath learningPath;
	private Estudiante estudiante;
	private Map<Integer, Actividad> actividadesPath;
	private List<Actividad> actObligatoriasPendientes;
	private List<Actividad> actObligatoriasCompletadas;
	private List<Actividad> actPendientes;
	private List<Actividad> actCompletadas;
	private Actividad actividadEnProgreso;
	private Map<String, Actividad> idActividades;
	private int progresoObligatorio;
	private int progresoTotal;
	private List<String> idsActividadesCopiadas;
	
	public Progreso(LearningPath learningPath, Estudiante estudiante) {
		this.learningPath = learningPath;
		this.estudiante = estudiante;
		this.actividadesPath = new HashMap<Integer, Actividad>();
		this.actCompletadas = new ArrayList<Actividad>();
		this.actPendientes = new ArrayList<Actividad>();
		this.actObligatoriasCompletadas = new ArrayList<Actividad>();
		this.actividadEnProgreso = null;
		this.idActividades = new HashMap<String, Actividad>();
		this.progresoObligatorio = 0;
		this.progresoTotal = 0;
		this.obtenerActividadesPath();
		this.idsActividadesCopiadas = new ArrayList<String>();
	}

	/**
	 * Constructor de progreso para manejo de datos
	 * @param learningPath
	 * @param estudiante
	 * @param idsActividadesCopiadas
	 */
	public Progreso(LearningPath learningPath, Estudiante estudiante, List<String> idsActividadesCopiadas) {
		super();
		this.learningPath = learningPath;
		this.estudiante = estudiante;
		this.progresoObligatorio = 0;
		this.progresoTotal = 0;
		this.idsActividadesCopiadas = idsActividadesCopiadas;
		this.actividadesPath = new HashMap<Integer, Actividad>();
		this.actCompletadas = new ArrayList<Actividad>();
		this.actPendientes = new ArrayList<Actividad>();
		this.actObligatoriasCompletadas = new ArrayList<Actividad>();
		this.actividadEnProgreso = null;
		this.idActividades = new HashMap<String, Actividad>();
	}


	public Map<Integer, Actividad> getActividadesPath() {
		return actividadesPath;
	}

	public void setActividadesPath(Map<Integer, Actividad> actividadesPath) {
		this.actividadesPath = actividadesPath;
	}

	public LearningPath getLearningPath() {
		return learningPath;
	}

	public void setLearningPath(LearningPath learningPath) {
		this.learningPath = learningPath;
	}

	public Estudiante getEstudiante() {
		return estudiante;
	}

	public void setEstudiante(Estudiante estudiante) {
		this.estudiante = estudiante;
	}
	
	public List<Actividad> getActObligatoriasPendientes() {
		return actObligatoriasPendientes;
	}

	public void setActObligatoriasPendientes(List<Actividad> actObligatoriasPendientes) {
		this.actObligatoriasPendientes = actObligatoriasPendientes;
	}

	public List<Actividad> getActObligatoriasCompletadas() {
		return actObligatoriasCompletadas;
	}

	public void setActObligatoriasCompletadas(List<Actividad> actObligatoriasCompletadas) {
		this.actObligatoriasCompletadas = actObligatoriasCompletadas;
	}
	
	public List<Actividad> getActPendientes() {
		return actPendientes;
	}

	public void setActPendientes(List<Actividad> actPendientes) {
		this.actPendientes = actPendientes;
	}
	
	public List<Actividad> getActCompletadas() {
		return actCompletadas;
	}

	public void setActCompletadas(List<Actividad> actCompletadas) {
		this.actCompletadas = actCompletadas;
	}

	public Actividad getActividadEnProgreso() {
		return actividadEnProgreso;
	}
	
	public Map<String, Actividad> getIdActividades() {
		return idActividades;
	}

	public void setIdActividades(Map<String, Actividad> idActividades) {
		this.idActividades = idActividades;
	}

	public int getProgresoObligatorio() {
		return progresoObligatorio;
	}

	public int getProgresoTotal() {
		return progresoTotal;
	}

	public List<String> getIdsActividadesCopiadas() {
		return idsActividadesCopiadas;
	}


	public void setIdsActividadesCopiadas(List<String> idsActividadesCopiadas) {
		this.idsActividadesCopiadas = idsActividadesCopiadas;
	}


	/**
	 * Clona todas las actividades de un Learning Path para poder ser completadas por el estudiante
	 */
	public void obtenerActividadesPath()
	{
		int tamanio = this.learningPath.getActividades().size();
		Actividad actNueva;
		Actividad actVieja;
		HashMap<Integer, Actividad> nuevasActividades = new HashMap<Integer, Actividad>();
		for (int i = 1; i <= tamanio; i++)
		{
			try {
				actNueva = (Actividad) this.learningPath.getActividades().get(i).clone();
				actNueva.actividadClonadaProgreso();
				Set<String> ids = this.idActividades.keySet();
				if (ids.contains(actNueva.getId()))
				{
					actVieja = this.idActividades.get(actNueva.getId());
					nuevasActividades.put(i, actVieja);
				}
				else
				{
					nuevasActividades.put(i, actNueva);
				}
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}
		}
		this.actividadesPath = nuevasActividades;
		this.obtenerIDsActividades();
		this.obtenerActPendientes();
		this.obtenerActObligatoriasPendientes();
		this.obtenerActCompletadas();
		this.obtenerActObligatoriasCompletadas();
		this.calcularProgreso();
	}
	
	/**
	 * Añade las actividades clonadas del progreso desde manejo de datos.
	 * Actualiza todos los valores del progreso
	 * @param actividadesClonadas: lista de actividades clonadas a añadir al progreso
	 */
	public void añadirActividadesClonadas (List<Actividad> actividadesClonadas) {
		Actividad actividad;
		int i = 1;
		HashMap<Integer, Actividad> nuevasActividades = new HashMap<Integer, Actividad>();
		for (Iterator<Actividad> it = actividadesClonadas.iterator(); it.hasNext();) {
			actividad = it.next();
			nuevasActividades.put(i, actividad);
			i++;
		}
		this.obtenerIDsActividades();
		this.obtenerActPendientes();
		this.obtenerActObligatoriasPendientes();
		this.obtenerActCompletadas();
		this.obtenerActObligatoriasCompletadas();
		this.calcularProgreso();
	}
	
	/**
	 * Actualiza el mapa de actividades por ID y la lista de ids de las copias de Actividades
	 */
	public void obtenerIDsActividades() {
		int tamanio = this.actividadesPath.size();
		Actividad act;
		Map <String, Actividad> idAct = new HashMap<String, Actividad>();
		List<String> ids = new ArrayList<String>();
		for (int i = 1; i <= tamanio; i++)
		{
			act = this.actividadesPath.get(i);
			idAct.put(act.getId(), act);
			ids.add(act.getIdEstudiante());
		}
		this.idActividades = idAct;
		//Actualizar las IDs de Estudiante las actividades clonadas
		this.idsActividadesCopiadas = ids;
	}
	
	/**
	 * Saca las actividades pendientes de las instancias clonadas de las
	 * actividades del Learning Path
	 */
	public void obtenerActPendientes() {
		List<Actividad> actividades = new ArrayList<Actividad>();
		int tamanio = this.learningPath.getActividades().size();
		Actividad act;
		for (int i = 1; i <= tamanio; i++)
		{
			act = this.actividadesPath.get(i);
			if (!act.isCompletada())
			{
				actividades.add(act);
			}
		}
		this.actPendientes = actividades;
	}
	
	/**
	 * Saca las actividades obligatorias pendientes de las instancias clonadas de las
	 * actividades del Learning Path
	 */
	public void obtenerActObligatoriasPendientes() {
		List<Actividad> actividades = new ArrayList<Actividad>();
		for (Actividad act: this.actPendientes)
		{
			if (act.isObligatorio())
			{
				actividades.add(act);
			}
		}
		this.actObligatoriasPendientes = actividades;
	}
	
	/**
	 * Saca las actividades completadas de las instancias clonadas de las
	 * actividades del Learning Path
	 */
	public void obtenerActCompletadas() {
		List<Actividad> actividades = new ArrayList<Actividad>();
		int tamanio = this.learningPath.getActividades().size();
		Actividad act;
		for (int i = 1; i <= tamanio; i++)
		{
			act = this.actividadesPath.get(i);
			if (act.isCompletada())
			{
				actividades.add(act);
			}
		}
		this.actCompletadas = actividades;
	}
	
	/**
	 * Saca las actividades obligatorias completadas de las instancias clonadas de las
	 * actividades del Learning Path
	 */
	public void obtenerActObligatoriasCompletadas() {
		List<Actividad> actividades = new ArrayList<Actividad>();
		for (Actividad act: this.actCompletadas)
		{
			if (act.isObligatorio())
			{
				actividades.add(act);
			}
		}
		this.actObligatoriasCompletadas = actividades;
	}
	
	/**
	 * Elimina una actividad de la lista de actividades pendientes
	 * @param act: actividad a eliminar de la lista
	 */
	private void eliminarActividadPendiente(Actividad act) {
		if (this.actPendientes.contains(act))
		{
			int pos = this.actPendientes.indexOf(act);
			this.actPendientes.remove(pos);
		}
		if (this.actObligatoriasPendientes.contains(act))
		{
			int pos = this.actObligatoriasPendientes.indexOf(act);
			this.actObligatoriasPendientes.remove(pos);
		}
	}
	 /**
	  * Añade una actividad a la lista de actividades pendientes.
	  * Esto especialmente se usa cuando un profesor marca como no exitosa
	  * una actividad y pone la actividad como pendiente para que el 
	  * estudiante la repita
	  * @param act: actividad a añadir a pendientes
	  */
	private void addActividadPendiente(Actividad act)
	{
		if (!this.actPendientes.contains(act))
		{
			this.actPendientes.add(act);
		}
		if (!this.actObligatoriasPendientes.contains(act))
		{
			this.actObligatoriasPendientes.add(act);
		}
	}
	
	/**
	 * Elimina una acticvidad de la lista de actividades completadas
	 * @param act: actividad a eliminar
	 */
	private void eliminarActividadCompletada(Actividad act)
	{
		if (this.actCompletadas.contains(act))
		{
			int pos = this.actCompletadas.indexOf(act);
			this.actCompletadas.remove(pos);
		}
		if (this.actObligatoriasCompletadas.contains(act))
		{
			int pos = this.actObligatoriasCompletadas.indexOf(act);
			this.actObligatoriasCompletadas.remove(pos);
		}
	}
	
	/**
	 * Añade una actividad a la lista de actividades completadas
	 * @param act: actividad a añadir
	 */
	private void addActividadCompletada(Actividad act)
	{
		if (!this.actCompletadas.contains(act))
		{
			this.actCompletadas.add(act);
		}
		if (!this.actObligatoriasCompletadas.contains(act))
		{
			this.actObligatoriasCompletadas.add(act);
		}
	}
	
	/**
	 * Empieza una actividad 
	 * @param act: actividad a empezar
	 * @throws YaExisteActividadEnProgresoException cuando se intenta iniciar
	 * una actividad sin completar la que ya estaba en progreso
	 */
	public void empezarActividad(Actividad act) throws YaExisteActividadEnProgresoException, Exception {
		if (this.actividadEnProgreso != null)
		{
			throw new YaExisteActividadEnProgresoException(this.actividadEnProgreso);
		}
		if (!this.idsActividadesCopiadas.contains(act.getIdEstudiante()) && !this.idActividades.containsKey(act.getId()))
		{
			throw new Exception("Se intento empezar una actividad que no hace parte del Learning Path");
		}
		this.actividadEnProgreso = act;
	}
	
	/**
	 * Marca como completada una actividad
	 * @param act: actividad a marcar como completada
	 * @return true si se completa exitosamente, false de lo contrario
	 * @throws Exception: si se intenta completar una actividad diferente
	 * a la actividad que esta en progreso
	 */
	public boolean completarActividad(Actividad act) throws Exception {
		boolean completada = false;
		if (!act.equals(this.actividadEnProgreso))
		{
			throw new Exception("Se intento completar una actividad que no estaba en progreso");
		}
		completada = act.completarActividad();
		eliminarActividadPendiente(act);
		addActividadCompletada(act);
		this.calcularProgreso();
		this.actividadEnProgreso = null;
		return completada;
	}
	
	/**
	 * Marca como no completada una actividad. Esto es exclusivo para los profesores
	 * para manejar las actividades de los estudiantes
	 * @param act: actividad a marcar como no completada
	 */
	public void descompletarActividad(Actividad act){
		eliminarActividadCompletada(act);
		addActividadPendiente(act);
		this.calcularProgreso();
	}
	
	/**
	 * Calcula el progreso del estudiante en el Learning Path
	 */
	private void calcularProgreso() {
		int actTotales = this.actPendientes.size() + this.actCompletadas.size();
		int actCompletadas = this.actCompletadas.size();
		this.progresoTotal = (actCompletadas / actTotales) * 100;
		
		actTotales = this.actObligatoriasCompletadas.size() + this.actObligatoriasPendientes.size();
		actCompletadas = this.actObligatoriasCompletadas.size();
		this.progresoObligatorio = (actCompletadas / actTotales) * 100;
	}
	
	/**
	 * Obtiene una actividad por el número en el que esta guardada en el Learning Path
	 * @param numActividad: número de la actividad en el Learning Path
	 * @return La actividad buscada. Es null si no se encuentra
	 */
	public Actividad obtenerActividadPorNum(int numActividad) {
		if (numActividad > this.actividadesPath.size() || numActividad < 1)
		{
			return null;
		}
		Actividad actividad = this.actividadesPath.get(numActividad);
		return actividad;
	}
	
	/**
	 * Retorna una lista de las instancias clonadas del Learning Path y modificadas por el estudiante
	 * @return lista de actividades del progreso (copias de las originales del Learning Path)
	 */
	public List<Actividad> returnActividadesClonadas()
	{
		return (List<Actividad>) this.actividadesPath.values();
	}

}
