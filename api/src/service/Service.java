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
    private static final int COLUMN_WIDTH = 28;
    Gson gson = new Gson();

    public String comparePokemon(Pokemon firstPokemon, Pokemon secondPokemon) {
        if (firstPokemon == null || secondPokemon == null) {
            return "Não foi possível comparar os pokemons";
        }

        double firstAverage = calculateLevelStatsAverage(firstPokemon.getLevel_stats());
        double secondAverage = calculateLevelStatsAverage(secondPokemon.getLevel_stats());

        String result;
        if (firstAverage > secondAverage) {
            result = firstPokemon.getName().toUpperCase() + " é o melhor pokemon";
        } else if (secondAverage > firstAverage) {
            result = secondPokemon.getName().toUpperCase() + " é o melhor pokemon";
        } else {
            result = "Os pokemons estão empatados";
        }

        return buildPokemonComparison(firstPokemon, secondPokemon, firstAverage, secondAverage, result);
    }

    private String buildPokemonComparison(
            Pokemon firstPokemon,
            Pokemon secondPokemon,
            double firstAverage,
            double secondAverage,
            String result
    ) {
        StringBuilder comparison = new StringBuilder();

        comparison.append("\n");
        comparison.append("=".repeat((COLUMN_WIDTH * 2) + 7)).append("\n");
        comparison.append(centerText("COMPARAÇÃO DE POKEMONS", (COLUMN_WIDTH * 2) + 7)).append("\n");
        comparison.append("=".repeat((COLUMN_WIDTH * 2) + 7)).append("\n");
        comparison.append(formatColumns(
                firstPokemon.getName().toUpperCase(),
                secondPokemon.getName().toUpperCase()
        )).append("\n");
        comparison.append("-".repeat((COLUMN_WIDTH * 2) + 7)).append("\n");
        comparison.append(formatColumns(
                "Level: " + firstPokemon.getLevel(),
                "Level: " + secondPokemon.getLevel()
        )).append("\n");
        comparison.append("-".repeat((COLUMN_WIDTH * 2) + 7)).append("\n");
        comparison.append(formatColumns("LEVEL_STATS", "LEVEL_STATS")).append("\n");
        comparison.append(formatStatColumns("HP", firstPokemon.getLevel_stats().getHp(), secondPokemon.getLevel_stats().getHp())).append("\n");
        comparison.append(formatStatColumns("Ataque", firstPokemon.getLevel_stats().getAttack(), secondPokemon.getLevel_stats().getAttack())).append("\n");
        comparison.append(formatStatColumns("Ataque Especial", firstPokemon.getLevel_stats().getSpecialAttack(), secondPokemon.getLevel_stats().getSpecialAttack())).append("\n");
        comparison.append(formatStatColumns("Defesa", firstPokemon.getLevel_stats().getDefence(), secondPokemon.getLevel_stats().getDefence())).append("\n");
        comparison.append(formatStatColumns("Defesa Especial", firstPokemon.getLevel_stats().getSpecialDefence(), secondPokemon.getLevel_stats().getSpecialDefence())).append("\n");
        comparison.append(formatStatColumns("Velocidade", firstPokemon.getLevel_stats().getSpeed(), secondPokemon.getLevel_stats().getSpeed())).append("\n");
        comparison.append(formatColumns(
                String.format("Média: %.2f", firstAverage),
                String.format("Média: %.2f", secondAverage)
        )).append("\n");
        comparison.append("=".repeat((COLUMN_WIDTH * 2) + 7)).append("\n");
        comparison.append(centerText("Resultado: " + result, (COLUMN_WIDTH * 2) + 7)).append("\n");
        comparison.append("=".repeat((COLUMN_WIDTH * 2) + 7));

        return comparison.toString();
    }

    private String formatStatColumns(String label, int firstValue, int secondValue) {
        return formatColumns(
                label + ": " + firstValue,
                label + ": " + secondValue
        );
    }

    private String formatColumns(String firstColumn, String secondColumn) {
        return "| " + padRight(firstColumn, COLUMN_WIDTH) + " | " + padRight(secondColumn, COLUMN_WIDTH) + " |";
    }

    private String padRight(String text, int width) {
        if (text.length() >= width) {
            return text.substring(0, width);
        }

        return text + " ".repeat(width - text.length());
    }

    private String centerText(String text, int width) {
        if (text.length() >= width) {
            return text;
        }

        int leftPadding = (width - text.length()) / 2;
        int rightPadding = width - text.length() - leftPadding;

        return " ".repeat(leftPadding) + text + " ".repeat(rightPadding);
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
