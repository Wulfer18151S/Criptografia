package com.criptografia.modelo;

public interface EstrategiaCifrado {
    String cifrar(String texto, String clave);
    String descifrar(String texto, String clave);
    String obtenerDescripcion();
    String obtenerNombre();
}