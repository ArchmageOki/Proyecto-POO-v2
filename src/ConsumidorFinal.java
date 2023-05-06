package src;

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
