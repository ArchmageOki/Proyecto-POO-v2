package src;
public class ProdTrigo extends Producto {

    private static double rendimientoToneladasPorHectarea = 4.75;
    private static double valorPorKg = 0.34;

    /**
     * @param persona   Propietario del cultivo.
     * @param extension Superficie total del cultivo.
     */
    public ProdTrigo(Persona persona, double extension) {
        super("Trigo", persona, false, extension);
    }

    /**
     * @return Toneladas disponibles para la cooperativa del producto.
     */
    public double getToneladasDisponibles() {
        return ProdTrigo.rendimientoToneladasPorHectarea * getExtension();
    }

    /**
     * @return El rendimiento en toneladas por hectárea.
     */
    public static double getRendimientoToneladasPorHectarea() {
        return ProdTrigo.rendimientoToneladasPorHectarea;
    }

}
