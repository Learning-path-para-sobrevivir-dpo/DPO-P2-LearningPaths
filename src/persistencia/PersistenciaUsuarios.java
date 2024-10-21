package persistencia;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import modelo.Usuario;

import java.io.*;
import java.util.HashMap;
import java.util.List;

public class PersistenciaUsuarios {
    private static final String ARCHIVO_USUARIOS = "usuarios.json"; //TODO: definir esto

    // Cargar usuarios desde el archivo JSON
    public static HashMap<List<String>, Usuario> cargarUsuarios() {
        try (Reader reader = new FileReader(ARCHIVO_USUARIOS)) {
            Gson gson = new Gson();
            return gson.fromJson(reader, new TypeToken<HashMap<List<String>, Usuario>>(){}.getType());
        } catch (IOException e) {
            e.printStackTrace();
            return new HashMap<>(); // Retornar un mapa vacío si hay error
        }
    }

    // Guardar usuarios en el archivo JSON
    public static void guardarUsuarios(HashMap<List<String>, Usuario> usuarios) {
        try (Writer writer = new FileWriter(ARCHIVO_USUARIOS)) {
            Gson gson = new Gson();
            gson.toJson(usuarios, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
