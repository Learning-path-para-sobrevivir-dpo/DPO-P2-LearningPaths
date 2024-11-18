package persistencia;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import modelo.*;
import modelo.actividades.Actividad;

import org.json.JSONArray;
import org.json.JSONObject;
public class PersistenciaProgresos {

    private static final String ARCHIVO_PROGRESO = "datos/progresos.json";

    public static HashMap<List<String>, Progreso> cargarProgresos(Map<String, Actividad> mapaActividades) {
        HashMap<List<String>, Progreso> progresos = new HashMap<>();

        try {
            /// Leer todo el contenido del archivo JSON
            String content = new String(Files.readAllBytes(Paths.get(ARCHIVO_PROGRESO)));
            
            if (!content.isBlank())
            {
            	// Convertir el contenido en un JSONArray
            	JSONArray jsonProgresos = new JSONArray(content);

            	// Iterar sobre el JSONArray y convertir cada objeto JSON a Progreso
            	for (int i = 0; i < jsonProgresos.length(); i++) {
            		JSONObject jsonProgreso = jsonProgresos.getJSONObject(i);

            		String learningPath = jsonProgreso.getString("learningPath");
            		String estudiante = jsonProgreso.getString("estudiante");
            		int progresoTotal = jsonProgreso.getInt("progresoTotal");
            		int progresoObligatorio = jsonProgreso.getInt("progresoObligatorio");

            		// Crear un nuevo objeto Progreso
            		Progreso progreso = new Progreso(learningPath, estudiante);

            		// Cargar actividadesPath desde el JSON
            		JSONArray jsonActividadesPath = jsonProgreso.getJSONArray("actividadesPath");
            		for (int j = 0; j < jsonActividadesPath.length(); j++) {
            			String key = jsonActividadesPath.getString(j); // Suponiendo que la clave es un entero (ID de actividad)
            			Actividad actividad = mapaActividades.get(key); // Obtener actividad por ID desde el mapa
            			if (actividad != null) {
            				progreso.getActividadesPath().put(key, actividad);
            			}
            		}

            		// Cargar otras listas de actividades
            		progreso.setActObligatoriasPendientes(cargarListaActividades(jsonProgreso.getJSONArray("actObligatoriasPendientes"), mapaActividades));
            		progreso.setActObligatoriasCompletadas(cargarListaActividades(jsonProgreso.getJSONArray("actObligatoriasCompletadas"), mapaActividades));
            		progreso.setActPendientes(cargarListaActividades(jsonProgreso.getJSONArray("actPendientes"), mapaActividades));
            		progreso.setActCompletadas(cargarListaActividades(jsonProgreso.getJSONArray("actCompletadas"), mapaActividades));

            		// Cargar actividadEnProgreso (si existe)
            		if (!jsonProgreso.isNull("actividadEnProgreso")) {
            			String idActividad = jsonProgreso.getString("actividadEnProgreso");
            			Actividad actividadEnProgreso = mapaActividades.get(idActividad); 
            			progreso.setActividadEnProgreso(actividadEnProgreso);
            		}

            		// Cargar idActividades
            		JSONObject jsonIdActividades = jsonProgreso.getJSONObject("idActividadesOriginales");
            		for (String key : jsonIdActividades.keySet()) {
            			int idActividad = jsonIdActividades.getInt(key);
            			Actividad actividad = mapaActividades.get(String.valueOf(idActividad)); // Obtener actividad por ID desde el mapa
            			if (actividad != null) {
            				progreso.getIdActividades().put(key, actividad);
            			}
            		}
            		
            		progreso.setProgresoObligatorio(progresoObligatorio);
            		progreso.setProgresoTotal(progresoTotal);

            		// Agregar progreso al mapa
            		progresos.put(List.of(learningPath, estudiante), progreso);
            	}
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return progresos;
    }

    // Método auxiliar para cargar listas de actividades por IDs
    private static List<Actividad> cargarListaActividades(JSONArray jsonArray, Map<String, Actividad> mapaActividades){
        List<Actividad> actividades = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            String idActividad = jsonArray.getString(i);
            Actividad actividad = mapaActividades.get(idActividad); // Obtener actividad por ID desde el mapa
            if (actividad != null) {
                actividades.add(actividad);
            }
        }
        return actividades;
    }

    public static void guardarProgreso(HashMap<List<String>, Progreso> progresos) {
        try {
            // Crear un JSONArray para almacenar los progresos
            JSONArray jsonProgresos = new JSONArray();

            // Iterar sobre el mapa de progresos y convertir cada progreso a JSON
            for (Map.Entry<List<String>, Progreso> entry : progresos.entrySet()) {
                List<String> key = entry.getKey();
                Progreso progreso = entry.getValue();

                JSONObject jsonProgreso = new JSONObject();
                jsonProgreso.put("learningPath", key.get(0));
                jsonProgreso.put("estudiante", key.get(1));

                // Convertir actividadesPath a JSONArray de IDs de actividades
                JSONArray jsonActividadesPath = new JSONArray();
                for (Map.Entry<String, Actividad> actEntry : progreso.getActividadesPath().entrySet()) {
                    jsonActividadesPath.put(actEntry.getKey());
                }
                jsonProgreso.put("actividadesPath", jsonActividadesPath);

                // Convertir actObligatoriasPendientes a JSONArray de IDs de actividades
                JSONArray jsonActObligatoriasPendientes = new JSONArray();
                
                if (progreso.getActObligatoriasPendientes() != null) {
                for (Actividad actividad : progreso.getActObligatoriasPendientes()) {
                    jsonActObligatoriasPendientes.put(actividad.getIdEstudiante());
                }}
                jsonProgreso.put("actObligatoriasPendientes", jsonActObligatoriasPendientes);

                // Convertir actObligatoriasCompletadas a JSONArray de IDs de actividades
                JSONArray jsonActObligatoriasCompletadas = new JSONArray();
                if (progreso.getActObligatoriasCompletadas() != null) {
                for (Actividad actividad : progreso.getActObligatoriasCompletadas()) {
                    jsonActObligatoriasCompletadas.put(actividad.getIdEstudiante());
                }}
                jsonProgreso.put("actObligatoriasCompletadas", jsonActObligatoriasCompletadas);

                // Convertir actPendientes a JSONArray de IDs de actividades
                JSONArray jsonActPendientes = new JSONArray();
                if (progreso.getActPendientes() != null) {
                for (Actividad actividad : progreso.getActPendientes()) {
                    jsonActPendientes.put(actividad.getIdEstudiante());
                }}
                jsonProgreso.put("actPendientes", jsonActPendientes);

                // Convertir actCompletadas a JSONArray de IDs de actividades
                JSONArray jsonActCompletadas = new JSONArray();
                if (progreso.getActCompletadas() != null) {
                for (Actividad actividad : progreso.getActCompletadas()) {
                    jsonActCompletadas.put(actividad.getIdEstudiante());
                }}
                jsonProgreso.put("actCompletadas", jsonActCompletadas);

                // Convertir actividadEnProgreso a ID de actividad (si no es null)
                if (progreso.getActividadEnProgreso() != null) {
                    jsonProgreso.put("actividadEnProgreso", progreso.getActividadEnProgreso().getIdEstudiante());
                } else {
                    jsonProgreso.put("actividadEnProgreso", JSONObject.NULL);
                }

                // Convertir idActividades a JSONObject de IDs de actividades
                JSONObject jsonIdActividades = new JSONObject();
                if (progreso.getIdActividades() != null) {
                for (Map.Entry<String, Actividad> idActEntry : progreso.getIdActividades().entrySet()) {
                    jsonIdActividades.put(idActEntry.getKey(), idActEntry.getValue().getIdEstudiante());
                }}
                jsonProgreso.put("idActividadesOriginales", jsonIdActividades);
                jsonProgreso.put("progresoTotal", progreso.getProgresoTotal());
                jsonProgreso.put("progresoObligatorio", progreso.getProgresoObligatorio());

                // Agregar progreso JSON al array de progresos
                jsonProgresos.put(jsonProgreso);
            }

            // Escribir el JSONArray al archivo
            try (FileWriter file = new FileWriter(ARCHIVO_PROGRESO)) {
                file.write(jsonProgresos.toString(2)); // El segundo argumento es para pretty-printing
                file.flush();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
