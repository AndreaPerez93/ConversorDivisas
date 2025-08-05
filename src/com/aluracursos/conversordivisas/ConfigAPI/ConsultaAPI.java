package com.aluracursos.conversordivisas.ConfigAPI;

import com.aluracursos.conversordivisas.Modelos.Moneda;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConsultaAPI {
    public Moneda obtenerDatos(String moneda) {
        URI direccion = URI.create("https://v6.exchangerate-api.com/v6/039a6124f553e8c8ac6ef1e3/latest/" + moneda);

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(direccion)
                .build();

        try {
            HttpResponse<String> response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());
            return  new Gson().fromJson(response.body(), Moneda.class);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);

        }
    }
}
