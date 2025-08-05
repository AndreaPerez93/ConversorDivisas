package com.aluracursos.conversordivisas.Principal;

import com.aluracursos.conversordivisas.ConfigAPI.ConsultaAPI;
import com.aluracursos.conversordivisas.Modelos.Moneda;

import java.text.DecimalFormat;
import java.util.Map;
import java.util.Scanner;


    public class Menu {
        private final Scanner teclado = new Scanner(System.in);
        private final ConsultaAPI consultaAPI = new ConsultaAPI();

        public void menuEntrada() {
            int opcion = 0;
            while (opcion != 9) {
                var menu1 = """
                    ----------------------------------------------------
                    Bienvenido(a) al conversor de Divisas
                    Selecciona el tipo de moneda que quieres convertir:

                    1- Dólar --> Peso Mexicano
                    2- Peso Mexicano --> Peso Argentino
                    9 - Salir
                   ----------------------------------------------------
                    """;
                System.out.println(menu1);

                if (!teclado.hasNextInt()) {
                    System.out.println("Opción inválida, ingresa un número.");
                    teclado.nextLine(); // limpiar
                    continue;
                }

                opcion = teclado.nextInt();
                teclado.nextLine();

                switch (opcion) {
                    case 9:
                        System.out.println("Cerrando la aplicación...");
                        break;
                    case 1:
                        convertir("USD", "MXN");
                        break;
                    case 2:
                        convertir("MXN", "ARS");
                        break;
                    default:
                        System.out.println("Opción no reconocida. Intenta de nuevo.");
                        break;
                }
            }
        }

        private void convertir(String baseCode, String targetCode) {
            try {
                System.out.printf("Escribe el monto en %s a convertir a %s:%n", baseCode, targetCode);
                String cantidadUsuario = teclado.nextLine();
                double cantidad = Double.parseDouble(cantidadUsuario);

                Moneda monedaBase = consultaAPI.obtenerDatos(baseCode);
                if (monedaBase == null) {
                    System.out.println("No se pudo obtener la información de la API para la moneda base.");
                    return;
                }

                Map<String, String> tasas = monedaBase.conversion_rates();
                if (tasas == null || !tasas.containsKey(targetCode)) {
                    System.out.printf("No se encontró la tasa de conversión de %s a %s.%n", baseCode, targetCode);
                    return;
                }

                String tasaStr = tasas.get(targetCode);
                double tasaConversion = Double.parseDouble(tasaStr);
                double resultado = tasaConversion * cantidad;

                DecimalFormat formatoDecimal = new DecimalFormat("#.#####"); // hasta 5 decimales
                System.out.printf("El valor %.4f [%s] corresponde a %s [%s]%n",
                        cantidad, baseCode, formatoDecimal.format(resultado), targetCode);
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Asegúrate de escribir un número válido.");
            } catch (Exception e) {
                System.out.println("Ocurrió un error inesperado: " + e.getMessage());
            }
        }
    }
