package banco;

import java.math.BigDecimal;

/** Pruebas sin dependencias externas. Termina con código 1 si alguna falla. */
public class PruebasBanco {
    private static int total = 0;
    private static int fallos = 0;

    public static void main(String[] args) {
        depositoSumaSaldo();
        retiroRestaSaldo();
        retiroMayorAlSaldoFalla();
        montoInvalidoFalla();
        transferenciaMueveDinero();
        transferenciaSinFondosNoCambiaNada();
        cuentaInexistenteFalla();
        titularVacioFalla();

        System.out.println(total + " pruebas, " + fallos + " fallos");
        if (fallos > 0) {
            System.exit(1);
        }
    }

    private static BigDecimal d(String valor) {
        return new BigDecimal(valor);
    }

    private static void verificar(String nombre, boolean condicion) {
        total++;
        if (condicion) {
            System.out.println("OK    " + nombre);
        } else {
            fallos++;
            System.out.println("FALLO " + nombre);
        }
    }

    private static void depositoSumaSaldo() {
        Cuenta c = new Cuenta(1, "Ana", d("100"));
        c.depositar(d("50.5"));
        verificar("depósito suma al saldo", c.getSaldo().compareTo(d("150.50")) == 0);
    }

    private static void retiroRestaSaldo() {
        Cuenta c = new Cuenta(1, "Ana", d("100"));
        try {
            c.retirar(d("40"));
            verificar("retiro resta del saldo", c.getSaldo().compareTo(d("60")) == 0);
        } catch (SaldoInsuficienteException e) {
            verificar("retiro resta del saldo", false);
        }
    }

    private static void retiroMayorAlSaldoFalla() {
        Cuenta c = new Cuenta(1, "Ana", d("100"));
        try {
            c.retirar(d("500"));
            verificar("retiro mayor al saldo lanza excepción", false);
        } catch (SaldoInsuficienteException e) {
            verificar("retiro mayor al saldo lanza excepción", true);
        }
    }

    private static void montoInvalidoFalla() {
        Cuenta c = new Cuenta(1, "Ana", d("100"));
        try {
            c.depositar(d("-5"));
            verificar("monto negativo es rechazado", false);
        } catch (IllegalArgumentException e) {
            verificar("monto negativo es rechazado", true);
        }
    }

    private static void transferenciaMueveDinero() {
        Banco banco = new Banco();
        Cuenta a = banco.crearCuenta("Ana", d("200"));
        Cuenta b = banco.crearCuenta("Beto", d("0"));
        try {
            banco.transferir(a.getNumero(), b.getNumero(), d("75"));
            verificar("transferencia descuenta al origen", a.getSaldo().compareTo(d("125")) == 0);
            verificar("transferencia acredita al destino", b.getSaldo().compareTo(d("75")) == 0);
        } catch (SaldoInsuficienteException e) {
            verificar("transferencia mueve dinero", false);
        }
    }

    private static void transferenciaSinFondosNoCambiaNada() {
        Banco banco = new Banco();
        Cuenta a = banco.crearCuenta("Ana", d("10"));
        Cuenta b = banco.crearCuenta("Beto", d("0"));
        try {
            banco.transferir(a.getNumero(), b.getNumero(), d("999"));
            verificar("transferencia sin fondos falla", false);
        } catch (SaldoInsuficienteException e) {
            verificar("transferencia sin fondos falla", true);
            verificar("saldos intactos tras fallo",
                a.getSaldo().compareTo(d("10")) == 0 && b.getSaldo().signum() == 0);
        }
    }

    private static void cuentaInexistenteFalla() {
        try {
            new Banco().buscar(9999);
            verificar("cuenta inexistente lanza excepción", false);
        } catch (IllegalArgumentException e) {
            verificar("cuenta inexistente lanza excepción", true);
        }
    }

    private static void titularVacioFalla() {
        try {
            new Cuenta(1, "   ", d("0"));
            verificar("titular vacío es rechazado", false);
        } catch (IllegalArgumentException e) {
            verificar("titular vacío es rechazado", true);
        }
    }
}
