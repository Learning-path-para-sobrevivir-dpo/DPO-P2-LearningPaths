package persistencia;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

import modelo.*;
import modelo.actividades.Pregunta;
import modelo.actividades.PreguntaAbierta;
import modelo.actividades.PreguntaMultiple;
import modelo.actividades.PreguntaVerdaderoFalso;

public class PersistenciaPreguntas {

    private static final String ARCHIVO_PREGUNTAS = "datos/preguntas.json";

    
 // Cargar preguntas desde el archivo JSON
    public static HashMap<String, Pregunta> cargarPreguntas() {
        HashMap<String, Pregunta> preguntas = new HashMap<>();

        // Leer y convertir el JSON desde el archivo
        try {
        	String content = new String(Files.readAllBytes(Paths.get(ARCHIVO_PREGUNTAS)));
        	if (!content.isBlank())
        	{
        		JSONArray jsonPreguntas = new JSONArray(content);

        		for (int i = 0; i < jsonPreguntas.length(); i++) {
        			JSONObject jsonPregunta = jsonPreguntas.getJSONObject(i);
        			String enunciado = jsonPregunta.getString("enunciado");
        			String tipoPregunta = jsonPregunta.getString("tipoPregunta");

        			Pregunta pregunta;
        			switch (tipoPregunta) {
        			case "PreguntaAbierta":
        				pregunta = new PreguntaAbierta(enunciado);
        				((PreguntaAbierta) pregunta).setRespuesta(jsonPregunta.getString("respuesta"));
        				((PreguntaAbierta) pregunta).setCorrecta(jsonPregunta.getBoolean("correcta"));
        				break;
        			case "PreguntaMultiple":
        				JSONArray opcionesArray = jsonPregunta.getJSONArray("opciones");
        				List<String> opciones = new ArrayList<>();
        				for (int j = 0; j < opcionesArray.length(); j++) {
        					opciones.add(opcionesArray.getString(j));
        				}
        				int opcionCorrecta = jsonPregunta.getInt("opcionCorrecta");
        				PreguntaMultiple pm = new PreguntaMultiple(enunciado, opciones, opcionCorrecta);
        				pm.setOpcionSeleccionada(jsonPregunta.getInt("opcionSeleccionada"));
        				pregunta = pm;
        				break;
        			case "PreguntaVerdaderoFalso":
        				boolean respuestaCorrecta = jsonPregunta.getBoolean("respuestaCorrecta");
        				PreguntaVerdaderoFalso pvf = new PreguntaVerdaderoFalso(enunciado, respuestaCorrecta);
        				pvf.setOpcionSeleccionada(jsonPregunta.getBoolean("opcionSeleccionada"));
        				pregunta = pvf;
        				break;
        			case "Pregunta":
        				pregunta = new Pregunta(enunciado); 	
        			default:
        				throw new IllegalArgumentException("Tipo de pregunta desconocido: " + tipoPregunta);
        			}

        			// Agregar la pregunta al mapa usando el enunciado como clave
        			preguntas.put(enunciado, pregunta);
        		}
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return preguntas;
    }
    // Guardar preguntas en el archivo JSON
 
        public static void guardarPreguntas(HashMap<String, Pregunta> preguntas) {
            try {
                // Crear un JSONArray para almacenar las preguntas
            JSONArray jsonPreguntas = new JSONArray();

            // Iterar sobre el mapa de preguntas y convertir cada pregunta a JSON
            for (Pregunta pregunta : preguntas.values()) {
                JSONObject jsonPregunta = new JSONObject();
                jsonPregunta.put("enunciado", pregunta.getEnunciado());

                if (pregunta instanceof PreguntaAbierta) {
                    jsonPregunta.put("tipoPregunta", "PreguntaAbierta");
                    PreguntaAbierta pa = (PreguntaAbierta) pregunta;
                    jsonPregunta.put("respuesta", pa.getRespuesta());
                    jsonPregunta.put("correcta", pa.isCorrecta());

                } else if (pregunta instanceof PreguntaMultiple) {
                    jsonPregunta.put("tipoPregunta", "PreguntaMultiple");
                    PreguntaMultiple pm = (PreguntaMultiple) pregunta;
                    jsonPregunta.put("opciones", new JSONArray(pm.getOpciones()));
                    jsonPregunta.put("opcionCorrecta", pm.getOpcionCorrecta() + 1);
                    jsonPregunta.put("opcionSeleccionada", pm.getOpcionSeleccionada());
                } 
                else if (pregunta instanceof PreguntaVerdaderoFalso) {
                    jsonPregunta.put("tipoPregunta", "PreguntaVerdaderoFalso");
                    PreguntaVerdaderoFalso pvf = (PreguntaVerdaderoFalso) pregunta;
                    jsonPregunta.put("respuestaCorrecta", pvf.isRespuestaCorrecta());
                    jsonPregunta.put("opcionSeleccionada", pvf.isOpcionSeleccionada());
                } else {
                    jsonPregunta.put("tipoPregunta", "Pregunta");
                }
                jsonPreguntas.put(jsonPregunta);
            }

            // Escribir el JSONArray al archivo
            try (FileWriter file = new FileWriter(ARCHIVO_PREGUNTAS)) {
                file.write(jsonPreguntas.toString(4)); 
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

