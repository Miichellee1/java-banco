package banco;

import java.math.BigDecimal;
import java.util.Scanner;

/** Menú de consola para usar el banco. */
public class Main {
    private static final Scanner in = new Scanner(System.in);
    private static final Banco banco = new Banco();

    public static void main(String[] args) {
        boolean salir = false;
        while (!salir && in.hasNextLine()) {
            mostrarMenu();
            String opcion = in.nextLine().trim();
            try {
                switch (opcion) {
                    case "1": crear(); break;
                    case "2": depositar(); break;
                    case "3": retirar(); break;
                    case "4": transferir(); break;
                    case "5": verMovimientos(); break;
                    case "6": listar(); break;
                    case "0": salir = true; break;
                    default: System.out.println("Opción inválida");
                }
            } catch (IllegalArgumentException | SaldoInsuficienteException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        System.out.println("¡Hasta pronto!");
    }

    private static void mostrarMenu() {
        System.out.println();
        System.out.println("=== BANCO ===");
        System.out.println("1) Crear cuenta");
        System.out.println("2) Depositar");
        System.out.println("3) Retirar");
        System.out.println("4) Transferir");
        System.out.println("5) Ver movimientos");
        System.out.println("6) Listar cuentas");
        System.out.println("0) Salir");
        System.out.print("Elige una opción: ");
    }

    private static void crear() {
        String titular = leer("Nombre del titular: ");
        BigDecimal saldo = leerMonto("Saldo inicial: ");
        Cuenta c = banco.crearCuenta(titular, saldo);
        System.out.println("Cuenta creada: " + c);
    }

    private static void depositar() {
        Cuenta c = banco.buscar(leerEntero("Número de cuenta: "));
        c.depositar(leerMonto("Monto a depositar: "));
        System.out.println("Nuevo saldo: $" + c.getSaldo());
    }

    private static void retirar() throws SaldoInsuficienteException {
        Cuenta c = banco.buscar(leerEntero("Número de cuenta: "));
        c.retirar(leerMonto("Monto a retirar: "));
        System.out.println("Nuevo saldo: $" + c.getSaldo());
    }

    private static void transferir() throws SaldoInsuficienteException {
        int origen = leerEntero("Cuenta de origen: ");
        int destino = leerEntero("Cuenta de destino: ");
        banco.transferir(origen, destino, leerMonto("Monto a transferir: "));
        System.out.println("Transferencia realizada");
    }

    private static void verMovimientos() {
        Cuenta c = banco.buscar(leerEntero("Número de cuenta: "));
        System.out.println(c);
        for (String m : c.getMovimientos()) {
            System.out.println("  - " + m);
        }
    }

    private static void listar() {
        if (banco.listar().isEmpty()) {
            System.out.println("Todavía no hay cuentas");
            return;
        }
        for (Cuenta c : banco.listar()) {
            System.out.println(c);
        }
    }

    private static String leer(String mensaje) {
        System.out.print(mensaje);
        return in.hasNextLine() ? in.nextLine().trim() : "";
    }

    private static int leerEntero(String mensaje) {
        try {
            return Integer.parseInt(leer(mensaje));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Ingresa un número entero válido");
        }
    }

    private static BigDecimal leerMonto(String mensaje) {
        try {
            return new BigDecimal(leer(mensaje).replace(',', '.'));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Ingresa un monto válido (ej: 1500.50)");
        }
    }
}
