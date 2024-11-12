package consola;

import java.util.List;

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
		
		System.out.println("- " + act.getTitulo() + ":");
		System.out.println("• Tipo: " + act.getTipoActividad());
		System.out.println("• Objetivo: " + act.getObjetivo());
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
		
		System.out.println("\n");
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
				System.out.println("• Medio de entrega: " + t.getMedioEntrega());
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
	}
	
	public void imprimirLearningPath(LearningPath lp)
	{
		
	}
	
	public void imprimirProgreso(Progreso prog)
	{
		System.out.println(prog.getEstudiante());
		System.out.println("- Progreso en el  Learning Path " + prog.getLearningPath());
		System.out.println("-----------------------------------------------------");
		
		System.out.println("Actividades Completadas:");
		List<Actividad> acts = prog.getActCompletadas();
		for (Actividad act: acts)
		{
			imprimirActividad(act, false, true, true);
		}
		
		System.out.println("Actividades Pendientes:");
		acts = prog.getActObligatoriasPendientes();
		for (Actividad act: acts)
		{
			imprimirActividad(act, false, true, true);
		}
		
		System.out.println("Actividad en progreso: ");
		imprimirActividad(prog.getActividadEnProgreso(), false, true, true);
	}
}
