package src;

public class Factura {

    private final String nombreProducto;
    private final EmpresaLogistica empresa;
    private final EntidadBase comprador;
    private static int count = 1000;
    private final int id;

    public Factura(String nombreProducto, EmpresaLogistica empresa, EntidadBase comprador) {
        this.nombreProducto = nombreProducto;
        this.empresa = empresa;
        this.comprador = comprador;
        this.id = count;
        count++;

        printFactura();
    }

    /**
     * @return Nombre del producto que se compra.
     */
    public String getNombreProducto() {
        return this.nombreProducto;
    }

    /**
     * @return Empresa que se encarga del transporte de la compra.
     */
    public EmpresaLogistica getEmpresa() {
        return this.empresa;
    }

    /**
     * @return Persona o entidad que compra el producto.
     */
    public EntidadBase getComprador() {
        return this.comprador;
    }

    /**
     * @return ID de la factura.
     */
    public int getId() {
        return this.id;
    }

    public void printFactura() {
        System.out.println();
        /* TODO: Seguir aqui. */
    }
}