package modelo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import excepciones.CompletarActividadQueNoEstaEnProgresoException;
import excepciones.LearningPathIncorrectoProgresoException;
import excepciones.YaExisteActividadEnProgresoException;
import modelo.actividades.Actividad;

public class Progreso {
	
	private String learningPath; //cambie esto por string, el titulo del learningPath
	private String estudiante; //Galarza: cambie estudiante a string de su usuario
	private List<String> ordenActividades;
	private Map<String, Actividad> actividadesPath;
	private List<Actividad> actObligatoriasPendientes;
	private List<Actividad> actObligatoriasCompletadas;
	private List<Actividad> actPendientes;
	private List<Actividad> actCompletadas;
	private Actividad actividadEnProgreso;
	private Map<String, Actividad> idActividadesOriginales;
	private int progresoObligatorio;
	private int progresoTotal;
	
	public Progreso(String learningPath, String estudiante) {
		this.learningPath = learningPath;
		this.estudiante = estudiante;
		this.actividadesPath = new HashMap<String, Actividad>();
		this.actCompletadas = new ArrayList<Actividad>();
		this.actPendientes = new ArrayList<Actividad>();
		this.actObligatoriasCompletadas = new ArrayList<Actividad>();
		this.actividadEnProgreso = null;
		this.idActividadesOriginales = new HashMap<String, Actividad>();
		this.progresoObligatorio = 0;
		this.progresoTotal = 0;
		//this.obtenerActividadesPath(); //Galarza: lo quite porque cambie obternerActividades, pero de por si o entinedo que hace
	}

