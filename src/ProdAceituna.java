package src;
public class ProdAceituna extends Producto {

    private static double rendimientoToneladasPorHectarea = 6.5;
    private static double valorPorKg = 0.92;

    /**
     * @param persona   Propietario del cultivo.
     * @param extension Superficie total del cultivo.
     */
    public ProdAceituna(Persona persona, double extension) {
        super("Aceituna", persona, true, extension);
    }

    /**
     * @return Toneladas disponibles para la cooperativa del producto.
     */
    public double getToneladasDisponibles() {
        return ProdAceituna.rendimientoToneladasPorHectarea * getExtension();
    }

    /**
     * @return El rendimiento en toneladas por hectárea.
     */
    public static double getRendimientoToneladasPorHectarea() {
        return ProdAceituna.rendimientoToneladasPorHectarea;
    }

    /**
     * @return El valor por kilogramo del producto.
     */
    public static double getValorPorKg() {
        return ProdAceituna.valorPorKg;
    }

}
