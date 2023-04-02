package src;
public class ProdGirasol extends Producto {

    private static double rendimientoToneladasPorHectarea = 1.24;
    private static double valorPorKg = 0.62;

    /**
     * @param persona   Propietario del cultivo.
     * @param extension Superficie total del cultivo.
     */
    public ProdGirasol(Persona persona, double extension) {
        super("Girasol", persona, false, extension);
    }

    /**
     * @return Toneladas disponibles para la cooperativa del producto.
     */
    public double getToneladasDisponibles() {
        return ProdGirasol.rendimientoToneladasPorHectarea * getExtension();
    }

    /**
     * @return El rendimiento en toneladas por hectárea.
     */
    public static double getRendimientoToneladasPorHectarea() {
        return ProdGirasol.rendimientoToneladasPorHectarea;
    }

}
