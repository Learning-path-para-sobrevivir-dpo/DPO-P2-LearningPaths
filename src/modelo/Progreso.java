package modelo;

import java.util.ArrayList;
import java.util.HashMap;
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
	}

	public Progreso(LearningPath learningPath, Estudiante estudiante, Map<Integer, Actividad> actividadesPath,
			List<Actividad> actObligatoriasPendientes, List<Actividad> actObligatoriasCompletadas,
			List<Actividad> actPendientes, List<Actividad> actCompletadas, Actividad actividadEnProgreso) {
		super();
		this.learningPath = learningPath;
		this.estudiante = estudiante;
		this.actividadesPath = actividadesPath;
		this.actObligatoriasPendientes = actObligatoriasPendientes;
		this.actObligatoriasCompletadas = actObligatoriasCompletadas;
		this.actPendientes = actPendientes;
		this.actCompletadas = actCompletadas;
		this.actividadEnProgreso = actividadEnProgreso;
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

	/**
	 * Clona todas las actividades de un Learning Path para poder ser completadas por el estudiante
	 */
	public void obtenerActividadesPath()
	{
		int tamanio = this.learningPath.getActividades().size();
		Actividad actNueva;
		Actividad actVieja;
		for (int i = 1; i <= tamanio; i++)
		{
			try {
				actNueva = (Actividad) this.learningPath.getActividades().get(i).clone();
				Set<String> ids = this.idActividades.keySet();
				if (ids.contains(actNueva.getId()))
				{
					actVieja = this.idActividades.get(actNueva.getId());
					actNueva.setEstado(actVieja.getEstado());
					actNueva.setCompletada(actVieja.isCompletada());
				}
				this.actividadesPath.put(i, actNueva);
				this.obtenerIDsActividades();
				this.obtenerActPendientes();
				this.obtenerActObligatoriasPendientes();
				this.obtenerActCompletadas();
				this.obtenerActObligatoriasCompletadas();
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void obtenerIDsActividades() {
		int tamanio = this.actividadesPath.size();
		Actividad act;
		for (int i = 1; i <= tamanio; i++)
		{
			act = this.actividadesPath.get(i);
			this.idActividades.put(act.getId(), act);
		}
	}
	
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
	
	public void eliminarActividadPendiente(Actividad act) {
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
	
	public void addActividadPendiente(Actividad act)
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
	
	public void eliminarActividadCompletada(Actividad act)
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
	
	public void addActividadCompletada(Actividad act)
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
	
	public void empezarActividad(Actividad act) throws YaExisteActividadEnProgresoException {
		if (this.actividadEnProgreso != null)
		{
			throw new YaExisteActividadEnProgresoException(this.actividadEnProgreso);
		}
		this.actividadEnProgreso = act;
	}
	
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
		return completada;
	}
	
	public void descompletarActividad(Actividad act){
		eliminarActividadCompletada(act);
		addActividadPendiente(act);
		this.calcularProgreso();
	}
	
	private void calcularProgreso() {
		int actTotales = this.actPendientes.size() + this.actCompletadas.size();
		int actCompletadas = this.actCompletadas.size();
		this.progresoTotal = (actCompletadas / actTotales) * 100;
		
		actTotales = this.actObligatoriasCompletadas.size() + this.actObligatoriasPendientes.size();
		actCompletadas = this.actObligatoriasCompletadas.size();
		this.progresoObligatorio = (actCompletadas / actTotales) * 100;
	}
	
	public Actividad obtenerActividadPorNum(int numActividad) {
		if (numActividad > this.actividadesPath.size() || numActividad < 1)
		{
			return null;
		}
		Actividad actividad = this.actividadesPath.get(numActividad);
		return actividad;
	}
	
}
