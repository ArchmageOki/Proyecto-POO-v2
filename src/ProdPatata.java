package src;
public class ProdPatata extends Producto {

    private static double rendimientoToneladasPorHectarea = 57.7;
    private static double valorPorKg = 0.37;

    /**
     * @param persona   Propietario del cultivo.
     * @param extension Superficie total del cultivo.
     */
    public ProdPatata(Persona persona, double extension) {
        super("Patata", persona, true, extension);
    }

    /**
     * @return Toneladas disponibles para la cooperativa del producto.
     */
    public double getToneladasDisponibles() {
        return ProdPatata.rendimientoToneladasPorHectarea * getExtension();
    }

    /**
     * @return El rendimiento en toneladas por hectárea.
     */
    public static double getRendimientoToneladasPorHectarea() {
        return ProdPatata.rendimientoToneladasPorHectarea;
    }

}
