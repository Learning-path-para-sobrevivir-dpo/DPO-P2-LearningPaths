package persistencia;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.util.HashMap;


import modelo.LearningPath;

public class PersistenciaLearningPaths {

    private static final String ARCHIVO_LEARNING_PATHS = "learningPaths.json";
    
    // Cargar Learning Paths desde el archivo JSON
    public static HashMap<String, LearningPath> cargarLearningPaths() {
        try (Reader reader = new FileReader(ARCHIVO_LEARNING_PATHS)) {
            Gson gson = new Gson();
            return gson.fromJson(reader, new TypeToken<HashMap<String, LearningPath>>(){}.getType());
        } catch (IOException e) {
            e.printStackTrace();
            return new HashMap<>(); // Retornar un mapa vacío si hay error
        }
    }

    // Guardar Learning Paths en el archivo JSON
    public static void guardarLearningPaths(HashMap<String, LearningPath> learningPaths) {
        try (Writer writer = new FileWriter(ARCHIVO_LEARNING_PATHS)) {
            Gson gson = new Gson();
            gson.toJson(learningPaths, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}