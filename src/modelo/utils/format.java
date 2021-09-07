package modelo.utils;

import java.lang.Math;

public class format {

    public static String priceLine(String nombre, int subtotal, int calorias) {
        long IVA = Math.round(subtotal * 0.19);
        long total = subtotal + IVA;

        String subtotalStr = Integer.toString(subtotal);
        String totalStr = Long.toString(total);
        String IVAStr = Long.toString(IVA);
        String caloriasStr = Integer.toString(calorias);
        
        String texto = formatPriceLine(nombre, subtotalStr, IVAStr, totalStr, caloriasStr);

        return texto;
    }

    public static String formatPriceLine(String nombre, String subtotal, String IVA, String total,
    String calorias) {
        if (nombre.length() > 28) {
            nombre = nombre.substring(0, 24) + "...";
        }
        while(nombre.length() < 30) {
            nombre = nombre + " ";
        }

        while(subtotal.length() < 10) {
            subtotal = subtotal + " ";
        }

        while(IVA.length() < 10) {
            IVA = IVA + " ";
        }

        while(total.length() < 10) {
            total = total + " ";
        }

        while(calorias.length() < 10) {
            calorias = calorias + " ";
        }

        String texto = nombre + subtotal + IVA + total + calorias + "\n";

        return texto;
    }
}
