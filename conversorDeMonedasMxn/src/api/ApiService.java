package api;
// Archivo: api/ApiService.java

// Importaciones necesarias
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.net.URI;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class ApiService {
    private static final String API_KEY = "054e77b1c95da58e4c19111f"; // Reemplaza con tu clave de API
    private static final String BASE_URL = "https://v6.exchangerate-api.com/v6/";

    // Método para obtener tasas de cambio de la API
    public JsonObject obtenerTasasDeCambio(String baseCurrency) {
        try {
            // Construir la URL completa con la clave API
            String urlString = BASE_URL + API_KEY + "/latest/" + baseCurrency;
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            // Verificar la respuesta de la conexión
            if (connection.getResponseCode() != 200) {
                System.out.println("Error: No se pudo conectar a la API. Código de respuesta: " + connection.getResponseCode());
                return null;
            }

            // Leer la respuesta JSON
            InputStreamReader reader = new InputStreamReader(connection.getInputStream());
            JsonParser parser = new JsonParser();
            JsonElement root = parser.parse(reader);
            JsonObject jsonObject = root.getAsJsonObject();

            // Verificar que la respuesta sea exitosa
            String result = jsonObject.get("result").getAsString();
            if (!result.equals("success")) {
                System.out.println("Error: La respuesta de la API no fue exitosa.");
                return null;
            }

            // Devolver el objeto JSON de las tasas de cambio
            return jsonObject;
        } catch (Exception e) {
            System.out.println("Error al obtener las tasas de cambio: " + e.getMessage());
            return null;
        }
    }
}