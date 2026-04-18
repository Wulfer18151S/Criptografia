package com.criptografia.modelo;

public class CifradoPlayfair implements EstrategiaCifrado {
    private String idioma = "ESPAÑOL";
    

    @Override
    public String cifrar(String texto, String clave) {
        // Normalizar acentos antes de procesar
        if (texto != null) {
            texto = normalizarAcentos(texto);
        }
        
        char[][] matriz = construirMatriz(clave);
        String textoProcesado = prepararTexto(texto, true);
        StringBuilder resultado = new StringBuilder();
        
        for (int i = 0; i < textoProcesado.length(); i += 2) {
            char c1 = textoProcesado.charAt(i);
            char c2 = textoProcesado.charAt(i + 1);
            
            int[] pos1 = encontrarPosicion(matriz, c1);
            int[] pos2 = encontrarPosicion(matriz, c2);
            
            if (pos1[0] == pos2[0]) {
                resultado.append(matriz[pos1[0]][(pos1[1] + 1) % 5]);
                resultado.append(matriz[pos2[0]][(pos2[1] + 1) % 5]);
            } else if (pos1[1] == pos2[1]) {
                resultado.append(matriz[(pos1[0] + 1) % 5][pos1[1]]);
                resultado.append(matriz[(pos2[0] + 1) % 5][pos2[1]]);
            } else {
                resultado.append(matriz[pos1[0]][pos2[1]]);
                resultado.append(matriz[pos2[0]][pos1[1]]);
            }
        }
        return resultado.toString();
    }

    @Override
    public String descifrar(String texto, String clave) {
        // Normalizar acentos y limpiar sin procesar con prepararTexto
        if (texto != null) {
            texto = normalizarAcentos(texto);
        }
        String textoProcesado = texto.toUpperCase().replaceAll("[^A-Z]", "");
        
        char[][] matriz = construirMatriz(clave);
        StringBuilder resultado = new StringBuilder();
        
        for (int i = 0; i < textoProcesado.length(); i += 2) {
            char c1 = textoProcesado.charAt(i);
            char c2 = textoProcesado.charAt(i + 1);
            
            int[] pos1 = encontrarPosicion(matriz, c1);
            int[] pos2 = encontrarPosicion(matriz, c2);
            
            if (pos1[0] == pos2[0]) {
                resultado.append(matriz[pos1[0]][(pos1[1] + 4) % 5]);
                resultado.append(matriz[pos2[0]][(pos2[1] + 4) % 5]);
            } else if (pos1[1] == pos2[1]) {
                resultado.append(matriz[(pos1[0] + 4) % 5][pos1[1]]);
                resultado.append(matriz[(pos2[0] + 4) % 5][pos2[1]]);
            } else {
                resultado.append(matriz[pos1[0]][pos2[1]]);
                resultado.append(matriz[pos2[0]][pos1[1]]);
            }
        }
        return resultado.toString();
    }

    private char[][] construirMatriz(String clave) {
        char[][] matriz = new char[5][5];
        boolean[] letrasUsadas = new boolean[26];
        
        String claveLimpia = (clave == null || clave.trim().isEmpty()) ? "CLAVE" : clave.toUpperCase().replaceAll("[^A-Z]", "");
        if (claveLimpia.isEmpty()) claveLimpia = "CLAVE";
        claveLimpia = claveLimpia.replace("J", "I");
        
        StringBuilder builder = new StringBuilder();
        for (char c : claveLimpia.toCharArray()) {
            if (!letrasUsadas[c - 'A']) {
                builder.append(c);
                letrasUsadas[c - 'A'] = true;
            }
        }
        
        for (char c = 'A'; c <= 'Z'; c++) {
            if (c != 'J' && !letrasUsadas[c - 'A']) {
                builder.append(c);
            }
        }
        
        String alfabeto = builder.toString();
        int index = 0;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                matriz[i][j] = alfabeto.charAt(index++);
            }
        }
        
        return matriz;
    }

    private String prepararTexto(String texto, boolean cifrar) {
        texto = texto.toUpperCase().replaceAll("[^A-ZÑ]", "");
        texto = texto.replace("J", "I");
        
        if (texto.isEmpty()) {
            return texto;
        }
        
        StringBuilder builder = new StringBuilder();
        
        for (int i = 0; i < texto.length(); i++) {
            char c = texto.charAt(i);
            if (builder.length() % 2 == 0) {
                if (i + 1 < texto.length() && texto.charAt(i + 1) == c) {
                    builder.append(c);
                    builder.append('X');
                } else {
                    builder.append(c);
                    if (i + 1 < texto.length()) {
                        builder.append(texto.charAt(i + 1));
                        i++;
                    } else {
                        builder.append('X');
                    }
                }
            }
        }
        
        return builder.toString();
    }

    private int[] encontrarPosicion(char[][] matriz, char c) {
        int[] pos = new int[2];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (matriz[i][j] == c) {
                    pos[0] = i;
                    pos[1] = j;
                    return pos;
                }
            }
        }
        // Si no se encuentra, retornar -1,-1 para manejo de errores
        pos[0] = -1;
        pos[1] = -1;
        return pos;
    }

    /**
     * Normaliza acentos en un texto: Á→A, É→E, Í→I, Ó→O, Ú→U, Ñ→Ñ
     */
    private String normalizarAcentos(String texto) {
        if (texto == null || texto.isEmpty()) {
            return texto;
        }
        return texto.toUpperCase()
            .replace('Á', 'A').replace('À', 'A').replace('Â', 'A').replace('Ä', 'A')
            .replace('É', 'E').replace('È', 'E').replace('Ê', 'E').replace('Ë', 'E')
            .replace('Í', 'I').replace('Ì', 'I').replace('Î', 'I').replace('Ï', 'I')
            .replace('Ó', 'O').replace('Ò', 'O').replace('Ô', 'O').replace('Ö', 'O')
            .replace('Ú', 'U').replace('Ù', 'U').replace('Û', 'U').replace('Ü', 'U')
            .replace('Ñ', 'Ñ');
    }

    @Override
    public String obtenerDescripcion() {
        return "Usa una matriz 5x5 basada en la clave para sustituir letras por pares. Ingrese una palabra como clave.";
    }

    @Override
    public String obtenerNombre() {
        return "Playfair";
    }

    // Getter para idioma
    @Override
    public String getIdioma() {
        return idioma;
    }

    // Setter para idioma - maneja acentos correctamente
    @Override
    public void setIdioma(String idioma) {
        if (idioma != null && !idioma.isEmpty()) {
            String normalizado = idioma.toUpperCase()
                .replace('Á', 'A').replace('É', 'E').replace('Í', 'I')
                .replace('Ó', 'O').replace('Ú', 'U').replace('Ñ', 'N');
            
            // Mapeo completo de valores aceptados
            if (normalizado.equals("INGLES") || normalizado.equals("EN") || 
                normalizado.equals("ENGLISH") || normalizado.equals("ING")) {
                this.idioma = "INGLES";
            } else if (normalizado.equals("ESPAÑOL") || normalizado.equals("ESPANOL") || 
                       normalizado.equals("ES") || normalizado.equals("SP") ||
                       normalizado.equals("SPANISH")) {
                this.idioma = "ESPAÑOL";
            } else if (normalizado.equals("ESPANOL_EXTENDIDO") || normalizado.equals("ESPE") ||
                       normalizado.equals("EXTENDIDO") || normalizado.equals("EXT")) {
                this.idioma = "ESPANOL_EXTENDIDO";
            }
            // Si no coincide, mantener el idioma actual
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
