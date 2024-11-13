package api;
// Archivo: api/Main.java

import com.google.gson.JsonObject;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ApiService apiService = new ApiService();

        // Mostrar bienvenida
        System.out.println("**************************************************");
        System.out.println("Bienvenido al Conversor de Monedas");
        System.out.println("**************************************************");

        boolean continuar = true;

        while (continuar) {
            // Mostrar menú con monedas y sus códigos
            System.out.println("\nElija una opción para convertir monedas:");
            System.out.println("1. Dólar a Peso Mexicano (MXN)");
            System.out.println("2. Peso Mexicano a Dólar (MXN -> USD)");
            System.out.println("3. Peso Colombiano a Dólar (COP -> USD)");
            System.out.println("4. Dólar a Peso Colombiano (USD -> COP)");
            System.out.println("5. Peso Chileno a Dólar (CLP -> USD)");
            System.out.println("6. Dólar a Peso Chileno (USD -> CLP)");
            System.out.println("7. Peso Argentino a Dólar (ARS -> USD)");
            System.out.println("8. Dólar a Peso Argentino (USD -> ARS)");
            System.out.println("9. Salir");

            System.out.print("Ingrese su opción: ");
            int opcion = scanner.nextInt();

            // Leer tasas de cambio
            JsonObject tasasDeCambio = apiService.obtenerTasasDeCambio("USD");

            if (tasasDeCambio != null) {
                // Verificar si la respuesta contiene el campo "result" con el valor "success"
                JsonElement resultElement = tasasDeCambio.get("result");
                if (resultElement == null || !resultElement.getAsString().equals("success")) {
                    System.out.println("Error: No se pudo obtener las tasas de cambio. Intenta nuevamente más tarde.");
                    break;
                }

                // Obtener las tasas de conversión si la respuesta fue exitosa
                JsonObject conversionRates = tasasDeCambio.getAsJsonObject("conversion_rates");

                if (conversionRates != null) {
                    // Filtrar monedas relevantes: COP, CLP, ARS
                    JsonElement pesoColombiano = conversionRates.get("COP");
                    JsonElement pesoChileno = conversionRates.get("CLP");
                    JsonElement pesoArgentino = conversionRates.get("ARS");
                    JsonElement dolar = conversionRates.get("USD");

                    switch (opcion) {
                        case 1:
                            if (conversionRates.has("MXN")) {
                                double tasaMXN = conversionRates.get("MXN").getAsDouble();
                                System.out.print("Ingrese la cantidad en USD: ");
                                double cantidad1 = scanner.nextDouble();
                                System.out.println(cantidad1 + " USD = " + cantidad1 * tasaMXN + " MXN");
                            } else {
                                System.out.println("Error: No se pudo obtener la tasa de cambio para MXN.");
                            }
                            break;
                        case 2:
                            if (conversionRates.has("MXN")) {
                                double tasaUSDfromMXN = 1 / conversionRates.get("MXN").getAsDouble();
                                System.out.print("Ingrese la cantidad en MXN: ");
                                double cantidad2 = scanner.nextDouble();
                                System.out.println(cantidad2 + " MXN = " + cantidad2 * tasaUSDfromMXN + " USD");
                            } else {
                                System.out.println("Error: No se pudo obtener la tasa de cambio para MXN.");
                            }
                            break;
                        case 3:
                            if (pesoColombiano != null) {
                                System.out.print("Ingrese la cantidad en USD: ");
                                double cantidad3 = scanner.nextDouble();
                                System.out.println(cantidad3 + " USD = " + cantidad3 * pesoColombiano.getAsDouble() + " COP");
                            } else {
                                System.out.println("Error: No se pudo obtener la tasa de cambio para COP.");
                            }
                            break;
                        case 4:
                            if (pesoColombiano != null) {
                                System.out.print("Ingrese la cantidad en COP: ");
                                double cantidad4 = scanner.nextDouble();
                                System.out.println(cantidad4 + " COP = " + cantidad4 * (1 / pesoColombiano.getAsDouble()) + " USD");
                            } else {
                                System.out.println("Error: No se pudo obtener la tasa de cambio para COP.");
                            }
                            break;
                        case 5:
                            if (pesoChileno != null) {
                                System.out.print("Ingrese la cantidad en USD: ");
                                double cantidad5 = scanner.nextDouble();
                                System.out.println(cantidad5 + " USD = " + cantidad5 * pesoChileno.getAsDouble() + " CLP");
                            } else {
                                System.out.println("Error: No se pudo obtener la tasa de cambio para CLP.");
                            }
                            break;
                        case 6:
                            if (pesoChileno != null) {
                                System.out.print("Ingrese la cantidad en CLP: ");
                                double cantidad6 = scanner.nextDouble();
                                System.out.println(cantidad6 + " CLP = " + cantidad6 * (1 / pesoChileno.getAsDouble()) + " USD");
                            } else {
                                System.out.println("Error: No se pudo obtener la tasa de cambio para CLP.");
                            }
                            break;
                        case 7:
                            if (pesoArgentino != null) {
                                System.out.print("Ingrese la cantidad en USD: ");
                                double cantidad7 = scanner.nextDouble();
                                System.out.println(cantidad7 + " USD = " + cantidad7 * pesoArgentino.getAsDouble() + " ARS");
                            } else {
                                System.out.println("Error: No se pudo obtener la tasa de cambio para ARS.");
                            }
                            break;
                        case 8:
                            if (pesoArgentino != null) {
                                System.out.print("Ingrese la cantidad en ARS: ");
                                double cantidad8 = scanner.nextDouble();
                                System.out.println(cantidad8 + " ARS = " + cantidad8 * (1 / pesoArgentino.getAsDouble()) + " USD");
                            } else {
                                System.out.println("Error: No se pudo obtener la tasa de cambio para ARS.");
                            }
                            break;
                        case 9:
                            System.out.println("Gracias por usar el Conversor de Monedas.");
                            continuar = false;
                            break;
                        default:
                            System.out.println("Opción no válida. Intente nuevamente.");
                    }
                } else {
                    System.out.println("Error: No se pudo obtener las tasas de conversión.");
                }
            } else {
                System.out.println("Error al obtener las tasas de cambio.");
            }

            // Preguntar si desea continuar
            if (continuar) {
                System.out.print("\n¿Desea continuar? (S/N): ");
                char respuesta = scanner.next().charAt(0);
                if (respuesta == 'N' || respuesta == 'n') {
                    continuar = false;
                    System.out.println("**************************************************");
                    System.out.println("Gracias por usar el Conversor de Monedas.");
                    System.out.println("**************************************************");

                }
            }
        }

        scanner.close();
    }
}