package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

import src.Persona;
import src.Productor;

public class Tests {

    Persona persona = new Persona("Juanma", "Garrido", "Aguilera", "73433769J", 27, "Hombre");
    Productor productor = new Productor("Lechuga", persona, 5);

    @Test
    public void quitarProductoDeProductor() {
        productor.addProducto("Maiz", 7);
        productor.addProducto("Naranja", 4);        
        productor.removeProducto("Maiz");
        assertEquals(9, productor.getExtension());
    }

    @Test
    public void addProductoAProductor() {
        productor.addProducto("Maiz", 7);
        productor.addProducto("Avena", 10);
        assertEquals(22, productor.getExtension());
    }
}
