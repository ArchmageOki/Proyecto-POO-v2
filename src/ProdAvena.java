package src;
public class ProdAvena extends Producto {

    private static double rendimientoToneladasPorHectarea = 3.58;
    private static double valorPorKg = 0.29;

    /**
     * @param persona   Propietario del cultivo.
     * @param extension Superficie total del cultivo.
     */
    public ProdAvena(Persona persona, double extension) {
        super("Avena", persona, false, extension);
    }

    /**
     * @return Toneladas disponibles para la cooperativa del producto.
     */
    public double getToneladasDisponibles() {
        return ProdAvena.rendimientoToneladasPorHectarea * getExtension();
    }

    /**
     * @return El rendimiento en toneladas por hectárea.
     */
    public static double getRendimientoToneladasPorHectarea() {
        return ProdAvena.rendimientoToneladasPorHectarea;
    }

}
