package src;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

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

    public static Map<String, Integer> productoDisponible = new HashMap<>(); // Está en toneladas.

    static {
        productoDisponible = new HashMap<>();
        for (String producto : nombresProductos) {
            productoDisponible.put(producto, 0);
        }
    }

    /**
     * Constructor privado. No se va a usar nunca porque la idea de la clase es que
     * todos los métodos sean static.
     */
    private Cooperativa() {
    }

    /**
     * @param args Argumentos.
     */
    public static void main(String args[]) {

        System.setProperty("console.encoding", "UTF-8");

        initMain();

        printBienvenida();
        printFechaCooperativa();
        printMenuSelector();

    }

    /**
     * Bienvenida del programa.
     */
    private static void printBienvenida() {
        System.out.println("\t~~~~~~~~Bienvenido a S. Coop. Juan Manuel Garrido~~~~~~~~");
        System.out.println();
    }

    /**
     * Imprime la fecha actual de la cooperativa.
     */
    private static void printFechaCooperativa() {
        System.out.println("\tLa fecha actual es " + Cooperativa.fechaActual.getDayOfMonth() + "/"
                + Cooperativa.fechaActual.getMonthValue() + "/" + Cooperativa.fechaActual.getYear());
        System.out.println();

    }

    /**
     * Menú selector del programa.
     */
    private static void printMenuSelector() {
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\tAcciones disponibles:");
            System.out.println();
            System.out.println("\t| 1 |\tVer productos disponibles para la venta");
            System.out.println();
            System.out.println("\t| 2 |\tDar de alta un nuevo productor");
            System.out.println("\t| 3 |\tAñadir un producto a un productor");
            System.out.println("\t| 4 |\tEliminar un producto de un productor");
            System.out.println();
            System.out.println("\t| 5 |\tComprar un producto");
            System.out.println();
            System.out.println("\t| 6 |\tDatos de análisis");
            System.out.println();
            System.out.println("\t| 7 |\tSalir");

            opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    printProductoDisponible();
                    break;
                case 2:
                    nuevoProductor(scanner);
                    break;
                case 3:
                    addProductoMenu(scanner);
                    break;
                case 4:
                    removeProductoMenu(scanner);
                    break;
                case 5:
                    menuComprarProducto(scanner);
                    break;
                case 6:
                    menuDatosAnalisis(scanner);
                    break;
                    case 7:
                    opcion = 9;
                default:
                    break;
            }

        } while (opcion != 9);

        scanner.close();
    }

    /**
     * Menú para seleccionar los datos de análisis.
     */
    private static void menuDatosAnalisis(Scanner scanner) {
        int numero;

        do {
            System.out.println("\tEscoger una opción:");
            System.out.println();
            System.out.println("\t| 1 |\tVentas de cada producto en un periodo");
            System.out.println("\t| 2 |\tGanancias de cada productor desgloasadas");
            System.out.println("\t| 3 |\tGanancias de las empresas logísticas");
            System.out.println("\t| 4 |\tBeneficios de la cooperativa desglosados");
            System.out.println("\t| 5 |\tEvolución de los precios de los productos");
            System.out.println();
            System.out.println("\t| 6 |\tResumen del año");
            System.out.println();
            System.out.println("\t| 7 |\tVolver al menú principal");
            System.out.println();

            System.out.println("\t> ");

            numero = scanner.nextInt();

            switch (numero) {
                case 1:
                    ventasProductoPeriodo(scanner);
                    break;
                case 2:
                    gananciasPorProductor(scanner);
                    break;
                case 3:
                    gananciasEmpresasLogisticas(scanner);
                    break;
                case 4:
                    beneficiosCooperativaDesglosados(scanner);
                    break;
                case 5:
                    evolucionPreciosProductos(scanner);
                    break;
                case 6:
                    resumenAnual(scanner);
                default:
                    numero = 7;
                    break;
            }

        } while (numero != 7);

        printMenuSelector();
    }

    /**
     * Imprime el resumen anual de la cooperativa.
     */
    private static void resumenAnual(Scanner scanner) {
        int numeroProductores = 0;
        int numeroCultivos = 0;
        int numeroEmpresaLogistica = 0;
        int numeroConsumidorFinal = 0;
        int numeroDistribuidores = 0;
        int numeroFacturas = 0;
        double mayorFactura = 0;
        int kgVendidos = 0;
        double dineroEmpresasLogisticas = 0.0;
        int kmRecorridos = 0;
        double mayorCultivo = 0.0;
        double dineroProductores = 0.0;
        double dineroCooperativa = (double) (Math.round(Cooperativa.dinero * 100.0) / 100.0);
        double dineroIva = 0.0;

        for (Productor p : productores) {
            numeroProductores++;
            for (Producto prod : p.getProductos()) {
                numeroCultivos++;
                if (prod.getExtension() > mayorCultivo) {
                    mayorCultivo = prod.getExtension();
                }

            }
            dineroProductores += p.getPropietario().getDinero();
        }

        numeroEmpresaLogistica = empresasLogisticas.size();
        numeroConsumidorFinal = consumidoresFinales.size();
        numeroDistribuidores = distribuidores.size();
        numeroFacturas = facturas.size();

        for (Factura f : facturas) {
            if (f.getPrecioTotal() > mayorFactura) {
                mayorFactura = f.getPrecioTotal();
            }
            kgVendidos += f.getKg();
            kmRecorridos += f.getDistancia();
            dineroIva += f.getIva();
        }

        for (EmpresaLogistica e : empresasLogisticas) {
            dineroEmpresasLogisticas += e.getDinero();
        }

        dineroProductores = (double) (Math.round(dineroProductores * 100.0) / 100.0);
        dineroIva = (double) (Math.round(dineroIva * 100.0) / 100.0);

        System.out.println("\tS. Coop. Juan Manuel Garrido - Resumen del año 2023");
        System.out.println();
        System.out.println("\tEntidades que han intervenido:");
        System.out.println("\t\tProductores: " + numeroProductores + " personas diferentes");
        System.out.println("\t\tCultivos: " + numeroCultivos + " cultivos únicos");
        System.out.println("\t\tEmpresas logísticas: " + numeroEmpresaLogistica + " empresas diferentes");
        System.out.println("\t\tConsumidores finales: " + numeroConsumidorFinal + " personas únicas");
        System.out.println("\t\tDistribuidores: " + numeroDistribuidores + " empresas únicas");
        System.out.println();
        System.out.println("\tFacturas:");
        System.out.println("\t\tFacturas: " + numeroFacturas + " compras únicas");
        System.out.println("\t\tCompra de mayor precio: " + mayorFactura + " €");
        System.out.println();
        System.out.println("\tProductos:");
        System.out.println("\t\tTotal de kg vendidos: " + kgVendidos + " kg");
        System.out.println();
        System.out.println("\tEmpresas logísticas:");
        System.out.println("\t\tDinero ganado: " + dineroEmpresasLogisticas + " €");
        System.out.println("\t\tKilómetros recorridos: " + kmRecorridos + " km");
        System.out.println();
        System.out.println("\tProductores:");
        System.out.println("\t\tCultivo de mayor extensión: " + mayorCultivo + " hectáreas");
        System.out.println("\t\tDinero total ganado: " + dineroProductores + " €");
        System.out.println();
        System.out.println("\tCooperativa:");
        System.out.println("\t\tDinero ganado: " + dineroCooperativa + " €");
        System.out.println("\t\tIVA recaudado: " + dineroIva + " €");
        System.out.println();
        
    }

    /**
     * Imprime la evolución de los precios de los productos.
     */
    private static void evolucionPreciosProductos(Scanner scanner) {
        String nombreProducto;

        System.out.print("\tElegir el nombre del producto a consultar: ");
        nombreProducto = scanner.next();
        while (!nombresProductos.contains(nombreProducto)) {
            System.out.print("\tEl producto no es válido. Introducir un producto válido: ");
            nombreProducto = scanner.next();
        }
        System.out.println();

        switch (nombreProducto) {
            case "Aceite":
                ProdAceite.printPrecioHistorico();
                break;
            case "Aceituna":
                ProdAceite.printPrecioHistorico();
                break;
            case "Avellana":
                ProdAceite.printPrecioHistorico();
                break;
            case "Avena":
                ProdAceite.printPrecioHistorico();
                break;
            case "Fresa":
                ProdAceite.printPrecioHistorico();
                break;
            case "Garbanzo":
                ProdAceite.printPrecioHistorico();
                break;
            case "Girasol":
                ProdAceite.printPrecioHistorico();
                break;
            case "Lechuga":
                ProdAceite.printPrecioHistorico();
                break;
            case "Maiz":
                ProdAceite.printPrecioHistorico();
                break;
            case "Naranja":
                ProdAceite.printPrecioHistorico();
                break;
            case "Patata":
                ProdAceite.printPrecioHistorico();
                break;
            case "Pepino":
                ProdAceite.printPrecioHistorico();
                break;
            case "Tomate":
                ProdAceite.printPrecioHistorico();
                break;
            case "Trigo":
                ProdAceite.printPrecioHistorico();
                break;
            default:
                break;
        }

    }

    /**
     * Imprime los beneficios de la empresa desglosados por productos.
     */
    private static void beneficiosCooperativaDesglosados(Scanner scanner) {
        double total = 0;

        Map<String, Double> dineroProductos = new HashMap<>();
        for (Factura f : facturas) {
            dineroProductos.merge(f.getNombreProducto(), f.getDineroCooperativa(), Double::sum);
        }

        System.out.println("\tS. Coop. Juan Manuel Garrido");
        System.out.println();
        System.out.println("\tProducto  \tBeneficio");
        System.out.println();
        for (Map.Entry<String, Double> entry : dineroProductos.entrySet()) {
            System.out.printf("\t%-10s\t%-5.2f €\n", entry.getKey(), entry.getValue());
            total += entry.getValue();
        }
        System.out.println();
        System.out.printf("\tTotal     \t%-5.2f €\n", total);
        System.out.println();

    }

    /**
     * Imprime las ganancias de las empresas logísticas.
     */
    private static void gananciasEmpresasLogisticas(Scanner scanner) {
        System.out.println("\tNombre de la empresa          \tGanancias");
        System.out.println();

        for (EmpresaLogistica e : empresasLogisticas) {
            System.out.printf("\t%-30s\t%.2f €\n", e.getNombreEmpresa(), e.getDinero());
        }
        System.out.println();
    }

    /**
     * Imprime las ganancias por productor.
     */
    private static void gananciasPorProductor(Scanner scanner) {

        for (Persona p : personas) {
            System.out.println("\t" + p.getNombre() + " " + p.getApellido1());
            double total = 0;
            for (Map.Entry<String, Double> entry : p.getVentasRealizadas().entrySet()) {
                total += (Math.round(entry.getValue() * 100.0) / 100.0);
                System.out.println(
                        "\t\t" + entry.getKey() + " = " + (Math.round(entry.getValue() * 100.0) / 100.0) + " €");
            }
            total = (double) (Math.round(total * 100.0) / 100.0);
            System.out.println();
            System.out.println("\t\tTotal = " + total + " €");
            System.out.println();
        }
    }

    /**
     * Ventas de la cooperativa en un periodo de tiempo.
     */
    private static void ventasProductoPeriodo(Scanner scanner) {
        int diaInicio;
        int mesInicio;
        LocalDate fechaInicio;
        int diaFin;
        int mesFin;
        LocalDate fechaFin;
        Map<String, Integer> ventas = new HashMap<>();

        System.out.println("\tSe mostrarán las ventas de un producto en el periodo que se introduzca a continuación.");
        System.out.println("\tSe tendrá en cuenta la FECHA DE LA ENTREGA.");
        System.out.println();

        System.out.println("\tEstablecer fecha de inicio:");
        System.out.print("\t\tDia: ");
        diaInicio = scanner.nextInt();
        System.out.print("\t\tMes: ");
        mesInicio = scanner.nextInt();
        while (!checkFechaValida(2023, mesInicio, diaInicio)) {
            System.out.println("\tLa fecha introducida es inválida. Introducir una fecha válida:");
            System.out.print("\t\tDia: ");
            diaInicio = scanner.nextInt();
            System.out.print("\t\tMes: ");
            mesInicio = scanner.nextInt();
        }
        fechaInicio = LocalDate.of(2023, mesInicio, diaInicio);
        System.out.println();

        System.out.println("\tEstablecer fecha de final:");
        System.out.print("\t\tDia: ");
        diaFin = scanner.nextInt();
        System.out.print("\t\tMes: ");
        mesFin = scanner.nextInt();
        while (!checkFechaValida(2023, mesFin, diaFin)) {
            System.out.println("\tLa fecha introducida es inválida. Introducir una fecha válida:");
            System.out.print("\t\tDia: ");
            diaFin = scanner.nextInt();
            System.out.print("\t\tMes: ");
            mesFin = scanner.nextInt();
        }
        fechaFin = LocalDate.of(2023, mesFin, diaFin);
        System.out.println();

        if (fechaInicio.isAfter(fechaFin)) {
            System.out.println("\tLa fecha de inicio es posterior a la fecha de fin.");
            System.out.println();
            menuDatosAnalisis(scanner);
        }

        for (Factura f : facturas) {
            if (f.getFechaEntrega().isAfter(fechaInicio) && f.getFechaEntrega().isBefore(fechaFin)) {
                ventas.merge(f.getNombreProducto(), f.getKg(), Integer::sum);
            }
        }

        if (ventas.isEmpty()) {
            System.out.println("\tNo hay ventas entre las fechas seleccionadas.");
            System.out.println();
            menuDatosAnalisis(scanner);
        }

        System.out.println("\tVentas entre " + fechaInicio + " y " + fechaFin + ":");
        System.out.println();
        for (Map.Entry<String, Integer> entry : ventas.entrySet()) {
            System.out.printf("\t%10s = %6d kg.\n", entry.getKey(), entry.getValue());
        }
        System.out.println();

        menuDatosAnalisis(scanner);
    }

    /**
     * Menú para comprar un producto.
     */
    private static void menuComprarProducto(Scanner scanner) {
        int numero;

        do {
            System.out.println("\tEscoger una opción:");
            System.out.println();
            System.out.println("\t| 1 |\tConsumidor final");
            System.out.println("\t| 2 |\tDistribuidor");
            System.out.println();
            System.out.println("\t| 3 |\tVolver al menú principal");

            numero = scanner.nextInt();

            switch (numero) {
                case 1:
                    menuConsumidorFinal(scanner);
                    break;
                case 2:
                    menuDistribuidor(scanner);
                    break;
                case 3:
                    printMenuSelector();
                    break;
            }
        } while (numero != 3);

    }

    /**
     * Imprime el menú de consumidor final.
     */
    private static void menuDistribuidor(Scanner scanner) {
        int numero;

        do {
            System.out.println("\tEscoger una función:");
            System.out.println();
            System.out.println("\t| 1 |\tListado de distribuidores");
            System.out.println("\t| 2 |\tNuevo distribuidor");
            System.out.println("\t| 3 |\tEscoger distribuidor");
            System.out.println();
            System.out.println("\t| 4 |\tVolver al menú principal");
            System.out.println();

            numero = scanner.nextInt();

            switch (numero) {
                case 1:
                    printDistribuidores();
                    menuDistribuidor(scanner);
                    break;
                case 2:
                    nuevoDistribuidor(scanner);
                    break;
                case 3:
                    escogerDistribuidor(scanner);
                    break;
                default:
                    numero = 4;
                    break;
            }
        } while (numero != 4);

        printMenuSelector();

    }

    /**
     * Se elige el distribuidor que va a hacer la compra.
     */
    private static void escogerDistribuidor(Scanner scanner) {
        int idDistribuidor;
        int idEmpresaLog;
        List<Integer> idDistribuidores = new ArrayList<>();
        List<Integer> idEmpresasLog = new ArrayList<>();
        Distribuidor distribuidor = null;
        String nombreProducto;
        int cantidad;
        EmpresaLogistica empresa = null;
        int dia;
        int mes;
        LocalDate fechaPedido;
        int diasParaEnvio;
        LocalDate fechaEntrega;

        printDistribuidores();

        for (Distribuidor d : Cooperativa.distribuidores) {
            idDistribuidores.add(d.getId());
        }

        System.out.print("\tElegir ID del distribuidor: ");
        idDistribuidor = scanner.nextInt();
        while (!idDistribuidores.contains(idDistribuidor)) {
            System.out.print("\tID incorrecto. Elegir un ID válido: ");
            idDistribuidor = scanner.nextInt();
        }
        for (Distribuidor d : Cooperativa.distribuidores) {
            if (idDistribuidor == d.getId()) {
                distribuidor = d;
                break;
            }
        }
        System.out.println();

        printProductoDisponible();

        System.out.print("\tProducto que va a comprar: ");
        nombreProducto = scanner.next();
        while (!Cooperativa.nombresProductos.contains(nombreProducto)) {
            System.out.print("\tNombre inválido. Introducir un nombre válido: ");
            nombreProducto = scanner.next();
        }
        System.out.println();

        System.out.print("\tCantidad que va a comprar en kg (mínimo 1000): ");
        cantidad = scanner.nextInt();
        while (cantidad < 1000) {
            System.out.print("\tCantidad inválida. Introducir una cantidad válida: ");
            cantidad = scanner.nextInt();
        }
        if (cantidad > Cooperativa.productoDisponible.get(nombreProducto)) {
            System.out.println("\tNo hay tanto producto disponible.");
            System.out.println();
            menuDistribuidor(scanner);
        }
        System.out.println();

        if (distribuidor.getDistancia() <= 100) {
            System.out.println("\tID   \tNombre de la empresa          \tGran logística");
            System.out.println();
            for (EmpresaLogistica e : Cooperativa.empresasLogisticas) {
                System.out.printf("\t%05d\t%-30s\t%4.3f €/km y kg\n", e.getId(), e.getNombreEmpresa(),
                        e.getPrecioKmGranLogistica());
                idEmpresasLog.add(e.getId());
            }
        } else {
            System.out.println("\tID   \tNombre de la empresa          \tPeq. logística\tGran logística");
            System.out.println();
            for (EmpresaLogistica e : Cooperativa.empresasLogisticas) {
                System.out.printf("\t%05d\t%-30s\t%9f €/km\t%14f\n", e.getId(), e.getNombreEmpresa(),
                        e.getPrecioKmPeqLogistica(), e.getPrecioKmGranLogistica());
                idEmpresasLog.add(e.getId());
            }
        }
        System.out.println();

        System.out.print("\tSeleccionar ID de empresa logística: ");
        idEmpresaLog = scanner.nextInt();
        while (!idEmpresasLog.contains(idEmpresaLog)) {
            System.out.print("\tID inválido. Introducir un ID válido: ");
            idEmpresaLog = scanner.nextInt();
        }

        for (EmpresaLogistica e : Cooperativa.empresasLogisticas) {
            if (e.getId() == idEmpresaLog) {
                empresa = e;
                break;
            }
        }
        System.out.println();

        System.out.println("\tFecha de la compra (dentro de 2023)");
        System.out.print("\tDía: ");
        dia = scanner.nextInt();
        System.out.print("\tMes: ");
        mes = scanner.nextInt();

        while (!checkFechaValida(2023, mes, dia)) {
            System.out.println("\tFecha incorrecta.");
            System.out.print("\tDía: ");
            dia = scanner.nextInt();
            System.out.println("\tMes: ");
            mes = scanner.nextInt();
        }
        fechaPedido = LocalDate.of(2023, mes, dia);
        System.out.println();

        System.out.print("\tDías para enviar el producto (entre 0 y 20): ");
        diasParaEnvio = scanner.nextInt();
        while (diasParaEnvio < 0 || diasParaEnvio > 20) {
            System.out.print("\tDías inválidos. Introducir entre 0 y 20: ");
            diasParaEnvio = scanner.nextInt();
        }
        fechaEntrega = fechaPedido.plusDays(diasParaEnvio);
        System.out.println();

        Factura factura = new Factura(nombreProducto, empresa, distribuidor, cantidad, fechaPedido, fechaEntrega, true);

    }

    /**
     * Crea un nuevo distribuidor.
     */
    private static void nuevoDistribuidor(Scanner scanner) {
        String nombre;
        String cif;
        int distancia;
        String direccion;
        String codigoPostal;

        System.out.print("\tNombre de la empresa: ");
        nombre = scanner.next();
        System.out.print("\tCIF: ");
        cif = scanner.next();
        System.out.print("\tDistancia hasta la cooperativa: ");
        distancia = scanner.nextInt();
        System.out.print("\tDirección (Sólo la calle y el número): ");
        direccion = scanner.next();
        System.out.print("\tCódigo postal: ");
        codigoPostal = scanner.next();
        System.out.println();

        Distribuidor distribuidor = new Distribuidor(nombre, cif, distancia, direccion, codigoPostal);

    }

    /**
     * Imprime la lista de distribuidores.
     */
    private static void printDistribuidores() {
        System.out.println("\tID   \tNombre                        \tDistancia");
        System.out.println();
        for (Distribuidor d : Cooperativa.distribuidores) {
            System.out.printf("\t%05d\t%-30s\t%-3d km\n", d.getId(), d.getNombre(),
                    d.getDistancia());
        }
        System.out.println();

    }

    /**
     * Imprime el menú de consumidor final.
     */
    private static void menuConsumidorFinal(Scanner scanner) {
        int numero;

        do {
            System.out.println("\tEscoger una función:");
            System.out.println();
            System.out.println("\t| 1 |\tListado de consumidores finales");
            System.out.println("\t| 2 |\tNuevo consumidor final");
            System.out.println("\t| 3 |\tEscoger consumidor final");
            System.out.println();
            System.out.println("\t| 4 |\tVolver al menú principal");
            System.out.println();

            numero = scanner.nextInt();

            switch (numero) {
                case 1:
                    printConsumidoresFinales();
                    menuConsumidorFinal(scanner);
                    break;
                case 2:
                    nuevoConsumidorFinal(scanner);
                    break;
                case 3:
                    escogerConsumidorFinal(scanner);
                    break;
                default:
                    numero = 4;
                    break;
            }

        } while (numero != 4);

        printMenuSelector();

    }

    /**
     * Elige un consumidor final existente para comprar un producto.
     */
    private static void escogerConsumidorFinal(Scanner scanner) {
        int idConsumidorFinal;
        int idEmpresaLog;
        List<Integer> idConsumidoresFinales = new ArrayList<>();
        List<Integer> idEmpresasLog = new ArrayList<>();
        String nombreProducto;
        int cantidad;
        ConsumidorFinal consumidor = null;
        EmpresaLogistica empresa = null;
        int dia;
        int mes;
        LocalDate fechaPedido;
        LocalDate fechaEntrega;
        int diasParaEnvio;

        printConsumidoresFinales();

        for (ConsumidorFinal c : Cooperativa.consumidoresFinales) {
            idConsumidoresFinales.add(c.getId());
        }

        System.out.print("\tElegir ID del consumidor final: ");
        idConsumidorFinal = scanner.nextInt();
        while (!idConsumidoresFinales.contains(idConsumidorFinal)) {
            System.out.print("\tID incorrecto. Elegir un ID válido: ");
            idConsumidorFinal = scanner.nextInt();
        }
        for (ConsumidorFinal c : Cooperativa.consumidoresFinales) {
            if (idConsumidorFinal == c.getId()) {
                consumidor = c;
                break;
            }
        }
        System.out.println();

        printProductoDisponible();

        System.out.print("\tProducto que va a comprar: ");
        nombreProducto = scanner.next();
        while (!Cooperativa.nombresProductos.contains(nombreProducto)) {
            System.out.print("\tNombre inválido. Introducir un nombre válido: ");
            nombreProducto = scanner.next();
        }
        System.out.println();

        System.out.print("\tCantidad que va a comprar en kg (entre 1 y 100): ");
        cantidad = scanner.nextInt();
        while (cantidad < 1 || cantidad > 100) {
            System.out.print("\tCantidad inválida. Introducir una cantidad válida: ");
            cantidad = scanner.nextInt();
        }
        if (cantidad > Cooperativa.productoDisponible.get(nombreProducto)) {
            System.out.println("\tNo hay tanto producto disponible.");
            System.out.println();
            menuConsumidorFinal(scanner);
        }
        System.out.println();

        if (consumidor.getDistancia() <= 100) {
            System.out.println("\tID   \tNombre de la empresa          \tGran logística");
            System.out.println();
            for (EmpresaLogistica e : Cooperativa.empresasLogisticas) {
                System.out.printf("\t%05d\t%-30s\t%4.3f €/km y kg\n", e.getId(), e.getNombreEmpresa(),
                        e.getPrecioKmGranLogistica());
                idEmpresasLog.add(e.getId());
            }
        } else {
            System.out.println("\tID   \tNombre de la empresa          \tPeq. logística\tGran logística");
            System.out.println();
            for (EmpresaLogistica e : Cooperativa.empresasLogisticas) {
                System.out.printf("\t%05d\t%-30s\t%9f €/km\t%4.3f €/km y kg\n", e.getId(), e.getNombreEmpresa(),
                        e.getPrecioKmPeqLogistica(), e.getPrecioKmGranLogistica());
                idEmpresasLog.add(e.getId());
            }
        }
        System.out.println();

        System.out.print("\tSeleccionar ID de empresa logística: ");
        idEmpresaLog = scanner.nextInt();
        while (!idEmpresasLog.contains(idEmpresaLog)) {
            System.out.print("\tID inválido. Introducir un ID válido: ");
            idEmpresaLog = scanner.nextInt();
        }

        for (EmpresaLogistica e : Cooperativa.empresasLogisticas) {
            if (e.getId() == idEmpresaLog) {
                empresa = e;
                break;
            }
        }
        System.out.println();

        System.out.println("\tFecha de la compra (dentro de 2023)");
        System.out.print("\tDía: ");
        dia = scanner.nextInt();
        System.out.print("\tMes: ");
        mes = scanner.nextInt();

        while (!checkFechaValida(2023, mes, dia)) {
            System.out.println("\tFecha incorrecta.");
            System.out.print("\tDía: ");
            dia = scanner.nextInt();
            System.out.println("\tMes: ");
            mes = scanner.nextInt();
        }
        fechaPedido = LocalDate.of(2023, mes, dia);
        System.out.println();

        System.out.print("\tDías para enviar el producto (entre 0 y 20): ");
        diasParaEnvio = scanner.nextInt();
        while (diasParaEnvio < 0 || diasParaEnvio > 20) {
            System.out.print("\tDías inválidos. Introducir entre 0 y 20: ");
            diasParaEnvio = scanner.nextInt();
        }
        fechaEntrega = fechaPedido.plusDays(diasParaEnvio);
        System.out.println();

        Factura factura = new Factura(nombreProducto, empresa, consumidor, cantidad, fechaPedido, fechaEntrega, true);

    }

    /**
     * @param anno Año de la fecha a comprobar.
     * @param mes  Mes de la fecha a comprobar.
     * @param dia  Dia de la fecha a comprobar.
     * @return True si la fecha es válida. False si la fecha es inválida.
     */
    private static boolean checkFechaValida(int anno, int mes, int dia) {
        LocalDate fecha;
        try {
            fecha = LocalDate.of(anno, mes, dia);
        } catch (DateTimeException e) {
            return false;
        }
        return true;
    }

    /**
     * Crea un nuevo consumidor final.
     */
    private static void nuevoConsumidorFinal(Scanner scanner) {
        String nombre;
        String apellido1;
        String apellido2;
        String dni;
        int edad;
        String sexo;
        int distancia;
        String direccion;
        String codigoPostal;

        System.out.print("\tNombre: ");
        nombre = scanner.next();
        System.out.print("\tApellido 1: ");
        apellido1 = scanner.next();
        System.out.print("\tApellido 2: ");
        apellido2 = scanner.next();
        System.out.print("\tDNI: ");
        dni = scanner.next();
        System.out.print("\tEdad: ");
        edad = scanner.nextInt();
        if (edad < 18) {
            System.out.println("\tUn menor de edad no puede estar dado de alta en la cooperativa.");
            System.out.println();
            menuConsumidorFinal(scanner);
        } else if (edad > 99) {
            System.out.println("\tLa edad no puede ser mayor que 99. Se establecerá la edad en 99.");
            edad = 99;
        }
        System.out.print("\tSexo (Hombre o Mujer): ");
        sexo = scanner.next();
        System.out.print("\tDistancia hasta la cooperativa: ");
        distancia = scanner.nextInt();
        if (distancia > 200) {
            System.out.println("\tEl consumidor final no puede estar a más de 200km.");
            System.out.println("\tLa distancia se ha establecido en 200km.");
            distancia = 200;
        }
        System.out.print("\tDirección (Sólo la calle y el número): ");
        direccion = scanner.next();
        System.out.print("\tCódigo postal: ");
        codigoPostal = scanner.next();

        ConsumidorFinal c = new ConsumidorFinal(nombre, apellido1, apellido2, dni, edad, sexo, distancia, direccion,
                codigoPostal);

    }

    /**
     * Imprime los consumidores finales.
     */
    private static void printConsumidoresFinales() {
        System.out.println("\tID   \tNombre  \tApellido\tDistancia");
        System.out.println();
        for (ConsumidorFinal c : Cooperativa.consumidoresFinales) {
            System.out.printf("\t%05d\t%-8s\t%-8s\t%-3d km\n", c.getId(), c.getNombre(), c.getApellido1(),
                    c.getDistancia());
        }
        System.out.println();
    }

    /**
     * Menú para elegir si eliminamoos un producto de un productor o de un productor
     * federado.
     */
    private static void removeProductoMenu(Scanner scanner) {
        int numero;

        do {
            System.out.println("\tEscoger una opción:");
            System.out.println();
            System.out.println("\t| 1 |\tProductor");
            System.out.println("\t| 2 |\tProductor federado");
            System.out.println();
            System.out.println("\t| 3 |\tVolver al menú principal");

            numero = scanner.nextInt();

            switch (numero) {
                case 1:
                    removeProductoProductor(scanner);
                    break;
                case 2:
                    removeProductoProductorFederado(scanner);
                    break;
                default:
                    break;
            }

        } while (numero != 3);

        printMenuSelector();

    }

    /**
     * Elimina un producto de un productor federado.
     */
    private static void removeProductoProductorFederado(Scanner scanner) {
        String nombreProducto;
        List<String> prodFedProductos = new ArrayList<>();
        ProductorFederado prodFed = null;
        int id;
        List<Integer> ids = new ArrayList<>();
        Persona persona = null;

        System.out.println("\tProducto\tSuperficie");
        System.out.println();
        for (ProductorFederado p : Cooperativa.productoresFederados) {
            System.out.printf("\t%-8s\t%.2f hectáreas", p.getNombreProducto(), p.getExtension());
            System.out.println();
            prodFedProductos.add(p.getNombreProducto());
        }
        System.out.println();

        System.out.print("\tElegir un producto: ");
        nombreProducto = scanner.next();
        while (!prodFedProductos.contains(nombreProducto)) {
            System.out.print("\tProducto incorrecto, introducir un producto válido: ");
            nombreProducto = scanner.next();
        }
        System.out.println();

        for (ProductorFederado p : Cooperativa.productoresFederados) {
            if (p.getNombreProducto().equals(nombreProducto)) {
                prodFed = p;
                break;
            }
        }

        for (Persona p : prodFed.getPropietarios()) {
            ids.add(p.getId());
        }

        System.out.println("\tID   \tNombre\tApellido \tSuperficie");
        System.out.println();
        for (Producto prod : prodFed.getProductos()) {
            System.out.printf("\t%05d\t%-6s\t%-9s\t%f hectáreas", prod.getPropietario().getId(),
                    prod.getPropietario().getNombre(), prod.getPropietario().getApellido1(), prod.getExtension());
            System.out.println();
        }

        System.out.print("\tElegir ID para eliminar: ");
        id = scanner.nextInt();
        while (!ids.contains(id)) {
            System.out.print("\tID incorrecta, elegir un ID válido: ");
            id = scanner.nextInt();
        }

        for (Persona p : prodFed.getPropietarios()) {
            if (p.getId() == id) {
                persona = p;
            }
        }

        prodFed.removeProducto(persona);

        System.out.println("\tLa persona " + id + " ya no forma parte del productor federado de " + nombreProducto);
        System.out.println("\tEl producto ha desaparecido.");
        System.out.println();

        printMenuSelector();

    }

    /**
     * Elimina un producto de un productor.
     */
    private static void removeProductoProductor(Scanner scanner) {
        int id;
        Productor productor = null;
        String nombreProducto;

        System.out.println("\tID   \tNombre  \tApellidos");
        System.out.println();
        for (Productor p : Cooperativa.productores) {
            System.out.printf("\t%05d\t%-8s\t%s %s\n", p.getId(), p.getPropietario().getNombre(),
                    p.getPropietario().getApellido1(), p.getPropietario().getApellido2());
        }

        do {
            System.out.print("\tIntroducir ID del productor: ");
            id = scanner.nextInt();
            if (!comprobarIdProductor(id)) {
                System.out.print("\tID incorrecta, introducir una ID existente: ");
                id = scanner.nextInt();
            }
        } while (!comprobarIdProductor(id));

        for (Productor p : Cooperativa.productores) {
            if (p.getId() == id) {
                productor = p;
            }
        }

        List<String> listaProductos = new ArrayList<>();

        System.out.println("\tEl productor tiene los siguentes productos:");
        for (Producto p : productor.getProductos()) {
            System.out.println("\t" + p.getNombreProducto() + " = " + p.getExtension() + " hectáreas");
            listaProductos.add(p.getNombreProducto());
        }
        System.out.println();
        System.out.print("\tSeleccionar producto a eliminar: ");
        nombreProducto = scanner.next();

        while (!listaProductos.contains(nombreProducto)) {
            System.out.print("\tProducto incorrecto, introducir un producto válido: ");
            nombreProducto = scanner.next();
        }

        productor.removeProducto(nombreProducto);

        System.out.println();
        System.out.println("\tEl productor " + id + " ya no produce " + nombreProducto + ".");
        System.out.println();

        printMenuSelector();

    }

    /**
     * Menú para elegir si añadimos un producto a un productor o a un productor
     * federado.
     */
    private static void addProductoMenu(Scanner scanner) {
        int numero;

        do {
            System.out.println("\tEscoger una opción:");
            System.out.println();
            System.out.println("\t| 1 |\tProductor");
            System.out.println("\t| 2 |\tProductor federado");
            System.out.println();
            System.out.println("\t| 3 |\tVolver al menú principal");

            numero = scanner.nextInt();

            switch (numero) {
                case 1:
                    addProductoProductor(scanner);
                    break;
                case 2:
                    addProductoProductorFederado(scanner);
                    break;
                default:
                    break;
            }

        } while (numero != 3);

        printMenuSelector();

    }

    /**
     * Añade un producto a un productor federado existente.
     */
    private static void addProductoProductorFederado(Scanner scanner) {
        String producto;
        int id;
        double extension;
        ProductorFederado prodFed = null;
        Persona persona = null;

        do {
            System.out.print("\tProducto a añadir: ");
            producto = scanner.next();
            if (!Cooperativa.nombresProductos.contains(producto)) {
                System.out.print("\tProducto inválido, elija un producto válido: ");
                producto = scanner.next();
            }
        } while (!Cooperativa.nombresProductos.contains(producto));

        for (ProductorFederado p : Cooperativa.productoresFederados) {
            if (p.getNombreProducto().equals(producto)) {
                prodFed = p;
            }
        }

        if (prodFed != null) {
            System.out.println("\tEl productor federado de " + producto + " contiene " + prodFed.getExtension()
                    + " hectáreas totales.");
            System.out.println("\tLas hectáreas totales no pueden ser superiores a " + Cooperativa.limiteExtension);
            System.out.print("\tIntroducir las hectáreas a añadir (separando los decimales con una coma): ");
            extension = scanner.nextDouble();
            System.out.println();

            if (extension + prodFed.getExtension() >= Cooperativa.limiteExtension) {
                System.out.println("\tSe ha superado el límite de la Cooperativa.");
                System.out.println("\tNo se añadirá el producto al productor federado.");
                System.out.println();
                printMenuSelector();
            } else {
                System.out.println("\tID   \tNombre  \tApellidos");
                System.out.println();
                for (Persona p : Cooperativa.personas) {
                    System.out.printf("\t%05d\t%-8s\t%s %s\n", p.getId(), p.getNombre(),
                            p.getApellido1(), p.getApellido2());
                }

                System.out.print("\tIntroducir ID de la persona: ");
                id = scanner.nextInt();
                while (!comprobarIdProductor(id)) {
                    System.out.print("\tID incorrecta, introducir una ID válida: ");
                    id = scanner.nextInt();
                }

                for (Persona p : Cooperativa.personas) {
                    if (p.getId() == id) {
                        persona = p;
                    }
                }

                prodFed.addProducto(persona, extension);
            }

        } else {
            System.out.println("\tNo existe un productor federado de " + producto);
        }

        printMenuSelector();

    }

    /**
     * Añade un producto a un productor existente.
     */
    private static void addProductoProductor(Scanner scanner) {
        String producto;
        int id;
        double extension;

        do {
            System.out.print("\tProducto a añadir: ");
            producto = scanner.next();
            if (!Cooperativa.nombresProductos.contains(producto)) {
                System.out.print("\tProducto inválido, elija un producto válido: ");
                producto = scanner.next();
            }
        } while (!Cooperativa.nombresProductos.contains(producto));

        System.out.println();

        System.out.println("\tID   \tNombre  \tApellidos");
        System.out.println();
        for (Productor p : Cooperativa.productores) {
            System.out.printf("\t%05d\t%-8s\t%s %s\n", p.getId(), p.getPropietario().getNombre(),
                    p.getPropietario().getApellido1(), p.getPropietario().getApellido2());
        }

        System.out.println();

        do {
            System.out.print("\tIntroducir ID del productor: ");
            id = scanner.nextInt();
            if (!comprobarIdProductor(id)) {
                System.out.print("\tID incorrecta, introducir una ID existente: ");
                id = scanner.nextInt();
            }
        } while (!comprobarIdProductor(id));

        System.out.print("\tIntroducir extensión (seperando los decimales con coma): ");
        extension = scanner.nextDouble();

        for (Productor p : Cooperativa.productores) {
            if (p.getId() == id) {
                p.addProducto(producto, extension);
            }
        }

        System.out.println();
        System.out.println("\tSe han añadido " + extension + " hectáreas de " + producto + " al productor " + id);
        System.out.println();

        printMenuSelector();

    }

    /**
     * @param id ID a consultar.
     * @return true si la ID existe. False si la ID no existe.
     */
    private static boolean comprobarIdProductor(int id) {
        for (Productor p : Cooperativa.productores) {
            if (p.getId() == id) {
                return true;
            }
        }
        return false;
    }

    /**
     * Dar de alta un nuevo productor.
     */
    private static void nuevoProductor(Scanner scanner) {
        int numero;

        do {
            System.out.println("\tEscoger una opción:");
            System.out.println();
            System.out.println("\t| 1 |\tProductor");
            System.out.println("\t| 2 |\tProductor federado");
            System.out.println();
            System.out.println("\t| 3 |\tVolver al menú principal");

            numero = scanner.nextInt();

            switch (numero) {
                case 1:
                    addNuevoProductor(scanner);
                    break;
                case 2:
                    addNuevoProductorFederado(scanner);
                    break;
                default:
                    break;
            }

        } while (numero != 3);

        printMenuSelector();

    }

    /**
     * Añade un nuevo productor federado.
     */
    private static void addNuevoProductorFederado(Scanner scanner) {
        String nombre;
        String apellido1;
        String apellido2;
        String dni;
        int edad;
        String sexo;
        String producto;
        double extension;
        ProductorFederado prodFed = null;

        System.out.print("\tProducto que va a cultivar: ");
        do {
            producto = scanner.next();
            if (!Cooperativa.nombresProductos.contains(producto)) {
                System.out.print("\tIntroduzca un producto válido: ");
            }

        } while (!Cooperativa.nombresProductos.contains(producto));

        for (ProductorFederado p : Cooperativa.productoresFederados) {
            if (p.getNombreProducto().equals(producto)) {
                prodFed = p;
            }
        }

        if (prodFed == null) {
            System.out.println("\tA continuación se pedirán los datos de la persona");
            System.out.println();
            System.out.print("\tNombre: ");
            nombre = scanner.next();
            System.out.print("\tPrimer apellido: ");
            apellido1 = scanner.next();
            System.out.print("\tSegundo apellido: ");
            apellido2 = scanner.next();
            System.out.print("\tDNI: ");
            dni = scanner.next();
            System.out.print("\tEdad: ");
            edad = scanner.nextInt();
            System.out.print("\tSexo (Hombre o Mujer): ");
            sexo = scanner.next();
            System.out.println();

            Persona persona = new Persona(nombre, apellido1, apellido2, dni, edad, sexo);
            System.out.print("\tTamaño del cultivo en hectáreas (separando los decimales con coma): ");
            extension = scanner.nextDouble();
            do {
                if (extension >= Cooperativa.limiteExtension) {
                    System.out.printf("\tExtensión inválida. No puede ser superior a %2.1f\n",
                            Cooperativa.limiteExtension);
                    System.out.print("\tIntroduzca una extensión válida: ");
                    extension = scanner.nextDouble();
                }
            } while (extension >= Cooperativa.limiteExtension);

            ProductorFederado productorFederado = new ProductorFederado(producto, persona, extension);

            System.out.println("\tProductor federado añadido correctamente");
            System.out.println();

            printMenuSelector();
        } else {
            System.out.println("\tYa existe un productor federado de " + producto + ".");
            System.out.println("\tNo pueden existir dos productores federados del mismo producto.");
            System.out.println();
            printMenuSelector();
        }

    }

    /**
     * Crea un productor nuevo.
     */
    private static void addNuevoProductor(Scanner scanner) {
        String nombre;
        String apellido1;
        String apellido2;
        String dni;
        int edad;
        String sexo;
        String producto;
        double extension;

        System.out.println("\tA continuación se pedirán los datos de la persona");
        System.out.println();
        System.out.print("\tNombre: ");
        nombre = scanner.next();
        System.out.print("\tPrimer apellido: ");
        apellido1 = scanner.next();
        System.out.print("\tSegundo apellido: ");
        apellido2 = scanner.next();
        System.out.print("\tDNI: ");
        dni = scanner.next();
        System.out.print("\tEdad: ");
        edad = scanner.nextInt();
        System.out.print("\tSexo (Hombre o Mujer): ");
        sexo = scanner.next();
        System.out.println();

        Persona persona = new Persona(nombre, apellido1, apellido2, dni, edad, sexo);

        System.out.print("\tProducto que va a cultivar: ");
        do {
            producto = scanner.next();
            if (!Cooperativa.nombresProductos.contains(producto)) {
                System.out.print("\tIntroduzca un producto válido: ");
            }

        } while (!Cooperativa.nombresProductos.contains(producto));

        System.out.print("\tTamaño del cultivo en hectáreas (separando los decimales con coma): ");
        extension = scanner.nextDouble();
        System.out.println();

        System.out.println();

        Productor productor = new Productor(producto, persona, extension);

        System.out.println("\tProductor añadido correctamente");
        System.out.println();

        printMenuSelector();

    }

    /**
     * Inicializa el main, añadiendo personas, productores, productores federados,
     * consumidores finales, distribuidores y empresas logísticas.
     */
    private static void initMain() {
        Random mesAleatorio = new Random();
        Random diaAleatorio = new Random();

        LocalDate fechaPrueba = LocalDate.of(2023, mesAleatorio.nextInt(12) + 1, diaAleatorio.nextInt(28) + 1);
        LocalDate fechaPrueba2 = LocalDate.of(2023, mesAleatorio.nextInt(12) + 1, diaAleatorio.nextInt(28) + 1);
        LocalDate fechaPrueba3 = LocalDate.of(2023, mesAleatorio.nextInt(12) + 1, diaAleatorio.nextInt(28) + 1);
        LocalDate fechaPrueba4 = LocalDate.of(2023, mesAleatorio.nextInt(12) + 1, diaAleatorio.nextInt(28) + 1);
        LocalDate fechaPrueba5 = LocalDate.of(2023, mesAleatorio.nextInt(12) + 1, diaAleatorio.nextInt(28) + 1);
        LocalDate fechaPrueba6 = LocalDate.of(2023, mesAleatorio.nextInt(12) + 1, diaAleatorio.nextInt(28) + 1);
        LocalDate fechaPrueba7 = LocalDate.of(2023, mesAleatorio.nextInt(12) + 1, diaAleatorio.nextInt(28) + 1);
        LocalDate fechaPrueba8 = LocalDate.of(2023, mesAleatorio.nextInt(12) + 1, diaAleatorio.nextInt(28) + 1);
        LocalDate fechaPrueba9 = LocalDate.of(2023, mesAleatorio.nextInt(12) + 1, diaAleatorio.nextInt(28) + 1);
        LocalDate fechaPrueba10 = LocalDate.of(2023, mesAleatorio.nextInt(12) + 1, diaAleatorio.nextInt(28) + 1);

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

        // Facturas
        Factura factura = new Factura("Aceite", empresa2, distribuidor5, 1200, fechaPrueba, fechaPrueba, false);
        Factura factura2 = new Factura("Lechuga", empresa1, distribuidor4, 1500, fechaPrueba2, fechaPrueba2, false);
        Factura factura3 = new Factura("Garbanzo", empresa3, distribuidor3, 2000, fechaPrueba3, fechaPrueba3, false);
        Factura factura4 = new Factura("Maiz", empresa5, distribuidor2, 1300, fechaPrueba4, fechaPrueba4, false);
        Factura factura5 = new Factura("Patata", empresa4, distribuidor, 1000, fechaPrueba5, fechaPrueba5, false);

        Factura factura6 = new Factura("Avellana", empresa1, consumidor, 35, fechaPrueba6, fechaPrueba6, false);
        Factura factura7 = new Factura("Naranja", empresa2, consumidor2, 85, fechaPrueba7, fechaPrueba7, false);
        Factura factura8 = new Factura("Pepino", empresa3, consumidor3, 40, fechaPrueba8, fechaPrueba8, false);
        Factura factura9 = new Factura("Trigo", empresa4, consumidor5, 70, fechaPrueba9, fechaPrueba9, false);
        Factura factura10 = new Factura("Fresa", empresa5, consumidor4, 15, fechaPrueba10, fechaPrueba10, false);

    }

    /**
     * Imprime el map de producto disponible.
     */
    private static void printProductoDisponible() {
        for (Map.Entry<String, Integer> map : productoDisponible.entrySet()) {
            String nombreProducto = map.getKey();
            int cantidad = map.getValue();
            System.out.printf("\t%-10s = %8d kg\n", nombreProducto, cantidad);
        }
        System.out.println();
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
     * @param consumidorFinal Añade al consumidor final a la lista de consumidores
     *                        finales.
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
        int kgActuales = productoDisponible.get(nombreProducto);
        double kgASumar = getRendimientoFromNombre(nombreProducto) * extension * 1000;
        int nuevosKg = kgActuales + (int) kgASumar;
        productoDisponible.put(nombreProducto, nuevosKg);
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
        int toneladasActuales = productoDisponible.get(nombreProducto);
        double toneladasARestar = getRendimientoFromNombre(nombreProducto) * extension * 1000;
        int nuevasToneladas = toneladasActuales - (int) toneladasARestar;
        productoDisponible.put(nombreProducto, nuevasToneladas);
    }

    /**
     * @param nombreProducto Nombre del producto.
     * @return Valor por kilogramo de producto.
     */
    public static double getPrecioPorKg(String nombreProducto, LocalDate fechaSuministro) {
        switch (nombreProducto) {
            case "Aceite":
                return ProdAceite.getValorPorKg(fechaSuministro);
            case "Aceituna":
                return ProdAceituna.getValorPorKg(fechaSuministro);
            case "Avellana":
                return ProdAvellana.getValorPorKg(fechaSuministro);
            case "Avena":
                return ProdAvena.getValorPorKg(fechaSuministro);
            case "Fresa":
                return ProdFresa.getValorPorKg(fechaSuministro);
            case "Garbanzo":
                return ProdGarbanzo.getValorPorKg(fechaSuministro);
            case "Girasol":
                return ProdGirasol.getValorPorKg(fechaSuministro);
            case "Lechuga":
                return ProdLechuga.getValorPorKg(fechaSuministro);
            case "Maiz":
                return ProdMaiz.getValorPorKg(fechaSuministro);
            case "Naranja":
                return ProdNaranja.getValorPorKg(fechaSuministro);
            case "Patata":
                return ProdPatata.getValorPorKg(fechaSuministro);
            case "Pepino":
                return ProdPepino.getValorPorKg(fechaSuministro);
            case "Tomate":
                return ProdTomate.getValorPorKg(fechaSuministro);
            case "Trigo":
                return ProdTrigo.getValorPorKg(fechaSuministro);
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
                factura.printFactura(true);
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

    /**
     * @param id ID de la factura.
     * @return Dinero que se lleva la cooperativa por la venta.
     */
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

    public static void pagarEmpresaLogistica(int id) {
        Factura factura = null;
        EmpresaLogistica empresa = null;
        for (Factura f : facturas) {
            if (f.getId() == id) {
                factura = f;
                empresa = f.getEmpresa();
                break;
            }
        }

        empresa.addDinero(factura.getDineroEmpresaLogistica());
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
                break;
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
                persona.ventasRealizadas.merge(nombreProducto, parteAPagar, Double::sum);
            }

            double beneficiosCooperativa = Cooperativa.getPrecioTotalCooperativa(id);
            Cooperativa.addDinero(beneficiosCooperativa);
        }
    }

    /**
     * Imprime el dinero de la cooperativa y debajo el nombre y dinero de cada
     * persona.
     */
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
