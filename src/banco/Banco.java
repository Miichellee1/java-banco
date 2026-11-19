package banco;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/** Administra las cuentas y las operaciones entre ellas. */
public class Banco {
    private final Map<Integer, Cuenta> cuentas = new LinkedHashMap<>();
    private int siguienteNumero = 1000;

    public Cuenta crearCuenta(String titular, BigDecimal saldoInicial) {
        Cuenta cuenta = new Cuenta(siguienteNumero, titular, saldoInicial);
        cuentas.put(siguienteNumero, cuenta);
        siguienteNumero++;
        return cuenta;
    }

    public Cuenta buscar(int numero) {
        Cuenta cuenta = cuentas.get(numero);
        if (cuenta == null) {
            throw new IllegalArgumentException("No existe la cuenta " + numero);
        }
        return cuenta;
    }

    public void transferir(int origen, int destino, BigDecimal monto)
            throws SaldoInsuficienteException {
        if (origen == destino) {
            throw new IllegalArgumentException("La cuenta de origen y la de destino deben ser distintas");
        }
        Cuenta cuentaOrigen = buscar(origen);
        Cuenta cuentaDestino = buscar(destino);
        cuentaOrigen.retirar(monto, "Transferencia enviada a #" + destino);
        cuentaDestino.depositar(monto, "Transferencia recibida de #" + origen);
    }

    public Collection<Cuenta> listar() {
        return Collections.unmodifiableCollection(cuentas.values());
    }
}
