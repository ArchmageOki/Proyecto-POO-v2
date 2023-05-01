package src;

public class Distribuidor extends EntidadBase {

    private String cif;
    private int distancia;
    private String direccion;
    private String codigoPostal;

    /**
     * @param nombre       Nombre de la empresa.
     * @param cif          CIF de la empresa.
     * @param distancia    Distancia entre la empresa y la cooperativa.
     * @param direccion    Dirección de entrega.
     * @param codigoPostal Código postal de la entrega.
     */
    public Distribuidor(String nombre, String cif, int distancia, String direccion, String codigoPostal) {
        super();
        this.nombre = nombre;
        setCif(cif);
        this.distancia = distancia;
        this.direccion = direccion;
        this.codigoPostal = codigoPostal;

        Cooperativa.addDistribuidor(this);
    }

    /**
     * @return String del CIF.
     */
    public String getCif() {
        return this.cif;
    }

    /**
     * @param dni Es el nuevo CIF del distribuidor. Si no es un CIF correcto, el
     *            valor de CIF pasa a ser "CIF INCORRECTO".
     */
    public void setCif(String cif) {
        if (checkCif(cif)) {
            this.cif = cif;
        } else {
            this.cif = "CIF INVÁLIDO";
        }
    }

    /**
     * @return True si el CIF es válido en España.
     */
    private boolean checkCif(String cif) {
        return cif.matches("[abcdefghjnpqrsuvwABCDEFGHJNPQRSUVW]\\d{8}");
    }

    /**
     * @return Distancia del distribuidor a la cooperativa.
     */
    public int getDistancia() {
        return this.distancia;
    }

    /**
     * @param distancia Es la nueva distancia entre el distribuidor y la
     *                  cooperativa.
     */
    public void setDistancia(int distancia) {
        this.distancia = distancia;
    }

    /**
     * @return Nombre de la empresa.
     */
    public String getNombre() {
        return this.nombre;
    }

    // Adecuar a @ConsumidorFinal

    /**
     * @param nombreProducto Nombre del producto que se evalúa.
     * @param kg             Kilogramos de producto.
     * @return True si se dan las condiciones adecuadas. False si no se cumplen.
     */
    private boolean checkCondiciones(String nombreProducto, int kg) {
        if (!Cooperativa.nombresProductos.contains(nombreProducto)) {
            System.out.println("El producto no existe en la cooperativa.");
            return false;
        } else if (kg < 1000) {
            System.out.println("No se permiten compras inferiores a 1000kg.");
            return false;
        } else if (kg > Cooperativa.productoDisponible.get(nombreProducto) * 1000) {
            System.out.println("No hay suficiente producto disponible.");
            return false;
        }
        return true;
    }

    /**
     * @param nombreProducto Producto a consultar.
     * @param kg             Kilogramos a consultar.
     */
    public void comprobarPrecios(String nombreProducto, int kg) {
        if (checkCondiciones(nombreProducto, kg)) {
            double tonleadasDisponibles = Cooperativa.productoDisponible.get(nombreProducto);
            System.out.println(nombreProducto + ": " + tonleadasDisponibles + "toneladas");
            System.out.println();
            printPrecioEmpresasLogisticas(nombreProducto, kg);
        }
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
        double resultado = valorPorKg * kg * 1.05 * 1.10;
        resultado = ((double) Math.round(resultado * 100) / 100);
        return resultado;
    }

    /**
     * @param nombreProducto Nombre del producto que se va a comprar.
     * @param kg             Cantidad que se quiere comprar.
     * @param empresa        Empresa que se va a encargar del transporte.
     */
    public void comprarProducto(String nombreProducto, int kg, EmpresaLogistica empresa, int diasParaEnvio) {
        if (checkCondiciones(nombreProducto, kg)) {
            Cooperativa.facturas.add(new Factura(nombreProducto, empresa, this, kg, diasParaEnvio));
        }
    }

    /**
     * Devuelve la dirección.
     */
    public String getDireccion() {
        return this.direccion;
    }

    /**
     * Devuelve el código postal.
     */
    public String getCodigoPostal() {
        return this.codigoPostal;
    }

}
