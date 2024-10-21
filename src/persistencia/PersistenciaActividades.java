package persistencia;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import modelo.Actividad;

import java.io.*;
import java.util.HashMap;

public class PersistenciaActividades {
	private static final String ARCHIVO_ACTIVIDADES = "../../datos/actividades.json";

    // Cargar Actividades desde el archivo JSON
    public static HashMap<String, Actividad> cargarActividades() {
        try (Reader reader = new FileReader(ARCHIVO_ACTIVIDADES)) {
            Gson gson = new Gson();
            return gson.fromJson(reader, new TypeToken<HashMap<String, Actividad>>(){}.getType());
        } catch (IOException e) {
            e.printStackTrace();
            return new HashMap<>(); // Retornar un mapa vacío si hay error
        }
    }

    // Guardar Actividades en el archivo JSON
    public static void guardarActividades(HashMap<String, Actividad> actividades) {
        try (Writer writer = new FileWriter(ARCHIVO_ACTIVIDADES)) {
            Gson gson = new Gson();
            gson.toJson(actividades, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
