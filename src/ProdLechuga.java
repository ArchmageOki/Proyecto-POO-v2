package src;
public class ProdLechuga extends Producto {

    private static double rendimientoToneladasPorHectarea = 30;
    private static double valorPorKg = 0.75;

    /**
     * @param persona   Propietario del cultivo.
     * @param extension Superficie total del cultivo.
     */
    public ProdLechuga(Persona persona, double extension) {
        super("Lechuga", persona, true, extension);
    }

    /**
     * @return Toneladas disponibles para la cooperativa del producto.
     */
    public double getToneladasDisponibles() {
        return ProdLechuga.rendimientoToneladasPorHectarea * getExtension();
    }

    /**
     * @return El rendimiento en toneladas por hectárea.
     */
    public static double getRendimientoToneladasPorHectarea() {
        return ProdLechuga.rendimientoToneladasPorHectarea;
    }

}
