package test;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.Map.Entry;

import org.junit.Test;

import src.ConsumidorFinal;
import src.Cooperativa;
import src.EmpresaLogistica;
import src.Factura;
import src.Persona;
import src.ProdAceite;
import src.ProdAceituna;
import src.Productor;
import src.ProductorFederado;

public class Tests {

    Persona persona = new Persona("Juanma", "Garrido", "Aguilera", "73433769J", 27, "Hombre");
    Persona persona2 = new Persona("Lidia", "Pedrosa", "Fernández", "73138004M", 28, "Mujer");
    Persona persona3 = new Persona("Pepe", "Castro", "Puto", "5", 29, "Hombre");
    Productor productor = new Productor("Lechuga", persona, 5);
    Productor productor4 = new Productor("Aceite", persona, 5);
    Productor productor2 = new Productor("Avellana", persona2, 4);
    Productor productor3 = new Productor("Lechuga", persona2, 2);
    ProductorFederado productorFederado = new ProductorFederado("Tomate", persona, 4);
    ConsumidorFinal consumidor = new ConsumidorFinal("Jose", "Garcia", "Garcia", "78451265S", 29, "Hombre", 140, "Calle 2", "31000");
    ConsumidorFinal consumidor2 = new ConsumidorFinal("Mariano", "Rajoy", "Brei", "73433769J", 56, "Hombre", 145, "Calle 4", "31000");
    EmpresaLogistica empresa1 = new EmpresaLogistica("Transportes Paco S.L.", 0.043, 0.02);
    EmpresaLogistica empresa2 = new EmpresaLogistica("Garcia y Hnos. S.L.", 0.051, 0.012);
    EmpresaLogistica empresa3 = new EmpresaLogistica("Pamplona transportes S.L.", 0.055, 0.015);
    LocalDate ld = LocalDate.now();

    @Test
    public void granProductor() {
        productor.addProducto("Pepino", 3);
        System.out.println(productor.getEsGranProductor());
    }

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
        // Revisar formato!
        System.out.println("Lechuga válida:");
        consumidor.comprobarPrecios("Lechuga", 70, ld);
        System.out.println("Tomate de más de 100 kg:");
        consumidor.comprobarPrecios("Tomate", 120, ld);
        System.out.println("Maiz insuficiente:");
        consumidor.comprobarPrecios("Maiz", 10, ld);
        System.out.println("Tomate válido:");
        consumidor.comprobarPrecios("Tomate", 20, ld);
    }

    @Test
    public void testFactura() {
        System.out.println("Producto perecedero, kg válidos, 140km");
        Factura factura = new Factura("Lechuga", empresa1, consumidor, 50, ld, 7);
        System.out.println("Factura desde la cooperativa:");
        Cooperativa.printFacturaFromId(4000000);
        System.out.println(Cooperativa.getPrecioTotalLogisticaFromId(4000000));
        System.out.println("Factura no válida:");
        Cooperativa.printFacturaFromId(1023987982);
    }

    @Test
    public void testFactura2() {
        Factura factura2 = new Factura("Tomate", empresa2, consumidor, 120, ld, 7);
    }

    @Test
    public void testFactura3() {
        System.out.println("Producto no perecedero, kg válidos, 200km");
        Factura factura3 = new Factura("Avellana", empresa3, consumidor2, 80, ld, 7);
        System.out.printf("%6.2f €\n", Cooperativa.getPrecioTotalLogisticaFromId(4000000));
        System.out.printf("%6.2f €\n", Cooperativa.getPrecioTotalProductores(4000000));
        System.out.printf("%6.2f €\n", Cooperativa.getPrecioTotalImpuestosFromId(4000000));
        System.out.printf("%6.2f €\n", Cooperativa.getPrecioTotalFacturaFromId(4000000));
    }

    @Test
    public void testRepartoDinero() {
        Factura facturaLechuga = new Factura("Lechuga", empresa1, consumidor, 30, ld, 3);
        persona.printDinero();
        persona2.printDinero();
        Cooperativa.printPersonasYDinero();
        assertTrue(persona.getDinero() + persona2.getDinero() + Cooperativa.getDinero() == facturaLechuga
                .getPrecioCooperativa());
    }

    @Test
    public void testConsumidorFinal() {
        consumidor.comprobarPrecios("Lechuga", 50, ld);
        consumidor.comprarProducto("Lechuga", 50, empresa1, ld, 4);
    }

    @Test
    public void testProductorFederadoCompra() {
        productorFederado.addProducto(persona2, 0.5);
        consumidor2.comprarProducto("Tomate", 10, empresa1, ld, 5);
        System.out.println(persona.getDinero());
        System.out.println(persona2.getDinero());
        assertNotEquals(persona.getDinero(), persona2.getDinero());
    }

    @Test
    public void testProductorFederadoCheckPrecios() {
        consumidor2.comprobarPrecios("Tomate", 10, ld);
    }

    @Test // Comprobar
    public void testBuclesProductos() {
        for (Entry<LocalDate, Double> entry : ProdAceite.precioHistorico.entrySet()) {
            LocalDate key = entry.getKey();
            Double value = entry.getValue();
            System.out.println(key + " = " + value);
        }
        System.out.println("-----------------");
        for (Entry<LocalDate, Double> entry : ProdAceituna.precioHistorico.entrySet()) {
            LocalDate key = entry.getKey();
            Double value = entry.getValue();
            System.out.println(key + " = " + value);
        }

    }

}
