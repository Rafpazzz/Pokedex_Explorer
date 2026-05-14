package service;

import Models.Pokemon;
import Models.PokemonStats;
import client.Conection;
import com.google.gson.Gson;
import dto.PokeDetailsDTO;
import exceptions.IdInvalidException;
import exceptions.NameInvalidException;

import java.io.IOException;

public class Service {
    Gson gson = new Gson();

    public String comparePokemon(Pokemon firstPokemon, Pokemon secondPokemon) {
        if (firstPokemon == null || secondPokemon == null) {
            return "Não foi possível comparar os pokemons";
        }

        double firstAverage = calculateLevelStatsAverage(firstPokemon.getLevel_stats());
        double secondAverage = calculateLevelStatsAverage(secondPokemon.getLevel_stats());

        if (firstAverage > secondAverage) {
            return firstPokemon.getName() + " é o melhor pokemon";
        }

        if (secondAverage > firstAverage) {
            return secondPokemon.getName() + " é o melhor pokemon";
        }

        return "Os pokemons estão empatados";
    }

    private double calculateLevelStatsAverage(PokemonStats levelStats) {
        if (levelStats == null) {
            return 0;
        }

        return (
                levelStats.getHp() +
                levelStats.getAttack() +
                levelStats.getSpecialAttack() +
                levelStats.getDefence() +
                levelStats.getSpecialDefence() +
                levelStats.getSpeed()
        ) / 6.0;
    }

    public Pokemon findPokeByName (String name, Conection conection) {
        try {
            String json = conection.responseAPI(name.trim());
            if (json.isBlank()) {
                throw new NameInvalidException();
            }

            PokeDetailsDTO dto = gson.fromJson(json, PokeDetailsDTO.class);
            if (dto == null || dto.id() == null) {
                throw new NameInvalidException();
            }

            Pokemon poke = new Pokemon(dto);

            return poke;

        } catch (NameInvalidException e) {
            e.getMessage();
        } catch (InterruptedException e) {
            System.out.println("Erro inesperado");
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println("Felha na conecxao da API");
            System.out.println(e.getMessage());
        }

        return null;

    }

    public Pokemon findPokeById (int id, Conection conection) {
        try {
            if (id <= 0) {
                throw new IdInvalidException();
            }

            String json = conection.responseAPI(String.valueOf(id));
            if (json.isBlank()) {
                throw new IdInvalidException();
            }

            PokeDetailsDTO dto = gson.fromJson(json, PokeDetailsDTO.class);

            if (dto == null || dto.id() == null) {
                throw new IdInvalidException();
            }

            Pokemon poke = new Pokemon(dto);

            return poke;

        } catch (IdInvalidException e) {
            e.getMessage();
        } catch (InterruptedException e) {
            System.out.println("Erro inesperado");
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println("Felha na conecxao da API");
            System.out.println(e.getMessage());
        }

        return null;

    }



}
