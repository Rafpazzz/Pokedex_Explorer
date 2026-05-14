package exceptions;

public class IdInvalidException extends RuntimeException {
    public IdInvalidException(String message) {
        super(message);
    }

    public IdInvalidException() {
      System.out.println("Não existe pokemon com esse número na pokedex");
    }
}
