package client;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class Conection {
    private static final Duration TIMEOUT = Duration.ofSeconds(10);
    String url = "https://pokeapi.co/api/v2/pokemon/";

    public Conection(){}

    public String urlFinal(String endereco, String name) {
        String uri = (endereco + name).trim();
        return uri;
    }

    public String responseAPI(String name) throws IOException, InterruptedException{
        String address = urlFinal(url, name);
        try {
            HttpClient client = HttpClient.newBuilder()
                    .connectTimeout(TIMEOUT)
                    .build();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(address))
                    .timeout(TIMEOUT)
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                return "";
            }

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

