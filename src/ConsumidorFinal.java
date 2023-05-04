package src;

import java.time.LocalDate;

public class ConsumidorFinal extends Persona {

    private int distancia;
    private String direccion;
    private String codigoPostal;

    /**
     * @param nombre       El nombre de la persona.
     * @param apellido1    El primer apellido de la persona.
     * @param apellido2    El segundo apellido de la persona.
     * @param dni          El documento de identidad de la persona. Si se introduce
     *                     un DNI no válido su valor será "DNI INCORRECTO".
     * @param edad         La edad de la persona.
     * @param sexo         El sexo de la persona. Siempre es "Hombre" a no ser que
     *                     se introduzca "Mujer".
     * @param distancia    La distancia en kilómetros que hay entre el consumidor
     *                     final y la cooperativa.
     * @param direccion    La dirección donde se entregan los productos.
     * @param codigoPostal El código postal donde se va a mandar.
     */
    public ConsumidorFinal(String nombre, String apellido1, String apellido2, String dni, int edad, String sexo,
            int distancia, String direccion, String codigoPostal) {
        super(nombre, apellido1, apellido2, dni, edad, sexo);
        this.distancia = distancia;
        this.direccion = direccion;
        this.codigoPostal = codigoPostal;
    }

    /**
     * @return Distancia del consumidor final a la cooperativa.
     */
    public int getDistancia() {
        return this.distancia;
    }

    /**
     * @param distancia Es la nueva distancia entre el consumidor final y la
     *                  cooperativa.
     */
    public void setDistancia(int distancia) {
        this.distancia = distancia;
    }

    /**
     * Añade a la persona a la lista de consumidores finales de la cooperativa. Se
     * sobreescribe para que la persona sea añadida a la lista de consumidores
     * finales.
     */
    @Override
    protected void addToCooperativa() {
        Cooperativa.addConsumidorFinal(this);
    }

    /**
     * @param nombreProducto Producto a consultar.
     * @param kg             Kilogramos a consultar.
     */
    public void comprobarPrecios(String nombreProducto, int kg, LocalDate fechaCompra) {
        if (checkCondiciones(nombreProducto, kg, fechaCompra)) {
            double toneladasDisponibles = Cooperativa.productoDisponible.get(nombreProducto);
            System.out.println(nombreProducto + ": " + toneladasDisponibles + " toneladas");
            System.out.println();
            printPrecioEmpresasLogisticas(nombreProducto, kg);
        }
    }

    /**
     * @param nombreProducto Nombre del producto que se evalúa.
     * @param kg             Kilogramos de producto.
     * @return True si se dan las condiciones adecuadas. False si no se cumplen.
     */
    private boolean checkCondiciones(String nombreProducto, int kg, LocalDate fechaCompra) {
        LocalDate fecha1 = LocalDate.of(2023, 1, 1);
        LocalDate fecha2 = LocalDate.of(2023, 12, 31);

        if (!Cooperativa.nombresProductos.contains(nombreProducto)) {
            System.out.println("El producto no existe en la cooperativa.");
            return false;
        } else if (kg > 100) {
            System.out.println("No se permiten compras superiores a 100kg.");
            return false;
        } else if (kg > Cooperativa.productoDisponible.get(nombreProducto) * 1000) {
            System.out.println("No hay suficiente producto disponible.");
            return false;
        } else if(fechaCompra.isBefore(fecha1) || fechaCompra.isAfter(fecha2)) {
            System.out.println("La fecha indicada no pertenece al año 2023");
            return false;
        }
        return true;
    }

    /**
     * @param nombreProducto Nombre del producto que se consulta.
     * @param kg             Cantidad de producto que se quiere consultar.
     */
    private void printPrecioEmpresasLogisticas(String nombreProducto, int kg) {
        if (Cooperativa.esPerecedero(nombreProducto)) {
            printPrecioPerecedero(nombreProducto, kg);
        } else {
            printPrecioNoPerecedero(nombreProducto, kg);
        }
        System.out.println();
    }

