package com.criptografia.vista;

import com.criptografia.modelo.Alfabeto;
import com.criptografia.modelo.CifradoAtbash;
import com.criptografia.modelo.CifradoCesar;
import com.criptografia.modelo.CifradoPlayfair;
import com.criptografia.modelo.CifradoRailFence;
import com.criptografia.modelo.CifradoVigenere;
import com.criptografia.modelo.EstrategiaCifrado;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;
import java.util.List;

public class VentanaPrincipal extends JFrame {

    // ═══════════════════════════════════════════════════════════════════
    // PALETA DE COLORES MODO CLARO (DISEÑO LIMPIO Y MODERNO)
    // ═══════════════════════════════════════════════════════════════════
    private static final Color COLOR_FONDO      = new Color(248, 249, 250);  // Fondo suave
    private static final Color COLOR_PANEL      = Color.WHITE;                  // Blanco limpio
    private static final Color COLOR_ACENTO    = new Color(52, 152, 219);     // Azul profesional
    private static final Color COLOR_ACENTO_HOVER = new Color(41, 128, 185); // Azul más oscuro
    private static final Color COLOR_TEXTO     = new Color(44, 62, 80);       // Gris oscuro
    private static final Color COLOR_BORDE    = new Color(189, 195, 199);   // Borde sutil
    private static final Color COLOR_DESCRIPCION = new Color(0xE3, 0xF2, 0xFD);    // Celeste claro #E3F2FD
    private static final Color COLOR_DESC_TXT = new Color(30, 45, 75);   // Texto oscuro legible sobre celeste

    // COLORES PARA BOTONES DE ACCIÓN (FlatLaf style)
    private static final Color COLOR_BTN_CIFRAR    = new Color(0, 0, 0);           // #000000 Negro
    private static final Color COLOR_BTN_CIFRAR_TXT = new Color(255, 255, 255);       // #FFFFFF Blanco
    private static final Color COLOR_BTN_DESCIFRAR = new Color(0, 0, 0);            // #000000 Negro (mismo que CIFRAR)
    private static final Color COLOR_BTN_DESCIFRAR_TXT = new Color(255, 255, 255); // #FFFFFF Blanco

    private static final Color COLOR_BTN_NORMAL  = Color.WHITE;
    private static final Color COLOR_BTN_ACTIVO = new Color(52, 152, 219);     // Azul activo
    private static final Color COLOR_BTN_ACTIVO_TXT = Color.WHITE;
    private static final Color COLOR_BTN_HOVER  = new Color(230, 240, 250);     // Azul muy claro hover
    private static final Color COLOR_SOMBRA    = new Color(200, 210, 220);   // Sombra sutil
    private static final Color COLOR_FONDO_INPUT = new Color(252, 252, 253);     // Fondo input
    private static final Color COLOR_ETIQUETA_TITULO = Color.WHITE;
    private static final Color COLOR_BTN_METODO_TXT = new Color(44, 62, 80);

    // ═══════════════════════════════════════════════════════════════════
    // BORDES REDONDEADOS AUXILIARES
    // ═══════════════════════════════════════════════════════════════════
    private static final int RADIO_BTN = 8;
    private static final int RADIO_INPUT = 6;

    // ── Fuentes ────────────────────────────────────────────────────────────────
    private static final Font FUENTE_TITULO     = new Font("Segoe UI", Font.BOLD,  14);
    private static final Font FUENTE_NORMAL     = new Font("Segoe UI", Font.PLAIN, 13);
    private static final Font FUENTE_BTN_METODO = new Font("Segoe UI", Font.BOLD,  12);
    private static final Font FUENTE_BTN_ACCION = new Font("Segoe UI", Font.BOLD,  15);
    private static final Font FUENTE_AREA       = new Font("Consolas",  Font.PLAIN, 13);
    private static final Font FUENTE_DESC       = new Font("Segoe UI", Font.ITALIC, 12);

    // ── Componentes ───────────────────────────────────────────────────────────
    private JTextField txtClave;
    private JLabel     lblDescripcion;
    private JLabel     lblTipoClave;   // Nuevo: ayuda dinámica del tipo de clave
    private JTextArea  txtOrigen;
    private JTextArea  txtDestino;
    private JScrollPane scrollOrigen;
    private JScrollPane scrollDestino;
    private EstrategiaCifrado estrategiaActual;
    private String idiomaActual = "ESPAÑOL";
    private JComboBox<String> comboIdioma;

