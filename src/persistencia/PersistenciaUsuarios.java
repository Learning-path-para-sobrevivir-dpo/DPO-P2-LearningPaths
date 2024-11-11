package persistencia;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import modelo.*;
import modelo.actividades.Actividad;

public class PersistenciaUsuarios {

    private static final String ARCHIVO_USUARIOS = "datos/usuarios.json";

    public static HashMap<List<String>, Usuario> cargarUsuarios(HashMap<List<String>, Progreso> mapaProgresos,
                                                      HashMap<String, LearningPath> mapaLearningPaths,
                                                      HashMap<String, Review> mapaReviews,
                                                      HashMap<String, Actividad> mapaActividades) {
        HashMap<List<String>, Usuario> usuarios = new HashMap<>();

        try {
            // Leer el archivo JSON
        	String content = new String(Files.readAllBytes(Paths.get(ARCHIVO_USUARIOS)));
        	if (!content.isBlank())
        	{
        		JSONArray jsonUsuarios = new JSONArray(content);

        		// Iterar sobre el JSONArray y convertir cada objeto JSON a Usuario
        		for (int i = 0; i < jsonUsuarios.length(); i++) {
        			JSONObject jsonUsuario = jsonUsuarios.getJSONObject(i);

        			String tipo = jsonUsuario.getString("tipo");

        			Usuario usuario;
        			if ("estudiante".equals(tipo)) {
        				usuario = cargarEstudiante(jsonUsuario, mapaProgresos, mapaLearningPaths);
        			} else if ("profesor".equals(tipo) ) {
        				usuario = cargarProfesor(jsonUsuario, mapaLearningPaths, mapaActividades);
        			} else {
        				throw new IllegalArgumentException("Tipo de usuario desconocido: " + tipo);
        			}

        			usuarios.put(List.of(usuario.getLogin(),usuario.getContraseña()), usuario);
        		}
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return usuarios;
    }

    private static Estudiante cargarEstudiante(JSONObject jsonUsuario,
                                               HashMap<List<String>, Progreso> mapaProgresos,
                                               HashMap<String, LearningPath> mapaLearningPaths) throws JSONException {
        String login = jsonUsuario.getString("login");
        String correo = jsonUsuario.getString("correo");
        String contraseña = jsonUsuario.getString("contraseña");

        Estudiante estudiante = new Estudiante(login, correo, contraseña, "Estudiante");

        // Cargar progresos de Learning Paths
        JSONArray jsonProgresos = jsonUsuario.getJSONArray("progresosLearningPaths");
        for (int j = 0; j < jsonProgresos.length(); j++) {
            JSONArray llaveProgreso = jsonProgresos.getJSONArray(j);
            String learningPath = llaveProgreso.getString(0);
            String estudianteLogin = llaveProgreso.getString(1);
            Progreso progreso = mapaProgresos.get(List.of(learningPath, estudianteLogin));
            if (progreso != null) {
                estudiante.getProgresosLearningPaths().put(learningPath, progreso);
            }
        }

        // Cargar Learning Paths inscritos
        JSONArray jsonLearningPaths = jsonUsuario.getJSONArray("learningPaths");
        for (int j = 0; j < jsonLearningPaths.length(); j++) {
            String titulo = jsonLearningPaths.getString(j);
            LearningPath lp = mapaLearningPaths.get(titulo);
            if (lp != null) {
                estudiante.getLearningPaths().put(titulo, lp);
            }
        }

        return estudiante;
    }

    private static Profesor cargarProfesor(JSONObject jsonUsuario,
                                           Map<String, LearningPath> mapaLearningPaths,
                                           Map<String, Actividad> mapaActividades) throws JSONException {
        String login = jsonUsuario.getString("login");
        String correo = jsonUsuario.getString("correo");
        String contraseña = jsonUsuario.getString("contraseña");

        Profesor profesor = new Profesor(login, correo, contraseña, "Profesor");

        // Cargar Learning Paths creados por el profesor
        JSONArray jsonLearningPathsCreados = jsonUsuario.getJSONArray("learningPathsCreados");
        for (int j = 0; j < jsonLearningPathsCreados.length(); j++) {
            String titulo = jsonLearningPathsCreados.getString(j);
            LearningPath lp = mapaLearningPaths.get(titulo);
            if (lp != null) {
                profesor.getLearningPathsCreados().put(titulo, lp);
            }
        }

        // Cargar Actividades creadas por el profesor
        JSONArray jsonActividadesCreadas = jsonUsuario.getJSONArray("actividadesCreadas");
        for (int j = 0; j < jsonActividadesCreadas.length(); j++) {
            String idActividad = jsonActividadesCreadas.getString(j);
            Actividad actividad = mapaActividades.get(idActividad);
            if (actividad != null) {
                profesor.getActCreadas().add(actividad);
            }
        }

        return profesor;
    }
private static void guardarEstudiante(Estudiante estudiante, JSONObject jsonUsuario) throws JSONException {
        // Guardar progresos de Learning Paths
        JSONArray jsonProgresos = new JSONArray();
        for (Progreso progreso : estudiante.getProgresosLearningPaths().values()) {
            JSONArray llaveProgreso = new JSONArray();
            llaveProgreso.put(progreso.getLearningPath());
            llaveProgreso.put(progreso.getEstudiante());
            jsonProgresos.put(llaveProgreso);
        }
        jsonUsuario.put("progresosLearningPaths", jsonProgresos);

        // Guardar Learning Paths inscritos
        JSONArray jsonLearningPaths = new JSONArray();
        for (LearningPath lp : estudiante.getLearningPaths().values()) {
            jsonLearningPaths.put(lp.getTitulo());
        }
        jsonUsuario.put("learningPaths", jsonLearningPaths);
    }

    private static void guardarProfesor(Profesor profesor, JSONObject jsonUsuario) throws JSONException {
        // Guardar Learning Paths creados por el profesor
        JSONArray jsonLearningPathsCreados = new JSONArray();
        for (LearningPath lp : profesor.getLearningPathsCreados().values()) {
            jsonLearningPathsCreados.put(lp.getTitulo());
        }
        jsonUsuario.put("learningPathsCreados", jsonLearningPathsCreados);

        // Guardar Actividades creadas por el profesor
        JSONArray jsonActividadesCreadas = new JSONArray();
        for (Actividad actividad : profesor.getActCreadas()) {
            jsonActividadesCreadas.put(actividad.getId());
        }
        jsonUsuario.put("actividadesCreadas", jsonActividadesCreadas);
    }
public static void guardarUsuarios(HashMap<List<String>, Usuario> usuarios) {
        JSONArray jsonUsuarios = new JSONArray();

        // Iterar sobre el mapa de usuarios y convertir cada usuario a JSON
        for (Usuario usuario : usuarios.values()) {
            JSONObject jsonUsuario = new JSONObject();
            jsonUsuario.put("login", usuario.getLogin());
            jsonUsuario.put("correo", usuario.getCorreo());
            jsonUsuario.put("contraseña", usuario.getContraseña());
            jsonUsuario.put("tipo", usuario.getTipo());

            // Guardar información específica de Estudiante o Profesor
            if (usuario instanceof Estudiante) {
                Estudiante estudiante = (Estudiante) usuario;
                jsonUsuario.put("tipoEspecifico", "Estudiante");
                guardarEstudiante(estudiante, jsonUsuario);
            } else if (usuario instanceof Profesor) {
                Profesor profesor = (Profesor) usuario;
                jsonUsuario.put("tipoEspecifico", "Profesor");
                guardarProfesor(profesor, jsonUsuario);
            }

            jsonUsuarios.put(jsonUsuario);
        }

        // Escribir el JSONArray en el archivo JSON
        try (FileWriter file = new FileWriter(ARCHIVO_USUARIOS)) {
            file.write(jsonUsuarios.toString(4));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}