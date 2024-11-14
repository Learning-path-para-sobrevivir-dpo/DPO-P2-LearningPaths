package consola;

import java.util.List;

import modelo.Estudiante;
import modelo.LearningPath;
import modelo.Progreso;
import modelo.actividades.Actividad;
import modelo.actividades.Examen;
import modelo.actividades.Prueba;
import modelo.actividades.Quiz;
import modelo.actividades.RecursoEducativo;
import modelo.actividades.Tarea;

/**
 * Clase usada para imprimir formateado la información
 * de cada clase principal
 */
public class ImprimirConsola {

	public ImprimirConsola() {
		super();
	}
	
	public void imprimirActividad(Actividad act, boolean imprimirActPrevias, boolean imprimirTipoAct, boolean imprimirInfoAdicional)
	{
		int nivel = act.getNivelDificultad();
		String nivelDif = "-";
		if (nivel == 1)
		{
			nivelDif = "Facil";
		}
		else if (nivel == 2)
		{
			nivelDif = "Intermedio";
		}
		else if (nivel == 3)
		{
			nivelDif = "Dificil";
		}
		
		System.out.println("\n- " + act.getTitulo() + ":");
		System.out.println("• Tipo: " + act.getTipoActividad());
		System.out.println("• Objetivo: " + act.getObjetivo());
		System.out.println("• Nivel de dificultad: " + nivelDif);
		System.out.println("• Tiempo Estimado: "+ Integer.toString(act.getDuracionMin()));
		String ob = "No";
		if (act.isObligatorio())
		{
			ob = "Si";
		}
		System.out.println("• Es obligatoria: "+ ob);
		
		List<Actividad> actPrevias = act.getActPreviasSugeridas();
		if (imprimirActPrevias && !actPrevias.isEmpty())
		{
			System.out.println("• Actividades Previas Sugeridas: ");
			for (Actividad actPrevia: actPrevias)
			{
				System.out.println("	• " + actPrevia.getTitulo());
			}
		}
		
		if (imprimirTipoAct)
		{
			imprimirInfoTipoActividad(act, imprimirInfoAdicional);
		}
	}
	
	public void imprimirInfoTipoActividad(Actividad act, boolean imprimirInfoAdicional)
	{
		String tipoActividad = act.getTipoActividad();
		switch (tipoActividad)
		{
		case "Recurso Educativo":
			RecursoEducativo rec = (RecursoEducativo) act;
			System.out.println("• Tipo de Recurso: " + rec.getTipoRecurso());
			System.out.println("• Contenido: " + rec.getContenido());
			System.out.println("• Enlace: " + rec.getEnlace());
			break;
		
		case "Tarea":
			Tarea t = (Tarea) act;
			System.out.println("• Contenido: " + t.getContenido());
			if (imprimirInfoAdicional)
			{
				String enviada = "No";
				if (t.isEnviado())
				{
					enviada = "Si";
				}
				System.out.println("• Enviada: " + enviada);
				String medio = "No entregado";
				if (t.getMedioEntrega() != null)
				{
					medio = t.getMedioEntrega();
				}
				System.out.println("• Medio de entrega: " + medio);
				
			}
			break;
			
		case "Prueba":
			Prueba p = (Prueba) act;
			System.out.println("• Tipo de Prueba: " + p.getTipoPrueba());
			if (imprimirInfoAdicional)
			{
				System.out.println("• Calificacion: " + Float.toString(p.getCalificacion()));
				String respondida = "No";
				if (p.isRespondida())
				{
					respondida = "Si";
				}
				System.out.println("• Enviada: " + respondida);
				if (p instanceof Examen)
				{
					Examen e = (Examen) p;
					String c = "No";
					if (e.isCalificado())
					{
						c = "Si";
					}
					System.out.println("• Calificado: " + c);
				}
			}
			if (p instanceof Quiz)
			{
				Quiz q = (Quiz) p;
				System.out.println("• Calificacion Mínima: " + Float.toString(q.getCalificacionMinima()));
			}
			
		}
		if (imprimirInfoAdicional)
		{
			System.out.println("• Completada: " + Boolean.toString(act.isCompletada()));
			System.out.println("• Estado: " + act.getEstado());
		}
	}
	
	public void imprimirLearningPath(LearningPath lp)
	{
		
	}
	
	public void imprimirProgreso(Progreso prog)
	{
		System.out.println("\n"+ prog.getEstudiante());
		System.out.println("- Progreso en el  Learning Path " + prog.getLearningPath());
		System.out.println("    • Progreso Total: " + Integer.toString(prog.getProgresoTotal())+ "% de actividades completadas");
		System.out.println("    • Progreso Actividades Obligatorias: " + Integer.toString(prog.getProgresoObligatorio())+ "% de actividades completadas");
		System.out.println("-----------------------------------------------------");
		
		System.out.println("Actividades Completadas:");
		List<Actividad> acts = prog.getActCompletadas();
		if (!acts.isEmpty())
		{
			for (Actividad act: acts)
			{
				imprimirActividad(act, false, true, true);
			}
			System.out.println("-----------------------------------------------------");
			System.out.println("Actividades Obligatorias Completadas:");
			acts = prog.getActObligatoriasCompletadas();
			if (!acts.isEmpty())
			{
				for (Actividad act: acts)
				{
					imprimirActividad(act, false, true, true);
				}
				System.out.println("-----------------------------------------------------");
			}
			else
			{
				System.out.println("\nNo hay actividades completadas");
				System.out.println("-----------------------------------------------------");
			}
		}
		else
		{
			System.out.println("\nNo hay actividades completadas");
			System.out.println("-----------------------------------------------------");
		}
		
		
		acts = prog.getActPendientes();
		System.out.println("ActividadesPendientes:");
		if (acts.isEmpty())
		{
			System.out.println("No tiene actividades pendientes");
			System.out.println("-----------------------------------------------------");
		}
		else
		{		
			for (Actividad act: acts)
			{
				imprimirActividad(act, false, true, true);
			}
			System.out.println("-----------------------------------------------------");
			System.out.println("Actividades Obligatorias Pendientes:");
			acts = prog.getActObligatoriasPendientes();
			if (acts.isEmpty())
			{
				System.out.println("No tiene actividades pendientes");
				System.out.println("-----------------------------------------------------");
			}
			else
			{
				for (Actividad act: acts)
				{
					imprimirActividad(act, false, true, true);
				}
				System.out.println("-----------------------------------------------------");
			}
			System.out.println("Actividad en progreso: ");
			if (prog.getActividadEnProgreso() != null) 
			{
				imprimirActividad(prog.getActividadEnProgreso(), false, true, true);
				System.out.println("-----------------------------------------------------");
			}
			else
			{
				System.out.println("No tiene ninguna actividad en progreso");
				System.out.println("-----------------------------------------------------");
			}
			System.out.println();
		}
	}
	
	public void imprimirEstudiante(Estudiante est)
	{
		System.out.println(est.getLogin());
		System.out.println("-----------------------------------------------------");
		System.out.println("• Correo: " + est.getCorreo());
		System.out.println("• Learning Paths inscritos: ");
		for (String namePath: est.getLearningPaths().keySet())
		{
			System.out.println("	• " + namePath);
		}
	}
}