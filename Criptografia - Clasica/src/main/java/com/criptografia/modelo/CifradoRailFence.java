package com.criptografia.modelo;

public class CifradoRailFence implements EstrategiaCifrado {
    private String idioma = "ESPAÑOL";  // Campo para idioma
     
    @Override
    public String cifrar(String texto, String clave) {
        int numRieles = obtenerNumeroRieles(clave);
        if (numRieles < 2 || texto == null || texto.isEmpty()) {
            return texto;
        }
        
        String textoSinEspacios = texto.replaceAll("\\s+", "");
        if (textoSinEspacios.isEmpty()) {
            return texto;
        }
        
        StringBuilder[] rieles = new StringBuilder[numRieles];
        for (int i = 0; i < numRieles; i++) {
            rieles[i] = new StringBuilder();
        }
        
        int rielActual = 0;
        boolean descendiendo = true;
        
        for (char c : textoSinEspacios.toCharArray()) {
            rieles[rielActual].append(c);
            if (numRieles > 1) {
                if (descendiendo) {
                    rielActual++;
                    if (rielActual >= numRieles) {
                        rielActual = numRieles - 2;
                        descendiendo = false;
                    }
                } else {
                    rielActual--;
                    if (rielActual < 0) {
                        rielActual = 1;
                        descendiendo = true;
                    }
                }
            }
        }
        
        StringBuilder resultado = new StringBuilder();
        for (int i = 0; i < numRieles; i++) {
            resultado.append(rieles[i].toString());
        }
        return resultado.toString();
    }

    @Override
    public String descifrar(String texto, String clave) {
        int numRieles = obtenerNumeroRieles(clave);
        if (numRieles < 2 || texto == null || texto.isEmpty()) {
            return texto;
        }

        String textoCifrado = texto.replaceAll("\\s+", "");
        if (textoCifrado.isEmpty()) {
            return texto;
        }

        int longitud = textoCifrado.length();
        int[] longitudPorRiel = new int[numRieles];

        // Paso 1: Calcular cuántos caracteres van en cada riel (simulando el zigzag)
        int rielActual = 0;
        boolean descendiendo = true;

        for (int i = 0; i < longitud; i++) {
            longitudPorRiel[rielActual]++;
            if (numRieles > 1) {
                if (descendiendo) {
                    rielActual++;
                    if (rielActual >= numRieles) {
                        rielActual = numRieles - 2;
                        descendiendo = false;
                    }
                } else {
                    rielActual--;
                    if (rielActual < 0) {
                        rielActual = 1;
                        descendiendo = true;
                    }
                }
            }
        }

        // Paso 2: Dividir el texto en chunks del tamaño de cada riel
        StringBuilder[] rieles = new StringBuilder[numRieles];
        int[] posicionEnRiel = new int[numRieles];
        int offset = 0;

        for (int i = 0; i < numRieles; i++) {
            int tamChunk = longitudPorRiel[i];
            rieles[i] = new StringBuilder(textoCifrado.substring(offset, offset + tamChunk));
            offset += tamChunk;
        }

        // Paso 3: Reconstruir el zigzag leyendo alternando rieles
        StringBuilder resultado = new StringBuilder();
        rielActual = 0;
        descendiendo = true;

        for (int i = 0; i < longitud; i++) {
            resultado.append(rieles[rielActual].charAt(posicionEnRiel[rielActual]++));
            if (numRieles > 1) {
                if (descendiendo) {
                    rielActual++;
                    if (rielActual >= numRieles) {
                        rielActual = numRieles - 2;
                        descendiendo = false;
                    }
                } else {
                    rielActual--;
                    if (rielActual < 0) {
                        rielActual = 1;
                        descendiendo = true;
                    }
                }
            }
        }

        return resultado.toString();
    }

    private int obtenerNumeroRieles(String clave) {
        try {
            int num = Integer.parseInt(clave);
            return Math.max(2, num);
        } catch (NumberFormatException e) {
            return 3;
        }
    }

    @Override
    public String obtenerDescripcion() {
        return "Escribe el texto en filas (rieles) y lo lee diagonalmente. Ingrese el número de rieles como clave.";
    }

    @Override
    public String obtenerNombre() {
        return "Rail Fence";
    }

    // Idiomas soportados (RailFence no usa alfabeto específico, pero mantiene la interfaz)
    @Override
    public String getIdioma() {
        return "ESPAÑOL";  // Default
    }

    // Setter para idioma - maneja acentos correctamente
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
        return "N/A";
    }
}
