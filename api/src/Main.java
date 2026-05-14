import Models.Pokemon;
import client.Conection;
import service.Service;

public class Main {
    public static void main(String[] args) throws Exception {
        String name = "charizard";
        Service service = new Service();
        Conection conection  = new Conection();
        Pokemon pokemon = service.findPokeByName(name, conection);

        System.out.println(pokemon.toString());
    }
}