package org.programmers.ejemplos;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.programmers.ejemplos.exceptions.DineroInsuficienteException;
import org.programmers.ejemplos.models.Cuenta;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class CuentaTest {

    @Test
    void testNombreCuenta(){
        Cuenta cuenta = new Cuenta("Eduardo",new BigDecimal("1000.123"));
//        cuenta.setPersona("Eduardo");
        String esperado = "Eduardo";
        String real = cuenta.getPersona();
        assertNotNull(real);
        assertEquals(esperado,real);
        assertTrue(real.equals("Eduardo"));
    }

    @Test
    void testSaldoCuenta(){
        Cuenta cuenta = new Cuenta("Eduardo", new BigDecimal("1000.123"));
        assertNotNull(cuenta.getSaldo());
        assertEquals(1000.123,cuenta.getSaldo().doubleValue());
        assertFalse(cuenta.getSaldo().compareTo(BigDecimal.ZERO)<0);
        assertTrue(cuenta.getSaldo().compareTo(BigDecimal.ZERO)>0);
    }

    @Test
    void testRefernciaCuenta() {
        Cuenta cuenta = new Cuenta("Manuel", new BigDecimal("8900.00"));
        Cuenta cuenta2 = new Cuenta("Manuel", new BigDecimal("8900.00"));

        //assertNotEquals(cuenta2,cuenta);
        assertEquals(cuenta2,cuenta); //diferentes objetos con mismo atributo, se debe sobreescribir el metodo equals en cuenta
    }

    @Test
    void testDebitoCuenta() {
        Cuenta cuenta = new Cuenta("Eduardo", new BigDecimal("1000.99"));
        cuenta.debito(new BigDecimal(100));
        assertNotNull(cuenta.getSaldo());
        assertEquals(900,cuenta.getSaldo().intValue());
        assertEquals("900.99",cuenta.getSaldo().toPlainString());
    }

    @Test
    void testCreditoCuenta() {
        Cuenta cuenta = new Cuenta("Eduardo", new BigDecimal("1100.99"));
        cuenta.credito(new BigDecimal(100));
        assertNotNull(cuenta.getSaldo());
        assertEquals(1200,cuenta.getSaldo().intValue());
        assertEquals("1200.99",cuenta.getSaldo().toPlainString());
    }

    @Test
    void testDineroInsuficienteExceptionCuenta() {
        Cuenta cuenta = new Cuenta("Eduardo",new BigDecimal("1000.123"));
        Exception exception= assertThrows(DineroInsuficienteException.class,()->{
            cuenta.debito(new BigDecimal(1002));
        });
        String actual = exception.getMessage();
        String esperado = "Dinero Insuficiente";
        assertEquals(esperado,actual);
    }
}