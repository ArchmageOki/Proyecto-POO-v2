package src;
/**
 * Clase base para crear entidades futuras que interactúan con la cooperativa.
 */
public abstract class EntidadBase {
    
    protected String nombre;

    public EntidadBase() {

    }

    public abstract String getDireccion();
    public abstract String getCodigoPostal();
}
