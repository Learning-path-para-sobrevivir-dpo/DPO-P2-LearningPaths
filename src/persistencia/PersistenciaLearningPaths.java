package persistencia;


import modelo.*;
import modelo.actividades.Actividad;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class PersistenciaLearningPaths {

    private static final String ARCHIVO_LEARNINGPATHS = "datos/learningPaths.json";

    // Método para cargar Learning Paths desde un archivo JSON
    public static HashMap<String, LearningPath> cargarLearningPaths(HashMap<List<String>, Progreso> progresosMap, HashMap<String, Actividad> actividadesMap) {
        HashMap<String, LearningPath> learningPaths = new HashMap<>();

        try {
            // Leer el archivo JSON
            String content = new String(Files.readAllBytes(Paths.get(ARCHIVO_LEARNINGPATHS)));

            if (!content.isBlank())
            {
            	// Convertir el contenido en un JSONArray
            	JSONArray jsonLearningPaths = new JSONArray(content);

            	// Iterar sobre cada objeto en el JSONArray
            	for (int i = 0; i < jsonLearningPaths.length(); i++) {
            		JSONObject jsonLearningPath = jsonLearningPaths.getJSONObject(i);

            		// Obtener datos de LearningPath
            		String titulo = jsonLearningPath.getString("titulo");
            		String descripcion = jsonLearningPath.getString("descripcion");
            		String objetivo = jsonLearningPath.getString("objetivo");
            		int nivelDificultad = jsonLearningPath.getInt("nivelDificultad");
            		int rating = jsonLearningPath.getInt("rating");
            		String fechaCreacion = jsonLearningPath.getString("fechaCreacion");
            		String fechaModificacion = jsonLearningPath.getString("fechaModificacion");
            		int version = jsonLearningPath.getInt("version");
            		String autor = jsonLearningPath.getString("autor");

            		// Crear instancia de LearningPath
            		LearningPath learningPath = new LearningPath(titulo, descripcion, objetivo, nivelDificultad, rating, fechaCreacion, fechaModificacion, version, autor);

            		// Cargar progresos
            		JSONObject jsonProgresos = jsonLearningPath.getJSONObject("progresosEstudiantiles");
            		for (String estudiante : jsonProgresos.keySet()) {
            			JSONArray jsonArrayProgreso = jsonProgresos.getJSONArray(estudiante);
            			List<String> idProgreso = new ArrayList<String>();
            			for (int j = 0; j < jsonArrayProgreso.length(); j++) {
            				idProgreso.add(jsonArrayProgreso.getString(j));
            			}
            			Progreso progreso = progresosMap.get(idProgreso);
            			if (progreso != null) {
            				learningPath.getProgresosEstudiantiles().put(estudiante, progreso);
            			}
            		}

            		// Cargar estudiantes
            		JSONArray jsonEstudiantes = jsonLearningPath.getJSONArray("estudiantes");
            		for (int j = 0; j < jsonEstudiantes.length(); j++) {
            			String estudiante = jsonEstudiantes.getString(j);
            			learningPath.getEstudiantes().add(estudiante);
            		}

            		// Cargar actividades
            		JSONObject jsonActividades = jsonLearningPath.getJSONObject("actividades");
            		for (String key : jsonActividades.keySet()) {
            			int orden = Integer.parseInt(key);
            			String idActividad = jsonActividades.getString(key);
            			Actividad actividad = actividadesMap.get(idActividad);
            			if (actividad != null) {
            				learningPath.getActividades().put(orden, actividad);
            			}
            			
            		}

            		// Agregar el LearningPath al HashMap
            		learningPaths.put(titulo, learningPath);
            	}
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return learningPaths;
    }

    // Método para guardar Learning Paths en un archivo JSON
    public static void guardarLearningPaths(HashMap<String, LearningPath> learningPaths) {
        try {
            // Crear un JSONArray para almacenar los Learning Paths
            JSONArray jsonLearningPaths = new JSONArray();

            // Iterar sobre cada Learning Path
            for (LearningPath learningPath : learningPaths.values()) {
                JSONObject jsonLearningPath = new JSONObject();
                jsonLearningPath.put("titulo", learningPath.getTitulo());
                jsonLearningPath.put("descripcion", learningPath.getDescripcion());
                jsonLearningPath.put("objetivo", learningPath.getObjetivo());
                jsonLearningPath.put("nivelDificultad", learningPath.getNivelDificultad());
                jsonLearningPath.put("duracion", learningPath.getDuracion());
                jsonLearningPath.put("rating", learningPath.getRating());
                jsonLearningPath.put("fechaCreacion", learningPath.getFechaCreacion());
                jsonLearningPath.put("fechaModificacion", learningPath.getFechaModificacion());
                jsonLearningPath.put("version", learningPath.getVersion());
                jsonLearningPath.put("autor", learningPath.getAutor());

                // Guardar los progresos como ID
                JSONObject jsonProgresos = new JSONObject();
                for (String estudiante : learningPath.getProgresosEstudiantiles().keySet()) {
                    Progreso progreso = learningPath.getProgresosEstudiantiles().get(estudiante);
                    if (progreso != null) {
                    	
                        jsonProgresos.put(estudiante, List.of(learningPath.getTitulo(), estudiante));
                    }
                }
                jsonLearningPath.put("progresosEstudiantiles", jsonProgresos);

                // Guardar los estudiantes
                JSONArray jsonEstudiantes = new JSONArray();
                for (String estudiante : learningPath.getEstudiantes()) {
                    jsonEstudiantes.put(estudiante);
                }
                jsonLearningPath.put("estudiantes", jsonEstudiantes);

                // Guardar las actividades como ID
                JSONObject jsonActividades = new JSONObject();
                for (Integer orden : learningPath.getActividades().keySet()) {
                    Actividad actividad = learningPath.getActividades().get(orden);
                    if (actividad != null) {
                        jsonActividades.put(orden.toString(), actividad.getId());
                    }
                }
                jsonLearningPath.put("actividades", jsonActividades);

                // Agregar el JSONObject al JSONArray
                jsonLearningPaths.put(jsonLearningPath);
            }

            // Escribir el JSONArray al archivo
            try (FileWriter file = new FileWriter(ARCHIVO_LEARNINGPATHS)) {
                file.write(jsonLearningPaths.toString(4));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
