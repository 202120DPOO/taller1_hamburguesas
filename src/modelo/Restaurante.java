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
    private Hashtable<String, ProductoMenu> menuBase = new Hashtable<String, ProductoMenu>();
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

    public void cerrarYGuargarPedido() throws Exception {
        if (this.pedidoEnCurso == null) {
            throw new Exception("No hay ningún pedido en curso, para guargar y cerrar");
        }
        File archivo = new File("facturas/" + "fv_" + Integer.toString(this.pedidoEnCurso.getIdPedido()));
        this.pedidoEnCurso.guardarFactura(archivo);
        pedidos.put(this.pedidoEnCurso.getIdPedido(), this.pedidoEnCurso);
        this.pedidoEnCurso = null;
    }

    public Pedido getPedidoEnCurso() {
        return this.pedidoEnCurso;
    }

    public ArrayList<ProductoMenu> getMenuBase() {
        return new ArrayList<ProductoMenu>(this.menuBase.values());
    }
    
    public ArrayList<Combo> getCombos(){
    	return this.combos;
    }

    public ArrayList<Ingrediente> getIngredientes() {
        return new ArrayList<Ingrediente>(this.ingredientes.values());
    }

    public void cargarInformacionRestaurante(File archivoIngredientes, File archivoMenu, File archivoCombos)
    throws IOException {
        this.cargarIngredientes(archivoIngredientes);
        this.cargarMenu(archivoMenu);
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
            Ingrediente ingr = new Ingrediente(ingrediente[0], Integer.parseInt(ingrediente[1]));
            this.ingredientes.put(ingrediente[0], ingr);
        }
        reader.close();
    }

    /**
     * Carga los productos del archivo y los añade a la contenedors menuBase
     * @param archivoMenu archivo con productos del menú
     * @throws IOException
     */
    private void cargarMenu(File archivoMenu) throws IOException {
        FileReader archivo = new FileReader(archivoMenu);
        BufferedReader reader = new BufferedReader(archivo);
        String line = "";
        while ((line = reader.readLine()) != null) {
            if (line.equals("")) {
                continue;
            }
            String[] producto = line.split(";");
            ProductoMenu prod = new ProductoMenu(producto[0], Integer.parseInt(producto[1]));
            menuBase.put(producto[0], prod);
        }
        reader.close();
    }

    private void cargarCombos(File archivoCombos) throws IOException {
        FileReader archivo = new FileReader(archivoCombos);
        BufferedReader reader = new BufferedReader(archivo);
        String line = "";
        while ((line = reader.readLine()) != null) {
            if (line.equals("")) {
                continue;
            }
            String[] combo = line.split(";");
            Combo cmb = new Combo(combo[0], Integer.parseInt(combo[1]));
            for (int i = 2; i < combo.length; i++){
                ProductoMenu itemCombo = menuBase.get(combo[i]);
                cmb.agregarItemACombo(itemCombo);
            }
            combos.add(cmb);
        }
        reader.close();
    }

}
