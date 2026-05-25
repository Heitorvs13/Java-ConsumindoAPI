package br.com.alura.screenmatch;

import br.com.alura.screenmatch.models.Titulo;
import br.com.alura.screenmatch.models.TituloOMDb;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    void main() throws IOException, InterruptedException {
        /*
            Chave API: b396f075
         */

        List<Titulo> titulos = new ArrayList<>();

        Gson gson = new GsonBuilder().
                setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).
                setPrettyPrinting().create();

        while(true) {
            Scanner s = new Scanner(System.in);

            System.out.println("Qual filme você deseja pesquisar?");

            String nomeFilme = s.nextLine();

            if(nomeFilme.equalsIgnoreCase("sair"))
                break;

            try {
                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create("http://www.omdbapi.com/?t=" + nomeFilme.replace(" ", "+") + "&apikey=b396f075"))
                        .build();

                HttpResponse<String> response = client
                        .send(request, HttpResponse.BodyHandlers.ofString());

                System.out.println((response.body()));
                String responseJson = response.body();


                TituloOMDb tituloOMDb = gson.fromJson(responseJson, TituloOMDb.class);

                titulos.add(new Titulo(tituloOMDb));

                System.out.println(titulos.getLast().toString());
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }

        if(!titulos.isEmpty()) {
            FileWriter escritor = new FileWriter("filmes.json");
            escritor.write(gson.toJson(titulos));
            escritor.close();
            System.out.println("Dados salvos em 'filmes.json'");
        } else{
            System.out.println("Não foram encontrados dados para gravar!");
        }
    }
}
