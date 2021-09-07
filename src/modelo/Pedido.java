package modelo;

import modelo.productos.*;
import modelo.utils.format;

import java.util.ArrayList;

import java.io.*;

public class Pedido {
    // ==== ATRIBUTOS ====

    private static int numeroPedidos = 0;
    private int idPedido;
    private String nombreCliente;
    private String direccionCliente;
    private ArrayList<ProductoMenu> prodsBase = new ArrayList<ProductoMenu>();
    private ArrayList<Combo> combos = new ArrayList<Combo>();
    private ArrayList<ProductoAjustado> adjProds = new ArrayList<ProductoAjustado>();

    // ==== METODOS ====
    /**
     * Le asigna valores a las variables nombreCliente, direcciónCliente e idPedido.
     * Aumenta en 1 el contador numeroPedidos.
     * @param nombreCliente nombre del cliente
     * @param direccionCliente dirección del cliente
     */
    public Pedido(String nombreCliente, String direccionCliente) {
        this.nombreCliente = nombreCliente;
        this.direccionCliente = direccionCliente;
        Pedido.numeroPedidos += 1;
        this.idPedido = Pedido.numeroPedidos;
    }

    /**
     * Retorna el id del pedido
     */
    public int getIdPedido() {
        return this.idPedido;
    }

    public void agregarBase(ProductoMenu base) {
        this.prodsBase.add(base);
    }

    public void agregarCombo(Combo comb) {
        this.combos.add(comb);
    }

    public void agregarAjustado(ProductoAjustado adjProd) {
        this.adjProds.add(adjProd);
    }

    private ArrayList<Producto> getProds() {
        ArrayList<Producto> prods = new ArrayList<Producto>(this.prodsBase);
        prods.addAll(this.combos);
        prods.addAll(this.adjProds);

        return prods;
    }
    
    /**
     * retorna el precio del pedido SIN IVA.
     */
    private int getPrecioNetoPedido() {
        int subtotal = 0;
        for (Producto prod : this.getProds()) {
            subtotal += prod.getPrecio();
        }

        return subtotal;
    }

    /**
     * Retorna el iva del pedido.
     */
    private int getPrecioIVAPedido() {
        int IVA = (int)Math.round(getPrecioNetoPedido() * 0.19);
        return IVA;
    }

    /**
     * Retorna el precio total del pedido.
     */
    private int getPrecioTotalPedido() {
        int total = getPrecioNetoPedido() + getPrecioIVAPedido();
        return total;
    }

    private int getCalorias() {
        int calorias = 0;
        for (Producto prod: getProds()) {
            calorias += prod.getCalorias();
        }

        return calorias;
    }

    /**
     * Retorna el texto a mostrar en la factura de venta del pedido.
     */
    public String generarTextoFactura() {
        String texto = "FACTURA DE VENTA. PEDIDO #" + this.idPedido + "\n";
        texto += "Nombre cliente: " + this.nombreCliente + "\n";
        texto += "Dirección: " + this.direccionCliente + "\n";
        texto += format.formatPriceLine("Nombre", "Subtotal", "IVA", "Total", "Calorias");
        for (Producto prod : this.getProds()) {
            texto = texto + prod.generarTextoFactura();
        }
        
        texto += "\n ==== CALORIAS TOTALES " + getCalorias() + " ==== \n\n";
        texto += " ==== SUBTOTAL:   " + getPrecioNetoPedido() + " ====\n";
        texto += " ==== IVA:        " + getPrecioIVAPedido() + " ====\n";
        texto += " ==== TOTAL:      " + getPrecioTotalPedido() + " ====\n";

        return texto;
    }

    public void guardarFactura(File archivo) throws IOException {
        FileWriter wArchivo = new FileWriter(archivo);
        BufferedWriter writer = new BufferedWriter(wArchivo);
        String textoFactura = generarTextoFactura();
        writer.write(textoFactura);
        writer.close();
    }

    public boolean equals(Pedido ped2) {
        if (!(this.nombreCliente.equals(ped2.nombreCliente))) {
            return false;
        }
        if (!(this.direccionCliente.equals(ped2.direccionCliente))) {
            return false;
        }
        if (this.prodsBase.size() != ped2.prodsBase.size()) {
            return false;
        }
        if (this.combos.size() != ped2.combos.size()) {
            return false;
        }
        if (this.adjProds.size() != ped2.adjProds.size()) {
            return false;
        }
        for (int i = 0; i < this.prodsBase.size(); i++) {
            if (!(this.prodsBase.get(i).equals(ped2.prodsBase.get(i)))) {
                return false;
            }
        }
        for (int i = 0; i < this.combos.size(); i++) {
            if (!(this.combos.get(i).equals(ped2.combos.get(i)))) {
                return false;
            }
        }
        for (int i = 0; i < this.adjProds.size(); i++) {
            if (!(this.adjProds.get(i).equals(ped2.adjProds.get(i)))) {
                return false;
            }
        }
        
        return true;
    }
}
