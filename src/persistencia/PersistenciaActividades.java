package persistencia;

import modelo.Actividad;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

public class PersistenciaActividades {
    private static final String ARCHIVO_ACTIVIDADES = "C:\\Users\\manue\\git\\DPO-P1-LearningPaths\\datos\\actividades.json";

    // Cargar Actividades desde el archivo JSON
    public static HashMap<String, Actividad> cargarActividades() {
        HashMap<String, Actividad> actividades = new HashMap<>();

        try {
            // Leer contenido del archivo JSON
            String content = new String(Files.readAllBytes(Paths.get(ARCHIVO_ACTIVIDADES)));
            JSONArray jsonArray = new JSONArray(content);

            // Iterar sobre las actividades en el archivo JSON
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonActividad = jsonArray.getJSONObject(i);

                // Crear el objeto Actividad
                actividad.setNombre(jsonActividad.getString("nombre"));
                actividad.setDescripcion(jsonActividad.getString("descripcion"));
                actividad.setEstado(jsonActividad.getBoolean("estado"));
                String titulo, String descripcion, int nivelDificultad, int duracionMin, boolean obligatorio,

                Actividad actividad = new Actividad(String titulo, String descripcion, int nivelDificultad, int duracionMin, boolean obligatorio,int tiempoCompletarSugerido, String tipo)

                // Obtener el ID de la actividad como clave para el HashMap
                String id = jsonActividad.getString("id");

                actividades.put(id, actividad);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return actividades;
    }

    // Guardar Actividades en el archivo JSON
    public static void guardarActividades(HashMap<String, Actividad> actividades) {
        JSONArray jsonArray = new JSONArray();

        // Iterar sobre el mapa de actividades y crear JSONObjects para cada una
        for (String id : actividades.keySet()) {
            Actividad actividad = actividades.get(id);

            JSONObject jsonActividad = new JSONObject();
            jsonActividad.put("nombre", actividad.getNombre());
            jsonActividad.put("descripcion", actividad.getDescripcion());
            jsonActividad.put("estado", actividad.getEstado());
            // Agregar otros atributos necesarios según tu clase Actividad

            jsonActividad.put("id", id);

            jsonArray.put(jsonActividad);
        }

        try (Writer writer = new FileWriter(ARCHIVO_ACTIVIDADES)) {
            writer.write(jsonArray.toString(4)); // 4 espacios de indentación para formatear el JSON
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
