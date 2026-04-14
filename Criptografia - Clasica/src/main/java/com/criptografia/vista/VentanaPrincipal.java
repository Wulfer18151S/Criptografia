package com.criptografia.vista;

import com.criptografia.modelo.CifradoAtbash;
import com.criptografia.modelo.CifradoCesar;
import com.criptografia.modelo.CifradoPlayfair;
import com.criptografia.modelo.CifradoRailFence;
import com.criptografia.modelo.CifradoVigenere;
import com.criptografia.modelo.EstrategiaCifrado;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

public class VentanaPrincipal extends JFrame {

    // ── Paleta de colores ──────────────────────────────────────────────────────
    private static final Color COLOR_FONDO       = new Color(245, 245, 250);
    private static final Color COLOR_PANEL        = Color.WHITE;
    private static final Color COLOR_ACENTO       = new Color(30, 30, 40);
    private static final Color COLOR_ACENTO_HOVER = new Color(60, 60, 80);
    private static final Color COLOR_TEXTO        = new Color(30, 30, 40);
    private static final Color COLOR_BORDE        = new Color(200, 200, 210);
    private static final Color COLOR_DESCRIPCION  = new Color(235, 235, 245);
    private static final Color COLOR_BTN_METODO   = new Color(255, 255, 255);

    // ── Fuentes ────────────────────────────────────────────────────────────────
    private static final Font FUENTE_TITULO   = new Font("Segoe UI", Font.BOLD,   14);
    private static final Font FUENTE_NORMAL   = new Font("Segoe UI", Font.PLAIN,  13);
    private static final Font FUENTE_BTN_METODO = new Font("Segoe UI", Font.BOLD, 12);
    private static final Font FUENTE_BTN_ACCION = new Font("Segoe UI", Font.BOLD, 15);
    private static final Font FUENTE_AREA     = new Font("Consolas",   Font.PLAIN, 13);

    // ── Componentes ───────────────────────────────────────────────────────────
    private JTextField txtClave;
    private JLabel lblDescripcion;
    private JTextArea txtOrigen;
    private JTextArea txtDestino;
    private EstrategiaCifrado estrategiaActual;

    // ──────────────────────────────────────────────────────────────────────────
    public VentanaPrincipal() {
        setTitle("Sistema de Cifrado Clásico");
        setSize(1050, 680);
        setMinimumSize(new Dimension(800, 550));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(0, 0));
        getContentPane().setBackground(COLOR_FONDO);