    /** Lista para controlar qué botón de método está activo */
    private final List<JButton> botonesMetodo = new ArrayList<>();

    /** Lista de estrategias para poder actualizar su idioma */
    private final List<EstrategiaCifrado> estrategias = new ArrayList<>();

    // ──────────────────────────────────────────────────────────────────────────
    public VentanaPrincipal() {
        // Configurar anti-aliasing para texto profesional
        configurarAntiAliasing();

        setTitle("Sistema de Cifrado Clásico");
        setSize(1050, 680);
        setMinimumSize(new Dimension(820, 560));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(0, 0));
        getContentPane().setBackground(COLOR_FONDO);

        inicializarComponentes();
        // La estrategia por defecto ya se seleccionó en crearPanelSuperior()
        // Solo marcamos el botón activo
        actualizarDescripcion();
        marcarBotonActivo(0);   // primer botón = César
    }

    // ── Construcción de la UI ─────────────────────────────────────────────────
    private void inicializarComponentes() {
        add(crearPanelSuperior(), BorderLayout.NORTH);
        add(crearPanelCentral(),  BorderLayout.CENTER);
    }

    // ── Panel superior: clave + métodos + descripción ─────────────────────────
    private JPanel crearPanelSuperior() {
        panelSuperior = new JPanel(new BorderLayout(0, 8));
        panelSuperior.setBackground(COLOR_PANEL);
        panelSuperior.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, COLOR_BORDE),
                new EmptyBorder(14, 18, 12, 18)
        ));

        // ── Fila 1: campo CLAVE ────────────────────────────────────────────
        JPanel filaClave = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        filaClave.setBackground(COLOR_PANEL);

        lblClave = new JLabel("CLAVE:");
        lblClave.setFont(FUENTE_TITULO);
        lblClave.setForeground(COLOR_TEXTO);
        filaClave.add(lblClave);

        txtClave = new JTextField(22);
        txtClave.setFont(FUENTE_NORMAL);
        txtClave.setPreferredSize(new Dimension(220, 38));

        // Eliminar bordes 3D, fondo más oscuro, borde sutil, padding interno
        txtClave.setOpaque(true);
        txtClave.setBackground(COLOR_FONDO_INPUT);
        txtClave.setForeground(COLOR_TEXTO);
        txtClave.setCaretColor(COLOR_TEXTO);
        txtClave.setBorder(crearBordeInputRedondeado(COLOR_BORDE, 8));

        // Focus listener para borde accentuado cuando tenga foco
        txtClave.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                if (txtClave.isEnabled()) {
                    txtClave.setBorder(crearBordeInputRedondeado(COLOR_ACENTO, 8));
                }
            }
            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                txtClave.setBorder(crearBordeInputRedondeado(COLOR_BORDE, 8));
            }
        });

        filaClave.add(txtClave);

        // Etiqueta de ayuda del tipo de clave (se actualiza dinámicamente)
        lblTipoClave = new JLabel("Tipo: Número (1-25)");
        lblTipoClave.setFont(FUENTE_DESC);
        lblTipoClave.setForeground(COLOR_DESC_TXT);
        lblTipoClave.setOpaque(true);
        lblTipoClave.setBackground(COLOR_DESCRIPCION);
        // Borde redondeado
        lblTipoClave.setBorder(crearBordeRedondeado(RADIO_INPUT, COLOR_BORDE, 4, 10, 4, 10));
        filaClave.add(lblTipoClave);

        // Selector de idioma (estilo minimalista FlatLaf)
        lblIdioma = new JLabel("  IDIOMA:");
        lblIdioma.setFont(FUENTE_TITULO);
        lblIdioma.setForeground(COLOR_TEXTO);
        filaClave.add(lblIdioma);

        comboIdioma = new JComboBox<>(new String[]{"ESPAÑOL", "INGLÉS"});
        comboIdioma.setSelectedItem(idiomaActual);
        comboIdioma.setFont(FUENTE_NORMAL);
        comboIdioma.setPreferredSize(new Dimension(110, 36));

        // Estilo minimalista: sin borde 3D, fondo FlatLaf
        comboIdioma.setOpaque(true);
        comboIdioma.setBackground(COLOR_PANEL);
        comboIdioma.setForeground(COLOR_TEXTO);
        comboIdioma.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_BORDE, 1, true),
                new EmptyBorder(4, 8, 4, 8)
        ));

        comboIdioma.addActionListener(e -> {
            idiomaActual = (String) comboIdioma.getSelectedItem();
            actualizarIdiomas();
            actualizarDescripcion();
        });
        filaClave.add(comboIdioma);

