package service;

import Models.Pokemon;
import client.Conection;
import com.google.gson.Gson;
import dto.PokeDetailsDTO;

import java.io.IOException;

public class Service {
    Gson gson = new Gson();

    public Pokemon findPokeByName (String name, Conection conection) {
        try {
            String json = conection.responseAPI(name);
            PokeDetailsDTO dto = gson.fromJson(json, PokeDetailsDTO.class);
            Pokemon poke = new Pokemon(dto);

            return poke;

        } catch (IOException e) {
            System.out.println("Erro inesperado");
            System.out.println(e.getMessage());
        } catch (InterruptedException e) {
            System.out.println("Felha na conecxao da API");
            System.out.println(e.getMessage());
        }

        return null;

    }



}