	public Progreso(String learningPath,String estudiante, Map<String, Actividad> actividadesPath,
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

	public List<String> getOrdenActividades() {
		return ordenActividades;
	}

	public void setOrdenActividades(List<String> ordenActividades) {
		this.ordenActividades = ordenActividades;
	}

	public Map<String, Actividad> getIdActividadesOriginales() {
		return idActividadesOriginales;
	}

	public void setIdActividadesOriginales(Map<String, Actividad> idActividadesOriginales) {
		this.idActividadesOriginales = idActividadesOriginales;
	}

	public Map<String, Actividad> getActividadesPath() {
		return actividadesPath;
	}

	public void setActividadesPath(Map<String, Actividad> actividadesPath) {
		this.actividadesPath = actividadesPath;
	}

	//modifique que dieran el nombre del learning path y no el learning path
	public String getLearningPath() {
		return learningPath;
	}

	public void setLearningPath(String learningPath) {
		this.learningPath = learningPath;
	}

	//Galarza: cambie el get y set de estudiante para que de el usuario
	
	 public String getEstudiante() {
		return estudiante;
	}

	public void setEstudiante(String estudiante) {
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
	
	public void setActividadEnProgreso(Actividad actividadEnProgreso2) {
		this.actividadEnProgreso= actividadEnProgreso2;
	}
	
	public Map<String, Actividad> getIdActividades() {
		return idActividadesOriginales;
	}

	public void setIdActividades(Map<String, Actividad> idActividades) {
		this.idActividadesOriginales = idActividades;
	}

	public int getProgresoObligatorio() {
		return progresoObligatorio;
	}
	
	public void setProgresoObligatorio(int progresoObligatorio) {
		this.progresoObligatorio = progresoObligatorio;
	}

	public int getProgresoTotal() {
		return progresoTotal;
	}
	
	public void setProgresoTotal(int progresoTotal) {
		this.progresoTotal = progresoTotal;
	}
	

	public Actividad obtenerActividadPorNum(int numActividad) {
		if (numActividad > this.ordenActividades.size() || numActividad < 1)
		{
			return null;
		}
		String idActividad = this.ordenActividades.get(numActividad-1);
		Actividad actividad = this.idActividadesOriginales.get(idActividad);
		return actividad;
	}
	
	public void empezarActividad(Actividad act) throws YaExisteActividadEnProgresoException {
		if (this.actividadEnProgreso != null)
		{
			throw new YaExisteActividadEnProgresoException(this.actividadEnProgreso);
		}
		this.actividadEnProgreso = act;
	}
	
	public void desempezarActividad() {
		this.actividadEnProgreso = null;
	}
	
	public boolean completarActividad(Actividad act) throws CompletarActividadQueNoEstaEnProgresoException {
		boolean completada = false;
		if (!act.equals(this.actividadEnProgreso))
		{
			throw new CompletarActividadQueNoEstaEnProgresoException(act);
		}
		completada = act.completarActividad();
		eliminarActividadPendiente(act);
		addActividadCompletada(act);
		this.calcularProgreso();
		return completada;
	}
	
	public void descompletarActividad(Actividad act){
		if (this.actCompletadas.contains(act))
		{
			eliminarActividadCompletada(act);
			addActividadPendiente(act);
			this.calcularProgreso();
			act.descompletarActividad();
		}
	}
	
	/**
	 * Clona todas las actividades de un Learning Path para poder ser completadas por el estudiante //Galarza: modifique para que reciba el mapa de learning paths.
	 */
	public Map<String, Actividad> obtenerActividadesPath(LearningPath learningPath) throws LearningPathIncorrectoProgresoException
	{
		if (!this.learningPath.equals(learningPath.getTitulo()))
		{
			throw new LearningPathIncorrectoProgresoException(learningPath.getTitulo(), this.learningPath);
		}
		int tamanio = learningPath.getActividades().size();
		Actividad actNueva;
		Actividad actVieja;
		HashMap<String, Actividad> nuevasActividades = new HashMap<String, Actividad>();
		HashMap<String, Actividad> actsOriginalesPath = new HashMap<String, Actividad>();
		List<String> orden = new ArrayList<String>();
		for (int i = 1; i <= tamanio; i++)
		{
			try {
				actNueva = (Actividad) learningPath.getActividades().get(i).clone();
				actNueva.actividadClonadaProgreso();
				Set<String> ids = this.idActividadesOriginales.keySet();
				if (ids.contains(actNueva.getId()))
				{
					String idVieja = this.idActividadesOriginales.get(actNueva.getId()).getIdEstudiante();
					actVieja = this.idActividadesOriginales.get(actNueva.getId());
					nuevasActividades.put(idVieja, actVieja);
					orden.add(actNueva.getId());
					actsOriginalesPath.put(actVieja.getId(), actVieja);
				}
				else
				{
					nuevasActividades.put(actNueva.getIdEstudiante(), actNueva);
					orden.add(actNueva.getId());
					actsOriginalesPath.put(actNueva.getId(), actNueva);
				}
				
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}
		}
		this.actividadesPath = nuevasActividades;
		this.ordenActividades = orden;
		this.idActividadesOriginales = actsOriginalesPath;
		this.obtenerActPendientes();
		this.obtenerActObligatoriasPendientes();
		this.obtenerActCompletadas();
		this.obtenerActObligatoriasCompletadas();
		//this.calcularProgreso();
		return this.getActividadesPath();
	}
	
	/*
	 * Estos Métodos se usan internamente para 
	 */
	
	/**
	 * Obtiene las actividades pendientes cuando se carga
	 * por primera vez el Progreso
	 */
	private void obtenerActPendientes() { //Galarza: agregue atributo de hash map
		
		List<Actividad> actividades = new ArrayList<Actividad>();
		Actividad act;
		for (String id: this.ordenActividades)
		{
			act = this.idActividadesOriginales.get(id);
			if (!act.isCompletada())
			{
				actividades.add(act);
			}
		}
		this.actPendientes = actividades;
	}
	
	/**
	 * Obtiene las actividades obligatorias pendientes cuando se carga
	 * por primera vez el Progreso
	 */
	private void obtenerActObligatoriasPendientes() {
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
	 * Obtiene las actividades completadas cuando se carga
	 * por primera vez el Progreso
	 */
	private void obtenerActCompletadas() {
		List<Actividad> actividades = new ArrayList<Actividad>();
		Actividad act;
		for (String idEstudiante: this.actividadesPath.keySet())
		{
			act = this.actividadesPath.get(idEstudiante);
			if (act.isCompletada())
			{
				actividades.add(act);
			}
		}
		this.actCompletadas = actividades;
	}
	
	/**
	 * Obtiene las actividades obligatorias completadas cuando se carga
	 * por primera vez el Progreso
	 */
	private void obtenerActObligatoriasCompletadas() {
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
	 * Elimina una actividad en progreso de la lista de pendientes cuando se
	 * completa esa actividad
	 * @param act actividad que estaba en progreso 
	 */
	private void eliminarActividadPendiente(Actividad act) {
		if (this.actPendientes.contains(act))
		{
			int pos = this.actPendientes.indexOf(act);
			this.actPendientes.remove(pos);
		}
		if (this.actObligatoriasPendientes != null) {
			if (this.actObligatoriasPendientes.contains(act))
			{
				int pos = this.actObligatoriasPendientes.indexOf(act);
				this.actObligatoriasPendientes.remove(pos);
			}
		}
	}
	
	/**
	 * Añade de vuelta una actividad a la lista de actividades pendientes
	 * cuando el profesor descompleta una actividad al marcarla como No Exitosa
	 * @param act actividad a añadir de vuelta a pendientes
	 */
	private void addActividadPendiente(Actividad act)
	{
		if (!this.actPendientes.contains(act))
		{
			this.actPendientes.add(act);
		}
		if (!this.actObligatoriasPendientes.contains(act) && act.isObligatorio())
		{
			this.actObligatoriasPendientes.add(act);
		}
	}
	
	/**
	 * Elimina una actividad completada cuando el profesor 
	 * descompleta una actividad al marcarla como No Exitosa
	 * @param act actividad a eliminar de la lista de actividades completadas
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
	 * Añade una actividad en progreso a la lista de actividades completadas
	 * cuando se completa esa actividad
	 * @param act Actividad completada 
	 */
	private void addActividadCompletada(Actividad act)
	{
		if (!this.actCompletadas.contains(act))
		{
			this.actCompletadas.add(act);
		}
		if (!this.actObligatoriasCompletadas.contains(act) && act.isObligatorio())
		{
			this.actObligatoriasCompletadas.add(act);
		}
	}
	
	/**
	 * Calcula el progreso del estudiante basandose en el número de actividades
	 * obligatorias completadas y el número de actividades totales completadas
	 */
	private void calcularProgreso() {
		// Verifica si las listas son null y las inicializa si es necesario
	    if (this.actPendientes == null) {
	        this.actPendientes = new ArrayList<Actividad>();
	    }
	    if (this.actCompletadas == null) {
	        this.actCompletadas = new ArrayList<Actividad>();
	    }
	    if (this.actObligatoriasCompletadas == null) {
	        this.actObligatoriasCompletadas = new ArrayList<Actividad>();
	    }
	    if (this.actObligatoriasPendientes == null) {
	        this.actObligatoriasPendientes = new ArrayList<Actividad>();
	    }
		int actTotales = this.actPendientes.size() + this.actCompletadas.size();
		int actCompletadas = this.actCompletadas.size();
		if (actTotales != 0)
			this.progresoTotal = (actCompletadas / actTotales) * 100;
		
		actTotales = this.actObligatoriasCompletadas.size() + this.actObligatoriasPendientes.size();
		actCompletadas = this.actObligatoriasCompletadas.size();
		if (actTotales != 0)
			this.progresoObligatorio = (actCompletadas / actTotales) * 100;
	}
	
	
	public int obtenerPosicionActividadPendiente(Actividad actividad) {
        if (actividad == null || actPendientes == null) {
            throw new IllegalArgumentException("Actividad o lista no pueden ser nulas.");
        }

        for (int i = 0; i < actPendientes.size(); i++) {
            if (actPendientes.get(i).equals(actividad)) {
                return i;
            }
        }

        return -1; // No se encontró la actividad
        
    }
	
	public int obtenerPosicionActividadCompletada(Actividad actividad) {
        if (actividad == null || actCompletadas == null) {
            throw new IllegalArgumentException("Actividad o lista no pueden ser nulas.");
        }

        for (int i = 0; i < actCompletadas.size(); i++) {
            if (actCompletadas.get(i).equals(actividad)) {
                return i;
            }
        }

        return -1; // No se encontró la actividad
	}
        
     public int getNumero(Actividad actividad, boolean completado) {
    	 int i;
    	 if (completado) {
    		 i = obtenerPosicionActividadCompletada(actividad) ;
    	 }
    	 else {
    		 i = obtenerPosicionActividadPendiente(actividad) ;
    	 }
    	 return i;
     }
	
}
