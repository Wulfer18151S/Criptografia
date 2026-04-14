# Especificación de Cifrados Clásicos

## Purpose

Definir los requisitos y comportamientos de los algoritmos de cifrado clásico en la aplicación, incluyendo la interfaz de usuario.

## ADDED Requirements

### Requirement: Algoritmo Vigenère

El sistema DEBE implementar el algoritmo de cifrado Vigenère que utiliza una clave alfabética para cifrar y descifrar texto.

#### Scenario: Cifrado con clave válidos

- GIVEN texto "HOLA" y clave "CLAVE"
- WHEN se ejecuta cifrar
- THEN el resultado DEBE ser el texto cifrado usando el cifrado Vigenère

#### Scenario: Descifrado con clave válidos

- GIVEN texto cifrado y clave "CLAVE"
- WHEN se ejecuta descifrar
- THEN el resultado DEBE restaurar el texto original

#### Scenario: Clave vacía

- GIVEN texto a cifrar y clave vacía
- WHEN se intenta cifrar
- THEN el sistema DEBE usar una clave por defecto o manejar el caso apropiadamente

---

### Requirement: Algoritmo Rail Fence

El sistema DEBE implementar el algoritmo Rail Fence que utiliza un número de rieles para cifrar y descifrar.

#### Scenario: Cifrado con rieles válidos

- GIVEN texto "HOLA" y rieles "3"
- WHEN se ejecuta cifrar
- THEN el resultado DEBE estar cifrado en formato rail fence

#### Scenario: Descifrado con rieles válidos

- GIVEN texto cifrado y rieles "3"
- WHEN se ejecuta descifrar
- THEN el resultado DEBE restaurar el texto original

#### Scenario: Número de rieles inválido

- GIVEN texto y rieles "0" o valor no numérico
- WHEN se intenta cifrar
- THEN el sistema DEBE usar 2 rieles por defecto

---

### Requirement: Algoritmo Playfair

El sistema DEBE implementar el algoritmo Playfair que utiliza una matriz 5x5 basada en una clave alfabética.

#### Scenario: Cifrado con clave válida

- GIVEN texto "HOLA MUNDO" y clave "SECRETO"
- WHEN se ejecuta cifrar
- THEN el resultado DEBE estar cifrado usando Playfair

#### Scenario: Descifrado con clave válida

- GIVEN texto cifrado y clave "SECRETO"
- WHEN se ejecuta descifrar
- THEN el resultado DEBE restaurar el texto original

#### Scenario: Texto con letras duplicadas en par

- GIVEN texto "HOLA" donde LL forma un par duplicado
- WHEN se ejecuta cifrar
- THEN el sistema DEBE insertar una letra separadora (ej: X) entre las duplicadas

---

### Requirement: Botón Limpiar

El sistema DEBE incluir un botón que limpie todos los campos de texto de la interfaz.

#### Scenario: Botón limpiar presionado

- GIVEN texto en áreas de origen y destino
- WHEN se presiona el botón Limpiar
- THEN ambas áreas de texto DEBEN quedar vacías
- AND el campo de clave DEBE quedar vacío

---

### Requirement: Campo de Clave Dinámico

El sistema DEBE mostrar u ocultar el campo de clave según los requerimientos del algoritmo seleccionado.

#### Scenario: Algoritmo con clave requerida

- GIVEN algoritmo César o Vigenère seleccionado
- WHEN se cambia de algoritmo
- THEN el campo de clave DEBE ser visible

#### Scenario: Algoritmo sin clave requerida

- GIVEN algoritmo Atbash seleccionado
- WHEN se cambia de algoritmo
- THEN el campo de clave DEBE ocultarse o mostrarse como deshabilitado

---

### Requirement: Descripción Dinámica

El sistema DEBE actualizar la descripción mostrada según el algoritmo seleccionado.

#### Scenario: Cambio de algoritmo

- GIVEN cualquier algoritmo seleccionado
- WHEN se selecciona un algoritmo diferente
- THEN la descripción DEBE actualizar para mostrar la explicación del nuevo método

---

### MODIFIED Requirements

### Requirement: Carga de Algoritmos en UI

(Anteriormente: Solo se cargaban César y Atbash)

El sistema DEBE cargar los 5 algoritmos (César, Atbash, Vigenère, Rail Fence, Playfair) en el panel de selección de métodos.

#### Scenario: Carga inicial

- GIVEN aplicación iniciando
- WHEN se carga la ventana principal
- THEN los 5 botones DEBERAN aparecer en el panel de métodos