package modelo.productos;

public abstract class ProductoMenu implements Producto {
    // ==== ATRIBUTOS ====

    protected String nombre;
    protected int precioBase;
    protected int calorias;

    // ==== METODOS ====

    /**
     * Metodo constructor. Inicializa las variables nombre y precioBase
     * @param pNombre
     * @param pPrecioBase
     */
    public ProductoMenu(String pNombre, int pPrecioBase, int calorias) {
        this.nombre = pNombre;
        this.precioBase = pPrecioBase;
        this.calorias = calorias;
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
        String texto = modelo.utils.format.priceLine(this.getNombre(), this.getPrecio(), this.getCalorias());
        return texto;
    }

    public int getCalorias() {
        return this.calorias;
    }

    public boolean equals(ProductoMenu prod2) {
        if (!(this.getNombre().equals(prod2.getNombre()))) {
            return false;
        } else {
            return true;
        }
    }

}