        inicializarComponentes();
        estrategiaActual = new CifradoCesar();
        actualizarDescripcion();
    }

    // ── Construcción de la UI ─────────────────────────────────────────────────
    private void inicializarComponentes() {
        add(crearPanelSuperior(), BorderLayout.NORTH);
        add(crearPanelCentral(), BorderLayout.CENTER);
    }

    /** Barra superior: campo de clave + botones de método + descripción */
    private JPanel crearPanelSuperior() {
        JPanel panelSuperior = new JPanel(new BorderLayout(0, 6));
        panelSuperior.setBackground(COLOR_PANEL);
        panelSuperior.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, COLOR_BORDE),
                new EmptyBorder(14, 18, 12, 18)
        ));

        // Fila 1: clave
        JPanel filaClave = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        filaClave.setBackground(COLOR_PANEL);

        JLabel lblClave = new JLabel("CLAVE:");
        lblClave.setFont(FUENTE_TITULO);
        lblClave.setForeground(COLOR_TEXTO);
        filaClave.add(lblClave);

        txtClave = new JTextField(22);
        txtClave.setFont(FUENTE_NORMAL);
        txtClave.setPreferredSize(new Dimension(220, 34));
        txtClave.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_BORDE, 1, true),
                new EmptyBorder(4, 8, 4, 8)
        ));
        txtClave.setBackground(COLOR_PANEL);
        txtClave.setForeground(COLOR_TEXTO);
        filaClave.add(txtClave);

        // Fila 2: métodos
        JPanel filaMetodos = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        filaMetodos.setBackground(COLOR_PANEL);

        JLabel lblMetodo = new JLabel("MÉTODO:");
        lblMetodo.setFont(FUENTE_TITULO);
        lblMetodo.setForeground(COLOR_TEXTO);
        filaMetodos.add(lblMetodo);

        List<EstrategiaCifrado> metodos = List.of(
                new CifradoCesar(),
                new CifradoAtbash(),
                new CifradoVigenere(),
                new CifradoRailFence(),
                new CifradoPlayfair()
        );

        for (EstrategiaCifrado metodo : metodos) {
            JButton btn = crearBotonMetodo(metodo.obtenerNombre());
            btn.setToolTipText(metodo.obtenerDescripcion());
            btn.addActionListener(e -> {
                estrategiaActual = metodo;
                actualizarDescripcion();
            });
            filaMetodos.add(btn);
        }

        JButton btnLimpiar = crearBotonMetodo("⟳ Limpiar");
        btnLimpiar.setToolTipText("Limpiar todos los campos");
        btnLimpiar.addActionListener(e -> limpiarCampos());
        filaMetodos.add(btnLimpiar);

        // Descripción
        lblDescripcion = new JLabel(" ");
        lblDescripcion.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        lblDescripcion.setForeground(new Color(80, 80, 100));
        lblDescripcion.setOpaque(true);
        lblDescripcion.setBackground(COLOR_DESCRIPCION);
        lblDescripcion.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_BORDE, 1, true),
                new EmptyBorder(7, 12, 7, 12)
        ));

        JPanel wrapFilas = new JPanel(new GridLayout(2, 1, 0, 6));
        wrapFilas.setBackground(COLOR_PANEL);
        wrapFilas.add(filaClave);
        wrapFilas.add(filaMetodos);

        panelSuperior.add(wrapFilas, BorderLayout.NORTH);
        panelSuperior.add(lblDescripcion, BorderLayout.SOUTH);

        return panelSuperior;
    }

    /** Área central: texto original | botones | texto cifrado */
    private JPanel crearPanelCentral() {
        JPanel panelCentral = new JPanel(new GridBagLayout());
        panelCentral.setBackground(COLOR_FONDO);
        panelCentral.setBorder(new EmptyBorder(16, 18, 18, 18));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;

        // ── Texto Original ────────────────────────────────────────────────
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.42;
        panelCentral.add(crearPanelTexto("📄  TEXTO ORIGINAL", true), gbc);

        // ── Botones de acción ─────────────────────────────────────────────
        gbc.gridx = 1;
        gbc.weightx = 0.16;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        panelCentral.add(crearPanelAcciones(), gbc);

        // ── Texto Cifrado ─────────────────────────────────────────────────
        gbc.gridx = 2;
        gbc.weightx = 0.42;
        gbc.fill = GridBagConstraints.BOTH;
        panelCentral.add(crearPanelTexto("🔒  TEXTO CIFRADO", false), gbc);

        return panelCentral;
    }

    /** Panel contenedor de un JTextArea con título */
    private JPanel crearPanelTexto(String titulo, boolean esOrigen) {
        JPanel panel = new JPanel(new BorderLayout(0, 8));
        panel.setBackground(COLOR_FONDO);

        JLabel lbl = new JLabel(titulo, SwingConstants.CENTER);
        lbl.setFont(FUENTE_TITULO);
        lbl.setForeground(COLOR_TEXTO);
        panel.add(lbl, BorderLayout.NORTH);

        JTextArea area = new JTextArea();
        area.setFont(FUENTE_AREA);
        area.setBackground(COLOR_PANEL);
        area.setForeground(COLOR_TEXTO);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setMargin(new Insets(10, 12, 10, 12));
        area.setEditable(esOrigen);

        JScrollPane scroll = new JScrollPane(area);
        scroll.setBorder(BorderFactory.createLineBorder(COLOR_BORDE, 1, true));
        panel.add(scroll, BorderLayout.CENTER);

        if (esOrigen) txtOrigen  = area;
        else          txtDestino = area;

        return panel;
    }

    /** Panel central con CIFRAR y DESCIFRAR */
    private JPanel crearPanelAcciones() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(COLOR_FONDO);
        panel.setBorder(new EmptyBorder(0, 10, 0, 10));

        JButton btnCifrar    = crearBotonAccion("CIFRAR ⟹",   COLOR_ACENTO, Color.WHITE);
        JButton btnDescifrar = crearBotonAccion("⟸ DESCIFRAR", COLOR_ACENTO, Color.WHITE);

        btnCifrar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnDescifrar.setAlignmentX(Component.CENTER_ALIGNMENT);

        btnCifrar.addActionListener(e    -> ejecutarCifrado(true));
        btnDescifrar.addActionListener(e -> ejecutarCifrado(false));

        panel.add(Box.createVerticalGlue());
        panel.add(btnCifrar);
        panel.add(Box.createRigidArea(new Dimension(0, 18)));
        panel.add(btnDescifrar);
        panel.add(Box.createVerticalGlue());

        return panel;
    }

    // ── Fábricas de botones ───────────────────────────────────────────────────

    /** Botones pequeños de selección de método */
    private JButton crearBotonMetodo(String texto) {
        JButton btn = new JButton(texto);
        btn.setFont(FUENTE_BTN_METODO);
        btn.setBackground(COLOR_BTN_METODO);
        btn.setForeground(COLOR_TEXTO);
        btn.setPreferredSize(new Dimension(0, 32));
        btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_BORDE, 1, true),
                new EmptyBorder(4, 12, 4, 12)
        ));
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                btn.setBackground(COLOR_ACENTO);
                btn.setForeground(Color.WHITE);
            }
            public void mouseExited(java.awt.event.MouseEvent e) {
                btn.setBackground(COLOR_BTN_METODO);
                btn.setForeground(COLOR_TEXTO);
            }
        });
        return btn;
    }

    /** Botones grandes CIFRAR / DESCIFRAR */
    private JButton crearBotonAccion(String texto, Color fondo, Color textoColor) {
        JButton btn = new JButton(texto);
        btn.setFont(FUENTE_BTN_ACCION);
        btn.setBackground(fondo);
        btn.setForeground(textoColor);
        btn.setPreferredSize(new Dimension(170, 52));
        btn.setMaximumSize(new Dimension(170, 52));
        btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(fondo.darker(), 1, true),
                new EmptyBorder(8, 16, 8, 16)
        ));
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                btn.setBackground(COLOR_ACENTO_HOVER);
            }
            public void mouseExited(java.awt.event.MouseEvent e) {
                btn.setBackground(fondo);
            }
        });
        return btn;
    }

    // ── Lógica de la aplicación ───────────────────────────────────────────────

    private void actualizarDescripcion() {
        if (estrategiaActual != null) {
            lblDescripcion.setText("  " + estrategiaActual.obtenerDescripcion());
        }
    }

    private void limpiarCampos() {
        txtOrigen.setText("");
        txtDestino.setText("");
        txtClave.setText("");
        txtOrigen.requestFocusInWindow();
    }

    private void ejecutarCifrado(boolean esCifrado) {
        String texto = esCifrado ? txtOrigen.getText().trim() : txtDestino.getText().trim();
        String clave = txtClave.getText().trim();

        if (texto.isEmpty()) {
            String campo = esCifrado ? "TEXTO ORIGINAL" : "TEXTO CIFRADO";
            JOptionPane.showMessageDialog(this,
                    "Por favor ingrese texto en el campo \"" + campo + "\" antes de continuar.",
                    "Campo vacío",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            String resultado = esCifrado
                    ? estrategiaActual.cifrar(texto, clave)
                    : estrategiaActual.descifrar(texto, clave);

            if (esCifrado) txtDestino.setText(resultado);
            else           txtOrigen.setText(resultado);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error al procesar el texto:\n" + ex.getMessage(),
                    "Error de procesamiento",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}