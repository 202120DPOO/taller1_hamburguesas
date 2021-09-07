package modelo.productos;

import java.util.ArrayList;

public class ProductoAjustado implements Producto {
    // ==== ATRIBUTOS ====
    private Producto prodBase;
    private ArrayList<Ingrediente> ingrAgregados = new ArrayList<Ingrediente>();
    private ArrayList<Ingrediente> ingrEliminados = new ArrayList<Ingrediente>();

    // ==== METODOS ====
    /**
     * Se asigna base a this.prodBase
     * @param base el producto base al cual se le harán modificaciones.
     */
    public ProductoAjustado(Producto base) {
        this.prodBase = base;
    }

    /**
     * Retorna el nombre del producto base, con nota '(MODIFICADO)'
     */
    public String getNombre() {
        return this.prodBase.getNombre();
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

    public void añadirIngrediente(Ingrediente ingr) {
        this.ingrAgregados.add(ingr);
    }

    public void eliminarIngrediente(Ingrediente ingr) {
        this.ingrEliminados.add(ingr);
    }

    public String generarTextoFactura() {
        // Producto base
        String texto = modelo.utils.format.priceLine(this.getNombre(), this.getPrecio(), this.getCalorias());
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

    public int getCalorias() {
        //Producto base
        int calorias = prodBase.getCalorias();
        for (Ingrediente ingr : ingrAgregados) {
            calorias += ingr.getCalorias();
        }
        for (Ingrediente ingr : ingrEliminados) {
            calorias -= ingr.getCalorias();
        }
        if (calorias < 0) {
            calorias = 0;
        }

        return calorias;
    }
}
