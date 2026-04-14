package com.criptografia.modelo;

public class CifradoVigenere implements com.criptografia.modelo.EstrategiaCifrado {
    
    @Override
    public String cifrar(String texto, String clave) {
        String claveValida = obtenerClaveValida(clave);
        StringBuilder resultado = new StringBuilder();
        int indiceClave = 0;
        
        for (char c : texto.toCharArray()) {
            if (Character.isLetter(c)) {
                char base = Character.isUpperCase(c) ? 'A' : 'a';
                int desplazamiento = Character.toUpperCase(claveValida.charAt(indiceClave)) - 'A';
                resultado.append((char) ((c - base + desplazamiento) % 26 + base));
                indiceClave = (indiceClave + 1) % claveValida.length();
            } else {
                resultado.append(c);
            }
        }
        return resultado.toString();
    }

    @Override
    public String descifrar(String texto, String clave) {
        String claveValida = obtenerClaveValida(clave);
        StringBuilder resultado = new StringBuilder();
        int indiceClave = 0;
        
        for (char c : texto.toCharArray()) {
            if (Character.isLetter(c)) {
                char base = Character.isUpperCase(c) ? 'A' : 'a';
                int desplazamiento = Character.toUpperCase(claveValida.charAt(indiceClave)) - 'A';
                int nuevoCaracter = (c - base - desplazamiento) % 26;
                if (nuevoCaracter < 0) {
                    nuevoCaracter += 26;
                }
                resultado.append((char) (nuevoCaracter + base));
                indiceClave = (indiceClave + 1) % claveValida.length();
            } else {
                resultado.append(c);
            }
        }
        return resultado.toString();
    }

    private String obtenerClaveValida(String clave) {
        if (clave == null || clave.trim().isEmpty()) {
            return "CLAVE";
        }
        return clave.toUpperCase().replaceAll("[^A-Z]", "");
    }

    @Override
    public String obtenerDescripcion() {
        return "Utiliza una tabla (cuadrado de Vigenère) donde cada letra se desplaza según una clave repetida. Ingrese una palabra como clave.";
    }

    @Override
    public String obtenerNombre() {
        return "Vigenère";
    }
}
