import client.Conection;
import service.Service;

public class Main {
    public static void main(String[] args) throws Exception {
        String name = "pikachu";
        Service service = new Service();
        Conection conection  = new Conection();

        System.out.println(service.findPokeByName(name,conection));
    }
}