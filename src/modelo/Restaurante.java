package modelo;

import java.io.*;
import java.util.ArrayList;
import java.util.Hashtable;

import modelo.productos.*;

public class Restaurante {
    // ==== ATRIBUTOS ====
    private Hashtable<Integer, Pedido> pedidos = new Hashtable<Integer, Pedido>();
    private Pedido pedidoEnCurso;
    private Hashtable<String, Ingrediente> ingredientes = new Hashtable<String, Ingrediente>();
    private Hashtable<String, noBebida> noBebidas = new Hashtable<String, noBebida>();
    private Hashtable<String, Bebida> bebidas = new Hashtable<String, Bebida>();
    private ArrayList<Combo> combos = new ArrayList<Combo>();

    // ==== METODOS ====

    /**
     * Inicia un nuevo pedido
     * @param nombreCliente nombre del cliente
     * @param direccionCliente dirección del cliente
     * 
     * @throws Exception Si se intenta iniciar un nuevo pedido cuando ya hay uno en curso.
     */
    public void iniciarPedido(String nombreCliente, String direccionCliente) throws Exception {
        if (this.pedidoEnCurso != null) {
            throw new Exception("Ya hay un pedido en curso. Para iniciar uno nuevo, debe gurardar y cerrar el actual.");
        }
        Pedido order = new Pedido(nombreCliente, direccionCliente);
        this.pedidoEnCurso = order;
    }

    public int cerrarYGuargarPedido() throws Exception {
        int identical = -1;
        if (this.pedidoEnCurso == null) {
            throw new Exception("No hay ningún pedido en curso, para guargar y cerrar");
        }
        File archivo = new File("facturas/" + "fv_" + Integer.toString(this.pedidoEnCurso.getIdPedido()) + ".txt");
        this.pedidoEnCurso.guardarFactura(archivo);
        for (Pedido ped : pedidos.values()) {
            if (ped.equals(this.pedidoEnCurso)) {
                identical = ped.getIdPedido();
                break;
            }
        }
        pedidos.put(this.pedidoEnCurso.getIdPedido(), this.pedidoEnCurso);
        this.pedidoEnCurso = null;
        return identical;
    }

    public Pedido getPedidoEnCurso() {
        return this.pedidoEnCurso;
    }

    public ArrayList<noBebida> getNoBebidas() {
        return new ArrayList<noBebida>(this.noBebidas.values());
    }
    
    public ArrayList<Combo> getCombos(){
    	return this.combos;
    }

    public ArrayList<Ingrediente> getIngredientes() {
        return new ArrayList<Ingrediente>(this.ingredientes.values());
    }

    public ArrayList<Bebida> getBebidas() {
        return new ArrayList<Bebida>(this.bebidas.values());
    }

    public void cargarInformacionRestaurante(File archivoIngredientes, File archivoNoBebidas, File archivoCombos,
    File archivoBebidas)
    throws IOException, Exception {
        this.cargarIngredientes(archivoIngredientes);
        this.cargarNoBebidas(archivoNoBebidas);
        this.cargarBebidas(archivoBebidas);
        this.cargarCombos(archivoCombos);
    }

    /**
     * Carga los ingredientes del archivo y los añade a la contenedor ingredientes.
     * @param archivoIngredientes archivo de Ingredientes
     * @throws IOException
     */
    private void cargarIngredientes(File archivoIngredientes) throws IOException {
        FileReader archivo = new FileReader(archivoIngredientes);
        BufferedReader reader = new BufferedReader(archivo);
        String line = "";
        while ((line = reader.readLine()) != null) {
            if (line.equals("")) {
                continue;
            }
            String[] ingrediente = line.split(";");
            Ingrediente ingr = new Ingrediente(ingrediente[0], Integer.parseInt(ingrediente[1]),
            Integer.parseInt(ingrediente[2]));
            this.ingredientes.put(ingrediente[0], ingr);
        }
        reader.close();
    }

    /**
     * Carga los productos del archivo y los añade a la contenedors menuBase
     * @param archivoMenu archivo con productos del menú
     * @throws IOException
     */
    private void cargarNoBebidas(File archivoNoBebidas) throws IOException {
        FileReader archivo = new FileReader(archivoNoBebidas);
        BufferedReader reader = new BufferedReader(archivo);
        String line = "";
        while ((line = reader.readLine()) != null) {
            if (line.equals("")) {
                continue;
            }
            String[] producto = line.split(";");
            noBebida prod = new noBebida(producto[0], Integer.parseInt(producto[1]),
            Integer.parseInt(producto[2]));
            noBebidas.put(producto[0], prod);
        }
        reader.close();
    }

    private void cargarCombos(File archivoCombos) throws IOException, Exception {
        FileReader archivo = new FileReader(archivoCombos);
        BufferedReader reader = new BufferedReader(archivo);
        String line = "";
        while ((line = reader.readLine()) != null) {
            if (line.equals("")) {
                continue;
            }
            String[] combo = line.split(";");
            Combo cmb = new Combo(combo[0],
            Integer.parseInt(combo[1].replace("%", "")) / 100);
            Hashtable<String, ProductoMenu> menuBase = this.getProductosMenuHashTB();
            for (int i = 2; i < combo.length; i++){
                ProductoMenu itemCombo;
                if ( (itemCombo = menuBase.get(combo[i])) != null ) {
                    cmb.agregarItemACombo(itemCombo);
                } else {
                    reader.close();
                    throw new Exception("Producto " + combo[i] + " no encontrado");
                }
            }
            combos.add(cmb);
        }
        reader.close();
    }


    private void cargarBebidas(File archivoBebidas) throws IOException {
        FileReader archivo = new FileReader(archivoBebidas);
        BufferedReader reader = new BufferedReader(archivo);
        String line = "";
        while ((line = reader.readLine()) != null) {
            if (line.equals("")) {
                continue;
            }
            String[] bebida = line.split(";");
            Bebida prod = new Bebida(bebida[0], Integer.parseInt(bebida[1]),
            Integer.parseInt(bebida[2]));
            bebidas.put(bebida[0], prod);
        }
        reader.close();
    }


    private Hashtable<String, ProductoMenu> getProductosMenuHashTB() {
        Hashtable<String, ProductoMenu> prods = new Hashtable<String, ProductoMenu>(this.noBebidas);
        prods.putAll(this.bebidas);
        return prods;
    }


    public ArrayList<ProductoMenu> getProductosMenu() {
        return new ArrayList<ProductoMenu>(this.getProductosMenuHashTB().values());
    }


    public Pedido getPedidoById(int id) {
        return pedidos.get(id);
    }

}
