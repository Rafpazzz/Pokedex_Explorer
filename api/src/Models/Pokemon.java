package Models;

import dto.PokeDetailsDTO;
import dto.StatSlotDTO;

import java.util.List;

public class Pokemon {
    private int game_index;
    private String name;
    private Integer level;
    private Double weight;
    private List<PokemonTypes> types;
    private PokemonStats stats;

    public Pokemon(){}


    public Pokemon(int game_index, String name, Integer level, Double weight, List<PokemonTypes> types, PokemonStats stats) {
        this.game_index = game_index;
        this.name = name;
        this.level = level;
        this.weight = weight;
        this.types = types;
        this.stats = stats;
    }

    public  Pokemon (PokeDetailsDTO poke) {
        this.game_index = poke.id();
        this.name  = poke.name();
        this.weight = poke.weight();
        this.types = poke.types().stream().map(typeDTO -> {
            PokemonTypes type = new PokemonTypes();
            type.setName(typeDTO.type().name());
            return type;
        }).toList();
        this.stats = convertStats(poke.stats());
    }

    private PokemonStats convertStats(List<StatSlotDTO> detailsDTO) {
        int hp = 0;
        int attack = 0;
        int specialAttack = 0;
        int defence = 0;
        int specialDefence = 0;
        int speed = 0;

        for (StatSlotDTO statDto : detailsDTO) {
            String statName = statDto.stat().name();
            int value = statDto.base_stat();

            switch (statName) {
                case "hp" -> hp = value;
                case "attack" -> attack = value;
                case "special-attack" -> specialAttack = value;
                case "defense" -> defence = value;
                case "special-defense" -> specialDefence = value;
                case "speed" -> speed = value;
            }
        }

        return new PokemonStats(hp, attack, specialAttack, defence, specialDefence, speed);
    }

    @Override
    public String toString() {
        return "Pokemon{" +
                "id=" + game_index +
                ", name='" + name + '\n' +
                ", level=" + level + '\n' +
                ", weight=" + weight + '\n' +
                ", types=" + types + '\n' +
                ", stats=" + stats + '\n'+
                '}';
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

    public int getGame_index() {
        return game_index;
    }

    public void setGame_index(int game_index) {
        this.game_index = game_index;
    }

    public List<PokemonTypes> getTypes() {
        return types;
    }

    public void setTypes(List<PokemonTypes> types) {
        this.types = types;
    }

    public PokemonStats getStats() {
        return stats;
    }

    public void setStats(PokemonStats stats) {
        this.stats = stats;
    }
}
