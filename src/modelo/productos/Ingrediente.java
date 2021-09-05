package modelo.productos;

public class Ingrediente {
    // ==== ATRIBUTOS ====
    private String nombre;
    private int costoAdicional;
    
    // ==== METODOS ====
    /**
     * Inicializa los atributos nombre y costoAdicional
     * @param pNombre nombre del ingrediente
     * @param pCostoAdicional costo adicional del ingrediente
     */
    public Ingrediente(String pNombre, int pCostoAdicional) {
        this.nombre = pNombre;
        this.costoAdicional = pCostoAdicional;
    }

    /**
     * Retorna el nombre del Ingrediente.
     */
    public String getNombre() {
        return this.nombre;
    }
    
    /**
     * Retorna el coto adicional del ingrediente.
     */
    public int getCostoAdicional() {
        return this.costoAdicional;
    }
}
