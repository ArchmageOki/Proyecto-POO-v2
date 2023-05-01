package src;

import java.time.LocalDate;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

public class ProdGirasol extends Producto {

    private static double rendimientoToneladasPorHectarea = 1.24;
    private static double valorPorKg = 0.62;

    public static Map<LocalDate, Double> precioHistorico = new TreeMap<>();

    static {

        precioHistorico.put(Cooperativa.fechaActual, valorPorKg);

        // Se ejecuta el while mientras la fecha de inicio (1/1/2023) sea inferior a
        // 31/12/2023.
        LocalDate fechaInicio = Cooperativa.fechaActual;
        double variacionMin = -0.02;
        double variacionMax = 0.03;
        Random rand = new Random();
        double valor = valorPorKg;

        while (fechaInicio.isBefore(LocalDate.of(2023, 12, 31))) {

            // Se añaden 7 días a cada iteración.
            fechaInicio = fechaInicio.plusDays(7);

            // Se varía el precio en cada iteración.
            double variacion = variacionMin + (variacionMax - variacionMin) * rand.nextDouble();
            valor = valor * (1 + variacion);
            valor = (double) Math.round(valor * 100) / 100;

            // Se almacena en el Map cada día con su precio
            precioHistorico.put(fechaInicio, valor);
        }
    }

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

    /**
     * @return El valor por kilogramo del producto.
     */
    public static double getValorPorKg() {
        return ProdGirasol.valorPorKg;
    }
}
