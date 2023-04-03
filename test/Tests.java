package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Test;

import src.ConsumidorFinal;
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
        assertEquals(4.5, productorFederado.getExtension());
    }

    @Test
    public void addProductoAProductor() {
        productor.addProducto("Maiz", 7);
        productor.addProducto("Avena", 10);
        assertEquals(22, productor.getExtension());
    }

    @Test
    public void comprobarPreciosTest() {
        consumidor.comprobarPrecios("Lechuga", 70);
    }
}
