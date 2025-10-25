package controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ApiVeiculoController {
    private static final String BASE_URL = "https://parallelum.com.br/fipe/api/v1/carros/marcas";

    public String consultarMarcas() {
        try {
            return fazerRequisicao(BASE_URL);
        } catch (Exception e) {
            System.err.println("Erro ao consultar API: " + e.getMessage());
            return mockDadosVeiculos();
        }
    }

    public String consultarModelos(String marcaId) {
        try {
            String url = BASE_URL + "/" + marcaId + "/modelos";
            return fazerRequisicao(url);
        } catch (Exception e) {
            System.err.println("Erro ao consultar modelos: " + e.getMessage());
            return mockModelosVeiculos();
        }
    }

    private String fazerRequisicao(String urlString) throws Exception {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("GET");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);

        int responseCode = connection.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(connection.getInputStream())
            );

            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            return response.toString();
        } else {
            throw new RuntimeException("Erro HTTP: " + responseCode);
        }
    }

    private String mockDadosVeiculos() {
        return """
        [
            {"codigo": "1", "nome": "Fiat"},
            {"codigo": "2", "nome": "Volkswagen"},
            {"codigo": "3", "nome": "Chevrolet"},
            {"codigo": "4", "nome": "Ford"},
            {"codigo": "5", "nome": "Toyota"},
            {"codigo": "6", "nome": "Honda"},
            {"codigo": "7", "nome": "Hyundai"},
            {"codigo": "8", "nome": "Renault"}
        ]
        """;
    }

    private String mockModelosVeiculos() {
        return """
            {
                "modelos": [
                    {"codigo": 1, "nome": "A1"},
                    {"codigo": 2, "nome": "A3"},
                    {"codigo": 3, "nome": "A4"},
                    {"codigo": 4, "nome": "Q3"},
                    {"codigo": 5, "nome": "Q5"},
                    {"codigo": 6, "nome": "Q7"}
                ]
            }
            """;
    }
}