package modelo.productos;

public class Ingrediente {
    // ==== ATRIBUTOS ====
    private String nombre;
    private int costoAdicional;
    private int calorias;
    
    // ==== METODOS ====
    /**
     * Inicializa los atributos nombre y costoAdicional
     * @param pNombre nombre del ingrediente
     * @param pCostoAdicional costo adicional del ingrediente
     */
    public Ingrediente(String pNombre, int pCostoAdicional, int calorias) {
        this.nombre = pNombre;
        this.costoAdicional = pCostoAdicional;
        this.calorias = calorias;
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

    public int getCalorias() {
        return this.calorias;
    }

    public boolean equals(Ingrediente ingr2) {
        if (this.getNombre().equals(getNombre())) {
            return true;
        }

        return false;
    }
}
