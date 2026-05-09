package Models;

import java.util.List;

public class Pokemon {
    private int pokedex_number;
    private String name;
    private Integer level;
    private Double weight;
    private List<String> types;
    private PokemonStats stats;

    public Pokemon(String name, Integer level, Double weight, List<String> type, int pokedex_number, PokemonStats stats) {
        this.pokedex_number = pokedex_number;
        this.name = name;
        this.level = level;
        this.weight = weight;
        this.types = type;
        this.stats = stats;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public int getPokedex_number() {
        return pokedex_number;
    }

    public void setPokedex_number(int pokedex_number) {
        this.pokedex_number = pokedex_number;
    }

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }

    public PokemonStats getStats() {
        return stats;
    }

    public void setStats(PokemonStats stats) {
        this.stats = stats;
    }
}
