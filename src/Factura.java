package src;

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
    private static int count = 1000;
    private final int id;

    public Factura(String nombreProducto, EmpresaLogistica empresa, EntidadBase comprador, int kg) {
        // Aquí se deberían calcular las condiciones correctas para generar una factura.
        this.nombreProducto = nombreProducto;
        this.empresa = empresa;
        this.tramosGranLogistica = new ArrayList<>();
        this.tramosPeqLogistica = new ArrayList<>();
        this.comprador = comprador;
        this.kg = kg;
        this.valorPorKg = Cooperativa.getPrecioPorKg(this.nombreProducto);
        this.id = count;
        count++;

        calcularGranLogistica();
        // calcularPeqLogistica();
        printFactura();
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
     * Introduce en el ArrayList de tramos de gran logística el precio de cada uno de los tramos.
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
            while(distancia > 50) {
                double precioTramo = empresa.precioTramoGranLogistica(distancia, this.valorPorKg, this.kg);
                double precioRedondeado = ((double) Math.round(precioTramo * 100.0) / 100.0);
                this.tramosGranLogistica.add(precioRedondeado);
                distancia -= 50;
            }
        }
    }

    /**
     * Imprime la factura.
     */
    public void printFactura() {
        String nombreCompleto = null;
        String id = null;
        int distancia = 0;
        String nombreEmpresa = this.empresa.getNombreEmpresa();
        if (comprador instanceof ConsumidorFinal) {
            String nombre = ((ConsumidorFinal) comprador).getNombre();
            String apellido1 = ((ConsumidorFinal) comprador).getApellido1();
            String apellido2 = ((ConsumidorFinal) comprador).getApellido2();
            nombreCompleto = nombre + " " + apellido1 + " " + apellido2;
            id = ((ConsumidorFinal) comprador).getDni();
            distancia = ((ConsumidorFinal) comprador).getDistancia();
        } else if (comprador instanceof Distribuidor) {
            // Pendiente
        }
        System.out.println();
        System.out.println("----------------------------------------");
        System.out.printf("|     Juan Manuel Garrido S. Coop.     |\n");
        System.out.println("----------------------------------------");
        System.out.printf("| Nombre: %28s |\n", nombreCompleto);
        System.out.printf("| ID: %32s |\n", id);
        System.out.printf("| Producto adquirido: %16s |\n", this.nombreProducto);
        System.out.printf("| Cantidad: %23d kg |\n", this.kg);
        System.out.printf("| Distancia: %22d km |\n", distancia);
        System.out.println("----------------------------------------");
        System.out.printf("| Transportista: %20s |\n", nombreEmpresa);
        System.out.printf("| Tramos gran logistica:               |\n");
        System.out.printf("| \tNumero de tramos: %12d |\n", this.tramosGranLogistica.size());
        System.out.printf("| \tPrecio por tramo: %10.2f € |\n", this.tramosGranLogistica.get(0));
        System.out.printf("| \t |\n");
        // Seguir aquí.
    }
}