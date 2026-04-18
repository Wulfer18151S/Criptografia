package com.criptografia.modelo;

/**
 * Clase utilitaria que define los alfabetos disponibles para los cifrados clásicos.
 * Soporta inglés, español y español extendido (con vocales acentuadas).
 */
public class Alfabeto {

    // Constantes de alfabetos
    public static final String INGLES = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String ESPANOL = "ABCDEFGHIJKLMNÑOPQRSTUVWXYZ";
    public static final String ESPANOL_EXTENDIDO = "ABCDEFGHIJKLMNÑOPQRSTUVWXYZÁÉÍÓÚÜ";

    // Mapeo de claves a alfabetos
    private static final java.util.Map<String, String> ALFABETOS = new java.util.HashMap<>();

    static {
        ALFABETOS.put("INGLES", INGLES);
        ALFABETOS.put("ESPAÑOL", ESPANOL);
        ALFABETOS.put("ESPANOL_EXTENDIDO", ESPANOL_EXTENDIDO);
    }

    /**
     * Retorna el alfabeto correspondiente al idioma especificado.
     * @param idioma Clave del alfabeto ("INGLES", "ESPAÑOL", "ESPANOL_EXTENDIDO")
     * @return El alfabeto seleccionado
     */
    public static String getAlfabeto(String idioma) {
        String clave = normalizarClave(idioma);
        return ALFABETOS.getOrDefault(clave, INGLES);
    }

    /**
     * Retorna la posición de una letra en el alfabeto especificado.
     * @param c Carácter a buscar
     * @param idioma Clave del alfabeto
     * @return Posición de la letra (0-based) o -1 si no existe (incluye acentos)
     */
    public static int getIndex(char c, String idioma) {
        String alfabeto = getAlfabeto(idioma);
        char mayuscula = Character.toUpperCase(c);
        // Si el carácter tiene acento, no estará en el alfabeto básico y retornará -1
        int index = alfabeto.indexOf(mayuscula);
        return index;
    }

    /**
     * Retorna el carácter en la posición especificada del alfabeto.
     * @param index Posición (0-based)
     * @param idioma Clave del alfabeto
     * @return Carácter en esa posición o '\0' si está fuera de rango
     */
    public static char getChar(int index, String idioma) {
        String alfabeto = getAlfabeto(idioma);
        if (index >= 0 && index < alfabeto.length()) {
            return alfabeto.charAt(index);
        }
        return '\0';
    }

    /**
     * Retorna la cantidad de letras en el alfabeto especificado.
     * @param idioma Clave del alfabeto
     * @return Número de letras
     */
    public static int getSize(String idioma) {
        return getAlfabeto(idioma).length();
    }

    /**
     * Normaliza un texto: elimina caracteres que no están en el alfabeto.
     * @param texto Texto a normalizar
     * @param idioma Clave del alfabeto
     * @return Texto normalizado (solo caracteres del alfabeto)
     */
    public static String normalizar(String texto, String idioma) {
        if (texto == null || texto.isEmpty()) {
            return "";
        }

        StringBuilder resultado = new StringBuilder();
        String alfabeto = getAlfabeto(idioma);

        for (char c : texto.toCharArray()) {
            char mayuscula = Character.toUpperCase(c);
            // Solo incluye caracteres que están en el alfabeto (sin acentos)
            if (alfabeto.indexOf(mayuscula) >= 0) {
                resultado.append(mayuscula);
            }
        }

        return resultado.toString();
    }

    /**
     * Normaliza la clave del alfabeto para búsquedas.
     */
    private static String normalizarClave(String idioma) {
        if (idioma == null) {
            return "INGLES";
        }
        return idioma.toUpperCase().replace("Á", "A").replace("É", "E")
                     .replace("Í", "I").replace("Ó", "O").replace("Ú", "U")
                     .replace("Ñ", "Ñ");
    }

    /**
     * Retorna el carácter original sin cambios.
     * Los caracteres con acentos serán ignorados en getIndex() al retornar -1.
     * @param c Carácter original
     * @return El mismo carácter sin modificación
     */
    public static char normalizarChar(char c) {
        return c;
    }
}