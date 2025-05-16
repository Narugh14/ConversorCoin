package org.montelongo;

import com.google.gson.*;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    // Monedas disponibles para conversión en el orden correcto
    private static final List<Moneda> MONEDAS = Arrays.asList(
            new Moneda("USD", "Dólar Estadounidense"),
            new Moneda("ARS", "Peso Argentino"),
            new Moneda("BOB", "Boliviano Boliviano"),
            new Moneda("BRL", "Real Brasileño"),
            new Moneda("CLP", "Peso Chileno"),
            new Moneda("COP", "Peso Colombiano")
    );

    public static void main(String[] args) {
        mostrarBienvenida();

        while (true) {
            try {
                mostrarMenuPrincipal();
                int opcion = leerOpcion();

                if (opcion == 7) {
                    System.out.println("\nGracias por usar el Conversor de Monedas. ¡Hasta pronto!");
                    break;
                }

                if (opcion < 1 || opcion > 6) {
                    System.out.println("\nOpción no válida. Por favor, elija una opción del 1 al 7.");
                    continue;
                }

                Moneda monedaBase = MONEDAS.get(opcion - 1);
                realizarConversion(monedaBase);

            } catch (Exception e) {
                System.out.println("\nOcurrió un error: " + e.getMessage());
                System.out.println("Por favor, intente nuevamente.");
            }
        }

        scanner.close();
    }

    private static void mostrarBienvenida() {
        System.out.println("******************************************");
        System.out.println("*  Sea bienvenido/a al Conversor de Moneda *");
        System.out.println("******************************************");
        System.out.println("\nEste programa permite convertir entre las siguientes monedas:");
    }

    private static void mostrarMenuPrincipal() {
        System.out.println("\n=== MENÚ PRINCIPAL ===");
        for (int i = 0; i < MONEDAS.size(); i++) {
            System.out.printf("%d. %s (%s)%n", i + 1, MONEDAS.get(i).getNombre(), MONEDAS.get(i).getCodigo());
        }
        System.out.println("7. Salir");
        System.out.print("\nElija una opción válida (1-7): ");
    }

    private static int leerOpcion() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("Entrada no válida. Por favor ingrese un número (1-7): ");
            }
        }
    }

    private static void realizarConversion(Moneda monedaBase) {
        System.out.println("\n=== CONVERSIÓN DESDE " + monedaBase.getNombre() + " ===");

        JsonObject tasas = obtenerTasasDeCambio(monedaBase.getCodigo());
        if (tasas == null) {
            System.out.println("No se pudieron obtener las tasas de cambio. Intente nuevamente.");
            return;
        }

        while (true) {
            try {
                mostrarMenuConversion(monedaBase);
                int opcion = leerOpcionConversion();

                if (opcion == 7) {
                    System.out.println("Volviendo al menú principal...");
                    break;
                }

                if (opcion < 1 || opcion > 6) {
                    System.out.println("\nOpción no válida. Por favor, elija una opción del 1 al 7.");
                    continue;
                }

                Moneda monedaObjetivo = MONEDAS.get(opcion - 1);

                if (monedaBase.getCodigo().equals(monedaObjetivo.getCodigo())) {
                    System.out.println("\nNo puede convertir a la misma moneda. Elija otra opción.");
                    continue;
                }

                double cantidad = obtenerCantidad();
                double tasa = tasas.get(monedaObjetivo.getCodigo()).getAsDouble();
                double resultado = cantidad * tasa;

                mostrarResultado(cantidad, monedaBase, resultado, monedaObjetivo, tasa);

                System.out.print("\n¿Desea realizar otra conversión con la misma moneda base? (s/n): ");
                String continuar = scanner.nextLine();
                if (!continuar.equalsIgnoreCase("s")) {
                    break;
                }

            } catch (Exception e) {
                System.out.println("\nError: " + e.getMessage());
                System.out.println("Por favor, intente nuevamente.");
            }
        }
    }

    private static void mostrarMenuConversion(Moneda monedaBase) {
        System.out.println("\nSeleccione la moneda a convertir desde " + monedaBase.getNombre() + ":");
        for (int i = 0; i < MONEDAS.size(); i++) {
            System.out.printf("%d. %s (%s)%n", i + 1, MONEDAS.get(i).getNombre(), MONEDAS.get(i).getCodigo());
        }
        System.out.println("7. Volver al menú principal");
        System.out.print("\nElija una opción (1-7): ");
    }

    private static int leerOpcionConversion() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("Entrada no válida. Por favor ingrese un número (1-7): ");
            }
        }
    }

    private static double obtenerCantidad() {
        while (true) {
            System.out.print("\nIngrese la cantidad a convertir (debe ser mayor a 0): ");
            try {
                double cantidad = Double.parseDouble(scanner.nextLine());
                if (cantidad > 0) {
                    return cantidad;
                }
                System.out.println("La cantidad debe ser mayor a 0. Intente nuevamente.");
            } catch (NumberFormatException e) {
                System.out.println("Entrada no válida. Por favor ingrese un número.");
            }
        }
    }

    private static JsonObject obtenerTasasDeCambio(String monedaBase) {
        try {
            String apiUrl = "https://v6.exchangerate-api.com/v6/f2d5af2d3c0f8a1f91488c06/latest/" + monedaBase;

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(apiUrl))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            JsonObject jsonObject = JsonParser.parseString(response.body()).getAsJsonObject();

            if (jsonObject.get("result").getAsString().equals("success")) {
                return jsonObject.getAsJsonObject("conversion_rates");
            }

            System.out.println("Error al obtener tasas de cambio desde la API.");
            return null;

        } catch (Exception e) {
            System.out.println("Error de conexión: " + e.getMessage());
            return null;
        }
    }

    private static void mostrarResultado(double cantidad, Moneda monedaBase,
                                         double resultado, Moneda monedaObjetivo, double tasa) {
        System.out.println("\n=== RESULTADO DE LA CONVERSIÓN ===");
        System.out.printf("%.2f %s = %.2f %s%n", cantidad, monedaBase.getCodigo(), resultado, monedaObjetivo.getCodigo());
        System.out.printf("Tasa de cambio: 1 %s = %.4f %s%n",
                monedaBase.getCodigo(), tasa, monedaObjetivo.getCodigo());
        System.out.println("----------------------------------");
    }

    // Clase auxiliar para representar una moneda
    static class Moneda {
        private final String codigo;
        private final String nombre;

        public Moneda(String codigo, String nombre) {
            this.codigo = codigo;
            this.nombre = nombre;
        }

        public String getCodigo() {
            return codigo;
        }

        public String getNombre() {
            return nombre;
        }
    }
}