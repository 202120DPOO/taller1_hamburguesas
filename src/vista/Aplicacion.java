package vista;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import modelo.*;
import modelo.productos.*; 

public class Aplicacion {

	public static void main(String[] args) throws Exception
	{
		Aplicacion consola = new Aplicacion();
		consola.ejecutarAplicacion();
	}
	
	private Restaurante rest = new Restaurante();
	
	public void ejecutarAplicacion() throws Exception {
		System.out.println("Bienvenidos a la tienda de Hamburguesas!\n");

		boolean continuar = true;
		while (continuar)
		{
			try
			{
				mostrarMenu();				
				ejecutarCargaDatos(); 		
				int opcion_seleccionada = Integer.parseInt(input("Por favor seleccione una opción"));
				if (opcion_seleccionada == 1 && rest != null) {
					System.out.println("Menu base: \n");
					mostrarMenuRest();
					System.out.println("Combos: \n");
					mostrarCombos();	}	
				else if (opcion_seleccionada == 2 && rest != null) {
					String nomb = input("Ingrese su nombre: ");
					String direc = input("Ingrese su direccion: ");
					ejecutarInicioPedido(nomb,direc);		}
				else if (opcion_seleccionada == 3 && rest != null)
					ejecutarAgregarElementoAPedido();
				else if (opcion_seleccionada == 4 && rest != null)
					ejecutarCerrarPedido();
				else if (opcion_seleccionada == 5 && rest != null)
					ejecutarConsultaInfo();
				else if (opcion_seleccionada == 6 && rest != null)
					continuar = false;
				else
				{
					System.out.println("Por favor seleccione una opción válida.");
				}
			}
			catch (NumberFormatException e)
			{
				System.out.println("Debe seleccionar uno de los números de las opciones.");
			}
		}
	}

	private void ejecutarConsultaInfo() {
        int id = Integer.parseInt(input("ID del pedido: "));
        Pedido pedido = rest.getPedidoById(id);
        if (pedido == null) {
            System.out.println("No hay ningún pedido con ese ID.");
            return;
        }
        System.out.println(pedido.generarTextoFactura());
	}

	private void ejecutarCerrarPedido() throws Exception {
        int id = rest.getPedidoEnCurso().getIdPedido();
        System.out.println("El id del pedido es: " + id);
		rest.cerrarYGuargarPedido();
	}

	private void ejecutarAgregarElementoAPedido() {
		Producto pr;
        Pedido pedido = rest.getPedidoEnCurso();
        if (pedido == null) {
            System.out.println("Ningún pedido en curso");
            return;
        }
		int opc = Integer.parseInt(input("Seleccione 1 para combo y 2 para producto: "));
		if (opc == 1) {
			mostrarCombos();
			int op = Integer.parseInt(input("Por favor seleccione el # del combo que desea agregar: /n"));
			op = op - 1;
			pr = rest.getCombos().get(op);
            pedido.agregarProducto(pr);
		}
		else {
			mostrarMenuRest();
			int op = Integer.parseInt(input("Por favor seleccione el # del producto que desea agregar:\n"));
			op = op - 1;
			pr = rest.getMenuBase().get(op);
            opc = Integer.parseInt(input("1 para modificar producto, 2 para añadirlo así: "));
            ProductoAjustado adjProd = new ProductoAjustado(pr);
            while(opc != 2) {
                adjProd = modificarProducto(adjProd);
                opc = Integer.parseInt(input("1 para modificar producto, 2 para añadirlo así: "));
            }
            pedido.agregarProducto(adjProd);
		}
	}

    private ProductoAjustado modificarProducto(ProductoAjustado prod) {
        int opcion = Integer.parseInt(input("1 para agregar ingrediente, 2 para quitar ingrediente"));
        if (opcion == 1) {
            return agregarIngrediente(prod);
        } else {
            return eliminarIngrediente(prod);
        }
    }

    private void mostrarIngredientes() {
        ArrayList<Ingrediente> ingrs = rest.getIngredientes();
        int i = 1;
        for (Ingrediente ingr : ingrs) {
            System.out.println(i + " Nombre: " + ingr.getNombre() + "\tPrecio: " + ingr.getCostoAdicional());
            i++;
        }
    }
    private ProductoAjustado agregarIngrediente(ProductoAjustado prod) {
        mostrarIngredientes();
        int opcion = Integer.parseInt(input("Selecciones ingrediente a agregar: ")) - 1;
        Ingrediente ingr = rest.getIngredientes().get(opcion);
        prod.añadirIngrediente(ingr);

        return prod;
    }

    private ProductoAjustado eliminarIngrediente(ProductoAjustado prod) {
        mostrarIngredientes();
        int opcion = Integer.parseInt(input("Selecciones ingrediente a eliminar: ")) - 1;
        Ingrediente ingr = rest.getIngredientes().get(opcion);
        prod.eliminarIngrediente(ingr);

        return prod;
    }

	private void ejecutarInicioPedido(String nombre, String direccion) throws Exception {
		rest.iniciarPedido(nombre, direccion);
		
	}

	private void ejecutarCargaDatos() throws Exception {
		try
		{
			rest.cargarInformacionRestaurante(new File("data/ingredientes.txt"), new File("data/menu.txt"),
			new File("data/combos.txt"), new File("data/bebidas.txt"));
		}
		catch (IOException e)
		{
			System.out.println("ERROR: hubo un problema leyendo el archivo.");
			System.out.println(e.getMessage());
		}
		}

	public void mostrarMenu()
	{
		System.out.println("\nOpciones de la aplicación\n");
		System.out.println("1. Mostrar menu del restaurante");
		System.out.println("2. Iniciar un nuevo pedido");
		System.out.println("3. Agregar un elemento a un pedido");
		System.out.println("4. Cerrar un pedido y guardar la factura");
		System.out.println("5. Consultar la información de un pedido dado su id");
		System.out.println("6. Salir de la aplicación\n");
	}
	
	public void mostrarMenuRest() {
		int i = 1;
		for (ProductoMenu prod: rest.getMenuBase()) {
			System.out.println(i + " Nombre:"+ prod.getNombre() + "\t" + prod.getPrecio());
			i+=1;
		}
	}
	
	public void mostrarCombos() {
		int i = 1;
		for (Combo combo: rest.getCombos()) {
			System.out.println(i + " Nombre:"+ combo.getNombre() + "\t" + combo.getPrecio());
			i+=1;
		}
	}
	
	
	public String input(String mensaje)
	{
		try
		{
			System.out.print(mensaje + ": ");
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			return reader.readLine();
		}
		catch (IOException e)
		{
			System.out.println("Error leyendo de la consola");
			e.printStackTrace();
		}
		return null;
	}
}
