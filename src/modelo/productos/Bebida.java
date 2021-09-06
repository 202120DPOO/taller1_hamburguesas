package modelo.productos;

public class Bebida implements Producto{
    private String nombre;
    private int precio;
    private int calorias;

    public Bebida(String nombre, int precio, int calorias) {
        this.nombre = nombre;
        this.precio = precio;
        this.calorias = calorias;
    }
    
    public int getCalorias() {
        return this.calorias;
    }
    
    public String getNombre() {
        return this.nombre;
    }

    public int getPrecio() {
        return this.precio;
    }


    public String generarTextoFactura() {
        String texto = modelo.utils.format.priceLine(this.getNombre(), this.getPrecio());
        return texto;
    }
}
