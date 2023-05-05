package src;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

public class ProdTrigo extends Producto {

    private static double rendimientoToneladasPorHectarea = 4.75;
    private static double valorPorKg = 0.34;

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

    /**
     * @return El valor por kilogramo del producto.
     */
    public static double getValorPorKg(LocalDate fechaSuministro) {
        Map<LocalDate, Double> fechaAlReves = ((TreeMap) precioHistorico).descendingMap();
        double valorPorKgEsteDia = 0.0;

        for (Map.Entry<LocalDate, Double> entry : fechaAlReves.entrySet()) {
            if (fechaSuministro.isAfter(entry.getKey()) || fechaSuministro.isEqual(entry.getKey())) {
                valorPorKgEsteDia = entry.getValue();
                break;
            }
        }

        return valorPorKgEsteDia;
    }
    
    /**
     * Imprime el precio histórico ordenado por fechas.
     */
    public static void printPrecioHistorico() {
        double max;
        double min;
        LocalDate maxFecha = null;
        LocalDate minFecha = null;

        max = Collections.max(precioHistorico.values());
        max = (double) (Math.round(max * 100.0) / 100.0);
        min = Collections.min(precioHistorico.values());
        min = (double) (Math.round(min * 100.0) / 100.0);

        for (Map.Entry<LocalDate, Double> entry : precioHistorico.entrySet()) {
            System.out.printf("\t%-10s\t%-5.2f €\n", entry.getKey(), entry.getValue());
        }
        System.out.println();

        for (Map.Entry<LocalDate, Double> entry : precioHistorico.entrySet()) {
            if (entry.getValue() == max) {
                maxFecha = entry.getKey();
            } else if (entry.getValue() == min) {
                minFecha = entry.getKey();
            }
        }

        System.out.println("\tPrecio máximo: " + maxFecha + " = " + max + " €");
        System.out.println("\tPrecio mínimo: " + minFecha + " = " + min + " €");
        System.out.println();
    }

}
