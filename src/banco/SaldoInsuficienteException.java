package banco;

/** Se lanza cuando se intenta retirar más dinero del disponible. */
public class SaldoInsuficienteException extends Exception {
    public SaldoInsuficienteException(String mensaje) {
        super(mensaje);
    }
}
