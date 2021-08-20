package org.programmers.ejemplos;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.programmers.ejemplos.exceptions.DineroInsuficienteException;
import org.programmers.ejemplos.models.Banco;
import org.programmers.ejemplos.models.Cuenta;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class CuentaTest {

    @Test
    void testNombreCuenta() {
        Cuenta cuenta = new Cuenta("Eduardo", new BigDecimal("1000.123"));
//        cuenta.setPersona("Eduardo");
        String esperado = "Eduardo g";
        String real = cuenta.getPersona();
        assertNotNull(real, ()-> "La cuenta no puede ser nula");
        assertEquals(esperado, real,()-> "el nombre de la cuenta no es el que se esperaba; se esperaba: "+esperado
        +" y fue: "+real);
        assertTrue(real.equals("Eduardo"),()-> "nombre de cuenta esperada debe ser igual al real");
    }

    @Test
    void testSaldoCuenta() {
        Cuenta cuenta = new Cuenta("Eduardo", new BigDecimal("1000.123"));
        assertNotNull(cuenta.getSaldo());
        assertEquals(1000.123, cuenta.getSaldo().doubleValue());
        assertFalse(cuenta.getSaldo().compareTo(BigDecimal.ZERO) < 0);
        assertTrue(cuenta.getSaldo().compareTo(BigDecimal.ZERO) > 0);
    }

    @Test
    void testRefernciaCuenta() {
        Cuenta cuenta = new Cuenta("Manuel", new BigDecimal("8900.00"));
        Cuenta cuenta2 = new Cuenta("Manuel", new BigDecimal("8900.00"));

        //assertNotEquals(cuenta2,cuenta);
        assertEquals(cuenta2, cuenta); //diferentes objetos con mismo atributo, se debe sobreescribir el metodo equals en cuenta
    }

    @Test
    void testDebitoCuenta() {
        Cuenta cuenta = new Cuenta("Eduardo", new BigDecimal("1000.99"));
        cuenta.debito(new BigDecimal(100));
        assertNotNull(cuenta.getSaldo());
        assertEquals(900, cuenta.getSaldo().intValue());
        assertEquals("900.99", cuenta.getSaldo().toPlainString());
    }

    @Test
    void testCreditoCuenta() {
        Cuenta cuenta = new Cuenta("Eduardo", new BigDecimal("1100.99"));
        cuenta.credito(new BigDecimal(100));
        assertNotNull(cuenta.getSaldo());
        assertEquals(1200, cuenta.getSaldo().intValue());
        assertEquals("1200.99", cuenta.getSaldo().toPlainString());
    }

    @Test
    void testDineroInsuficienteExceptionCuenta() {
        Cuenta cuenta = new Cuenta("Eduardo", new BigDecimal("1000.123"));
        Exception exception = assertThrows(DineroInsuficienteException.class, () -> {
            cuenta.debito(new BigDecimal(1002));
        });
        String actual = exception.getMessage();
        String esperado = "Dinero Insuficiente";
        assertEquals(esperado, actual);
    }

    @Test
    void testTransferirDineroCuentas() {
        Cuenta cuenta1 = new Cuenta("Eduardo Garza", new BigDecimal("2500"));
        Cuenta cuenta2 = new Cuenta("Manuel", new BigDecimal("1500.8989"));

        Banco banco = new Banco();
        banco.setNombre("Banco EMGS");
        banco.transferir(cuenta2, cuenta1, new BigDecimal(500));
        assertEquals("1000.8989", cuenta2.getSaldo().toPlainString());
        assertEquals("3000", cuenta1.getSaldo().toPlainString());

    }

    @Test
    void testRelacionBancoCuentas() {
        Cuenta cuenta1 = new Cuenta("Eduardo Garza", new BigDecimal("2500"));
        Cuenta cuenta2 = new Cuenta("Manuel", new BigDecimal("1500.8989"));

        Banco banco = new Banco();
        banco.addCuenta(cuenta1);
        banco.addCuenta(cuenta2);

        banco.setNombre("Banco EMGS");
        banco.transferir(cuenta2, cuenta1, new BigDecimal(500));
        assertAll(
                () -> assertEquals("1000.8989", cuenta2.getSaldo().toPlainString(),
                                ()->"El valor del salod de la cuenta 2 no es el esperado"),
                () -> assertEquals("3000", cuenta1.getSaldo().toPlainString(),
                        ()->"El valor del salod de la cuenta 1 no es el esperado"),
                () -> assertEquals(2, banco.getCuentas().size(),()->"banco no tiene las cuentas esperadas"),
                () -> assertEquals("Banco EMGS", cuenta1.getBanco().getNombre(),
                        ()->"no existe banco con ese nombre"), // valida si cuenta1 está en el banco emgs
                () -> assertEquals("Manuel", banco.getCuentas().stream()
                        .filter(c -> c.getPersona().equals("Manuel"))
                        .findFirst().get().getPersona(),
                        ()->"No existe cuenta a nombre de la persona buscada"), // valida si banco tiene una cuenta de la persona manuel filtrando de una lista
                () -> assertTrue(banco.getCuentas().stream().anyMatch(c -> c.getPersona().equals("Eduardo Garza")),
                        ()->"No existe cuenta a nombre de la persona buscada")
        );
    }
}