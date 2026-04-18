package com.criptografia.modelo;

public class CifradoVigenere implements EstrategiaCifrado {

    private String idioma = "INGLES";

    @Override
    public String cifrar(String texto, String clave) {
        if (texto == null || texto.isEmpty()) {
            return "";
        }
        String claveValida = obtenerClaveValida(clave);
        StringBuilder resultado = new StringBuilder();
        int indiceClave = 0;
        int tamAlfabeto = Alfabeto.getSize(idioma);

        for (char c : texto.toCharArray()) {
            int indexLetra = Alfabeto.getIndex(c, idioma);
            if (indexLetra >= 0) {
                // Es una letra válida en el alfabeto del idioma
                int desplazamiento = Alfabeto.getIndex(claveValida.charAt(indiceClave), idioma);
                int nuevoIndex = (indexLetra + desplazamiento) % tamAlfabeto;
                char nuevoChar = Alfabeto.getChar(nuevoIndex, idioma);
                // Mantener mayúscula/minúscula del original
                if (Character.isLowerCase(c)) {
                    nuevoChar = Character.toLowerCase(nuevoChar);
                }
                resultado.append(nuevoChar);
                indiceClave = (indiceClave + 1) % claveValida.length();
            } else {
                // No es letra del alfabeto: mantener carácter tal cual
                resultado.append(c);
            }
        }
        return resultado.toString();
    }

    @Override
    public String descifrar(String texto, String clave) {
        if (texto == null || texto.isEmpty()) {
            return "";
        }
        String claveValida = obtenerClaveValida(clave);
        StringBuilder resultado = new StringBuilder();
        int indiceClave = 0;
        int tamAlfabeto = Alfabeto.getSize(idioma);

        for (char c : texto.toCharArray()) {
            int indexLetra = Alfabeto.getIndex(c, idioma);
            if (indexLetra >= 0) {
                // Es una letra válida en el alfabeto del idioma
                int desplazamiento = Alfabeto.getIndex(claveValida.charAt(indiceClave), idioma);
                int nuevoIndex = indexLetra - desplazamiento;
                if (nuevoIndex < 0) {
                    nuevoIndex += tamAlfabeto;
                }
                char nuevoChar = Alfabeto.getChar(nuevoIndex, idioma);
                // Mantener mayúscula/minúscula del original
                if (Character.isLowerCase(c)) {
                    nuevoChar = Character.toLowerCase(nuevoChar);
                }
                resultado.append(nuevoChar);
                indiceClave = (indiceClave + 1) % claveValida.length();
            } else {
                // No es letra del alfabeto: mantener carácter tal cual
                resultado.append(c);
            }
        }
        return resultado.toString();
    }

    private String obtenerClaveValida(String clave) {
        if (clave == null || clave.trim().isEmpty()) {
            return "CLAVE";
        }
        // Normalizar la clave al idioma seleccionado
        String claveNormalizada = Alfabeto.normalizar(clave, idioma);
        if (claveNormalizada.isEmpty()) {
            return "CLAVE";
        }
        return claveNormalizada;
    }

    @Override
    public String obtenerDescripcion() {
        String alfaDesc = getAlfabetoDesc();
        return "Utiliza una tabla donde cada letra se desplaza según una clave repetida. (" + alfaDesc + "). Ingrese una palabra como clave.";
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
        return "Vigenère";
    }

    // Getter para idioma
    public String getIdioma() {
        return idioma;
    }

    // Setter para idioma - maneja acentos correctamente
    @Override
    public void setIdioma(String idioma) {
        if (idioma != null && !idioma.isEmpty()) {
            // Normalizar: quitar acentos para comparar
            String normalizado = idioma.toUpperCase()
                .replace("Á", "A").replace("É", "E").replace("Í", "I")
                .replace("Ó", "O").replace("Ú", "U");

            if (normalizado.equals("INGLES") || normalizado.equals("ES") ||
                normalizado.equals("ESP") || normalizado.equals("ESPE")) {
                this.idioma = normalizado.startsWith("ING") ? "INGLES" :
                             normalizado.startsWith("ESPE") ? "ESPANOL_EXTENDIDO" : "ESPAÑOL";
            }
        }
    }
}