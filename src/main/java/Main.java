import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
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

        List<Oferta> ofertas = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/" + csvFilePath))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");

                if (fields.length != 9) {
                    System.err.println("Error: El archivo CSV no tiene el formato correcto");
                    System.exit(1);
                }
                Oferta oferta = null;
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
                ofertas.add(oferta);
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

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        try {
            ObjectNode rootNode = objectMapper.createObjectNode();
            rootNode.putPOJO("ofertas", ofertas);
            System.out.println(objectMapper.writeValueAsString(rootNode));
        } catch (IOException e) {
            System.err.println("Error al convertir las ofertas a JSON: " + e.getMessage());
            System.exit(1);
        }


    }
}
