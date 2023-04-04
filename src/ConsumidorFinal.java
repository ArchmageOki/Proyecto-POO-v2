package src;

public class ConsumidorFinal extends Persona {

    private int distancia;

    /**
     * @param nombre    El nombre de la persona.
     * @param apellido1 El primer apellido de la persona.
     * @param apellido2 El segundo apellido de la persona.
     * @param dni       El documento de identidad de la persona. Si se introduce un
     *                  DNI no válido su valor será "DNI INCORRECTO".
     * @param edad      La edad de la persona.
     * @param sexo      El sexo de la persona. Siempre es "Hombre" a no ser que se
     *                  introduzca "Mujer".
     * @param distancia La distancia en kilómetros que hay entre el consumidor final
     *                  y la cooperativa.
     */
    public ConsumidorFinal(String nombre, String apellido1, String apellido2, String dni, int edad, String sexo,
            int distancia) {
        super(nombre, apellido1, apellido2, dni, edad, sexo);
        if (distancia > 200) {
            System.out.println("El consumidor final no puede estar a más de 200km.");
            System.out.println("La distancia se ha establecido en 200km.");
            this.distancia = 200;
        } else {
            this.distancia = distancia;
        }
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
    public void comprobarPrecios(String nombreProducto, int kg) {
        if (checkCondiciones(nombreProducto, kg)) {
            double tonleladasDisponibles = Cooperativa.productoDisponible.get(nombreProducto);
            System.out.println(nombreProducto + ": " + tonleladasDisponibles + " toneladas");
            System.out.println();
            printPrecioEmpresasLogisticas(nombreProducto, kg);
        }
    }

    /**
     * @param nombreProducto Nombre del producto que se evalúa.
     * @param kg             Kilogramos de producto.
     * @return True si se dan las condiciones adecuadas. False si no se cumplen.
     */
    private boolean checkCondiciones(String nombreProducto, int kg) {
        if (!Cooperativa.nombresProductos.contains(nombreProducto)) {
            System.out.println("El producto no existe en la cooperativa.");
            return false;
        } else if (kg > 100) {
            System.out.println("No se permiten compras superiores a 100kg.");
            return false;
        } else if (kg > Cooperativa.productoDisponible.get(nombreProducto) * 1000) {
            System.out.println("No hay suficiente producto disponible.");
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
                double total = 0;
                System.out.print(empresa.getNombreEmpresa() + ": ");
                suma = empresa.precioTramoGranLogistica(this.distancia, valorPorKg, kg);
                System.out.print((double) Math.round(suma * 100) / 100);
                System.out.print(" + ");
                System.out.print(precioCooperativa);
                total = ((double) Math.round(suma * 100) / 100) + precioCooperativa;
                System.out.print(" = " + total);
                System.out.println(" euros.");
            }
        } else {
            for (EmpresaLogistica empresa : Cooperativa.empresasLogisticas) {
                double suma = 0;
                double precioCooperativa = getPrecioCooperativa(nombreProducto, kg);
                double total = 0;
                System.out.print(empresa.getNombreEmpresa() + ": ");
                suma += empresa.precioTramoGranLogistica(100, valorPorKg, kg);
                suma += empresa.precioTramoPeqLogistica(this.distancia - 100, kg);
                System.out.print((double) Math.round(suma * 100) / 100);
                System.out.print(" + ");
                System.out.print(precioCooperativa);
                total = ((double) Math.round(suma * 100) / 100) + precioCooperativa;
                System.out.print(" = " + total);
                System.out.println(" euros.");
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
                double total = 0;
                System.out.print(empresa.getNombreEmpresa() + ": ");
                suma = empresa.precioTramoGranLogistica(this.distancia, valorPorKg, kg);
                System.out.print((double) Math.round(suma * 100) / 100);
                System.out.print(" + ");
                System.out.print(precioCooperativa);
                total = ((double) Math.round(suma * 100) / 100) + precioCooperativa;
                System.out.print(" = " + total);
                System.out.println(" euros.");
            }
        } else {
            for (EmpresaLogistica empresa : Cooperativa.empresasLogisticas) {
                int distanciaAux = this.distancia;
                double suma = 0;
                double precioCooperativa = getPrecioCooperativa(nombreProducto, kg);
                double total = 0;
                System.out.print(empresa.getNombreEmpresa() + ": ");
                while (this.distancia > 50) {
                    suma += empresa.precioTramoGranLogistica(distanciaAux, valorPorKg, kg);
                    distanciaAux -= 50;
                }
                suma += empresa.precioTramoPeqLogistica(distanciaAux, kg);
                System.out.print((double) Math.round(suma * 100) / 100);
                System.out.print(" + ");
                System.out.print(precioCooperativa);
                total = ((double) Math.round(suma * 100) / 100) + precioCooperativa;
                System.out.print(" = " + total);
                System.out.println(" euros.");
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
        return valorPorKg * kg;
    }

    public void comprarProducto(String nombreProducto, int kg, EmpresaLogistica empresa) {
        if(checkCondiciones(nombreProducto, kg)) {
            Cooperativa.facturas.add(new Factura(nombreProducto, empresa, this, kg));
        }
    }
}
