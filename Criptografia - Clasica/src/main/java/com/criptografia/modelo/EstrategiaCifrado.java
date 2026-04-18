package com.criptografia.modelo;

public interface EstrategiaCifrado {
    String cifrar(String texto, String clave);
    String descifrar(String texto, String clave);
    String obtenerDescripcion();
    String obtenerNombre();

    // Métodos por defecto para idioma (opcionales)
    default String getIdioma() {
        return "INGLES";
    }

    default void setIdioma(String idioma) {
        // Default no hace nada - cada subclase puede overrides
    }

    default String getAlfabetoDesc() {
        return "";
    }
}