import Models.Pokemon;
import client.Conection;
import service.Service;

public class Main {
    public static void main(String[] args) throws Exception {
        String name = "charizard";
        int id = 23;
        Service service = new Service();
        Conection conection  = new Conection();
        Pokemon pokemon1 = service.findPokeById(id,conection);
        Pokemon pokemon2 = service.findPokeByName(name, conection);

        System.out.println(service.comparePokemon(pokemon1, pokemon2));
    }
}