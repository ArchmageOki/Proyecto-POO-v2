package src;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cooperativa {

    public static double limiteExtension = 5.0;
    public static int anno = 2023;
    public static List<String> nombresProductos = Arrays.asList("Lechuga", "Tomate", "Aceituna", "Fresa", "Naranja",
            "Patata", "Pepino", "Aceite", "Trigo", "Maiz", "Girasol", "Avellana", "Garbanzo", "Avena");

    public static List<Persona> personas = new ArrayList<>();
    public static List<Productor> productores = new ArrayList<>();
    public static List<ProductorFederado> productoresFederados = new ArrayList<>();
    public static List<EmpresaLogistica> empresasLogisticas = new ArrayList<>();
    public static List<ConsumidorFinal> consumidoresFinales = new ArrayList<>();
    public static List<Distribuidor> distribuidores = new ArrayList<>();

    public static Map<String, Double> productoDisponible = new HashMap<>();

    static {
        productoDisponible = new HashMap<>();
        for (String producto : nombresProductos) {
            productoDisponible.put(producto, 0.0);
        }
    }

    /**
     * Constructor privado. No se va a usar nunca porque la idea de la clase es que
     * todos los métodos sean static.
     */
    private Cooperativa() {
    }

    /**
     * @param persona Añade a la persona a la lista de personas.
     */
    public static void addPersona(Persona persona) {
        Cooperativa.personas.add(persona);
    }

    /**
     * @param productor Añade al productor a la lista de productores.
     */
    public static void addProductor(Productor productor) {
        Cooperativa.productores.add(productor);
    }

    /**
     * @param productorFederado Añade al productor federado a la lista de
     *                          productores federados.
     */
    public static void addProductorFederado(ProductorFederado productorFederado) {
        Cooperativa.productoresFederados.add(productorFederado);
    }

    /**
     * @param empresaLogistica Añade la empresa logística a la lista de empresas
     *                         logísticas.
     */
    public static void addEmpresaLogistica(EmpresaLogistica empresaLogistica) {
        Cooperativa.empresasLogisticas.add(empresaLogistica);
    }

    /**
     * @param persona Añade al consumidor final a la lista de consumidores finales.
     */
    public static void addConsumidorFinal(ConsumidorFinal consumidorFinal) {
        Cooperativa.consumidoresFinales.add(consumidorFinal);
    }

    /**
     * @param distribuidor Añade al distribuidor a la lista de distribuidores.
     */
    public static void addDistribuidor(Distribuidor distribuidor) {
        Cooperativa.distribuidores.add(distribuidor);
    }

    /**
     * @param nombreProducto Nombre del producto que se quiere comprobar.
     * @return True si existe un productor federado con ese producto.
     */
    public static boolean checkProductorFederado(String nombreProducto) {
        for (ProductorFederado productorFederado : productoresFederados) {
            if (productorFederado.getNombreProducto().equals(nombreProducto)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param nombreProducto Nombre del producto al que se le quiere añadir una
     *                       cantidad.
     * @param extension      Cantidad que se quiere añadir.
     */
    public static void addProductoDisponible(String nombreProducto, double extension) {
        double toneladasActuales = productoDisponible.get(nombreProducto);
        double toneladasASumar = getRendimientoFromNombre(nombreProducto) * extension;
        double nuevasToneladas = toneladasActuales + toneladasASumar;
        nuevasToneladas = (double) Math.round(nuevasToneladas * 100) / 100;
        productoDisponible.put(nombreProducto, nuevasToneladas);
    }

    /**
     * @param nombreProducto Nombre del producto.
     * 
     * @return El rendimiento en toneladas por hectárea.
     */
    private static double getRendimientoFromNombre(String nombreProducto) {
        switch (nombreProducto) {
            case "Aceite":
                return ProdAceite.getRendimientoToneladasPorHectarea();
            case "Aceituna":
                return ProdAceituna.getRendimientoToneladasPorHectarea();
            case "Avellana":
                return ProdAvellana.getRendimientoToneladasPorHectarea();
            case "Avena":
                return ProdAvena.getRendimientoToneladasPorHectarea();
            case "Fresa":
                return ProdFresa.getRendimientoToneladasPorHectarea();
            case "Garbanzo":
                return ProdGarbanzo.getRendimientoToneladasPorHectarea();
            case "Girasol":
                return ProdGirasol.getRendimientoToneladasPorHectarea();
            case "Lechuga":
                return ProdLechuga.getRendimientoToneladasPorHectarea();
            case "Maiz":
                return ProdMaiz.getRendimientoToneladasPorHectarea();
            case "Naranja":
                return ProdNaranja.getRendimientoToneladasPorHectarea();
            case "Patata":
                return ProdPatata.getRendimientoToneladasPorHectarea();
            case "Pepino":
                return ProdPepino.getRendimientoToneladasPorHectarea();
            case "Tomate":
                return ProdTomate.getRendimientoToneladasPorHectarea();
            case "Trigo":
                return ProdTrigo.getRendimientoToneladasPorHectarea();
            default:
                return 0.0;

        }
    }

    /**
     * @param nombreProducto Nombre del producto al que se le quiere restar una
     *                       cantidad.
     * @param extension      Cantidad que se quiere restar.
     */
    public static void substractProductoDisponible(String nombreProducto, double extension) {
        double toneladasActuales = productoDisponible.get(nombreProducto);
        double toneladasARestar = getRendimientoFromNombre(nombreProducto) * extension;
        double nuevasToneladas = toneladasActuales - toneladasARestar;
        nuevasToneladas = (double) Math.round(nuevasToneladas * 100) / 100;
        productoDisponible.put(nombreProducto, nuevasToneladas);
    }

}
