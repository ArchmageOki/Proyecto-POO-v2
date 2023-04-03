package src;

import javax.sound.midi.Soundbank;

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
        if (!Cooperativa.nombresProductos.contains(nombreProducto)) {
            System.out.println("El producto no existe en la cooperativa.");
        } else if (kg > 100) {
            System.out.println("No se permiten compras superiores a 100kg.");
        } else {
            double tonleladasDisponibles = Cooperativa.productoDisponible.get(nombreProducto);
            System.out.println(nombreProducto + ": " + tonleladasDisponibles + " toneladas");
            System.out.println();
            getPrecioEmpresasLogisticas(nombreProducto, kg);
        }
    }

    /**
     * @param nombreProducto Nombre del producto.
     * @return True si el producto es perecedero. False si es no perecedero.
     */
    private boolean esPerecedero(String nombreProducto) {
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
     * @param nombreProducto Nombre del producto.
     * @return Valor por kilogramo de producto.
     */
    private double getPrecioPorKg(String nombreProducto) {
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
     * @param nombreProducto Nombre del producto que se consulta.
     * @param kg             Cantidad de producto que se quiere consultar.
     */
    private void getPrecioEmpresasLogisticas(String nombreProducto, int kg) {
        if (kg > Cooperativa.productoDisponible.get(nombreProducto) * 1000) {
            System.out.println("No hay suficiente producto disponible.");
        } else if (esPerecedero(nombreProducto)) {
            getPrecioPerecedero(nombreProducto, kg);
        } else {
            getPrecioNoPerecedero(nombreProducto, kg);
        }
        System.out.println();
    }

    /**
     * @param nombreProducto El producto que se está consultando.
     * @param kg             La cantidad que se quiere.
     */
    private void getPrecioPerecedero(String nombreProducto, int kg) {
        double valorPorKg = getPrecioPorKg(nombreProducto);
        if (this.distancia < 100) {
            for (EmpresaLogistica empresa : Cooperativa.empresasLogisticas) {
                double suma = 0;
                System.out.print(empresa.getNombreEmpresa() + ": ");
                suma = empresa.precioTramoGranLogistica(this.distancia, valorPorKg, kg);
                System.out.print((double) Math.round(suma * 100) / 100);
                System.out.println(" euros.");
            }
        } else {
            for (EmpresaLogistica empresa : Cooperativa.empresasLogisticas) {
                double suma = 0;
                System.out.print(empresa.getNombreEmpresa() + ": ");
                suma += empresa.precioTramoGranLogistica(100, valorPorKg, kg);
                suma += empresa.precioTramoPeqLogistica(this.distancia - 100, kg);
                System.out.print((double) Math.round(suma * 100) / 100);
                System.out.println(" euros.");
            }
        }
    }

    /**
     * @param nombreProducto El producto que se está consultando.
     * @param kg             La cantidad que se quiere.
     */
    private void getPrecioNoPerecedero(String nombreProducto, int kg) {
        double valorPorKg = getPrecioPorKg(nombreProducto);
        if (this.distancia < 100) {
            for (EmpresaLogistica empresa : Cooperativa.empresasLogisticas) {
                double suma = 0;
                System.out.print(empresa.getNombreEmpresa() + ": ");
                suma = empresa.precioTramoGranLogistica(this.distancia, valorPorKg, kg);
                System.out.print((double) Math.round(suma * 100) / 100);
                System.out.println(" euros.");
            }
        } else {
            for (EmpresaLogistica empresa : Cooperativa.empresasLogisticas) {
                int distanciaAux = this.distancia;
                double suma = 0;
                System.out.print(empresa.getNombreEmpresa() + ": ");
                while (this.distancia > 50) {
                    suma += empresa.precioTramoGranLogistica(distanciaAux, valorPorKg, kg);
                    distanciaAux -= 50;
                }
                suma += empresa.precioTramoPeqLogistica(distanciaAux, kg);
                System.out.print((double) Math.round(suma * 100) / 100);
                System.out.println(" euros.");
            }
        }
    }
}
