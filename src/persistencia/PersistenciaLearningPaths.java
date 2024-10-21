package persistencia;

import java.nio.file.Files;
import java.nio.file.Paths;

///import org.json.JSONObject;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import modelo.LearningPath;

public class PersistenciaLearningPaths {

    private static final String ARCHIVO_LEARNING_PATHS = "learningPaths.json";
    
    private Gson gson;

    public PersistenciaLearningPaths() {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    /**
     * Guarda los LearningPaths en el archivo JSON.
     * 
     * @param learningPaths: HashMap que contiene los LearningPaths con su título como llave.
     * @throws IOException Si ocurre un error al escribir en el archivo.
     */
    public void guardarLearningPaths(HashMap<String, LearningPath> learningPaths) throws IOException {
        try (FileWriter writer = new FileWriter(ARCHIVO_LEARNING_PATHS)) {
            gson.toJson(learningPaths, writer);
        }
    }

    /**
     * Carga los LearningPaths desde el archivo JSON.
     * 
     * @return Un HashMap con los LearningPaths cargados desde el archivo JSON.
     * @throws IOException Si ocurre un error al leer el archivo.
     */
    public HashMap<String, LearningPath> cargarLearningPaths() throws IOException {
        try (FileReader reader = new FileReader(ARCHIVO_LEARNING_PATHS)) {
            //TODO: hacer esto
    }
}
