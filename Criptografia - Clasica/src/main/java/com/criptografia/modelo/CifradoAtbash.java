package com.criptografia.modelo;

public class CifradoAtbash implements EstrategiaCifrado {
    @Override
    public String cifrar(String texto, String clave) {
        return procesar(texto);
    }

    @Override
    public String descifrar(String texto, String clave) {
        return procesar(texto); // Atbash es simétrico
    }

    private String procesar(String texto) {
        StringBuilder resultado = new StringBuilder();
        for (char c : texto.toCharArray()) {
            if (Character.isLetter(c)) {
                char base = Character.isUpperCase(c) ? 'A' : 'a';
                resultado.append((char) (base + 25 - (c - base)));
            } else {
                resultado.append(c);
            }
        }
        return resultado.toString();
    }

    @Override
    public String obtenerDescripcion() {
        return "Sustituye la primera letra del alfabeto por la última, la segunda por la penúltima, etc. No requiere clave.";
    }

    @Override
    public String obtenerNombre() {
        return "Atbash";
    }
}