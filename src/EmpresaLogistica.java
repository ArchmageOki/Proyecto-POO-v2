package src;

public class EmpresaLogistica {

    private String nombreEmpresa;
    private double precioKmGranLogistica;
    private double precioKmPeqLogistica;
    private static int contador = 1;
    private int id;
    private double dinero;

    /**
     * @param nombreEmpresa         Nombre de la empresa logística.
     * @param precioKmGranLogistica Precio por kilómetro de gran logística.
     * @param precioKmPeqLogistica  Precio por kilómetro de pequeña logística.
     */
    public EmpresaLogistica(String nombreEmpresa, double precioKmGranLogistica, double precioKmPeqLogistica) {
        this.nombreEmpresa = nombreEmpresa;
        this.precioKmGranLogistica = precioKmGranLogistica;
        this.precioKmPeqLogistica = precioKmPeqLogistica;
        this.id = contador;
        EmpresaLogistica.contador++;

        Cooperativa.addEmpresaLogistica(this);
    }

    /**
     * @param dinero Añade dinero a la empresa logística.
     */
    public void addDinero (double dinero) {
        this.dinero += dinero;
    }

    /**
     * @return Dinero de la empresa logística.
     */
    public double getDinero() {
        return this.dinero;
    }

    /**
     * @return ID de la empresa logística.
     */
    public int getId() {
        return this.id;
    }

    /**
     * @return El nombre de la empresa.
     */
    public String getNombreEmpresa() {
        return this.nombreEmpresa;
    }

    /**
     * @param nombreEmpresa El nuevo nombre de la empresa.
     */
    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }

    /**
     * @return Precio por kilómetro de la gran logística.
     */
    public double getPrecioKmGranLogistica() {
        return this.precioKmGranLogistica;
    }

    /**
     * @param precio Precio nuevo por kilómetro de la gran logística.
     */
    public void setPrecioKmGranLogistica(double precio) {
        this.precioKmGranLogistica = precio;
    }

    /**
     * @return Precio por kilómetro de la pequeña logística.
     */
    public double getPrecioKmPeqLogistica() {
        return this.precioKmPeqLogistica;
    }

    /**
     * @param precio Precio nuevo por kilómetro de la pequeña logística.
     */
    public void setPrecioKmPeqLogistica(double precio) {
        this.precioKmPeqLogistica = precio;
    }

    /**
     * @param km         Kilómetros que corresponden al tramo.
     * 
     *                   Productos perecederos:
     *                   - Debe ser <= 100 kilómetros.
     * 
     *                   Productos no perecederos:
     *                   - Siempre son tramos de 50 kilómetros.
     * 
     * @param valorPorKg Es el precio por kilogramo de producto.
     * @param cargaKg    La cantidad de kilogramos que se transportan.
     * 
     * @return Precio que se debe pagar al transportista por el tramo.
     */
    public double precioTramoGranLogistica(int km, double valorPorKg, double cargaKg) {
        double precioBase = 0.5 * valorPorKg * cargaKg;
        double kilometraje = km * this.precioKmGranLogistica;
        return precioBase + kilometraje;
    }

    /**
     * @param km      Kilómetros que corresponden al tramo.
     * 
     *                Productos perecederos:
     *                - Debe ser <= 100 kilómetros.
     * 
     *                Productos no perecederos:
     *                - Siempre son tramos < 50 kilómetros.
     * 
     * @param cargaKg La cantidad de kilogramos que se transportan.
     * 
     * @return Precio que se debe pagar al transportista por el tramo.
     */
    public double precioTramoPeqLogistica(int km, double cargaKg) {
        return cargaKg * km * precioKmPeqLogistica;
    }

}
