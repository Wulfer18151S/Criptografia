package com.criptografia.modelo;

public class CifradoRailFence implements EstrategiaCifrado {
    
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
        
        String textoSinEspacios = texto.replaceAll("\\s+", "");
        if (textoSinEspacios.isEmpty()) {
            return texto;
        }
        
        int longitud = textoSinEspacios.length();
        int[] longitudPorRiel = new int[numRieles];
        
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
        
        StringBuilder[] rieles = new StringBuilder[numRieles];
        int[] indices = new int[numRieles];
        
        for (int i = 0; i < numRieles; i++) {
            rieles[i] = new StringBuilder(textoSinEspacios.substring(0, longitudPorRiel[i]));
            textoSinEspacios = textoSinEspacios.substring(longitudPorRiel[i]);
        }
        
        StringBuilder resultado = new StringBuilder();
        rielActual = 0;
        descendiendo = true;
        
        for (int i = 0; i < longitud; i++) {
            resultado.append(rieles[rielActual].charAt(indices[rielActual]++));
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
}
