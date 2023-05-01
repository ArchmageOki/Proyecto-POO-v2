package src;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Cooperativa {

    private static double dinero;

    public static double limiteExtension = 5.0;

    public static LocalDate fechaActual = LocalDate.of(2023, 1, 1);

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

    public static void main(String args[]) {

        // Inicializamos algunos objetos para no tener que crearlos a mano durante la
        // ejecución del programa. Se randomizan ciertos valores para que cada vez que
        // se ejecute el programa tenga valores diferentes.

        // Personas.
        Persona persona = new Persona("Juanma", "Garrido", "Aguilera", "73433769J", 27, "Hombre");
        Persona persona2 = new Persona("Lidia", "Pedrosa", "Fernández", "73138004M", 28, "Mujer");
        Persona persona3 = new Persona("Marisa", "Cabanillas", "Álvarez", "43985964M", 44, "Mujer");
        Persona persona4 = new Persona("José Carlos", "Gibert", "Cabañas", "87706754A", 37, "Hombre");
        Persona persona5 = new Persona("Fabiola", "Marcos", "Jáuregui", "25012816V", 55, "Mujer");
        Persona persona6 = new Persona("Jessica", "Berrocal", "Román", "28384023E", 40, "Mujer");
        Persona persona7 = new Persona("Leopoldo", "Figuerola", "Amores", "96489463R", 67, "Hombre");
        Persona persona8 = new Persona("Celso", "Peñas", "Rosell", "48462872D", 24, "Hombre");
        Persona persona9 = new Persona("Julián", "Matas", "Montoya", "16264581Q", 35, "Hombre");
        Persona persona10 = new Persona("Tamara", "Miralles", "Montserrat", "90305900B", 57, "Mujer");

        // Productores.
        // Valores que van a determinar el tamaño máximo y mínimo que puede alcanzar un
        // cultivo. Por defecto son 3.3 y 0.3.
        final double MAXEXTENSION = 3.3;
        final double MINEXTENSION = 0.3;

        Productor productor = new Productor("Lechuga", persona,
                numeroAleatorio(MAXEXTENSION, MINEXTENSION));
        productor.addProducto("Tomate", numeroAleatorio(MAXEXTENSION, MINEXTENSION));
        productor.addProducto("Garbanzo", numeroAleatorio(MAXEXTENSION, MINEXTENSION));
        Productor productor2 = new Productor("Avellana", persona2,
                numeroAleatorio(MAXEXTENSION, MINEXTENSION));
        productor2.addProducto("Aceite", numeroAleatorio(MAXEXTENSION, MINEXTENSION));
        productor2.addProducto("Patata", numeroAleatorio(MAXEXTENSION, MINEXTENSION));
        Productor productor3 = new Productor("Fresa", persona3,
                numeroAleatorio(MAXEXTENSION, MINEXTENSION));
        productor3.addProducto("Garbanzo", numeroAleatorio(MAXEXTENSION, MINEXTENSION));
        productor3.addProducto("Aceituna", numeroAleatorio(MAXEXTENSION, MINEXTENSION));
        Productor productor4 = new Productor("Garbanzo", persona4,
                numeroAleatorio(MAXEXTENSION, MINEXTENSION));
        productor4.addProducto("Avena", numeroAleatorio(MAXEXTENSION, MINEXTENSION));
        productor4.addProducto("Maiz", numeroAleatorio(MAXEXTENSION, MINEXTENSION));
        Productor productor5 = new Productor("Trigo", persona5,
                numeroAleatorio(MAXEXTENSION, MINEXTENSION));
        productor5.addProducto("Lechuga", numeroAleatorio(MAXEXTENSION, MINEXTENSION));
        productor5.addProducto("Fresa", numeroAleatorio(MAXEXTENSION, MINEXTENSION));
        Productor productor6 = new Productor("Avena", persona6,
                numeroAleatorio(MAXEXTENSION, MINEXTENSION));
        productor6.addProducto("Lechuga", numeroAleatorio(MAXEXTENSION, MINEXTENSION));
        productor6.addProducto("Naranja", numeroAleatorio(MAXEXTENSION, MINEXTENSION));
        Productor productor7 = new Productor("Patata", persona7,
                numeroAleatorio(MAXEXTENSION, MINEXTENSION));
        productor7.addProducto("Aceituna", numeroAleatorio(MAXEXTENSION, MINEXTENSION));
        productor7.addProducto("Trigo", numeroAleatorio(MAXEXTENSION, MINEXTENSION));
        Productor productor8 = new Productor("Aceituna", persona8,
                numeroAleatorio(MAXEXTENSION, MINEXTENSION));
        productor8.addProducto("Maiz", numeroAleatorio(MAXEXTENSION, MINEXTENSION));
        productor8.addProducto("Avena", numeroAleatorio(MAXEXTENSION, MINEXTENSION));
        Productor productor9 = new Productor("Naranja", persona9,
                numeroAleatorio(MAXEXTENSION, MINEXTENSION));
        productor9.addProducto("Fresa", numeroAleatorio(MAXEXTENSION, MINEXTENSION));
        productor9.addProducto("Maiz", numeroAleatorio(MAXEXTENSION, MINEXTENSION));
        Productor productor10 = new Productor("Girasol", persona10,
                numeroAleatorio(MAXEXTENSION, MINEXTENSION));
        productor10.addProducto("Tomate", numeroAleatorio(MAXEXTENSION, MINEXTENSION));
        productor10.addProducto("Naranja", numeroAleatorio(MAXEXTENSION, MINEXTENSION));

        // Productores federados.
        ProductorFederado productorFederado = new ProductorFederado("Girasol", persona, 0.4);
        productorFederado.addProducto(persona9, 1.7);
        productorFederado.addProducto(persona7, 2);
        ProductorFederado productorFederado2 = new ProductorFederado("Tomate", persona6, 1.5);
        productorFederado2.addProducto(persona3, 1.2);
        productorFederado2.addProducto(persona2, 2.2);
        ProductorFederado productorFederado3 = new ProductorFederado("Aceite", persona4, 1.2);
        productorFederado3.addProducto(persona10, 1.6);
        productorFederado3.addProducto(persona7, 1.4);
        ProductorFederado productorFederado4 = new ProductorFederado("Trigo", persona3, 0.9);
        productorFederado4.addProducto(persona8, 1.9);
        productorFederado4.addProducto(persona, 1.6);
        ProductorFederado productorFederado5 = new ProductorFederado("Avellana", persona5, 2.1);
        productorFederado5.addProducto(persona10, 1.2);
        productorFederado5.addProducto(persona9, 0.7);
        ProductorFederado productorFederado6 = new ProductorFederado("Pepino", persona2, 3.1);
        productorFederado6.addProducto(persona4, 1.0);
        productorFederado6.addProducto(persona5, 0.3);

        // Consumidores finales.
        ConsumidorFinal consumidor = new ConsumidorFinal("Rómulo", "Almansa", "Ponce", "79072650F", 35, "Hombre", 155,
                "Calle Cedillo 3", "31857");
        ConsumidorFinal consumidor2 = new ConsumidorFinal("Ismael", "Tejero", "Casanova", "56083976V", 56, "Hombre",
                45, "Calle Acebo 17", "31464");
        ConsumidorFinal consumidor3 = new ConsumidorFinal("Bárbara", "Taboada", "Baró", "11214157R", 68, "Mujer",
                70, "Avenida Peñalara 1", "31128");
        ConsumidorFinal consumidor4 = new ConsumidorFinal("Olivia", "Bonilla", "Antúnez", "22211904E", 43, "Mujer",
                130, "Plaza Miravalles 3", "31852");
        ConsumidorFinal consumidor5 = new ConsumidorFinal("Rodrigo", "Velázquez", "Collado", "88374109J", 48, "Hombre",
                130, "Calle la Salle 45", "31826");

        // Distribuidores.
        Distribuidor distribuidor = new Distribuidor("María e hijos", "A22748339", 185, "Travesía del Pardo 6",
                "31215");
        Distribuidor distribuidor2 = new Distribuidor("Frutas Gómez SL", "A70097795", 195, "Calle Bolarque 93",
                "31672");
        Distribuidor distribuidor3 = new Distribuidor("Alimentación Sheila", "A33859521", 70, "Paseo Braille 8",
                "31102");
        Distribuidor distribuidor4 = new Distribuidor("Supermercado sostenible", "A30629349", 25,
                "Calle San Rafael 4", "31265");
        Distribuidor distribuidor5 = new Distribuidor("Casa Ramírez", "A02390532", 40, "Calle Guzmán", "31942");

        // Empresas logísticas.
        EmpresaLogistica empresa1 = new EmpresaLogistica("Transportes Paco S.L.", 0.043, 0.02);
        EmpresaLogistica empresa2 = new EmpresaLogistica("García y Hnos. S.L.", 0.051, 0.012);
        EmpresaLogistica empresa3 = new EmpresaLogistica("La Navarra transportes S.L.", 0.055, 0.015);
        EmpresaLogistica empresa4 = new EmpresaLogistica("Carioca trailers S.A.", 0.061, 0.009);
        EmpresaLogistica empresa5 = new EmpresaLogistica("Ángel y asociados S.L.", 0.048, 0.018);

        // consumidor.comprarProducto("Lechuga", 20, empresa3, 7);
        // distribuidor.comprarProducto("Maiz", 1200, empresa5, 2);
        // Cooperativa.printPersonasYDinero();

        System.out.println("Bienvenido a S. Coop. Juan Manuel Garrido");
        // Escribir el menú

    }

    /**
     * @param MAXEXTENSION Extensión máxima para generar.
     * @param MINEXTENSION Extensión mínima para generar.
     * @return Número aleatorio para la extensión de los cultivos.
     */
    private static double numeroAleatorio(double MAXEXTENSION, double MINEXTENSION) {
        Random r = new Random();
        return Math.round((r.nextDouble() * MAXEXTENSION + MINEXTENSION) * 10) / 10.0;
    }

    /**
     * @return El dinero que ha ganado la cooperativa.
     */
    public static double getDinero() {
        return Cooperativa.dinero;
    }

    /**
     * @param dinero Dinero que se añade a la cooperativa. Debe ser >= 0.
     */
    public static void addDinero(double dinero) {
        if (dinero >= 0) {
            Cooperativa.dinero += dinero;
        }
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
     * @return Cantidad de dinero que reciben los productores.
     */
    public static double getPrecioTotalProductores(int id) {
        for (Factura factura : facturas) {
            if (factura.getId() == id) {
                double precioTotalCooperativa = factura.getPrecioCooperativa();
                if (factura.getComprador() instanceof ConsumidorFinal) {
                    return precioTotalCooperativa / 1.15;
                } else {
                    return precioTotalCooperativa / 1.05;
                }
            }
        }
        return 0;
    }

    public static double getPrecioTotalCooperativa(int id) {
        for (Factura factura : facturas) {
            if (factura.getId() == id) {
                double precioTotalCooperativa = factura.getPrecioCooperativa();
                if (factura.getComprador() instanceof ConsumidorFinal) {
                    return precioTotalCooperativa - (precioTotalCooperativa / 1.15);
                } else {
                    return precioTotalCooperativa - (precioTotalCooperativa / 1.05);
                }
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
                        + Cooperativa.getPrecioTotalProductores(id)) * factura.getImpuestos() / 100;
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
                        + Cooperativa.getPrecioTotalProductores(id)
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

            double dineroARepartir = Cooperativa.getPrecioTotalProductores(id);
            for (Persona persona : pagos.keySet()) {
                double hectareasPersona = pagos.get(persona);
                double parteAPagar = (hectareasPersona * dineroARepartir) / hectareasTotales;
                persona.addDinero(parteAPagar);
            }

            double beneficiosCooperativa = Cooperativa.getPrecioTotalCooperativa(id);
            Cooperativa.addDinero(beneficiosCooperativa);
        }
    }

    public static void printPersonasYDinero() {
        double dineroCooperativa = Cooperativa.getDinero();
        dineroCooperativa = (double) Math.round(dineroCooperativa * 100) / 100;
        System.out.println("Cooperativa = " + dineroCooperativa + " €");
        for (Persona persona : personas) {
            String nombre = persona.getNombre();
            String apellido1 = persona.getApellido1();
            double dinero = persona.getDinero();
            System.out.println(nombre + " " + apellido1 + " = " + dinero + " €");
        }
    }

}
