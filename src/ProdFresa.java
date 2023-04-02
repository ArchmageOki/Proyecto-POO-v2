package src;
public class ProdFresa extends Producto {

    private static double rendimientoToneladasPorHectarea = 17.7;
    private static double valorPorKg = 2.45;

    /**
     * @param persona   Propietario del cultivo.
     * @param extension Superficie total del cultivo.
     */
    public ProdFresa(Persona persona, double extension) {
        super("Fresa", persona, true, extension);
    }

    /**
     * @return Toneladas disponibles para la cooperativa del producto.
     */
    public double getToneladasDisponibles() {
        return ProdFresa.rendimientoToneladasPorHectarea * getExtension();
    }

    /**
     * @return El rendimiento en toneladas por hectárea.
     */
    public static double getRendimientoToneladasPorHectarea() {
        return ProdFresa.rendimientoToneladasPorHectarea;
    }

}
