package main.java.com.criptografia.modelo;

public class CifradoCesar implements EstrategiaCifrado{
    @Override
    public String cifrar(String texto, String clave) {
        int desplazamiento = parsearClave(clave);
        return procesar(texto, desplazamiento);
    }

    @Override
    public String descifrar(String texto, String clave) {
        int desplazamiento = parsearClave(clave);
        return procesar(texto, 26 - (desplazamiento % 26));
    }

    private String procesar(String texto, int desplazamiento) {
        StringBuilder resultado = new StringBuilder();
        for (char c : texto.toCharArray()) {
            if (Character.isLetter(c)) {
                char base = Character.isUpperCase(c) ? 'A' : 'a';
                resultado.append((char) ((c - base + desplazamiento) % 26 + base));
            } else {
                resultado.append(c);
            }
        }
        return resultado.toString();
    }

    private int parsearClave(String clave) {
        try {
            return Integer.parseInt(clave);
        } catch (NumberFormatException e) {
            return 3; // Desplazamiento por defecto si no se ingresa un número
        }
    }

    @Override
    public String obtenerDescripcion() {
        return "Desplaza cada letra del texto un número fijo de posiciones en el alfabeto. (Ingrese un número en la clave).";
    }

    @Override
    public String obtenerNombre() {
        return "César";
    }
}
