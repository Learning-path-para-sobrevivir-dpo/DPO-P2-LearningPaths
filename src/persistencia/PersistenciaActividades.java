package persistencia;

import org.json.JSONArray;
import org.json.JSONObject;

import modelo.Actividad;
import modelo.Review;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

public class PersistenciaActividades {

    private static final String ARCHIVO_ACTIVIDADES = "C:\\\\Users\\\\manue\\\\git\\\\DPO-P1-LearningPaths\\\\datos\\\\actividades.json";

    // Cargar actividades desde el archivo JSON en un HashMap
    public static HashMap<String, Actividad> cargarActividades(HashMap<String, Review> reviewsMap) {
        HashMap<String, Actividad> actividades = new HashMap<>();
        try {
            // Leer todo el contenido del archivo JSON
            String content = new String(Files.readAllBytes(Paths.get(ARCHIVO_ACTIVIDADES)));

            // Convertir el contenido en un JSONArray
            JSONArray jsonActividades = new JSONArray(content);

            // Iterar sobre cada objeto en el JSONArray
            for (int i = 0; i < jsonActividades.length(); i++) {
                JSONObject jsonActividad = jsonActividades.getJSONObject(i);

                String titulo = jsonActividad.getString("titulo");
                String descripcion = jsonActividad.getString("descripcion");
                int nivelDificultad = jsonActividad.getInt("nivelDificultad");
                int duracionMin = jsonActividad.getInt("duracionMin");
                boolean obligatorio = jsonActividad.getBoolean("obligatorio");
                int tiempoCompletarSugerido = jsonActividad.getInt("tiempoCompletarSugerido");
                String tipoActividad = jsonActividad.getString("tipoActividad");
                String id = jsonActividad.getString("id");

                // Crear una instancia de Actividad
                Actividad actividad = new Actividad(titulo, descripcion, nivelDificultad, duracionMin, obligatorio,
                        tiempoCompletarSugerido, tipoActividad);

                // Leer las reviews de la actividad
                JSONArray jsonReviews = jsonActividad.getJSONArray("reviews");
                List<Review> listaReviews = new ArrayList<>();
                for (int j = 0; j < jsonReviews.length(); j++) {
                    String contenidoReview = jsonReviews.getString(j);
                    // Buscar la review en el HashMap de reviews usando el contenido como clave
                    Review review = reviewsMap.get(contenidoReview);
                    if (review != null) {
                        listaReviews.add(review);
                    }
                }
                actividad.setReviews(listaReviews);

                // Agregar la actividad al HashMap usando el id como llave
                actividades.put(id, actividad);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return actividades;  // Devolver el mapa de actividades
    }

    // Guardar actividades en el archivo JSON desde un HashMap
    public static void guardarActividades(HashMap<String, Actividad> actividades) {
        try {
            // Crear un JSONArray para almacenar las actividades
            JSONArray jsonActividades = new JSONArray();

            // Convertir cada actividad en un JSONObject
            for (Actividad actividad : actividades.values()) {
                JSONObject jsonActividad = new JSONObject();
                jsonActividad.put("titulo", actividad.getTitulo());
                jsonActividad.put("descripcion", actividad.getDescripcion());
                jsonActividad.put("nivelDificultad", actividad.getNivelDificultad());
                jsonActividad.put("duracionMin", actividad.getDuracionMin());
                jsonActividad.put("obligatorio", actividad.isObligatorio());
                jsonActividad.put("tiempoCompletarSugerido", actividad.getTiempoCompletarSugerido());
                jsonActividad.put("tipoActividad", actividad.getTipoActividad());
                jsonActividad.put("id", actividad.getId());

                // Guardar solo el contenido de las reviews en el JSON
                JSONArray jsonReviews = new JSONArray();
                for (Review review : actividad.getReviews()) {
                    jsonReviews.put(review.getContenido());
                }
                jsonActividad.put("reviews", jsonReviews);

                // Agregar el JSONObject al JSONArray
                jsonActividades.put(jsonActividad);
            }

            // Escribir el JSONArray al archivo
            try (FileWriter file = new FileWriter(ARCHIVO_ACTIVIDADES)) {
                file.write(jsonActividades.toString());
                file.flush();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
