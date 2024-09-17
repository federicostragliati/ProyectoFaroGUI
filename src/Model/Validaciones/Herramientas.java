package Model.Validaciones;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Herramientas {

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
            Long.parseLong(valor);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static Date convertirStringADate(String fechaString) {
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        try {
            return formato.parse(fechaString);
        } catch (ParseException e) {
            System.err.println("Error al convertir la fecha: " + e.getMessage());
            return null;
        }
    }

    public static String extraerID(String texto) {
        String regex = "^\\d{1,5}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(texto);

        if (matcher.find()) {
            return matcher.group();
        } else {
            return null;
        }
    }

    public static boolean esFormatoFechaValido(String fecha) {
        // Definir el formato de la fecha
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");

        // Establecer que no permita fechas inválidas (como 31/02/2024)
        formatoFecha.setLenient(false);

        try {
            // Intentar parsear la fecha
            formatoFecha.parse(fecha);
            return true; // Si no lanza excepción, el formato es válido
        } catch (ParseException e) {
            return false; // Si lanza excepción, el formato no es válido
        }

    }

    public static boolean sumaDeMontos(BigDecimal num1, BigDecimal num2, BigDecimal resultadoEsperado) {
        // Sumar los dos primeros BigDecimal
        BigDecimal suma = num1.add(num2);

        // Comparar la suma con el tercer BigDecimal
        return suma.compareTo(resultadoEsperado) == 0;
    }

    public static String convertirFecha(String fecha) {
        // Define los formatos de fecha
        SimpleDateFormat formatoOriginal = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatoNuevo = new SimpleDateFormat("dd/MM/yyyy");

        try {
            // Convierte la cadena de texto a un objeto Date
            Date fechaDate = formatoOriginal.parse(fecha);
            // Convierte la fecha al nuevo formato
            return formatoNuevo.format(fechaDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String[] dividirTexto(String input) {
        // Dividir el string en tres partes usando " - " como separador
        String[] partes = input.split(" - ");

        // Crear un array de 3 posiciones, quitando espacios en cada parte
        String[] resultado = new String[3];
        if (partes.length == 3) {
            resultado[0] = partes[0].trim();   // Parte antes del primer guion - ID
            resultado[1] = partes[1].trim();   // Parte entre los guiones - Nombre
            resultado[2] = partes[2].trim();   // Parte después del segundo guion - CUIT
        }

        return resultado;

    }
}
