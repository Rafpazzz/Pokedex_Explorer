package exceptions;

public class NameInvalidException extends RuntimeException {
    public NameInvalidException(String message) {
        super(message);
    }

    public NameInvalidException() {
        System.out.println("Nome do pokemon esta invalido");
    }
}
