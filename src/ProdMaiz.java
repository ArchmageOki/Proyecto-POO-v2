package src;
public class ProdMaiz extends Producto {

    private static double rendimientoToneladasPorHectarea = 12.48;
    private static double valorPorKg = 0.38;

    /**
     * @param persona   Propietario del cultivo.
     * @param extension Superficie total del cultivo.
     */
    public ProdMaiz(Persona persona, double extension) {
        super("Maiz", persona, false, extension);
    }

    /**
     * @return Toneladas disponibles para la cooperativa del producto.
     */
    public double getToneladasDisponibles() {
        return ProdMaiz.rendimientoToneladasPorHectarea * getExtension();
    }

    /**
     * @return El rendimiento en toneladas por hectárea.
     */
    public static double getRendimientoToneladasPorHectarea() {
        return ProdMaiz.rendimientoToneladasPorHectarea;
    }

    /**
     * @return El valor por kilogramo del producto.
     */
    public static double getValorPorKg() {
        return ProdMaiz.valorPorKg;
    }

}
