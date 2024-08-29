package Model.Validaciones;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validador {

    public static boolean validarCUIT(String input) {
        // Expresión regular para el formato "2 dígitos-8 dígitos-1 dígitos"
        String regex = "^\\d{2}-\\d{8}-\\d{1}$";

        // Compilamos la expresión regular
        Pattern pattern = Pattern.compile(regex);

        // Verificamos si el input coincide con la expresión regular
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }

    public static boolean validarEmail(String email) {
        // Expresión regular para validar emails con dominios y subdominios opcionales
        String regex = "^[a-z0-9._-]+@[a-z0-9-]+(\\.[a-z0-9]+)*(\\.[a-zA-Z]{2,4})$";

        // Compilamos la expresión regular
        Pattern pattern = Pattern.compile(regex);

        // Verificamos si el email coincide con la expresión regular
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean validarNumeroDecimal(String numero) {

        // Expresión regular para validar el formato: hasta 65 números, un punto, y de 1 a 2 números después del punto
        String regex = "^\\d{1,65}(\\.\\d{1,2})?$";

        // Compilar la expresión regular y hacer la validación
        return Pattern.matches(regex, numero);
    }

    public static boolean validarLongitud(String texto) {
        if (texto == null) {
            return false; // Manejo de cadenas nulas
        }
        int longitud = texto.length();
        return longitud > 0 && longitud <= 100;
    }

    public static boolean validarPorcentaje(int numero) {
        // Convertir el número a su valor absoluto para manejar negativos
        numero = Math.abs(numero);

        // Verificar si el número tiene 3 o menos dígitos
        return numero >= 0 && numero <= 100;
    }

    public static boolean esNumeroEntero(String valor) {
        if (valor == null) {
            return false;
        }
        try {
            Integer.parseInt(valor);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }





}
