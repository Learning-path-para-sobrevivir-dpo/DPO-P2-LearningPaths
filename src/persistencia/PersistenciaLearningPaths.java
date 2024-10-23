package persistencia;

import modelo.LearningPath;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import modelo.*;

public class PersistenciaLearningPaths {
    private static final String ARCHIVO_LEARNING_PATHS = "C:\\Users\\manue\\git\\DPO-P1-LearningPaths\\datos\\learningPaths.json";

    // Cargar Learning Paths desde el archivo JSON
    public static HashMap<String, LearningPath> cargarLearningPaths() {
        HashMap<String, LearningPath> learningPaths = new HashMap<>();

        try {
            // Leer contenido del archivo JSON
            String content = new String(Files.readAllBytes(Paths.get(ARCHIVO_LEARNING_PATHS)));
            JSONArray jsonArray = new JSONArray(content);

            // Iterar sobre los learning paths en el archivo JSON
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonLearningPath = jsonArray.getJSONObject(i);

                // Crear el objeto LearningPath
                
                String titulo = (jsonLearningPath.getString("titulo"));
                String descripcion = (jsonLearningPath.getString("descripcion"));
                String objetivo = (jsonLearningPath.getString("objetivo"));
                int nivelDificultad = (jsonLearningPath.getInt("nivelDificultad"));
                int rating = (jsonLearningPath.getInt("rating"));
                String fechaDeCreacion = (jsonLearningPath.getString("fechaDeCreacion"));
                String fechaModificacion = (jsonLearningPath.getString("fechaModificacion"));
                int version = (jsonLearningPath.getInt("version"));
                String autor = (jsonLearningPath.getString("autor"));
                LearningPath learningPath = new LearningPath(titulo, descripcion,objetivo,nivelDificultad,rating,fechaDeCreacion,fechaModificacion,version,autor);
                
                learningPaths.put(titulo, learningPath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return learningPaths;
    }

    // Guardar Learning Paths en el archivo JSON
    public static void guardarLearningPaths(HashMap<String, LearningPath> learningPaths) {
        JSONArray jsonArray = new JSONArray();

        // Iterar sobre el mapa de learning paths y crear JSONObjects para cada uno
        for (String id : learningPaths.keySet()) {
            LearningPath learningPath = learningPaths.get(id);

            JSONObject jsonLearningPath = new JSONObject();
            jsonLearningPath.put("nombre", learningPath.getNombre());
            jsonLearningPath.put("descripcion", learningPath.getDescripcion());
            // Agregar otros atributos según tu clase LearningPath

            jsonLearningPath.put("id", id);

            jsonArray.put(jsonLearningPath);
        }

        try (Writer writer = new FileWriter(ARCHIVO_LEARNING_PATHS)) {
            writer.write(jsonArray.toString(4)); // 4 espacios de indentación para formatear el JSON
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
