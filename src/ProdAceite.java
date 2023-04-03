package src;
public class ProdAceite extends Producto {

    private static double rendimientoToneladasPorHectarea = 1.44;
    private static double valorPorKg = 3.35;

    /**
     * @param persona   Propietario del cultivo.
     * @param extension Superficie total del cultivo.
     */
    public ProdAceite(Persona persona, double extension) {
        super("Aceite", persona, false, extension);
    }

    /**
     * @return Toneladas disponibles para la cooperativa del producto.
     */
    public double getToneladasDisponibles() {
        return ProdAceite.rendimientoToneladasPorHectarea * getExtension();
    }

    /**
     * @return El rendimiento en toneladas por hectárea.
     */
    public static double getRendimientoToneladasPorHectarea() {
        return ProdAceite.rendimientoToneladasPorHectarea;
    }

    /**
     * @return El valor por kilogramo del producto.
     */
    public static double getValorPorKg() {
        return ProdAceite.valorPorKg;
    }
}