    /**
     * @param nombreProducto El producto que se está consultando.
     * @param kg             La cantidad que se quiere.
     */
    private void printPrecioPerecedero(String nombreProducto, int kg) {
        double valorPorKg = Cooperativa.getPrecioPorKg(nombreProducto);
        if (this.distancia <= 100) {
            for (EmpresaLogistica empresa : Cooperativa.empresasLogisticas) {
                double suma = 0;
                double precioCooperativa = getPrecioCooperativa(nombreProducto, kg);
                double subtotal = 0;
                double total = 0;
                double impuestos = 0;
                System.out.print(empresa.getNombreEmpresa() + ": ");
                suma = empresa.precioTramoGranLogistica(this.distancia, valorPorKg, kg);
                suma = ((double) Math.round(suma * 100) / 100);
                System.out.printf("%.2f (logística)", suma);
                System.out.print(" + ");
                System.out.printf("%.2f (cooperativa)", precioCooperativa);
                System.out.print(" + ");
                impuestos = suma + precioCooperativa;
                impuestos = ((double) Math.round(impuestos * 100) / 100);
                System.out.printf("%.2f (impuestos)", impuestos);
                subtotal = ((double) Math.round(suma * 100) / 100) + precioCooperativa;
                total = subtotal * 1.10;
                total = ((double) Math.round(total * 100) / 100);
                System.out.print(" = " + total);
                System.out.println(" €");
            }
        } else {
            for (EmpresaLogistica empresa : Cooperativa.empresasLogisticas) {
                double suma = 0;
                double precioCooperativa = getPrecioCooperativa(nombreProducto, kg);
                double subtotal = 0;
                double total = 0;
                double impuestos = 0;
                System.out.print(empresa.getNombreEmpresa() + ": ");
                suma += empresa.precioTramoGranLogistica(100, valorPorKg, kg);
                suma += empresa.precioTramoPeqLogistica(this.distancia - 100, kg);
                suma = ((double) Math.round(suma * 100) / 100);
                System.out.printf("%.2f (logística)", suma);
                System.out.print(" + ");
                System.out.printf("%.2f (cooperativa)", precioCooperativa);
                System.out.print(" + ");
                impuestos = (suma + precioCooperativa) * 0.10;
                impuestos = ((double) Math.round(impuestos * 100) / 100);
                System.out.printf("%.2f (impuestos)", impuestos);
                subtotal = ((double) Math.round(suma * 100) / 100) + precioCooperativa;
                total = subtotal * 1.10;
                total = ((double) Math.round(total * 100) / 100);
                System.out.printf(" = %.2f", total);
                System.out.println(" €");
            }
        }
    }

    /**
     * @param nombreProducto El producto que se está consultando.
     * @param kg             La cantidad que se quiere.
     */
    private void printPrecioNoPerecedero(String nombreProducto, int kg) {
        double valorPorKg = Cooperativa.getPrecioPorKg(nombreProducto);
        if (this.distancia <= 100) {
            for (EmpresaLogistica empresa : Cooperativa.empresasLogisticas) {
                double suma = 0;
                double precioCooperativa = getPrecioCooperativa(nombreProducto, kg);
                double subtotal = 0;
                double total = 0;
                double impuestos = 0;
                System.out.print(empresa.getNombreEmpresa() + ": ");
                suma = empresa.precioTramoGranLogistica(this.distancia, valorPorKg, kg);
                suma = ((double) Math.round(suma * 100) / 100);
                System.out.printf("%.2f (logística)", suma);
                System.out.print(" + ");
                System.out.printf("%.2f (cooperativa)", precioCooperativa);
                System.out.print(" + ");
                impuestos = suma + precioCooperativa;
                impuestos = ((double) Math.round(impuestos * 100) / 100);
                System.out.printf("%.2f (impuestos)", impuestos);
                subtotal = ((double) Math.round(suma * 100) / 100) + precioCooperativa;
                total = subtotal * 1.10;
                total = ((double) Math.round(total * 100) / 100);
                System.out.printf(" = %.2f", total);
                System.out.println(" €");
            }
        } else {
            for (EmpresaLogistica empresa : Cooperativa.empresasLogisticas) {
                int distanciaAux = this.distancia;
                double suma = 0;
                double precioCooperativa = getPrecioCooperativa(nombreProducto, kg);
                double subtotal = 0;
                double total = 0;
                double impuestos = 0;
                System.out.print(empresa.getNombreEmpresa() + ": ");
                while (this.distancia > 50) {
                    suma += empresa.precioTramoGranLogistica(distanciaAux, valorPorKg, kg);
                    distanciaAux -= 50;
                }
                suma += empresa.precioTramoPeqLogistica(distanciaAux, kg);
                suma = ((double) Math.round(suma * 100) / 100);
                System.out.printf("%.2f (logística)", suma);
                System.out.print(" + ");
                System.out.printf("%.2f (cooperativa)", precioCooperativa);
                System.out.print(" + ");
                impuestos = (suma + precioCooperativa) * 0.10;
                impuestos = ((double) Math.round(impuestos * 100) / 100);
                System.out.printf("%.2f (impuestos)", impuestos);
                subtotal = ((double) Math.round(suma * 100) / 100) + precioCooperativa;
                total = subtotal * 1.10;
                total = ((double) Math.round(total * 100) / 100);
                System.out.printf(" = %.2f", total);
                System.out.println(" €");
            }
        }
    }

    /**
     * @param nombreProducto Nombre del producto.
     * @param kg             Kilogramos de producto.
     * @return Precio que se paga a la cooperativa por el producto.
     */
    private double getPrecioCooperativa(String nombreProducto, int kg) {
        double valorPorKg = Cooperativa.getPrecioPorKg(nombreProducto);
        double resultado = valorPorKg * kg * 1.15;
        resultado = ((double) Math.round(resultado * 100) / 100);
        return resultado;
    }

    /**
     * No hace nada.
     */
    @Override
    public void printDinero() {

    }

    /**
     * No hace nada.
     */
    @Override
    public void addDinero(double dinero) {

    }

    /**
     * Devuelve la dirección.
     */
    @Override
    public String getDireccion() {
        return this.direccion;
    }

    /**
     * Devuelve el código postal.
     */
    @Override
    public String getCodigoPostal() {
        return this.codigoPostal;
    }
}
