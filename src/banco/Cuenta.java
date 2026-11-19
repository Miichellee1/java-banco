package banco;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** Cuenta bancaria con saldo e historial de movimientos. */
public class Cuenta {
    private final int numero;
    private final String titular;
    private BigDecimal saldo;
    private final List<String> movimientos = new ArrayList<>();

    public Cuenta(int numero, String titular, BigDecimal saldoInicial) {
        if (titular == null || titular.trim().isEmpty()) {
            throw new IllegalArgumentException("El titular no puede estar vacío");
        }
        if (saldoInicial == null || saldoInicial.signum() < 0) {
            throw new IllegalArgumentException("El saldo inicial no puede ser negativo");
        }
        this.numero = numero;
        this.titular = titular.trim();
        this.saldo = saldoInicial.setScale(2, RoundingMode.HALF_UP);
        movimientos.add("Apertura de cuenta con $" + this.saldo);
    }

    public void depositar(BigDecimal monto) {
        depositar(monto, "Depósito");
    }

    public void depositar(BigDecimal monto, String concepto) {
        validarMonto(monto);
        BigDecimal valor = monto.setScale(2, RoundingMode.HALF_UP);
        saldo = saldo.add(valor);
        movimientos.add(concepto + ": +$" + valor + " (saldo $" + saldo + ")");
    }

    public void retirar(BigDecimal monto) throws SaldoInsuficienteException {
        retirar(monto, "Retiro");
    }

    public void retirar(BigDecimal monto, String concepto) throws SaldoInsuficienteException {
        validarMonto(monto);
        BigDecimal valor = monto.setScale(2, RoundingMode.HALF_UP);
        if (valor.compareTo(saldo) > 0) {
            throw new SaldoInsuficienteException(
                "Saldo insuficiente. Disponible: $" + saldo + ", solicitado: $" + valor);
        }
        saldo = saldo.subtract(valor);
        movimientos.add(concepto + ": -$" + valor + " (saldo $" + saldo + ")");
    }

    private static void validarMonto(BigDecimal monto) {
        if (monto == null || monto.signum() <= 0) {
            throw new IllegalArgumentException("El monto debe ser mayor que cero");
        }
    }

    public int getNumero() { return numero; }

    public String getTitular() { return titular; }

    public BigDecimal getSaldo() { return saldo; }

    public List<String> getMovimientos() {
        return Collections.unmodifiableList(movimientos);
    }

    @Override
    public String toString() {
        return "#" + numero + " | " + titular + " | $" + saldo;
    }
}
