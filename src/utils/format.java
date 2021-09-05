package utils;

import java.lang.Math;

public class format {

    public static String priceLine(String nombre, int subtotal) {
        long IVA = Math.round(subtotal * 0.19);
        long total = subtotal + IVA;

        String subtotalStr = Integer.toString(subtotal);
        String totalStr = Long.toString(total);
        String IVAStr = Long.toString(IVA);
        
        if (nombre.length() > 38) {
            nombre = nombre.substring(0, 34) + "...";
        }
        while(nombre.length() < 40) {
            nombre = nombre + " ";
        }

        while(subtotalStr.length() < 10) {
            subtotalStr = subtotalStr + " ";
        }

        while(IVAStr.length() < 10) {
            IVAStr = IVAStr + " ";
        }

        while(totalStr.length() < 10) {
            totalStr = totalStr + " ";
        }

        String texto = nombre + subtotalStr + IVAStr + totalStr + "\n";

        return texto;
    }
}
