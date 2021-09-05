package modelo;

import modelo.productos.Producto;
import java.util.ArrayList;

public class Pedido {
    // ==== ATRIBUTOS ====

    private static int numeroPedidos = 0;
    private int idPedido;
    private String nombreCliente;
    private String direccionCliente;
    private ArrayList<Producto> prods = new ArrayList<Producto>();

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

    /**
     * Agrega un producto al pedido.
     * @param prod producto a agregar.
     */
    public void agregarProducto(Producto prod) {
        this.prods.add(prod);
    }
    
    /**
     * retorna el precio del pedido SIN IVA.
     */
    private int getPrecioNetoPedido() {
        int subtotal = 0;
        for (Producto prod : this.prods) {
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

    /**
     * Retorna el texto a mostrar en la factura de venta del pedido.
     */
    private String generarTextoFactura() {
        String texto = "FACTURA DE VENTA. PEDIDO #" + this.idPedido + "\n";
        texto += "Nombre cliente: " + this.nombreCliente + "\n";
        texto += "Dirección: " + this.direccionCliente + "\n";
        for (Producto prod : this.prods) {
            texto = texto + prod.generarTextoFactura();
        }

        return texto;
    }

    public void guardarFactura() {
        //TODO
    }
}
