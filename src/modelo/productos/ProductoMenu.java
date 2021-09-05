package modelo.productos;

public class ProductoMenu implements Producto {
    // ==== ATRIBUTOS ====

    protected String nombre;
    protected int precioBase;

    // ==== METODOS ====

    /**
     * Metodo constructor. Inicializa las variables nombre y precioBase
     * @param pNombre
     * @param pPrecioBase
     */
    public ProductoMenu(String pNombre, int pPrecioBase) {
        this.nombre = pNombre;
        this.precioBase = pPrecioBase;
    }

    /**
     * Retorna el nombre del producto
     */
    public String getNombre() {
        return this.nombre;
    }

    /**
     * Retorna el precio base del producto
     */
    public int getPrecio() {
        return this.precioBase;
    }

    /**
     * Retorna el texto que se debe incluir para facturar el producto
     */
    public String generarTextoFactura() {
        String texto = "Prod:" + this.nombre + "\t Precio:" + this.precioBase;
        return texto;
    }

}
