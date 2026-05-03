package br.com.alura.screenmatch;

import br.com.alura.screenmatch.models.Titulo;
import br.com.alura.screenmatch.models.TituloOMDb;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class Main {
    void main() throws IOException, InterruptedException {
        /*
            Chave API: b396f075
         */

        Scanner s = new Scanner(System.in);

        System.out.println("Qual filme você deseja pesquisar?");

        String nomeFilme = s.nextLine();

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://www.omdbapi.com/?t=" + nomeFilme + "&apikey=b396f075"))
                .build();

        HttpResponse<String> response = client
                .send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println((response.body()));
        String responseJson = response.body();

        Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();
        TituloOMDb tituloOMDb = gson.fromJson(responseJson, TituloOMDb.class);

        Titulo titulo = new Titulo(tituloOMDb);
        System.out.println(titulo.toString());

    }
}
