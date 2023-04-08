package src;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Productor {

    private Persona propietario;
    private List<Producto> productos;
    private double balance;
    private double extension;
    private boolean esGranProductor;

    public Productor(String nombreProducto, Persona propietario, double extension) {
        if (!Cooperativa.nombresProductos.contains(nombreProducto)) {
            System.out.println("El producto no existe.");
        } else {
            this.propietario = propietario;
            this.balance = 0;
            productos = new ArrayList<>();
            crearProducto(nombreProducto, extension);
            Cooperativa.addProductoDisponible(nombreProducto, extension);
            this.extension = extension;
            setEsGranProductor();

            Cooperativa.addProductor(this);
        }
    }

    /**
     * @return Persona dueña del conjunto de cultivos.
     */
    public Persona getPropietario() {
        return this.propietario;
    }

    /**
     * @param propietario Establece una persona nueva como propietario.
     */
    public void setPropietario(Persona propietario) {
        this.propietario = propietario;
    }

    /**
     * @return Lista de productos que posee el propietario.
     */
    public List<Producto> getProductos() {
        return this.productos;
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
     * @return La extensión total del productor.
     */
    public double getExtension() {
        return this.extension;
    }

    /**
     * @param nombreProducto Es el nombre del producto que se quiere añadir. Si el
     *                       producto no tiene ningún cultivo con ese nombre, creará
     *                       un objeto del nuevo cultuivo y lo añadirá.
     * @param extension      Tamaño en hectáreas del cultivo que se quiere añadir.
     *                       Si ya hay algún cultivo con el mismo nombre, se sumará
     *                       la extensión del nuevo con el anterior.
     */
    public void addProducto(String nombreProducto, double extension) {
        if (Cooperativa.nombresProductos.contains(nombreProducto)) {
            Producto existente = null;
            for (Producto p : productos) {
                if (p.getNombreProducto().equals(nombreProducto)) {
                    existente = p;
                }
            }
            if (existente != null) {
                existente.setExtension(existente.getExtension() + extension);
                System.out.println("El productor ya tenía un cultivo similar.");
                System.out.println("Se ha actualizado su extensión.");
            } else {
                crearProducto(nombreProducto, extension);
                this.extension += extension;
            }
            Cooperativa.addProductoDisponible(nombreProducto, extension);

            setEsGranProductor();
        } else {
            System.out.println("El producto no existe en la Cooperativa.");
        }
    }

    /**
     * @param nombreProducto Nombre del producto que se quiere eliminar. Lo elimina
     *                       por completo.
     */
    public void removeProducto(String nombreProducto) {
        System.out.println("Extension antes de actualizar: " + this.extension);
        Iterator<Producto> iter = productos.iterator();
        while (iter.hasNext()) {
            Producto producto = iter.next();
            if (producto.getNombreProducto().equals(nombreProducto)) {
                System.out.println("Nombre del producto: " + producto.getNombreProducto());
                System.out.println(producto.toString());
                System.out.println("Extension del objeto que se quita: " + producto.getExtension());
                this.extension -= producto.getExtension();
                Cooperativa.substractProductoDisponible(nombreProducto, producto.getExtension());
                iter.remove();
            }
        }
        System.out.println("Extension nueva: " + this.extension);
    }

    /**
     * @return True si es gran productor.
     */
    public boolean getEsGranProductor() {
        return this.esGranProductor;
    }

    /**
     * Establece si es un gran productor si la extensión total de sus cultivos es
     * mayor que el límite que marca la cooperativa.
     */
    private void setEsGranProductor() {
        if (this.extension > Cooperativa.limiteExtension) {
            this.esGranProductor = true;
        } else {
            this.esGranProductor = false;
        }
    }

    /**
     * @param nombreProducto El nombre del cultivo que se va a crear.
     */
    private void crearProducto(String nombreProducto, double extension) {
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
