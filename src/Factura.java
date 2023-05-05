package src;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Factura {

    private final String nombreProducto;
    private final EmpresaLogistica empresa;
    private List<Double> tramosGranLogistica;
    private List<Double> tramosPeqLogistica;
    private final EntidadBase comprador;
    private final int kg;
    private final double valorPorKg;
    private static int count = 4000000;
    private final int id;
    private final LocalDate fechaCompra;
    private final LocalDate fechaEntrega;
    private double dineroEmpresaLogistica;
    private double dineroCooperativa;
    private double precioTotal;
    private double distancia;
    private double iva;

    public Factura(String nombreProducto, EmpresaLogistica empresa, EntidadBase comprador, int kg,
            LocalDate fechaPedido, LocalDate fechaEntrega, boolean imprimirFactura) {

        this.nombreProducto = nombreProducto;
        this.empresa = empresa;
        this.tramosGranLogistica = new ArrayList<>();
        this.tramosPeqLogistica = new ArrayList<>();
        this.comprador = comprador;
        this.kg = kg;
        this.id = count;
        count++;
        this.fechaCompra = fechaPedido;
        this.fechaEntrega = fechaEntrega;
        this.valorPorKg = Cooperativa.getPrecioPorKg(this.nombreProducto, this.fechaEntrega);

        calcularGranLogistica();
        calcularPeqLogistica();

        printFactura(imprimirFactura);

        restarCantidadProducto();

        Cooperativa.addFactura(this);

        // Asociar los pagos que se hacen con las personas que los producen.

        Cooperativa.pagarProductores(this.id);
        Cooperativa.pagarEmpresaLogistica(this.id);
    }

    /**
     * @return IVA recaudado.
     */
    public double getIva() {
        return iva;
    }

    /**
     * @return Kilómetros recorridos.
     */
    public double getDistancia() {
        return distancia;
    }

    /**
     * @return Precio total de la factura.
     */
    public double getPrecioTotal() {
        return precioTotal;
    }

    /**
     * Resta la cantidad comprada al total de la cooperativa.
     */
    private void restarCantidadProducto() {
        Cooperativa.productoDisponible.merge(nombreProducto, -kg, Integer::sum);
    }

    /**
     * @return Dinero que se lleva la cooperativa.
     */
    public double getDineroCooperativa() {
        return dineroCooperativa;
    }

    /**
     * @return Kg que se compran.
     */
    public int getKg() {
        return kg;
    }

    /**
     * @return Fecha de la compra.
     */
    public LocalDate getFechaCompra() {
        return fechaCompra;
    }

    /**
     * @return Fecha de la entrega.
     */
    public LocalDate getFechaEntrega() {
        return fechaEntrega;
    }

    /**
     * @return Dinero que ganará la empresa logística.
     */
    public double getDineroEmpresaLogistica() {
        return this.dineroEmpresaLogistica;
    }

    /**
     * @return Lista con los tramos de gran logística.
     */
    public List<Double> getTramosGranLogistica() {
        return this.tramosGranLogistica;
    }

    /**
     * @return Lista con los tramos de pequeña logística.
     */
    public List<Double> getTramosPeqLogistica() {
        return this.tramosPeqLogistica;
    }

    /**
     * Imprime la factura. Imprimirá una factura válida si se cumplen las
     * condiciones o una inválida si no se cumplen.
     */
    public void printFactura(Boolean imprimirFactura) {
        printFacturaValida(imprimirFactura);
    }

    /**
     * @return True si se cumplen las condiciones. False si no se cumplen las
     *         condiciones.
     */
    private boolean checkCondiciones() {
        if (!Cooperativa.nombresProductos.contains(this.nombreProducto)) {
            System.out.println("El producto no existe en la cooperativa.");
            return false;
        } else if (kg > Cooperativa.productoDisponible.get(this.nombreProducto) * 1000) {
            System.out.println("No hay suficiente producto disponible.");
            return false;
        } else if (comprador instanceof ConsumidorFinal && kg > 100) {
            System.out.println("No se permiten compras superiores a 100kg para consumidores finales.");
            return false;
        } else if (comprador instanceof Distribuidor && kg < 1000) {
            System.out.println("No se permiten compras inferiores a 1000kg para distribuidores.");
            return false;
        }
        return true;
    }

    /**
     * @return Nombre del producto que se compra.
     */
    public String getNombreProducto() {
        return this.nombreProducto;
    }

    /**
     * @return Empresa que se encarga del transporte de la compra.
     */
    public EmpresaLogistica getEmpresa() {
        return this.empresa;
    }

    /**
     * @return Persona o entidad que compra el producto.
     */
    public EntidadBase getComprador() {
        return this.comprador;
    }

    /**
     * @return ID de la factura.
     */
    public int getId() {
        return this.id;
    }

    /**
     * Introduce en el ArrayList de tramos de gran logística el precio de cada uno
     * de los tramos.
     */
    private void calcularGranLogistica() {
        int distancia = 0;

        if (comprador instanceof ConsumidorFinal) {
            distancia = ((ConsumidorFinal) comprador).getDistancia();
        } else if (comprador instanceof Distribuidor) {
            distancia = ((Distribuidor) comprador).getDistancia();
        }

        if (Cooperativa.esPerecedero(this.nombreProducto) && distancia <= 100) {
            double precioTramo = empresa.precioTramoGranLogistica(distancia, this.valorPorKg, this.kg);
            double precioRedondeado = ((double) Math.round(precioTramo * 100.0) / 100.0);
            this.tramosGranLogistica.add(precioRedondeado);
        } else if (Cooperativa.esPerecedero(this.nombreProducto) && distancia > 100) {
            double precioTramo = empresa.precioTramoGranLogistica(100, this.valorPorKg, this.kg);
            double precioRedondeado = ((double) Math.round(precioTramo * 100.0) / 100.0);
            this.tramosGranLogistica.add(precioRedondeado);
        } else if (!Cooperativa.esPerecedero(this.nombreProducto) && distancia <= 100) {
            double precioTramo = empresa.precioTramoGranLogistica(distancia, this.valorPorKg, this.kg);
            double precioRedondeado = ((double) Math.round(precioTramo * 100.0) / 100.0);
            this.tramosGranLogistica.add(precioRedondeado);
        } else {
            while (distancia >= 50) {
                double precioTramo = empresa.precioTramoGranLogistica(50, this.valorPorKg, this.kg);
                double precioRedondeado = ((double) Math.round(precioTramo * 100.0) / 100.0);
                this.tramosGranLogistica.add(precioRedondeado);
                distancia -= 50;
            }
        }
    }

    /**
     * Introduce en el ArrayList de tramos de pequeña logística el precio de cada
     * uno
     * de los tramos.
     */
    private void calcularPeqLogistica() {
        int distancia = 0;

        if (comprador instanceof ConsumidorFinal) {
            distancia = ((ConsumidorFinal) comprador).getDistancia();
        } else if (comprador instanceof Distribuidor) {
            distancia = ((Distribuidor) comprador).getDistancia();
        }

        if (Cooperativa.esPerecedero(this.nombreProducto) && distancia <= 100) {
            this.tramosPeqLogistica.add(0.0);
        } else if (Cooperativa.esPerecedero(this.nombreProducto) && distancia > 100) {
            distancia -= 100;
            double precioTramo = empresa.precioTramoPeqLogistica(distancia, this.kg);
            double precioRedondeado = ((double) Math.round(precioTramo * 100) / 100);
            this.tramosPeqLogistica.add(precioRedondeado);
        } else if (!Cooperativa.esPerecedero(this.nombreProducto) && distancia <= 100) {
            this.tramosPeqLogistica.add(0.0);
        } else if (!Cooperativa.esPerecedero(this.nombreProducto) && distancia > 100) {
            while (distancia >= 50) {
                distancia -= 50;
            }
            if (distancia > 0) {
                double precioTramo = empresa.precioTramoPeqLogistica(distancia, this.kg);
                double precioRedondeado = ((double) Math.round(precioTramo * 100) / 100);
                this.tramosPeqLogistica.add(precioRedondeado);
            } else {
                this.tramosPeqLogistica.add(0.0);
            }
        }
    }

    /**
     * Imprime la factura.
     */
    public void printFacturaValida(Boolean printFactura) {
        String nombreCompleto = null;
        String id = null;
        int distancia = 0;
        String nombreEmpresa = this.empresa.getNombreEmpresa();
        double precioTotalLogistica = this.tramosGranLogistica.size() * getPrecioGranLogistica()
                + this.tramosPeqLogistica.size() * getPrecioPeqLogistica();
        double subtotal = precioTotalLogistica + getPrecioCooperativa();
        double subtotalImpuestos = ((double) Math.round(subtotal * getImpuestos()) / 100);
        double total = subtotal + subtotalImpuestos;

        if (comprador instanceof ConsumidorFinal) {
            String nombre = ((ConsumidorFinal) comprador).getNombre();
            String apellido1 = ((ConsumidorFinal) comprador).getApellido1();
            String apellido2 = ((ConsumidorFinal) comprador).getApellido2();
            nombreCompleto = nombre + " " + apellido1 + " " + apellido2;
            id = ((ConsumidorFinal) comprador).getDni();
            distancia = ((ConsumidorFinal) comprador).getDistancia();
        } else if (comprador instanceof Distribuidor) {
            nombreCompleto = ((Distribuidor) comprador).getNombre();
            id = ((Distribuidor) comprador).getCif();
            distancia = ((Distribuidor) comprador).getDistancia();
        }

        if (printFactura) {
            System.out.println();
            System.out.println("\t~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            System.out.printf("\t|     Juan Manuel Garrido S. Coop.     |\n");
            System.out.println("\t|--------------------------------------|");
            System.out.printf("\t| Numero de factura: %17d |\n", this.id);
            System.out.println("\t|--------------------------------------|");
            System.out.printf("\t| Fecha de compra: %19s |\n", this.fechaCompra.toString());
            System.out.printf("\t| Fecha de entrega: %18s |\n", this.fechaEntrega.toString());
            System.out.printf("\t| Dirección: %25s |\n", this.comprador.getDireccion());
            System.out.printf("\t| Código postal: %21s |\n", this.comprador.getCodigoPostal());
            System.out.println("\t|--------------------------------------|");
            System.out.printf("\t| Nombre: %28s |\n", nombreCompleto);
            System.out.printf("\t| ID: %32s |\n", id);
            System.out.printf("\t| Producto adquirido: %16s |\n", this.nombreProducto);
            System.out.printf("\t| Cantidad: %23d kg |\n", this.kg);
            System.out.printf("\t| Distancia: %22d km |\n", distancia);
            System.out.println("\t|--------------------------------------|");
            System.out.printf("\t| Empresa: %27s |\n", nombreEmpresa);
            System.out.println("\t|                                      |");
            System.out.printf("\t| Tramos gran logistica:               |\n");
            System.out.printf("\t| \tNumero de tramos: %3d x %3d km |\n", this.tramosGranLogistica.size(),
                    kmPorTramoGranLogistica(distancia));
            System.out.printf("\t| \tPrecio por tramo: %10.2f € |\n", getPrecioGranLogistica());
            System.out.println("\t|                                      |");
            System.out.printf("\t| Tramos peq. logistica:               |\n");
            System.out.printf("\t| \tNumero de tramos: %3d x %3d km |\n", getTramosPeqLogistica(distancia),
                    kmPorTramoPeqLogistica(distancia));
            System.out.printf("\t| \tPrecio por tramo: %10.2f € |\n", getPrecioPeqLogistica());
            System.out.println("\t|                                      |");
            System.out.printf("\t| Precio total logistica: %10.2f € |\n", precioTotalLogistica);
            System.out.println("\t|--------------------------------------|");
            System.out.printf("\t| Precio de cooperativa: %11.2f € |\n", getPrecioCooperativa());
            System.out.println("\t|--------------------------------------|");
            System.out.printf("\t| \tSubtotal: %18.2f € |\n", subtotal);
            System.out.println("\t|--------------------------------------|");
            System.out.printf("\t| Impuestos aplicables: %12.2f %% |\n", getImpuestos());
            System.out.printf("\t| \tSubtotal: %18.2f € |\n", subtotalImpuestos);
            System.out.println("\t|--------------------------------------|");
            System.out.println("\t|                                      |");
            System.out.printf("\t| TOTAL: %27.2f € |\n", total);
            System.out.println("\t|                                      |");
            System.out.println("\t~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            System.out.println();
        }

        this.dineroEmpresaLogistica = precioTotalLogistica;
        this.precioTotal = total;
        this.distancia = distancia;
        this.iva = subtotalImpuestos;
    }

    /**
     * @param distancia Distancia total del trayecto, usada para calcular cuantos
     *                  kilómetros tiene cada tramo de gran logística.
     * @return La distancia que recorre cada tramo de gran logistica.
     */
    private int kmPorTramoGranLogistica(int distancia) {
        if (Cooperativa.esPerecedero(this.nombreProducto) && distancia <= 100) {
            return distancia;
        } else if (Cooperativa.esPerecedero(this.nombreProducto) && distancia > 100) {
            return 100;
        } else if (!Cooperativa.esPerecedero(this.nombreProducto) && distancia <= 100) {
            return distancia;
        } else {
            return 50;
        }
    }

    /**
     * @param distancia Distancia total del trayecto, usada para calcular cuantos
     *                  kilómetros tiene cada tramo de pequeña logística.
     * @return La distancia que recorre cada tramo de pequeña logistica.
     */
    private int kmPorTramoPeqLogistica(int distancia) {
        if (Cooperativa.esPerecedero(this.nombreProducto) && distancia <= 100) {
            return 0;
        } else if (Cooperativa.esPerecedero(this.nombreProducto) && distancia > 100) {
            return distancia - 100;
        } else if (!Cooperativa.esPerecedero(this.nombreProducto) && distancia <= 100) {
            return 0;
        } else {
            while (distancia > 50) {
                distancia -= 50;
            }
            return distancia;
        }

    }

    /**
     * @return Precio de la pequeña logística. Si el ArrayList está vacío, devuelve
     *         0.00 € para evitar errores en las funciones.
     */
    public double getPrecioPeqLogistica() {
        if (this.tramosPeqLogistica.isEmpty()) {
            return 0.0;
        } else {
            return this.tramosPeqLogistica.get(0);
        }
    }

    /**
     * @param distancia Distancia total del trayecto, usada para calcular la
     *                  cantidad de tramos de pequeña logística.
     * @return La cantidad de tramos de pequeña logística.
     */
    private int getTramosPeqLogistica(int distancia) {
        if (Cooperativa.esPerecedero(this.nombreProducto) && distancia > 100) {
            return 1;
        } else if (!Cooperativa.esPerecedero(this.nombreProducto) && distancia > 100) {
            if (distancia % 50 == 0) {
                return 0;
            } else {
                return 1;
            }
        }
        return 0;
    }

    /**
     * @return Precio de la gran logística. Si el ArrayList está vacío, devuelve
     *         0.00 € para evitar errores en las funciones.
     */
    public double getPrecioGranLogistica() {
        if (this.tramosGranLogistica.isEmpty()) {
            return 0.0;
        } else {
            return this.tramosGranLogistica.get(0);
        }
    }

    /**
     * @return El precio que paga el comprador a la cooperativa por el producto. Si
     *         es un consumidor final, el beneficio para la cooperativa es del 15%.
     *         Si es un distribuidor, el beneficio es de un 5%.
     */
    public double getPrecioCooperativa() {
        if (comprador instanceof ConsumidorFinal) {
            dineroCooperativa = (valorPorKg * kg * 1.15) - (valorPorKg * kg);
            return valorPorKg * kg * 1.15;
        } else {
            dineroCooperativa = (valorPorKg * kg * 1.05) - (valorPorKg * kg);
            return valorPorKg * kg * 1.05;
        }
    }

    /**
     * @return El porcentaje de impuestos aplicable.
     */
    public double getImpuestos() {
        if (comprador instanceof ConsumidorFinal) {
            return 10;
        } else {
            return 0;
        }
    }

    /**
     * Factura de error.
     */
    public void printFacturaError() {
        System.out.println();
        System.out.println("\t~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.printf("\t|     Juan Manuel Garrido S. Coop.     |\n");
        System.out.println("\t|--------------------------------------|");
        System.out.printf("\t| Numero de factura: %17d |\n", this.id);
        System.out.println("\t|--------------------------------------|");
        System.out.printf("\t|          Factura no valida.          |\n");
        System.out.println("\t~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println();

        checkCondiciones();
    }

    /**
     * @param diasParaEnvio Plazo de días que solicita el cliente para que se le
     *                      entregue el producto.
     * @return Entero entre 0 y 20. El plazo no puede ser mayor a 20 días.
     */
    private int condicionesDiasEnvio(int diasParaEnvio) {
        if (diasParaEnvio > 20) {
            System.out.println(
                    "No se puede realizar un pedido con un plazo de entrega superior a 20 días. Se entregará dentro de 20 días.");
            return 20;
        } else if (diasParaEnvio < 0) {
            return 0;
        }
        return diasParaEnvio;
    }
}