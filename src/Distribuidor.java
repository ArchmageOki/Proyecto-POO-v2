package src;

public class Distribuidor extends EntidadBase {

    private String cif;
    private int distancia;
    private String direccion;
    private String codigoPostal;
    private static int count = 1;
    private int id;

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
        this.id = Distribuidor.count;
        Distribuidor.count++;

        Cooperativa.addDistribuidor(this);
    }

    /**
     * @return ID del distribuidor.
     */
    public int getId() {
        return this.id;
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
