package persistencia;

import modelo.IActividad;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONObject;

public class PersistenciaActividades {

    public void salvarActividades(String archivo, ManejoDatos datos) {
        JSONArray actividadesArray = new JSONArray();
        
        for (IActividad actividad : datos.getActividades().values()) {
            JSONObject actividadJson = new JSONObject();
            actividadJson.put("nombre", actividad.getNombre());
            actividadesArray.put(actividadJson);
        }

        try (FileWriter fileWriter = new FileWriter(new File(archivo))) {
            fileWriter.write(actividadesArray.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cargarActividades(String archivo, ManejoDatos datos) {
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

            JSONArray actividadesArray = new JSONArray(content.toString());
            for (int i = 0; i < actividadesArray.length(); i++) {
                JSONObject actividadJson = actividadesArray.getJSONObject(i);
                String nombre = actividadJson.getString("nombre");
                // TODO: completar esto dependiendo de como quede actividades
                //IActividad actividad = new Actividad(nombre, ...); 
                datos.addActividad(actividad); 
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

