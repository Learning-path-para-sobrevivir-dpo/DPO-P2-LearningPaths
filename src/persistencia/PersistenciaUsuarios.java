package persistencia;

import modelo.Usuario;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONObject;

public class PersistenciaUsuarios {

    public void salvarUsuarios(String archivo, ManejoDatos datos) {
        JSONArray usuariosArray = new JSONArray();

        for (Usuario usuario : datos.getUsuarios().values()) {
            JSONObject usuarioJson = new JSONObject();
            usuarioJson.put("login", usuario.getLogin());
            usuarioJson.put("correo", usuario.getCorreo());
            usuarioJson.put("contraseña", usuario.getContraseña());
            usuarioJson.put("tipo", usuario.getTipo());
            // TODO: completar atributos
            usuariosArray.put(usuarioJson);
        }

        try (FileWriter fileWriter = new FileWriter(new File(archivo))) {
            fileWriter.write(usuariosArray.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cargarUsuarios(String archivo, ManejoDatos datos) {
        File file = new File(archivo);
        if (!file.exists()) {
            return; 
        }

        try (FileReader fileReader = new FileReader(file)) {
            StringBuilder content = new StringBuilder();
            int character;
            while ((character = fileReader.read()) != -1) {
                content.append((char) character);
            }

            JSONArray usuariosArray = new JSONArray(content.toString());
            for (int i = 0; i < usuariosArray.length(); i++) {
                JSONObject usuarioJson = usuariosArray.getJSONObject(i);
                String login = usuarioJson.getString("login");
                String correo = usuarioJson.getString("correo");
                String contraseña = usuarioJson.getString("contraseña");
                String tipo = usuarioJson.getString("tipo");
                Usuario usuario = new Usuario(login, correo, contraseña, tipo); 
                datos.addUsuario(usuario);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
