package main.java.com.criptografia.modelo;

public class CifradoPlayfair implements EstrategiaCifrado {
    

    @Override
    public String cifrar(String texto, String clave) {
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
        char[][] matriz = construirMatriz(clave);
        String textoProcesado = prepararTexto(texto, false);
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
        texto = texto.toUpperCase().replaceAll("[^A-Z]", "");
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
        return pos;
    }

    @Override
    public String obtenerDescripcion() {
        return "Usa una matriz 5x5 basada en la clave para sustituir letras por pares. Ingrese una palabra como clave.";
    }

    @Override
    public String obtenerNombre() {
        return "Playfair";
    }
}
