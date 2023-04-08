package src;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ProductorFederado {

    private List<Persona> propietarios;
    private List<Producto> productos;
    private String nombreProducto;
    private double balance;
    private double extension;

    public ProductorFederado(String nombreProducto, Persona propietario, double extension) {
        if (!Cooperativa.nombresProductos.contains(nombreProducto)) {
            System.out.println("El producto no existe en la Cooperativa.");
        } else if (Cooperativa.checkProductorFederado(nombreProducto)) {
            System.out.println("Ya hay un productor federado asociado a este producto.");
        } else if (extension > Cooperativa.limiteExtension) {
            System.out.println(
                    "No se puede crear el productor federado porque se excece el tamaño máximo permitido por la Cooperativa");
        } else {
            this.propietarios = new ArrayList<>();
            propietarios.add(propietario);
            this.productos = new ArrayList<>();
            this.nombreProducto = nombreProducto;
            this.balance = 0;
            this.extension = extension;

            crearProducto(nombreProducto, propietario, extension);
            Cooperativa.addProductoDisponible(nombreProducto, extension);
            Cooperativa.addProductorFederado(this);
        }
    }

    /**
     * @return Lista de propietarios de productos del productor federado.
     */
    public List<Persona> getPropietarios() {
        return this.propietarios;
    }

    /**
     * @return Lista de productos que hay en el productor federado.
     */
    public List<Producto> getProductos() {
        return this.productos;
    }

    /**
     * @return Nombre del producto que cultiva el productor federado.
     */
    public String getNombreProducto() {
        return this.nombreProducto;
    }

    /**
     * @return El dinero que ha ganado con las ventas.
     */
    public double getBalance() {
        return this.balance;
    }

    /**
     * @param balance Añade una cantidad mayor que 0 al balance.
     */
    public void addBalance(double balance) {
        if (balance > 0.0) {
            this.balance += balance;
        }
    }

    /**
     * @return Extensión en hectáreas del productor federado.
     */
    public double getExtension() {
        return this.extension;
    }

    /**
     * @param propietario Persona que se va a unir al productor federado.
     * @param extension   Extensión en hectáreas que va a ocupar el nuevo cuiltivo.
     *                    No se puede superar la extensión máxima marcada por la
     *                    Cooperativa.
     */
    public void addProducto(Persona propietario, double extension) {
        if (this.extension + extension > Cooperativa.limiteExtension) {
            System.out.println("Producto no válido porque se supera el límite de hectáreas de la Cooperativa.");
        } else {
            crearProducto(this.nombreProducto, propietario, extension);
            this.propietarios.add(propietario);
            this.extension += extension;

            Cooperativa.addProductoDisponible(this.nombreProducto, extension);
        }
    }

    /**
     * @param propietario Persona que se da de baja en el productor federado.
     */
    public void removeProducto(Persona propietario) {
        Iterator<Producto> iter = productos.iterator();
        while (iter.hasNext()) {
            Producto producto = iter.next();
            if (producto.getPropietario() == propietario) {
                this.extension -= producto.getExtension();
                Cooperativa.substractProductoDisponible(producto.getNombreProducto(), producto.getExtension());
                iter.remove();
            }
        }
    }

    /**
     * @param nombreProducto El nombre del cultivo que se va a crear.
     */
    private void crearProducto(String nombreProducto, Persona propietario, double extension) {
        switch (nombreProducto) {
            case "Lechuga":
                productos.add(new ProdLechuga(propietario, extension));
                break;
            case "Tomate":
                productos.add(new ProdTomate(propietario, extension));
                break;
            case "Aceituna":
                productos.add(new ProdAceituna(propietario, extension));
                break;
            case "Fresa":
                productos.add(new ProdFresa(propietario, extension));
                break;
            case "Naranja":
                productos.add(new ProdNaranja(propietario, extension));
                break;
            case "Patata":
                productos.add(new ProdPatata(propietario, extension));
                break;
            case "Pepino":
                productos.add(new ProdPepino(propietario, extension));
                break;
            case "Aceite":
                productos.add(new ProdAceite(propietario, extension));
                break;
            case "Trigo":
                productos.add(new ProdTrigo(propietario, extension));
                break;
            case "Maiz":
                productos.add(new ProdMaiz(propietario, extension));
                break;
            case "Girasol":
                productos.add(new ProdGirasol(propietario, extension));
                break;
            case "Avellana":
                productos.add(new ProdAvellana(propietario, extension));
                break;
            case "Garbanzo":
                productos.add(new ProdGarbanzo(propietario, extension));
                break;
            case "Avena":
                productos.add(new ProdAvena(propietario, extension));
                break;
            default:
                System.out.println("El producto no existe");

        }
    }

}
