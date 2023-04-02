package src;
public abstract class Producto {

    private final String nombreProducto;
    private final Persona propietario;
    private final boolean perecedero;
    private double extension;

    /**
     * 
     * @param nombreProducto Producto que se va a cultivar.
     * @param persona        Propietario del cultivo.
     * @param perecedero     Define si se trata de un producto perecedero o no
     *                       perecedero.
     * @param extension      Superficie total del cultivo.
     */
    public Producto(String nombreProducto, Persona persona, boolean perecedero, double extension) {
        this.nombreProducto = nombreProducto;
        this.propietario = persona;
        this.perecedero = perecedero;
        this.extension = extension;
    }

    /**
     * @return String de nombre.
     */
    public String getNombreProducto() {
        return this.nombreProducto;
    }

    /**
     * @return el propietario como objeto de Persona.
     */
    public Persona getPropietario() {
        return this.propietario;
    }

    /**
     * @return true si el producto es perecedero, false si no lo es.
     */
    public boolean getPerecedero() {
        return this.perecedero;
    }

    /**
     * @return la extensión del cultivo en hectáreas.
     */
    public double getExtension() {
        return this.extension;
    }

    /**
     * @param extension es la extensión del producto en hectáreas. Debe ser mayor
     *                  que 0.
     */
    public void setExtension(double extension) {
        this.extension = extension;
    }

    /**
     * Método base para obtener la cantidad disponible de cada producto.
     */
    public abstract double getToneladasDisponibles();

}
