import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Primero, procesamos los argumentos
        if (args.length != 2) {
            System.err.println("Se deben proporcionar dos argumentos: la ruta del archivo CSV y el tipo de salida (JSON)");
            System.exit(1);
        }
        String csvFilePath = args[0];
        String outputType = args[1];

        if (!outputType.equalsIgnoreCase("json")) {
            System.err.println("El tipo de salida debe ser 'json'");
            System.exit(1);
        }

        // Creamos la lista de ofertas
        List<Oferta> ofertas = new ArrayList<>();

        // Leemos el archivo csv y creamos los objetos
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/" + csvFilePath))) {
            String line;
            br.readLine();  // Omitimos la primera línea de encabezado
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");

                // Controlamos que la línea tenga 9 campos
                if (fields.length != 9) {
                    System.err.println("Error: El archivo CSV no tiene el formato correcto");
                    System.exit(1);
                }
                Oferta oferta = null;

                // En caso de que implementemos otros tipos de descuento, debemos colocarlos aquí
                switch (fields[4]) {
                    case "%" -> oferta = new Oferta(
                            new Producto(fields[0],
                                    new Peso(
                                            Double.parseDouble(fields[1]),
                                            fields[2]),
                                    Double.parseDouble(fields[3])),
                            new DescuentoPorcentual(
                                    Double.parseDouble(fields[5]),
                                    Double.parseDouble(fields[6])),
                            fields[7],
                            fields[8]
                    );
                    default -> {
                        System.err.println("Tipo de descuento aun no definido");
                        System.exit(1);
                    }
                }
                ofertas.add(oferta);  // Agregamos la oferta a la lista
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo CSV: " + e.getMessage());
            System.exit(1);
        } catch (NumberFormatException e) {
            System.err.println("Error de formato en el archivo CSV: " + e.getMessage());
            System.exit(1);
        } catch (ParseException e) {
            System.err.println("Error en el formato de alguna fecha" + e.getMessage());
            System.exit(1);
        }

        // Creamos el Object Mapper
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);  // Esta línea realiza la indentación de la salida

        try {
            System.out.println(objectMapper.writeValueAsString(ofertas));  // Imprimimos cada objeto de la lista ofertas en formato json
        } catch (IOException e) {
            System.err.println("Error al convertir las ofertas a JSON: " + e.getMessage());
            System.exit(1);
        }
    }
}
