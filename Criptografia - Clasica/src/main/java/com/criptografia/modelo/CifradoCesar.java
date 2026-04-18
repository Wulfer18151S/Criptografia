package com.criptografia.modelo;

public class CifradoCesar implements EstrategiaCifrado {
    private String idioma = "ESPAÑOL";

    @Override
    public String cifrar(String texto, String clave) {
        int desplazamiento = parsearClave(clave);
        return procesarCifrar(texto, desplazamiento);
    }

    @Override
    public String descifrar(String texto, String clave) {
        int desplazamiento = parsearClave(clave);
        return procesarDescifrar(texto, desplazamiento);
    }

    private String procesarCifrar(String texto, int desplazamiento) {
        // CIFRADO: sumar desplazamiento
        StringBuilder resultado = new StringBuilder();
        int tamAlfabeto = Alfabeto.getSize(idioma);

        for (char c : texto.toCharArray()) {
            int index = Alfabeto.getIndex(c, idioma);
            if (index >= 0) {
                int nuevoIndex = (index + desplazamiento) % tamAlfabeto;
                char nuevoChar = Alfabeto.getChar(nuevoIndex, idioma);
                if (Character.isLowerCase(c)) {
                    nuevoChar = Character.toLowerCase(nuevoChar);
                }
                resultado.append(nuevoChar);
            } else {
                resultado.append(c);
            }
        }
        return resultado.toString();
    }
    
    private String procesarDescifrar(String texto, int desplazamiento) {
        // DESCIFRADO: restar desplazamiento (la operación inversa)
        StringBuilder resultado = new StringBuilder();
        int tamAlfabeto = Alfabeto.getSize(idioma);

        for (char c : texto.toCharArray()) {
            int index = Alfabeto.getIndex(c, idioma);
            if (index >= 0) {
                int nuevoIndex = index - desplazamiento;
                // Si es negativo, sumar el tamaño del alfabeto (módulo negativo)
                if (nuevoIndex < 0) {
                    nuevoIndex += tamAlfabeto;
                }
                char nuevoChar = Alfabeto.getChar(nuevoIndex, idioma);
                if (Character.isLowerCase(c)) {
                    nuevoChar = Character.toLowerCase(nuevoChar);
                }
                resultado.append(nuevoChar);
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
            return 3; // Desplazamiento por defecto
        }
    }

    @Override
    public String obtenerDescripcion() {
        String alfaDesc = "Español: A-Z+Ñ".equals(getAlfabetoDesc()) ? " (A-Z+Ñ)" : " (A-Z)";
        String infoClave = " | Desplazamiento por defecto: 3";
        return "Desplaza cada letra del texto un número fijo de posiciones en el alfabeto" + alfaDesc + infoClave + ".";
    }

    @Override
    public String obtenerNombre() {
        return "César";
    }

    @Override
    public String getIdioma() {
        return idioma;
    }

    @Override
    public void setIdioma(String idioma) {
        if (idioma != null && !idioma.isEmpty()) {
            String normalizado = idioma.toUpperCase()
                .replace('Á', 'A').replace('É', 'E').replace('Í', 'I')
                .replace('Ó', 'O').replace('Ú', 'U').replace('Ñ', 'N');
            
            if (normalizado.equals("INGLES") || normalizado.startsWith("ES")) {
                this.idioma = normalizado.startsWith("ING") ? "INGLES" :
                             normalizado.contains("EXT") ? "ESPANOL_EXTENDIDO" : "ESPAÑOL";
            }
        }
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
}