# 🏦 Banco en Java

Aplicación de consola en **Java** que simula un banco: crear cuentas, depositar, retirar, transferir y consultar movimientos. Usa `BigDecimal` para el dinero (nunca `double`), excepciones propias y pruebas sin dependencias externas.

## Características

- Creación de cuentas con numeración automática
- Depósitos, retiros y transferencias entre cuentas
- Validación de montos y manejo de saldo insuficiente
- Historial de movimientos por cuenta
- Pruebas automáticas y CI con GitHub Actions

## Requisitos

- JDK 11 o superior (`java -version` y `javac -version` para comprobarlo)

## Ejecutar

**Linux / macOS**
```bash
bash run.sh
```

**Windows**
```bat
run.bat
```

**A mano**
```bash
mkdir out
javac -encoding UTF-8 -d out src/banco/*.java
java -cp out banco.Main
```

## Pruebas

```bash
bash test.sh        # Windows: test.bat
```

## Estructura

```
src/banco/
├── Main.java                         # menú de consola
├── Banco.java                        # gestiona cuentas y transferencias
├── Cuenta.java                       # saldo y movimientos
└── SaldoInsuficienteException.java   # excepción propia
test/banco/PruebasBanco.java          # pruebas
.github/workflows/ci.yml              # integración continua
```

## Ejemplo de uso

```
=== BANCO ===
1) Crear cuenta
...
Elige una opción: 1
Nombre del titular: Ana
Saldo inicial: 500
Cuenta creada: #1000 | Ana | $500.00
```

## Ideas para mejorar

- Guardar las cuentas en archivo o base de datos
- Interés mensual y tipos de cuenta (ahorro, corriente)
- Migrar a Maven o Gradle

## Licencia

MIT