// Espaciador
        filaClave.add(Box.createHorizontalStrut(20));

        // ── Fila 2: botones de método ──────────────────────────────────────
        JPanel filaMetodos = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        filaMetodos.setBackground(COLOR_PANEL);

        lblMetodo = new JLabel("MÉTODO:");
        lblMetodo.setFont(FUENTE_TITULO);
        lblMetodo.setForeground(COLOR_TEXTO);
        filaMetodos.add(lblMetodo);

        // Crear estrategias y guardarlas para luego actualizar su idioma
        CifradoCesar cesar = new CifradoCesar();
        CifradoAtbash atbash = new CifradoAtbash();
        CifradoVigenere vigenere = new CifradoVigenere();
        CifradoRailFence railFence = new CifradoRailFence();
        CifradoPlayfair playfair = new CifradoPlayfair();

        estrategias.clear();
        estrategias.add(cesar);
        estrategias.add(atbash);
        estrategias.add(vigenere);
        estrategias.add(railFence);
        estrategias.add(playfair);

        // Inicializar estrategia por defecto
        estrategiaActual = cesar;
        actualizarIdiomas();  // Aplicar idioma inicial a todas las estrategias

        List<EstrategiaCifrado> metodos = new ArrayList<>(estrategias);

        for (int i = 0; i < metodos.size(); i++) {
            final int idx         = i;
            final EstrategiaCifrado metodo = metodos.get(idx);
            JButton btn = crearBotonMetodo(metodo.obtenerNombre());
            btn.addActionListener(e -> {
                estrategiaActual = metodo;
                actualizarDescripcion();
                marcarBotonActivo(idx);
            });
            botonesMetodo.add(btn);
            filaMetodos.add(btn);
        }

        // Separador visual
        JSeparator sep = new JSeparator(SwingConstants.VERTICAL);
        sep.setPreferredSize(new Dimension(1, 28));
        sep.setForeground(COLOR_BORDE);
        filaMetodos.add(sep);

        // Botón limpiar — no entra en botonesMetodo para que no quede "activo"
        // Mismo estilo que CIFRAR/DESCIFRAR: fondo negro, texto blanco, bordes redondeados
        JButton btnLimpiar = crearBotonLimpiar("⟳  LIMPIAR");
        btnLimpiar.addActionListener(e -> limpiarCampos());
        filaMetodos.add(btnLimpiar);

        // ═══════════════════════════════════════════════════════════════════
        // Descripción dinámica (MEJORADA)
        // Fondo más oscuro #1A1B1E, texto #C1C2C5
        // ═══════════════════════════════════════════════════════════════════
        lblDescripcion = new JLabel(" ");
        lblDescripcion.setFont(FUENTE_DESC);
        lblDescripcion.setForeground(COLOR_DESC_TXT);
        lblDescripcion.setOpaque(true);
        lblDescripcion.setBackground(COLOR_DESCRIPCION);
        // Borde redondeado moderno
        lblDescripcion.setBorder(crearBordeRedondeado(RADIO_BTN, COLOR_BORDE, 10, 16, 10, 16));

        // ── Agrupación ─────────────────────────────────────��───────────────
        JPanel wrapFilas = new JPanel(new GridLayout(2, 1, 0, 8));
        wrapFilas.setBackground(COLOR_PANEL);
        wrapFilas.add(filaClave);
        wrapFilas.add(filaMetodos);

        panelSuperior.add(wrapFilas,       BorderLayout.NORTH);
        panelSuperior.add(lblDescripcion,  BorderLayout.SOUTH);

        return panelSuperior;
    }

    // ── Panel central: texto original | acciones | texto cifrado ─────────────
    private JPanel crearPanelCentral() {
        panelCentral = new JPanel(new GridBagLayout());
        panelCentral.setBackground(COLOR_FONDO);
        panelCentral.setBorder(new EmptyBorder(16, 18, 18, 18));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill    = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;
        gbc.gridy   = 0;

        gbc.gridx   = 0;
        gbc.weightx = 0.43;
        panelCentral.add(crearPanelTexto("🖊 ENTRADA", true), gbc);

        gbc.gridx   = 1;
        gbc.weightx = 0.14;
        gbc.fill    = GridBagConstraints.NONE;
        gbc.anchor  = GridBagConstraints.CENTER;
        panelCentral.add(crearPanelAcciones(), gbc);

        gbc.gridx   = 2;
        gbc.weightx = 0.43;
        gbc.fill    = GridBagConstraints.BOTH;
        panelCentral.add(crearPanelTexto("📄 SALIDA", false), gbc);

        return panelCentral;
    }

    /** Panel con título + JScrollPane + JTextArea (MEJORADO) */
    private JPanel crearPanelTexto(String titulo, boolean esOrigen) {
        JPanel panel = new JPanel(new BorderLayout(0, 8));
        panel.setBackground(COLOR_FONDO);

        JLabel lbl = new JLabel(titulo, SwingConstants.CENTER);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 14));  // Fuente más elegante
        lbl.setForeground(COLOR_TEXTO);
        // Fondo de etiqueta más oscuro que el panel en modo oscuro (#1A1B1E vs #25262B)
        lbl.setOpaque(true);
        lbl.setBackground(COLOR_ETIQUETA_TITULO);
        lbl.setBorder(new EmptyBorder(4, 0, 8, 0));
        panel.add(lbl, BorderLayout.NORTH);

        if (esOrigen) lblTextoOrigen = lbl;
        else lblTextoCifrado = lbl;

        JTextArea area = new JTextArea();
        // inicializar con tamaño grande por defecto (24pt)
        area.setFont(new Font("Consolas", Font.PLAIN, 24));
        // Fondo ligeramente tintado
        area.setBackground(esOrigen ? COLOR_PANEL : COLOR_FONDO_INPUT);
        area.setForeground(COLOR_TEXTO);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setMargin(new Insets(12, 14, 12, 14));  // Padding más generoso
        area.setEditable(esOrigen);
        area.setCaretColor(COLOR_TEXTO);

        // ENVOLVER en JPanel con BorderLayout para centrar el texto verticalmente
        JPanel panelCentro = new JPanel(new BorderLayout());
        panelCentro.setBackground(esOrigen ? COLOR_PANEL : COLOR_FONDO_INPUT);
        panelCentro.add(area, BorderLayout.CENTER);

        JScrollPane scroll = new JScrollPane(panelCentro);
        // Borde redondeado moderno
        scroll.setBorder(crearBordeRedondeado(RADIO_INPUT, COLOR_BORDE, 2, 2, 2, 2));
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        // Personalizar scrollbar (solo funciona en sistemas que soportan Nimbus)
        scroll.getVerticalScrollBar().setBackground(COLOR_PANEL);
        panel.add(scroll, BorderLayout.CENTER);

        if (esOrigen) {
            txtOrigen = area;
            scrollOrigen = scroll;
            // agregar DocumentListener para txtOrigen
            txtOrigen.getDocument().addDocumentListener(new DocumentListener() {
                public void changedUpdate(DocumentEvent e) { actualizarFormatoTexto(txtOrigen); }
                public void removeUpdate(DocumentEvent e) { actualizarFormatoTexto(txtOrigen); }
                public void insertUpdate(DocumentEvent e) { actualizarFormatoTexto(txtOrigen); }
            });
        } else {
            txtDestino = area;
            scrollDestino = scroll;
            // agregar DocumentListener para txtDestino
            txtDestino.getDocument().addDocumentListener(new DocumentListener() {
                public void changedUpdate(DocumentEvent e) { actualizarFormatoTexto(txtDestino); }
                public void removeUpdate(DocumentEvent e) { actualizarFormatoTexto(txtDestino); }
                public void insertUpdate(DocumentEvent e) { actualizarFormatoTexto(txtDestino); }
            });
        }

        return panel;
    }

    /** Panel con los dos botones de acción grandes (FlatLaf style) */
    private JPanel crearPanelAcciones() {
        panelAcciones = new JPanel();
        panelAcciones.setLayout(new BoxLayout(panelAcciones, BoxLayout.Y_AXIS));
        panelAcciones.setBackground(COLOR_FONDO);
        panelAcciones.setBorder(new EmptyBorder(0, 8, 0, 8));

        // Botones con iconos de candado para acción profesional
        // CIFRAR: fondo #74C0FC, texto #1A1B1E, radio 10, icono 🔒
        btnCifrar = crearBotonCifrar("CIFRAR 🔒");
        // DESCIFRAR: fondo #373A40, texto #C1C2C5, radio 10, icono 🔓
        btnDescifrar = crearBotonDescifrar("🔓 DESCIFRAR");

        btnCifrar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnDescifrar.setAlignmentX(Component.CENTER_ALIGNMENT);

        btnCifrar.addActionListener(e    -> ejecutarCifrado(true));
        btnDescifrar.addActionListener(e -> ejecutarCifrado(false));

        panelAcciones.add(Box.createVerticalGlue());
        panelAcciones.add(btnCifrar);
        panelAcciones.add(Box.createRigidArea(new Dimension(0, 20)));
        panelAcciones.add(btnDescifrar);
        panelAcciones.add(Box.createVerticalGlue());

        return panelAcciones;
    }

    /** Botón CIFRAR: fondo #74C0FC, texto #1A1B1E, radio 10 */
    private JButton crearBotonCifrar(String texto) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setBackground(COLOR_BTN_CIFRAR);      // #74C0FC
        btn.setForeground(COLOR_BTN_CIFRAR_TXT);   // #1A1B1E

        // Tamaño profesional con dimensiones más generosas
        btn.setPreferredSize(new Dimension(180, 52));
        btn.setMaximumSize(new Dimension(180, 52));

        // Padding generoso: top=12, left=24, bottom=12, right=24
        btn.setMargin(new Insets(12, 24, 12, 24));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);

        // Borde radius 10 para acabado profesional
        Color bordeColor = COLOR_BTN_CIFRAR.darker();
        btn.setBorder(crearBordeRedondeado(10, bordeColor, 10, 24, 10, 24));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setOpaque(true);

        // Efecto hover
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                btn.setBackground(COLOR_ACENTO_HOVER); // Más oscuro
                btn.setBorder(crearBordeRedondeado(10, COLOR_ACENTO_HOVER.darker(), 10, 24, 10, 24));
            }
            public void mouseExited(java.awt.event.MouseEvent e) {
                btn.setBackground(COLOR_BTN_CIFRAR);
                btn.setBorder(crearBordeRedondeado(10, COLOR_BTN_CIFRAR.darker(), 10, 24, 10, 24));
            }
        });
        return btn;
    }

    /** Botón DESCIFRAR: fondo #373A40, texto #C1C2C5, radio 10 */
    private JButton crearBotonDescifrar(String texto) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setBackground(COLOR_BTN_DESCIFRAR);      // #373A40
        btn.setForeground(COLOR_BTN_DESCIFRAR_TXT);   // #C1C2C5

        // Tamaño profesional con dimensiones más generosas
        btn.setPreferredSize(new Dimension(180, 52));
        btn.setMaximumSize(new Dimension(180, 52));

        // Padding generoso: top=12, left=24, bottom=12, right=24
        btn.setMargin(new Insets(12, 24, 12, 24));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);

        // Borde radius 10 para acabado profesional
        Color bordeColor = COLOR_BTN_DESCIFRAR.darker();
        btn.setBorder(crearBordeRedondeado(10, bordeColor, 10, 24, 10, 24));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setOpaque(true);

        // Efecto hover (mismo que CIFRAR)
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                btn.setBackground(COLOR_ACENTO_HOVER); // Más oscuro (igual que CIFRAR)
                btn.setBorder(crearBordeRedondeado(10, COLOR_ACENTO_HOVER.darker(), 10, 24, 10, 24));
            }
            public void mouseExited(java.awt.event.MouseEvent e) {
                btn.setBackground(COLOR_BTN_DESCIFRAR);
                btn.setBorder(crearBordeRedondeado(10, COLOR_BTN_DESCIFRAR.darker(), 10, 24, 10, 24));
            }
        });
        return btn;
    }

    /** Botón LIMPIAR: mismo estilo que CIFRAR/DESCIFRAR - fondo negro #000000, texto blanco #FFFFFF */
    private JButton crearBotonLimpiar(String texto) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setBackground(COLOR_BTN_CIFRAR);       // #000000 Negro
        btn.setForeground(COLOR_BTN_CIFRAR_TXT);   // #FFFFFF Blanco

        // Mismo tamaño que CIFRAR/DESCIFRAR
        btn.setPreferredSize(new Dimension(140, 40));
        btn.setMaximumSize(new Dimension(140, 40));

        // Mismo padding: top=12, left=24, bottom=12, right=24
        btn.setMargin(new Insets(10, 20, 10, 20));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);

        // Mismo borde redondeado radio 10
        Color bordeColor = COLOR_BTN_CIFRAR.darker();
        btn.setBorder(crearBordeRedondeado(10, bordeColor, 10, 20, 10, 20));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setOpaque(true);

        // Efecto hover (igual que CIFRAR/DESCIFRAR)
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                btn.setBackground(COLOR_ACENTO_HOVER);
                btn.setBorder(crearBordeRedondeado(10, COLOR_ACENTO_HOVER.darker(), 10, 20, 10, 20));
            }
            public void mouseExited(java.awt.event.MouseEvent e) {
                btn.setBackground(COLOR_BTN_CIFRAR);
                btn.setBorder(crearBordeRedondeado(10, COLOR_BTN_CIFRAR.darker(), 10, 20, 10, 20));
            }
        });
        return btn;
    }

    // ═══════════════════════════════════════════════════════════════════
    // FÁBRICAS DE BOTONES (MEJORADOS con bordes redondeados y sombras)
    // ═══════════════════════════════════════════════════════════════════

    /** Botón de método de cifrado como toggle con estilo moderno profesional */
    private JButton crearBotonMetodo(String texto) {
        JButton btn = new JButton(texto);
        btn.setFont(FUENTE_BTN_METODO);
        btn.setBackground(COLOR_BTN_NORMAL);       // #373A40
        btn.setForeground(COLOR_BTN_METODO_TXT);  // #C1C2C5

        // Borde redondeado moderno con radio 8 (coincide con estilo de otros botones)
        btn.setBorder(crearBordeRedondeado(RADIO_BTN, COLOR_BORDE, 8, 16, 8, 16));
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setOpaque(true);

        // Efecto hover mejorado - actualiza tanto fondo como borde redondeado
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                // Solo cambia color si NO está activo
                if (!COLOR_BTN_ACTIVO.equals(btn.getBackground())) {
                    btn.setBackground(COLOR_BTN_HOVER);
                    btn.setForeground(COLOR_BTN_METODO_TXT);
                    // Actualizar borde con color de hover
                    btn.setBorder(crearBordeRedondeado(RADIO_BTN, COLOR_ACENTO, 8, 16, 8, 16));
                }
            }
            public void mouseExited(java.awt.event.MouseEvent e) {
                if (!COLOR_BTN_ACTIVO.equals(btn.getBackground())) {
                    btn.setBackground(COLOR_BTN_NORMAL);
                    btn.setForeground(COLOR_BTN_METODO_TXT);
                    // Restaurar borde original
                    btn.setBorder(crearBordeRedondeado(RADIO_BTN, COLOR_BORDE, 8, 16, 8, 16));
                }
            }
        });
        return btn;
    }

    /** Botón grande de acción con estilo moderno y profesional (FlatLaf style) */
    private JButton crearBotonAccion(String texto, boolean esPrimario) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setBackground(COLOR_ACENTO);
        btn.setForeground(Color.WHITE);

        // Tamaño profesional con dimensiones más generosas
        btn.setPreferredSize(new Dimension(180, 52));
        btn.setMaximumSize(new Dimension(180, 52));

        // Padding generoso: top=12, left=24, bottom=12, right=24
        btn.setMargin(new Insets(12, 24, 12, 24));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);

        // Borde radius 10 para acabado profesional
        btn.setBorder(crearBordeRedondeado(10, COLOR_ACENTO.darker(), 10, 24, 10, 24));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setOpaque(true);

        // Efecto hover con transición de color usando getters dinámicos
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                btn.setBackground(COLOR_ACENTO_HOVER);
                btn.setBorder(crearBordeRedondeado(10, COLOR_ACENTO_HOVER.darker(), 10, 24, 10, 24));
            }
            public void mouseExited(java.awt.event.MouseEvent e) {
                btn.setBackground(COLOR_ACENTO);
                btn.setBorder(crearBordeRedondeado(10, COLOR_ACENTO.darker(), 10, 24, 10, 24));
            }
        });
        return btn;
    }

    /** Botón grande de acción legacy - usa el método nuevo */
    private JButton crearBotonAccion(String texto) {
        return crearBotonAccion(texto, true);
    }

    // ── Selección visual del método activo ────────────────────────────────────

    /**
     * Resalta el botón en la posición {@code idx} como activo
     * y restaura el estilo del resto.
     * Mantiene bordes redondeados para consistencia visual.
     */
    private void marcarBotonActivo(int idx) {
        for (int i = 0; i < botonesMetodo.size(); i++) {
            JButton btn = botonesMetodo.get(i);
            if (i == idx) {
                btn.setBackground(COLOR_BTN_ACTIVO);
                btn.setForeground(COLOR_BTN_ACTIVO_TXT);
                // Borde activo: color azul con radio 8
                btn.setBorder(crearBordeRedondeado(RADIO_BTN, COLOR_ACENTO, 8, 16, 8, 16));
            } else {
                btn.setBackground(COLOR_BTN_NORMAL);
                btn.setForeground(COLOR_BTN_METODO_TXT);
                // Borde inactivo: color gris borde con radio 8
                btn.setBorder(crearBordeRedondeado(RADIO_BTN, COLOR_BORDE, 8, 16, 8, 16));
            }
        }
    }

    // ── Lógica de la aplicación ───────────────────────────────────────────────

    private void actualizarDescripcion() {
        if (estrategiaActual != null) {
            // Actualizar descripción del método
            lblDescripcion.setText("  ℹ  " + estrategiaActual.obtenerDescripcion());

            // Actualizar tipo de clave según el método
            String nombre = estrategiaActual.obtenerNombre();
            String tipoClave;
            switch (nombre) {
                case "César":
                    tipoClave = "Clave: Número entero (desplazamiento)";
                    break;
                case "Atbash":
                    tipoClave = "Clave: No requiere clave";
                    break;
                case "Vigenère":
                    tipoClave = "Clave: Palabra o texto";
                    break;
                case "Playfair":
                    tipoClave = "Clave: Palabra sin espacios";
                    break;
                case "Rail Fence":
                    tipoClave = "Clave: Número de rieles (2-10)";
                    break;
                default:
                    tipoClave = "Clave: Verificar documentación";
            }
            lblTipoClave.setText(tipoClave);
        }
    }

    private void actualizarIdiomas() {
        // Aplicar el idioma actual a todas las estrategias
        for (EstrategiaCifrado estrategia : estrategias) {
            estrategia.setIdioma(idiomaActual);
        }
    }

    private boolean validarClave(String nombreMetodo, String clave) {
        // Validar según el tipo de método
        switch (nombreMetodo) {
            case "César":
            case "Rail Fence":
                // Debe ser número válido
                if (clave.isEmpty()) {
                    // Permitir vacío - usará valor por defecto
                    return true;
                }
                try {
                    Integer.parseInt(clave);
                    return true;
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this,
                            "Para «" + nombreMetodo + "» la clave debe ser un número entero.",
                            "Clave inválida",
                            JOptionPane.WARNING_MESSAGE);
                    return false;
                }

            case "Vigenère":
            case "Playfair":
                // Clave obligatoria (no puede estar vacía)
                if (clave == null || clave.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this,
                            "Para «" + nombreMetodo + "» la clave es obligatoria.\n" +
                            "Ingrese una palabra como clave.",
                            "Clave vacía",
                            JOptionPane.WARNING_MESSAGE);
                    return false;
                }
                return true;

            default:
                return true;  // Atbash no requiere validación
        }
    }

    private void limpiarCampos() {
        txtOrigen.setText("");
        txtDestino.setText("");
        txtClave.setText("");
        txtOrigen.requestFocusInWindow();
    }

    private void ejecutarCifrado(boolean esCifrado) {
        // NUEVA LÓGICA: siempre tomar de ENTRADA y mostrar en RESULTADO
        String texto = txtOrigen.getText().trim();
        String clave = txtClave.getText().trim();

        if (texto.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Por favor ingrese texto en el campo «ENTRADA» antes de continuar.",
                    "Campo vacío",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Validar clave según el método
        if (!validarClave(estrategiaActual.obtenerNombre(), clave)) {
            return;
        }

        try {
            // NUEVA LÓGICA: siempre procesar entrada y mostrar en resultado
            String resultado = esCifrado
                    ? estrategiaActual.cifrar(texto, clave)
                    : estrategiaActual.descifrar(texto, clave);

            // Mostrar siempre en el recuadro derecho (RESULTADO)
            txtDestino.setText(resultado);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error al procesar el texto:\n" + ex.getMessage(),
                    "Error de procesamiento",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // ── Tamaño de fuente dinámico ─────────────────────────────────────────

    /**
     * Actualiza el tamaño de fuente y alineación del texto según su longitud.
     * <ul>
     *   <li>Menos de 20 caracteres: tamaño 24 (grande, centrado)</li>
     *   <li>20-50 caracteres: tamaño 18 (mediano)</li>
     *   <li>50-100 caracteres: tamaño 14</li>
     *   <li>Más de 100 caracteres: tamaño 12 (pequeño)</li>
     * </ul>
     * La alineación cambia de centrada a izquierda cuando el texto supera 30 caracteres.
     */
    private void actualizarFormatoTexto(JTextArea textArea) {
        String texto = textArea.getText();
        int longitud = texto.length();

        // calcular tamaño de fuente según longitud
        int tamano;
        if (longitud < 20) {
            tamano = 24;
        } else if (longitud < 50) {
            tamano = 18;
        } else if (longitud < 100) {
            tamano = 14;
        } else {
            tamano = 12;
        }

        // crear nueva fuente con el tamaño calculado
        Font fuente = new Font("Consolas", Font.PLAIN, tamano);
        textArea.setFont(fuente);

        // ajustar alineación: si el texto es corto, centrado; si es largo, izquierda
        if (longitud > 30) {
            // Texto largo: alineación izquierda
            textArea.setAlignmentX(Component.LEFT_ALIGNMENT);
        } else {
            // Texto corto: alineación centrada
            textArea.setAlignmentX(Component.CENTER_ALIGNMENT);
        }
    }

    // ── Referencias a componentes ───────────────────────────────────────────

    // Referencias a componentes que necesitan actualización de colores
    private JLabel lblClave;
    private JLabel lblIdioma;
    private JLabel lblMetodo;
    private JPanel panelSuperior;
    private JPanel panelCentral;
    private JPanel panelAcciones;
    private JLabel lblTextoOrigen;
    private JLabel lblTextoCifrado;
    private JButton btnCifrar;
    private JButton btnDescifrar;
    private List<JButton> todosBotones = new ArrayList<>();

    // ═══════════════════════════════════════════════════════════════════
    // BORDES REDONDEADOS PERSONALIZADOS
    // ═══════════════════════════════════════════════════════════════════

    /**
     * Crea un borde redondeado para botones/inputs.
     */
    private Border crearBotonBorondeado(int radio, Color colorBorde) {
        return new Border() {
            @Override
            public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(colorBorde);
                g2.drawRoundRect(x, y, width - 1, height - 1, radio * 2, radio * 2);
                g2.dispose();
            }

            @Override
            public Insets getBorderInsets(Component c) {
                return new Insets(radio, radio, radio, radio);
            }

            @Override
            public boolean isBorderOpaque() {
                return false;
            }
        };
    }

    /**
     * Crea un borde compuesto con borde redondeado + padding.
     */
    private Border crearBordeRedondeado(int radio, Color colorBorde, int top, int left, int bottom, int right) {
        return BorderFactory.createCompoundBorder(
            crearBotonBorondeado(radio, colorBorde),
            new EmptyBorder(top, left, bottom, right)
        );
    }

    /**
     * Crea un borde específicamente para inputs (JTextField) con estilo FlatLaf.
     * Sin padding interno para permitir padding visual interno del campo.
     */
    private Border crearBordeInputRedondeado(Color colorBorde, int radio) {
        return new Border() {
            @Override
            public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(colorBorde);
                g2.drawRoundRect(x, y, width - 1, height - 1, radio * 2, radio * 2);
                g2.dispose();
            }

            @Override
            public Insets getBorderInsets(Component c) {
                return new Insets(8, 12, 8, 12); // Padding interno generoso
            }

            @Override
            public boolean isBorderOpaque() {
                return false;
            }
        };
    }

    /**
     * Configura anti-aliasing para texto en toda la aplicación.
     * Debe llamarse antes de crear los componentes.
     */
    private void configurarAntiAliasing() {
        System.setProperty("awt.useSystemAAFontSettings", "on");
        System.setProperty("swing.aatext", "true");

        // Para componentes existentes, configurar el rendering hints
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Object fontRenderContext = toolkit.getDesktopProperty("awt.font.desktop");
        if (fontRenderContext != null && fontRenderContext instanceof FontRenderContext) {
            // Already set at desktop level
        }
    }

    /**
     * Configura un scrollbar para que sea delgado y oscuro (FlatLaf style).
     */
    private void configurarScrollBar(JScrollPane scrollPane, Color colorFondo) {
        JScrollBar verticalBar = scrollPane.getVerticalScrollBar();
        verticalBar.setPreferredSize(new Dimension(8, Integer.MAX_VALUE));
        verticalBar.setBackground(colorFondo);

        // Opcional: personalizar la trac
        verticalBar.setUnitIncrement(10);
    }
}