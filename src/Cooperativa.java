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
    public static List<Factura> facturas = new ArrayList<>();

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
     * @param factura Añade la factura a la lista de facturas.
     */
    public static void addFactura(Factura factura) {
        Cooperativa.facturas.add(factura);
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

    /**
     * @param nombreProducto Nombre del producto.
     * @return Valor por kilogramo de producto.
     */
    public static double getPrecioPorKg(String nombreProducto) {
        switch (nombreProducto) {
            case "Aceite":
                return ProdAceite.getValorPorKg();
            case "Aceituna":
                return ProdAceituna.getValorPorKg();
            case "Avellana":
                return ProdAvellana.getValorPorKg();
            case "Avena":
                return ProdAvena.getValorPorKg();
            case "Fresa":
                return ProdFresa.getValorPorKg();
            case "Garbanzo":
                return ProdGarbanzo.getValorPorKg();
            case "Girasol":
                return ProdGirasol.getValorPorKg();
            case "Lechuga":
                return ProdLechuga.getValorPorKg();
            case "Maiz":
                return ProdMaiz.getValorPorKg();
            case "Naranja":
                return ProdNaranja.getValorPorKg();
            case "Patata":
                return ProdPatata.getValorPorKg();
            case "Pepino":
                return ProdPepino.getValorPorKg();
            case "Tomate":
                return ProdTomate.getValorPorKg();
            case "Trigo":
                return ProdTrigo.getValorPorKg();
            default:
                return 0.0;
        }
    }

    /**
     * @param nombreProducto Nombre del producto que se quiere consultar.
     * @return True si es perecedero. False si es no perecedero.
     */
    public static boolean esPerecedero(String nombreProducto) {
        switch (nombreProducto) {
            case "Aceite", "Avellana", "Avena", "Garbanzo", "Girasol", "Maiz", "Trigo":
                return false;
            case "Aceituna", "Fresa", "Lechuga", "Naranja", "Patata", "Pepino", "Tomate":
                return true;
            default:
                return false;
        }
    }

    /**
     * @param id ID de la factura que se quiere imprimir.
     */
    public static void printFacturaFromId(int id) {
        for (Factura factura : facturas) {
            if (factura.getId() == id) {
                factura.printFactura();
                break;
            }
        }
        System.out.println("No hay ninguna factura con el ID " + id + ".");
    }

    /**
     * @param id ID de la factura.
     * @return Cantidad de dinero que se le paga a la logística.
     */
    public static double getPrecioTotalLogisticaFromId(int id) {
        for (Factura factura : facturas) {
            if (factura.getId() == id) {
                double precioTotalLogistica = factura.getTramosGranLogistica().size() * factura.getPrecioGranLogistica()
                        + factura.getTramosPeqLogistica().size() * factura.getPrecioPeqLogistica();
                return precioTotalLogistica;
            }
        }
        return 0;
    }

    /**
     * @param id ID de la factura.
     * @return Cantidad de dinero que recibe la cooperativa.
     */
    public static double getPrecioTotalCooperativaFromId(int id) {
        for (Factura factura : facturas) {
            if (factura.getId() == id) {
                double precioTotalCooperativa = factura.getPrecioCooperativa();
                return precioTotalCooperativa;
            }
        }
        return 0;
    }

    /**
     * @param id ID de la factura.
     * @return Cantidad de dinero que son impuestos.
     */
    public static double getPrecioTotalImpuestosFromId(int id) {
        for (Factura factura : facturas) {
            if (factura.getId() == id) {
                double precioTotalImpuestos = (Cooperativa.getPrecioTotalLogisticaFromId(id)
                        + Cooperativa.getPrecioTotalCooperativaFromId(id)) * factura.getImpuestos() / 100;
                return precioTotalImpuestos;
            }
        }
        return 0;
    }

    /**
     * @param id ID de la factura.
     * @return Cantidad de dinero total de la factura.
     */
    public static double getPrecioTotalFacturaFromId(int id) {
        for (Factura factura : facturas) {
            if (factura.getId() == id) {
                double precioTotal = Cooperativa.getPrecioTotalLogisticaFromId(id)
                        + Cooperativa.getPrecioTotalCooperativaFromId(id)
                        + Cooperativa.getPrecioTotalImpuestosFromId(id);
                return precioTotal;
            }
        }
        return 0;
    }

    /**
     * @param id ID de la factura.
     */
    public static void pagarProductores(int id) {
        // Saber que producto es
        String nombreProducto = null;
        Factura factura = null;
        for (Factura facturaAux : facturas) {
            if (facturaAux.getId() == id) {
                factura = facturaAux;
                nombreProducto = facturaAux.getNombreProducto();
            }
        }
        if (nombreProducto == null && factura == null) {
            System.out.println("La factura no existe");
        } else {
            // Buscar los productores que tienen ese producto y sus hectáreas

            // Persona y hectáreas que posee.
            Map<Persona, Double> pagos = new HashMap<>();
            double hectareasTotales = 0;

            for (Productor productor : productores) {
                for (Producto producto : productor.getProductos()) {
                    if (producto.getNombreProducto().equals(nombreProducto)) {
                        pagos.put(producto.getPropietario(), producto.getExtension());
                        hectareasTotales += producto.getExtension();
                    }
                }
            }

            for (ProductorFederado productorFederado : productoresFederados) {
                for (Producto producto : productorFederado.getProductos()) {
                    if (producto.getNombreProducto().equals(nombreProducto)) {
                        producto.getPropietario();
                        pagos.put(producto.getPropietario(), producto.getExtension());
                        hectareasTotales += producto.getExtension();
                    }
                }
            }

            // Pagar proporcionalmente (sólo la parte que se lleva la cooperativa)

            double dineroARepartir = Cooperativa.getPrecioTotalCooperativaFromId(id);
            for (Persona persona : pagos.keySet()) {
                double hectareasPersona = pagos.get(persona);
                double parteAPagar = (hectareasPersona * dineroARepartir) / hectareasTotales;
                persona.addDinero(parteAPagar);
            }
        }
    }

    public static void printPersonasYDinero() {
        for (Persona persona : personas) {
            String nombre = persona.getNombre();
            String apellido1 = persona.getApellido1();
            double dinero = persona.getDinero();
            System.out.println(nombre + " " + apellido1 + " = " + dinero + " €");
        }
    }
}
