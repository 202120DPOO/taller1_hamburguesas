package modelo.productos;

public interface Producto {
    public int getPrecio();
    public String getNombre();
    public String generarTextoFactura();
    public int getCalorias();
}
