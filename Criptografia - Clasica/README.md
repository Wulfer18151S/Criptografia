# Sistema de Cifrado Clásico

> Aplicación de escritorio para aprender y experimentar con los métodos de cifrado clásicos más importantes de la historia de la criptografía.

[](https://www.java.com/)
[](https://docs.oracle.com/javase/tutorial/uiswing/)
[](https://www.google.com/search?q=LICENSE)

-----

## Descripción

**Sistema de Cifrado Clásico** es una aplicación de escritorio desarrollada en Java con Swing que implementa los métodos de cifrado más icónicos de la criptografía clásica. Fue diseñada con fines educativos para comprender cómo funcionan los algoritmos de cifrado tradicionales que sentaron las bases de la seguridad informática moderna.

La aplicación cuenta con una interfaz gráfica moderna, intuitiva y bilingüe (español/inglés), que permite cifrar y descifrar texto utilizando cinco métodos diferentes de cifrado clásico.

-----

## Características

  - **5 Métodos de Cifrado Clásicos**: César, Atbash, Vigenère, Rail Fence y Playfair.
  - **Interfaz Gráfica Moderna**: Diseño limpio y profesional con Java Swing.
  - **Soporte Bilingüe**: Completamente funcional en Español e Inglés.
  - **Operación Bidireccional**: Soporta tanto el proceso de cifrado como el de descifrado.
  - **Textos largos**: Admite entradas de cualquier longitud con ajuste automático de tamaño de fuente.
  - **Mantenimiento**: Botón para limpiar campos rápidamente.
  - **Validación de clave**: Verifica el tipo de clave requerido por cada método.

-----

## Capturas de Interfaz

La aplicación presenta una interfaz dividida en tres paneles principales:

| Panel | Descripción |
|-------|-------------|
| **Panel Superior** | Campo de clave, selector de idioma, botones de método y descripción dinámica. |
| **Panel Izquierdo** | Área de entrada de texto (texto claro o cifrado según operación). |
| **Panel Derecho** | Área de salida con el resultado (texto cifrado o descifrado). |

**Elementos destacados**:

  - Botones *toggle* para seleccionar el método de cifrado.
  - Etiqueta dinámica que muestra la descripción del método seleccionado.
  - Indicador visual del tipo de clave requerida.
  - Botones de acción rápida para CIFRAR y DESCIFRAR con estilo profesional.

-----

## Requisitos

### Requisitos del Sistema

| Requisito | Versión Mínima |
|-----------|----------------|
| **Java JDK** | 17 o superior |
| **Sistema Operativo** | Windows 10+, macOS 10.14+, Linux (Ubuntu 20.04+) |
| **Memoria RAM** | 256 MB mínimo |
| **Espacio en Disco** | 50 MB libre |

### Dependencias

  - **Java Swing** (incluido en el JDK).
  - No se requieren librerías externas adicionales.

-----

## Instalación

### Opción 1: Compilar desde código fuente

```bash
# 1. Clonar o descargar el proyecto
git clone https://github.com/tu-usuario/criptografia-clasica.git
cd criptografia-clasica

# 2. Compilar el proyecto
javac -d out src/main/java/com/criptografia/**/*.java

# 3. Ejecutar la aplicación
java -cp out com.criptografia.Main
```

### Opción 2: Usar IntelliJ IDEA

1.  Abrir el proyecto en IntelliJ IDEA.
2.  Seleccionar el SDK de Java 17+.
3.  Ejecutar la clase `Main`.

### Opción 3: Ejecutable JAR (si está disponible)

```bash
java -jar Criptografia-Clasica.jar
```

-----

## Cómo Usar

1.  **Seleccionar el Método**: Haz clic en uno de los botones (César, Atbash, Vigenère, Rail Fence o Playfair).
2.  **Configurar la Clave**: Ingresa el valor correspondiente (número o palabra según el método).
3.  **Ingresar el Texto**: Escribe en el panel de **ENTRADA**.
4.  **Procesar**: Presiona **CIFRAR** o **DESCIFRAR** para obtener el resultado en el panel de **SALIDA**.

-----

## Métodos de Cifrado Explicados

### 1\. Cifrado César

Desplazamiento de cada letra un número fijo de posiciones en el alfabeto.

### 2\. Cifrado Atbash

Sustitución que invierte el alfabeto (A ↔ Z, B ↔ Y).

### 3\. Cifrado Vigenère

Método polialfabético que usa una palabra clave para variar el desplazamiento.

### 4\. Cifrado Rail Fence

Transposición que escribe el texto en zigzag a través de "rieles" imaginarios.

### 5\. Cifrado Playfair

Sustitución digrámica basada en una matriz de 5x5.

-----

## Estructura del Proyecto

```
Criptografia-Clasica/
├── src/
│   └── main/
│       └── java/
│           └── com/
│               └── criptografia/
│                   ├── Main.java                # Punto de entrada
│                   ├── modelo/                  # Lógica de negocio
│                   │   ├── EstrategiaCifrado.java
│                   │   ├── Alfabeto.java
│                   │   ├── CifradoCesar.java
│                   │   ├── CifradoAtbash.java
│                   │   ├── CifradoVigenere.java
│                   │   ├── CifradoRailFence.java
│                   │   └── CifradoPlayfair.java
│                   └── vista/                   # Interfaz de usuario
│                       └── VentanaPrincipal.java
├── README.md
└── LICENSE
```

-----

## Licencia

Este proyecto está bajo la licencia **MIT**. Consulta el archivo [LICENSE](https://www.google.com/search?q=LICENSE) para más detalles.

-----

*Hecho con ❤️ para la educación en criptografía*
