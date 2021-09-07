package modelo.productos;

import java.util.ArrayList;
import java.lang.Math;

public class Combo implements Producto {
    // ==== ATRIBUTOS ====

    private double descuento;
    private String nombreCombo;

    // Almacena los productos del combo
    private ArrayList<Producto> prodsCombo = new ArrayList<Producto>();

    // ==== METODOS ====

    /**
     * Inicializa los atributos descuento y nombreCombo
     * @param pNombre nombre del combo
     * @param pDescuento % de descuento sobre los productos del combo
     */
    public Combo(String pNombre, double pDescuento) {
        this.descuento = pDescuento;
        this.nombreCombo = pNombre;
    }

    /**
     * Agrega un producto al combo
     * @param itemCombo producto a agregar
     */
    public void agregarItemACombo(Producto itemCombo) {
        this.prodsCombo.add(itemCombo);
    }

    /**
     * Retorna el precio del combo SIN IVA
     */
    public int getPrecio() {
        int precio = 0;
        // Suma los precios de todos los productos
        for (Producto prod : this.prodsCombo) {
            precio += prod.getPrecio();
        }
        // Aplica el porcentaje de descuento
        precio = (int)Math.round(precio * (1 - this.descuento));

        return precio;
    }

    /**
     * Retorna el texto a imprimir para facturar el producto.
     */
    public String generarTextoFactura() {
        // Nombre y precio del combo
        String texto = modelo.utils.format.priceLine(this.getNombre(), this.getPrecio(), this.getCalorias());
        // Contenido
        texto = texto + "\t Contiene:\n";
        // Ciclo por todos los productos que contiene
        for (Producto prod : prodsCombo) {
            texto = texto + "\t   + " + prod.getNombre() + "\n";
        }
        
        return texto + "\n";
    }

    /**
     * Retorna el nombre del combo
     */
    public String getNombre() {
        return this.nombreCombo;
    }

    public int getCalorias() {
        int caloras = 0;
        for (Producto prod : prodsCombo) {
            caloras += prod.getCalorias();
        }

        return caloras;
    }
}
