package service;

import client.Conection;
import com.google.gson.Gson;
import dto.PokeNameDto;

import java.io.IOException;

public class Service {
    Gson gson = new Gson();

    public String findPokeByName (String name, Conection conection) throws IOException, InterruptedException {
        PokeNameDto pokeName = gson.fromJson(conection.responseAPI(name), PokeNameDto.class);

        return pokeName.toString();
    }

}
