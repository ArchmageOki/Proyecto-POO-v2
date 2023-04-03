package src;
public class ProdNaranja extends Producto {

    private static double rendimientoToneladasPorHectarea = 32.3;
    private static double valorPorKg = 0.17;

    /**
     * @param persona   Propietario del cultivo.
     * @param extension Superficie total del cultivo.
     */
    public ProdNaranja(Persona persona, double extension) {
        super("Naranja", persona, true, extension);
    }

    /**
     * @return Toneladas disponibles para la cooperativa del producto.
     */
    public double getToneladasDisponibles() {
        return ProdNaranja.rendimientoToneladasPorHectarea * getExtension();
    }

    /**
     * @return El rendimiento en toneladas por hectárea.
     */
    public static double getRendimientoToneladasPorHectarea() {
        return ProdNaranja.rendimientoToneladasPorHectarea;
    }

    /**
     * @return El valor por kilogramo del producto.
     */
    public static double getValorPorKg() {
        return ProdNaranja.valorPorKg;
    }

}
