# Sistema de Cifrado Clásico

> Aplicación de escritorio para aprender y experimentar con los métodos de cifrado clásicos más importantes de la historia de la criptografía.

[![Java](https://img.shields.io/badge/Java-17-blue?style=flat-square&logo=java)](https://www.java.com/)
[![Swing](https://img.shields.io/badge/Swing-GUI-green?style=flat-square)](https://docs.oracle.com/javase/tutorial/uiswing/)
[![Licencia](https://img.shields.io/badge/Licencia-MIT-yellow?style=flat-square)](LICENSE)

---

## Descripción

**Sistema de Cifrado Clásico** es una aplicación de escritorio desarrollada en Java con Swing que implementa los métodos de cifrado más icónicos de la criptografía clásica. Fue diseñada con fines educativos para comprender cómo funcionan los algoritmos de cifrado tradicionales que sentaron las bases de la seguridad informática moderna.

La aplicación cuenta con una interfaz gráfica moderna, intuitiva y bilingüe (español/inglés), que permite cifrar y descifrar texto utilizando cinco métodos diferentes de cifrado clásico.

---

## Características

- **5 Métodos de Cifrado Clásicos**: César, Atbash, Vigenère, Rail Fence y Playfair
- **Interfaz Gráfica Moderna**: Diseño limpio y profesional con Java Swing
- **Soporte Bilingüe**: Completamente funcional en Español e Inglés
- **Cifrar y Descifrar**:双向操作 para ambos procesos
- **Textos largos**: Admite entradas de cualquier longitud con ajuste automático de tamaño de fuente
- **Mantenimiento**: Botón para limpiar campos rápidamente
- **Validación de clave**: Verifica el tipo de clave requerido por cada método

---

## Capturas de Interfaz

La aplicación presenta una interfaz dividida en tres paneles principales:

| Panel | Descripción |
|-------|-------------|
| **Panel Superior** | Campo de clave, selector de idioma, botones de método y descripción dinámica |
| **Panel Izquierdo** | Área de entrada de texto (texto claro o cifrado según operación) |
| **Panel Derecho** | Área de salida con el resultado (texto cifrado o descifrado) |

**Elementos destacados**:
- Botones toggle para seleccionar el método de cifrado
- Etiqueta dinámico que muestra la descripción del método seleccionado
- Indicador visual del tipo de clave requerida
- Botones grandes para CIFRAR y DESCIFRAR con estilo profesional

---

## Requisitos

### Requisitos del Sistema

| Requisito | Versión Mínima |
|-----------|----------------|
| **Java JDK** | 17 o superior |
| **Sistema Operativo** | Windows 10+, macOS 10.14+, Linux (Ubuntu 20.04+) |
| **Memoria RAM** | 256 MB mínimo |
| **Espacio en Disco** | 50 MB libre |

### Dependencias

- **Java Swing** (incluido en JDK desde Java 1.2)
- No se requieren librerías externas adicionales

---

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

1. Abrir el proyecto en IntelliJ IDEA
2. Seleccionar el SDK de Java 17+
3. Ejecutar la clase `Main`

### Opción 3: Ejecutable JAR (si está disponible)

```bash
java -jar Criptografia-Clasica.jar
```

---

## Cómo Usar

### Paso 1: Ejecutar la Aplicación

Después de instalar, ejecute la aplicación:
```bash
java -cp out com.criptografia.Main
```

### Paso 2: Seleccionar el Método de Cifrado

Haga clic en uno de los botones de método en el panel superior:
- **César** - Cifrado por desplazamiento
- **Atbash** - Inversión del alfabeto
- **Vigenère** - Cifrado polialfabético
- **Rail Fence** - Transposición en rieles
- **Playfair** - Matriz 5x5

### Paso 3: Configurar la Clave

Ingrese la clave según el método seleccionado:

| Método | Tipo de Clave | Ejemplo |
|--------|--------------|--------|
| César | Número entero (1-25) | `3` |
| Atbash | No requiere clave | - |
| Vigenère | Palabra o texto | `CLAVE` |
| Rail Fence | Número de rieles (2-10) | `3` |
| Playfair | Palabra sin espacios | `CLAVE` |

### Paso 4: Ingresar el Texto

Escriba el texto a cifrar o descifrar en el panel de **ENTRADA** (panel izquierdo).

### Paso 5: Cifrar o Descifrar

- **CIFRAR** - Para cifrar el texto de entrada
- **DESCIFRAR** - Para descifrar el texto de entrada

### Paso 6: Copiar el Resultado

El resultado aparecerá en el panel de **SALIDA** (panel derecho). puede copiarlo manualmente.

---

## Métodos de Cifrado Explicados

### 1. Cifrado César

El cifrado César es uno de los métodos más antiguos y simples. Consiste en desplazar cada letra un número fijo de posiciones en el alfabeto.

**Ejemplo** (desplazamiento = 3):
```
Texto original: HOLA
Texto cifrado: KROD
```

**Detalle**:
- A → D, B → E, C → F, ... X → A, Y → B, Z → C

### 2. Cifrado Atbash

El cifrado Atbash es un método de sustitución que invierte el orden del alfabeto. La primera letra se sustituye por la última, la segunda por la penúltima, etc.

**Ejemplo**:
```
Texto original: HOLA
Texto cifrado: TLOZ
```

**Detalle**:
- A ↔ Z, B ↔ Y, C ↔ X, ... M ↔ N, N ↔ M

### 3. Cifrado Vigenère

El cifrado Vigenère es un método polialfabético que utiliza una palabra clave para determinar el desplazamiento de cada letra. Es considerado uno de los cifrados más seguros de la era clásica.

**Ejemplo** (clave = "CLAVE"):
```
Texto original: HOLA
Texto cifrado: JVRF
```

**Detalle**: Cada letra se desplaza según la letra correspondientes de la clave.

### 4. Cifrado Rail Fence

El cifrado Rail Fence es un método de transposición que escribe el texto en rieles (filas) y lo lee diagonalmente.

**Ejemplo** (3 rieles):
```
Texto original: HOLA MUNDO
Texto cifrado: HOUDLNLA MU
```

**Detalle**: El texto se escribe en zigzag a través de múltiples rieles.

### 5. Cifrado Playfair

El cifrado Playfair es un método de sustitución digrámica que utiliza una matriz 5x5 para substituir pares de letras.

**Ejemplo** (clave = "PLAYFAIR"):
```
Texto original: HOLA
Texto cifrado: RKPS
```

**Detalle**: Usa una matriz de 5x5 construida con la clave.

---

## Estructura del Proyecto

```
Criptografia-Clasica/
├── src/
│   └── main/
│       └── java/
│           └── com/
│               └── criptografia/
│                   ├── Main.java                    # Punto de entrada
│                   ├── modelo/
│                   │   ├── EstrategiaCifrado.java  # Interfaz de cifrado
│                   │   ├── Alfabeto.java          # Gestión del alfabeto
│                   │   ├── CifradoCesar.java      # Cifrado César
│                   │   ├── CifradoAtbash.java      # Cifrado Atbash
│                   │   ├── CifradoVigenere.java   # Cifrado Vigenère
│                   │   ├── CifradoRailFence.java # Cifrado Rail Fence
│                   │   └── CifradoPlayfair.java   # Cifrado Playfair
│                   └── vista/
│                       └── VentanaPrincipal.java     # Interfaz gráfica
├── .idea/                                  # Configuración IntelliJ
├── Criptografia-Clasica.iml                # Archivo de proyecto
��── README.md                               # Este archivo
```

---

## Contribuidores

| Rol | Nombre | Contacto |
|-----|--------|---------|
| Desarrollador Principal | [Tu Nombre] | tu@email.com |
| Tutor/Asesor | [Nombre del Tutor] | tutor@email.com |

---

## Licencia

Este proyecto está bajo la licencia **MIT**. See [LICENSE](LICENSE) para más detalles.

---

## Recursos Adicionales

- [Historia de la Criptografía](https://es.wikipedia.org/wiki/Criptograf%C3%ADa)
- [Cifrado César - Wikipedia](https://es.wikipedia.org/wiki/Cifrado_C%C3%A9sar)
- [Cifrado Vigenère - Wikipedia](https://es.wikipedia.org/wiki/Cifrado_Vigen%C3%A8re)
- [Tutorial Java Swing](https://docs.oracle.com/javase/tutorial/uiswing/)

---

## Agradecimientos

Agradecimientos a todos los estudiantes y profesores que han contribuido a mejorar este proyecto.

---

*Made with ❤️ for education in cryptography*