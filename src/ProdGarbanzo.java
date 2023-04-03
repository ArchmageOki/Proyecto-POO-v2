package src;
public class ProdGarbanzo extends Producto {

    private static double rendimientoToneladasPorHectarea = 2.84;
    private static double valorPorKg = 1.51;

    /**
     * @param persona   Propietario del cultivo.
     * @param extension Superficie total del cultivo.
     */
    public ProdGarbanzo(Persona persona, double extension) {
        super("Garbanzo", persona, false, extension);
    }

    /**
     * @return Toneladas disponibles para la cooperativa del producto.
     */
    public double getToneladasDisponibles() {
        return ProdGarbanzo.rendimientoToneladasPorHectarea * getExtension();
    }

    /**
     * @return El rendimiento en toneladas por hectárea.
     */
    public static double getRendimientoToneladasPorHectarea() {
        return ProdGarbanzo.rendimientoToneladasPorHectarea;
    }

    /**
     * @return El valor por kilogramo del producto.
     */
    public static double getValorPorKg() {
        return ProdGarbanzo.valorPorKg;
    }

}
