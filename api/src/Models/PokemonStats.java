package Models;

public class PokemonStats {
    private int hp;
    private int Attack;
    private int SpecialAttack;
    private int Defence;
    private int SpecialDefence;
    private int Speed;

    public PokemonStats(int hp, int attack, int specialAttack, int defence, int specialDefence, int speed) {
        this.hp = hp;
        Attack = attack;
        SpecialAttack = specialAttack;
        Defence = defence;
        SpecialDefence = specialDefence;
        Speed = speed;
    }

    @Override
    public String toString() {
        return "PokemonStats{" +
                "hp=" + hp +
                ", Attack=" + Attack +
                ", SpecialAttack=" + SpecialAttack +
                ", Defence=" + Defence +
                ", SpecialDefence=" + SpecialDefence +
                ", Speed=" + Speed +
                '}';
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getAttack() {
        return Attack;
    }

    public void setAttack(int attack) {
        Attack = attack;
    }

    public int getSpecialAttack() {
        return SpecialAttack;
    }

    public void setSpecialAttack(int specialAttack) {
        SpecialAttack = specialAttack;
    }

    public int getDefence() {
        return Defence;
    }

    public void setDefence(int defence) {
        Defence = defence;
    }

    public int getSpecialDefence() {
        return SpecialDefence;
    }

    public void setSpecialDefence(int specialDefence) {
        SpecialDefence = specialDefence;
    }

    public int getSpeed() {
        return Speed;
    }

    public void setSpeed(int speed) {
        Speed = speed;
    }
}
