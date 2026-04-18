package com.criptografia.modelo;

public class CifradoAtbash implements EstrategiaCifrado {

    private String idioma = "ESPAÑOL";

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
            int index = Alfabeto.getIndex(c, idioma);
            if (index >= 0) {
                // Fórmula Atbash: nuevaPosition = size - 1 - posicionOriginal
                // Inglés (26): A(0) → Z(25), B(1) → Y(24)
                // Español (27): A(0) → Ñ(26), B(1) → Ñ-1(25)
                int size = Alfabeto.getSize(idioma);
                int nuevaPosicion = size - 1 - index;
                char nuevoChar = Alfabeto.getChar(nuevaPosicion, idioma);
                // Mantener mayúscula/minúscula según el original
                if (Character.isUpperCase(c)) {
                    resultado.append(nuevoChar);
                } else {
                    resultado.append(Character.toLowerCase(nuevoChar));
                }
            } else {
                resultado.append(c);
            }
        }
        return resultado.toString();
    }

    @Override
    public String obtenerDescripcion() {
        String alfaDesc = getAlfabetoDesc();
        return "Sustituye la primera letra del alfabeto por la última, la segunda por la penúltima, etc. (" + alfaDesc + "). No requiere clave.";
    }

    @Override
    public String getAlfabetoDesc() {
        if ("INGLES".equals(idioma)) {
            return "Inglés: A-Z";
        } else if ("ESPAÑOL".equals(idioma)) {
            return "Español: A-Z+Ñ";
        } else {
            return "Español extendido";
        }
    }

    @Override
    public String obtenerNombre() {
        return "Atbash";
    }

    // Getter para idioma
    public String getIdioma() {
        return idioma;
    }

    // Setter para idioma - maneja acentos correctamente
    @Override
    public void setIdioma(String idioma) {
        if (idioma != null && !idioma.isEmpty()) {
            String normalizado = idioma.toUpperCase()
                .replace('Á', 'A').replace('É', 'E').replace('Í', 'I')
                .replace('Ó', 'O').replace('Ú', 'U');  // NO incluir Ñ
            
            if (normalizado.equals("INGLES") || normalizado.startsWith("ES")) {
                this.idioma = normalizado.startsWith("ING") ? "INGLES" :
                             normalizado.contains("EXT") ? "ESPANOL_EXTENDIDO" : "ESPAÑOL";
            }
        }
    }
}