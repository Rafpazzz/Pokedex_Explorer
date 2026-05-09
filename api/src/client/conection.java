package client;

import Models.Pokemon;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class conection {
    String url = "https://pokeapi.co/api/v2/pokemon/";

    public static String urlFinal(String endereco, String name) {
        String uri = (endereco + name).trim();
        return uri;
    }

    public static String responseAPI(String address, Pokemon pokemon) throws IOException, InterruptedException{
        address = urlFinal(address, pokemon.getName());
        try {
            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(address)).build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            return response.body();

        } catch (NumberFormatException e) {
            System.out.println("Ocorreu um erro");
            System.out.println(e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Erro no endereço");
            System.out.println(e.getMessage());
        }

        return "";
    }
}


