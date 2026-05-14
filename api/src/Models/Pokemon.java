package Models;

import dto.PokeDetailsDTO;
import dto.StatSlotDTO;

import java.util.List;
import java.util.Random;

public class Pokemon {
    private int game_index;
    private String name;
    private Integer level;
    private Double weight;
    private List<PokemonTypes> types;
    private PokemonStats base_stats;
    private PokemonStats level_stats;
    Random r = new Random();

    public Pokemon(){}

    public Pokemon (PokeDetailsDTO poke) {
        this.game_index = poke.id();
        this.name  = poke.name();
        this.level = r.nextInt(1, 101);
        this.weight = poke.weight();
        this.types = poke.types().stream().map(typeDTO -> {
            PokemonTypes type = new PokemonTypes();
            type.setName(typeDTO.type().name());
            return type;
        }).toList();
        this.base_stats = convertStats(poke.stats());
        this.level_stats = conversaoLevel(this.level, this.base_stats);
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

    public PokemonStats conversaoLevel(int level, PokemonStats stats) {
        int hpLevel = ((2 * stats.getHp() * level) / 100) + level + 10;
        int attackLevel = ((2 * stats.getAttack() * level) / 100) + 5;
        int specialAttackLevel = ((2 * stats.getSpecialAttack() * level) / 100) + 5;
        int defenceLevel = ((2 * stats.getDefence() * level) / 100) + 5;
        int specialDefenceLevel = ((2 * stats.getSpecialDefence() * level) / 100) + 5;
        int speedLevel = ((2 * stats.getSpeed() * level) / 100) + 5;

        return new PokemonStats(
                hpLevel,
                attackLevel,
                specialAttackLevel,
                defenceLevel,
                specialDefenceLevel,
                speedLevel
        );
    }

    @Override
    public String toString() {
        return "\n==============================" + '\n' +
                "Pokemon: " + name.toUpperCase() + '\n' +
                "Numero da Pokedex: " + game_index + '\n' +
                "Nivel: " + level + '\n' +
                "Peso: " + weight + '\n' +
                "Tipos: " + types + '\n' +
                '\n' +
                "Estatisticas Base:" + '\n' +
                "  " + base_stats + '\n' +
                '\n' +
                "Estatisticas no Nivel " + level + ":" + '\n' +
                "  " + level_stats + '\n' +
                "==============================";
    }

    public PokemonStats getBase_stats() {
        return base_stats;
    }

    public PokemonStats getLevel_stats() {
        return level_stats;
    }

    public List<PokemonTypes> getTypes() {
        return types;
    }

    public Double getWeight() {
        return weight;
    }

    public Integer getLevel() {
        return level;
    }

    public String getName() {
        return name;
    }

    public int getGame_index() {
        return game_index;
    }
}
