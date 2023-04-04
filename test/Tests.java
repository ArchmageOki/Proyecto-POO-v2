package test;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.Test;

import src.ConsumidorFinal;
import src.Cooperativa;
import src.EmpresaLogistica;
import src.Factura;
import src.Persona;
import src.Productor;
import src.ProductorFederado;

public class Tests {

    Persona persona = new Persona("Juanma", "Garrido", "Aguilera", "73433769J", 27, "Hombre");
    Persona persona2 = new Persona("Lidia", "Pedrosa", "Fernández", "73138004M", 28, "Mujer");
    Persona persona3 = new Persona("Pepe", "Castro", "Puto", "5", 29, "Hombre");
    Productor productor = new Productor("Lechuga", persona, 5);
    ProductorFederado productorFederado = new ProductorFederado("Tomate", persona, 4);
    ConsumidorFinal consumidor = new ConsumidorFinal("Jose", "Garcia", "Garcia", "78451265S", 29, "Hombre", 140);
    EmpresaLogistica empresa1 = new EmpresaLogistica("Transportes Paco S.L.", 0.043, 0.02);
    EmpresaLogistica empresa2 = new EmpresaLogistica("Garcia y Hnos. S.L.", 0.051, 0.012);
    EmpresaLogistica empresa3 = new EmpresaLogistica("Pamplona transportes S.L.", 0.055, 0.015);

    @Test
    public void quitarProductoDeProductor() {
        productor.addProducto("Maiz", 7);
        productor.addProducto("Naranja", 4);
        productor.removeProducto("Maiz");
        assertEquals(9, productor.getExtension());
    }

    @Test
    public void quitarProductoDeProdFederado() {
        productorFederado.addProducto(persona2, 0.5);
        assertEquals(4.5, productorFederado.getExtension());
        productorFederado.addProducto(persona3, 4);
        assertTrue("Se superan las hectáreas marcadas", productorFederado.getExtension() < Cooperativa.limiteExtension);
    }

    @Test
    public void addProductoAProductor() {
        productor.addProducto("Maiz", 7);
        productor.addProducto("Avena", 10);
        assertEquals(22, productor.getExtension());
    }

    @Test
    public void comprobarPreciosConsumidorFinalTest() {
        System.out.println("Lechuga válida:");
        consumidor.comprobarPrecios("Lechuga", 70);
        System.out.println("Tomate de más de 100 kg:");
        consumidor.comprobarPrecios("Tomate", 120);
        System.out.println("Maiz insuficiente:");
        consumidor.comprobarPrecios("Maiz", 10);
        System.out.println("Tomate válido:");
        consumidor.comprobarPrecios("Tomate", 20);
    }

    @Test
    public void testFactura() {
        Factura factura = new Factura("Lechuga", empresa1, consumidor, 30);
    }
}
