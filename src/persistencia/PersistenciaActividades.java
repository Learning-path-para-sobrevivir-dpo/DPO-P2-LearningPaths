package persistencia;

import modelo.*;
import modelo.actividades.Actividad;
import modelo.actividades.Encuesta;
import modelo.actividades.Examen;
import modelo.actividades.Pregunta;
import modelo.actividades.PreguntaAbierta;
import modelo.actividades.PreguntaMultiple;
import modelo.actividades.PreguntaVerdaderoFalso;
import modelo.actividades.Quiz;
import modelo.actividades.QuizOpcionMultiple;
import modelo.actividades.QuizVerdaderoFalso;
import modelo.actividades.RecursoEducativo;
import modelo.actividades.Tarea;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;



public class PersistenciaActividades {

    private static final String ARCHIVO_ACTIVIDADES = "datos/actividades.json";

    // Cargar actividades desde el archivo JSON en un HashMap
    public static HashMap<String, Actividad> cargarActividades(HashMap<String, Review> reviewsMap, HashMap<String, Pregunta> preguntasMap) {
        HashMap<String, Actividad> actividades = new HashMap<>();
        try {
            // Leer todo el contenido del archivo JSON
            String content = new String(Files.readAllBytes(Paths.get(ARCHIVO_ACTIVIDADES)));
            
            if (!content.isBlank())
            {
            	// Convertir el contenido en un JSONArray
            	JSONArray jsonActividades = new JSONArray(content);

            	// Iterar sobre cada objeto en el JSONArray
            	for (int i = 0; i < jsonActividades.length(); i++) {
            		JSONObject jsonActividad = jsonActividades.getJSONObject(i);

            		// Obtener los datos de la actividad
            		String titulo = jsonActividad.getString("titulo");
            		String objetivo = jsonActividad.getString("objetivo");
            		int nivelDificultad = jsonActividad.getInt("nivelDificultad");
            		int duracionMin = jsonActividad.getInt("duracionMin");
            		boolean obligatorio = jsonActividad.getBoolean("obligatorio");
            		int tiempoCompletarSugerido = jsonActividad.getInt("tiempoCompletarSugerido");
            		String tipoActividad = jsonActividad.getString("tipoActividad");
            		boolean completada = jsonActividad.getBoolean("completada");
            		String estado = jsonActividad.getString("estado");

            		// Crear una instancia de Actividad
            		Actividad actividad;
            		String id = jsonActividad.getString("id");
            		String idEstudiante = jsonActividad.getString("idEstudiante");
            		switch (tipoActividad) {
            		case "Prueba":
            			String tipoPrueba = jsonActividad.getString("tipoPrueba");
            			switch (tipoPrueba) {
            			case "Encuesta":
            				JSONArray jsonPreguntas = jsonActividad.getJSONArray("preguntas");
            				List<PreguntaAbierta> listaPreguntas = new ArrayList<>();
            				for (int j = 0; j < jsonPreguntas.length(); j++) {
            					String enunciadoPregunta = jsonPreguntas.getString(j);
            					// Buscar la pregunta en el HashMap de preguntas usando el enunciado como clave
            					Pregunta pregunta = preguntasMap.get(enunciadoPregunta );
            					if (pregunta != null) {
            						listaPreguntas.add((PreguntaAbierta) pregunta);
            					}
            				}
            				actividad = new Encuesta(titulo, objetivo, nivelDificultad, duracionMin, obligatorio, tiempoCompletarSugerido, tipoActividad, listaPreguntas, tipoPrueba, id, idEstudiante);
            				break;
            			case "Quiz Opcion Multiple":
            				JSONArray jsonPreguntas3 = jsonActividad.getJSONArray("preguntas");
            				List<PreguntaMultiple> listaPreguntas3 = new ArrayList<>();
            				for (int j = 0; j < jsonPreguntas3.length(); j++) {
            					String enunciadoPregunta = jsonPreguntas3.getString(j);
            					// Buscar la pregunta en el HashMap de preguntas usando el enunciado como clave
            					Pregunta pregunta = preguntasMap.get(enunciadoPregunta );
            					if (pregunta != null) {
            						listaPreguntas3.add((PreguntaMultiple) pregunta);
            					}
            				}
            				float calificacionMinima = jsonActividad.getFloat("calificacionMinima");
            				actividad = new QuizOpcionMultiple(titulo, objetivo, nivelDificultad, duracionMin, obligatorio,
            						tiempoCompletarSugerido, tipoActividad, calificacionMinima, listaPreguntas3, tipoPrueba, id, idEstudiante);
            				break;
            			case "Quiz Verdadero Falso":
            				JSONArray jsonPreguntas4 = jsonActividad.getJSONArray("preguntas");
            				List<PreguntaVerdaderoFalso> listaPreguntas4 = new ArrayList<>();
            				for (int j = 0; j < jsonPreguntas4.length(); j++) {
            					String enunciadoPregunta = jsonPreguntas4.getString(j);
            					// Buscar la pregunta en el HashMap de preguntas usando el enunciado como clave
            					Pregunta pregunta = preguntasMap.get(enunciadoPregunta );
            					if (pregunta != null) {
            						listaPreguntas4.add((PreguntaVerdaderoFalso) pregunta);
            					}
            				}
            				float calificacionMinima2 = jsonActividad.getFloat("calificacionMinima");
            				actividad = new QuizVerdaderoFalso(titulo, objetivo, nivelDificultad, duracionMin, obligatorio,
            						tiempoCompletarSugerido, tipoActividad, calificacionMinima2, tipoPrueba,  listaPreguntas4, id, idEstudiante);
            				break;
            			case "Examen":
            				JSONArray jsonPreguntas2 = jsonActividad.getJSONArray("preguntas");
            				List<PreguntaAbierta> listaPreguntas2 = new ArrayList<>();
            				for (int j = 0; j < jsonPreguntas2.length(); j++) {
            					String enunciadoPregunta = jsonPreguntas2.getString(j);
            					Pregunta pregunta = preguntasMap.get(enunciadoPregunta );
            					if (pregunta != null) {
            						listaPreguntas2.add((PreguntaAbierta) pregunta);
            					}
            				}
            				actividad = new Examen(titulo, objetivo, nivelDificultad, duracionMin,
            						obligatorio, tiempoCompletarSugerido, tipoActividad, listaPreguntas2, tipoPrueba, id, idEstudiante);
            				Boolean calificado = jsonActividad.getBoolean("calificado");
            				((Examen) actividad).setCalificado(calificado);
            				break;
            			default:
            				throw new IllegalArgumentException("Tipo de actividad desconocido: " + tipoActividad);
            			}
            			break;
            		case "Tarea":
            			String contenido = jsonActividad.getString("contenido");
            			String medio = jsonActividad.getString("medioEntrega");
            			boolean enviada = jsonActividad.getBoolean("enviada");
            			actividad = new Tarea(titulo, objetivo, nivelDificultad, duracionMin, obligatorio, tiempoCompletarSugerido, tipoActividad, contenido, id, idEstudiante, medio);
            			Tarea t = (Tarea) actividad;
            			t.setEnviado(enviada);
            			t.setMedioEntrega(medio);
            			actividad = t;
            			break;
            		case "Recurso Educativo":
            			String contenido2 = jsonActividad.getString("contenido");
            			String tipoRecurso =  jsonActividad.getString("tipoRecurso");
            			String enlace = jsonActividad.getString("enlace");
            			actividad = new RecursoEducativo(titulo, objetivo, nivelDificultad, duracionMin,
            					obligatorio, tiempoCompletarSugerido, tipoActividad, tipoRecurso, contenido2, enlace, id, idEstudiante);
            			break;
            		default:
            			throw new IllegalArgumentException("Tipo de actividad desconocido: " + tipoActividad);
            		}
            		actividad.setCompletada(completada);
            		actividad.setEstado(estado);
            		id = actividad.getId();

            		// Leer las reviews de la actividad
            		JSONArray jsonReviews = jsonActividad.getJSONArray("reviews");
            		List<Review> listaReviews = new ArrayList<>();
            		for (int j = 0; j < jsonReviews.length(); j++) {
            			String contenidoReview = jsonReviews.getString(j);
            			// Buscar la review en el HashMap de reviews usando el contenido como clave
            			Review review = reviewsMap.get(contenidoReview);
            			if (review != null) {
            				listaReviews.add(review);
            			}
            		}
            		actividad.setReviews(listaReviews);
            		

            		// Agregar la actividad al HashMap usando el id como llave
            		if (!idEstudiante.equals(""))
            			actividades.put(idEstudiante, actividad);
            		else if (!actividades.containsKey(id))
            			actividades.put(id, actividad);
            		
            	}
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return actividades;  // Devolver el mapa de actividades
    }

    // Guardar actividades en el archivo JSON desde un HashMap
    public static void guardarActividades(HashMap<String, Actividad> actividades) {
        try {
            // Crear un JSONArray para almacenar las actividades
            JSONArray jsonActividades = new JSONArray();

            // Convertir cada actividad en un JSONObject
            for (Actividad actividad : actividades.values()) {
                JSONObject jsonActividad = new JSONObject();
                jsonActividad.put("titulo", actividad.getTitulo());
                jsonActividad.put("objetivo", actividad.getObjetivo());
                jsonActividad.put("nivelDificultad", actividad.getNivelDificultad());
                jsonActividad.put("duracionMin", actividad.getDuracionMin());
                jsonActividad.put("obligatorio", actividad.isObligatorio());
                jsonActividad.put("tiempoCompletarSugerido", actividad.getTiempoCompletarSugerido());
                jsonActividad.put("tipoActividad", actividad.getTipoActividad());
                jsonActividad.put("id", actividad.getId());
                jsonActividad.put("idEstudiante", actividad.getIdEstudiante());
                jsonActividad.put("completada", actividad.isCompletada());
                jsonActividad.put("estado", actividad.getEstado());

                // Procesar las actividades según su tipo
                if (actividad instanceof Encuesta) {
                    Encuesta encuesta = (Encuesta) actividad;
                    jsonActividad.put("tipoPrueba", "Encuesta");
                    
                    // Convertir las preguntas de Encuesta
                    JSONArray jsonPreguntas = new JSONArray();
                    for (PreguntaAbierta pregunta : encuesta.getPreguntas()) {
                        jsonPreguntas.put(pregunta.getEnunciado());
                    }
                    jsonActividad.put("preguntas", jsonPreguntas);

                } else if (actividad instanceof QuizOpcionMultiple) {
                    QuizOpcionMultiple quiz = (QuizOpcionMultiple) actividad;
                    jsonActividad.put("tipoPrueba", "Quiz Opcion Multiple");
                    jsonActividad.put("calificacionMinima", quiz.getCalificacionMinima());

                    // Convertir las preguntas de Quiz
                    JSONArray jsonPreguntas = new JSONArray();
                    for (PreguntaMultiple pregunta : quiz.getPreguntas()) {
                        jsonPreguntas.put(pregunta.getEnunciado());
                    }
                    jsonActividad.put("preguntas", jsonPreguntas);
                    
                } else if (actividad instanceof QuizVerdaderoFalso) {
                	QuizVerdaderoFalso quiz = (QuizVerdaderoFalso) actividad;
                    jsonActividad.put("tipoPrueba", "Quiz Verdadero Falso");
                    jsonActividad.put("calificacionMinima", quiz.getCalificacionMinima());

                    // Convertir las preguntas de Quiz
                    JSONArray jsonPreguntas = new JSONArray();
                    for (PreguntaVerdaderoFalso pregunta : quiz.getPreguntas()) {
                        jsonPreguntas.put(pregunta.getEnunciado());
                    }
                    jsonActividad.put("preguntas", jsonPreguntas);

                } else if (actividad instanceof Examen) {
                    Examen examen = (Examen) actividad;
                    jsonActividad.put("tipoPrueba", "Examen");
                    jsonActividad.put("calificado", examen.isCalificado());

                    // Convertir las preguntas de Examen
                    JSONArray jsonPreguntas = new JSONArray();
                    for (PreguntaAbierta pregunta : examen.getPreguntas()) {
                        jsonPreguntas.put(pregunta.getEnunciado());
                    }
                    jsonActividad.put("preguntas", jsonPreguntas);

                } else if (actividad instanceof Tarea) {
                    Tarea tarea = (Tarea) actividad;
                    jsonActividad.put("contenido", tarea.getContenido());
                    jsonActividad.put("medioEntrega", tarea.getMedioEntrega());
                    jsonActividad.put("enviada", tarea.isEnviado());

                } else if (actividad instanceof RecursoEducativo) {
                    RecursoEducativo recurso = (RecursoEducativo) actividad;
                    jsonActividad.put("contenido", recurso.getContenido());
                    jsonActividad.put("tipoRecurso", recurso.getTipoRecurso());
                    jsonActividad.put("enlace", recurso.getEnlace());
                }

                // Guardar solo el contenido de las reviews en el JSON
                JSONArray jsonReviews = new JSONArray();
                for (Review review : actividad.getReviews()) {
                    jsonReviews.put(review.getContenido());
                }
                jsonActividad.put("reviews", jsonReviews);

                // Agregar el JSONObject al JSONArray
                jsonActividades.put(jsonActividad);
            }

            // Escribir el JSONArray al archivo
            try (FileWriter file = new FileWriter(ARCHIVO_ACTIVIDADES)) {
                file.write(jsonActividades.toString(4));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
