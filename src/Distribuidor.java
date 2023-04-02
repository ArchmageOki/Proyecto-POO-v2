package src;
public class Distribuidor extends EntidadBase {

    private String cif;
    private int distancia;

    public Distribuidor(String nombre, String cif, int distancia) {
        super();
        this.nombre = nombre;
        setCif(cif);
        this.distancia = distancia;

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
}
