package modelo.productos;

import java.util.ArrayList;

public class ProductoAjustado implements Producto {
    // ==== ATRIBUTOS ====
    private ProductoMenu prodBase;
    private ArrayList<Ingrediente> ingrAgregados = new ArrayList<Ingrediente>();
    private ArrayList<Ingrediente> ingrEliminados = new ArrayList<Ingrediente>();

    // ==== METODOS ====
    /**
     * Se asigna base a this.prodBase
     * @param base el producto base al cual se le harán modificaciones.
     */
    public ProductoAjustado(ProductoMenu base) {
        this.prodBase = base;
    }

    /**
     * Retorna el nombre del producto base, con nota '(MODIFICADO)'
     */
    public String getNombre() {
        return this.prodBase.getNombre() + " (MODIFICADO)";
    }

    public int getPrecio() {
        // Precio base
        int precio = prodBase.getPrecio();
        // Añade Precio de productos agregados
        for (Ingrediente ingr : ingrAgregados) {
            precio += ingr.getCostoAdicional();
        }

        return precio;
    }

    public String generarTextoFactura() {
        // Producto base
        String texto = modelo.utils.format.priceLine(this.getNombre(), this.getPrecio());
        texto = texto + "\t MODIFICACIONES:\n";
        // Ingredientes agregados
        for (Ingrediente ingr : ingrAgregados) {
            texto = texto + "\t\t+" + ingr.getNombre() + "\n";
        }
        // Ingredientes eliminados
        for (Ingrediente ingr : ingrEliminados) {
            texto = texto + "\t\t-" + ingr.getNombre() + "\n";
        }

        return texto;
    }
}
