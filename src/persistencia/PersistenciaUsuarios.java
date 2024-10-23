package persistencia;

import modelo.Usuario;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.nio.file.Files;
import java.nio.file.Paths;

public class PersistenciaUsuarios {
    private static final String ARCHIVO_USUARIOS = "C:\\Users\\manue\\git\\DPO-P1-LearningPaths\\datos\\usuarios.json";

    // Cargar usuarios desde el archivo JSON
    public static HashMap<List<String>, Usuario> cargarUsuarios() {
        HashMap<List<String>, Usuario> usuarios = new HashMap<>();

        try {
            // Leer contenido del archivo JSON
            String content = new String(Files.readAllBytes(Paths.get(ARCHIVO_USUARIOS)));
            JSONArray jsonArray = new JSONArray(content);

            // Iterar sobre los usuarios en el archivo JSON
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonUsuario = jsonArray.getJSONObject(i);

                // Crear el objeto Usuario
                Usuario usuario = new Usuario();
                usuario.setNombre(jsonUsuario.getString("nombre"));
                usuario.setEdad(jsonUsuario.getInt("edad"));
                // Agregar otros atributos necesarios según tu clase Usuario

                // Crear la clave (lista de strings) para el HashMap
                JSONArray jsonClave = jsonUsuario.getJSONArray("clave");
                List<String> clave = jsonClave.toList(); // Esto puede necesitar ajuste según cómo manejas las claves

                usuarios.put(clave, usuario);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return usuarios;
    }

    // Guardar usuarios en el archivo JSON
    public static void guardarUsuarios(HashMap<List<String>, Usuario> usuarios) {
        JSONArray jsonArray = new JSONArray();

        // Iterar sobre el mapa de usuarios y crear JSONObjects para cada uno
        for (List<String> clave : usuarios.keySet()) {
            Usuario usuario = usuarios.get(clave);

            JSONObject jsonUsuario = new JSONObject();
            jsonUsuario.put("nombre", usuario.getNombre());
            jsonUsuario.put("edad", usuario.getEdad());
            // Agregar otros atributos necesarios según tu clase Usuario

            // Convertir la clave a JSONArray y agregar al objeto
            JSONArray jsonClave = new JSONArray(clave);
            jsonUsuario.put("clave", jsonClave);

            jsonArray.put(jsonUsuario);
        }

        try (Writer writer = new FileWriter(ARCHIVO_USUARIOS)) {
            writer.write(jsonArray.toString(4)); // 4 espacios de indentación para formatear el JSON
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

