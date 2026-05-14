import Models.Pokemon;
import client.Conection;
import service.Service;

public class Main {
    public static void main(String[] args) throws Exception {
        String name = "charizard";
        int id = 23;
        Service service = new Service();
        Conection conection  = new Conection();
        Pokemon pokemon = service.findPokeById(id,conection);

        System.out.println(pokemon.toString());
    }
}