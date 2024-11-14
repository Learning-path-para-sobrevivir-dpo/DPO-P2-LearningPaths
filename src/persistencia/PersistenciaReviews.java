package persistencia;



import modelo.Review;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

public class PersistenciaReviews {

    private static final String ARCHIVO_REVIEWS = "datos/reviews.json";

    public static HashMap<String, Review> cargarReviews() {
        HashMap<String, Review> reviews = new HashMap<>();
        try {
            // Leer todo el contenido del archivo JSON
            String content = new String(Files.readAllBytes(Paths.get(ARCHIVO_REVIEWS)));
            
            if (!content.isBlank())
            {
            	// Convertir el contenido en un JSONArray
            	JSONArray jsonReviews = new JSONArray(content);

            	// Iterar sobre cada objeto en el JSONArray
            	for (int i = 0; i < jsonReviews.length(); i++) {
            		JSONObject jsonReview = jsonReviews.getJSONObject(i);

            		// Obtener los datos de la review
            		String fecha = jsonReview.getString("fecha");
            		String contenido = jsonReview.getString("contenido");
            		float rating = (float) jsonReview.getDouble("rating");
            		String tipo = jsonReview.getString("tipo");

            		// Crear una instancia de Review
            		Review review = new Review(fecha, contenido, tipo);
            		review.setRating(rating);  

            		// Guardar la review en el mapa usando 'contenido' como llave
            		reviews.put(contenido, review);
            	}
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return reviews; 
    }

    public static void guardarReviews(HashMap<String, Review> reviews) {
        try {
            // Crear un JSONArray para almacenar las reviews
            JSONArray jsonReviews = new JSONArray();

            // Convertir cada review en un JSONObject
            for (Review review : reviews.values()) {
                JSONObject jsonReview = new JSONObject();
                jsonReview.put("fecha", review.getFecha());
                jsonReview.put("contenido", review.getContenido());
                jsonReview.put("rating", review.getRating());
                jsonReview.put("tipo", review.getTipo());

                // Agregar el JSONObject al JSONArray
                jsonReviews.put(jsonReview);
            }

            // Escribir el JSONArray al archivo
            try (FileWriter file = new FileWriter(ARCHIVO_REVIEWS)) {
                file.write(jsonReviews.toString(4));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
