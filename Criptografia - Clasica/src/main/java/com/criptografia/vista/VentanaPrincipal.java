package main.java.com.criptografia.vista;

import main.java.com.criptografia.modelo.CifradoAtbash;
import main.java.com.criptografia.modelo.CifradoCesar;
import main.java.com.criptografia.modelo.CifradoPlayfair;
import main.java.com.criptografia.modelo.CifradoRailFence;
import main.java.com.criptografia.modelo.CifradoVigenere;
import main.java.com.criptografia.modelo.EstrategiaCifrado;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class VentanaPrincipal extends JFrame {

    // ── Paleta de colores ──────────────────────────────────────────────────────
    private static final Color COLOR_FONDO         = new Color(245, 245, 250);
    private static final Color COLOR_PANEL         = Color.WHITE;
    private static final Color COLOR_ACENTO        = new Color(30, 30, 40);
    private static final Color COLOR_ACENTO_HOVER  = new Color(60, 60, 80);
    private static final Color COLOR_TEXTO         = new Color(30, 30, 40);
    private static final Color COLOR_BORDE         = new Color(200, 200, 210);
    private static final Color COLOR_DESCRIPCION   = new Color(235, 235, 245);
    private static final Color COLOR_BTN_NORMAL    = Color.WHITE;
    private static final Color COLOR_BTN_ACTIVO    = new Color(30, 30, 40);   // fondo seleccionado
    private static final Color COLOR_BTN_ACTIVO_TXT= Color.WHITE;

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
    private JTextArea  txtOrigen;
    private JTextArea  txtDestino;
    private EstrategiaCifrado estrategiaActual;

    /** Lista para controlar qué botón de método está activo */
    private final List<JButton> botonesMetodo = new ArrayList<>();

    // ──────────────────────────────────────────────────────────────────────────
    public VentanaPrincipal() {
        setTitle("Sistema de Cifrado Clásico");
        setSize(1050, 680);
        setMinimumSize(new Dimension(820, 560));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(0, 0));
        getContentPane().setBackground(COLOR_FONDO);

        inicializarComponentes();
        // Seleccionar César como método por defecto
        estrategiaActual = new CifradoCesar();
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
        JPanel panelSuperior = new JPanel(new BorderLayout(0, 8));
        panelSuperior.setBackground(COLOR_PANEL);
        panelSuperior.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, COLOR_BORDE),
                new EmptyBorder(14, 18, 12, 18)
        ));

        // ── Fila 1: campo CLAVE ────────────────────────────────────────────
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

        // ── Fila 2: botones de método ──────────────────────────────────────
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
        JButton btnLimpiar = crearBotonMetodo("⟳  Limpiar");
        btnLimpiar.addActionListener(e -> limpiarCampos());
        filaMetodos.add(btnLimpiar);

        // ── Descripción dinámica ───────────────────────────────────────────
        lblDescripcion = new JLabel(" ");
        lblDescripcion.setFont(FUENTE_DESC);
        lblDescripcion.setForeground(new Color(70, 70, 100));
        lblDescripcion.setOpaque(true);
        lblDescripcion.setBackground(COLOR_DESCRIPCION);
        lblDescripcion.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_BORDE, 1, true),
                new EmptyBorder(8, 14, 8, 14)
        ));

        // ── Agrupación ─────────────────────────────────────────────────────
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
        JPanel panelCentral = new JPanel(new GridBagLayout());
        panelCentral.setBackground(COLOR_FONDO);
        panelCentral.setBorder(new EmptyBorder(16, 18, 18, 18));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill    = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;
        gbc.gridy   = 0;

        gbc.gridx   = 0;
        gbc.weightx = 0.43;
        panelCentral.add(crearPanelTexto("📄  TEXTO ORIGINAL", true), gbc);

        gbc.gridx   = 1;
        gbc.weightx = 0.14;
        gbc.fill    = GridBagConstraints.NONE;
        gbc.anchor  = GridBagConstraints.CENTER;
        panelCentral.add(crearPanelAcciones(), gbc);

        gbc.gridx   = 2;
        gbc.weightx = 0.43;
        gbc.fill    = GridBagConstraints.BOTH;
        panelCentral.add(crearPanelTexto("🔒  TEXTO CIFRADO", false), gbc);

        return panelCentral;
    }

    /** Panel con título + JScrollPane + JTextArea */
    private JPanel crearPanelTexto(String titulo, boolean esOrigen) {
        JPanel panel = new JPanel(new BorderLayout(0, 8));
        panel.setBackground(COLOR_FONDO);

        JLabel lbl = new JLabel(titulo, SwingConstants.CENTER);
        lbl.setFont(FUENTE_TITULO);
        lbl.setForeground(COLOR_TEXTO);
        lbl.setBorder(new EmptyBorder(0, 0, 4, 0));
        panel.add(lbl, BorderLayout.NORTH);

        JTextArea area = new JTextArea();
        area.setFont(FUENTE_AREA);
        area.setBackground(COLOR_PANEL);
        area.setForeground(COLOR_TEXTO);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setMargin(new Insets(10, 12, 10, 12));
        area.setEditable(true);
        if (!esOrigen) area.setBackground(new Color(250, 250, 255));

        JScrollPane scroll = new JScrollPane(area);
        scroll.setBorder(BorderFactory.createLineBorder(COLOR_BORDE, 1, true));
        panel.add(scroll, BorderLayout.CENTER);

        if (esOrigen) txtOrigen  = area;
        else          txtDestino = area;

        return panel;
    }

    /** Panel con los dos botones de acción grandes */
    private JPanel crearPanelAcciones() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(COLOR_FONDO);
        panel.setBorder(new EmptyBorder(0, 8, 0, 8));

        JButton btnCifrar    = crearBotonAccion("CIFRAR ⟹");
        JButton btnDescifrar = crearBotonAccion("⟸ DESCIFRAR");

        btnCifrar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnDescifrar.setAlignmentX(Component.CENTER_ALIGNMENT);

        btnCifrar.addActionListener(e    -> ejecutarCifrado(true));
        btnDescifrar.addActionListener(e -> ejecutarCifrado(false));

        panel.add(Box.createVerticalGlue());
        panel.add(btnCifrar);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(btnDescifrar);
        panel.add(Box.createVerticalGlue());

        return panel;
    }

    // ── Fábricas de botones ───────────────────────────────────────────────────

    /** Botón de método de cifrado (tamaño automático por contenido) */
    private JButton crearBotonMetodo(String texto) {
        JButton btn = new JButton(texto);
        btn.setFont(FUENTE_BTN_METODO);
        btn.setBackground(COLOR_BTN_NORMAL);
        btn.setForeground(COLOR_TEXTO);
        // SIN setPreferredSize fijo — el tamaño lo da el contenido + padding
        btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_BORDE, 1, true),
                new EmptyBorder(6, 14, 6, 14)
        ));
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                // Solo cambia color si NO está activo
                if (!COLOR_BTN_ACTIVO.equals(btn.getBackground())) {
                    btn.setBackground(new Color(230, 230, 240));
                    btn.setForeground(COLOR_TEXTO);
                }
            }
            public void mouseExited(java.awt.event.MouseEvent e) {
                if (!COLOR_BTN_ACTIVO.equals(btn.getBackground())) {
                    btn.setBackground(COLOR_BTN_NORMAL);
                    btn.setForeground(COLOR_TEXTO);
                }
            }
        });
        return btn;
    }

    /** Botón grande de acción: CIFRAR / DESCIFRAR */
    private JButton crearBotonAccion(String texto) {
        JButton btn = new JButton(texto);
        btn.setFont(FUENTE_BTN_ACCION);
        btn.setBackground(COLOR_ACENTO);
        btn.setForeground(Color.WHITE);
        btn.setPreferredSize(new Dimension(168, 54));
        btn.setMaximumSize(new Dimension(168, 54));
        btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_ACENTO.darker(), 1, true),
                new EmptyBorder(8, 16, 8, 16)
        ));
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                btn.setBackground(COLOR_ACENTO_HOVER);
            }
            public void mouseExited(java.awt.event.MouseEvent e) {
                btn.setBackground(COLOR_ACENTO);
            }
        });
        return btn;
    }

    // ── Selección visual del método activo ────────────────────────────────────

    /**
     * Resalta el botón en la posición {@code idx} como activo
     * y restaura el estilo del resto.
     */
    private void marcarBotonActivo(int idx) {
        for (int i = 0; i < botonesMetodo.size(); i++) {
            JButton btn = botonesMetodo.get(i);
            if (i == idx) {
                btn.setBackground(COLOR_BTN_ACTIVO);
                btn.setForeground(COLOR_BTN_ACTIVO_TXT);
                btn.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(COLOR_ACENTO, 1, true),
                        new EmptyBorder(6, 14, 6, 14)
                ));
            } else {
                btn.setBackground(COLOR_BTN_NORMAL);
                btn.setForeground(COLOR_TEXTO);
                btn.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(COLOR_BORDE, 1, true),
                        new EmptyBorder(6, 14, 6, 14)
                ));
            }
        }
    }

    // ── Lógica de la aplicación ───────────────────────────────────────────────

    private void actualizarDescripcion() {
        if (estrategiaActual != null) {
            lblDescripcion.setText("  ℹ  " + estrategiaActual.obtenerDescripcion());
        }
    }

    private void limpiarCampos() {
        txtOrigen.setText("");
        txtDestino.setText("");
        txtClave.setText("");
        txtOrigen.requestFocusInWindow();
    }

    private void ejecutarCifrado(boolean esCifrado) {
        String texto = esCifrado
                ? txtOrigen.getText().trim()
                : txtDestino.getText().trim();
        String clave = txtClave.getText().trim();

        if (texto.isEmpty()) {
            String campo = esCifrado ? "TEXTO ORIGINAL" : "TEXTO CIFRADO";
            JOptionPane.showMessageDialog(this,
                    "Por favor ingrese texto en el campo «" + campo + "» antes de continuar.",
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